package DAO;

import main.DBConnection;
import model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userAccess extends Users {

    public userAccess(int userID, String userName, String userPassword) {
        super();
    }

    /**
     * User login validation.
     */
    public static int validateUser(String username, String password) {
        int userID = -1; // Default value if no user is found

        try {
            String sqlQuery = "SELECT * FROM users WHERE user_name = ? AND password = ?";

            // Use prepared statement with parameters to avoid SQL injection
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlQuery);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            // Check if the result set contains any rows
            if (rs.next()) {
                // Now it's safe to access the ResultSet
                if (rs.getString("User_Name").equals(username)) {
                    if (rs.getString("Password").equals(password)) {
                        userID = rs.getInt("User_ID");
                    }
                }
            } else {
                // Handle the case where no matching user is found (optional)
                System.out.println("No user found with the provided credentials.");
            }
        } catch (SQLException e) {
            // Handle SQLException
            e.printStackTrace();
        }

        return userID;
    }


    /**
     * Retrieves all user data from the database and populates and returns an ObservableList.
     */
    public static ObservableList<userAccess> getAllUsers() throws SQLException {
        ObservableList<userAccess> usersObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from users";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            userAccess user = new userAccess(userID, userName, userPassword);
            usersObservableList.add(user);
        }
        return usersObservableList;
    }
}
