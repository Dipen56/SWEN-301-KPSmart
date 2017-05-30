package main;

import model.KPSmartModel;
import model.database.KPSDatabase;
import view.GUI;

/**
 * Created by Dipen on 18/04/2017. This class is used to start up the KPS Application.
 */
public class KPSMain {

    private KPSmartModel kpSmartModel;

    public KPSMain() {
        kpSmartModel = new KPSmartModel();
    }

    public void run() {
        javafx.application.Application.launch(GUI.class);
    }

    public static void main(String[] args) {
       // KPSDatabase db = new KPSDatabase();
        new KPSMain().run();
    }
}
