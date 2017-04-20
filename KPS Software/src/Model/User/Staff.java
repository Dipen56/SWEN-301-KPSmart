package Model.User;

public abstract class Staff {

    private int UID;
    private String userName;
    private String password;

    public Staff(int uid, String name, String pass) {
        this.setUID(uid);
        this.setUserName(name);
        this.setPassword(pass);
    }

    /**
     * This methods checks if a given login user name and password match
     * those of this staff member. Returns true if both match and false
     * if either does not match.
     *
     * @param name
     * @param pass
     * @return
     */
    public boolean checkLogin(String name, String pass) {
        if (this.userName.equals(name)
                && this.password.equals(pass)) {
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

}
