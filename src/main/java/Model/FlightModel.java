package Model;

import java.sql.*;
import java.util.*;

public class FlightModel extends Model{
    public static int flightID;


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
        String query = "SELECT AllFlights.FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival," +
                "AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,AllFlights.sellerUserName," +
                "OriginalPrice FROM AllFlights  inner join PendingFlights on AllFlights.FlightId=PendingFlights.FlightId where PendingFlights.sellerUserName=?"/*where SellerUserName IN (SELECT sellerUserName FROM PendingFlights Where sellerUserName=?)*/;
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

    public String readConfirmedFlightBuyer(int flightId){
        String buyer="";
        String sql = "SELECT buyerUserName FROM ConfirmedSaleFlights WHERE FlightId =?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,flightId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                // loop through the result set
                buyer = rs.getString("buyerUserName");
                return buyer;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return buyer;
    }


    public String readPurchasedFlightBuyer(int flightId){
        String buyer="";
        String sql = "SELECT UserName FROM PurchasedFlights WHERE FlightId =?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,flightId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                // loop through the result set
                buyer = rs.getString("UserName");
                return buyer;
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
        String sql = "SELECT AllFlights.FlightId,AllFlights.Origin,AllFlights.Destination,AllFlights.Price,AllFlights.DateOfDeparture,AllFlights.DateOfArrival " +
                "FROM AllFlights INNER JOIN ConfirmedSaleFlights ON ConfirmedSaleFlights.FlightId = AllFlights.FlightId  WHERE buyerUserName=? ";
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

    /**
     * list of all vacations the buyer got confirm on and need to pay for
     * vacation format(String): VacationId,Origin,Destionation,Price,DateOfDeparture,DateOfArrival
     * @param sellerUserName
     * @return
     */
    public ArrayList<Flight> readConfirmedFlightsSeller(String sellerUserName){
        ArrayList<Flight> vacationsToPay = new ArrayList<Flight>();
        //String sql = "SELECT VacationId,Origin,Destination,Price,DateOfDeparture,DateOfArrival FROM ConfirmedSaleVacations WHERE buyerUserName=?";
        String sql = "SELECT AllFlights.FlightId,AllFlights.Origin,AllFlights.Destination,AllFlights.Price,AllFlights.DateOfDeparture,AllFlights.DateOfArrival " +
                "FROM AllFlights INNER JOIN ConfirmedSaleFlights ON ConfirmedSaleFlights.FlightId = AllFlights.FlightId  WHERE ConfirmedSaleFlights.sellerUserName=? ";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection connect = DriverManager.getConnection(url);
             PreparedStatement stmt = connect.prepareStatement(sql)){
            stmt.setString(1,sellerUserName);
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

    public void insertFlight(Flight Data){
        if (Data.getFlightId() == 0) {
            flightID++;
            Data.setFlightId(flightID);
        }
        String insertStatement = "INSERT INTO AvailableFlights (FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        createFlight(Data, insertStatement);
        insertStatement = "INSERT INTO AllFlights (FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        createFlight(Data, insertStatement);
    }

    public Flight getFlight(int flightID){
        String readStatement = "SELECT * FROM AllFlights WHERE FlightId = ?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(readStatement)) {
            // set the corresponding param
            pstmt.setInt(1, flightID);
            // execute the deleteUser statement
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
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
                int originPrice = rs.getInt("OriginalPrice");
                Flight flight = new Flight(flightId, origin, destination, price, destinationAirport, dateOfDeparture, dateOfArrival, airlineCompany, numberOfTickets, baggage, ticketsType, vacationStyle, sellerUserName, originPrice);
                return flight;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
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
     * This Method delete a row from 'PendingToSwapFlight' where flightId is equal to flight id in the database
     * @param flightID
     */
    public void deletePendingToSwapFlight(int flightID){
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
     * This method read all the pending flights from the PendingToSwapFlights DB
     * @return array list of flights of all the flights exist in the pendingToSwap
     */
    public ArrayList<Flight> readPendingToSwapFlights(){
        ArrayList<Flight> pendingToSwapFlights = new ArrayList<>();
        String readStatement = "SELECT * FROM AllFlights inner join PendingToSwapFlights on AllFlights.FlightId = PendingToSwapFlights.FlightId";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(readStatement)) {
            // execute the read statement
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
                int originPrice = rs.getInt("OriginalPrice");
                Flight flight = new Flight(flightId,origin,destination,price,destinationAirport,dateOfDeparture,dateOfArrival,airlineCompany,numberOfTickets,baggage,ticketsType,vacationStyle,sellerUserName,originPrice);
                pendingToSwapFlights.add(flight);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pendingToSwapFlights;
    }

    /**
     * This method insert a new row to 'OfferedToSwapFlights' table
     * @param offeredToSwapFlight
     */
    public void insertOfferToSwapFlight(OfferedToSwapFlight offeredToSwapFlight){
        String insertStatement = "INSERT INTO OfferedToSwapFlights (FlightIdPending, FlightIdChosen) VAlUES (?,?)";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setInt(1, offeredToSwapFlight.getFlightIdPending());
            pstmt.setInt(2, offeredToSwapFlight.getFlightIdOffered());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This Method delete a row from 'OfferedToSwapFlights' where flightIDPending is equal to FlightIdPending in the database
     * and flightIDChosen is equal to FlightIdChosen in the data base
     * @param flightIDPending
     * @param flightIDChosen
     */
    public void deleteOfferToSwapFlight(int flightIDPending, int flightIDChosen){
        String deleteStatement = "DELETE FROM OfferedToSwapFlights WHERE FlightIdPending = ? and FlightIdChosen = ?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            // set the corresponding param
            pstmt.setInt(1, flightIDPending);
            pstmt.setInt(2, flightIDChosen);
            // execute the deleteUser statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public TreeMap<Integer, Vector<Flight>> readOfferToSwapFlights(String userName){
        TreeMap<Integer,Vector<Flight>> offeres = new TreeMap<>();
        Vector<Flight> flights = new Vector<>();
        String query = "SELECT FlightId from PurchasedFlights where UserName = ?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            // set the corresponding param
            pstmt.setString(1, userName);
            // execute the read statement
            ResultSet rs  = pstmt.executeQuery();
            // get all userName purchase
            while (rs.next()){
                int flightId = rs.getInt("FlightId");
                String getOffersQuery = "SELECT FlightIdChosen from OfferedToSwapFlights where FlightIdPending = ?";
                try (Connection connection = DriverManager.getConnection(url);
                     PreparedStatement preparedStatement = connection.prepareStatement(getOffersQuery)) {
                    preparedStatement.setInt(1,flightId);
                    ResultSet resultSet  = preparedStatement.executeQuery();
                    flights = new Vector<>();
                    // get all offers for flightId
                    while (resultSet.next()){
                        int chosenFlightId = resultSet.getInt("FlightIdChosen");
                        String getAllFlights = "SELECT * from AllFlights where FlightId = ?";
                        try (Connection newConnection = DriverManager.getConnection(url);
                             PreparedStatement pStatement = newConnection.prepareStatement(getAllFlights)) {
                            pStatement.setInt(1,chosenFlightId);
                            ResultSet rSet  = pStatement.executeQuery();
                            // get all details for all flights in offer
                            while (rSet.next()){
                                int FlightId = rSet.getInt("FlightId");
                                String origin = rSet.getString("Origin");
                                String destination = rSet.getString("Destination");
                                int price = rSet.getInt("Price");
                                String destinationAirport = rSet.getString("DestinationAirport");
                                String dateOfDeparture = rSet.getString("DateOfDeparture");
                                String dateOfArrival = rSet.getString("DateOfArrival");
                                String airlineCompany = rSet.getString("AirlineCompany");
                                int numberOfTickets = rSet.getInt("NumberOfTickets");
                                String baggage = rSet.getString("Baggage");
                                String ticketsType = rSet.getString("TicketsType");
                                String vacationStyle = rSet.getString("VacationStyle");
                                String sellerUserName = rSet.getString("SellerUserName");
                                int originPrice = rSet.getInt("OriginalPrice");
                                Flight flight = new Flight(FlightId,origin,destination,price,destinationAirport,dateOfDeparture,dateOfArrival,airlineCompany,numberOfTickets,baggage,ticketsType,vacationStyle,sellerUserName,originPrice);
                                flights.add(flight);
                            }
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                offeres.put(flightId,flights);
            }
        }  catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return offeres;
    }

    /**
     * This method insert a new row to 'SwappedFlights' table
     * @param swappedFlight
     */
    public void insertSwappedFlights(SwappedFlight swappedFlight){
        String insertStatement = "INSERT INTO SwappedFlights (FlightId1, FlightId2) VAlUES (?,?)";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setInt(1, swappedFlight.getFlightId1());
            pstmt.setInt(2, swappedFlight.getFlightId2());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method return all the flights that was purchased by userName and doesn't appear in pendingToSwapFlight DB
     * @param userName
     * @return
     */
    public ArrayList<Flight> getFlightsToSwapHomePage(String userName){
        ArrayList<Flight> pendingToSwapFlights = readPendingToSwapFlights();
        ArrayList<Flight> purchasedFlights = readPurchasedFlights(userName);
        HashSet<Integer> swaappedFlights = readSwappedFlights();
        HashSet<Integer> offeredFlights = getOffersToSwapFlights();
        for (Flight flight :pendingToSwapFlights) {
            if (purchasedFlights.contains(flight)) {
                int index = purchasedFlights.indexOf(flight);
                purchasedFlights.remove(index);
            }
        }
        for (int i = 0; i < purchasedFlights.size();i++){
            int id = purchasedFlights.get(i).getFlightId();
            if (swaappedFlights.contains(id) || offeredFlights.contains(id)){
                purchasedFlights.remove(i);
            }
        }
        return purchasedFlights;
    }

    private HashSet<Integer> readSwappedFlights() {
        HashSet<Integer> flightId = new HashSet<>();
        String insertStatement = "SELECT * FROM SwappedFlights";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                int flightId1 = rs.getInt("FlightId1");
                int flightId2 = rs.getInt("FlightId2");
                flightId.add(flightId1);
                flightId.add(flightId2);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flightId;
    }

    /**
     * This method return all the flights that was purchased by userName and doesn't appear in pendingToSwapFlight DB
     * @param userName
     * @return
     */
    public ArrayList<Flight> getFlightsToSwap(String userName){
        HashSet<Integer> offeredFlights = getOffersToSwapFlights();
        HashSet<Integer> swaappedFlights = readSwappedFlights();
        ArrayList<Flight> purchasedFlights = readPurchasedFlights(userName);
        for (int i = 0; i < purchasedFlights.size();i++){
            int id = purchasedFlights.get(i).getFlightId();
            if (offeredFlights.contains(id) || swaappedFlights.contains(id)){
                purchasedFlights.remove(i);
            }
        }
        return purchasedFlights;
    }

    public ArrayList<Flight> readPurchasedFlights(String userName){
        ArrayList<Flight> purchasedFlights = new ArrayList<>();
        String insertStatement = "SELECT *  from AllFlights INNER JOIN PurchasedFlights ON AllFlights.FlightId=PurchasedFlights.FlightId WHERE PurchasedFlights.UserName=?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            pstmt.setString(1,userName);
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
                int originPrice = rs.getInt("OriginalPrice");
                Flight flight = new Flight(flightId,origin,destination,price,destinationAirport,dateOfDeparture,dateOfArrival,airlineCompany,numberOfTickets,baggage,ticketsType,vacationStyle,sellerUserName,originPrice);
                purchasedFlights.add(flight);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return purchasedFlights;

    }

    public HashSet<Integer> getOffersToSwapFlights (){
        HashSet<Integer> offeredToSwapFile = new HashSet<>();
        String insertStatement = "SELECT * from OfferedToSwapFlights";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                int chosenFlight = rs.getInt("FlightIdChosen");
                offeredToSwapFile.add(chosenFlight);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return offeredToSwapFile;
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
        //  String insertStatement = "INSERT INTO AllFlights (FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String insertStatement = sql;
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
            System.out.println(e.getMessage());
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
        return 1;
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
                int originPrice = rs.getInt("OriginalPrice");
                Flight flight = new Flight(flightId,origin,destination,price,destinationAirport,dateOfDeparture,dateOfArrival,airlineCompany,numberOfTickets,baggage,ticketsType,vacationStyle,sellerUserName,originPrice);
                availableFlights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableFlights;

    }

}
