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
    private boolean manager;

    public Staff(int uid, String username, String password, boolean manager) {
        this.setUID(uid);
        this.setUserName(username);
        this.setPassword(password);
        this.setManager(manager);
    }

    // USER ACCOUNT METHODS

    public String editPassword(String oldPassword, String newPassword, String retypePassword) {

        if (!oldPassword.equals(password)) {
            return "Old Passwords not match";
        } else if (!newPassword.equals(retypePassword)) {
            return "New passwords and re typed password do not match";
        } else if (newPassword.matches("[^a-zA-Z0-9]")) {
            // FIXME: 31/05/2017 the regex is not working :(((((((
            return "New passwords must only contain alphanumeric ";
        } else if (newPassword.length() < 6) {
            return "New passwords must be minimum 6 characters";
        } else {
            password = newPassword;
            return "Password change successful";
        }
    }

    /*
     * Getter and Setter methods.
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public boolean isManager() {
        return manager;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "UID=" + UID +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
