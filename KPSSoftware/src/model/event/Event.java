package model.event;

import java.time.LocalDateTime;

/**
 * This class is the base class of all events
 *
 * @author Hector
 * @version 2017/5/20
 */
public abstract class Event {

    /**
     * tThe staffId who logged this event
     */
    private int staffId;

    /**
     * The time stamp of when this event happened
     */
    private LocalDateTime timeStamp;

    /**
     * Constructor
     *
     * @param staffId
     * @param timeStamp
     */
    public Event(int staffId, LocalDateTime timeStamp) {
        this.staffId = staffId;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "staffId=" + staffId + ", timeStamp=" + timeStamp;
    }
}
