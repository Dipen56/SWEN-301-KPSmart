package model.route;

import model.location.InternationalLocation;
import model.location.Location;
import model.transportFirm.TransportFirm;

/**
 * This class represents a postal route between two locations.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class Route {

    /**
     * The id of this route
     */
    public final int id;

    /**
     * The type of this route
     */
    public final RouteType routeType;

    /**
     * The start location of this route
     */
    private final Location start;

    /**
     * The end location of this route
     */
    private final Location end;

    /**
     * The duration that this route takes to go from start to end
     */
    private final float duration;

    /**
     * The company operating on thie route
     */
    private final TransportFirm transportFirm;

    /**
     * The price per gram
     */
    private float pricePerGram;

    /**
     * The price per volume
     */
    private float pricePerVolume;

    /**
     * The cost per gram
     */
    private float costPerGram;

    /**
     * The cost per volume
     */
    private float costPerVolume;

    /**
     * Constructor
     *
     * @param id
     * @param start
     * @param end
     * @param routeType
     * @param duration
     * @param transportFirm
     * @param pricePerGram
     * @param pricePerVolume
     * @param costPerGram
     * @param costPerVolume
     */
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

    /**
     * @return the start location
     */
    public Location getStartLocation() {
        return start;
    }

    /**
     * @return the end location
     */
    public Location getEndLocation() {
        return end;
    }

    /**
     * @return the price per gram
     */
    public float getPricePerGram() {
        return pricePerGram;
    }

    /**
     * Set the price per gram
     *
     * @param pricePerGram
     */
    public void setPricePerGram(float pricePerGram) {
        this.pricePerGram = pricePerGram;
    }

    /**
     * @return the price per volume
     */
    public float getPricePerVolume() {
        return pricePerVolume;
    }

    /**
     * Set the price per volume
     *
     * @param pricePerVolume
     */
    public void setPricePerVolume(float pricePerVolume) {
        this.pricePerVolume = pricePerVolume;
    }

    /**
     * @return the cost per gram
     */
    public float getCostPerGram() {
        return costPerGram;
    }

    /**
     * Set the cost per gram
     *
     * @param costPerGram
     */
    public void setCostPerGram(float costPerGram) {
        this.costPerGram = costPerGram;
    }

    /**
     * @return the cost per volume
     */
    public float getCostPerVolume() {
        return costPerVolume;
    }

    /**
     * Set the cost per volume
     *
     * @param costPerVolume
     */
    public void setCostPerVolume(float costPerVolume) {
        this.costPerVolume = costPerVolume;
    }

    /**
     * Calculate the revenue of this route.
     *
     * @param weight the weight used to calculate the mail's revenue
     * @param volume the volume used to calculate the mail's revenue
     * @return the revenue of this route.
     */
    public float getRevenue(float weight, float volume) {
        return pricePerGram * weight + pricePerVolume * volume;
    }

    /**
     * Calculate the expenditure(cost) of this route.
     *
     * @param weight the weight used to calculate the mail's expenditure(cost)
     * @param volume the volume used to calculate the mail's expenditure(cost)
     * @return the expenditure(cost) of this route.
     */
    public float getExpenditure(float weight, float volume) {
        return costPerGram * weight + costPerVolume * volume;
    }

    /**
     * Calculate the profit of this route.
     *
     * @param weight the weight used to calculate the mail's profit
     * @param volume the volume used to calculate the mail's profit
     * @return the profit of this route.
     */
    public float getNetProfit(float weight, float volume) {
        return getRevenue(weight, volume) - getExpenditure(weight, volume);
    }

    /**
     * @return the duration that this route takes to go from start to end
     */
    public float getDuration() {
        return duration;
    }

    /**
     * @return true if this route is international route, or false if it's domestic
     */
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
