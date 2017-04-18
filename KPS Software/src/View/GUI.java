package View;/**
 * Created by Dipen on 18/04/2017.
 */

import Controller.GUIController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {
    private static GUIController controller;
    Scene loginScene;

    public  GUI(GUIController controller){
        this.controller = controller;

    }
    public GUI() {

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kelburn Postal Smart - Buttercup");
        primaryStage.getIcons().add(LoadResources.ICON_IMAGE);
        primaryStage.show();

    }
    //public static void main(String[] args) {
    // launch(args);
    // }
}
