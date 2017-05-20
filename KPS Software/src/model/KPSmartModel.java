package model;

import model.event.*;
import model.location.InternationalLocation;
import model.location.NZCity;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;
import model.route.Route;
import model.route.RouteType;
import model.route.RoutingSystem;
import model.staff.Clerk;
import model.staff.Manager;
import model.staff.Staff;
import model.transportFirm.TransportFirm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a wrapper class that contains all model objects
 *
 * @author Hector
 * @version 2017/5/20
 */
public class KPSmartModel {

    private Staff currentStaff;

    // ======= Mock data, existing in memory. Will be stored in DB in final version ==========

    private Map<Integer, Staff> registeredStaff;
    private EventLogger eventLogger;
    private RoutingSystem routingSystem;
    private List<Mail> mails;

    // =======================================================================================


    /**
     * This constructor WILL NOT BE USED IN FINAL VERSION.
     * For now this constructor generates many fake data and models for testing.
     */
    public KPSmartModel() {
        generateEvents();
    }

    /**
     * generates Events. THIS IS ONLY A METHOD FOR TESTING PURPOSE.
     */
    private void generateEvents() {
        registeredStaff = new HashMap<>();
        eventLogger = new EventLogger();
        mails = new ArrayList<>();
        routingSystem = new RoutingSystem();

        // 1. Add 2 users, 1 manager, 1 clerk
        registeredStaff.put(1, new Manager(1, "123", "123"));
        registeredStaff.put(2, new Clerk(2, "123", "123"));

        // 2. Login the manager
        login(1);

        // 3. Add at least 10 routes
        NZLocation wellington = new NZLocation(1, NZCity.Wellington);
        NZLocation auckland = new NZLocation(2, NZCity.Auckland);
        NZLocation christchurch = new NZLocation(3, NZCity.Christchurch);
        NZLocation dunedin = new NZLocation(4, NZCity.Dunedin);
        NZLocation hamilton = new NZLocation(5, NZCity.Hamilton);
        NZLocation parmerstonNorth = new NZLocation(6, NZCity.Palmerston_North);
        NZLocation rotorua = new NZLocation(7, NZCity.Rotorua);

        InternationalLocation sydney = new InternationalLocation(8, "Sydney");
        InternationalLocation singapore = new InternationalLocation(9, "Singapore");
        InternationalLocation hongkong = new InternationalLocation(10, "HongKong");
        InternationalLocation guangzhou = new InternationalLocation(11, "GuangZhou");
        InternationalLocation moscow = new InternationalLocation(12, "Moscow");

        TransportFirm royalPost = new TransportFirm("RoyalPost");
        TransportFirm nzFast = new TransportFirm("NZFast");
        TransportFirm pacificaMails = new TransportFirm("PacificaMails");
        TransportFirm fedEx = new TransportFirm("FedEx");

        Route rout_1 = new Route(dunedin, christchurch, RouteType.Land, 4.0f, royalPost, 1.2f, 0.7f, 1.0f, 0.5f);
        Route rout_2 = new Route(dunedin, christchurch, RouteType.Land, 3.2f, nzFast, 1.4f, 0.9f, 1.1f, 0.6f);
        Route rout_3 = new Route(christchurch, wellington, RouteType.Air, 1.2f, royalPost, 1.9f, 1.3f, 1.6f, 0.9f);
        Route rout_4 = new Route(christchurch, wellington, RouteType.Air, 1.1f, nzFast, 1.8f, 1.3f, 1.7f, 1.0f);
        Route rout_5 = new Route(dunedin, auckland, RouteType.Air, 4.5f, nzFast, 3.9f, 4.0f, 3.2f, 3.5f);
        Route rout_6 = new Route(christchurch, auckland, RouteType.Land, 4.9f, royalPost, 2.9f, 3.2f, 2.5f, 3.0f);
        Route rout_7 = new Route(auckland, sydney, RouteType.Air, 3.0f, fedEx, 6.6f, 6.2f, 5.5f, 5.0f);
        Route rout_8 = new Route(auckland, sydney, RouteType.Sea, 7.5f, pacificaMails, 4.0f, 3.5f, 3.5f, 3.0f);
        Route rout_9 = new Route(auckland, singapore, RouteType.Sea, 10.5f, pacificaMails, 4.8f, 4.6f, 4.5f, 4.2f);
        Route rout_10 = new Route(sydney, hongkong, RouteType.Air, 4.8f, fedEx, 8.8f, 7.6f, 6.5f, 6.5f);
        Route rout_11 = new Route(singapore, guangzhou, RouteType.Sea, 4, pacificaMails, 3.2f, 3.7f, 3.0f, 3.2f);
        Route rout_12 = new Route(hongkong, guangzhou, RouteType.Sea, 0.5f, fedEx, 1.2f, 0.7f, 1.0f, 0.5f);
        Route rout_13 = new Route(guangzhou, moscow, RouteType.Land, 14.0f, fedEx, 9.6f, 9.9f, 8.5f, 8.5f);
        Route rout_14 = new Route(guangzhou, moscow, RouteType.Air, 5.5f, fedEx, 13.5f, 14.5f, 12.0f, 12.5f);

        addRoute(rout_1);
        addRoute(rout_2);
        addRoute(rout_3);
        addRoute(rout_4);
        addRoute(rout_5);
        addRoute(rout_6);
        addRoute(rout_7);
        addRoute(rout_8);
        addRoute(rout_9);
        addRoute(rout_10);
        addRoute(rout_11);
        addRoute(rout_12);
        addRoute(rout_13);
        addRoute(rout_14);

        // 4. deliver several Mails

        /*
         * currently the routing system is empty, so no valid routes can be found
         */

        // this routes should not be rout_5, dunedin to auckland by air
        List<Route> routesForMail_1 = routingSystem.findRoutes(dunedin, auckland, Priority.Domestic_Standard);
        if (isValidRouteChain(routesForMail_1)) {
            deliverMail(new Mail(1, dunedin, auckland, 2500, 3000, Priority.Domestic_Standard, routesForMail_1));
        }

        List<Route> routesForMail_2 = routingSystem.findRoutes(dunedin, moscow, Priority.International_Standard);
        if (isValidRouteChain(routesForMail_2)) {
            deliverMail(new Mail(2, dunedin, moscow, 6200, 30000, Priority.International_Standard, routesForMail_2));
        }

        List<Route> routesForMail_3 = routingSystem.findRoutes(dunedin, hongkong, Priority.International_Air);
        if (isValidRouteChain(routesForMail_3)) {
            deliverMail(new Mail(3, dunedin, hongkong, 99000, 10000, Priority.International_Air, routesForMail_3));
        }

        List<Route> routesForMail_4 = routingSystem.findRoutes(auckland, sydney, Priority.International_Air);
        if (isValidRouteChain(routesForMail_4)) {
            deliverMail(new Mail(4, auckland, sydney, 500, 100, Priority.International_Air, routesForMail_4));
        }

        List<Route> routesForMail_5 = routingSystem.findRoutes(christchurch, auckland, Priority.Domestic_Standard);
        if (isValidRouteChain(routesForMail_5)) {
            deliverMail(new Mail(5, christchurch, auckland, 8800, 8800, Priority.Domestic_Standard, routesForMail_5));
        }

        // 5. update customer price
        int idRouteToUpdatePrice = 1;
        float newPricePerGram = 35.0f;
        float newPricePerVolume = 35.0f;

        updateCustomerPrice(idRouteToUpdatePrice, newPricePerGram, newPricePerVolume);

        // 6. update transport cost
        int idRouteToUpdateCost = 2;
        float newCostPerGram = 35.0f;
        float newCostPerVolume = 35.0f;

        updateTransportCost(idRouteToUpdateCost, newCostPerGram, newCostPerVolume);

        // 7. delete a route (transport discontinued)
        int idRouteToDelete = 3;
        deleteRoute(idRouteToDelete);


        // 8. Calculate Business figures
        // TODO: calculate business figures. (It's Angelo's job. for now.)


        // Notes: a. For each route added in step 3, make sure the related models are populated. e.g. companies, locations.
        //        b. each step will have the corresponding events logged.

    }

