package Model;

import Database.*;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Observable;
import java.util.regex.Pattern;

public class Model extends Observable {

    public static int flightID =0;
    private Flight flight;
    //private PurchasedFlightsDB purchasedFlightsDB;
    private PendingFlight pendingFlight;
    private ConfirmedSaleFlight confirmedSaleFlight;
    private RegisteredUser registeredUser;
    private UnregisteredUser unregisteredUser;
    private AvailableFlight availableFlight;
    private PurchasedFlight purchasedFlight;
    private DB db;





    //public enum errorType {PASSWORD_USERS_NOT_MATCH, PASSWORDS_NOT_MATCH, USER_NOT_EXIST}

    /**
     * Constructor for class Model
     * The constructor create a new database with the name "Vacation4U"
     * and create a new table by the name "Users"
     */
    public Model() {
        this.db = new DB("EveryVacation4U");
        db.connect("EveryVacation4U");
        this.pendingFlight = new PendingFlight("EveryVacation4U");
        this.confirmedSaleFlight = new ConfirmedSaleFlight("EveryVacation4U");
        flight = new Flight("EveryVacation4U");
        registeredUser = new RegisteredUser("EveryVacation4U");
        this.unregisteredUser = new UnregisteredUser("EveryVacation4U");
        this.availableFlight = new AvailableFlight("EveryVacation4U");
        this.purchasedFlight = new PurchasedFlight("EveryVacation4U");
        flightID = flight.getLastId();

    }

    /**
     * This method insert to the database a new row with the given parameters
     * @param registeredUser
     * @param confirmPassword
     * @return true if insert succeeded, otherwise return false
     */
    public String insert(RegisteredUser registeredUser, String confirmPassword) {

        // Checking if the registeredUser name already exist in the data base
        if (readUsers(registeredUser.getUserName(), true) != null){
            return "שם המשתמש שהזנת כבר קיים";
        }

        // Checking that both password text fields are equal
        else if (!registeredUser.getPassword().equals(confirmPassword)){
            return "הסיסמאות אינן תואמות";
        }
        else if(!isValidEmail(registeredUser.getEmail()))
            return "האימייל לא בפורמט הנכון";
        else{
            unregisteredUser.createUser(registeredUser);
            return "התחברת בהצלחה";
        }
    }

