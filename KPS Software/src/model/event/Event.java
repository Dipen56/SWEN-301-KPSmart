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

    private Staff staff;
    private LocalDateTime timeStamp;

    public Event(Staff staff, LocalDateTime timeStamp) {
        this.staff = staff;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "staff=" + staff + ", timeStamp=" + timeStamp;
    }
}
