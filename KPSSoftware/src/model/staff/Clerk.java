package model.staff;

/**
 * This class represents the clerk user. Clerks have fewer permissions than Managers in KPS system.
 */
public class Clerk extends Staff {

    /**
     * Constructor
     *
     * @param uid
     * @param username
     * @param password
     * @param manager
     */
    public Clerk(int uid, String username, String password, boolean manager) {
        super(uid, username, password, manager);
    }
}
