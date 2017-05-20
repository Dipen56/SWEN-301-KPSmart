package model;

import model.event.EventManager;
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

import java.util.*;

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
    private EventManager eventManager;
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
        eventManager = new EventManager();
        mails = new ArrayList<>();

        // 1. Add 2 users, 1 manager, 1 clerk
        registeredStaff.put(1, new Manager(1, "123", "123"));
        registeredStaff.put(2, new Clerk(2, "123", "123"));

        // 2. Login the manager
        login(1);

        // 3. Add at least 10 routes
        Set<Route> routes = new HashSet<>();

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

        routes.add(rout_1);
        routes.add(rout_2);
        routes.add(rout_3);
        routes.add(rout_4);
        routes.add(rout_5);
        routes.add(rout_6);
        routes.add(rout_7);
        routes.add(rout_8);
        routes.add(rout_9);
        routes.add(rout_10);
        routes.add(rout_11);
        routes.add(rout_12);
        routes.add(rout_13);
        routes.add(rout_14);

        routingSystem = new RoutingSystem(routes);

        // 4. Add several Mails
        Mail mail_1;
        Mail mail_2;
        Mail mail_3;
        Mail mail_4;
        Mail mail_5;

        // this routes should not be rout_5, dunedin to auckland by air
        List<Route> routesForMail_1 = routingSystem.findRoutes(dunedin, auckland, Priority.Domestic_Standard);
        if (isLegalRoutes(routesForMail_1)) {
            mail_1 = new Mail(1, dunedin, auckland, 2500, 3000, Priority.Domestic_Standard, routesForMail_1);
            mails.add(mail_1);
        }

        List<Route> routesForMail_2 = routingSystem.findRoutes(dunedin, moscow, Priority.International_Standard);
        if (isLegalRoutes(routesForMail_2)) {
            mail_2 = new Mail(2, dunedin, moscow, 6200, 30000, Priority.International_Standard, routesForMail_2);
            mails.add(mail_2);
        }

        List<Route> routesForMail_3 = routingSystem.findRoutes(dunedin, hongkong, Priority.International_Air);
        if (isLegalRoutes(routesForMail_3)) {
            mail_3 = new Mail(3, dunedin, hongkong, 99000, 10000, Priority.International_Air, routesForMail_3);
            mails.add(mail_3);
        }

        List<Route> routesForMail_4 = routingSystem.findRoutes(auckland, sydney, Priority.International_Air);
        if (isLegalRoutes(routesForMail_4)) {
            mail_4 = new Mail(4, auckland, sydney, 500, 100, Priority.International_Air, routesForMail_4);
            mails.add(mail_4);
        }

        List<Route> routesForMail_5 = routingSystem.findRoutes(christchurch, auckland, Priority.Domestic_Standard);
        if (isLegalRoutes(routesForMail_5)) {
            mail_5 = new Mail(5, christchurch, auckland, 8800, 8800, Priority.Domestic_Standard, routesForMail_5);
            mails.add(mail_5);
        }

        // 5. Calculate Business figures
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
    private boolean isLegalRoutes(List<Route> routes) {
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
}
