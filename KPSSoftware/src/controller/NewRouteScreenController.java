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
import model.mail.Priority;
import model.route.Route;
import model.route.RouteType;
import model.staff.Staff;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 25/04/2017.
 */
public class NewRouteScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private Label errorLabel;
    @FXML
    private Button reviewLogsButton;
    @FXML
    private ComboBox typeCombobox;
    @FXML
    private TextField weightPriceTextfield;
    @FXML
    private TextField volumePriceTextfield;
    @FXML
    private TextField durationTextfield;
    @FXML
    private TextField originTextfield;
    @FXML
    private TextField destinationTextfield;
    @FXML
    private TextField companyTextfield;
    @FXML
    private TextField weightCostTextfield;
    @FXML
    private TextField volumeCostTextfield;
    @FXML
    private ImageView avatar;


    public NewRouteScreenController() {
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
            Parent sendMailScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/send mail screen.fxml"));
            Scene sendMailScene = new Scene(sendMailScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(sendMailScene);
            tempStage.show();
        } else if (event.toString().contains("routeDiscontinue")) {
            Parent routeDiscontinueScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/route discontinue screen.fxml"));
            Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(routeDiscontinueScene);
            tempStage.show();
        } else if (event.toString().contains("customerPriceUpdate")) {
            Parent priceUpdateScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/price update screen.fxml"));
            Scene priceUpdateScene = new Scene(priceUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(priceUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("transportCostUpdate")) {
            Parent transportCostUpdateScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/transport cost screen.fxml"));
            Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(transportCostUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("businessFigures")) {
            Parent businessFiguresScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/business figures screen.fxml"));
            Scene businessFiguresScene = new Scene(businessFiguresScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(businessFiguresScene);
            tempStage.show();
        } else if (event.toString().contains("reviewLogs")) {
            //TODO; still need to build the screen
        } else if (event.toString().contains("logout")) {
            //TODO; POP up dialog box to ask the user if they are sure want to logout
            //DialogBox.LogoutyMsg("Logout", "Are you sure you want to logout.");
            Parent loginScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/login screen.fxml"));
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
            //TODO: Test all these cases
            if (!originTextfield.getText().matches("[a-zA-Z]+")
                    || !destinationTextfield.getText().matches("[a-zA-Z]+")
                    || companyTextfield.getText().equals("")
                    || !durationTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?")
                    || !weightPriceTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?")
                    || !volumePriceTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?")
                    || !weightCostTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?")
                    || !volumeCostTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?")
                    || typeCombobox.getValue()==null) {
                errorLabel.setText("Please Fill in all the Information");
            }else{
                //TODO: i could add a way to check for 2dp but we need to...
                String origin = originTextfield.getText();
                String destination= destinationTextfield.getText();
                RouteType type = RouteType.valueOf((String)typeCombobox.getValue());
                double duration = Double.parseDouble(durationTextfield.getText());
                String firm= companyTextfield.getText();
                double pricePerGram = Double.parseDouble(weightPriceTextfield.getText());
                double pricePerVolume = Double.parseDouble(volumePriceTextfield.getText());
                double costPerGram = Double.parseDouble(weightCostTextfield.getText());
                double costPerVolume = Double.parseDouble(volumeCostTextfield.getText());
                kpsMain.addRoute(origin,destination,type,duration,firm,pricePerGram,pricePerVolume,costPerGram,costPerVolume);
                errorLabel.setText("New Route Successfully Created");
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
        avatar.setImage(new Image(NewRouteScreenController.class.getResourceAsStream("/img/" + (staff.id % 5) + ".png")));
        if (!staff.isManager()) {
            reviewLogsButton.setVisible(false);
            reviewLogsButton.setDisable(false);
        }
        typeCombobox.getItems().add(RouteType.Air.toString());
        typeCombobox.getItems().add(RouteType.Land.toString());
        typeCombobox.getItems().add(RouteType.Sea.toString());


    }

    private void clearContent(ActionEvent event) {
        Parent newRouteScreen = null;
        try {
            newRouteScreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/new route screen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene newRouteScene = new Scene(newRouteScreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(newRouteScene);
        tempStage.show();
    }


    private void returnHome(ActionEvent event) {
        Parent homescreen = null;
        try {
            homescreen = FXMLLoader.load(NewRouteScreenController.class.getResource("/fxml/home screen.fxml"));
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
        NewRouteScreenController.kpsMain = kpsMain;
    }
}
