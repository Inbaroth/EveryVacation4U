package View;

import Controller.Controller;
import Model.Flight;
import Model.OfferedToSwapFlight;
import Model.PendingToSwapFlight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

public class MyFlightsForSwap  extends HomePage implements MyFlights, EventHandler<ActionEvent> {

    private Controller controller;
    private Stage stage;
    public javafx.scene.control.TableView flightBoard;
    public javafx.scene.control.TableColumn origin;
    public javafx.scene.control.TableColumn destination;
    public javafx.scene.control.TableColumn price;
    public javafx.scene.control.TableColumn DateOfDeparture;
    public javafx.scene.control.TableColumn DateOfArrival;
    public javafx.scene.control.TableColumn numberOfTickets;
    public javafx.scene.control.TableColumn buy;

    //use this to display the flights
    public VBox VB_buttons;
    public VBox VB_labels;
    private ArrayList<Button> buttonsList;
    private ArrayList<Label> labelList;

    public void setController(Controller controller, Stage primaryStage) {
        this.controller = controller;
        this.stage = primaryStage;
        displayFlights();

    }

    /**
     *
     *
     */
    public void displayFlights(){
        origin.setCellValueFactory(new PropertyValueFactory<Flight,String>("origin"));
        destination.setCellValueFactory(new PropertyValueFactory<Flight,String>("destination"));
        price.setCellValueFactory(new PropertyValueFactory<Flight,String>("price"));
        DateOfDeparture.setCellValueFactory(new PropertyValueFactory<Flight,String>("dateOfDeparture"));
        DateOfArrival.setCellValueFactory(new PropertyValueFactory<Flight,String>("dateOfArrival"));
        numberOfTickets.setCellValueFactory(new PropertyValueFactory<Flight,String>("numOfTickets"));
        buy.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Flight, String>, TableCell<Flight, String>> cellFactory
                = //
                new Callback<TableColumn<Flight, String>, TableCell<Flight, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Flight, String> param) {
                        final TableCell<Flight, String> cell = new TableCell<Flight, String>() {

                            final Button btn = new Button("הצע חופשה להחלפה");

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
                                        OfferedToSwapFlight offeredToSwapFlight = new OfferedToSwapFlight(UserHomePage.flightIdForSwap,flight.getFlightId());
                                        controller.insertOfferToSwapFlight(offeredToSwapFlight);
                                        alert("בקשתך נשלחה למוכר", Alert.AlertType.CONFIRMATION);
                                        btn.setDisable(true);
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
        buy.setCellFactory(cellFactory);
        ObservableList<Flight> data = FXCollections.observableArrayList(controller.getFlightsToSwap(controller.getUserName()));
        flightBoard.setItems(data);
    }


    @Override
    public void handle(ActionEvent event) {

    }



}
