package model.location;

/**
 * This class is the base class of any location. A location class represents a location, which can be used as the
 * origin or destination of a mail.
 *
 * @author Hector
 * @version 2017/5/20
 */
public abstract class Location {

    private final int id;

    public Location(int locationId) {
        this.id = locationId;
    }

    public abstract String getLocationName();
}
