package View;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginGUI {
	
	public static void display(){
		
		Stage welcomeStage = new Stage();
		
		welcomeStage.initModality(Modality.APPLICATION_MODAL);
		welcomeStage.setTitle("Welcome to Kelburn Postal Smart - Please Login");
		welcomeStage.getIcons().add(LoadResources.ICON_IMAGE);
		welcomeStage.setResizable(false);
		
		// loading page resources
        try {
            BorderPane page = (BorderPane) FXMLLoader.load(GUI.class.getResource("/rec/login screen.fxml"));
            Scene scene = new Scene(page);
            welcomeStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // allowing for safe closing of window
        welcomeStage.setOnCloseRequest(e -> {
        	System.out.println("Closing login window");
        	welcomeStage.close();
        });
		welcomeStage.showAndWait();
	}
	
}
