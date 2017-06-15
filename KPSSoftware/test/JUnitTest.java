/**
 * Created by angelo on 6/15/2017.
 */

import model.KPSModel;
import model.mail.Mail;
import model.mail.Priority;
import model.route.RouteType;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class JUnitTest {

    @Test
    public void demonstration_deliverMail() {
        KPSModel kpsModel = new KPSModel();
        String originString = "Christchurch";   // case insensitive
        String destinationString = "Auckland";   // case insensitive
        double weight = 500f;
        double volume = 1000f;
        Priority priority = Priority.Domestic_Standard;
        Mail tempMail = kpsModel.processMail(originString, destinationString, weight, volume, priority);
        assert (tempMail!=null);
    }

    @Test
    public void demonstration_noDuplicateRoutes() {
        KPSModel kpsModel = new KPSModel();
        String startString = "Christchurch";
        String endString = "Auckland";
        RouteType routeType = RouteType.Land;
        double duration = 3.5f;
        String transportFirm = "FedEx";
        double pricePerGram = 2.5f;
        double pricePerVolume = 2.6f;
        double costPerGram = 2.7f;
        double costPerVolume = 2.8f;
        boolean newRoute = kpsModel.addRoute(startString, endString, routeType, duration, transportFirm, pricePerGram, pricePerVolume, costPerGram, costPerVolume);
        assertFalse(newRoute);
    }
}
