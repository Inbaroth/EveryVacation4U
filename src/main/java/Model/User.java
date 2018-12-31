package Model;

import java.sql.*;

public abstract class User {

   protected String DBName;

    /**
     * This method search and return the row in the database which is equal to the given userName
     * @param userName - user name to search by
     * @return the founded row
     */
    public RegisteredUser search(String userName){

        String selectQuery = "SELECT * FROM users WHERE user_name = ?";

        String url = "jdbc:sqlite:" + DBName + ".db";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {

            // set the value
            pstmt.setString(1,userName);
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                RegisteredUser registeredUser = new RegisteredUser(rs.getString("user_name"), rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("birthday"),
                        rs.getString("address"),
                        rs.getString("email"),
                        null);
                return registeredUser;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

}
