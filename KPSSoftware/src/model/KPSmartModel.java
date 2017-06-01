package model;

import controller.*;
import model.database.KPSDatabase;
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
    //===============================
    //Dipen
    //===============================
    private KPSDatabase database;
    private Staff currentStaffTemp; // TODO: change name to currentStaff later
    //============Controllers===================
    private static LoginScreenController loginScreenController;
    private static HomeScreenController homeScreenController;
    private static UserSettingController userSettingController;
    private static ChangePasswordController changePasswordController;
    private static ManageUserController manageUserControllerl;


    /**
     * This constructor WILL NOT BE USED IN FINAL VERSION.
     * For now this constructor generates many fake data and models for testing.
     */
    public KPSmartModel() {
        //generateEvents();
        //=====================
        //Created by Dipen
        //=====================
        database = new KPSDatabase();
        LoginScreenController.setKpSmartModel(this);
        HomeScreenController.setKpSmartModel(this);
        UserSettingController.setKpSmartModel(this);
        ChangePasswordController.setKpSmartModel(this);
        ManageUserController.setKpSmartModel(this);

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
        registeredStaff.put(1, new Manager(1, "123", "123", true));
        registeredStaff.put(2, new Clerk(2, "123", "123", false));

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

        Route rout_1 = new Route(1, dunedin, christchurch, RouteType.Land, 4.0f, royalPost, 1.2f, 0.7f, 1.0f, 0.5f);
        Route rout_2 = new Route(2, dunedin, christchurch, RouteType.Land, 3.2f, nzFast, 1.4f, 0.9f, 1.1f, 0.6f);
        Route rout_3 = new Route(3, christchurch, wellington, RouteType.Air, 1.2f, royalPost, 1.9f, 1.3f, 1.6f, 0.9f);
        Route rout_4 = new Route(4, christchurch, wellington, RouteType.Air, 1.1f, nzFast, 1.8f, 1.3f, 1.7f, 1.0f);
        Route rout_5 = new Route(5, dunedin, auckland, RouteType.Air, 4.5f, nzFast, 3.9f, 4.0f, 3.2f, 3.5f);
        Route rout_6 = new Route(6, christchurch, auckland, RouteType.Land, 4.9f, royalPost, 2.9f, 3.2f, 2.5f, 3.0f);
        Route rout_7 = new Route(7, auckland, sydney, RouteType.Air, 3.0f, fedEx, 6.6f, 6.2f, 5.5f, 5.0f);
        Route rout_8 = new Route(8, auckland, sydney, RouteType.Sea, 7.5f, pacificaMails, 4.0f, 3.5f, 3.5f, 3.0f);
        Route rout_9 = new Route(9, auckland, singapore, RouteType.Sea, 10.5f, pacificaMails, 4.8f, 4.6f, 4.5f, 4.2f);
        Route rout_10 = new Route(10, sydney, hongkong, RouteType.Air, 4.8f, fedEx, 8.8f, 7.6f, 6.5f, 6.5f);
        Route rout_11 = new Route(11, singapore, guangzhou, RouteType.Sea, 4, pacificaMails, 3.2f, 3.7f, 3.0f, 3.2f);
        Route rout_12 = new Route(12, hongkong, guangzhou, RouteType.Sea, 0.5f, fedEx, 1.2f, 0.7f, 1.0f, 0.5f);
        Route rout_13 = new Route(13, guangzhou, moscow, RouteType.Land, 14.0f, fedEx, 9.6f, 9.9f, 8.5f, 8.5f);
        Route rout_14 = new Route(14, guangzhou, moscow, RouteType.Air, 5.5f, fedEx, 13.5f, 14.5f, 12.0f, 12.5f);

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

        // this routes should not be rout_5, dunedin to auckland by air
        Mail mail_1 = new Mail(1, dunedin, auckland, 2500, 3000, Priority.Domestic_Standard);
        List<Route> routesForMail_1 = routingSystem.findRoutes(mail_1);
        if (isValidRouteChain(routesForMail_1)) {
            mail_1.setRoutes(routesForMail_1);
            deliverMail(mail_1);
        }

        Mail mail_2 = new Mail(2, dunedin, moscow, 6200, 30000, Priority.International_Standard);
        List<Route> routesForMail_2 = routingSystem.findRoutes(mail_2);
        if (isValidRouteChain(routesForMail_2)) {
            mail_2.setRoutes(routesForMail_2);
            deliverMail(mail_2);
        }

        Mail mail_3 = new Mail(3, dunedin, hongkong, 99000, 10000, Priority.International_Air);
        List<Route> routesForMail_3 = routingSystem.findRoutes(mail_3);
        if (isValidRouteChain(routesForMail_3)) {
            mail_3.setRoutes(routesForMail_3);
            deliverMail(mail_3);
        }

        Mail mail_4 = new Mail(4, auckland, sydney, 500, 100, Priority.International_Air);
        List<Route> routesForMail_4 = routingSystem.findRoutes(mail_4);
        if (isValidRouteChain(routesForMail_4)) {
            mail_4.setRoutes(routesForMail_4);
            deliverMail(mail_4);
        }

        Mail mail_5 = new Mail(5, christchurch, auckland, 8800, 8800, Priority.Domestic_Standard);
        List<Route> routesForMail_5 = routingSystem.findRoutes(mail_5);
        if (isValidRouteChain(routesForMail_5)) {
            mail_5.setRoutes(routesForMail_5);
            deliverMail(mail_5);
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
     * =================================================================================================================
     * These methods made by Dipen
     * =================================================================================================================
     */
    public Staff getCurrentUser() {
        return currentStaffTemp;
    }

    public List<Staff> getAllUsers() {
        return KPSDatabase.getLogins();
    }

    public void deleteUser(String selectedUser) {
        Staff tempStaff = null;
        for (Staff s : KPSDatabase.getLogins()) {
            String[] names = selectedUser.split(" ");
            if (s.getFirstName().equals(names[0]) && s.getLastName().equals(names[1])) {
                tempStaff = s;
            }
        }
        KPSDatabase.getLogins().remove(tempStaff);
        assert KPSDatabase.getLogins().size() == 1;
    }

    public boolean updateStaffInformation(String selectedUser, String firstName, String lastName, String email, String phone, boolean changeRole) {
        Staff tempStaff = null;
        for (Staff s : KPSDatabase.getLogins()) {
            String[] names = selectedUser.split(" ");
            if (s.getFirstName().equals(names[0]) && s.getLastName().equals(names[1])) {
                tempStaff = s;
            }
        }
        if (tempStaff == null) {
            return false;
        }
        if (changeRole) {
            //if the user wants to change roles to manager
            KPSDatabase.getLogins().remove(tempStaff);
            assert KPSDatabase.getLogins().size() == 1;
            Staff newStaff = new Manager(tempStaff.getUID(), tempStaff.getUserName(), tempStaff.getPassword(), true);
            if (!firstName.equals("")) {
                tempStaff.setFirstName(firstName);
            } else {
                newStaff.setFirstName(tempStaff.getFirstName());
            }
            if (!lastName.equals("")) {
                tempStaff.setLastName(lastName);
            } else {
                newStaff.setLastName(tempStaff.getLastName());
            }
            if (!email.equals("")) {
                tempStaff.setEmail(email);
            } else {
                newStaff.setEmail(tempStaff.getEmail());
            }
            if (!phone.equals("")) {
                tempStaff.setPhoneNumber(phone);
            } else {
                newStaff.setPhoneNumber(tempStaff.getPhoneNumber());
            }
            KPSDatabase.getLogins().add(newStaff);
            assert KPSDatabase.getLogins().size() == 2;
            return true;

        } else {
            // if the user just want ot update an users information
            if (!firstName.equals("")) {
                tempStaff.setFirstName(firstName);
            }
            if (!lastName.equals("")) {
                tempStaff.setLastName(lastName);
            }
            if (!email.equals("")) {
                tempStaff.setEmail(email);
            }
            if (!phone.equals("")) {
                tempStaff.setPhoneNumber(phone);
            }
            return true;
        }

    }

    public Staff getSelectedUser(String selectedUser) {
        for (Staff s : KPSDatabase.getLogins()) {
            String[] names = selectedUser.split(" ");
            if (s.getFirstName().equals(names[0]) && s.getLastName().equals(names[1])) {
                return s;
            }

        }
        return null;
    }

    public String changeUserPassword(String oldPassword, String newPassword, String retypePassword) {
        return currentStaffTemp.editPassword(oldPassword, newPassword, retypePassword);
    }

    public boolean authenticateLogin(String username, String password) {
        Staff staff = KPSDatabase.checkLogin(username, password);
        if (staff != null) {
            currentStaffTemp = staff;
            return true;
        }
        return false;
    }

    public static void setLoginScreenController(Object controllers) {
        if (controllers instanceof LoginScreenController) {
            loginScreenController = (LoginScreenController) controllers;
        } else if (controllers instanceof HomeScreenController) {
            homeScreenController = (HomeScreenController) controllers;
        } else if (controllers instanceof UserSettingController) {
            userSettingController = (UserSettingController) controllers;
        } else if (controllers instanceof ChangePasswordController) {
            changePasswordController = (ChangePasswordController) controllers;
        } else if (controllers instanceof ManageUserController) {
            manageUserControllerl = (ManageUserController) controllers;
        }

    }
    /**
     * =================================================================================================================
     * END
     * =================================================================================================================
     */

    /**
     * This method checks if the given routes is legal, i.e. not null and not empty
     *
     * @param routes the routes to be checked
     * @return
     */
    private boolean isValidRouteChain(List<Route> routes) {
        // TODO: delete these prints.
        System.out.println();
        System.out.println("========== Finding Routes ===========");

        if (routes == null) {
            System.out.println("Routes is null");
        } else if (routes.isEmpty()) {
            System.out.println("Routes is empty");
        } else {
            routes.forEach(System.out::println);
        }

        System.out.println("======================================");
        System.out.println();

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
        // TODO: need to self-increment the route id here.
    }

    private void deliverMail(Mail mail) {
        mails.add(mail);
        eventLogger.logEvent(new MailDeliveryEvent(currentStaff, LocalDateTime.now(), mail));
        // TODO: need to self-increment the mail id here.
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
