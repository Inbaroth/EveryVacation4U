package Model;

import java.sql.*;
import java.util.ArrayList;

public class Flight {
    //here or at model  or both(?)
   // private static int flightId = 0;
    private int flightId;
    private String origin;
    private String destination;
    private int price;
    private String destinationAirport;
    private String dateOfDeparture;
    private String dateOfArrival;
    private String airlineCompany;
    private String seller;
    private int numOfTickets;
    //adult,child,baby
    private String ticketsType;
    //Urban/ Exotic/ Natures/ Multi
    private String vacationStyle;
    //None/Only hand bag/Up to 8 kg/Up to 23 kg/ Up to 31 kg/ More than 31 kg
    private String baggage;
    private int originalPrice;
    protected String DBName;


    public Flight(String databaseName){
        DBName= databaseName;
    }

    public Flight(int flightId, String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice) {

        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.destinationAirport = destinationAirport;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.airlineCompany = airlineCompany;
        this.numOfTickets = numOfTickets;
        this.baggage = baggage;
        this.ticketsType = ticketsType;
        this.vacationStyle = vacationStyle;
        this.seller=seller;
        this.originalPrice =originalPrice;




    }


    public Flight(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice) {
        this.flightId =0;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.destinationAirport = destinationAirport;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.airlineCompany = airlineCompany;
        this.numOfTickets = numOfTickets;
        this.baggage = baggage;
        this.ticketsType = ticketsType;
        this.vacationStyle = vacationStyle;
        this.seller=seller;
        this.originalPrice =originalPrice;


    }


    public Flight(String origin, String destination, String dateOfDeparture, String dateOfArrival, int numOfTickets) {
        this.origin = origin;
        this.destination = destination;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.numOfTickets = numOfTickets;
    }



    public void insertFlight(Flight Data, int FlightId, String sql) throws SQLException {
        String insertStatement = "INSERT INTO AllFlights (FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
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
            throw (new SQLException(e));
        }
    }

    //ATTENTION:
    //this method search by ALL columns data except for VacationId,
    //maybe there will be another usage for this
    //maybe return by using it all vacations by removing the "WHERE" parts
//    //implement readUsers method which will display eace available vacation
    public ArrayList<Flight> readVacation(Flight Data) {
        ArrayList<Flight> flights = new ArrayList<>();
        String origin = Data.getOrigin();
        String destination = Data.getDestination();
        int price = Data.getPrice();
        String destinationAirport = Data.getDestinationAirport();
        String dateOfDeparture = Data.getDateOfDeparture();
        String dateOfArrival = Data.getDateOfArrival();
        String airlineCompany = Data.getAirlineCompany();
        int numOfTickets = Data.getNumOfTickets();
        //None/Only hand bag/Up to 8 kg/Up to 23 kg/ Up to 31 kg/ More than 31 kg
        String baggage = Data.getBaggage();
        //adult,child,baby
        String ticketsType = Data.getTicketsType();
        //Urban/ Exotic/ Natures/ Multi
        String vacationStyle = Data.getVacationStyle();
        String seller = Data.getSeller();
        int originalPrice = Data.getOriginalPrice();
        String sql = "SELECT Origin,Destionation,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName FROM AvailableFlight WHERE " +
                "Origin='" + origin + "' AND Destionation= '" + destination + "' AND Price='" + price + "' AND DestinationAirport='" + destinationAirport + "'AND DateOfDeparture='" + dateOfDeparture + "'AND DateOfarrival='" + dateOfArrival + "'AND AirlineCompany='" + airlineCompany + "'AND NumberOfTickets='" + numOfTickets + "'AND Baggage='" + baggage + "' AND TicketsType='" + ticketsType + "'AND VacationStyle='" + vacationStyle + "'AND SellerUserName='" + seller + "' AND OriginalPrice= '" +originalPrice + "'";
        String url = "jdbc:sqlite:" + DBName + ".db";
        flights = searchFlight(url, sql);
        return flights;
    }



    public ArrayList<Flight> searchFlight(String url, String query){

        query = "SELECT Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,sellerUserName,OriginalPrice FROM AvailableFlights where Origin=? and Destination=? and DateOfArrival=? and DateOfDeparture=? and NumberOfTickets>=?";
        ArrayList<Flight> flights = new ArrayList<Flight>();
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query);
             //stmt.setString(1,);
             ResultSet rs = stmt.executeQuery(query)) {

            // loop through the result set
            while (rs.next()) {
                //creating flights objects only so we could display them, there are not going to be a real availableVacation obects
                Flight flight = new Flight(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14));
                flights.add(flight);
                flights = null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flights;
    }

    public int getLastId(){
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



    public int getFlightId() {
        return flightId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getPrice() {
        return price;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public String getDateOfArrival() {
        return dateOfArrival;
    }

    public String getAirlineCompany() {
        return airlineCompany;
    }

    public int getNumOfTickets() {
        return numOfTickets;
    }

    public String getBaggage() {
        return baggage;
    }

    public String getTicketsType() {
        return ticketsType;
    }

    public String getVacationStyle() {
        return vacationStyle;
    }

    public String getSeller() {
        return seller;
    }

    public int getOriginalPrice() { return originalPrice; }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

}
