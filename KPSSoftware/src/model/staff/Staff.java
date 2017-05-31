package model.staff;

import java.util.ArrayList;

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

    private ArrayList<Staff> accounts;
    private Staff currentUser;

    public Staff(int uid, String username, String password, boolean manager) {
        this.setUID(uid);
        this.setUserName(username);
        this.setPassword(password);
        this.setManager(manager);

    }

    public Staff() {
        accounts = new ArrayList<Staff>();
    }

    // USER ACCOUNT METHODS

    /**
     * This methods checks if a given login user name and password match
     * those of this staff member. Returns true if both match and false
     * if either does not match.
     *
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password) {
        for (Staff employee : accounts) {
            if (employee.getUserName().equals(username) && employee.getPassword().equals(password)) {
                currentUser = employee;
                return true;
            }
        }
        return false;
    }
    /*
    public boolean checkLogin(String username, String password) {
        return this.userName.equals(username) && this.password.equals(password);
    }
    */

    public boolean editPassword(String password) {
        boolean edited = false;
        for (Staff employee : accounts) {
            if (employee.getUserName().equals(currentUser.getUserName())
                    && employee.getPassword().equals(currentUser.getPassword())) {
                employee.setPassword(password);
                edited = true;
            }
        }
        currentUser.setPassword(password);
        return edited;
    }

    public boolean editManager(String username, boolean manager) {
        if (currentUser.isManager()) {
            findUser(username).setManager(manager);
        }
        return false;
    }

    public boolean delete(Staff user) {
        boolean removed = false;
        for (Staff employee : accounts) {
            if (employee.getUserName().equals(user.getUserName()) && employee.getPassword().equals(user.getPassword())) {
                accounts.remove(employee);
                removed = true;
                break;
            }
        }
        return removed;
    }

    public void add(Staff user) {
        boolean exists = false;
        for (Staff employee : accounts) {
            if (employee.getUserName().equals(user.getUserName())) {
                exists = true;
            }
        }
        if (!exists) {
            accounts.add(user);
        }
    }

    public void logout() {
        currentUser = null;
    }

    public Staff findUser(String username) {
        for (Staff employee : accounts) {
            if (employee.getUserName().equals(username)) {
                return employee;
            }
        }
        return null;
    }

    public void addUserToList(Staff user) {
        accounts.add(user);
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

    public void setCurrentUser(Staff currentUser) {
        this.currentUser = currentUser;
    }

    public Staff getCurrentUser() {
        return currentUser;
    }

    public ArrayList<Staff> getAccounts() {
        return accounts;
    }

}
