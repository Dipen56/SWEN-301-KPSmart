package model;

import model.database.XMLDriver;
import model.event.*;
import model.location.InternationalLocation;
import model.location.Location;
import model.location.NZCity;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;
import model.route.Route;
import model.route.RouteType;
import model.route.RoutingSystem;
import model.staff.Staff;

import java.time.LocalDateTime;
import java.util.*;

/*
TODO: consider another approach for implementing Database TRANSACTION:
      1. record all of results in each step, e.g. bool_1, bool_2, bool_3, ...
      2. if (!(bool_1 && bool_2 && bool_3 ... )) {
             reloadEverythingFromXML();
             return false;
         } else {
             return true;
         }
 */

/**
 * This class is a wrapper class that contains all model objects
 *
 * @author Hector
 * @version 2017/5/20
 */
public class KPSModel {

    // ================================= Key modules ================================

    private RoutingSystem routingSystem;


    // ================ fields for maintaining programme status =====================

    private Staff currentStaff;

    private int maxEventId;
    private int maxLocationId;
    private int maxMailId;
    private int maxRouteId;
    private int maxStaffId;


    // ================= cached data (data from XML files) ======================

    private Map<Integer, Event> events;

    /**
     * All locations in the system. They are maintained as a HashMap, where the key is the id of location, and the value
     * is the location itself.
     */
    private Map<Integer, Location> locations;

    private Map<Integer, Mail> mails;


    /**
     * All routes in the system. They are maintained as a HashMap, where the key is the id of route, and the value is
     * the route itself.
     */
    private Map<Integer, Route> routes;

    private Map<Integer, Staff> registeredStaffs;

    /**
     * All recorded origins in the system.
     */
    private Set<NZLocation> origins;

    /**
     * All recorded destinations in the system.
     */
    private Set<Location> destinations;

    /**
     * Constructor
     */
    public KPSModel() {
        loadDataFromXML();
        routingSystem = new RoutingSystem(routes);
        prepareRoutingSystem();
        prepareOriginsAndDestinations();
    }

    // ============================================================
    //                  Methods for Events
    // ============================================================

    /**
     * @return All the events recorded in the system.
     */
    public Map<Integer, Event> getAllEvens() {
        return this.events;
    }

    /**
     * @param id the id of the event that we want to find.
     * @return the event with the given id.
     */
    public Event getEventById(int id) {
        return this.events.get(id);
    }

    // ============================================================
    //                 Methods for Locations
    // ============================================================

    /**
     * @return the set of all available origins in the system
     */
    public Set<NZLocation> getAvailableOrigins() {
        return this.origins;
    }

    /**
     * @return the set of all available destinations in the system
     */
    public Set<Location> getAvailableDestinations() {
        return this.destinations;
    }


    // ============================================================
    //                   Methods for Mails
    // ============================================================


    /**
     * Find out if we can support sending mail from the given origin to the given destination. If we can't send the
     * mail, then -1 is returned. If we can, the id of the mail is returned.
     *
     * @param originString
     * @param destinationString
     * @param weight
     * @param volume
     * @param priority
     * @return -1 if we can't send this mail; or the id of the mail if we can.
     */
    public int processMail(String originString, String destinationString, double weight, double volume, Priority priority) {
        NZLocation origin = findOrCreateNZLocationByString(originString);
        Location destination = findOrCreateLocationByString(destinationString);

        maxMailId++;
        Mail mail = new Mail(maxMailId, origin, destination, weight, volume, priority);

        // let the routing system try to find a valid chain of routes
        List<Route> routes = routingSystem.findRoutes(mail);

        // if cannot find available routes, return false
        if (!isValidRouteChain(routes)) {
            maxMailId--;
            return -1;
        }

        mail.setRoutes(routes);

        this.mails.put(mail.id, mail);

        // log the event
        maxEventId++;
        XMLDriver.writeMailDeliveryEvent(new MailDeliveryEvent(maxEventId, currentStaff.id, LocalDateTime.now(), mail.id));

        return mail.id;
    }

    /**
     * @return all mails as a map, where the key is the id of the Mail, and the value is the Mail object
     */
    public Map<Integer, Mail> getAllMails() {
        return this.mails;
    }

