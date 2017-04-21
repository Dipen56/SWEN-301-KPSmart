package View;

/**
 * Created by Dipen on 18/04/2017.
 */

import Controller.GUIController;
import javafx.application.Application;
import javafx.stage.Stage;

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
        // displaying home screen page
    	stage = HomeScreen.getStage();
    	primaryStage = stage;
    	primaryStage.show();
    	// open login gui
        //TODO: user login authentication with database
        LoginGUI.display();
    }

    public void displayLoginScreen() {
        loginScene = new LoginScreen();
        primaryStage.setScene(loginScene.getScene());
    }

}
