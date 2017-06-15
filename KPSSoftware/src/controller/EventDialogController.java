package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dipen on 15/06/2017.
 */
public class EventDialogController implements Initializable {
    @FXML
    private Label labelOne;
    @FXML
    private Label labelTwo;
    @FXML
    private Label labelThree;
    @FXML
    private Label labelFour;
    @FXML
    private Label labelFive;
    @FXML
    private Label labelSix;
    @FXML
    private Label labelSeven;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(labelOne);
    }

    public void handleButtons(ActionEvent event) throws IOException {
        if (event.toString().contains("closeButton")) {
            Stage tempStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tempStage.close();
        }
    }
}
