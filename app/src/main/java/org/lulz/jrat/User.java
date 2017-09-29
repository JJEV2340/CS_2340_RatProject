package org.lulz.jrat;

/**
 * User class
 * Jin Woo Lee 09/27
 */
public class User {

    private String userID;
    private String userPW;
    private String userEmail;
    //0 - Admin 1 - User
    private int access_level;

    /**
     * no-arg constructor
     * default access_level is 1 for user.
     */
    public User() {
        this(null, null, null, 1);
    }

    /**
     * constructor
     * @param userID username (used as key in Authentication)
     * @param userPW password
     * @param userEmail email address
     * @param access_level currently Admin(0) or User(1)
     */
    public User(String userID, String userPW, String userEmail, int access_level) {
        this.userID = userID;
        this.userPW = userPW;
        this.userEmail = userEmail;
        this.access_level = access_level;
    }

    /**
     * getter for userID
     * @return userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * getter for userPW
     * @return userPW
     */
    public String getUserPW() {
        return userPW;
    }

    /**
     * getter for email
     * @return userEmail
     */
    public String getEmail() {
        return userEmail;
    }

    /**
     * getter for access level
     * @return int representation of access level
     */
    public int getAccessLevel() {
        return access_level;
    }

    /**
     * setter for userID
     * @param newID new username
     */
    public void setUserID(String newID) {
        userID = newID;
    }

    /**
     * setter for userPW
     * @param newPW new password
     */
    public void setUserPW(String newPW) {
        userPW = newPW;
    }

    /**
     * setter for userEmail
     * @param newEmail new email
     */
    public void setUserEmail(String newEmail) {
        userEmail = newEmail;
    }

    /**
     * setter for access_level
     * @param newStatus new access level
     */
    public void setAccessLevel(int newStatus) {
        access_level = newStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return this.userID.equals(((User) o).userID)
                && this.userPW.equals(((User) o).userPW)
                && this.userEmail.equals(((User) o).userEmail)
                && this.access_level == ((User) o).access_level;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + userID.hashCode();
        result = 31 * result + userPW.hashCode();
        result = 31 * result + userEmail.hashCode();
        result = 31 * result + access_level;
        return result;
    }
}
