package Model;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.regex.Pattern;

public class UserModel extends Model {

    public UserModel(String DBName) {
        super(DBName);
    }

    /**
     * This method insertUser to the database a new row with the given parameters
     * @param registeredUser
     * @param confirmPassword
     * @return true if insertUser succeeded, otherwise return false
     */
    public String createUser(RegisteredUser registeredUser, String confirmPassword) {

        // Checking if the registeredUser name already exist in the data base
        if (searchUsers(registeredUser.getUserName(), true) != null){
            return "שם המשתמש שהזנת כבר קיים";
        }

        // Checking that both password text fields are equal
        else if (!registeredUser.getPassword().equals(confirmPassword)){
            return "הסיסמאות אינן תואמות";
        }
        else if(!isValidEmail(registeredUser.getEmail()))
            return "האימייל לא בפורמט הנכון";
        else{
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
            return "התחברת בהצלחה";
        }
    }

    /**
     *
     * @param email
     * @return true if @param email is in the right format
     */
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }

    /**
     * This method searchUser and return the row in the database with the same user name as @param userName if exist
     * if doesn't exist - alert message shows up
     * @param userName
     * @return
     */
    public RegisteredUser searchUsers(String userName, Boolean isInsert) {
        RegisteredUser ans;
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
                ans= registeredUser;
                return ans;
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
/*        if (ans != null){
            return ans;
        }*/
/*        else if (!isInsert){
            //alert("שם משתמש לא קיים במערכת", Alert.AlertType.ERROR);
            return null;
        }*/
        return null;
    }

    /**
     * This method updateUser the database with the given @param data
     * @param registeredUser
     * @param oldUserName
     */
    /**
     * This method update the row in the data base where the registeredUser name is equal to the given registeredUser name in the
     * data string
     * @param registeredUser - all the parameters needed to be updated
     */
    public String updateUser(String oldUserName, RegisteredUser registeredUser, String confirmPassword) {
        // Checking that both password text fields are equal
        if(!registeredUser.getPassword().equals(confirmPassword)){
            return "הסיסמאות אינן תואמות";
        }
        else if(!isValidEmail(registeredUser.getEmail()))
            return "האימייל לא בפורמט הנכון";
        else{
            //registeredUser.updateUserDetails(registeredUser,oldUserName);
            String updatetatement = "UPDATE Users SET user_name = ?,"
                    + "password = ? ,"
                    + "first_name = ? ,"
                    + "last_name = ? ,"
                    + "birthday = ? ,"
                    + "address = ? ,"
                    + "email = ? ,"
                    + "profilePicture= ?"
                    + "WHERE user_name = ?";

            String url = "jdbc:sqlite:" + DBName + ".db";

            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(updatetatement)) {

                // set the corresponding param
                pstmt.setString(1, registeredUser.getUserName()); // registeredUser name
                pstmt.setString(2, registeredUser.getPassword()); // password
                pstmt.setString(3, registeredUser.getFirstName()); // first name
                pstmt.setString(4, registeredUser.getLastName()); // last name
                pstmt.setString(5, registeredUser.getBirthday()); // birthday
                pstmt.setString(6, registeredUser.getAddress()); // address
                pstmt.setString(7, registeredUser.getEmail()); // email
                pstmt.setString(8,"picture"); // picture
                pstmt.setString(9, oldUserName); // registeredUser name - primary key
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "פרטי החשבון עודכנו בהצלחה";
        }

    }
    /**
     * This method deleteUser a row from the database where user name is equal to @param userName
     * @param userName
     */
    /**
     * This method deleteUser a row from the data base where the user name is equal to given userName param
     * @param userName
     */
    public void deleteUser(String userName) {
        String deleteStatement = "DELETE FROM Users WHERE user_name = ?";

        String url = "jdbc:sqlite:" + DBName + ".db";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            // set the corresponding param
            pstmt.setString(1, userName);
            // execute the deleteUser statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public String signIn(String userName, String password){
        RegisteredUser ans = searchUsers(userName,false);
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
        //return unregisteredUser.signIn(userName,password);
    }


}
