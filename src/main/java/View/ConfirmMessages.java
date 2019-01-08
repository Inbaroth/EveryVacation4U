package View;

import Controller.Controller;
import Model.Flight;
import Model.PendingToSwapFlight;
import Model.RegisteredUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

public class ConfirmMessages extends HomePage{

    public VBox VB_buttons;
    public VBox VB_labels;
    private ArrayList<Button> buttonsList;
    private ArrayList<Label> labelList;
    private Controller controller;
    private Stage stage;
    private FlightsWaitingForApproval flightsWaitingForApproval;
    public static int itemID;
    public javafx.scene.control.TableView flightBoard;
    public javafx.scene.control.TableColumn origin;
    public javafx.scene.control.TableColumn destination;
    public javafx.scene.control.TableColumn price;
    public javafx.scene.control.TableColumn DateOfDeparture;
    public javafx.scene.control.TableColumn DateOfArrival;
    public javafx.scene.control.TableColumn numberOfTickets;
    public javafx.scene.control.TableColumn email;

    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
        setMessages();
    }

    public void setMessages() {
        origin.setCellValueFactory(new PropertyValueFactory<Flight,String>("origin"));
        destination.setCellValueFactory(new PropertyValueFactory<Flight,String>("destination"));
        price.setCellValueFactory(new PropertyValueFactory<Flight,String>("price"));
        DateOfDeparture.setCellValueFactory(new PropertyValueFactory<Flight,String>("dateOfDeparture"));
        DateOfArrival.setCellValueFactory(new PropertyValueFactory<Flight,String>("dateOfArrival"));
        numberOfTickets.setCellValueFactory(new PropertyValueFactory<Flight,String>("numOfTickets"));
        email.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Flight, String>, TableCell<Flight, String>> cellFactory
                = //
                new Callback<TableColumn<Flight, String>, TableCell<Flight, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Flight, String> param) {
                        final TableCell<Flight, String> cell = new TableCell<Flight, String>() {

                            final Button btn = new Button("צור קשר עם המוכר");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                this.setAlignment(Pos.CENTER);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Flight flight = getTableView().getItems().get(getIndex());
                                        flight = controller.getFlight(flight.getFlightId());
                                        RegisteredUser registeredUser = controller.readUsers(flight.getSeller(),false);
                                        String email = registeredUser.getEmail();
                                        String message = "צור קשר עם מוכר החופשה במייל " + email + " להעברת התשלום";
                                        alert(message, Alert.AlertType.INFORMATION);
                                        //stage.close();

                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        email.setCellFactory(cellFactory);
        ObservableList<Flight> data = FXCollections.observableArrayList(controller.readConfirmedFlights(controller.getUserName()));
        flightBoard.setItems(data);


    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
