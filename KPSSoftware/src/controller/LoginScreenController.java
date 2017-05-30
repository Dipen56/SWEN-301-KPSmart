package controller;

/**
 * Created by Dipen on 18/04/2017.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.KPSmartModel;
import model.database.KPSDatabase;

import java.io.IOException;


public class LoginScreenController {
    private static KPSmartModel kpSmartModel;
    //this variables are links to there FXML counterparts
    @FXML
    private TextField usernameTextfield;
    @FXML
    private PasswordField passwordTextfield;
    @FXML
    private Label authticationError;
    public LoginScreenController(){
        KPSmartModel.setLoginScreenController(this);
    }
    public static void setKpSmartModel(KPSmartModel kpsModel) {
        kpSmartModel = kpsModel;
    }

    /**
     * this method is connected to the login button as well is the text fields, and is used to perform the steps
     * required for logging into the system.
     */
    @FXML
    public void performLogin(ActionEvent event) throws IOException {
        String username = usernameTextfield.getText();
        String password = passwordTextfield.getText();
        // checks given user name and password against database
        if (kpSmartModel.authenticateLogin(username,password)) {

//            Parent homescreen = FXMLLoader.load(LoginScreenController.class.getResource("/fxml/home screen.fxml"));
//            Scene homeSecne = new Scene(homescreen);
//            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            tempStage.setScene(homeSecne);
//            tempStage.show();
        } else {
            authticationError.setText("Check username and password and try again");
            //DialogBox.displayMsg("Invaild Input", "Please Check your Username and Password are correct and try again");
        }
    }
}
