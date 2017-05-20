package model.staff;

public abstract class Staff {

    private int UID;
    private String userName;
    private String password;

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
        return false;
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

    @Override
    public String toString() {
        return "Staff{" +
                "UID=" + UID +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
