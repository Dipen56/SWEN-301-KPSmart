/**
 * Created by angelo on 6/15/2017.
 */

import model.KPSModel;
import model.mail.Mail;
import model.mail.Priority;
import org.junit.Test;
import static org.junit.Assert.*;

public class JUnitTest {

    private KPSModel kpsModel;

    @Test
    public void demonstration_deliverMail() {
        String originString = "wellington";   // case insensitive
        String destinationString = "moscow";   // case insensitive
        double weight = 500f;
        double volume = 1000f;
        Priority priority = Priority.International_Air;

        Mail tempMail = kpsModel.processMail(originString, destinationString, weight, volume, priority);

        if (tempMail == null) {
            assertFalse();
        } else {
            // We can deliver the mail
            boolean result = kpsModel.deliverMail(tempMail);
            // note we must deliver the mail first, otherwise there is no such mail in database.
            double revenue = kpsModel.getMailRevenue(tempMail.id);
            double expenditure = kpsModel.getMailExpenditure(tempMail.id);
            assertTrue();
        }
    }
}
