package model.database;

import model.event.Event;
import model.location.InternationalLocation;
import model.location.Location;
import model.location.NZCity;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;
import model.route.Route;
import model.route.RouteType;
import model.staff.Staff;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent a XML writer/reader.
 *
 * @author Hector
 * @version 2017/6/5
 */
public class XMLDriver {

    /**
     * The system-independent path of where this programme is executed.
     */
    private static final String RUNTIME_PATH = System.getProperty("user.dir").replace("\\", "/");

    /**
     * The folder name of where all xml resource files are stored
     */
    private static final String RESOURCE_XML_FOLDER_PATH = "/xml";

    /**
     * The file name to write "events.xml"
     */
    private static final String EVENT_XML_FILE_NAME = "/events.xml";

    /**
     * The file name to write "locations.xml"
     */
    private static final String LOCATION_XML_FILE_NAME = "/locations.xml";

    /**
     * The file name to write "mails.xml"
     */
    private static final String MAIL_XML_FILE_NAME = "/mails.xml";

    /**
     * The file name to write "routes.xml"
     */
    private static final String ROUTE_XML_FILE_NAME = "/routes.xml";

    /**
     * The file name to write "staffs.xml"
     */
    private static final String STAFF_XML_FILE_NAME = "/staffs.xml";

    /**
     * The file path to write "locations.xml"
     */
    private static final String LOCATION_XML_WRITE_PATH = RUNTIME_PATH + LOCATION_XML_FILE_NAME;

    /**
     * The file path to write "staffs.xml"
     */
    private static final String STAFF_XML_WRITE_PATH = RUNTIME_PATH + STAFF_XML_FILE_NAME;

    /**
     * The file path to write "mails.xml"
     */
    private static final String MAIL_XML_WRITE_PATH = RUNTIME_PATH + MAIL_XML_FILE_NAME;

    /**
     * The file path to routes "routes.xml"
     */
    private static final String ROUTE_XML_WRITE_PATH = RUNTIME_PATH + ROUTE_XML_FILE_NAME;

    /**
     * The file path to routes "events.xml"
     */
    private static final String EVENT_XML_WRITE_PATH = RUNTIME_PATH + EVENT_XML_FILE_NAME;

    /**
     * A uniform format for writing all XML files
     */
    private static OutputFormat format = OutputFormat.createPrettyPrint();

    /**
     * The XML parser
     */
    private static SAXReader reader = new SAXReader();

    static {
        format.setEncoding("UTF-8");
    }

    /**
     * Private Constructor. This class should only be used as a provider of static methods.
     */
    private XMLDriver() {}

    // ===============================================================
    //                EVENTS
    // TODO: events functions are not done yet
    // ===============================================================

    public static boolean writeEvent(Event event) {
        return false;
    }

//    public static Map<Integer, Event> readEvents() {
//
//    }
//
//    public static Event readEventById(int eventId) {
//
//    }
//
//    public static int getMaxEventId() {
//
//    }

    // ===============================================================
    //                LOCATIONS
    // ===============================================================

