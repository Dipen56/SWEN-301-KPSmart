package model.event;

import model.staff.Staff;

import java.time.LocalDateTime;

/**
 * This class represents an event of route deletion (transport discontinued)
 *
 * @author Hector
 * @version 2017/5/20
 */
public class RouteDeletionEvent extends Event {

    private int deletedRouteId;

    public RouteDeletionEvent(Staff staff, LocalDateTime timeStamp, int deletedRouteId) {
        super(staff, timeStamp);
        this.deletedRouteId = deletedRouteId;
    }

    @Override
    public String toString() {
        return "RouteDeletionEvent{" +
                super.toString() +
                ", deletedRouteId=" + deletedRouteId +
                '}';
    }
}