    /**
     * This method checks if the given routes is legal, i.e. not null and not empty
     *
     * @param routes  the routes to be checked
     * @return
     */
    private boolean isValidRouteChain(List<Route> routes) {
        return routes != null && !routes.isEmpty();
    }

    /**
     * Log in the staff with the given staff id.
     *
     * @param staffId
     * @return
     */
    public boolean login(int staffId) {
        Staff staff = registeredStaff.get(staffId);

        if (staff == null) {
            return false;
        } else {
            this.currentStaff = staff;
            return true;
        }
    }

    private void addRoute(Route route) {
        routingSystem.addRoute(route);
        eventLogger.logEvent(new RouteAdditionEvent(currentStaff, LocalDateTime.now(), route));
    }

    private void deliverMail(Mail mail) {
        mails.add(mail);
        eventLogger.logEvent(new MailDeliveryEvent(currentStaff, LocalDateTime.now(), mail));
    }

    private void updateCustomerPrice(int routeId, float newPricePerGram, float newPricePerVolume) {
        Route route = routingSystem.getRouteById(routeId);
        float oldPricePerGram = route.getPricePerGram();
        float oldPricePerVolume = route.getPricePerVolume();

        routingSystem.updateRoutePriceById(routeId, newPricePerGram, newPricePerVolume);
        eventLogger.logEvent(new CustomerPriceUpdateEvent(currentStaff, LocalDateTime.now(), routeId,
                oldPricePerGram, oldPricePerVolume, newPricePerGram, newPricePerVolume));

    }

    private void updateTransportCost(int routeId, float newCostPerGram, float newCostPerVolume) {
        Route route = routingSystem.getRouteById(routeId);
        float oldCostPerGram = route.getCostPerGram();
        float oldCostPerVolume = route.getCostPerVolume();

        routingSystem.updateRouteCostById(routeId, newCostPerGram, newCostPerVolume);
        eventLogger.logEvent(new TransportCostUpdateEvent(currentStaff, LocalDateTime.now(), routeId,
                oldCostPerGram, oldCostPerVolume, newCostPerGram, newCostPerVolume));

    }

    private void deleteRoute(int routeId) {
        routingSystem.deleteRouteById(routeId);
        eventLogger.logEvent(new RouteDeletionEvent(currentStaff, LocalDateTime.now(), routeId));
    }
}
