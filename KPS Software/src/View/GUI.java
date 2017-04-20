package View;/**
 * Created by Dipen on 18/04/2017.
 */

import Controller.GUIController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class GUI extends Application {
    private static GUIController controller;
    private static LoginScreen loginScene;
    private static Stage primaryStage;

    public GUI(GUIController controller) {
        this.controller = controller;


    }

    public GUI() {

    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Kelburn Postal Smart - Team Buttercup");
        primaryStage.getIcons().add(LoadResources.ICON_IMAGE);
        primaryStage.setResizable(false);
        displayLoginScreen();
        primaryStage.show();
    }

    public void displayLoginScreen() {
        loginScene = new LoginScreen();
        primaryStage.setScene(loginScene.getScene());
    }

}
