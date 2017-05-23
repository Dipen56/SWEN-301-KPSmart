package controller;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import view.DialogBox;

/**
 * Created by Dipen on 25/04/2017.
 */
public class UserManagementController {
    public Label userLable;
    public ImageView avatar;

    /**
     * this method is used by the buttons on the left side menu to change change the scene.
     *
     * @param event
     * @throws IOException
     */
    public void changeScenes(ActionEvent event) throws IOException {

        if (event.toString().contains("ChangePassword")) {
            Parent changePasswordScreen = FXMLLoader.load(UserManagementController.class.getResource("/fxml/ChangePassword.fxml"));
            Scene changePasswordScene = new Scene(changePasswordScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(changePasswordScene);
            tempStage.show();
        } else if (event.toString().contains("ManageUser")) {
            Parent manageUserScreen = FXMLLoader.load(UserManagementController.class.getResource("/fxml/ManageUser.fxml"));
            Scene manageUserScene = new Scene(manageUserScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(manageUserScene);
            tempStage.show();

        } else if (event.toString().contains("AddNewUser")) {
            Parent addNewUserScreen = FXMLLoader.load(UserManagementController.class.getResource("/fxml/AddNewUser.fxml"));
            Scene addNewUserScene = new Scene(addNewUserScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(addNewUserScene);
            tempStage.show();
        } else if (event.toString().contains("Exit")) {
            Parent returnHomeScreen = FXMLLoader.load(UserManagementController.class.getResource("/fxml/home screen.fxml"));
            Scene returnHomeScene = new Scene(returnHomeScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(returnHomeScene);
            tempStage.show();
        } else if (event.toString().contains("logout")) {
            //TODO; POP up dialog box to ask the user if they are sure want to logout
            DialogBox.LogoutyMsg("Logout", "Are you sure to logout?");
            Parent loginScreen = FXMLLoader.load(UserManagementController.class.getResource("/fxml/login screen.fxml"));
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
        if (event.toString().contains("Confirm")) {
            //TODO: retive information from the field and pass to logic...
            System.out.println("comfirmed");
        }else if (event.toString().contains("discard")) {
            clearContent(event);
        } else if (event.toString().contains("Return")) {
            returnUserManagement(event);
        }
    }
    /**
     * Everything that should occur before the home is displayed should go in here.
     *
     * @param location
     * @param resources
     */

    public void initialize(URL location, ResourceBundle resources) {
        //TODO: change this based on real information
        userLable.setText("Clerk Buttercup");
        avatar.setImage(new Image(controller.SendMailScreenController.class.getResourceAsStream("/img/buttercup.png")));
        //TODO: if clerk disable reviewLogs button. reviewLogs.setVisible(false);

    }

    private void clearContent(ActionEvent event) {
        Parent changePasswordScreen = null;
        try {
            changePasswordScreen = FXMLLoader.load(UserManagementController.class.getResource("/fxml/ChangePassword.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene changePasswordScene = new Scene(changePasswordScreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(changePasswordScene);
        tempStage.show();
    }


    private void returnUserManagement(ActionEvent event) {
        Parent userManagementscreen = null;
        try {
            userManagementscreen = FXMLLoader.load(UserManagementController.class.getResource("/fxml/user management.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene userManagementScene = new Scene(userManagementscreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(userManagementScene);
        tempStage.show();
    }
}
