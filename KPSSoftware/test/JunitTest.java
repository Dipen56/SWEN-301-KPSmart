import model.KPSModel;
import model.mail.Mail;
import model.mail.Priority;
import org.junit.jupiter.api.Test;


/**
 * Pair testing Dipen and Angelo on 16/06/2017.
 */
public class JunitTest {
    @Test
    public void testScuessfullMailDelivery() {
        KPSModel kpsModel = new KPSModel();

        String originString = "Christchurch";   // case insensitive
        String destinationString = "Auckland";   // case insensitive
        double weight = 500f;
        double volume = 1000f;
        Priority priority = Priority.Domestic_Standard;
        Mail tempMail = kpsModel.processMail(originString, destinationString, weight, volume, priority);
        assert (tempMail != null);
       kpsModel.deliverMail(tempMail);
//        System.out.println(temp);
//        assertTrue(temp == true);


    }

}
