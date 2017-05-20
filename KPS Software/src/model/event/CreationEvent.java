package model.event;

import model.staff.Staff;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * # Class description here
 *
 * @author Hector
 * @version 2017/5/20
 */
public class CreationEvent extends Event {

    private Map<String, String> creationFields;

    public CreationEvent(EventType eventType, Staff staff, LocalDateTime timeStamp) {
        super(eventType, staff, timeStamp);
    }
}
