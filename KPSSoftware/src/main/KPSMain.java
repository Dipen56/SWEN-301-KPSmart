package main;

import model.KPSmartModel;
import view.GUI;

/**
 * Created by Dipen on 18/04/2017.
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
        KPSDatabase db = new KPSDatabase();
        new KPSMain().run();
    }
}
