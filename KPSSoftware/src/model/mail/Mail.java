package model.mail;

import model.location.Location;
import model.location.NZLocation;
import model.route.Route;

import java.util.List;

/**
 * This class represents a mail
 *
 * @author Hector
 * @version 2017/5/20
 */
public class Mail {

    /**
     * The id of this mail
     */
    public final int id;

    /**
     * The origin of this mail, i.e. where does this mail start
     */
    private NZLocation origin;

    /**
     * the destination of this mail, i.e. where does this mail end
     */
    private Location destination;

    /**
     * The weight of this mail
     */
    private float weight;

    /**
     * The volume of this mail
     */
    private float volume;

    /**
     * The priority of this mail.
     */
    private Priority priority;

    /**
     * A series of routes that connects origin to destination.
     * <p>
     * IMPORTANT: Mail object itself is not responsible for checking the validity of the routes. Make sure it's valid
     * before passing the routes in here.
     */
    private List<Route> routes;

    /*
     * TODO: when Mail is constructed, routes is passed in. We can't afford to find routes inside the constructor.
     *       So before constructor is called, find routes first. If the routes can't be found, then do not construct
     *       the Mail.
     *
     * TODO: when updating the mail (origin, destination, priority), find a new set of routes first, make sure there is
     *       acceptable routes for the new parameters. e.g. the priority is changed to international air, then we need
     *       to check if we can meet that requirement before updating the routes.
     */

    /**
     * Constructor
     *
     * @param id
     * @param origin
     * @param destination
     * @param weight
     * @param volume
     * @param priority
     */
    public Mail(int id, NZLocation origin, Location destination, float weight, float volume, Priority priority) {
        if (origin.id == destination.id) {
            throw new IllegalArgumentException("Invalid mail: origin and destination cannot be the same");
        }

        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.volume = volume;
        this.priority = priority;
    }

    /**
     * @return the weight of this mail
     */
    public float getWeight() {
        return weight;
    }

    /**
     * @return the volume of this mail
     */
    public float getVolume() {
        return volume;
    }

    /**
     * @return the priority of this mail
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @return the series of routes that connects origin to destination.
     */
    public List<Route> getRoutes() {
        return routes;
    }

    /**
     * @return the origin of this mail
     */
    public NZLocation getOrigin() {
        return this.origin;
    }

    /**
     * @return the destination of this mail
     */
    public Location getDestination() {
        return this.destination;
    }

    /**
     * This method assigns a series of routes to this mail. These routes should be connecting origin
     * to destination.
     *
     * @param routes a series of routes that connects origin to destination. IMPORTANT: this method or any method in
     *               Mail class is not responsible for checking the validity of these these routes. Make sure they are
     *               valid before calling setRoutes().
     */
    public void setRoutes(List<Route> routes) {
        if (routes == null || routes.isEmpty()) {
            throw new IllegalArgumentException("Invalid mail: parameter 'routes' is illegal");
        }

        this.routes = routes;
    }

    /**
     * @return the total revenue of this mail
     */
    public float getRevenue() {
        if (routes == null || routes.isEmpty()) {
            throw new UnsupportedOperationException("This mail has no valid routes, please set the routes before calculating");
        }

        // go through the routes assigned to this mail, calculate the revenue of each route, and add them together.
        return routes.stream()
                .map(route -> route.getRevenue(weight, volume))
                .reduce(0.0f, (sum, revenue) -> sum += revenue)
                * priority.getPriceFactor();
    }

    /**
     * @return the total expenditure(cost) of this mail
     */
    public float getExpenditure() {
        if (routes == null || routes.isEmpty()) {
            throw new UnsupportedOperationException("This mail has no valid routes, please set the routes before calculating");
        }

        // go through the routes assigned to this mail, calculate the cost of each route, and add them together.
        return routes.stream()
                .map(route -> route.getExpenditure(weight, volume))
                .reduce(0.0f, (sum, expenditure) -> sum += expenditure);
    }

    /**
     * @return the total duration of this mail.
     */
    public float getDuration() {
        if (routes == null || routes.isEmpty()) {
            throw new UnsupportedOperationException("This mail has no valid routes, please set the routes before calculating");
        }

        // go through the routes assigned to this mail, calculate the duration of each route, and add them together.
        return routes.stream()
                .map(Route::getDuration)
                .reduce(0.0f, (sum, duration) -> sum += duration);
    }

    @Override
    public String toString() {
        return "Mail{" +
                "origin=" + origin +
                ", destination=" + destination +
                ", weight=" + weight +
                ", volume=" + volume +
                ", priority=" + priority +
                '}';
    }
}