    /**
     * @param id
     * @return the mail that matches the given id.
     */
    public Mail getMailById(int id) {
        return this.mails.get(id);
    }

    /**
     * @param id the id of mail that we want to get revenue from
     * @return the revenue of the mail
     */
    public double getMailRevenue(int id) {
        return getMailById(id).getRevenue();
    }

    /**
     * @param id the id of mail that we want to get expenditure from
     * @return the expenditure of the mail
     */
    public double getMailExpenditure(int id) {
        return getMailById(id).getExpenditure();
    }

    /**
     * Find all mails whose origin matches the given origin.
     *
     * @param originString      wanted origin name
     * @return wanted mails as a map, where the key is the id of the Mail, and the value is the Mail object
     */
    public Map<Integer, Mail> getMailsByOrigin(String originString) {
        Map<Integer, Mail> wantedMails = new HashMap<>();

        this.mails.forEach((id, mail) -> {
            String mailOrigin = mail.getOrigin().getLocationName();

            if (mailOrigin.equalsIgnoreCase(originString)) {
                wantedMails.put(mail.id, mail);
            }
        });

        return wantedMails;
    }

    /**
     * Find all mails whose destination matches the given destination.
     *
     * @param destinationString wanted destination name
     * @return wanted mails as a map, where the key is the id of the Mail, and the value is the Mail object
     */
    public Map<Integer, Mail> getMailsByDestination(String destinationString) {
        Map<Integer, Mail> wantedMails = new HashMap<>();

        this.mails.forEach((id, mail) -> {
            String mailDestination = mail.getDestination().getLocationName();

            if (mailDestination.equalsIgnoreCase(destinationString)) {
                wantedMails.put(mail.id, mail);
            }
        });

        return wantedMails;
    }

    /**
     * Find all mails whose origin matches the given origin, and destination matches the given destination.
     *
     * @param originString      wanted origin name
     * @param destinationString wanted destination name
     * @return wanted mails as a map, where the key is the id of the Mail, and the value is the Mail object
     */
    public Map<Integer, Mail> getMailsByOriginAndDestination(String originString, String destinationString) {
        Map<Integer, Mail> wantedMails = new HashMap<>();

        this.mails.forEach((id, mail) -> {
            String mailOrigin = mail.getOrigin().getLocationName();
            String mailDestination = mail.getDestination().getLocationName();

            if (mailOrigin.equalsIgnoreCase(originString) && mailDestination.equalsIgnoreCase(destinationString)) {
                wantedMails.put(mail.id, mail);
            }
        });

        return wantedMails;
    }

    // ============================================================
    //                   Methods for Routes
    // ============================================================

    /**
     * Update the prices (price per gram & price per volume) of the route with the given id.
     *
     * @param routeId           the id of the route that needs to be updated
     * @param newPricePerGram   the new price per gram
     * @param newPricePerVolume the new price per volume
     * @return true if successful, or false if failed.
     */
    public boolean updateCustomerPrice(int routeId, double newPricePerGram, double newPricePerVolume) {
        Route route = getRouteById(routeId);

        // if we can't find the route, or the route is inactive, return false
        if (route == null || !route.isActive()) {
            return false;
        }

        double oldPricePerGram = route.getPricePerGram();
        double oldPricePerVolume = route.getPricePerVolume();

        // update the price
        route.setPricePerGram(newPricePerGram);
        route.setPricePerVolume(newPricePerVolume);

        // update the xml file
        XMLDriver.updateRoute(routeId, route);

        // log event into xml file
        maxEventId++;
        XMLDriver.writeCustomerPriceUpdateEvent(new CustomerPriceUpdateEvent(maxEventId, currentStaff.id, LocalDateTime.now(),
                routeId, oldPricePerGram, oldPricePerVolume, newPricePerGram, newPricePerVolume));

        return true;
    }

