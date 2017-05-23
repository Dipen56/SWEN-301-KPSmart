package model.route;

import model.location.Location;
import model.transportFirm.TransportFirm;

/**
 * # Class description here
 *
 * @author Hector
 * @version 2017/5/20
 */
public class Route {

    private final Location end_1;
    private final Location end_2;
    private final RouteType routeType;
    private final float duration;
    private final TransportFirm transportFirm;
    private float pricePerGram;
    private float pricePerVolume;
    private float costPerGram;
    private float costPerVolume;

    public Route(Location end_1, Location end_2, RouteType routeType, float duration, TransportFirm transportFirm,
                 float pricePerGram, float pricePerVolume, float costPerGram, float costPerVolume) {
        if (end_1.equals(end_2)) {
            throw new IllegalArgumentException("Invalid route: two ends cannot be the same");
        }

        this.end_1 = end_1;
        this.end_2 = end_2;
        this.routeType = routeType;
        this.duration = duration;
        this.transportFirm = transportFirm;
        this.pricePerGram = pricePerGram;
        this.pricePerVolume = pricePerVolume;
        this.costPerGram = costPerGram;
        this.costPerVolume = costPerVolume;
    }

    public float getPricePerGram() {
        return pricePerGram;
    }

    public void setPricePerGram(float pricePerGram) {
        this.pricePerGram = pricePerGram;
    }

    public float getPricePerVolume() {
        return pricePerVolume;
    }

    public void setPricePerVolume(float pricePerVolume) {
        this.pricePerVolume = pricePerVolume;
    }

    public float getCostPerGram() {
        return costPerGram;
    }

    public void setCostPerGram(float costPerGram) {
        this.costPerGram = costPerGram;
    }

    public float getCostPerVolume() {
        return costPerVolume;
    }

    public void setCostPerVolume(float costPerVolume) {
        this.costPerVolume = costPerVolume;
    }

    public float getRevenue(float weight, float volume) {
        return pricePerGram * weight + pricePerVolume * volume;
    }

    public float getExpenditure(float weight, float volume) {
        return costPerGram * weight + costPerVolume * volume;
    }

    public float getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Route{" +
                "end_1=" + end_1 +
                ", end_2=" + end_2 +
                ", routeType=" + routeType +
                ", duration=" + duration +
                ", transportFirm=" + transportFirm.getName() +
                ", pricePerGram=" + pricePerGram +
                ", pricePerVolume=" + pricePerVolume +
                ", costPerGram=" + costPerGram +
                ", costPerVolume=" + costPerVolume +
                '}';
    }
}
