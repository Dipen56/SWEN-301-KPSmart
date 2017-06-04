package model.location;

/**
 * This class represents a location in New Zealand. Any location used as the origin of mails must be a NZLocation.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class NZLocation extends Location {

    /**
     * The name of this New Zealand location
     */
    private final NZCity city;

    /**
     * Constructor
     *
     * @param locationId
     * @param city
     */
    public NZLocation(int locationId, NZCity city) {
        super(locationId);
        this.city = city;
    }

    @Override
    public String getLocationName() {
        return city.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NZLocation))
            return false;

        NZLocation that = (NZLocation) o;

        return city == that.city;
    }

    @Override
    public int hashCode() {
        return city.hashCode();
    }

    @Override
    public String toString() {
        return "NZLocation{" +
                "location=" + city +
                '}';
    }
}