    /**
     * Update the costs (cost per gram & cost per volume) of the route with the given id.
     *
     * @param routeId          the id of the route that needs to be updated
     * @param newCostPerGram   the new cost per gram
     * @param newCostPerVolume the new cost per volume
     * @return true if successful, or false if failed.
     */
    public boolean updateTransportCost(int routeId, double newCostPerGram, double newCostPerVolume) {
        Route route = getRouteById(routeId);

        // if we can't find the route, or the route is inactive, return false
        if (route == null || !route.isActive()) {
            return false;
        }

        double oldCostPerGram = route.getCostPerGram();
        double oldCostPerVolume = route.getCostPerVolume();

        // update the cost
        route.setCostPerGram(newCostPerGram);
        route.setCostPerVolume(newCostPerVolume);

        // update the xml file
        XMLDriver.updateRoute(routeId, route);

        // log event into xml file
        maxEventId++;
        XMLDriver.writeTransportCostUpdateEvent(new TransportCostUpdateEvent(maxEventId, currentStaff.id, LocalDateTime.now(),
                routeId, oldCostPerGram, oldCostPerVolume, newCostPerGram, newCostPerVolume));

        return true;
    }

    /**
     * Add a route
     *
     * @param startString    the String form of the start location
     * @param endString      the String form of the end location
     * @param routeType
     * @param duration
     * @param transportFirm
     * @param pricePerGram
     * @param pricePerVolume
     * @param costPerGram
     * @param costPerVolume
     * @return
     */
    public boolean addRoute(String startString, String endString, RouteType routeType, double duration, String transportFirm,
                            double pricePerGram, double pricePerVolume, double costPerGram, double costPerVolume) {

        // do not add same route again
        for (Route route : this.routes.values()) {
            if (route.getStartLocation().getLocationName().equalsIgnoreCase(startString)
                    && route.getEndLocation().getLocationName().equalsIgnoreCase(endString)
                    && route.routeType.equals(routeType)
                    && route.getTransportFirm().equalsIgnoreCase(transportFirm)
                    && route.getDuration() == duration) {
                return false;
            }
        }

        // find or create start location and end location
        Location start = findOrCreateLocationByString(startString);
        Location end = findOrCreateLocationByString(endString);

        // add start and end to available origins and destinations.
        if (start instanceof NZLocation) {
            NZLocation origin = (NZLocation) start;
            this.origins.add(origin);
        }
        this.destinations.add(end);

        maxRouteId++;
        Route route = new Route(maxRouteId, start, end, routeType, duration, transportFirm, pricePerGram, pricePerVolume,
                costPerGram, costPerVolume);

        // Update cached routes, the routing system, and the xml file
        this.routes.put(route.id, route);
        routingSystem.addGraphRoute(route);
        XMLDriver.writeRoute(route);

        // log event
        maxEventId++;
        XMLDriver.writeRouteAdditionEvent(new RouteAdditionEvent(maxEventId, currentStaff.id, LocalDateTime.now(), route.id));

        return true;
    }

    /**
     * Deactivate the route
     *
     * @param routeId routeId the id of the route that needs to be updated
     * @return true if the route is no longer active, or false if the deactivation failed.
     */
    public boolean deactivateRoute(int routeId) {
        Route route = getRouteById(routeId);
        route.setIsActive(false);

        routingSystem.deleteGraphRouteById(routeId);
        XMLDriver.updateRoute(routeId, route);

        // log event
        maxEventId++;
        XMLDriver.writeRouteDeactivationEvent(new RouteDeactivationEvent(maxEventId, currentStaff.id, LocalDateTime.now(), routeId));

        return true;
    }

    /**
     * @param id the id of the route that needs to be found
     * @return the route with the given id
     */
    public Route getRouteById(int id) {
        return routes.get(id);
    }

    /**
     * @return all Route as a map, where the key is the id of the Route, and the value is the Route object
     */
    public Map<Integer, Route> getAllRoutes() {
        return this.routes;
    }

    /**
     * @return all critical mails
     */
    public Map<Integer, Mail> getCriticalMails() {
        Map<Integer, Mail> criticalMails = new HashMap<>();

        mails.values().forEach(mail -> {
            if (mail.getRevenue() - mail.getExpenditure() < 0) {
                criticalMails.put(mail.id, mail);
            }
        });

        return criticalMails;
    }

    // ============================================================
    //                   Methods for Staff
    // ============================================================

