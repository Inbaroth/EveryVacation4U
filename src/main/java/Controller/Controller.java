package Controller;

import Model.Model;
import Model.UserModel;
import Model.FlightModel;
import Model.Flight;
import Model.RegisteredUser;
import Model.PurchasedFlight;
import Model.PendingFlight;
import Model.ConfirmedFlight;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller extends Observable implements Observer {

    private UserModel userModel;
    private FlightModel flightModel;
    public static String currentUserName;
    private ArrayList<Flight> flightMatchSearches;



    /**
     * Constructor for the class Controller
     * @param userModel
     */
    public Controller(UserModel userModel, FlightModel flightModel) {
        this.userModel = userModel;
        this.flightModel = flightModel;
    }

    /**
     *
     * @param registeredUser
     * This method insertUser a new row to the data base with the given parameters
     */
    public String insertUser(RegisteredUser registeredUser, String confirmPassword) {
        return userModel.createUser(registeredUser,confirmPassword);

    }

    /**
     * This method searchUser and return the row in the database which is equal to the given userName
     * @param userName
     * @return the row
     */
    public RegisteredUser readUsers(String userName, Boolean isInsert){
        return userModel.searchUsers(userName,isInsert);
    }


    public String updatedDB(String oldUserName, RegisteredUser registeredUser, String confirmPassword){
        return userModel.updateUser(oldUserName, registeredUser,confirmPassword);
    }
    /**
     * This method deleteUser a row from the data base where the primary key is equal to @param userName
     * @param userName
     */
    public void deleteUser(String userName){
        userModel.deleteUser(userName);
    }

    public ArrayList<Flight> readPendingFlights(String sellerUserName){
        return flightModel.readPendingFlights(sellerUserName);
    }

    public ArrayList<Flight> readConfirmedFlights(String buyerUserName){
        return flightModel.readConfirmedFlights(buyerUserName);
    }

    public void deletePendingFlight(String vacationID){
        flightModel.deletePendingFlight(Integer.valueOf(vacationID));
    }
    public void insertConfirmedFlight(ConfirmedFlight CFlight){
        flightModel.insertConfirmedFlight(CFlight);
    }

    public void insertPurchasedFlight(PurchasedFlight flight){
        flightModel.insertPurchasedFlight(flight);
    }

    /**
     * This method create an Alert object.
     * This method invoked when the user didn't insertUser an input
     */
/*
    public void alert(String messageText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(messageText);
        alert.showAndWait();
        alert.close();
    }
*/


    @Override
    public void update(Observable o, Object arg) {
        /// check if has usage!!!!!!!
        if (o == userModel){
            setChanged();
            notifyObservers(arg);
        }

    }

    public void insertFlight(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice){
        flightModel.insertFlight(origin, destination, price, destinationAirport, dateOfDeparture, dateOfArrival, airlineCompany, numOfTickets, baggage, ticketsType, vacationStyle, seller, originalPrice);
    }

    public int getflightID(){
        return flightModel.getFlightID();
    }

    public String signIn(String userName, String password){
        currentUserName = userName;
        return userModel.signIn(userName,password);
    }

    /**
     * return true if size is 0, else return false
     * @return
     */
    public boolean setMatchesFlights(Flight flight){
        flightMatchSearches = new ArrayList<Flight>();
        flightMatchSearches = flightModel.getMatchesFlights(flight);
        if(flightMatchSearches.size()==0)
            return true;
        return false;
    }

    public ArrayList<Flight> getMatchesFlights(){
        if(flightMatchSearches !=null)
            return flightMatchSearches;
        return null;
    }



    public void deleteAvailableFlight(int vacationId){
        flightModel.deleteAvailableFlight(vacationId);
    }

    public String readPendingFlightBuyer(int VacationId){
        return flightModel.readPendingFlightBuyer(VacationId);
    }

    public String getUserName() {
        return currentUserName;
    }

    public void insertPendingFlight(PendingFlight pFlight){
        flightModel.insertPendingFlight(pFlight);
    }


    public void deleteConfirmedFlight(int vacationID) {
        flightModel.deleteConfirmedFlight(vacationID);
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
