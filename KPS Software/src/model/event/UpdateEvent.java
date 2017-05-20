package model.event;

import model.staff.Staff;

import java.time.LocalDateTime;

/**
 * # Class description here
 *
 * @author Hector
 * @version 2017/5/20
 */
public class UpdateEvent extends Event {
    public UpdateEvent(EventType eventType, Staff staff, LocalDateTime timeStamp) {
        super(eventType, staff, timeStamp);
    }
}
