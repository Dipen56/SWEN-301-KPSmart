package model.staff;

/**
 * User class of the KPS, which has a username,
 * password and a boolean to represent manager status.
 *
 * @author Betty
 */

public abstract class Staff {
    private int UID;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public Staff(int uid, String username, String password) {
        this.setUID(uid);
        this.setUserName(username);
        this.setPassword(password);
    }

    /**
     * This methods checks if a given login user name and password match
     * those of this staff member. Returns true if both match and false
     * if either does not match.
     *
     * @param username
     * @param password
     * @return
     */

    public boolean checkLogin(String username, String password) {
        if (this.userName.equals(username)
                && this.password.equals(password)) {
            return true;
        }
        else return false;
    }

    /*
     * Getter and Setter methods.
     *
     */
    public int getUID() {
        return UID;
    }

    public void setUID(int uID) {
        UID = uID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String userName) {
        this.firstName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

/*    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public boolean isManager() {
        return manager;
    }
*/

    @Override
    public String toString() {
        return "Staff{" +
                "UID=" + UID +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
