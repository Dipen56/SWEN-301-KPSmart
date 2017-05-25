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
import java.util.Iterator;
import java.util.List;

/**
 * The KPSDatabase class handles the saving and loading of user
 *  data and event logging files along with keeping these files
 *  up to date with the system.
 */
public class KPSDatabase {

    // field storing the path to the user data file
    private static final String USER_PATH = "KPSSoftware/resources/xml/user-logins.xml";

    // field storing uid for new users
    private static int UID = 0;

    // field for the list of all staff logins
    private static List<Staff> logins;
    // field for the list of events
    private List<Event> eventLogFile;

    // returns the list of staff logins
    public static List<Staff> getLogins() {
        return logins;
    }

    /*
        KPSDATABASE CONSTRUCTOR
     */
    public KPSDatabase(){
        logins = new ArrayList<>();
        loadUsers();
    }

    // helper method *not used*
    public static Document parseXML(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    /**
     * Checks user name isnt taken and create a new user
     * @param name username
     * @param pass password
     * @param isManager true to add manager
     * @return false if user name is taken. true if new user successfully added
     */
    public static boolean addUser(String name, String pass, String firstName, String lastName,
                                  String email, String phone, boolean isManager){
        // check user name hasnt been taken
        for(Staff employee:logins) {
            if (employee.getUserName().equals(name)) {
                // return false if name already taken
                return false;
            }
        }
        // create new employee and add to logins
        if(isManager){
            Manager manager = new Manager(UID++, name, pass);
            manager.setFirstName(firstName);
            manager.setLastName(lastName);
            manager.setEmail(email);
            manager.setPhoneNumber(phone);
            return logins.add(manager);
        }else{
            Clerk clerk = new Clerk(UID++, name, pass);
            clerk.setFirstName(firstName);
            clerk.setLastName(lastName);
            clerk.setEmail(email);
            clerk.setPhoneNumber(phone);
            return logins.add(clerk);
        }
    }

    /**
     * Finds and removes a user from the database
     * @param name username
     * @param pass password
     * @return true if user successfully removed, false if user not found
     */
    public static boolean removeUser(String name, String pass){
        // finds and removes user
        for(Staff s: logins){
            if(s.getUserName().equals(name) && s.getPassword().equals(pass)){
                return logins.remove(s);
            }
        }
        // return false if cannot find user
        return false;
    }

    /**
     * Checks a given user name and password against all user logins
     * @param username username
     * @param pass password
     * @return true if name and password match known login
     */
    public static boolean checkLogin(String username, String pass){
        // checks all employee logins
        for(Staff employee:logins){
            if(employee.getUserName().equals(username) &&
                    employee.getPassword().equals(pass)){
                // return true if match found
                return true;
            }
        }
        // return false if no match found
        return false;
    }

    /**
     * Loads the user data from the user-logins file
     * @return true if loaded successfully
     */
    private static boolean loadUsers(){
        try {
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
                UID++;
                int uid = Integer.parseInt(node.valueOf("@uid"));
                String name = node.valueOf("@name");
                String password = node.valueOf("@password");
                // creating clerk to add to list of all users
                Clerk cur = new Clerk(uid, name , password);
                // iterate over stored info and update clerk
                Element element = (Element)node;
                Iterator<Element> itr = element.elementIterator();
                while(itr.hasNext()){
                    Element infoElement = (Element)itr.next();
                    switch(infoElement.getName()){
                        // first name
                        case "first":
                            cur.setFirstName(infoElement.getText());
                            break;
                        // last name
                        case "last":
                            cur.setLastName(infoElement.getText());
                            break;
                        case "email":
                            cur.setEmail(infoElement.getText());
                            break;
                        case "phone":
                            cur.setPhoneNumber(infoElement.getText());
                            break;
                    }
                }
                // adding clerk to list of all users
                logins.add(cur);
            }
            // get all manager nodes
            List<Node> managerNodes = document.selectNodes("/users/manager" );
            // load all manager users
            for (Node node : managerNodes) {
                UID++;
                int uid = Integer.parseInt(node.valueOf("@uid"));
                String name = node.valueOf("@name");
                String password = node.valueOf("@password");
                // creating manager to add to list of all users
                Manager cur = new Manager(uid, name , password);
                // iterate over stored info and update clerk
                Element element = (Element)node;
                Iterator<Element> itr = element.elementIterator();
                while(itr.hasNext()){
                    Element infoElement = (Element)itr.next();
                    switch(infoElement.getName()){
                        // first name
                        case "first":
                            cur.setFirstName(infoElement.getText());
                            break;
                        // last name
                        case "last":
                            cur.setLastName(infoElement.getText());
                            break;
                        case "email":
                            cur.setEmail(infoElement.getText());
                            break;
                        case "phone":
                            cur.setPhoneNumber(infoElement.getText());
                            break;
                    }
                }
                // adding manager to list of all users
                logins.add(cur);
            }
            // output for testing
            System.out.println("Successfully loaded: " + logins.size() + " user logins");
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
                Element a1 = root.addElement( "manager" )
                        .addAttribute( "uid", Integer.toString(employee.getUID()) )
                        .addAttribute( "name", employee.getUserName() )
                        .addAttribute( "password", employee.getPassword() );
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
            writer = new XMLWriter( new FileWriter(USER_PATH), format );
            writer.write( document );
            // close writer
            writer.close();
            // output for testing
            System.out.println("Successfully saved: " + (clerkCount+managerCount) + " user logins");
        } catch (IOException e) { return false; }
        return true;
    }

}
