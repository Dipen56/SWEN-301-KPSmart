package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.KPSmartModel;
import model.staff.Staff;
import view.DialogBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 25/04/2017. this class represents the controller for the home screen.
 */
public class HomeScreenController implements Initializable {
    private static KPSmartModel kpSmartModel;
    @FXML
    private Label userLable;
    @FXML
    private Button reviewLogs;
    @FXML
    private ImageView avatar;
    @FXML
    private Button setting;

    /**
     * the constructor is used to set the reference to this class
     */
    public HomeScreenController() {
        KPSmartModel.setLoginScreenController(this);
    }

    /**
     * this method is used to change betweeen screens.
     *
     * @param event
     * @throws IOException
     */
    public void changeScenes(ActionEvent event) throws IOException {

        if (event.toString().contains("sendMail")) {
            Parent sendMailScreen = FXMLLoader.load(HomeScreenController.class.getResource("/fxml/send mail screen.fxml"));
            Scene sendMailScene = new Scene(sendMailScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(sendMailScene);
            tempStage.show();
        } else if (event.toString().contains("routeDiscontinue")) {
            Parent routeDiscontinueScreen = FXMLLoader.load(HomeScreenController.class.getResource("/fxml/route discontinue screen.fxml"));
            Scene routeDiscontinueScene = new Scene(routeDiscontinueScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(routeDiscontinueScene);
            tempStage.show();
        } else if (event.toString().contains("customerPriceUpdate")) {
            Parent priceUpdateScreen = FXMLLoader.load(HomeScreenController.class.getResource("/fxml/price update screen.fxml"));
            Scene priceUpdateScene = new Scene(priceUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(priceUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("transportCostUpdate")) {
            Parent transportCostUpdateScreen = FXMLLoader.load(HomeScreenController.class.getResource("/fxml/transport cost screen.fxml"));
            Scene transportCostUpdateScene = new Scene(transportCostUpdateScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(transportCostUpdateScene);
            tempStage.show();
        } else if (event.toString().contains("newRoute")) {
            Parent newRouteScreen = FXMLLoader.load(HomeScreenController.class.getResource("/fxml/new route screen.fxml"));
            Scene newRouteScene = new Scene(newRouteScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(newRouteScene);
            tempStage.show();
        } else if (event.toString().contains("businessFigures")) {
            Parent businessFiguresScreen = FXMLLoader.load(HomeScreenController.class.getResource("/fxml/business figures screen.fxml"));
            Scene businessFiguresScene = new Scene(businessFiguresScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(businessFiguresScene);
            tempStage.show();
        } else if (event.toString().contains("reviewLogs")) {
            //TODO; still need to build the screen
        } else if (event.toString().contains("logout")) {
            //TODO; POP up dialog box to ask the user if they are sure want to logout
            DialogBox.LogoutyMsg("Logout", "Are you sure to logout?");
            Parent loginScreen = FXMLLoader.load(HomeScreenController.class.getResource("/fxml/login screen.fxml"));
            Scene loginScene = new Scene(loginScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(loginScene);
            tempStage.show();
        } else if (event.toString().contains("setting")) {
            Parent usermanagementScreen = FXMLLoader.load(HomeScreenController.class.getResource("/fxml/user settings.fxml"));
            Scene usermanagementScene = new Scene(usermanagementScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(usermanagementScene);
            tempStage.show();
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
        Staff staff = kpSmartModel.getCurrentUser();
        userLable.setText(staff.getFirstName());
        avatar.setImage(new Image(SendMailScreenController.class.getResourceAsStream("/img/" + staff.getUID() + ".png")));
        ImageView settingImage = new ImageView(new Image(getClass().getResourceAsStream("/img/setting-icon.png")));
        settingImage.setFitHeight(55);
        settingImage.setFitWidth(50);
        setting.setGraphic(settingImage);
        // disables features of the KPS which should not be avabliable certain users types.
        if (!staff.isManager()) {
            reviewLogs.setDisable(true);
            reviewLogs.setVisible(false);
        }
    }

    /**
     * to set the KPSmodels class reference.
     *
     * @param kpsModel
     */
    public static void setKpSmartModel(KPSmartModel kpsModel) {
        kpSmartModel = kpsModel;
    }
}
