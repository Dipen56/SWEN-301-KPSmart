package model.database;

import model.location.InternationalLocation;
import model.location.Location;
import model.location.NZCity;
import model.location.NZLocation;
import model.route.Route;
import model.staff.Clerk;
import model.staff.Staff;
import model.transportFirm.TransportFirm;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to populate the initial data, so the programme won't have to be demonstrated with blank data. this class is used to load the data need for the program to run.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class DataPopulater {
    private static final String USER_PATH = "KPSSoftware/resources/xml/preload-data.xml";
    // private static final String USER_PATH = "/xml/preload-data.xml";
    private static List<Location> locations = new ArrayList<>();
    private static List<Route> routes = new ArrayList<>();
    private static List<TransportFirm> transportFirms = new ArrayList<>();


    public static boolean addLocations(String lccationName, boolean isInternational) {
        if (isInternational) {
            Location location = new InternationalLocation(locations.size() + 1, lccationName);
            return locations.add(location);
        } else {
            NZCity.valueOf(lccationName);
            Location location = new NZLocation(locations.size() + 1, NZCity.Auckland);
            return locations.add(location);
        }
    }

    /**
     * Saves user data back to user-login file
     *
     * @return true if saved successfully
     */
    public static boolean saveLocations() {

        // create new document
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("locations");
        // count number of Domistic and international locations.
        int internationalCount = 0, domisticCount = 0;
        // process all employees
        for (Location location : locations) {
            if (location instanceof NZLocation) {
                domisticCount++;
                // saving a clerk
                Element a1 = root.addElement("NZLocation")
                        .addAttribute("locationId", Integer.toString(location.id))
                        .addAttribute("locationName", location.getLocationName());
            } else {
                internationalCount++;
                // saving a manager
                Element a1 = root.addElement("InternationalLocation")
                        .addAttribute("locationId", Integer.toString(location.id))
                        .addAttribute("locationName", location.getLocationName());
            }
        }
        // writing locations document
        try {

            OutputFormat format = OutputFormat.createPrettyPrint();
// FIXME: 5/06/2017 can't get this to save to the right location it creating and saving to the rourte folder.
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

}
