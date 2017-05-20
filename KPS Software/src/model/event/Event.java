package model.event;

import model.staff.Staff;

import java.time.LocalDateTime;

/**
 * This class is the base class of all events
 *
 * @author Hector
 * @version 2017/5/20
 */
public abstract class Event {

    private EventType eventType;
    private Staff staff;
    private LocalDateTime timeStamp;

    public Event(EventType eventType, Staff staff, LocalDateTime timeStamp) {
        this.eventType = eventType;
        this.staff = staff;
        this.timeStamp = timeStamp;
    }
}
