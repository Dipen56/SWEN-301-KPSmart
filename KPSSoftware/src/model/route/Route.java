package model.route;

import model.location.InternationalLocation;
import model.location.Location;
import model.transportFirm.TransportFirm;

/**
 * This class represents a postal route between two cities.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class Route {

    public final int id;
    public final RouteType routeType;

    private final Location start;
    private final Location end;
    private final float duration;
    private final TransportFirm transportFirm;

    private float pricePerGram;
    private float pricePerVolume;
    private float costPerGram;
    private float costPerVolume;

    public Route(int id, Location start, Location end, RouteType routeType, float duration, TransportFirm transportFirm,
                 float pricePerGram, float pricePerVolume, float costPerGram, float costPerVolume) {
        if (start.id == end.id) {
            throw new IllegalArgumentException("Invalid route: two ends cannot be the same");
        }

        this.id = id;
        this.start = start;
        this.end = end;
        this.routeType = routeType;
        this.duration = duration;
        this.transportFirm = transportFirm;
        this.pricePerGram = pricePerGram;
        this.pricePerVolume = pricePerVolume;
        this.costPerGram = costPerGram;
        this.costPerVolume = costPerVolume;
    }

    public Location getStartLocation() {
        return start;
    }

    public Location getEndLocation() {
        return end;
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

    public float getNetProfit(float weight, float volume) {
        return getRevenue(weight, volume) - getExpenditure(weight, volume);
    }

    public float getDuration() {
        return duration;
    }

    public boolean isInternationalRoute() {
        return start instanceof InternationalLocation || end instanceof InternationalLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Route))
            return false;

        Route route = (Route) o;

        return id == route.id
                && Float.compare(route.duration, duration) == 0
                && Float.compare(route.pricePerGram, pricePerGram) == 0
                && Float.compare(route.pricePerVolume, pricePerVolume) == 0
                && Float.compare(route.costPerGram, costPerGram) == 0
                && Float.compare(route.costPerVolume, costPerVolume) == 0
                && routeType == route.routeType
                && start.equals(route.start)
                && end.equals(route.end)
                && transportFirm.equals(route.transportFirm);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + routeType.hashCode();
        result = 31 * result + start.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + (duration != +0.0f ? Float.floatToIntBits(duration) : 0);
        result = 31 * result + transportFirm.hashCode();
        result = 31 * result + (pricePerGram != +0.0f ? Float.floatToIntBits(pricePerGram) : 0);
        result = 31 * result + (pricePerVolume != +0.0f ? Float.floatToIntBits(pricePerVolume) : 0);
        result = 31 * result + (costPerGram != +0.0f ? Float.floatToIntBits(costPerGram) : 0);
        result = 31 * result + (costPerVolume != +0.0f ? Float.floatToIntBits(costPerVolume) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
//                ", routeType=" + routeType +
//                ", duration=" + duration +
//                ", transportFirm=" + transportFirm.getName() +
//                ", pricePerGram=" + pricePerGram +
//                ", pricePerVolume=" + pricePerVolume +
//                ", costPerGram=" + costPerGram +
//                ", costPerVolume=" + costPerVolume +
                '}';
    }
}
