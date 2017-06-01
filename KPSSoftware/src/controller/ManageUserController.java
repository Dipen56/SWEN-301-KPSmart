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
import model.KPSmartModel;
import model.staff.Staff;
import view.DialogBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 25/05/2017.
 */
public class ManageUserController implements Initializable {
    private static KPSmartModel kpSmartModel;
    @FXML
    private ImageView avatar;
    @FXML
    private ImageView userImage;
    @FXML
    private Label userLable;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label userRole;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label emailAddress;
    @FXML
    private Label username;
    @FXML
    private ComboBox selectUser;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private CheckBox changeRoleCheckBox;
    @FXML
    private Button deleteButton;


    public ManageUserController() {
        KPSmartModel.setLoginScreenController(this);
    }

    /**
     * this method is used by the buttons on the left side menu to change change the scene.
     *
     * @param event
     * @throws IOException
     */
    public void changeScenes(ActionEvent event) throws IOException {

        if (event.toString().contains("ChangePassword")) {
            Parent changePasswordScreen = FXMLLoader.load(ManageUserController.class.getResource("/fxml/ChangePassword.fxml"));
            Scene changePasswordScene = new Scene(changePasswordScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(changePasswordScene);
            tempStage.show();
        } else if (event.toString().contains("addNewUser")) {
            Parent addNewUserScreen = FXMLLoader.load(ManageUserController.class.getResource("/fxml/AddNewUser.fxml"));
            Scene addNewUserScene = new Scene(addNewUserScreen);
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.setScene(addNewUserScene);
            tempStage.show();
        } else if (event.toString().contains("logout")) {
            //TODO; POP up dialog box to ask the user if they are sure want to logout
            DialogBox.LogoutyMsg("Logout", "Are you sure to logout?");
            Parent loginScreen = FXMLLoader.load(ManageUserController.class.getResource("/fxml/login screen.fxml"));
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
        if (event.toString().contains("reset")) {
            clearContent(event);
        } else if (event.toString().contains("discard")) {
            returnUserManagement(event);
        } else if (event.toString().contains("update")) {
            if (selectUser.getValue() != null) {
                //TODO create a label and implement value input check.
                boolean vaildUpdate = kpSmartModel.updateStaffInformation((String) selectUser.getValue(), firstNameTextField.getText(),
                        lastNameTextField.getText(), emailTextField.getText(), phoneNumberTextField.getText(), changeRoleCheckBox.isSelected());
                clearContent(event);
            }

        } else if (event.toString().contains("deleteButton")) {
            if (selectUser.getValue() != null) {
                kpSmartModel.deleteUser((String) selectUser.getValue());
                clearContent(event);
            }

        } else if (event.toString().contains("selectUser")) {
            Staff staff = kpSmartModel.getSelectedUser((String) selectUser.getValue());
            userImage.setImage(new Image(ManageUserController.class.getResourceAsStream("/img/" + staff.getUID() + ".png")));
            firstName.setText("First Name: " + staff.getFirstName());
            lastName.setText("Last Name: " + staff.getLastName());
            emailAddress.setText("Email: " + staff.getEmail());
            phoneNumber.setText("Phone: " + staff.getPhoneNumber());
            username.setText("Username: " + staff.getUserName());
            if (staff.getFirstName().equals(kpSmartModel.getCurrentUser().getFirstName()) && staff.getLastName().equals(kpSmartModel.getCurrentUser().getLastName())) {
                deleteButton.setDisable(true);
            } else {
                deleteButton.setDisable(false);
            }
            if (staff.isManager()) {
                userRole.setText("Role: Manager");
                changeRoleCheckBox.setDisable(true);
            } else {
                userRole.setText("Role: Clerk");
                changeRoleCheckBox.setDisable(false);
            }
        }

    }

    /**
     * Everything that should occur before the home is displayed should go in here.
     *
     * @param location
     * @param resources
     */

    public void initialize(URL location, ResourceBundle resources) {
        Staff staff = kpSmartModel.getCurrentUser();
        userLable.setText(staff.getFirstName());
        avatar.setImage(new Image(ManageUserController.class.getResourceAsStream("/img/" + staff.getUID() + ".png")));
        for (Staff s : kpSmartModel.getAllUsers()) {
            selectUser.getItems().add(s.getFirstName() + " " + s.getLastName());
        }


    }

    private void clearContent(ActionEvent event) {
        Parent changePasswordScreen = null;
        try {
            changePasswordScreen = FXMLLoader.load(ManageUserController.class.getResource("/fxml/manage user.fxml"));
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
            userManagementscreen = FXMLLoader.load(ManageUserController.class.getResource("/fxml/user settings.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene userManagementScene = new Scene(userManagementscreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(userManagementScene);
        tempStage.show();
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