    /**
     *
     * @param email
     * @return true if @param email is in the right format
     */
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }

    /**
     * This method search and return the row in the database with the same user name as @param userName if exist
     * if doesn't exist - alert message shows up
     * @param userName
     * @return
     */
    public RegisteredUser readUsers(String userName, Boolean isInsert) {
        RegisteredUser ans = registeredUser.search(userName);
        if (ans != null){
            return ans;
        }
        else if (!isInsert){
            alert("שם משתמש לא קיים במערכת", Alert.AlertType.ERROR);
        }
        return null;
    }

    /**
     * This method updateUser the database with the given @param data
     * @param registeredUser
     * @param oldUserName
     */
    public String updateUser(String oldUserName, RegisteredUser registeredUser, String confirmPassword) {
        // Checking that both password text fields are equal
        if(!registeredUser.getPassword().equals(confirmPassword)){
            return "הסיסמאות אינן תואמות";
        }
        else if(!isValidEmail(registeredUser.getEmail()))
            return "האימייל לא בפורמט הנכון";
        else{
            registeredUser.updateUserDetails(registeredUser,oldUserName);
            return "פרטי החשבון עודכנו בהצלחה";
        }

    }
    /**
     * This method deleteUser a row from the database where user name is equal to @param userName
     * @param userName
     */
    public void deleteUser(String userName) {
        registeredUser.deleteUser( userName);
        alert("החשבון נמחק בהצלחה", Alert.AlertType.INFORMATION);
    }

    public void deleteAvailableFlight(int flightId){
        availableFlight.deleteFromTable(flightId);
    }

    public String signIn(String userName, String password){
        return unregisteredUser.signIn(userName,password);
    }
    public void insertPendingFlight(int flightId, String seller, String buyer ){
        try{
            pendingFlight.insertPendingFlight(flightId, seller,  buyer);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Flight> readPendingFlights(String sellerUserName){
        ArrayList<Flight> pendingFlights = new ArrayList<>();
        pendingFlights = pendingFlight.readPendingFlights(sellerUserName);
        return pendingFlights;
    }

    public String readPendingFlightBuyer(int flightId){
        return pendingFlight.readPendingFlightBuyer(flightId);
    }

    public void deleteFlightFlight(int vacationID){
        pendingFlight.deletePendingFlight(vacationID);
    }

    public void insertConfirmedFlight(int flightId, String seller, String buyer, String origin, String destination, int price, String dateOfDeparture, String dateOfArrival ){
        try{
            confirmedSaleFlight.insertConfirmedFlight(flightId, seller,  buyer);
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }

    public ArrayList<Flight> readConfirmedFlights(String buyerUserName){
        ArrayList<Flight> confirmedFlights = new ArrayList<>();
        confirmedFlights = confirmedSaleFlight.readConfirmedFlights(buyerUserName);
        return confirmedFlights;
    }

    public void deleteConfirmedFlight(int flightID){
        confirmedSaleFlight.deleteConfirmedFlight(flightID);
    }

    public void insertFlight(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice){
        flightID++;
        Flight flight = new Flight(flightID, origin,  destination,  price,  destinationAirport,  dateOfDeparture,  dateOfArrival,  airlineCompany,  numOfTickets,  baggage,  ticketsType,  vacationStyle,  seller, originalPrice);
        try {
            String insertStatement = "INSERT INTO AvailableFlights (FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            availableFlight.insertFlight(flight, flightID,insertStatement);
           // allFlightsDB.insertFlight(flight, flightID);
             insertStatement = "INSERT INTO AllFlights (FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            this.flight.insertFlight(flight, flightID, insertStatement);
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            //inform controller something is wrong

            //check in GUI that all values aren't null ,don't handle this here
        }
    }

    private void insertAvailableFlight(int flightId, String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice){

        Flight flight = new Flight(flightId, origin,  destination,  price,  destinationAirport,  dateOfDeparture,  dateOfArrival,  airlineCompany,  numOfTickets,  baggage,  ticketsType,  vacationStyle,  seller, originalPrice);
        try {
            String insertStatement = "INSERT INTO AvailableFlights (FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            availableFlight.insertFlight(flight, flightID, insertStatement);
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            //inform controller something is wrong

            //check in GUI that all values aren't null ,don't handle this here
        }
    }

    /**
     * returns list of all match flight from DB based on given data (as the parameters in signature)
     * @param origin
     * @param destination
     * @param price
     * @param destinationAirport
     * @param dateOfDeparture
     * @param dateOfArrival
     * @param airlineCompany
     * @param numOfTickets
     * @param baggage
     * @param ticketsType
     * @param vacationStyle
     * @param seller
     * @return
     */
    public ArrayList<Flight> getFlights(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int OriginalPrice){
        ArrayList<Flight> matchesFlights = new ArrayList<Flight>();
        Flight flight = new Flight(origin,  destination,  price,  destinationAirport,  dateOfDeparture,  dateOfArrival,  airlineCompany,  numOfTickets,  baggage,  ticketsType,  vacationStyle,  seller, OriginalPrice);
        matchesFlights = availableFlight.readVacation(flight);
        return matchesFlights;
    }

    public ArrayList<Flight> getMatchesFlights(String origin, String destination, String dateOfDeparture, String dateOfArrival, int numOfTickets){
        ArrayList<Flight> matchesFlights = new ArrayList<Flight>();
        Flight flight = new Flight(origin,  destination,  dateOfDeparture,  dateOfArrival,  numOfTickets);
        matchesFlights = availableFlight.readMatchFlights(flight);
        return matchesFlights;
    }

    public void insertPurchasedFlight(int flightId, String date, String time, String  userName){
        try{
            purchasedFlight.insertFlight(new PurchasedFlight(flightId,date,time,userName));
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            //inform controller something is wrong

            //check in GUI that all values aren't null ,don't handle this here
        }
    }

    public int getFlightID() {
        return flightID;
    }

    private void alert(String messageText, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(messageText);
        alert.showAndWait();
        alert.close();
    }


}
