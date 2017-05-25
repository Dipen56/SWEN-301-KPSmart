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

    private static final String USER_PATH = "KPSSoftware/resources/xml/output.xml";

    public static List<Staff> logins;
    public List<Event> eventLogFile;


    public static List<Staff> getLogins() {
        return logins;
    }

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

    public static boolean loadUsers(){
        try {
            File inputFile = new File(USER_PATH);
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputFile);

            Element classElement = document.getRootElement();

            List<Node> clerkNodes = document.selectNodes("/users/clerk" );

            System.out.println("loading clerks---------------------------");

            for (Node node : clerkNodes) {
                int uid = Integer.parseInt(node.valueOf("@uid"));
                String name = node.valueOf("@name");
                String password = node.valueOf("@password");

                logins.add(new Clerk(uid, name , password));
            }

            List<Node> managerNodes = document.selectNodes("/users/manager" );

            System.out.println("loading managers----------------------------");

            for (Node node : managerNodes) {
                int uid = Integer.parseInt(node.valueOf("@uid"));
                String name = node.valueOf("@name");
                String password = node.valueOf("@password");

                logins.add(new Manager(uid, name , password));
            }

            System.out.println("Num users loaded: " + logins.size());

        } catch (DocumentException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean saveUsers() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "users" );

        int clerkCount = 0, managerCount = 0;

        for(Staff employee : logins){
            if(employee instanceof Clerk){
                clerkCount++;
                Element a1 = root.addElement( "clerk" )
                        .addAttribute( "uid", Integer.toString(employee.getUID()) )
                        .addAttribute( "name", employee.getUserName() )
                        .addAttribute( "password", employee.getPassword() );
            } else {
                managerCount++;
                Element a1 = root.addElement( "manager" )
                        .addAttribute( "uid", Integer.toString(employee.getUID()) )
                        .addAttribute( "name", employee.getUserName() )
                        .addAttribute( "password", employee.getPassword() );
            }
        }

        try {
            XMLWriter writer;
            OutputFormat format = OutputFormat.createPrettyPrint();
            writer = new XMLWriter( new FileWriter(USER_PATH), format );
            writer.write( document );
            writer.close();

            System.out.println(clerkCount + " clerk logins saved");
            System.out.println(managerCount + " manager logins saved");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
