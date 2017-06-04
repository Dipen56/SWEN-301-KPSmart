package model.database;

import model.location.InternationalLocation;
import model.location.Location;
import model.location.NZCity;
import model.location.NZLocation;
import model.route.Route;
import model.staff.Clerk;
import model.staff.Manager;
import model.staff.Staff;
import model.transportFirm.TransportFirm;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is used to populate the initial data, so the programme won't have to be demonstrated with blank data. this class is used to load the data need for the program to run.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class DataPopulater {
    private static final String USER_PATH = "KPSSoftware/resources/xml/preload-data.xml";
    private static List<Location> locations = new ArrayList<>();
    private static List<Route> routes = new ArrayList<>();
    private static List<TransportFirm> transportFirms = new ArrayList<>();

    public DataPopulater() {
        grenrateLcoations();
        saveLocations();
        loadLocations();
    }

    public static boolean addLocations(String lccationName, boolean isInternational) {
        if (isInternational) {
            Location location = new InternationalLocation(locations.size() + 1, lccationName);
            return locations.add(location);
        } else {

            Location location = new NZLocation(locations.size() + 1, NZCity.Auckland);
            return locations.add(location);
        }
    }

    /**
     * loads the data required for the application to funcation.
     *
     * @return true if loaded successfully
     */
    private static boolean loadLocations() {

        try {
            locations.clear();
            // Get and read file
            SAXReader reader = new SAXReader();
            //FIXME: this path need to be dynamicly made after fixing the save because at the mement the save will not save to the right place.
            ///xml/preload.xml is the file i created manaually and copyed the data over from the original file which is being saved to thw root atm

            Document document = reader.read(DataPopulater.class.getResource("/xml/preload.xml"));


            // get root node
            Element classElement = document.getRootElement();
            // get all nzLocationNodes nodes

            List<Node> nzLocationNodes = document.selectNodes("/locations/NZLocation");
            // load all nzLocations

            for (Node node : nzLocationNodes) {
                int locationId = Integer.parseInt(node.valueOf("@locationId"));
                String name = node.valueOf("@locationName");
                // creating location to add to list of all Locations
                Location current = new NZLocation(locationId, NZCity.valueOf(name));
                // iterate over stored info and update location
                locations.add(current);
            }

            // get all InternationalLocation nodes
            List<Node> internationalLocationNode = document.selectNodes("/locations/InternationalLocation");
            // load all InternationalLocation locations
            for (Node node : internationalLocationNode) {
                int locationId = Integer.parseInt(node.valueOf("@locationId"));
                String name = node.valueOf("@locationName");
                // creating location to add to list of all locaitons
                Location current = new InternationalLocation(locationId, name);
                // iterate over stored info and update locations
                // adding InternationalLocation to list of all location
                locations.add(current);
            }
            // output for testing
            System.out.println("Successfully loaded: " + locations.size() + " locations");
        } catch (DocumentException e) {
            return false;
        }
        return true;
    }

    /**
     * Saves the predate need for the application to funcation.
     *
     * @return true if saved successfully
     */
    public static boolean saveLocations() {

        // create new document
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("locations");
        // count number of Domistic and international locations.
        int internationalCount = 0, domisticCount = 0;
        // process all locations
        for (Location location : locations) {
            if (location instanceof NZLocation) {
                domisticCount++;
                // saving a NZLocation
                Element a1 = root.addElement("NZLocation")
                        .addAttribute("locationId", Integer.toString(location.id))
                        .addAttribute("locationName", location.getLocationName());
            } else {
                internationalCount++;
                // saving a InternationalLocation
                Element a1 = root.addElement("InternationalLocation")
                        .addAttribute("locationId", Integer.toString(location.id))
                        .addAttribute("locationName", location.getLocationName());
            }
        }
        // writing locations document
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            // FIXME: 5/06/2017 can't get this to save to the right location it creating and saving to the root folder but needs to be saved in /xml/preload-data.xml.
            XMLWriter writer = new XMLWriter(new FileWriter("perload-data.xml"), format);
            writer.write(document);
            writer.close();
            // output for testing
            System.out.println("Successfully saved: " + (internationalCount + domisticCount) + " Locations");
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    private static void genrateTransportCompanys(){

    }

    /**
     * this is a helper method that's used to create the initial data need for saving. This method is only required when
     * new data need to be generated due no previous xml file being present.
     */
    private static void grenrateLcoations() {
        addLocations("Wellington", false);
        addLocations("Auckland", false);
        addLocations("Christchurch", false);
        addLocations("Dunedin", false);
        addLocations("Hamilton", false);
        addLocations("Palmerston_North", false);
        addLocations("Rotorua", false);

        addLocations("Sydney", true);
        addLocations("Singapore", true);
        addLocations("HongKong", true);
        addLocations("GuangZhou", true);
        addLocations("Moscow", true);
        addLocations("Mumbai", true);
        addLocations("NewYork", true);
        addLocations("London", true);
    }

}
