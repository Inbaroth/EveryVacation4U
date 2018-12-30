package Model;

import Database.*;
import Database.AvailableFlightsDB;
//import Database.PurchcasedVacationDB;
import Database.UsersDB;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

import java.util.Observable;
import java.util.regex.Pattern;

public class Model extends Observable {

    private UsersDB usersDB;
    public static int flightID =0;
    private Flight flight;
    private AvailableFlightsDB availableFlightsDB;
    private PurchasedFlightsDB purchasedFlightsDB;
    private PendingFlightsDB pendingFlightsDB;
    private ConfirmedSaleFlightsDB confirmedSaleFlightsDB;
    private AllFlightsDB allFlightsDB;


    //public enum errorType {PASSWORD_USERS_NOT_MATCH, PASSWORDS_NOT_MATCH, USER_NOT_EXIST}

    /**
     * Constructor for class Model
     * The constructor create a new database with the name "Vacation4U"
     * and create a new table by the name "Users"
     */
    public Model() {
        this.usersDB = new UsersDB("Vacation4U");
        usersDB.connect("Vacation4U");
        usersDB.createTable();

        this.availableFlightsDB = new AvailableFlightsDB("Vacation4U");
        availableFlightsDB.connect("Vacation4U");
        availableFlightsDB.createNewTable();

        this.purchasedFlightsDB = new PurchasedFlightsDB("vacation4U");
        purchasedFlightsDB.connect("Vacation4U");
        purchasedFlightsDB.createNewTable();

        this.pendingFlightsDB = new PendingFlightsDB("Vacation4U");
        pendingFlightsDB.connect("Vacation4U");
        pendingFlightsDB.createNewTable();

        this.confirmedSaleFlightsDB = new ConfirmedSaleFlightsDB("Vacation4U");
        confirmedSaleFlightsDB.connect("Vacation4U");
        confirmedSaleFlightsDB.createNewTable();

        this.allFlightsDB = new AllFlightsDB("Vacation4U");
        allFlightsDB.connect("Vacation4U");
        allFlightsDB.createNewTable();

        flightID = allFlightsDB.getMaxNumber();
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
            usersDB.insertIntoTable(registeredUser);
            return "התחברת בהצלחה";
        }
    }

    /**
     *
     * @param CSV
     * @return true is CSV a valid CSV number. In a length of 3 and contain only digits
     */
    private boolean isValidCSVNumber(String CSV) {
        return CSV.length() == 3 && StringUtils.isNumeric(CSV);
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
     *
     * @param creditCardNumber
     * @return true is creditCardNumber a valid CSV number. In a length of 16 and contain only digits
     */
    private boolean isValidCreditCardNumber(String creditCardNumber){
        return creditCardNumber.length() == 16 && StringUtils.isNumeric(creditCardNumber);
    }

    /**
     * This method search and return the row in the database with the same user name as @param userName if exist
     * if doesn't exist - alert message shows up
     * @param userName
     * @return
     */
    public RegisteredUser readUsers(String userName, Boolean isInsert) {
        if (usersDB.read(userName) != null){
            return usersDB.read(userName);
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
            usersDB.updateDatabase(registeredUser,oldUserName);
            return "פרטי החשבון עודכנו בהצלחה";
        }

    }
    /**
     * This method deleteUser a row from the database where user name is equal to @param userName
     * @param userName
     */
    public void deleteUser(String userName) {
        usersDB.deleteFromTable( userName);
        alert("החשבון נמחק בהצלחה", Alert.AlertType.INFORMATION);
    }

    public void deleteAvailableFlight(int flightId){
        availableFlightsDB.deleteFromTable(flightId);
    }

    public String signIn(String userName, String password) {
        RegisteredUser registeredUser = readUsers(userName,false);
        boolean isLegal = true;
        if (registeredUser != null){
            if (!password.equals(registeredUser.getPassword())) {
                return "הסיסמאות אינן תואמות";
            }
            else{
                return userName;
            }
            //return null;
        }
        return null;
    }
    public void insertPendingFlight(int flightId, String seller, String buyer ){
        try{
            pendingFlightsDB.insertVacation(flightId, seller,  buyer);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Flight> readPendingFlights(String sellerUserName){
        ArrayList<Flight> pendingFlights = new ArrayList<>();
        pendingFlights = pendingFlightsDB.readPendingVacation(sellerUserName);
        return pendingFlights;
    }

    public String readPendingFlightBuyer(int flightId){
        return pendingFlightsDB.readPendingVacationBuyer(flightId);
    }

    public void deleteFlightFlight(int vacationID){
        pendingFlightsDB.deleteVacation(vacationID);
    }

    public void insertConfirmedFlight(int flightId, String seller, String buyer, String origin, String destination, int price, String dateOfDeparture, String dateOfArrival ){
        try{
            confirmedSaleFlightsDB.insertVacation(flightId, seller,  buyer, origin,destination, price, dateOfDeparture,  dateOfArrival);
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }

    public ArrayList<Flight> readConfirmedFlights(String buyerUserName){
        ArrayList<Flight> confirmedFlights = new ArrayList<>();
        confirmedFlights = confirmedSaleFlightsDB.readConfirmedVacations(buyerUserName);
        return confirmedFlights;
    }

    public void deleteConfirmedFlight(int flightID){
        confirmedSaleFlightsDB.deleteVacation(flightID);
    }

    public void insertFlight(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice){
        flightID++;
        Flight flight = new Flight(flightID, origin,  destination,  price,  destinationAirport,  dateOfDeparture,  dateOfArrival,  airlineCompany,  numOfTickets,  baggage,  ticketsType,  vacationStyle,  seller, originalPrice);
        try {
            availableFlightsDB.insertVacation(flight, flightID);
            allFlightsDB.insertFlight(flight, flightID);
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            //inform controller something is wrong

            //check in GUI that all values aren't null ,don't handle this here
        }
    }

    private void insertAvailableFlight(int flightId, String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice){

        Flight flight = new Flight(flightId, origin,  destination,  price,  destinationAirport,  dateOfDeparture,  dateOfArrival,  airlineCompany,  numOfTickets,  baggage,  ticketsType,  vacationStyle,  seller, originalPrice);
        try {
            availableFlightsDB.insertVacation(flight, flightID);
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
        matchesFlights = availableFlightsDB.readVacation(flight);
        return matchesFlights;
    }

    public ArrayList<Flight> getMatchesFlights(String origin, String destination, String dateOfDeparture, String dateOfArrival, int numOfTickets){
        ArrayList<Flight> matchesFlights = new ArrayList<Flight>();
        Flight flight = new Flight(origin,  destination,  dateOfDeparture,  dateOfArrival,  numOfTickets);
        matchesFlights = availableFlightsDB.readMatchVacations(flight);
        return matchesFlights;
    }

    public void insertPurchasedFlight(int flightId, String date, String time, String  userName){
        try{
           // purchasedFlightsDB.insertVacation( flightId, date,  time,  userName);
            purchasedFlightsDB.insertFlight(new PurchasedFlight(flightId,date,time,userName));
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
