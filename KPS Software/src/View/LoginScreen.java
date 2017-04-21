package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Created by Dipen on 21/04/2017.
 */
public class LoginScreen {
    private Scene scene;

    public LoginScreen() {
        try {
            BorderPane page = (BorderPane) FXMLLoader.load(GUI.class.getResource("/rec/login screen.fxml"));
            scene = new Scene(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return scene;
    }
}
