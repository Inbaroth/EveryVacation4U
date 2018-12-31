package Model;

import java.awt.*;
import java.sql.*;

public class RegisteredUser extends User{

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String birthday;
    private String address;
    private String email;
    private Image profilePicture;


    public RegisteredUser(String DBName) {
        this.DBName = DBName;
    }

    public RegisteredUser(String userName, String password, String firstName, String lastName, String birthday, String address, String email, Image profilePicture) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.profilePicture = profilePicture;
    }


    //<editor-fold desc="Getters">
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    //</editor-fold>


    //<editor-fold desc="Setters">
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    //</editor-fold>






    /**
     * This method update the row in the data base where the registeredUser name is equal to the given registeredUser name in the
     * data string
     * @param registeredUser - all the parameters needed to be updated
     */
    public void updateUserDetails(RegisteredUser registeredUser, String userName) {
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
            pstmt.setString(9, userName); // registeredUser name - primary key
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deleteUser a row from the data base where the user name is equal to given userName param
     * @param userName
     */
    public void deleteUser(String userName){
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
}
