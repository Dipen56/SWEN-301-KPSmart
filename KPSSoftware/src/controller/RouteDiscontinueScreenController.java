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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.KPSMain;
import model.location.Location;
import model.route.Route;
import model.staff.Staff;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 25/04/2017.
 */
public class RouteDiscontinueScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private Button reviewLogsButton;
    @FXML
    private ComboBox routeCombobox;
    @FXML
    private ImageView avatar;
    @FXML
    private Label affectedOriginLabel;
    @FXML
    private Label affectedDestinationLabel;
    @FXML
    private Label transportFirmLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label notificationLabel;

    public RouteDiscontinueScreenController() {
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
            Parent sendMailScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/send mail screen.fxml"));
            Scene sendMailScene = new Scene(sendMailScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(sendMailScene);
            tempStage.show();
        } else if (event.toString().contains("customerPriceUpdate")) {
            Parent priceUpdateScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/price update screen.fxml"));
            Scene priceUpdateScene = new Scene(priceUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(priceUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("transportCostUpdate")) {
            Parent transportCostUpdateScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/transport cost screen.fxml"));
            Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(transportCostUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("newRoute")) {
            Parent newRouteScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/new route screen.fxml"));
            Scene newRouteScene = new Scene(newRouteScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(newRouteScene);
            tempStage.show();
        } else if (event.toString().contains("businessFigures")) {
            Parent businessFiguresScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/business figures screen.fxml"));
            Scene businessFiguresScene = new Scene(businessFiguresScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(businessFiguresScene);
            tempStage.show();
        } else if (event.toString().contains("reviewLogs")) {
            //TODO; still need to build the screen
        } else if (event.toString().contains("logout")) {
            //TODO; POP up dialog box to ask the user if they are sure want to logout
            //DialogBox.LogoutyMsg("Logout", "Are you sure you want to logout.");
            Parent loginScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/login screen.fxml"));
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
            //this is view button in the gui
            if(routeCombobox.getValue()!=null){
                String[] selectdText = ((String) routeCombobox.getValue()).split(" ");

                int routeID = Integer.parseInt(selectdText[0]);
                Route root = kpsMain.getRoute(routeID);
                if (root != null) {
                    affectedOriginLabel.setText("Affected Origin: " + root.getStartLocation().getLocationName());
                    affectedDestinationLabel.setText("Affected Destination: " + root.getEndLocation().getLocationName());
                    transportFirmLabel.setText("Tranport Firm: " + root.getTransportFirm());
                    typeLabel.setText("Type: " + root.routeType.toString());
                    statusLabel.setText("Status: Activate");
                    notificationLabel.setVisible(true);
                    affectedOriginLabel.setVisible(true);
                    affectedDestinationLabel.setVisible(true);
                    transportFirmLabel.setVisible(true);
                    typeLabel.setVisible(true);
                    statusLabel.setVisible(true);

                }
            }

        } else if (event.toString().contains("reset")) {
            clearContent(event);

        } else if (event.toString().contains("discard")) {
            returnHome(event);
        } else if (event.toString().contains("discontinueButton")) {
            String[] selectdText = ((String) routeCombobox.getValue()).split(" ");

            int routeID = Integer.parseInt(selectdText[0]);
            Route root = kpsMain.getRoute(routeID);
            if (root != null) {
                root.setIsActive(false);
                affectedOriginLabel.setText("Affected Origin: " + root.getStartLocation().getLocationName());
                affectedDestinationLabel.setText("Affected Destination: " + root.getEndLocation().getLocationName());
                transportFirmLabel.setText("Tranport Firm: " + root.getTransportFirm());
                typeLabel.setText("Type: " + root.routeType.toString());
                statusLabel.setText("Status: Deactivated!");
                notificationLabel.setVisible(true);
                affectedOriginLabel.setVisible(true);
                affectedDestinationLabel.setVisible(true);
                transportFirmLabel.setVisible(true);
                typeLabel.setVisible(true);
                statusLabel.setVisible(true);

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
        avatar.setImage(new Image(RouteDiscontinueScreenController.class.getResourceAsStream("/img/" + staff.id + ".png")));
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
        affectedOriginLabel.setVisible(false);
        affectedDestinationLabel.setVisible(false);
        transportFirmLabel.setVisible(false);
        typeLabel.setVisible(false);
        statusLabel.setVisible(false);


    }

    private void clearContent(ActionEvent event) {
        Parent routeDiscontinueScreen = null;
        try {
            routeDiscontinueScreen = FXMLLoader.load(RouteDiscontinueScreenController.class.getResource("/fxml/route discontinue screen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(routeDiscontinueScene);
        tempStage.show();
    }


    private void returnHome(ActionEvent event) {
        Parent homescreen = null;
        try {
            homescreen = FXMLLoader.load(controller.SendMailScreenController.class.getResource("/fxml/home screen.fxml"));
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
        RouteDiscontinueScreenController.kpsMain = kpsMain;
    }
}
