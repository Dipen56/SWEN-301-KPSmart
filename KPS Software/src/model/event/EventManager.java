package model.event;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a logger and reader of events.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class EventManager {

    // private DatabaseDriver dbDriver;

    private int incrementingId;

    // ======= Mock database, existing in memory. Will be stored in DB in final version ==========
    private Map<Integer, Event> events;

    public EventManager() {
        events = new HashMap<>();
        incrementingId = 0;
    }

    public boolean logEvent(Event event) {
        events.put(incrementingId, event);
        incrementingId++;
        return true;
    }


}
