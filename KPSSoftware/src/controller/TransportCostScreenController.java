package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.KPSMain;
import model.route.Route;
import model.staff.Staff;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 25/04/2017.
 */
public class TransportCostScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private Button reviewLogsButton;
    @FXML
    private ComboBox routeCombobox;
    @FXML
    private Label errorLabel;
    @FXML
    private Label notificationLabel;
    @FXML
    private Label orginLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private Label transportFirmLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label weightPriceLabel;
    @FXML
    private Label volumePriceLabel;
    @FXML
    private Label durationLabel;
    @FXML
    private TextField weightTextfield;
    @FXML
    private TextField volumeTextfield;
    @FXML
    private ImageView avatar;

    public TransportCostScreenController() {
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
            Parent sendMailScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/send mail screen.fxml"));
            Scene sendMailScene = new Scene(sendMailScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(sendMailScene);
            tempStage.show();
        } else if (event.toString().contains("routeDiscontinue")) {
            Parent routeDiscontinueScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/route discontinue screen.fxml"));
            Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(routeDiscontinueScene);
            tempStage.show();
        } else if (event.toString().contains("customerPriceUpdate")) {
            Parent priceUpdateScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/price update screen.fxml"));
            Scene priceUpdateScene = new Scene(priceUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(priceUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("newRoute")) {
            Parent newRouteScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/new route screen.fxml"));
            Scene newRouteScene = new Scene(newRouteScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(newRouteScene);
            tempStage.show();
        } else if (event.toString().contains("businessFigures")) {
            Parent businessFiguresScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/business figures screen.fxml"));
            Scene businessFiguresScene = new Scene(businessFiguresScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(businessFiguresScene);
            tempStage.show();
        } else if (event.toString().contains("reviewLogs")) {
            //TODO; still need to build the screen
        } else if (event.toString().contains("logout")) {
            //TODO; POP up dialog box to ask the user if they are sure want to logout
            //DialogBox.LogoutyMsg("Logout", "Are you sure you want to logout.");
            Parent loginScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/login screen.fxml"));
            Scene loginScene = new Scene(loginScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(loginScene);
            tempStage.show();
        } else if (event.toString().contains("setting")) {
            //TODO
            System.out.println("setting");
        }
    }

    /**
     * This method is used to handel local screen button actions. i.e accept, reset, discard and exit
     *
     * @param event
     */
    public void handleButtons(ActionEvent event) {
        if (event.toString().contains("accept")) {
            if (routeCombobox == null || (!weightTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?") || Double.parseDouble(weightTextfield.getText()) < 0)
                    || (!volumeTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?") || Double.parseDouble(volumeTextfield.getText()) < 0)) {
                errorLabel.setText("Please Fill in all the Information");
            } else {
                String[] selectdText = ((String) routeCombobox.getValue()).split(" ");

                int routeID = Integer.parseInt(selectdText[0]);
                Route route = kpsMain.getRoute(routeID);
                double oldWeightPrice = route.getPricePerGram();
                double oldVolumePrice = route.getCostPerVolume();
                double weightCost = Double.parseDouble(weightTextfield.getText());
                double volumeCost = Double.parseDouble(volumeTextfield.getText());
                kpsMain.updateRouteTransportCost(routeID, weightCost, volumeCost);
                errorLabel.setText("Customer price was successfully updated");
                transportPriceUpdateNotification(route, oldWeightPrice, oldVolumePrice);
            }
        } else if (event.toString().contains("reset")) {
            clearContent(event);

        } else if (event.toString().contains("discard")) {
            returnHome(event);
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
        avatar.setImage(new Image(TransportCostScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));
        if (!staff.isManager()) {
            reviewLogsButton.setVisible(false);
            reviewLogsButton.setDisable(false);
        }
        for (Integer i : kpsMain.getAllRoutes().keySet()) {
            Route root = kpsMain.getAllRoutes().get(i);
            if (root.isActive()) {
                routeCombobox.getItems().add(root.id + " " + root.getStartLocation().getLocationName() + " ->" + root.getEndLocation().getLocationName() + " : " + root.routeType.toString());
            }
        }
        notificationLabel.setVisible(false);
        orginLabel.setVisible(false);
        destinationLabel.setVisible(false);
        transportFirmLabel.setVisible(false);
        typeLabel.setVisible(false);
        weightPriceLabel.setVisible(false);
        volumePriceLabel.setVisible(false);
        durationLabel.setVisible(false);


    }

    private void clearContent(ActionEvent event) {
        Parent transportCostUpdateScreen = null;
        try {
            transportCostUpdateScreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/transport cost screen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(transportCostUpdateScene);
        tempStage.show();
    }


    private void returnHome(ActionEvent event) {
        Parent homescreen = null;
        try {
            homescreen = FXMLLoader.load(TransportCostScreenController.class.getResource("/fxml/home screen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene homeSecne = new Scene(homescreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(homeSecne);
        tempStage.show();
    }

    private void transportPriceUpdateNotification(Route route, double oldWeightPrice, double oldVolumePrice) {
        orginLabel.setText("Affected Origin: " + route.getStartLocation().getLocationName());
        destinationLabel.setText("Affected Destination: " + route.getEndLocation().getLocationName());
        typeLabel.setText("Type: " + route.routeType.toString());
        weightPriceLabel.setText("Old Weight Price: $" + String.format("%.2f", oldWeightPrice) + " New Price: $" + String.format("%.2f", route.getPricePerGram()));
        volumePriceLabel.setText("Old Volume Price: $" + String.format("%.2f", oldVolumePrice) + " New Price: $" + String.format("%.2f", route.getPricePerVolume()));
        transportFirmLabel.setText("Transport Firm: " + route.getTransportFirm());
        durationLabel.setText("Duration "+ route.getDuration()+" Hours");
        notificationLabel.setVisible(true);
        orginLabel.setVisible(true);
        destinationLabel.setVisible(true);
        typeLabel.setVisible(true);
        transportFirmLabel.setVisible(true);
        durationLabel.setVisible(true);
        weightPriceLabel.setVisible(true);
        volumePriceLabel.setVisible(true);
    }

    /**
     * to set the KPSMain class reference.
     *
     * @param kpsMain
     */
    public static void setKPSMain(KPSMain kpsMain) {
        TransportCostScreenController.kpsMain = kpsMain;
    }
}
