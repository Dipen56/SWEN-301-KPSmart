package model.event;

import model.route.Route;
import model.staff.Staff;

import java.time.LocalDateTime;

/**
 * # Class description here
 *
 * @author Hector
 * @version 2017/5/20
 */
public class RouteAdditionEvent extends Event {

    private Route newRoute;

    public RouteAdditionEvent(Staff staff, LocalDateTime timeStamp, Route newRoute) {
        super(staff, timeStamp);
        this.newRoute = newRoute;
    }

    @Override
    public String toString() {
        return "RouteAdditionEvent{" +
                super.toString() +
                ", newRoute=" + newRoute +
                '}';
    }
}
