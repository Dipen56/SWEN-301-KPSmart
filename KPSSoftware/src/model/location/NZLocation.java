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
    private final NZCity cityName;

    /**
     * Constructor
     *
     * @param locationId
     * @param cityName
     */
    public NZLocation(int locationId, NZCity cityName) {
        super(locationId);
        this.cityName = cityName;
    }

    @Override
    public String getLocationName() {
        return cityName.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NZLocation))
            return false;

        NZLocation that = (NZLocation) o;

        return cityName == that.cityName;
    }

    @Override
    public int hashCode() {
        return cityName.hashCode();
    }

    @Override
    public String toString() {
        return "NZLocation{" +
                "location=" + cityName +
                '}';
    }
}
