package Database;

import Model.PurchasedFlight;

import java.sql.*;

public class PurchasedFlightsDB extends genericDB{


    public PurchasedFlightsDB(String databaseName) { super(databaseName); }

    /**
     * Create a new table in the test database
     *
     */
    public void createNewTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS PurchasedFlights (\n"
                + "	FlightId integer PRIMARY KEY,\n"
                + "	DateOfPurchase text NOT NULL,\n"
                + " TimeOfPurchase text NOT NULL,\n"
                + " UserName text NOT NULL \n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

//    public void insertVacation( int FlightId,String date, String time,String  userName) throws SQLException {
//        String insertStatement = "INSERT INTO PurchasedFlights (FlightId,DateOfPurchase,TimeOfPurchase,UserName) VAlUES (?,?,?,?)";
//        String url = "jdbc:sqlite:" + DBName + ".db";
//        try (Connection conn = DriverManager.getConnection(url);
//             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
//            // set the corresponding parameters
//            pstmt.setInt(1, FlightId);
//            pstmt.setString(2, date);
//            pstmt.setString(3, time);
//            pstmt.setString(4, userName);
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw (new SQLException(e));
//
//        }
//    }

    public void insertFlight(PurchasedFlight flight) throws SQLException {
        String insertStatement = "INSERT INTO PurchasedFlights (FlightId,DateOfPurchase,TimeOfPurchase,UserName) VAlUES (?,?,?,?)";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setInt(1, flight.getFlightId());
            pstmt.setString(2, flight.getDateOfPurchase());
            pstmt.setString(3, flight.getTimeOfPurchase());
            pstmt.setString(4, flight.getUserName());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw (new SQLException(e));

        }
    }
}
