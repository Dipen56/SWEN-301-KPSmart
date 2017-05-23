package model.event;

import model.mail.Mail;
import model.staff.Staff;

import java.time.LocalDateTime;

/**
 * This class represents an event of mail delivery
 *
 * @author Hector
 * @version 2017/5/20
 */
public class MailDeliveryEvent extends Event {

    private Mail newMail;

    public MailDeliveryEvent(Staff staff, LocalDateTime timeStamp, Mail newMail) {
        super(staff, timeStamp);
        this.newMail = newMail;
    }

    @Override
    public String toString() {
        return "MailDeliveryEvent{" +
                super.toString() +
                ", newMail=" + newMail +
                '}';
    }
}
