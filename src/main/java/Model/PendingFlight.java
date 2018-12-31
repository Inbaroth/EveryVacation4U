package Model;

import Model.PurchaseFlightHandler;

import java.sql.*;
import java.util.ArrayList;

public class PendingFlight extends PurchaseFlightHandler {


    public PendingFlight(String databaseName) {
        super(databaseName);
    }



    public void insertPendingFlight(int FlightId, String seller, String buyer) throws SQLException {
        String insertStatement = "INSERT INTO PendingFlights (FlightId,sellerUserName,buyerUserName) VAlUES (?,?,?)";
        insertFlight( FlightId, seller,  buyer, insertStatement);
    }


    public void deletePendingFlight(int FlightId){
        String deleteStatement = "DELETE FROM PendingFlights WHERE FlightId = ?";
        deleteFlight(FlightId,deleteStatement);
    }

    /**
     * list of all vacations the seller need to confirm or reject
     * vacation format(String): VacationId,Origin,Destionation,Price,DateOfDeparture,DateOfArrival
     * @param sellerUserName
     * @return
     */
    public ArrayList<Flight> readPendingFlights(String sellerUserName){
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
        //flights = searchFlight(url, sql);
        return flights;
    }


    public String readPendingFlightBuyer(int FlightId){
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
