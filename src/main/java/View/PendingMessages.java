package View;

import Controller.Controller;
import Model.ConfirmedFlight;
import Model.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Callback;

public class PendingMessages extends HomePage{

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
    public javafx.scene.control.TableColumn decline;


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
        approve.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        decline.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Flight, String>, TableCell<Flight, String>> cellFactory
                = //
                new Callback<TableColumn<Flight, String>, TableCell<Flight, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Flight, String> param) {
                        final TableCell<Flight, String> cell = new TableCell<Flight, String>() {

                            final Button btn = new Button("אשר מכירת חופשה");

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
                                        String buyer = controller.readPendingFlightBuyer(flight.getFlightId());
                                        ConfirmedFlight confirmedFlight = new ConfirmedFlight(flight.getFlightId(),controller.getUserName(),buyer);
                                        controller.insertConfirmedFlight(confirmedFlight);
                                        controller.deletePendingFlight(flight.getFlightId());
                                        alert("הסכמת רכישה תועבר לקונה", Alert.AlertType.CONFIRMATION);
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
        Callback<TableColumn<Flight, String>, TableCell<Flight, String>> cellFactoryDecline
                = //
                new Callback<TableColumn<Flight, String>, TableCell<Flight, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Flight, String> param) {
                        final TableCell<Flight, String> cell = new TableCell<Flight, String>() {

                            final Button btn = new Button("סרב מכירת חופשה");

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
                                        controller.insertAvailableFlight(flight);
                                        controller.deletePendingFlight(flight.getFlightId());
                                        alert("החופשה תוחזר ללוח טיסות", Alert.AlertType.CONFIRMATION);
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
        decline.setCellFactory(cellFactoryDecline);
        ObservableList<Flight> data = FXCollections.observableArrayList(controller.readPendingFlights(controller.getUserName()));
        flightBoard.setItems(data);

    }


    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
