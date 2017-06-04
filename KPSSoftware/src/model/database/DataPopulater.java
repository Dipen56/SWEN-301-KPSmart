package model.database;

import model.location.Location;
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
    private static final String USER_PATH = DataPopulater.class.getResource("/xml/preload-data.xml").getPath();
    private static List<Location> locations = new ArrayList<>();
    private static List<Route> routes = new ArrayList<>();
    private static List<TransportFirm> transportFirms= new ArrayList<>();


    /**
     * Saves user data back to user-login file
     *
     * @return true if saved successfully
     */
    public static boolean saveLocations() {
        // create new document
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("locations");
        // count number of clerks and managers processed
        int internationalCount = 0, domisticCount = 0;
        // process all employees
        for (Location location : locations) {
            if (location instanceof NZLocation) {
                internationalCount++;
                // saving a clerk
                Element a1 = root.addElement("NZLocation")
                        .addAttribute("locationId", Integer.toString(location.getId()))
                        .addAttribute("name", employee.getUserName())
                        .addAttribute("password", employee.getPassword());
                a1.addElement("first")
                        .addText(employee.getFirstName());
                a1.addElement("last")
                        .addText(employee.getLastName());
                a1.addElement("email")
                        .addText(employee.getEmail());
                a1.addElement("phone")
                        .addText(employee.getPhoneNumber());
            } else {
                managerCount++;
                // saving a manager
                Element a1 = root.addElement("manager")
                        .addAttribute("uid", Integer.toString(employee.getUID()))
                        .addAttribute("name", employee.getUserName())
                        .addAttribute("password", employee.getPassword());
                a1.addElement("first")
                        .addText(employee.getFirstName());
                a1.addElement("last")
                        .addText(employee.getLastName());
                a1.addElement("email")
                        .addText(employee.getEmail());
                a1.addElement("phone")
                        .addText(employee.getPhoneNumber());
            }
        }
        // writing user-logins document
        try {
            // creating XML writer
            XMLWriter writer;
            // format and write document to file
            OutputFormat format = OutputFormat.createPrettyPrint();
            writer = new XMLWriter(new FileWriter(USER_PATH), format);
            writer.write(document);
            // close writer
            writer.close();
            // output for testing

            System.out.println("Successfully saved: " + (clerkCount + managerCount) + " user logins");
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
