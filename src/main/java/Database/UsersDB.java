package Database;

import Model.RegisteredUser;

import java.sql.*;

public class UsersDB extends genericDB{

    /**
     * Constructor for the class UsersDB
     * @param databaseName
     */
    public UsersDB(String databaseName) {
        super(databaseName);
    }


    /**
     * This method create a new table in the data base by the name tableName
     */
    public void createTable(){
        String createStatement = "CREATE TABLE IF NOT EXISTS Users (\n"
                + "	user_name text PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + " first_name text NOT NULL,\n"
                + " last_name text NOT NULL,\n"
                + "	birthday text,\n"
                + "	address text,\n"
                + "	email text,\n"
                + " profilePicture text NOT NULL\n"
                + ");";

        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * This method insert a new row to Users table with the given data
     * @param registeredUser  data of the new row which need to be added
     */
    public void insertIntoTable(RegisteredUser registeredUser){

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

    /**
     * This method search and return the row in the database which is equal to the given userName
     * @param userName - user name to search by
     * @return the founded row
     */
    public RegisteredUser read (String userName){

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

    /**
     * This method update the row in the data base where the registeredUser name is equal to the given registeredUser name in the
     * data string
     * @param registeredUser - all the parameters needed to be updated
     */
    public void updateDatabase(RegisteredUser registeredUser, String userName) {
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
            pstmt.setString(12, userName); // registeredUser name - primary key
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deleteUser a row from the data base where the user name is equal to given userName param
     * @param userName
     */
    public void deleteFromTable (String userName){
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

