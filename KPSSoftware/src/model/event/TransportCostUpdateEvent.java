package model.event;

import java.time.LocalDateTime;

/**
 * This class represents an event of transport cost update
 *
 * @author Hector
 * @version 2017/5/20
 */
public class TransportCostUpdateEvent extends Event {

    /**
     * The id of the updated route
     */
    private int routeId;

    /**
     * The cost per gram before update
     */
    private float oldCostPerGram;

    /**
     * the cost per volume before update
     */
    private float oldCostPerVolume;

    /**
     * the new cost per gram after update
     */
    private float newCostPerGram;

    /**
     * the new cost per volume after update
     */
    private float newCostPerVolume;

    /**
     * Constructor
     *
     * @param staffId
     * @param timeStamp
     * @param routeId
     * @param oldCostPerGram
     * @param oldCostPerVolume
     * @param newCostPerGram
     * @param newCostPerVolume
     */
    public TransportCostUpdateEvent(int staffId, LocalDateTime timeStamp, int routeId, float oldCostPerGram,
                                    float oldCostPerVolume, float newCostPerGram, float newCostPerVolume) {
        super(staffId, timeStamp);
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
