package Controller;

/**
 * Created by Dipen on 18/04/2017.
 */
import View.GUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class GUIController {
    private GUI gui;
    //need to add the business class here
    private EventHandler<ActionEvent> actionEvent;

    public GUIController() {
       gui = new GUI(this);
       GUI.launch(GUI.class);
    }

    public void startListners() {
        setActionEventHandler();
    }

    public EventHandler<ActionEvent> getActionEventHandler() {
        return actionEvent;
    }

    private void setActionEventHandler() {
        actionEvent = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            }
        };
    }


}
