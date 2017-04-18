package View;
/**
 * Created by Dipen on 18/04/2017. This class is used to load images in a static way to increase performance.
 */
import javafx.scene.image.Image;

public class LoadResources {
    public static Image ICON_IMAGE;
    public static String STAYLE_SHEET;

    static {
        ICON_IMAGE = loadImage("/rec/KPS.png");
        STAYLE_SHEET=LoadResources.class.getResource("/rec/style.css").toExternalForm();
    }


    private static Image loadImage(String imageName){
        return new Image(LoadResources.class.getResourceAsStream(imageName));

    }



}
