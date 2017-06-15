/**
 * Created by angelo on 6/15/2017.
 */

import model.KPSModel;
import model.mail.Mail;
import model.mail.Priority;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class JUnitTest {

    private KPSModel kpsModel;

    @Test
    public void demonstration_deliverMail() {
        kpsModel = new KPSModel();
        String originString = "Christchurch";   // case insensitive
        String destinationString = "Auckland";   // case insensitive
        double weight = 500f;
        double volume = 1000f;
        Priority priority = Priority.Domestic_Standard;

        Mail tempMail = kpsModel.processMail(originString, destinationString, weight, volume, priority);
        assert (tempMail!=null);
    }
}
