package model.event;

import java.time.LocalDateTime;

/**
 * This class represents the event of customer price update
 *
 * @author Hector
 * @version 2017/5/20
 */
public class CustomerPriceUpdateEvent extends Event {
    /**
     * The id of the updated route
     */
    private int routeId;

    /**
     * The price per gram before update
     */
    private float oldPricePerGram;

    /**
     * the price per volume before update
     */
    private float oldPricePerVolume;

    /**
     * the new price per gram after update
     */
    private float newPricePerGram;

    /**
     * the new price per volume after update
     */
    private float newPricePerVolume;

    /**
     * Constructor
     *
     * @param staffId
     * @param timeStamp
     * @param routeId
     * @param oldPricePerGram
     * @param oldPricePerVolume
     * @param newPricePerGram
     * @param newPricePerVolume
     */
    public CustomerPriceUpdateEvent(int staffId, LocalDateTime timeStamp, int routeId, float oldPricePerGram,
                                    float oldPricePerVolume, float newPricePerGram, float newPricePerVolume) {
        super(staffId, timeStamp);
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
