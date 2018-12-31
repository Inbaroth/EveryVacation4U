package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PurchaseFlightHandler {

    protected String DBName;
    private int FlightId;
    private String seller;
    private String buyer;

    public PurchaseFlightHandler(String DBName) {
        this.DBName = DBName;
    }

    public void insertFlight(int FlightId, String seller, String buyer, String query) throws SQLException {
        String insertStatement = query;
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setInt(1, FlightId);
            pstmt.setString(2, seller);
            pstmt.setString(3, buyer);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw (new SQLException(e));
        }
    }

    public void deleteFlight(int FlightId, String query){
        String deleteStatement = query;
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            // set the corresponding param
            pstmt.setInt(1, FlightId);
            // execute the deleteUser statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
