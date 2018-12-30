package View;

import Controller.Controller;
import Model.Flight;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ConfirmMessages extends HomePage implements EventHandler<ActionEvent> {

    public VBox VB_buttons;
    public VBox VB_labels;
    private Controller controller;
    private Stage stage;
    private Payment payment;
    private ArrayList<Button> buttonsList;
    private ArrayList<Label> labelList;
    public static int vacationID;

    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
        setMessages();
    }

    public void setMessages() {
        ArrayList<Flight> pendingFlights = controller.readConfirmedVacations(controller.getUserName());
        this.buttonsList = new ArrayList<>();
        this.labelList = new ArrayList<>();
        for (Flight flight : pendingFlights) {
            String vacationID = String.valueOf(flight.getFlightId());
            String details = "שדה תעופה ביעד:"+ flight.getDestination() + "\n" + " מחיר: "+ flight.getNumOfTickets();
            Button button = new Button("קנה עכשיו");
            button.setId(vacationID);
            button.setOnAction(this);
            button.setFont(new Font("Calibri Light",15));
            buttonsList.add(button);
            Label label = new Label(details);
            label.setFont(new Font("Calibri Light",15));
            labelList.add(label);
        }
        VB_buttons.getChildren().clear();
        VB_buttons.getChildren().addAll(buttonsList);
        VB_labels.getChildren().clear();
        VB_labels.getChildren().addAll(labelList);

    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        int index = buttonsList.indexOf(button);
        this.vacationID = Integer.valueOf(button.getId());
        newStage("Payment.fxml", "כניסת משתמש רשום", payment, 600, 400,controller);
    }
    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
