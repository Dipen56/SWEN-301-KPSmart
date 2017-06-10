package main;

import controller.*;
import model.KPSModel;
import model.database.DataPopulater;
import model.event.Event;
import model.location.Location;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;
import model.route.RouteType;
import model.staff.Staff;
import view.GUI;

import java.util.Map;
import java.util.Set;

/**
 * This class is the top-level controller of the app. The main method of the programme is located in this class.
 *
 * @author Dipen
 * @version 18/04/2017
 */
public class KPSMain {

    // ================== Model objects ============================

    private KPSModel kpsModel;

    // ================== controller objects =======================
    private static LoginScreenController loginScreenController;
    private static HomeScreenController homeScreenController;
    private static UserSettingController userSettingController;
    private static ChangePasswordController changePasswordController;
    private static ManageUserController manageUserControllerl;
    private static AddNewUserController addNewUserController;
    private static SendMailScreenController sendMailScreenController;
    private static RouteDiscontinueScreenController routeDiscontinueScreenController;

    // ================== view objects =============================


    /**
     * Constructor
     */
    public KPSMain() {
        kpsModel = new KPSModel();

        LoginScreenController.setKPSMain(this);
        HomeScreenController.setKPSMain(this);
        UserSettingController.setKPSMain(this);
        ChangePasswordController.setKPSMain(this);
        ManageUserController.setKPSMain(this);
        AddNewUserController.setKPSMain(this);
        SendMailScreenController.setKPSMain(this);
        RouteDiscontinueScreenController.setKPSMain(this);
    }


    /**
     * =================================================================================================================
     * These methods made by Dipen
     * =================================================================================================================
     */

    public Staff getCurrentStaff() {
        return kpsModel.getCurrentStaff();
    }

    public Map<Integer, Staff> getAllUsers() {
        return kpsModel.getAllStaffs();
    }

    public boolean addNewUser(String userName, String password, boolean isManager, String firstName,
                              String lastName, String email, String phoneNumber) {
        return kpsModel.createNewStaff(userName, password, isManager, firstName, lastName, email, phoneNumber);
    }

    public boolean deleteUser(String firstName, String lastName) {

        /*
        TODO:  We should delete the user by its id, instead of by its firstname and lastname, because we don't enforce the uniqueness of firstname and lastname.
               The best way I see to fix this is: let the front end pass back the id of the selected user.
               Or, if we really don't care, we don't need to fix it. For demonstration, it's not likely to have
               two users with same firstname and lastname.  -- Hektar
         */

        for (Staff s : kpsModel.getAllStaffs().values()) {
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                return kpsModel.deleteStaff(s.id);
            }
        }

