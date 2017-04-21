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
       setGui(new GUI(this));
       GUI.launch(GUI.class);
    }

    /*
     * Setting up action event handlers
     */
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

    /*
     * Getter and setter for the GUI
     */
	public GUI getGui() {
		return gui;
	}
	public void setGui(GUI gui) {
		this.gui = gui;
	}
	
}
