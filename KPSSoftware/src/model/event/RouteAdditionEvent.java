package model.event;

import java.time.LocalDateTime;

/**
 * # Class description here
 *
 * @author Hector
 * @version 2017/5/20
 */
public class RouteAdditionEvent extends Event {

    /**
     * The id of the added route
     */
    private int routeId;

    /**
     * Constructor
     *
     * @param staffId
     * @param timeStamp
     * @param routeId
     */
    public RouteAdditionEvent(int staffId, LocalDateTime timeStamp, int routeId) {
        super(staffId, timeStamp);
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        return "RouteAdditionEvent{" +
                super.toString() +
                ", routeId=" + routeId +
                '}';
    }
}
