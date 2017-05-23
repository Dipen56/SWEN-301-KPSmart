package model.event;

import model.staff.Staff;

import java.time.LocalDateTime;

/**
 * This class represents an event of transport cost update
 *
 * @author Hector
 * @version 2017/5/20
 */
public class TransportCostUpdateEvent extends Event {

    private int routeId;
    private float oldCostPerGram;
    private float oldCostPerVolume;
    private float newCostPerGram;
    private float newCostPerVolume;

    public TransportCostUpdateEvent(Staff staff, LocalDateTime timeStamp, int routeId, float oldCostPerGram,
                                    float oldCostPerVolume, float newCostPerGram, float newCostPerVolume) {
        super(staff, timeStamp);
        this.routeId = routeId;
        this.oldCostPerGram = oldCostPerGram;
        this.oldCostPerVolume = oldCostPerVolume;
        this.newCostPerGram = newCostPerGram;
        this.newCostPerVolume = newCostPerVolume;
    }

    @Override
    public String toString() {
        return "TransportCostUpdateEvent{" +
                super.toString() +
                ", routeId=" + routeId +
                ", oldCostPerGram=" + oldCostPerGram +
                ", newCostPerGram=" + newCostPerGram +
                ", oldCostPerVolume=" + oldCostPerVolume +
                ", newCostPerVolume=" + newCostPerVolume +
                "}";
    }
}
