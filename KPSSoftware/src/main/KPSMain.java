package main;

import controller.*;
import model.KPSModel;
import model.database.DataPopulater;
import model.event.Event;
import model.location.Location;
import model.location.NZLocation;
import model.mail.Mail;
import model.mail.Priority;
import model.route.Route;
import model.route.RouteType;
import model.staff.Staff;
import view.GUI;

import java.time.LocalDate;
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
    private static UserSettingScreenController userSettingScreenController;
    private static ChangePasswordScreenController changePasswordScreenController;
    private static ManageUserScreenController manageUserScreenControllerl;
    private static AddNewUserScreenController addNewUserScreenController;
    private static SendMailScreenController sendMailScreenController;
    private static RouteDiscontinueScreenController routeDiscontinueScreenController;
    private static PriceUpdateScreenController priceUpdateScreenController;
    private static TransportCostScreenController transportCostScreenController;
    private static NewRouteScreenController newRouteScreenController;
    private static BusinessFiguresScreenController businessFiguresScreenController;
    private static ReviewLogsController reviewLogsController;
    private static EventDialogController eventDialogController;

    // ================== view objects =============================


    /**
     * Constructor
     */
    public KPSMain() {
        kpsModel = new KPSModel();

        LoginScreenController.setKPSMain(this);
        HomeScreenController.setKPSMain(this);
        UserSettingScreenController.setKPSMain(this);
        ChangePasswordScreenController.setKPSMain(this);
        ManageUserScreenController.setKPSMain(this);
        AddNewUserScreenController.setKPSMain(this);
        SendMailScreenController.setKPSMain(this);
        RouteDiscontinueScreenController.setKPSMain(this);
        PriceUpdateScreenController.setKPSMain(this);
        TransportCostScreenController.setKPSMain(this);
        NewRouteScreenController.setKPSMain(this);
        BusinessFiguresScreenController.setKPSMain(this);
        ReviewLogsController.setKPSMain(this);
        EventDialogController.setKPSMain(this);
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

    public boolean changeCurrentStaffPassword(String newPassword) {
        Staff currentStaff = getCurrentStaff();

        return kpsModel.updateStaff(currentStaff.id, currentStaff.getUserName(), newPassword, currentStaff.isManager(),
                currentStaff.getFirstName(), currentStaff.getLastName(), currentStaff.getEmail(), currentStaff.getPhoneNumber());
    }

    public Staff getSelectedUser(String firstName, String lastName) {
        for (Staff s : kpsModel.getAllStaffs().values()) {
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                return s;
            }
        }

        return null;
    }

    public boolean authenticateLogin(String username, String password) {
        return kpsModel.login(username, password);
    }

    public static void setLoginScreenController(Object controllers) {
        if (controllers instanceof LoginScreenController) {
            loginScreenController = (LoginScreenController) controllers;
        } else if (controllers instanceof HomeScreenController) {
            homeScreenController = (HomeScreenController) controllers;
        } else if (controllers instanceof UserSettingScreenController) {
            userSettingScreenController = (UserSettingScreenController) controllers;
        } else if (controllers instanceof ChangePasswordScreenController) {
            changePasswordScreenController = (ChangePasswordScreenController) controllers;
        } else if (controllers instanceof ManageUserScreenController) {
            manageUserScreenControllerl = (ManageUserScreenController) controllers;
        } else if (controllers instanceof AddNewUserScreenController) {
            addNewUserScreenController = (AddNewUserScreenController) controllers;
        } else if (controllers instanceof SendMailScreenController) {
            sendMailScreenController = (SendMailScreenController) controllers;
        } else if (controllers instanceof RouteDiscontinueScreenController) {
            routeDiscontinueScreenController = (RouteDiscontinueScreenController) controllers;
        } else if (controllers instanceof PriceUpdateScreenController) {
            priceUpdateScreenController = (PriceUpdateScreenController) controllers;
        } else if (controllers instanceof TransportCostScreenController) {
            transportCostScreenController = (TransportCostScreenController) controllers;
        } else if (controllers instanceof NewRouteScreenController) {
            newRouteScreenController = (NewRouteScreenController) controllers;
        } else if (controllers instanceof BusinessFiguresScreenController) {
            businessFiguresScreenController = (BusinessFiguresScreenController) controllers;
        } else if (controllers instanceof ReviewLogsController) {
            reviewLogsController = (ReviewLogsController) controllers;
        }else if (controllers instanceof EventDialogController) {
            eventDialogController = (EventDialogController) controllers;
        }
    }

    public Set<Location> getAvailableDestinations() {
        return kpsModel.getAvailableDestinations();
    }

    public Set<NZLocation> getAvailableOrigins() {
        return kpsModel.getAvailableOrigins();
    }

    // process mail
    public Mail processMail(String origin, String destination, double weight, double volume, Priority priority) {
        return kpsModel.processMail(origin, destination, weight, volume, priority);
    }

    // deliver mail
    public boolean deliverMail(Mail mail) {
        return kpsModel.deliverMail(mail);
    }

    public double getMailRevenue(int mailId) {
        return kpsModel.getMailRevenue(mailId);
    }

    public double getMailExpenditure(int mailId) {
        return kpsModel.getMailExpenditure(mailId);
    }

    public double getTempMailRevenue(Mail tempMail) {
        return kpsModel.getTempMailRevenue(tempMail);
    }

    public double getTempMailExpenditure(Mail tempMail) {
        return kpsModel.getTempMailExpenditure(tempMail);
    }

    public Map<Integer, Route> getAllRoutes() {
        return kpsModel.getAllRoutes();
    }

    public Route getRoute(int id) {
        return kpsModel.getRouteById(id);
    }

    public boolean deactivateRoute(int routId) {
        return kpsModel.deactivateRoute(routId);
    }

    public void updateRouteCustomerPrice(int idToUpdate, double newPricePerGram, double newPricePerVolume) {
        kpsModel.updateCustomerPrice(idToUpdate, newPricePerGram, newPricePerVolume);
    }

    public void updateRouteTransportCost(int idToUpdate, double newCostPerGram, double newCostPerVolume) {
        kpsModel.updateTransportCost(idToUpdate, newCostPerGram, newCostPerVolume);
    }

    public void addRoute(String startString, String endString, RouteType routeType, double duration, String transportFirm, double pricePerGram, double pricePerVolume, double costPerGram, double costPerVolume) {
        kpsModel.addRoute(startString, endString, routeType, duration, transportFirm, pricePerGram, pricePerVolume, costPerGram, costPerVolume);
    }

    public Map<Integer, Mail> getCriticalMails() {
        return kpsModel.getCriticalMails();
    }

    public double getTotalRevenue(Map<Integer, Mail> mails) {
        return KPSModel.calculateTotalRevenue(mails);
    }

    public double getTotalExpenditure(Map<Integer, Mail> mails) {
        return KPSModel.calculateTotalExpenditure(mails);
    }

    public Map<Integer, Event> getEvensByStartEndTime(LocalDate startDate, LocalDate endDate) {
        return kpsModel.getEventsByStartAndEndTime(startDate, endDate);
    }

    public Map<Integer, Mail> getMailsByStartEndTime(LocalDate startDate, LocalDate endDate) {
        return kpsModel.getMailsByStartAndEndTime(startDate, endDate);
    }

    public double getAverageDeliveryTime(String origin, String destination, Priority priority) {
        return kpsModel.calculateAverageDeliveryTime(origin, destination, priority);

    }

    public Map<Integer, Event> getAllEvent() {
        return kpsModel.getAllEvens();
    }

    public Mail getMail(int id){
        return kpsModel.getMailById(id);
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

        Mail tempMail = kpsModel.processMail(originString, destinationString, weight, volume, priority);

        if (tempMail == null) {
            // we don't support sending mails from the given origin to the given destination
        } else {
            // We can deliver the mail

            boolean result = kpsModel.deliverMail(tempMail);

            // note we must deliver the mail first, otherwise there is no such mail in database.
            double revenue = kpsModel.getMailRevenue(tempMail.id);
            double expenditure = kpsModel.getMailExpenditure(tempMail.id);
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
        Map<Integer, Mail> allMails = kpsModel.getAllMails();

        double totalRevenue = KPSModel.calculateTotalRevenue(allMails);

        double totalCost = KPSModel.calculateTotalExpenditure(allMails);

        double totalProfit = KPSModel.calculateTotalProfit(allMails);
    }

    // get a list of [origin, destination, priority] triples, aka critical routes
    public void demonstration_getCriticalRoutes() {
        Map<Integer, Mail> criticalMails = kpsModel.getCriticalMails();

        // for each mail, to get the origin, destination, and priority
        criticalMails.values().forEach(mail -> {
            String origin = mail.getOrigin().getLocationName();
            String destination = mail.getDestination().getLocationName();
            Priority priority = mail.getPriority();
        });

        // calculate the average revenue and average expenditure:
        double numMails = criticalMails.size();
        double totalRevenue = KPSModel.calculateTotalRevenue(criticalMails);
        double totalCost = KPSModel.calculateTotalExpenditure(criticalMails);

        double averageRevenue = totalRevenue / numMails;
        double averageCost = totalCost / numMails;

    }

    public void demonstration_getBusinessFiguresBetweenTimeRange() {
        String startDateString = "2017-01-01";
        String endDateString = "2017-12-31";

        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);

        Map<Integer, Mail> mails = kpsModel.getMailsByStartAndEndTime(startDate, endDate);

        double revenue = KPSModel.calculateTotalRevenue(mails);
        double expenditure = KPSModel.calculateTotalExpenditure(mails);
        int numMails = mails.size();
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        KPSMain app = new KPSMain();

        // prepare some data so we can have something to play with
        new DataPopulater().populateSomethingForMeWillYa();

        javafx.application.Application.launch(GUI.class);
    }

}
