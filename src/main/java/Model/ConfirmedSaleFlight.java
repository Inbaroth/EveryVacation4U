package Model;

import Model.PurchaseFlightHandler;

import java.sql.*;
import java.util.ArrayList;

public class ConfirmedSaleFlight extends PurchaseFlightHandler {


    public ConfirmedSaleFlight(String databaseName) { super(databaseName); }


    public void insertConfirmedFlight(int FlightId, String seller, String buyer) throws SQLException {
        String insertStatement = "INSERT INTO ConfirmedSaleFlights (FlightId,sellerUserName,buyerUserName) VAlUES (?,?,?)";
        insertFlight( FlightId, seller,  buyer, insertStatement);
    }



    public void deleteConfirmedFlight(int FlightId){
        String deleteStatement = "DELETE FROM ConfirmedSaleFlights WHERE FlightId = ?";
        deleteFlight(FlightId,deleteStatement);
    }


    /**
     * list of all vacations the buyer got confirm on and need to pay for
     * vacation format(String): VacationId,Origin,Destionation,Price,DateOfDeparture,DateOfArrival
     * @param buyerUserName
     * @return
     */
    public ArrayList<Flight> readConfirmedFlights(String buyerUserName){
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
