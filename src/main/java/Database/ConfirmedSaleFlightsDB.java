package Database;

import Model.Flight;

import java.sql.*;
import java.util.ArrayList;

public class ConfirmedSaleFlightsDB extends genericDB {


    public ConfirmedSaleFlightsDB(String databaseName) { super(databaseName); }

    /**
     * Create a new table in the test database
     *
     */
    public void createNewTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS ConfirmedSaleFlights (\n"
                + "	FlightId integer PRIMARY KEY,\n"
                + "	sellerUserName text NOT NULL,\n"
                + "	buyerUserName text NOT NULL \n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void insertVacation( int FlightId,String seller, String buyer, String origin, String destination, int price, String dateOfDeparture, String dateOfArrival) throws SQLException {
        String insertStatement = "INSERT INTO ConfirmedSaleFlights (FlightId,sellerUserName,buyerUserName,Origin,Destination,Price,DateOfDeparture,DateOfArrival) VAlUES (?,?,?,?,?,?,?,?)";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setInt(1, FlightId);
            pstmt.setString(2, seller);
            pstmt.setString(3, buyer);
            pstmt.setString(4, origin);
            pstmt.setString(5, destination);
            pstmt.setInt(6, price);
            pstmt.setString(7, dateOfDeparture);
            pstmt.setString(8, dateOfArrival);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw (new SQLException(e));
        }
    }



    public void deleteVacation(int FlightId){
        String deleteStatement = "DELETE FROM ConfirmedSaleFlights WHERE FlightId = ?";
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
     * list of all vacations the buyer got confirm on and need to pay for
     * vacation format(String): VacationId,Origin,Destionation,Price,DateOfDeparture,DateOfArrival
     * @param buyerUserName
     * @return
     */
    public ArrayList<Flight> readConfirmedVacations(String buyerUserName){
        ArrayList<Flight> vacationsToPay = new ArrayList<Flight>();
        //String sql = "SELECT VacationId,Origin,Destination,Price,DateOfDeparture,DateOfArrival FROM ConfirmedSaleVacations WHERE buyerUserName=?";
        String sql = "SELECT AllFlights.FlightId,AllFlights.Origin,AllFlights.Destination,AllFlights.Price,AllFlights.DateOfDeparture,AllFlights.DateOfArrival FROM AllFlights INNER JOIN ConfirmedSaleFlights ON ConfirmedSaleFlights.FlightId = AllFlights.FlightId  WHERE buyerUserName=? ";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection connect = DriverManager.getConnection(url);
             PreparedStatement stmt = connect.prepareStatement(sql)){
            stmt.setString(1,buyerUserName);
             ResultSet rs = stmt.executeQuery();
            // loop through the result set
            while (rs.next()) {
                //Flight flight = new Flight(rs.getInt("VacationId"), rs.getString("Origin"),)
                Flight flight = new Flight(rs.getString("Origin"),rs.getString("Destination"),rs.getString("DateOfDeparture"),rs.getString("DateOfArrival"),rs.getInt("Price"));
                //String details = "שדה תעופה ביעד:"+flight.getDestinationAirport() + " מס' כרטיסים: " + flight.getNumOfTickets() + "\n" +  " כבודה:"+ flight.getBaggage() + " סוג כרטיס: " + flight.getTicketsType() + "\n" + " מחיר: "+ flight.getPrice()
                flight.setFlightId(rs.getInt("FlightId"));
                vacationsToPay.add(flight);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vacationsToPay;
    }






}