    /**
     * Write the given Location object into "locations.xml"
     *
     * @param location
     * @return true if successful, or false if failed.
     */
    public static boolean writeLocation(Location location) {
        //  data of location
        String id = String.valueOf(location.id);
        String elementName = location.getClass().getSimpleName();
        String cityName = location.getLocationName();

        // read from XML file to get the existing locations
        Document document = readDocumentFrom(LOCATION_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new location node
        List<Element> locations = document.getRootElement().elements();

        Element newLocation = DocumentHelper.createElement(elementName);
        newLocation.addAttribute("id", id);
        attachChildNode(newLocation, "cityName", cityName);

        locations.add(newLocation);

        // write the DOM tree back into XML
        return writeDocumentTo(document, LOCATION_XML_WRITE_PATH);
    }

    /**
     * Read from XML file to get all recorded locations
     *
     * @return a Map of locations where the key is location id, and the value is the Location object
     */
    public static Map<Integer, Location> readLocations() {
        Map<Integer, Location> locations = new HashMap<>();

        // read from XML file to get the existing locations
        Document document = readDocumentFrom(LOCATION_XML_FILE_NAME);
        if (document == null) {
            return null;
        }

        // get all NZLocation nodes from XML and construct them into NZLocations
        List<Node> nzNodes = document.selectNodes("/Locations/NZLocation");
        nzNodes.forEach(node -> {
            int id = Integer.parseInt(node.valueOf("@id"));
            String cityName = node.valueOf("./cityName");
            NZCity nzCity = NZCity.createFromString(cityName);
            NZLocation nzLocation = new NZLocation(id, nzCity);

            locations.put(id, nzLocation);
        });

        // get all InternationalLocation nodes from XML and construct them into InternationalLocation
        List<Node> internationalNodes = document.selectNodes("/Locations/InternationalLocation");
        internationalNodes.forEach(node -> {
            int id = Integer.parseInt(node.valueOf("@id"));
            String cityName = node.valueOf("./cityName");
            InternationalLocation internationalLocation = new InternationalLocation(id, cityName);

            locations.put(id, internationalLocation);
        });

        return locations;
    }

    /**
     * Given a location ID, read the XML file, and find the Location with the given id.
     *
     * @param locationId
     * @return the Location object with the specified ID, or null if cannot find it.
     */
    public static Location readLocationById(int locationId) {
        Map<Integer, Location> locations = readLocations();

        if (locations == null) {
            return null;
        }

        return locations.get(locationId);
    }

    /**
     * @return the max ID of locations recorded in XML file.
     */
    public static int getMaxLocationId() {
        Document document = readDocumentFrom(LOCATION_XML_FILE_NAME);
        if (document == null) {
            return -1;
        }

        return Integer.parseInt(document.getRootElement().valueOf("@maxId"));
    }

    // ===============================================================
    //                MAILS
    // ===============================================================

    /**
     * Write the given Mail object into "mails.xml"
     *
     * @param mail
     * @return true if successful, or false if failed.
     */
    public static boolean writeMail(Mail mail) {
        //  metadata of Mail
        String elementName = "Mail";
        String id = String.valueOf(mail.id);
        String originId = String.valueOf(mail.getOrigin().id);
        String destinationId = String.valueOf(mail.getDestination().id);
        String weight = String.valueOf(mail.getWeight());
        String volume = String.valueOf(mail.getVolume());
        String priority = mail.getPriority().toString();

        // read from XML file to get the existing locations
        Document document = readDocumentFrom(MAIL_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new staff node
        List<Element> mails = document.getRootElement().elements();

        Element newMail = DocumentHelper.createElement(elementName);
        newMail.addAttribute("id", id);

        attachChildNode(newMail, "originId", originId);
        attachChildNode(newMail, "destinationId", destinationId);
        attachChildNode(newMail, "weight", weight);
        attachChildNode(newMail, "volume", volume);
        attachChildNode(newMail, "priority", priority);

        mails.add(newMail);

        // write the DOM tree back into XML
        return writeDocumentTo(document, MAIL_XML_WRITE_PATH);
    }

    /**
     * Read from XML file to get all recorded mails
     *
     * @return a Map of mails where the key is mail id, and the value is the Mail object
     */
    public static Map<Integer, Mail> readMails() {
        Map<Integer, Mail> mails = new HashMap<>();

        // read from XML file to get the existing mails
        Document document = readDocumentFrom(MAIL_XML_FILE_NAME);
        if (document == null) {
            return null;
        }

        // read all locations
        Map<Integer, Location> locations = readLocations();

        // get all staff nodes from XML and construct them into Staff objects
        List<Node> mailNodes = document.selectNodes("/Mails/Mail");
        mailNodes.forEach(node -> {
            int id = Integer.parseInt(node.valueOf("@id"));
            NZLocation origin = (NZLocation) locations.get(Integer.parseInt(node.valueOf("./originId")));
            Location destination = locations.get(Integer.parseInt(node.valueOf("./destinationId")));
            float weight = Float.parseFloat(node.valueOf("./weight"));
            float volume = Float.parseFloat(node.valueOf("./volume"));
            Priority priority = Priority.createPriorityFrom(node.valueOf("./priority"));

            Mail mail = new Mail(id, origin, destination, weight, volume, priority);
            mails.put(id, mail);
        });

        return mails;
    }

    /**
     * Given a mail ID, read the XML file, and find the Mail with the given id.
     *
     * @param mailId
     * @return the Mail object with the specified ID, or null if cannot find it.
     */
    public static Mail readMailById(int mailId) {
        Map<Integer, Mail> mails = readMails();

        if (mails == null) {
            return null;
        }

        return mails.get(mailId);
    }

    /**
     * @return the max ID of mails recorded in XML file.
     */
    public static int getMaxMailId() {
        Document document = readDocumentFrom(MAIL_XML_FILE_NAME);
        if (document == null) {
            return -1;
        }

        return Integer.parseInt(document.getRootElement().valueOf("@maxId"));
    }

    // ===============================================================
    //                ROUTES
    // ===============================================================

    /**
     * Write the given Route object into "routes.xml"
     *
     * @param route
     * @return true if successful, or false if failed.
     */
    public static boolean writeRoute(Route route) {
        //  metadata of route
        String elementName = "Route";
        String id = String.valueOf(route.id);
        String routeType = route.routeType.toString();
        String startId = String.valueOf(route.getStartLocation().id);
        String endId = String.valueOf(route.getEndLocation().id);
        String duration = String.valueOf(route.getDuration());
        String transportFirm = route.getTransportFirm();
        String pricePerGram = String.valueOf(route.getPricePerGram());
        String pricePerVolume = String.valueOf(route.getCostPerVolume());
        String costPerGram = String.valueOf(route.getCostPerGram());
        String costPerVolume = String.valueOf(route.getCostPerVolume());

        // read from XML file to get the existing locations
        Document document = readDocumentFrom(ROUTE_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new route node
        List<Element> routes = document.getRootElement().elements();

        Element newRoute = DocumentHelper.createElement(elementName);
        newRoute.addAttribute("id", id);

        attachChildNode(newRoute, "routeType", routeType);
        attachChildNode(newRoute, "startId", startId);
        attachChildNode(newRoute, "endId", endId);
        attachChildNode(newRoute, "duration", duration);
        attachChildNode(newRoute, "transportFirm", transportFirm);
        attachChildNode(newRoute, "pricePerGram", pricePerGram);
        attachChildNode(newRoute, "pricePerVolume", pricePerVolume);
        attachChildNode(newRoute, "costPerGram", costPerGram);
        attachChildNode(newRoute, "costPerVolume", costPerVolume);

        routes.add(newRoute);

        // write the DOM tree back into XML
        return writeDocumentTo(document, ROUTE_XML_WRITE_PATH);
    }

    /**
     * Read from XML file to get all recorded routes
     *
     * @return a Map of routes where the key is route id, and the value is the Route object
     */
    public static Map<Integer, Route> readRoutes() {
        Map<Integer, Route> routes = new HashMap<>();

        // read from XML file to get the existing staffs
        Document document = readDocumentFrom(ROUTE_XML_FILE_NAME);
        if (document == null) {
            return null;
        }

        // read all locations
        Map<Integer, Location> locations = readLocations();

        // get all route nodes from XML and construct them into Route objects
        List<Node> routeNodes = document.selectNodes("/Routes/Route");
        routeNodes.forEach(node -> {
            int id = Integer.parseInt(node.valueOf("@id"));
            RouteType routeType = RouteType.createFromString(node.valueOf("./routeType"));
            Location start = locations.get(Integer.parseInt(node.valueOf("./startId")));
            Location end = locations.get(Integer.parseInt(node.valueOf("./endId")));
            float duration = Float.parseFloat(node.valueOf("./duration"));
            String transportFirm = node.valueOf("./transportFirm");
            float pricePerGram = Float.parseFloat(node.valueOf("./pricePerGram"));
            float pricePerVolume = Float.parseFloat(node.valueOf("./pricePerVolume"));
            float costPerGram = Float.parseFloat(node.valueOf("./costPerGram"));
            float costPerVolume = Float.parseFloat(node.valueOf("./costPerVolume"));

            Route route = new Route(id, start, end, routeType, duration, transportFirm, pricePerGram, pricePerVolume, costPerGram, costPerVolume);

            routes.put(id, route);
        });

        return routes;
    }

    /**
     * Given a route ID, read the XML file, and find the Route with the given id.
     *
     * @param routeId
     * @return the Route object with the specified ID, or null if cannot find it.
     */
    public static Route readRouteById(int routeId) {
        Map<Integer, Route> routes = readRoutes();

        if (routes == null) {
            return null;
        }

        return routes.get(routeId);
    }

    /**
     * @return the max ID of routes recorded in XML file.
     */
    public static int getMaxRouteId() {
        Document document = readDocumentFrom(ROUTE_XML_FILE_NAME);
        if (document == null) {
            return -1;
        }

        return Integer.parseInt(document.getRootElement().valueOf("@maxId"));
    }

    // ===============================================================
    //                STAFFS
    // ===============================================================

    /**
     * Write the given Staff object into "staffs.xml"
     *
     * @param staff
     * @return true if successful, or false if failed.
     */
    public static boolean writeStaff(Staff staff) {
        //  metadata of staff
        String elementName = "Staff";
        String id = String.valueOf(staff.id);
        String userName = staff.getUserName();
        String password = staff.getPassword();
        String firstName = staff.getFirstName();
        String lastName = staff.getLastName();
        String email = staff.getEmail();
        String phoneNumber = staff.getPhoneNumber();
        String isManager = String.valueOf(staff.isManager());

        // read from XML file to get the existing locations
        Document document = readDocumentFrom(STAFF_XML_FILE_NAME);
        if (document == null) {
            return false;
        }

        // Update the maxID
        document.getRootElement().addAttribute("maxId", id);

        // create the new staff node
        List<Element> staffs = document.getRootElement().elements();

        Element newStaff = DocumentHelper.createElement(elementName);
        newStaff.addAttribute("id", id);

        attachChildNode(newStaff, "userName", userName);
        attachChildNode(newStaff, "password", password);
        attachChildNode(newStaff, "firstName", firstName);
        attachChildNode(newStaff, "lastName", lastName);
        attachChildNode(newStaff, "email", email);
        attachChildNode(newStaff, "phoneNumber", phoneNumber);
        attachChildNode(newStaff, "isManager", isManager);

        staffs.add(newStaff);

        // write the DOM tree back into XML
        return writeDocumentTo(document, STAFF_XML_WRITE_PATH);
    }

    /**
     * Read from XML file to get all recorded staffs
     *
     * @return a Map of staffs where the key is staff id, and the value is the Staff object
     */
    public static Map<Integer, Staff> readStaffs() {
        Map<Integer, Staff> staffs = new HashMap<>();

        // read from XML file to get the existing staffs
        Document document = readDocumentFrom(STAFF_XML_FILE_NAME);
        if (document == null) {
            return null;
        }

        // get all staff nodes from XML and construct them into Staff objects
        List<Node> staffNodes = document.selectNodes("/Staffs/Staff");
        staffNodes.forEach(node -> {
            int id = Integer.parseInt(node.valueOf("@id"));
            String userName = node.valueOf("./userName");
            String password = node.valueOf("./password");
            String firstName = node.valueOf("./firstName");
            String lastName = node.valueOf("./lastName");
            String email = node.valueOf("./email");
            String phoneNumber = node.valueOf("./phoneNumber");
            Boolean isManager = Boolean.valueOf(node.valueOf("./isManager"));

            Staff staff = new Staff(id, userName, password, isManager);
            staff.setFirstName(firstName);
            staff.setLastName(lastName);
            staff.setEmail(email);
            staff.setPhoneNumber(phoneNumber);

            staffs.put(id, staff);
        });

        return staffs;
    }

    /**
     * Given a staff ID, read the XML file, and find the Staff with the given id.
     *
     * @param staffId
     * @return the Staff object with the specified ID, or null if cannot find it.
     */
    public static Staff readStaffById(int staffId) {
        Map<Integer, Staff> staffs = readStaffs();

        if (staffs == null) {
            return null;
        }

        return staffs.get(staffId);
    }

    /**
     * @return the max ID of staffs recorded in XML file.
     */
    public static int getMaxStaffId() {
        Document document = readDocumentFrom(STAFF_XML_FILE_NAME);
        if (document == null) {
            return -1;
        }

        return Integer.parseInt(document.getRootElement().valueOf("@maxId"));
    }

    // ===============================================================
    //                PRIVATE METHODS
    // ===============================================================

    /**
     * Read the XML file with the given file name
     *
     * @param fileName
     * @return the parsed Document object
     */
    private static Document readDocumentFrom(String fileName) {
        String filePath;

        if (Files.exists(Paths.get(RUNTIME_PATH + fileName))) {
            filePath = RUNTIME_PATH + fileName;
        } else {
            filePath = RESOURCE_XML_FOLDER_PATH + fileName;
        }

        try {
            // if the given file exist in programme path, read it; otherwise read from resource.
            if (filePath.startsWith("/")) {
                return reader.read(DataPopulater.class.getResource(filePath));
            } else {
                return reader.read(new File(filePath));
            }
        } catch (DocumentException e) {
            logError(e);
            return null;
        }
    }

    /**
     * Write the given Document object into file, using the given file path.
     *
     * @param document
     * @param path
     * @return true if successful, or false if failed.
     */
    private static boolean writeDocumentTo(Document document, String path) {
        try {
            XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(path)), format);
            writer.write(document);
            writer.close();

            return true;
        } catch (IOException e) {
            logError(e);
            return false;
        }
    }

    /**
     * Attach a child node whose name is <i>childNodeName</i> and inner text is <i>childNodeText</i>, to the
     * <i>parentNode</i>.
     *
     * @param parentNode
     * @param childNodeName
     * @param childNodeText
     */
    private static void attachChildNode(Element parentNode, String childNodeName, String childNodeText) {
        Element childNode = DocumentHelper.createElement(childNodeName);

        if (childNodeText != null) {
            childNode.setText(childNodeText);
        }

        parentNode.add(childNode);
    }

    /**
     * A helper method to log error into console.
     *
     * @param e
     */
    private static void logError(Exception e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
    }
}
