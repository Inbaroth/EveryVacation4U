package View;

import Controller.Controller;
import Model.ConfirmedFlight;
import Model.Flight;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.ArrayList;

public class PendingMessages extends HomePage implements EventHandler<ActionEvent>{

    public VBox VB_buttonsConfirm;
    public VBox VB_buttonsCancel;
    public VBox VB_labels;
    private Controller controller;
    private Stage stage;
    private ArrayList<Button> buttonsListConfirm;
    private ArrayList<Button> buttonsListCancel;
    private ArrayList<Flight> flights;
    private ArrayList<Label> labelList;

    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
        setMessages();
    }

    public void setMessages() {
        ArrayList<Flight> pendingFlights = controller.readPendingFlights(controller.getUserName());
        this.flights = new ArrayList<>();
        this.buttonsListConfirm = new ArrayList<>();
        this.buttonsListCancel = new ArrayList<>();
        this.labelList = new ArrayList<>();
        for (Flight flight : pendingFlights) {
            String vacationID = String.valueOf(flight.getFlightId());
            String details = "שדה תעופה ביעד:"+ flight.getDestinationAirport() + " מס' כרטיסים: " + flight.getNumOfTickets() + "\n" +  " כבודה:"+ flight.getBaggage() + " סוג כרטיס: " + flight.getTicketsType() + "\n" + " מחיר: "+ flight.getPrice();
            Button buttonConfirm = new Button("אשר רכישה");
            buttonConfirm.setId(vacationID);
            buttonConfirm.setOnAction(this);
            buttonConfirm.setFont(new Font("Calibri Light",15));
            Button buttonCancel = new Button("סרב רכישה");
            buttonCancel.setId(vacationID);
            buttonCancel.setOnAction(this);
            buttonCancel.setFont(new Font("Calibri Light",15));
            buttonsListConfirm.add(buttonConfirm);
            buttonsListCancel.add(buttonCancel);
            Label label = new Label(details);
            label.setFont(new Font("Calibri Light",15));
            label.setPrefSize(500.0,38.0);
            this.flights.add(flight);
            labelList.add(label);
        }
        VB_buttonsConfirm.getChildren().clear();
        VB_buttonsConfirm.getChildren().addAll(buttonsListConfirm);
        VB_buttonsCancel.getChildren().clear();
        VB_buttonsCancel.getChildren().addAll(buttonsListCancel);
        VB_labels.getChildren().clear();
        VB_labels.getChildren().addAll(labelList);

    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        if (button.getText().equals("אשר רכישה")) {
            int index = buttonsListConfirm.indexOf(button);
            String buyer = controller.readPendingFlightBuyer(Integer.valueOf(button.getId()));
            controller.deletePendingFlight(button.getId());
            Label label = labelList.get(index);
            String labelText = label.getText();
            Flight flight = this.flights.get(index);
            //String[] data = labelText.split(",");
            ConfirmedFlight CF = new ConfirmedFlight(Integer.valueOf(button.getId()), controller.getUserName(), buyer);
            controller.insertConfirmedFlight(CF);
            button.setDisable(true);
            buttonsListCancel.get(index).setDisable(true);
            alert("הודעת אישור תועבר לקונה", Alert.AlertType.CONFIRMATION);
        }
        else{
            //למחוק מפנדיג
            // להוסיך לזמינים
            controller.deletePendingFlight(button.getId());
            int index = buttonsListCancel.indexOf(button);
            Label label = labelList.get(index);
            Flight flight = this.flights.get(index);
            controller.insertFlight(flight);
            button.setDisable(true);
            buttonsListConfirm.get(index).setDisable(true);

        }

    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
