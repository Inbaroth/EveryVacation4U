package View;

import Controller.Controller;
import Model.Flight;
import Model.PurchasedFlight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Observer;

public class FlightsWaitingForApproval extends HomePage implements Observer {


    private Controller controller;
    private Stage stage;
    public javafx.scene.control.TableView flightBoard;
    public javafx.scene.control.TableColumn origin;
    public javafx.scene.control.TableColumn destination;
    public javafx.scene.control.TableColumn price;
    public javafx.scene.control.TableColumn DateOfDeparture;
    public javafx.scene.control.TableColumn DateOfArrival;
    public javafx.scene.control.TableColumn numberOfTickets;
    public javafx.scene.control.TableColumn approve;


    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
        displayFlights();

    }

    private void displayFlights(){
        origin.setCellValueFactory(new PropertyValueFactory<Flight,String>("origin"));
        destination.setCellValueFactory(new PropertyValueFactory<Flight,String>("destination"));
        price.setCellValueFactory(new PropertyValueFactory<Flight,String>("price"));
        DateOfDeparture.setCellValueFactory(new PropertyValueFactory<Flight,String>("dateOfDeparture"));
        DateOfArrival.setCellValueFactory(new PropertyValueFactory<Flight,String>("dateOfArrival"));
        numberOfTickets.setCellValueFactory(new PropertyValueFactory<Flight,String>("numOfTickets"));
        approve.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Flight, String>, TableCell<Flight, String>> cellFactory
                = //
                new Callback<TableColumn<Flight, String>, TableCell<Flight, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Flight, String> param) {
                        final TableCell<Flight, String> cell = new TableCell<Flight, String>() {

                            final Button btn = new Button("אשר קבלת תשלום");

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
                                        String buyer = controller.readConfirmedFlightBuyer(flight.getFlightId());
                                        LocalDateTime localDateTime = LocalDateTime.now();
                                        String date = LocalDateTime.now().toString().substring(0,localDateTime.toString().indexOf("T"));
                                        String time = LocalTime.now().toString();
                                        controller.deleteConfirmedFlight(flight.getFlightId());
                                        PurchasedFlight purchasedFlight = new PurchasedFlight(flight.getFlightId(),date,time,buyer);
                                        controller.insertPurchasedFlight(purchasedFlight);
                                        alert("מצוין!", Alert.AlertType.CONFIRMATION);
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
        approve.setCellFactory(cellFactory);
        ObservableList<Flight> data = FXCollections.observableArrayList(controller.readConfirmedFlightsSeller(controller.getUserName()));
        flightBoard.setItems(data);
    }


}
