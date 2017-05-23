package model.mail;

/**
 * This Enum class represents several types of priority
 *
 * @author Hector
 * @version 2017/5/20
 */
public enum Priority {
    International_Air,
    International_Standard,
    Domestic_Air,
    Domestic_Standard;


    public float getPriceFactor() {
        switch (this) {
            case International_Air:
            case International_Standard:
                return 1.2f;
            case Domestic_Air:
            case Domestic_Standard:
                return 1.0f;
            default:
                return 0;  // dead code
        }
    }

    public boolean isInternational() {
        switch (this) {
            case International_Air:
            case International_Standard:
                return true;
            case Domestic_Air:
            case Domestic_Standard:
                return false;
            default:
                return false;  // dead code
        }
    }

    public boolean isAir() {
        switch (this) {
            case International_Air:
            case Domestic_Air:
                return true;
            case International_Standard:
            case Domestic_Standard:
                return false;
            default:
                return false;  // dead code
        }
    }
}
