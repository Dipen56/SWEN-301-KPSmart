package model.staff;

/**
 * This class represents the manager user. Managers have full permissions in KPS system.
 */
public class Manager extends Staff {

    /**
     * Consturctor
     *
     * @param uid
     * @param username
     * @param password
     * @param manager
     */
    public Manager(int uid, String username, String password, boolean manager) {
        super(uid, username, password, manager);
    }
}
