package View;

import Controller.Controller;
import Model.Flight;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.*;

public class SignIn extends HomePage implements Observer{

    private Controller controller;
    private Stage stage;
    private String userDetails;
    private UserHomePage userHomePage;
    private PendingMessages pendingMessage;
    public javafx.scene.control.TextField username;
    public javafx.scene.control.PasswordField password;


    public void setController(Controller controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;

    }

    public void logIn(){

        String userName = username.getText();
        String Password = password.getText();

        //if one or more is empty
        if(userName == null || Password == null || username.getText().trim().isEmpty() || password.getText().trim().isEmpty()){
            alert("שדה אחד או יותר ריקים", Alert.AlertType.ERROR);
        }
        else{
            String ans = controller.signIn(userName, Password);
            if (ans != null && !ans.equals(userName))
                alert(ans,Alert.AlertType.ERROR);
            else if (ans != null && ans.equals(userName)){
                stage.close();
                newStage("UserHomePage.fxml", "כניסת משתמש רשום", userHomePage, 940, 581,controller);
                HomePage.stage.close();
                ArrayList<Flight> pendingFlights = controller.readPendingVacations(controller.getUserName());
                //VacationId,Origin,Destionation,Price,DateOfDeparture,Date
                if (pendingFlights.size() > 0)
                    newStage("PendingMessages.fxml", "אשר רכישת חופשות", pendingMessage, 604, 312,controller);
                ArrayList<Flight> confirmFlights = controller.readConfirmedVacations(controller.getUserName());
                if (confirmFlights.size() > 0){
                    newStage("ConfirmMessages.fxml", "אשר תשלום עבור חופשות", pendingMessage, 400, 600,controller);

                }

            }
        }


    }

}

