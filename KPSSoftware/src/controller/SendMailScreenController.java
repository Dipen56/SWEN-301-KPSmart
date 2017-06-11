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
import model.location.Location;
import model.mail.Priority;
import model.staff.Staff;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 25/04/2017.
 */
public class SendMailScreenController implements Initializable {
    private static KPSMain kpsMain;
    @FXML
    private Label userLable;
    @FXML
    private Label priceLabel;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Label costLabel;
    @FXML
    private ImageView avatar;
    @FXML
    private ComboBox originCombobox;
    @FXML
    private ComboBox destinationCombobox;
    @FXML
    private TextField weightTextfield;
    @FXML
    private TextField volumeTextfield;
    @FXML
    private ComboBox priorityCombobox;
    @FXML
    private Button reviewLogsButton;


    public SendMailScreenController() {
        KPSMain.setLoginScreenController(this);
    }


    /**
     * this method is used by the buttons on the left side menu to change change the scene.
     *
     * @param event
     * @throws IOException
     */
    public void changeScenes(ActionEvent event) throws IOException {

        if (event.toString().contains("routeDiscontinue")) {
            Parent routeDiscontinueScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/route discontinue screen.fxml"));
            Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(routeDiscontinueScene);
            tempStage.show();
        } else if (event.toString().contains("customerPriceUpdate")) {
            Parent priceUpdateScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/price update screen.fxml"));
            Scene priceUpdateScene = new Scene(priceUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(priceUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("transportCostUpdate")) {
            Parent transportCostUpdateScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/transport cost screen.fxml"));
            Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(transportCostUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("newRoute")) {
            Parent newRouteScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/new route screen.fxml"));
            Scene newRouteScene = new Scene(newRouteScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(newRouteScene);
            tempStage.show();
        } else if (event.toString().contains("businessFigures")) {
            Parent businessFiguresScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/business figures screen.fxml"));
            Scene businessFiguresScene = new Scene(businessFiguresScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(businessFiguresScene);
            tempStage.show();
        } else if (event.toString().contains("reviewLogs")) {
            //TODO; still need to build the screen
        } else if (event.toString().contains("logout")) {
            //TODO; POP up dialog box to ask the user if they are sure want to logout
            //DialogBox.LogoutyMsg("Logout", "Are you sure you want to logout.");
            Parent loginScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/login screen.fxml"));
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
        if (event.toString().contains("Accept")) {
            System.out.println(weightTextfield.getText());
            if ((originCombobox.getValue() == null) || (destinationCombobox.getValue() == null)
                    || (!weightTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?") || Double.parseDouble(weightTextfield.getText()) < 0)
                    || (!volumeTextfield.getText().matches("[0-9]{1,13}(\\.[0-9]*)?") || Double.parseDouble(volumeTextfield.getText()) < 0)
                    || priorityCombobox.getValue() == null) {
                errorLabel.setText("Please Fill in all the Information");
            } else {
                int mailId = kpsMain.deliverMail((String) originCombobox.getValue(), (String) destinationCombobox.getValue(),
                        Double.parseDouble(weightTextfield.getText()), Double.parseDouble(volumeTextfield.getText()),
                        Priority.createPriorityFrom((String) priorityCombobox.getValue()));
                if (mailId > 0) {
                    errorLabel.setText("Mail was successfully sent");
                    priceLabel.setText(String.format("%.2f", kpsMain.getMailRevenue(mailId)));
                    costLabel.setText(String.format("%.2f", kpsMain.getMailExpenditure(mailId)));
                    totalPriceLabel.setVisible(true);
                    priceLabel.setVisible(true);
                    totalCostLabel.setVisible(true);
                    costLabel.setVisible(true);
                    //need a way to stall here
                    //clearContent(event);
                } else {
                    errorLabel.setText("The Selected Route is Unavailable");
                }
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
        avatar.setImage(new Image(SendMailScreenController.class.getResourceAsStream("/img/" + staff.id + ".png")));
        if (!staff.isManager()) {
            reviewLogsButton.setVisible(false);
            reviewLogsButton.setDisable(false);
        }
        for (Location loc : kpsMain.getAvailableOrigins()) {
            originCombobox.getItems().add(loc.getLocationName());
        }
        for (Location loc : kpsMain.getAvailableDestinations()) {

            destinationCombobox.getItems().add(loc.getLocationName());
        }
        priorityCombobox.getItems().add(Priority.Domestic_Standard.toString());
        priorityCombobox.getItems().add(Priority.Domestic_Air.toString());
        priorityCombobox.getItems().add(Priority.International_Standard.toString());
        priorityCombobox.getItems().add(Priority.International_Air.toString());
        totalPriceLabel.setVisible(false);
        priceLabel.setVisible(false);
        totalCostLabel.setVisible(false);
        costLabel.setVisible(false);

    }

    private void clearContent(ActionEvent event) {
        Parent sendMailScreen = null;
        try {
            sendMailScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/send mail screen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene sendMailScene = new Scene(sendMailScreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(sendMailScene);
        tempStage.show();
    }


    private void returnHome(ActionEvent event) {
        Parent homescreen = null;
        try {
            homescreen = FXMLLoader.load(SendMailScreenController.class.getResource("/fxml/home screen.fxml"));
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
        SendMailScreenController.kpsMain = kpsMain;
    }
}
