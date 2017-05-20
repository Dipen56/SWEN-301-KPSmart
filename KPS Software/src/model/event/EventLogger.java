package model.event;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a logger and reader of events.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class EventLogger {

    // private DatabaseDriver dbDriver;

    private int currentId;

    // ======= Mock database, existing in memory. Will be stored in DB in final version ==========
    // key is id, value is the event
    private Map<Integer, Event> events;

    public EventLogger() {
        events = new HashMap<>();
        currentId = findCurrentId();
    }

    public boolean logEvent(Event event) {
        events.put(currentId, event);
        currentId++;

        System.out.println("[EventLog] " + event.toString());

        return true;
    }

    private int findCurrentId() {
        // TODO: should read the XML file and find the last ID
        return 0;
    }

}
