package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PurchasedFlight {
    private int FlightId;
    private String DateOfPurchase;
    private String TimeOfPurchase;
    private String UserName;
    private String DBName;

    public PurchasedFlight(String databaseName) {
        this.DBName = databaseName;
    }

    public PurchasedFlight(int flightId, String dateOfPurchase, String timeOfPurchase, String userName) {
        FlightId = flightId;
        DateOfPurchase = dateOfPurchase;
        TimeOfPurchase = timeOfPurchase;
        UserName = userName;
    }




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


    public int getFlightId() {
        return FlightId;
    }

    public String getDateOfPurchase() {
        return DateOfPurchase;
    }

    public String getTimeOfPurchase() {
        return TimeOfPurchase;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
