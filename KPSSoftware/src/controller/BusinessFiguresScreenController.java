package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.KPSMain;
import model.KPSModel;
import model.location.Location;
import model.mail.Mail;
import model.mail.Priority;
import model.staff.Staff;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 25/04/2017.
 */
public class BusinessFiguresScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private Button reviewLogsButton;
    @FXML
    private Label revenueLabel;
    @FXML
    private Label expenditureLabel;
    @FXML
    private Label numberEventLabel;
    @FXML
    private Label totalMailLabel;
    @FXML
    private ComboBox avgOriginComboBox;
    @FXML
    private ComboBox avgDestinationComboBox;
    @FXML
    private ComboBox avgPriorityComboBox;
    @FXML
    private Label averageDeliveryLabel;
    @FXML
    private ListView criticalRoutesListView;
    @FXML
    private ImageView avatar;
    @FXML
    private Label errorLabel;
    @FXML
    private ComboBox criticalRouteOriginComboBox;
    @FXML
    private ComboBox criticalRouteDestinationComboBox;
    @FXML
    private ComboBox criticalRoutePriorityComboBox;
    @FXML
    private Label criticalRouteErrorLabel;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label dateErrorLabel;


    public BusinessFiguresScreenController() {
        KPSMain.setLoginScreenController(this);
    }

    /**
     * this method is used by the buttons on the left side menu to change change the scene.
     *
     * @param event
     * @throws IOException
     */
    public void changeScenes(ActionEvent event) throws IOException {

        if (event.toString().contains("sendMail")) {
            Parent sendMailScreen = FXMLLoader.load(BusinessFiguresScreenController.class.getResource("/fxml/SendMailScreen.fxml"));
            Scene sendMailScene = new Scene(sendMailScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(sendMailScene);
            tempStage.show();
        } else if (event.toString().contains("routeDiscontinue")) {
            Parent routeDiscontinueScreen = FXMLLoader.load(BusinessFiguresScreenController.class.getResource("/fxml/RouteDiscontinueScreen.fxml"));
            Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(routeDiscontinueScene);
            tempStage.show();
        } else if (event.toString().contains("customerPriceUpdate")) {
            Parent priceUpdateScreen = FXMLLoader.load(BusinessFiguresScreenController.class.getResource("/fxml/PriceUpdateScreen.fxml"));
            Scene priceUpdateScene = new Scene(priceUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(priceUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("transportCostUpdate")) {
            Parent transportCostUpdateScreen = FXMLLoader.load(BusinessFiguresScreenController.class.getResource("/fxml/TransportCostScreen.fxml"));
            Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(transportCostUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("newRoute")) {
            Parent newRouteScreen = FXMLLoader.load(BusinessFiguresScreenController.class.getResource("/fxml/NewRouteScreen.fxml"));
            Scene newRouteScene = new Scene(newRouteScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(newRouteScene);
            tempStage.show();
        } else if (event.toString().contains("reviewLogs")) {
            //TODO; still need to build the screen
        } else if (event.toString().contains("logout")) {
            //TODO; POP up dialog box to ask the user if they are sure want to logout
            //DialogBox.LogoutyMsg("Logout", "Are you sure you want to logout.");
            Parent loginScreen = FXMLLoader.load(BusinessFiguresScreenController.class.getResource("/fxml/LoginScreen.fxml"));
            Scene loginScene = new Scene(loginScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(loginScene);
            tempStage.show();
        }
    }

    /**
     * This method is used to handel local screen button actions. i.e accept, reset, discard and exit
     *
     * @param event
     */
    public void handleButtons(ActionEvent event) {
        if (event.toString().contains("exit")) {
            returnHome(event);
        } else if (event.toString().contains("avgViewButton")) {
            if (avgOriginComboBox.getValue() == null) {
                errorLabel.setText("Please Select a Origin");
            } else if (avgDestinationComboBox.getValue() == null) {
                errorLabel.setText("Please Select a Destination");
            } else if (avgPriorityComboBox.getValue() == null) {
                errorLabel.setText("Please Select a Priority");
            } else {
                double averageTime = kpsMain.getAverageDeliveryTime((String) avgOriginComboBox.getValue(), (String) avgDestinationComboBox.getValue(), Priority.createPriorityFrom((String) avgPriorityComboBox.getValue()));
                if (averageTime >= 0) {
                    averageDeliveryLabel.setText(String.format("%.2f", averageTime));
                } else {
                    errorLabel.setText("Selected Route Unavailable");
                }
            }
        } else if (event.toString().contains("criticalRouteViewButton")) {

            if (criticalRouteOriginComboBox.getValue() == null) {
                criticalRouteErrorLabel.setText("Please Select a Origin");
            } else if (criticalRouteDestinationComboBox.getValue() == null) {
                criticalRouteErrorLabel.setText("Please Select a Destination");
            } else if (criticalRoutePriorityComboBox.getValue() == null) {
                criticalRouteErrorLabel.setText("Please Select a Priority");
            } else {
                //TODO: critical Routes
            }
        } else if (event.toString().contains("statsViewButton")) {
            if (startDatePicker.getValue() == null) {
                dateErrorLabel.setText("Please Select a Start Date");
            } else if (endDatePicker.getValue() == null) {
                dateErrorLabel.setText("Please Select a End Date");
            } else {
                LocalDate startDate = (LocalDate) startDatePicker.getValue();
                LocalDate endDate = (LocalDate) endDatePicker.getValue();

           Map<Integer, Mail> mails = kpsMain.getAllMails(startDate,endDate);
           revenueLabel.setText(String.format("%.2f", kpsMain.getTotalRevenue(mails)));
          expenditureLabel.setText(String.format("%.2f", kpsMain.getTotalExpenditure(mails)));
//            numberEventLabel.setText("" + kpsMain.getAllEvens().size());
           totalMailLabel.setText("" + mails.size());
            }
        }
    }


    /**
     * Everything that should occur before the home is displayed should go in here.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Staff staff = kpsMain.getCurrentStaff();
        userLable.setText(staff.getFirstName());
        avatar.setImage(new Image(BusinessFiguresScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));
        if (!staff.isManager()) {
            reviewLogsButton.setVisible(false);
            reviewLogsButton.setDisable(false);
        }
        for (Location loc : kpsMain.getAvailableOrigins()) {
            avgOriginComboBox.getItems().add(loc.getLocationName());
            criticalRouteOriginComboBox.getItems().add(loc.getLocationName());
        }
        for (Location loc : kpsMain.getAvailableDestinations()) {

            avgDestinationComboBox.getItems().add(loc.getLocationName());
            criticalRouteDestinationComboBox.getItems().add(loc.getLocationName());
        }
        avgPriorityComboBox.getItems().add(Priority.Domestic_Standard.toString());
        avgPriorityComboBox.getItems().add(Priority.Domestic_Air.toString());
        avgPriorityComboBox.getItems().add(Priority.International_Standard.toString());
        avgPriorityComboBox.getItems().add(Priority.International_Air.toString());

        criticalRoutePriorityComboBox.getItems().add(Priority.Domestic_Standard.toString());
        criticalRoutePriorityComboBox.getItems().add(Priority.Domestic_Air.toString());
        criticalRoutePriorityComboBox.getItems().add(Priority.International_Standard.toString());
        criticalRoutePriorityComboBox.getItems().add(Priority.International_Air.toString());


    }

    private void returnHome(ActionEvent event) {
        Parent homescreen = null;
        try {
            homescreen = FXMLLoader.load(BusinessFiguresScreenController.class.getResource("/fxml/HomeScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene homeSecne = new Scene(homescreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(homeSecne);
        tempStage.show();
    }

    /**
     * to set the KPSMain class reference.
     *
     * @param kpsMain
     */
    public static void setKPSMain(KPSMain kpsMain) {
        BusinessFiguresScreenController.kpsMain = kpsMain;
    }
}
