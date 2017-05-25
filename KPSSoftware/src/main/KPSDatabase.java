package main;

import model.event.Event;
import model.staff.Clerk;
import model.staff.Manager;
import model.staff.Staff;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick on 23-May-17.
 */
public class KPSDatabase {

    // field storing the path to the user data file
    private static final String USER_PATH = "KPSSoftware/resources/xml/output.xml";

    // field for the list of all staff logins
    public static List<Staff> logins;
    // field for the list of events
    public List<Event> eventLogFile;

    // returns the list of staff logins
    public static List<Staff> getLogins() {
        return logins;
    }

    /**
     * Checks a given user name and password against all user logins
     * @param username
     * @param pass
     * @return true if name and password match known login
     */
    public static boolean checkLogin(String username, String pass){
        for(Staff employee:logins){
            if(employee.getUserName().equals(username) &&
                    employee.getPassword().equals(pass)){
                return true;
            }
        }
        return false;
    }

    public KPSDatabase(){
        logins = new ArrayList<>();
    }

    public static Document parseXML(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    /**
     * Loads the user data from the user-logins file
     * @return true if loading successful
     */
    public static boolean loadUsers(){
        try {
            // clear all logins
            logins.clear();

            // Get and read file
            File inputFile = new File(USER_PATH);
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputFile);

            // get root node
            Element classElement = document.getRootElement();
            // get all clerk nodes
            List<Node> clerkNodes = document.selectNodes("/users/clerk" );
            // load all clerk users
            for (Node node : clerkNodes) {
                int uid = Integer.parseInt(node.valueOf("@uid"));
                String name = node.valueOf("@name");
                String password = node.valueOf("@password");
                // add clerk to list of all users
                logins.add(new Clerk(uid, name , password));
            }
            // get all manager nodes
            List<Node> managerNodes = document.selectNodes("/users/manager" );
            // load all manager users
            for (Node node : managerNodes) {
                int uid = Integer.parseInt(node.valueOf("@uid"));
                String name = node.valueOf("@name");
                String password = node.valueOf("@password");
                // add manager to list of all users
                logins.add(new Manager(uid, name , password));
            }
            // output for testing
            System.out.println("Num users loaded: " + logins.size());
        } catch (DocumentException e) { return false; }
        return true;
    }

    /**
     * Saves user data back to user-login file
     * @return true if saved successfully
     */
    public static boolean saveUsers() {
        // create new document
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "users" );
        // count number of clerks and managers processed
        int clerkCount = 0, managerCount = 0;
        // process all employees
        for(Staff employee : logins){
            if(employee instanceof Clerk){
                clerkCount++;
                // saving a clerk
                Element a1 = root.addElement( "clerk" )
                        .addAttribute( "uid", Integer.toString(employee.getUID()) )
                        .addAttribute( "name", employee.getUserName() )
                        .addAttribute( "password", employee.getPassword() );
            } else {
                managerCount++;
                // saving a manager
                Element a1 = root.addElement( "manager" )
                        .addAttribute( "uid", Integer.toString(employee.getUID()) )
                        .addAttribute( "name", employee.getUserName() )
                        .addAttribute( "password", employee.getPassword() );
            }
        }
        // writing user-logins document
        try {
            // creating XML writer
            XMLWriter writer;
            // format and write document to file
            OutputFormat format = OutputFormat.createPrettyPrint();
            writer = new XMLWriter( new FileWriter(USER_PATH), format );
            writer.write( document );
            // close writer
            writer.close();
            // output for testing
            System.out.println(clerkCount + " clerk logins saved");
            System.out.println(managerCount + " manager logins saved");
        } catch (IOException e) { return false; }
        return true;
    }

}