    /**
     * This method tries to log in with the given username and password. If the login is successful, this method will
     * assign the matched Staff as the logged-in user.
     *
     * @param username
     * @param password
     * @return true if the given username and password match any registered staff, or false if there is no match.
     */
    public boolean login(String username, String password) {
        for (Staff staff : registeredStaffs.values()) {
            if (staff.checkCredentials(username, password)) {
                currentStaff = staff;
                return true;
            }
        }

        return false;
    }

    /**
     * Create a new staff, and save it to XML.
     *
     * @param userName
     * @param password
     * @param isManager
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @return true if successful, or false if failed.
     */
    public boolean createNewStaff(String userName, String password, boolean isManager, String firstName,
                                  String lastName, String email, String phoneNumber) {

        // do not add same staff again
        for (Staff staff : this.registeredStaffs.values()) {
            if ((staff.getFirstName().equals(firstName) && staff.getLastName().equals(lastName))
                    || staff.getUserName().equals(userName)) {
                return false;
            }
        }

        maxStaffId++;
        Staff newStaff = new Staff(maxStaffId, userName, password, isManager, firstName, lastName, email, phoneNumber);

        boolean result = XMLDriver.writeStaff(newStaff);

        if (result) {
            registeredStaffs.put(newStaff.id, newStaff);
            return true;
        } else {
            maxStaffId--;
            return false;
        }
    }

    /**
     * @return the staff logged in currently
     */
    public Staff getCurrentStaff() {
        return this.currentStaff;
    }

    /**
     * @return all registered staffs as a map, where the key is the id of the staff, and the value is the Staff object
     */
    public Map<Integer, Staff> getAllStaffs() {
        return this.registeredStaffs;
    }

    /**
     * @param id
     * @return the staff who matches the given id, or null if there is no match
     */
    public Staff getStaffById(int id) {
        return this.registeredStaffs.get(id);
    }

    /**
     * Update the Staff information, and write into XML.
     *
     * @param idToUpdate
     * @param newUserName
     * @param newPassword
     * @param newIsManager
     * @param newFirstName
     * @param newLastName
     * @param newEmail
     * @param newPhoneNumber
     * @return
     */
    public boolean updateStaff(int idToUpdate, String newUserName, String newPassword, boolean newIsManager, String newFirstName,
                               String newLastName, String newEmail, String newPhoneNumber) {

        // if there is no match for the given id, return false;do not add same staff again
        if (registeredStaffs.values().stream().noneMatch(staff -> staff.id == idToUpdate)) {
            return false;
        }

        Staff staff = new Staff(idToUpdate, newUserName, newPassword, newIsManager, newFirstName, newLastName, newEmail, newPhoneNumber);
        this.registeredStaffs.put(idToUpdate, staff);

        return XMLDriver.updateStaff(idToUpdate, staff);
    }

    /**
     * Delete the staff who matches the given id
     *
     * @param id
     * @return true if successful, or false if failed.
     */
    public boolean deleteStaff(int id) {
        boolean result = XMLDriver.deleteStaffById(id);

        if (result) {
            this.registeredStaffs.remove(id);
            return true;
        } else {
            return false;
        }
    }


    // =========================================================================
    //                     STATIC (HELPER) METHODS
    // =========================================================================

    /**
     * @return the total revenue of given mails
     */
    public static double calculateTotalRevenue(Map<Integer, Mail> mails) {
        return mails.values().stream()
                .mapToDouble(Mail::getRevenue)
                .reduce(0, (result, revenue) -> result = result + revenue);
    }

    /**
     * @return the total cost(expenditure) of given mails
     */
    public static double calculateTotalExpenditure(Map<Integer, Mail> mails) {
        return mails.values().stream()
                .mapToDouble(Mail::getExpenditure)
                .reduce(0, (result, cost) -> result = result + cost);
    }

    /**
     * @return the total profit of given mails
     */
    public static double calculateTotalProfit(Map<Integer, Mail> mails) {
        return calculateTotalRevenue(mails) - calculateTotalExpenditure(mails);
    }


    // =========================================================================
    //                         PRIVATE METHODS
    // =========================================================================


