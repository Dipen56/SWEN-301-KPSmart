package model.event;

import java.time.LocalDateTime;

/**
 * This class represents an event of route deletion (transport discontinued)
 *
 * @author Hector
 * @version 2017/5/20
 */
public class RouteDeletionEvent extends Event {

    /**
     * The id of deleted route
     */
    private int routeId;

    /**
     * Constructor
     *
     * @param staffId
     * @param timeStamp
     * @param routeId
     */
    public RouteDeletionEvent(int staffId, LocalDateTime timeStamp, int routeId) {
        super(staffId, timeStamp);
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        return "RouteDeletionEvent{" +
                super.toString() +
                ", routeId=" + routeId +
                '}';
    }
}
