package Controller;

/**
 * Created by Dipen on 18/04/2017.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;


public class LoginScreenController {
    //this variables are links to there FXML counterparts
    public TextField usernameTextfield;
    public PasswordField passwordTextfield;


    /**
     * this method is connected to the login button as well is the text fields, and is used to perform the steps
     * required for logging into the system.
     */
    @FXML
    public void performLogin(ActionEvent event) throws IOException {
        // String userName = usernameTextfield.getText();
        //  String password = passwordTextfield.getText();
        //System.out.println(userName + " " + password);
//        if (vaild user){
//            Parent homescreen = FXMLLoader.load(GUI.class.getResource("/rec/home screen.fxml"));
//            Scene homeSecne = new Scene(homescreen);
//            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            tempStage.setScene(homeSecne);
//            tempStage.show();
//        }else{
//            DialogBox.displayMsg("Invaild Input", "Please Check your Username and Password are correct and try again");
//        }
        //only here for testing can be deleted later...
        Parent homescreen = FXMLLoader.load(LoginScreenController.class.getResource("/rec/home screen.fxml"));
        Scene homeSecne = new Scene(homescreen);
        Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        tempStage.setScene(homeSecne);
        tempStage.show();

    }


}
