package Controller;

import View.GUI;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 25/04/2017.
 */
public class SendMailScreenController implements Initializable {
    public Label userLable;
    public Button reviewLogs;
    public ComboBox originCombobox;
    public ComboBox destinationCombobox;
    public TextField weightTextfield;
    public TextField volumeTextfield;
    public ComboBox priorityCombobox;
    public Label priceLabel;
    public ImageView avatar;

    /**
     * this method is used by the buttons on the left side menu to change change the scene.
     *
     * @param event
     * @throws IOException
     */
    public void changeScenes(ActionEvent event) throws IOException {

        if (event.toString().contains("routeDiscontinue")) {
            Parent routeDiscontinueScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/rec/route discontinue screen.fxml"));
            Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(routeDiscontinueScene);
            tempStage.show();
        } else if (event.toString().contains("customerPriceUpdate")) {
            Parent priceUpdateScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/rec/price update screen.fxml"));
            Scene priceUpdateScene = new Scene(priceUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(priceUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("transportCostUpdate")) {
            Parent transportCostUpdateScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/rec/transport cost screen.fxml"));
            Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(transportCostUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("newRoute")) {
            Parent newRouteScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/rec/new route screen.fxml"));
            Scene newRouteScene = new Scene(newRouteScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(newRouteScene);
            tempStage.show();
        } else if (event.toString().contains("businessFigures")) {
            Parent businessFiguresScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/rec/business figures screen.fxml"));
            Scene businessFiguresScene = new Scene(businessFiguresScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(businessFiguresScene);
            tempStage.show();
        } else if (event.toString().contains("reviewLogs")) {
            //TODO; still need to build the screen
        } else if (event.toString().contains("logout")) {
            //TODO; POP up dialog box to ask the user if they are sure want to logout
            //DialogBox.LogoutyMsg("Logout", "Are you sure you want to logout.");
            Parent loginScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/rec/login screen.fxml"));
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
            //TODO: retive information from the field and pass to logic...
            System.out.println("accpted");
        } else if (event.toString().contains("reset")) {
            clearContent(event);

        } else if (event.toString().contains("discard")) {
            returnHome(event);
        } else if (event.toString().contains("exit")) {
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
        //TODO: change this based on real information
        userLable.setText("Manager Dipen");
        avatar.setImage(new Image(SendMailScreenController.class.getResourceAsStream("/rec/oldman-user.jpg")));
        //TODO: if clerk disable reviewLogs button. reviewLogs.setVisible(false);
        //TODO: forall comboboxs originCombobox.getItems().addAll("Wellington","Auckland");

    }

    private void clearContent(ActionEvent event) {
        Parent sendMailScreen = null;
        try {
            sendMailScreen = FXMLLoader.load(SendMailScreenController.class.getResource("/rec/send mail screen.fxml"));
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
            homescreen = FXMLLoader.load(SendMailScreenController.class.getResource("/rec/home screen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene homeSecne = new Scene(homescreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(homeSecne);
        tempStage.show();
    }
}
