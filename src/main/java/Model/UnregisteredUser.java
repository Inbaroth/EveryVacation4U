package Model;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UnregisteredUser extends User {

    public UnregisteredUser(String databaseName) {
        this.DBName = databaseName;
    }

    /**
     * This method insert a new row to Users table with the given data
     * @param registeredUser  data of the new row which need to be added
     */
    public void createUser(RegisteredUser registeredUser){

        String insertStatement = "INSERT INTO Users (user_name, password, first_name, last_name, birthday, address, email, profilePicture) VALUES (?,?,?,?,?,?,?,?)";

        String url = "jdbc:sqlite:" + DBName + ".db";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setString(1, registeredUser.getUserName()); // registeredUser name
            pstmt.setString(2, registeredUser.getPassword()); // password
            pstmt.setString(3, registeredUser.getFirstName()); // first name
            pstmt.setString(4, registeredUser.getLastName()); // last name
            pstmt.setString(5, registeredUser.getBirthday()); // birthday
            pstmt.setString(6, registeredUser.getAddress()); // address
            pstmt.setString(7, registeredUser.getEmail()); // email
            pstmt.setString(8,"picture"); // picture
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String signIn(String userName, String password){
        RegisteredUser ans = super.search(userName);
        if (ans != null){
            if (!password.equals(ans.getPassword())) {
                return "הסיסמאות אינן תואמות";
            }
            else{
                return userName;
            }
        }
        else{
            return "שם משתמש לא קיים במערכת";
        }
    }

}
