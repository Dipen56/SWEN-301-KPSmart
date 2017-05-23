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

    private int mailID;
    private NZLocation origin;
    private Location destination;
    private float weight;
    private float volume;
    private Priority priority;

    /**
     * A series of routes that connects origin to destionation.
     * <p>
     * IMPORTANT: Mail object itself is not responsible for checking the validity of the routes. Make sure it's valid
     * before constructing or updating the Mail object.
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

    public Mail(int mailID, NZLocation origin, Location destination, float weight, float volume, Priority priority, List<Route> routes) {
        if (origin.equals(destination)) {
            throw new IllegalArgumentException("Invalid mail: origin and destination cannot be the same");
        } else if (routes == null || routes.isEmpty()) {
            throw new IllegalArgumentException("Invalid mail: parameter 'routes' is illegal");
        }

        this.mailID = mailID;
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.volume = volume;
        this.priority = priority;
        this.routes = routes;
    }

    public float getRevenue() {
        return routes.stream()
                .map(route -> route.getRevenue(weight, volume))
                .reduce(0.0f, (sum, revenue) -> sum += revenue)
                * priority.getPriceFactor();
    }


    public float getExpenditure() {
        return routes.stream()
                .map(route -> route.getExpenditure(weight, volume))
                .reduce(0.0f, (sum, expenditure) -> sum += expenditure);
    }

    public float getDuration() {
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
