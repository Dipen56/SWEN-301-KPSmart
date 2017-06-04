package model.event;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is the logger and reader of events. The EventLogger acts as the interface of accessing XML files.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class EventLogger {

    // private DatabaseDriver dbDriver;

    /**
     * Current event ID. This id self-increments every time it logs an event
     */
    private int currentId;

    // ======= Mock database, existing in memory. Will be stored in DB in final version ==========
    // key is id, value is the event
    private Map<Integer, Event> events;

    /**
     * Constructor
     */
    public EventLogger() {
        events = new HashMap<>();
        currentId = findCurrentId();
    }

    /**
     * This method logs an event into XML files
     *
     * @param event the event to log
     * @return true if logging succeeded, or false if failed.
     */
    public boolean logEvent(Event event) {
        events.put(currentId, event);
        currentId++;

        // TODO: log into XML

        System.out.println("[EventLog] " + event.toString());

        return true;
    }

    /**
     * This method finds the max id of all events from XML file. This max id can be used for the
     * next event.
     *
     * @return the max id of all events that are recorded in XML
     */
    private int findCurrentId() {
        // TODO: should read the XML file and find the last ID
        return 0;
    }
}
