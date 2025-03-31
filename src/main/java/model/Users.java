package model;
/**
 * Represents a type of report that includes the appointment type and the total number of appointments.
 */
public class Users {

    public int userID;
    public String userName;
    public String userPassword;

    public Users() {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * returns userID
     */
    public int getUserID() {

        return userID;
    }

    /**
     * returns userName
     */
    public String getUserName() {

        return userName;
    }

    /**
     * returns userPassword for user login
     */
    public String getUserPassword() {

        return userPassword;
    }
}