    /**
     * Load data from XML files.
     */
    private void loadDataFromXML() {
        events = XMLDriver.readEvents();
        locations = XMLDriver.readLocations();
        mails = XMLDriver.readMails();
        routes = XMLDriver.readRoutes();
        registeredStaffs = XMLDriver.readStaffs();

        maxEventId = XMLDriver.getMaxEventId();
        maxLocationId = XMLDriver.getMaxLocationId();
        maxMailId = XMLDriver.getMaxMailId();
        maxRouteId = XMLDriver.getMaxRouteId();
        maxStaffId = XMLDriver.getMaxStaffId();
    }

    /**
     * Prepare the routing graph system
     */
    private void prepareRoutingSystem() {
        this.routingSystem.clearAll();

        routes.values().forEach(route -> {
            routingSystem.addGraphRoute(route);
        });
    }

    /**
     * Prepare the set of available origins and destinations
     */
    private void prepareOriginsAndDestinations() {
        this.origins = new HashSet<>();
        this.destinations = new HashSet<>();

        routes.values().forEach(route -> {
            // find or create start location and end location
            Location start = route.getStartLocation();
            Location end = route.getEndLocation();

            // add start and end to available origins and destinations.
            if (start instanceof NZLocation) {
                NZLocation origin = (NZLocation) start;
                this.origins.add(origin);
            }
            this.destinations.add(end);
        });
    }

    /**
     * Given a city name as a string, this method tries to find a match from existing *NZ* locations. If a match can be
     * found, then the match is returned. If no match can be found, this method will create a new NZLocation, and return
     * the newly created one.
     * <p>
     * NOTE: this method should be used to find a NZLocation that can be used as the origin of mails.
     *
     * @param nzLocationName
     * @return
     */
    private NZLocation findOrCreateNZLocationByString(String nzLocationName) {
        for (Location location : this.locations.values()) {
            if (location.getLocationName().equalsIgnoreCase(nzLocationName) && location instanceof NZLocation) {
                return (NZLocation) location;
            }
        }

        return createNZLocation(nzLocationName);
    }

    /**
     * Given a city name as a string, this method tries to find a match from existing locations. If a match can be
     * found, then the match is returned. If no match can be found, this method will create a new one, and return the
     * newly created one.
     * <p>
     * NOTE: this method should be used to find a generic Location that can be used as the destination of mails.
     *
     * @param locationName
     * @return
     */
    private Location findOrCreateLocationByString(String locationName) {
        for (Location location : this.locations.values()) {
            if (location.getLocationName().equalsIgnoreCase(locationName)) {
                return location;
            }
        }

        return createLocation(locationName);
    }

    /**
     * Create a new *NZ* location, and record it to XML.
     *
     * @param nzCityName
     * @return
     */
    private NZLocation createNZLocation(String nzCityName) {
        NZCity nzCity = NZCity.createFromString(nzCityName);

        if (nzCity != null) {
            return createNZLocation(nzCity);
        }

        return null;
    }

    /**
     * Create a new location, and record it to XML.
     *
     * @param locationName
     * @return
     */
    private Location createLocation(String locationName) {
        NZLocation nzLocation = createNZLocation(locationName);

        if (nzLocation != null) {
            return nzLocation;
        }

        // this is a international location
        maxLocationId++;
        InternationalLocation internationalLocation = new InternationalLocation(maxLocationId, locationName);

        this.locations.put(internationalLocation.id, internationalLocation);
        XMLDriver.writeLocation(internationalLocation);

        return internationalLocation;
    }

    /**
     * A helper method to create a NZLocation from enum NZCity object.
     *
     * @param nzCity
     * @return
     */
    private NZLocation createNZLocation(NZCity nzCity) {
        maxLocationId++;
        NZLocation nzLocation = new NZLocation(maxLocationId, nzCity);

        this.locations.put(nzLocation.id, nzLocation);
        XMLDriver.writeLocation(nzLocation);

        return nzLocation;
    }

    /**
     * This method checks if the given routes is legal, i.e. not null and not empty
     *
     * @param routes the routes to be checked
     * @return
     */
    private boolean isValidRouteChain(List<Route> routes) {
        return routes != null && !routes.isEmpty();
    }
}
