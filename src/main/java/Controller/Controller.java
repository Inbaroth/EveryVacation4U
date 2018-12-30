package Controller;

import Model.Model;
import Model.Flight;
import Model.RegisteredUser;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller extends Observable implements Observer {

    private Model model;
    public static String currentUserName;

    private ArrayList<Flight> flightMatchSearches;



    /**
     * Constructor for the class Controller
     * @param model
     */
    public Controller(Model model) {

        this.model = model;
    }

    /**
     *
     * @param registeredUser
     * This method insert a new row to the data base with the given parameters
     */
    public String insertUser (RegisteredUser registeredUser, String confirmMessage) {
        return model.insert(registeredUser,confirmMessage);

    }

    /**
     * This method search and return the row in the database which is equal to the given userName
     * @param userName
     * @return the row
     */
    public RegisteredUser readUsers(String userName, Boolean isInsert){
        return model.readUsers(userName,isInsert);
    }


    public String updateDB(String oldUserName, RegisteredUser registeredUser, String confirmMessage){
        return model.updateUser(oldUserName, registeredUser,confirmMessage);
    }
    /**
     * This method deleteUser a row from the data base where the primary key is equal to @param userName
     * @param userName
     */
    public void deleteUser(String userName){
        model.deleteUser(userName);
    }

    public ArrayList<Flight> readPendingVacations(String sellerUserName){
        return model.readPendingFlights(sellerUserName);
    }

    public ArrayList<Flight> readConfirmedVacations(String buyerUserName){
        return model.readConfirmedFlights(buyerUserName);
    }

    public void deletePendingVacation(String vacationID){
        model.deleteFlightFlight(Integer.valueOf(vacationID));
    }
    public void insertConfirmedVacation(int vacationId,String seller, String buyer,String origin, String destination, int price, String dateOfDeparture, String dateOfArrival ){
        model.insertConfirmedFlight(vacationId,seller,buyer,origin,destination,price,dateOfDeparture,dateOfArrival);
    }

    public void insertPurchasedVacation(int vacationId,String date, String time,String  userName){
        model.insertPurchasedFlight(vacationId,date,time,userName);
    }

    /**
     * This method create an Alert object.
     * This method invoked when the user didn't insertUser an input
     */
    public void alert(String messageText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(messageText);
        alert.showAndWait();
        alert.close();
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o == model){
            setChanged();
            notifyObservers(arg);
        }

    }

    public void insertVacation(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice){
        model.insertFlight(origin, destination, price, destinationAirport, dateOfDeparture, dateOfArrival, airlineCompany, numOfTickets, baggage, ticketsType, vacationStyle, seller, originalPrice);
    }

    public int getVacationID(){
        return model.getFlightID();
    }


    public String signIn(String userName, String password){
        currentUserName = userName;
        return model.signIn(userName,password);
    }

    /**
     * return true if size is 0, else return false
     * @param origin
     * @param destination
     * @param dateOfDeparture
     * @param dateOfArrival
     * @param numOfTickets
     * @return
     */
    public boolean setMatchesVacations(String origin, String destination, String dateOfDeparture,String dateOfArrival,int numOfTickets){
        flightMatchSearches = new ArrayList<Flight>();
        flightMatchSearches = model.getMatchesFlights(origin,  destination,  dateOfDeparture,  dateOfArrival,  numOfTickets);
        if(flightMatchSearches.size()==0)
            return true;
        return false;
    }

    public ArrayList<Flight> getMatchesVacations(){
        if(flightMatchSearches !=null)
            return flightMatchSearches;
        return null;
    }



    public void deleteAvailableVacation(int vacationId){
        model.deleteAvailableFlight(vacationId);
    }

    public String readPendingVacationBuyer(int VacationId){
        return model.readPendingFlightBuyer(VacationId);
    }

    public String getUserName() {
        return currentUserName;
    }

    public void insertPendingvacation(int vacationId,String seller, String buyer ){
        model.insertPendingFlight(vacationId, seller, buyer);
    }


    public void deleteConfirmedVacation(int vacationID) {
        model.deleteConfirmedFlight(vacationID);
    }

        /**
         * FROM:YYYY-MM-DD  TO:DD/MM/YY
         * @param dataPickerValue YYYY-MM-DD
         * @return DD/MM/YY
         */
    public String changeToRightDateFormat(String dataPickerValue){
        String[] arr = new String[3];
        String str = dataPickerValue.substring(0,4);
        arr[2] = str;
        str = dataPickerValue.substring(5,7);
        arr[1] = str;
        str = dataPickerValue.substring(8);
        arr[0] = str;
        String RightDateFormat = arr[0] + "/" + arr[1] + "/" + arr[2] ;
        return RightDateFormat;
    }

    public void setUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }
}
