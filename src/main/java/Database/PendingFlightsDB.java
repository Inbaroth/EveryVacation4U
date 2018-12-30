package Database;

import Model.Flight;

import java.sql.*;
import java.util.ArrayList;

public class PendingFlightsDB extends genericDB {


    public PendingFlightsDB(String databaseName) {
        super(databaseName);
    }

    /**
     * Create a new table in the test database
     *
     */
    public void createNewTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS PendingFlights (\n"
                + "FlightId integer PRIMARY KEY,\n"
                + "	sellerUserName text NOT NULL,\n"
                + " buyerUserName text NOT NULL\n"
                + ");";


        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }


    public void insertVacation(int FlightId,String seller, String buyer) throws SQLException {
        String insertStatement = "INSERT INTO PendingFlights (FlightId,sellerUserName,buyerUserName) VAlUES (?,?,?)";
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


    public void deleteVacation(int FlightId){
        String deleteStatement = "DELETE FROM PendingFlights WHERE FlightId = ?";
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

    /**
     * list of all vacations the seller need to confirm or reject
     * vacation format(String): VacationId,Origin,Destionation,Price,DateOfDeparture,DateOfArrival
     * @param sellerUserName
     * @return
     */
    public ArrayList<Flight> readPendingVacation(String sellerUserName){
        ArrayList<Flight> flights = new ArrayList<Flight>();
        String sql = "SELECT FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany, NumberOfTickets,Baggage, TicketsType,VacationStyle," +
                "SellerUserName,OriginalPrice, FROM AvailableVacations WHERE SellerUserName " +
                "IN (SELECT SellerUserName FROM PendingFlights  WHERE " +
                "SellerUserName='" +sellerUserName+"'AND PendingFlights.FlightId =AvailableVacations.FlightId )";
        String query = "SELECT FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival," +
                "AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,sellerUserName," +
                "OriginalPrice FROM AllFlights where SellerUserName IN (SELECT sellerUserName FROM PendingFlights Where sellerUserName=?)";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query)){
                stmt.setString(1,sellerUserName);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    //creating flights objects only so we could display them, there are not going to be a real availableVacation obects
                    Flight flight= new Flight(rs.getInt("FlightId"),
                            rs.getString("Origin"),
                            rs.getString("Destination"),
                            rs.getInt("Price"),
                            rs.getString("DestinationAirport"),
                            rs.getString("DateOfDeparture"),
                            rs.getString("DateOfArrival"),
                            rs.getString("AirlineCompany"),
                            rs.getInt("NumberOfTickets"),
                            rs.getString("Baggage"),
                            rs.getString("TicketsType"),
                            rs.getString("VacationStyle"),
                            rs.getString("sellerUserName"),
                            rs.getInt("NumberOfTickets"));
                    flights.add(flight);
                    flight = null;
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //flights = getVacationsBasedOnQuery(url, sql);
        return flights;
    }


    public String readPendingVacationBuyer(int FlightId){
        String buyer="";
        String sql = "SELECT buyerUserName FROM PendingFlights WHERE FlightId =?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,FlightId);
            ResultSet rs = stmt.executeQuery();
             while (rs.next()){
                     // loop through the result set
                     buyer = rs.getString("buyerUserName");
                 }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return buyer;
    }



}
