package View;

import Controller.Controller;
import Model.Flight;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class DisplayVacations extends HomePage implements EventHandler<ActionEvent> {

    private Controller controller;
    private Stage stage;
    private SignIn signInWindow;
    ArrayList<Flight> matchFlights;
    public VBox vb_Buttons;
    public VBox vb_details;
    public Label l_originAndDestination;
    public Label l_dates;

    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
        matchFlights = new ArrayList<>();
        matchFlights = controller.getMatchesVacations();
        offerVacations();
    }
             //(ActionEvent event)
    void offerVacations() {
        //check this is the right order HERE
        l_originAndDestination.setText("מ"+ matchFlights.get(0).getOrigin() + " אל "+ matchFlights.get(0).getDestination() );
        l_dates.setText(matchFlights.get(0).getDateOfDeparture() + "-" + matchFlights.get(0).getDateOfArrival() );
        ArrayList<Button> buttonlist = new ArrayList<Button>(); //our Collection to hold newly created Buttons
        String buttonTitle = "רכוש חופשה"; //extract button text, adapt the String to the columnname that you are interested in
        ArrayList<Label> detailsLabels = new ArrayList<Label>();
        //change this to flight details
        for (Flight flight : matchFlights) {
            Button btn = new Button(buttonTitle);
            btn.setId(String.valueOf(flight.getFlightId() + "," + flight.getSeller()));
            btn.setFont(new Font("Calibri Light", 15));
            btn.setPrefHeight(38.0);
            btn.setOnAction(this);
           // btn.setTextFill();
            buttonlist.add(btn);
            String details = "שדה תעופה ביעד:"+ flight.getDestinationAirport() + " מס' כרטיסים: " + flight.getNumOfTickets() + "\n" +  " כבודה:"+ flight.getBaggage() + " סוג כרטיס: " + flight.getTicketsType() + "\n" + " מחיר: "+ flight.getPrice();
            Label lbl = new Label();
            lbl.setText(details);
            lbl.setFont(new Font("Calibri Light", 15));
            lbl.setPrefSize(500.0,38.0);
            detailsLabels.add(lbl);
        }
        vb_Buttons.getChildren().clear();
        vb_Buttons.getChildren().addAll(buttonlist);

        vb_details.getChildren().clear();
        vb_details.getChildren().addAll(detailsLabels);
    }


    @Override
    public void handle(ActionEvent event) {
        if (controller.getUserName() == null){
            alert("על מנת לרכוש חופשה עליך להתחבר למערכת תחילה", Alert.AlertType.ERROR);
            stage.close();
        }
        else {
            Button button = (Button) event.getSource();
            String[] split = button.getId().split(",");
            String vacationID = split[0];
            String seller = split[1];
            controller.insertPendingvacation(Integer.valueOf(vacationID), seller, controller.getUserName());
            controller.deleteAvailableVacation(Integer.valueOf(vacationID));
            alert("בקשתך נשלחה למוכר", Alert.AlertType.CONFIRMATION);
            stage.close();
        }

    }
}
