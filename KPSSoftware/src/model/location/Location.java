package model.location;

/**
 * This class is the base class of any location. A location class represents a location, which can be used as the
 * origin or destination of a mail.
 *
 * @author Hector
 * @version 2017/5/20
 */
public abstract class Location {

    public final int id;

    /**
     * Constructor
     *
     * @param locationId
     */
    public Location(int locationId) {
        this.id = locationId;
    }

    /**
     * @return the name of this location
     */
    public abstract String getLocationName();
}
