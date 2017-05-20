package model.event;

import model.staff.Staff;

import java.time.LocalDateTime;

/**
 * This class represents an event of customer price update
 *
 * @author Hector
 * @version 2017/5/20
 */
public class CustomerPriceUpdateEvent extends Event {

    private int routeId;
    private float oldPricePerGram;
    private float oldPricePerVolume;
    private float newPricePerGram;
    private float newPricePerVolume;

    public CustomerPriceUpdateEvent(Staff staff, LocalDateTime timeStamp, int routeId, float oldPricePerGram,
                                    float oldPricePerVolume, float newPricePerGram, float newPricePerVolume) {
        super(staff, timeStamp);
        this.routeId = routeId;
        this.oldPricePerGram = oldPricePerGram;
        this.oldPricePerVolume = oldPricePerVolume;
        this.newPricePerGram = newPricePerGram;
        this.newPricePerVolume = newPricePerVolume;
    }

    @Override
    public String toString() {
        return "CustomerPriceUpdateEvent{" +
                super.toString() +
                ", routeId=" + routeId +
                ", oldPricePerGram=" + oldPricePerGram +
                ", newPricePerGram=" + newPricePerGram +
                ", oldPricePerVolume=" + oldPricePerVolume +
                ", newPricePerVolume=" + newPricePerVolume +
                '}';
    }
}
