package model.database;

import model.event.*;
import model.location.InternationalLocation;
import model.location.NZCity;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;
import model.route.Route;
import model.route.RouteType;
import model.staff.Staff;

import java.time.LocalDateTime;

/**
 * This class is used to populate the initial data, so the programme won't have to be demonstrated with blank data. this
 * class is used to load the data need for the program to run.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class DataPopulater {

    public DataPopulater() {
        populateStaffs();
        populateLocationsAndRoutesAndMails();
        populateEvents();
    }

    private void populateStaffs() {
        Staff staff_1 = new Staff(1, "John", "test123", false, "John",
                "Smith", "jsmith@email.com", "123 456 7890");
        Staff staff_2 = new Staff(2, "Bob", "test123", true, "Bob",
                "Jones", "bjones@email.com", "123 456 7890");
        Staff staff_3 = new Staff(3, "Steve", "test123", false, "Steve",
                "Jobs", "sjobs@email.com", "123 456 7890");
        Staff staff_4 = new Staff(4, "123", "123", true, "John",
                "Doe", "jd@email.com", "123 456 7890");

        XMLDriver.writeStaff(staff_1);
        XMLDriver.writeStaff(staff_2);
        XMLDriver.writeStaff(staff_3);
        XMLDriver.writeStaff(staff_4);
    }

    private void populateLocationsAndRoutesAndMails() {

        // ================= populate locations ============================

        NZLocation auckland = new NZLocation(1, NZCity.Auckland);
        NZLocation hamilton = new NZLocation(2, NZCity.Hamilton);
        NZLocation rotorua = new NZLocation(3, NZCity.Rotorua);
        NZLocation parmerstonNorth = new NZLocation(4, NZCity.Palmerston_North);
        NZLocation wellington = new NZLocation(5, NZCity.Wellington);
        NZLocation christchurch = new NZLocation(6, NZCity.Christchurch);
        NZLocation dunedin = new NZLocation(7, NZCity.Dunedin);

        InternationalLocation sydney = new InternationalLocation(8, "Sydney");
        InternationalLocation singapore = new InternationalLocation(9, "Singapore");
        InternationalLocation hongkong = new InternationalLocation(10, "HongKong");
        InternationalLocation guangzhou = new InternationalLocation(11, "GuangZhou");
        InternationalLocation moscow = new InternationalLocation(12, "Moscow");

        XMLDriver.writeLocation(auckland);
        XMLDriver.writeLocation(hamilton);
        XMLDriver.writeLocation(rotorua);
        XMLDriver.writeLocation(parmerstonNorth);
        XMLDriver.writeLocation(wellington);
        XMLDriver.writeLocation(christchurch);
        XMLDriver.writeLocation(dunedin);
        XMLDriver.writeLocation(sydney);
        XMLDriver.writeLocation(singapore);
        XMLDriver.writeLocation(hongkong);
        XMLDriver.writeLocation(guangzhou);
        XMLDriver.writeLocation(moscow);

        // ================= populate routes ============================

        Route rout_1 = new Route(1, dunedin, christchurch, RouteType.Land, 4.0f, "RoyalPost", 1.2f, 0.7f, 1.0f, 0.5f);
        Route rout_2 = new Route(2, dunedin, christchurch, RouteType.Land, 3.2f, "NZFast", 1.4f, 0.9f, 1.1f, 0.6f);
        Route rout_3 = new Route(3, christchurch, wellington, RouteType.Air, 1.2f, "RoyalPost", 1.9f, 1.3f, 1.6f, 0.9f);
        Route rout_4 = new Route(4, christchurch, wellington, RouteType.Air, 1.1f, "NZFast", 1.8f, 1.3f, 1.7f, 1.0f);
        Route rout_5 = new Route(5, dunedin, auckland, RouteType.Air, 4.5f, "NZFast", 3.9f, 4.0f, 3.2f, 3.5f);
        Route rout_6 = new Route(6, christchurch, auckland, RouteType.Land, 4.9f, "RoyalPost", 2.9f, 3.2f, 2.5f, 3.0f);
        Route rout_7 = new Route(7, auckland, sydney, RouteType.Air, 3.0f, "FedEx", 6.6f, 6.2f, 5.5f, 5.0f);
        Route rout_8 = new Route(8, auckland, sydney, RouteType.Sea, 7.5f, "PacificaMails", 4.0f, 3.5f, 3.5f, 3.0f);
        Route rout_9 = new Route(9, auckland, singapore, RouteType.Sea, 10.5f, "PacificaMails", 4.8f, 4.6f, 4.5f, 4.2f);
        Route rout_10 = new Route(10, sydney, hongkong, RouteType.Air, 4.8f, "FedEx", 8.8f, 7.6f, 6.5f, 6.5f);
        Route rout_11 = new Route(11, singapore, guangzhou, RouteType.Sea, 4, "PacificaMails", 3.2f, 3.7f, 3.0f, 3.2f);
        Route rout_12 = new Route(12, hongkong, guangzhou, RouteType.Sea, 0.5f, "FedEx", 1.2f, 0.7f, 1.0f, 0.5f);
        Route rout_13 = new Route(13, guangzhou, moscow, RouteType.Land, 14.0f, "FedEx", 9.6f, 9.9f, 8.5f, 8.5f);
        Route rout_14 = new Route(14, guangzhou, moscow, RouteType.Air, 5.5f, "FedEx", 13.5f, 14.5f, 12.0f, 12.5f);

        XMLDriver.writeRoute(rout_1);
        XMLDriver.writeRoute(rout_2);
        XMLDriver.writeRoute(rout_3);
        XMLDriver.writeRoute(rout_4);
        XMLDriver.writeRoute(rout_5);
        XMLDriver.writeRoute(rout_6);
        XMLDriver.writeRoute(rout_7);
        XMLDriver.writeRoute(rout_8);
        XMLDriver.writeRoute(rout_9);
        XMLDriver.writeRoute(rout_10);
        XMLDriver.writeRoute(rout_11);
        XMLDriver.writeRoute(rout_12);
        XMLDriver.writeRoute(rout_13);
        XMLDriver.writeRoute(rout_14);

        // ================= populate mails ============================

        Mail mail_1 = new Mail(1, dunedin, auckland, 2500, 3000, Priority.Domestic_Standard);
        Mail mail_2 = new Mail(2, dunedin, moscow, 6200, 30000, Priority.International_Standard);
        Mail mail_3 = new Mail(3, dunedin, hongkong, 99000, 10000, Priority.International_Air);
        Mail mail_4 = new Mail(4, auckland, sydney, 500, 100, Priority.International_Air);
        Mail mail_5 = new Mail(5, christchurch, auckland, 8800, 8800, Priority.Domestic_Standard);

        XMLDriver.writeMail(mail_1);
        XMLDriver.writeMail(mail_2);
        XMLDriver.writeMail(mail_3);
        XMLDriver.writeMail(mail_4);
        XMLDriver.writeMail(mail_5);
    }

    private void populateEvents() {
        CustomerPriceUpdateEvent cpuEvent = new CustomerPriceUpdateEvent(1, 1, LocalDateTime.now(), 1, 1.0f, 0.5f, 1.2f, 0.7f);
        TransportCostUpdateEvent tcuEvent = new TransportCostUpdateEvent(2, 2, LocalDateTime.now(), 2, 1.2f, 0.8f, 1.1f, 0.6f);
        MailDeliveryEvent mdEvent = new MailDeliveryEvent(3, 1, LocalDateTime.now(), 1);
        RouteAdditionEvent raEvent = new RouteAdditionEvent(4, 1, LocalDateTime.now(), 14);
        RouteDeactivationEvent rdEvent = new RouteDeactivationEvent(5, 1, LocalDateTime.now(), 3);

        XMLDriver.writeCustomerPriceUpdateEvent(cpuEvent);
        XMLDriver.writeTransportCostUpdateEvent(tcuEvent);
        XMLDriver.writeMailDeliveryEvent(mdEvent);
        XMLDriver.writeRouteAdditionEvent(raEvent);
        XMLDriver.writeRouteDeactivationEvent(rdEvent);
    }

//    public static boolean addLocations(String lccationName, boolean isInternational) {
//        if (isInternational) {
//            Location location = new InternationalLocation(locations.size() + 1, lccationName);
//            return locations.add(location);
//        } else {
//
//            Location location = new NZLocation(locations.size() + 1, NZCity.Auckland);
//            return locations.add(location);
//        }
//    }

//    /**
//     * loads the data required for the application to funcation.
//     *
//     * @return true if loaded successfully
//     */
//    private static boolean loadLocations() {
//
//        try {
//            locations.clear();
//            // Get and read file
//            SAXReader reader = new SAXReader();
//            //FIXME: this path need to be dynamicly made after fixing the save because at the mement the save will not save to the right place.
//            ///xml/preload.xml is the file i created manaually and copyed the data over from the original file which is being saved to thw root atm
//
//            Document document = reader.read(DataPopulater.class.getResource("/xml/preload.xml"));
//
//
//            // get root node
//            Element classElement = document.getRootElement();
//            // get all nzLocationNodes nodes
//
//            List<Node> nzLocationNodes = document.selectNodes("/locations/NZLocation");
//            // load all nzLocations
//
//            for (Node node : nzLocationNodes) {
//                int locationId = Integer.parseInt(node.valueOf("@locationId"));
//                String name = node.valueOf("@locationName");
//                // creating location to add to list of all Locations
//                Location current = new NZLocation(locationId, NZCity.valueOf(name));
//                // iterate over stored info and update location
//                locations.add(current);
//            }
//
//            // get all InternationalLocation nodes
//            List<Node> internationalLocationNode = document.selectNodes("/locations/InternationalLocation");
//            // load all InternationalLocation locations
//            for (Node node : internationalLocationNode) {
//                int locationId = Integer.parseInt(node.valueOf("@locationId"));
//                String name = node.valueOf("@locationName");
//                // creating location to add to list of all locaitons
//                Location current = new InternationalLocation(locationId, name);
//                // iterate over stored info and update locations
//                // adding InternationalLocation to list of all location
//                locations.add(current);
//            }
//            // output for testing
//            System.out.println("Successfully loaded: " + locations.size() + " locations");
//        } catch (DocumentException e) {
//            return false;
//        }
//        return true;
//    }

