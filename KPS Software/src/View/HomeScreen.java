package View;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HomeScreen {
	
	public static Stage getStage(){
		
		Stage homeStage = new Stage();
		
		homeStage.setTitle("Welcome to Kelburn Postal Smart - Team Buttercup");
		homeStage.getIcons().add(LoadResources.ICON_IMAGE);
		homeStage.setResizable(false);
		
		// loading page resources
        try {
            HBox page = (HBox) FXMLLoader.load(GUI.class.getResource("/rec/home screen.fxml"));
            Scene scene = new Scene(page);
            homeStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // allowing for safe closing of window
        homeStage.setOnCloseRequest(e -> {
        	System.out.println("Closing KPS window");
        	homeStage.close();
        });
		return homeStage;
	}
	
}
