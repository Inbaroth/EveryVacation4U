package Model;

import java.sql.*;
import java.util.ArrayList;

public class AvailableFlight extends Flight {

    public AvailableFlight(String databaseName) {
        super(databaseName);
    }

    public AvailableFlight(int vacationId, String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice) {
        super(vacationId, origin, destination, price, destinationAirport, dateOfDeparture, dateOfArrival, airlineCompany, numOfTickets, baggage, ticketsType, vacationStyle, seller, originalPrice);
    }

    public AvailableFlight(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice) {
        super(origin, destination, price, destinationAirport, dateOfDeparture, dateOfArrival, airlineCompany, numOfTickets, baggage, ticketsType, vacationStyle, seller, originalPrice);
    }

    public AvailableFlight(String origin, String destination, String dateOfDeparture, String dateOfArrival, int numOfTickets) {
        super(origin, destination, dateOfDeparture, dateOfArrival, numOfTickets);
    }

    //implement readUsers method which will display each available vacation which match given data
    public ArrayList<Flight> readMatchFlights(Flight Data) {
        ArrayList<Flight> flights = new ArrayList<>();
        String origin = Data.getOrigin();
        String destination = Data.getDestination();
        String dateOfDeparture = Data.getDateOfDeparture();
        String dateOfArrival = Data.getDateOfArrival();
        int numOfTickets = Data.getNumOfTickets();
        String url = "jdbc:sqlite:" + DBName + ".db";
        String query = "SELECT FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival," +
                "AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,sellerUserName," +
                "OriginalPrice FROM AvailableFlights where Origin=? and Destination=? and DateOfDeparture=? " +
                "and DateOfArrival=? and NumberOfTickets>=?";
        //ArrayList<Flight> flights = new ArrayList<Flight>();
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,origin);
            stmt.setString(2,destination);
            stmt.setString(3,dateOfDeparture);
            stmt.setString(4,dateOfArrival);
            stmt.setInt(5,numOfTickets);
//            stmt.setString(6,Controller.currentUserName);
            ResultSet rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                //creating flights objects only so we could display them, there are not going to be a real availableVacation obects
                Flight flight = new Flight(rs.getInt("FlightId"),
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
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        //flights = searchFlight(url, sql);
        return flights;
        //flights = searchFlight(url, sql);
        //return flights;
    }


//    private ArrayList<Flight> searchFlight(String url, String query){
//       ArrayList<Flight> vacations = new ArrayList<Flight>();
//        try (Connection conn = DriverManager.getConnection(url);
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//
//            // loop through the result set
//            while (rs.next()) {
//                //creating vacation objects only so we could display them, there are not going to be a real availableVacation obects
//                Flight vacation = new Flight(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14));
//                vacations.add(vacation);
//                vacation = null;
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return vacations;
//    }


    public void deleteFromTable ( int FlightId){
        String deleteStatement = "DELETE FROM AvailableFlights WHERE FlightId = ?";
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
