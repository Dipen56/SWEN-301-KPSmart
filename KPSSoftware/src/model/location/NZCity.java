package model.location;

/**
 * This enum class represents a city in New Zealand.
 *
 * @author Hector
 * @version 2017/5/20
 */
public enum NZCity {
    Auckland, Hamilton, Rotorua, Palmerston_North, Wellington, Christchurch, Dunedin;

    @Override
    public String toString() {
        return this.name().replace("_", " ");
    }
}
