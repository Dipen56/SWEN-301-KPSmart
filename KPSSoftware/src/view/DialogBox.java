package view;

import controller.HomeScreenController;
import controller.ReviewLogsController;
import controller.SendMailScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.event.*;

import java.io.IOException;


/**
 * Created by Dipen on 21/04/2017. This class is a will only have static method used for dialog box.
 */
public class DialogBox {
    public static boolean tempReturn = false;



    /**
     * this method will open a dialog box given the title and the massage.
     *
     * @param title
     * @param message
     */
    public static void displayMsg(String title, String message) {
        Stage window = new Stage();
        // this makes it so you can't click on the window other then this one
        window.initModality(Modality.APPLICATION_MODAL);
        // sets the title
        window.setTitle(title);
        // sets the weight and height
        window.setMinHeight(200);
        window.setMinWidth(300);
        Label label = new Label();
        // set the massage
        label.setText(message);
        // creates the button
        Button ok = new Button("Ok");
        ok.setOnAction(e -> window.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, ok);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

    public static void LogoutyMsg(String title, String message,ActionEvent events) {
        Stage window = new Stage();
        tempReturn = false;
        // this makes it so you can't click on the window other then this one
        window.initModality(Modality.APPLICATION_MODAL);
        // sets the title
        window.setTitle(title);
        // sets the weight and height
        window.setMinHeight(200);
        window.setMinWidth(300);
        Label label = new Label();
        // set the massage
        label.setText(message);
        // creates the button
        Button ok = new Button("Ok");
        Button close = new Button("Close");

        ok.setOnAction(event -> {
            try {
                Parent loginScreen = FXMLLoader.load(DialogBox.class.getResource("/fxml/LoginScreen.fxml"));
                Scene loginScene = new Scene(loginScreen);
                Stage tempStage = (Stage) ((Node) events.getSource()).getScene().getWindow();
                tempStage.setScene(loginScene);
                tempStage.show();
            }catch(Exception e){

            }

            window.close();
        });
        close.setOnAction(event -> {
            tempReturn = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, ok, close);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }




}
