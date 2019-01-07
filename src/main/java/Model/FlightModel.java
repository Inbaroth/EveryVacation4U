package Model;

import java.sql.*;
import java.util.ArrayList;

public class FlightModel extends Model{
    public static int flightID ;


    public FlightModel(String DBName) {
        super(DBName);
        flightID = getLastId();
    }

    public void deleteAvailableFlight(int flightId){
        String deleteStatement = "DELETE FROM AvailableFlights WHERE FlightId = ?";
        String url = "jdbc:sqlite:" + this.DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            // set the corresponding param
            pstmt.setInt(1, flightId);
            // execute the deleteUser statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void insertPendingFlight(PendingFlight PFlight ){
            //pendingFlight.insertPendingFlight(flightId, seller,  buyer);
            String insertStatement = "INSERT INTO PendingFlights (FlightId,sellerUserName,buyerUserName) VAlUES (?,?,?)";
            String url = "jdbc:sqlite:" + DBName + ".db";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
                // set the corresponding parameters
                pstmt.setInt(1, PFlight.getFlightId());
                pstmt.setString(2, PFlight.getSeller());
                pstmt.setString(3, PFlight.getBuyer());
                pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Flight> readPendingFlights(String sellerUserName){
        ArrayList<Flight> flights = new ArrayList<Flight>();
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

    public String readPendingFlightBuyer(int flightId){
        String buyer="";
        String sql = "SELECT buyerUserName FROM PendingFlights WHERE FlightId =?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,flightId);
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

    public void deletePendingFlight(int flightId){
        String deleteStatement = "DELETE FROM PendingFlights WHERE FlightId = ?";
      //  deleteFlight(FlightId,deleteStatement);
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            // set the corresponding param
            pstmt.setInt(1, flightId);
            // execute the deleteUser statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertConfirmedFlight(ConfirmedFlight CFlight )  {
            String insertStatement = "INSERT INTO ConfirmedSaleFlights (FlightId,sellerUserName,buyerUserName) VAlUES (?,?,?)";
            String url = "jdbc:sqlite:" + DBName + ".db";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
                // set the corresponding parameters
                pstmt.setInt(1, CFlight.getFlightId());
                pstmt.setString(2, CFlight.getSeller());
                pstmt.setString(3, CFlight.getBuyer());
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

    public void deleteConfirmedFlight(int flightID){
        //confirmedSaleFlight.deleteConfirmedFlight(flightID);
        String deleteStatement = "DELETE FROM ConfirmedSaleFlights WHERE FlightId = ?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            // set the corresponding param
            pstmt.setInt(1, flightID);
            // execute the deleteUser statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertFlight(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice){
        flightID++;
        Flight flight = new Flight(flightID, origin,  destination,  price,  destinationAirport,  dateOfDeparture,  dateOfArrival,  airlineCompany,  numOfTickets,  baggage,  ticketsType,  vacationStyle,  seller, originalPrice);
            String insertStatement = "INSERT INTO AvailableFlights (FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
           // availableFlight.insertFlight(flight, flightID,insertStatement);
            // allFlightsDB.insertFlight(flight, flightID);
        System.out.println("hello");
            createFlight(flight, insertStatement);
            insertStatement = "INSERT INTO AllFlights (FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
           // this.flight.insertFlight(flight, flightID, insertStatement);
            createFlight(flight, insertStatement);
    }

    /**
     *     //implement readUsers method which will display each available vacation which match given data
     * @return
     */
    public ArrayList<Flight> getMatchesFlights(Flight Data){
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
    }


    public void insertPurchasedFlight(PurchasedFlight flight){
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
            System.out.println(e.getMessage());
        }
            //inform controller something is wrong

            //check in GUI that all values aren't null ,don't handle this here

    }

    /**
     * This method insert a new row to 'PendingToSwapFlights' table
     * @param pendingToSwapFlight
     */
    public void insertPendingToSwapFlight(PendingToSwapFlight pendingToSwapFlight){
        String insertStatement = "INSERT INTO PendingToSwapFlights (FlightId) VAlUES (?)";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setInt(1, pendingToSwapFlight.getFlightId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     *
     * @param flightID
     */
    public void deletePendingToSwapFlight(int flightID){
        //confirmedSaleFlight.deleteConfirmedFlight(flightID);
        String deleteStatement = "DELETE FROM PendingToSwapFlights WHERE FlightId = ?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            // set the corresponding param
            pstmt.setInt(1, flightID);
            // execute the deleteUser statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * returns list of all match flight from DB based on given data (as the parameters in signature)
//     * @param origin
//     * @param destination
//     * @param price
//     * @param destinationAirport
//     * @param dateOfDeparture
//     * @param dateOfArrival
//     * @param airlineCompany
//     * @param numOfTickets
//     * @param baggage
//     * @param ticketsType
//     * @param vacationStyle
//     * @param seller
     * @return
     */
//    public ArrayList<Flight> getFlights(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int OriginalPrice){
//        ArrayList<Flight> matchesFlights = new ArrayList<Flight>();
//        Flight flight = new Flight(origin,  destination,  price,  destinationAirport,  dateOfDeparture,  dateOfArrival,  airlineCompany,  numOfTickets,  baggage,  ticketsType,  vacationStyle,  seller, OriginalPrice);
//        matchesFlights = availableFlight.readVacation(flight);
//        return matchesFlights;
//    }

    public int getFlightID() {
        return flightID;
    }

    private void createFlight(Flight Data,String sql)  {
        System.out.println("creating the flight:");
      //  String insertStatement = "INSERT INTO AllFlights (FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String insertStatement = sql;
        System.out.println(sql);
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            System.out.println(Data.getFlightId());
            pstmt.setInt(1, Data.getFlightId());
            pstmt.setString(2, Data.getOrigin());
            pstmt.setString(3, Data.getDestination());
            pstmt.setInt(4, Data.getPrice());
            pstmt.setString(5, Data.getDestinationAirport());
            pstmt.setString(6, Data.getDateOfDeparture());
            pstmt.setString(7, Data.getDateOfArrival());
            pstmt.setString(8, Data.getAirlineCompany());
            pstmt.setInt(9, Data.getNumOfTickets());
            pstmt.setString(10, Data.getBaggage());
            pstmt.setString(11, Data.getTicketsType());
            pstmt.setString(12, Data.getVacationStyle());
            pstmt.setString(13, Data.getSeller());
            pstmt.setInt(14, Data.getOriginalPrice());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("shit happens");
        }
    }

    private int getLastId(){
        String url = "jdbc:sqlite:" + DBName + ".db";
        String query = "SELECT MAX(FlightId) FROM AllFlights";
        try (Connection conn = DriverManager.getConnection(url);
             Statement pstmt = conn.createStatement()) {
            pstmt.execute(query);
            ResultSet rs = pstmt.getResultSet();
            while (rs.next()) {
                int w = rs.getInt(1);
                return w;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Flight> readAllAvailableFlights(){
        ArrayList<Flight> availableFlights = new ArrayList<>();
        String selectQuery = "SELECT * FROM AvailableFlights";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                int flightId = rs.getInt("FlightId");
                String origin = rs.getString("Origin");
                String destination = rs.getString("Destination");
                int price = rs.getInt("Price");
                String destinationAirport = rs.getString("DestinationAirport");
                String dateOfDeparture = rs.getString("DateOfDeparture");
                String dateOfArrival = rs.getString("DateOfArrival");
                String airlineCompany = rs.getString("AirlineCompany");
                int numberOfTickets = rs.getInt("NumberOfTickets");
                String baggage = rs.getString("Baggage");
                String ticketsType = rs.getString("TicketsType");
                String vacationStyle = rs.getString("VacationStyle");
                String sellerUserName = rs.getString("SellerUserName");
                int originPrice = rs.getInt("OriginPrice");
                Flight flight = new Flight(flightId,origin,destination,price,destinationAirport,dateOfDeparture,dateOfArrival,airlineCompany,numberOfTickets,baggage,ticketsType,vacationStyle,sellerUserName,originPrice);
                availableFlights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableFlights;

    }

}