        return false;
    }


    public boolean updateStaffInformation(String firstName, String lastName, String newFirstName, String newLastName, String newEmail, String newPhone, boolean changeRole) {
        Staff tempStaff = null;

        for (Staff s : kpsModel.getAllStaffs().values()) {
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                tempStaff = s;
            }
        }

        if (tempStaff == null) {
            return false;
        }

        if (changeRole) {
            //if the user wants to change roles to manager
            kpsModel.deleteStaff(tempStaff.id);

            String fName = "".equals(newFirstName) ? tempStaff.getFirstName() : newFirstName;
            String LName = "".equals(newLastName) ? tempStaff.getLastName() : newLastName;
            String nMail = "".equals(newEmail) ? tempStaff.getEmail() : newEmail;
            String nPhone = "".equals(newPhone) ? tempStaff.getPhoneNumber() : newPhone;

            kpsModel.createNewStaff(tempStaff.getUserName(), tempStaff.getPassword(), true, fName, LName, nMail, nPhone);

            return true;

        } else {
            // if the user just want ot update an users information
            if (!"".equals(newFirstName)) {
                tempStaff.setFirstName(newFirstName);
            }
            if (!"".equals(newLastName)) {
                tempStaff.setLastName(newLastName);
            }
            if (!"".equals(newEmail)) {
                tempStaff.setEmail(newEmail);
            }
            if (!"".equals(newPhone)) {
                tempStaff.setPhoneNumber(newPhone);
            }
            return true;
        }

    }

    public Staff getSelectedUser(String firstName, String lastName) {
        for (Staff s : kpsModel.getAllStaffs().values()) {
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                return s;
            }
        }

        return null;
    }

    public String changeUserPassword(String oldPassword, String newPassword, String retypePassword) {
        return kpsModel.getCurrentStaff().editPassword(oldPassword, newPassword, retypePassword);
    }

    public boolean authenticateLogin(String username, String password) {
        return kpsModel.login(username, password);
    }

    public static void setLoginScreenController(Object controllers) {
        if (controllers instanceof LoginScreenController) {
            loginScreenController = (LoginScreenController) controllers;
        } else if (controllers instanceof HomeScreenController) {
            homeScreenController = (HomeScreenController) controllers;
        } else if (controllers instanceof UserSettingController) {
            userSettingController = (UserSettingController) controllers;
        } else if (controllers instanceof ChangePasswordController) {
            changePasswordController = (ChangePasswordController) controllers;
        } else if (controllers instanceof ManageUserController) {
            manageUserControllerl = (ManageUserController) controllers;
        } else if (controllers instanceof AddNewUserController) {
            addNewUserController = (AddNewUserController) controllers;
        } else if (controllers instanceof SendMailScreenController) {
            sendMailScreenController = (SendMailScreenController) controllers;
        } else if (controllers instanceof RouteDiscontinueScreenController) {
            routeDiscontinueScreenController = (RouteDiscontinueScreenController) controllers;
        }

    }

    public Set<Location> getAvailableDestinations() {
        return kpsModel.getAvailableDestinations();
    }

    public Set<NZLocation> getAvailableOrigins() {
        return kpsModel.getAvailableOrigins();
    }

    // deliver mail
    public boolean deliverMail(String origin, String destination, double weight, double volume, Priority priority) {
        return kpsModel.deliverMail(origin, destination, weight, volume, priority);
    }


    /**
     * =================================================================================================================
     * END
     * =================================================================================================================
     */

    // ====================================================================
    //            Demonstrations for how to use the KPSModel
    //
    //        These methods will be deleted in the final version
    // ====================================================================

    // add staff, given the parameters for creating a Staff object
    public void demonstration_AddStaff() {
        String userName = "username";
        String password = "password";
        boolean isManager = true;
        String firstName = "Hektar";
        String lastName = "Zhao";
        String email = "123@456.com";
        String phoneNumber = "0211111111";

        kpsModel.createNewStaff(userName, password, isManager, firstName, lastName, email, phoneNumber);
    }

    // delete staff, given id
    public void demonstration_deleteStaff() {
        int idToDelete = 2;

        kpsModel.deleteStaff(2);
    }

    // update staff, given id, and the parameters for creating a Staff object except id
    public void demonstration_updateStaff() {
        int idToUpdate = 2;
        String userName = "username";
        String password = "password";
        boolean isManager = true;
        String firstName = "Hektar";
        String lastName = "Zhao";
        String email = "123@456.com";
        String phoneNumber = "0211111111";

        kpsModel.updateStaff(idToUpdate, userName, password, isManager, firstName, lastName, email, phoneNumber);
    }

    // get staff, given id
    public void demonstration_getStaffById() {
        int id = 2;

        Staff staff = kpsModel.getStaffById(id);
    }

    // get logged in staff
    public void demonstration_getCurrentStaff() {
        Staff currentStaff = kpsModel.getCurrentStaff();
    }

    // add route
    public void demonstration_addRoute() {
        String startString = "Wellington";  // case insensitive
        String endString = "moscow"; // case insensitive
        RouteType routeType = RouteType.Air;
        double duration = 3.5f;
        String transportFirm = "FedEx";
        double pricePerGram = 2.5f;
        double pricePerVolume = 2.6f;
        double costPerGram = 2.7f;
        double costPerVolume = 2.8f;

        kpsModel.addRoute(startString, endString, routeType, duration, transportFirm, pricePerGram, pricePerVolume, costPerGram, costPerVolume);
    }

    // deactivate route
    public void demonstration_deactivateRoute() {
        int idToDeactivate = 2;

        kpsModel.deactivateRoute(idToDeactivate);
    }

    // update route price
    public void demonstration_updateRouteCustomerPrice() {
        int idToUpdate = 2;
        double newPricePerGram = 3.5f;
        double newPricePerVolume = 4.0f;

        kpsModel.updateCustomerPrice(idToUpdate, newPricePerGram, newPricePerVolume);
    }

    // update route cost
    public void demonstration_updateRouteTransportCost() {
        int idToUpdate = 2;
        double newCostPerGram = 3.5f;
        double newCostPerVolume = 4.0f;

        kpsModel.updateTransportCost(idToUpdate, newCostPerGram, newCostPerVolume);
    }

    //dilver mail
    public void demonstration_deliverMail() {
        String originString = "wellington";   // case insensitive
        String destinationString = "moscow";   // case insensitive
        double weight = 500f;
        double volume = 1000f;
        Priority priority = Priority.International_Air;

        boolean result = kpsModel.deliverMail(originString, destinationString, weight, volume, priority);

        if (result) {
            // success
        } else {
            // several reasons:
            // origin and destination are not connected yet
            // origin/destination is not in our system
        }
    }

    // get available Origins
    public void demonstration_getAvailableOrigins() {
        Set<NZLocation> origins = kpsModel.getAvailableOrigins();
    }

    // get available destinations
    public void demonstration_getAvailableDestinations() {
        Set<Location> destinations = kpsModel.getAvailableDestinations();
    }

    // get all events
    public void demonstration_getAllEvents() {
        Map<Integer, Event> events = kpsModel.getAllEvens();
    }

    // calculate business figures of one mail
    public void demonstration_getBusinessFiguresOfOneMail() {
        int mainIdToCalculate = 3;
        Mail mailToCalculate = kpsModel.getMailById(mainIdToCalculate);

        double revenue = mailToCalculate.getRevenue();

        double cost = mailToCalculate.getExpenditure();

        double profit = revenue - cost;
    }

    // calculate business figures of all mails
    public void demonstration_getBusinessFiguresOfAllMail() {
        double totalRevenue = kpsModel.calculateTotalRevenue();

        double totalCost = kpsModel.calculateTotalExpenditure();

        double totalProfit = kpsModel.calculateTotalProfit();
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        KPSMain app = new KPSMain();

        // for testing the data populater, and the writing functions of XMLDriver
        new DataPopulater();

        javafx.application.Application.launch(GUI.class);
    }

}
