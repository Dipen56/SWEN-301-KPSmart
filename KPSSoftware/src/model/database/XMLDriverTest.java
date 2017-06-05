package model.database;

import model.location.InternationalLocation;
import model.location.Location;
import model.location.NZCity;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;
import model.route.Route;
import model.route.RouteType;
import model.staff.Staff;

import java.util.Map;

/**
 * This class is to test the functionality of XMLDriver.
 *
 * @author Hector
 * @version 2017/6/5
 */
public class XMLDriverTest {

    public static void main(String[] args) {

        // ============= Locations ===========================

        // test reading from locations
        Map<Integer, Location> locations = XMLDriver.readLocations();
        locations.forEach((key, value) -> System.out.println("id: " + key + ", location: " + value));

        // test write into locations
        Location location = new InternationalLocation(55, "NewYork");
        XMLDriver.writeLocation(location);

        // test find the location by id
        XMLDriver.readLocationById(13);

        // max ID
        System.out.println(XMLDriver.getMaxStaffId());

        // ==================== Staffs ===============================

        // test reading from staffs
        Map<Integer, Staff> staffs = XMLDriver.readStaffs();
        staffs.forEach((key, value) -> System.out.println("id: " + key + ", staff: " + value));

        // test write into staffs
        Staff staff = new Staff(55, "123", "123", true);
        XMLDriver.writeStaff(staff);

        // test find the staff by id
        XMLDriver.readStaffById(2);

        // max ID
        System.out.println(XMLDriver.getMaxStaffId());


        // ==================== Mails ===============================

        // test reading from mails
        Map<Integer, Mail> mails = XMLDriver.readMails();
        mails.forEach((key, value) -> System.out.println("id: " + key + ", mail: " + value));

        // test write into staffs
        NZLocation dunedin = new NZLocation(7, NZCity.Dunedin);
        InternationalLocation moscow = new InternationalLocation(12, "Moscow");
        Mail mail = new Mail(7, dunedin, moscow, 6200, 30000, Priority.International_Standard);
        XMLDriver.writeMail(mail);

        // test find the staff by id
        System.out.println(XMLDriver.readMailById(7));

        // max ID
        System.out.println(XMLDriver.getMaxMailId());

        // ==================== Routes ===============================

        // test reading from mails
        Map<Integer, Route> routes = XMLDriver.readRoutes();
        routes.forEach((key, value) -> System.out.println("id: " + key + ", mail: " + value));

        // test write into staffs
        NZLocation auckland = new NZLocation(1, NZCity.Auckland);
        InternationalLocation sydney = new InternationalLocation(8, "Sydney");

        Route route = new Route(8, auckland, sydney, RouteType.Sea, 7.5f, "PacificaMails", 4.0f, 3.5f, 3.5f, 3.0f);
        XMLDriver.writeRoute(route);

        // test find the staff by id
        System.out.println(XMLDriver.readRouteById(8));

        // max ID
        System.out.println(XMLDriver.getMaxRouteId());
    }
}