//    /**
//     * Saves the predate need for the application to funcation.
//     *
//     * @return true if saved successfully
//     */
//    public static boolean saveLocations() {
//
//        // create new document
//        Document document = DocumentHelper.createDocument();
//        Element root = document.addElement("locations");
//        // count number of Domistic and international locations.
//        int internationalCount = 0, domisticCount = 0;
//        // process all locations
//        for (Location location : locations) {
//            if (location instanceof NZLocation) {
//                domisticCount++;
//                // saving a NZLocation
//                Element a1 = root.addElement("NZLocation")
//                        .addAttribute("locationId", Integer.toString(location.id))
//                        .addAttribute("locationName", location.getLocationName());
//            } else {
//                internationalCount++;
//                // saving a InternationalLocation
//                Element a1 = root.addElement("InternationalLocation")
//                        .addAttribute("locationId", Integer.toString(location.id))
//                        .addAttribute("locationName", location.getLocationName());
//            }
//        }
//        // writing locations document
//        try {
//            OutputFormat format = OutputFormat.createPrettyPrint();
//            // FIXME: 5/06/2017 can't get this to save to the right location it creating and saving to the root folder but needs to be saved in /xml/preload-data.xml.
//            XMLWriter writer = new XMLWriter(new FileWriter("perload-data.xml"), format);
//            writer.write(document);
//            writer.close();
//            // output for testing
//            System.out.println("Successfully saved: " + (internationalCount + domisticCount) + " Locations");
//        } catch (IOException e) {
//            return false;
//        }
//        return true;
//    }
}
