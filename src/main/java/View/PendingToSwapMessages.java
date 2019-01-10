package View;

import Controller.Controller;
import Model.Flight;
import Model.PendingFlight;
import Model.RegisteredUser;
import Model.SwappedFlight;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class PendingToSwapMessages extends HomePage {


    public ChoiceBox chooseFlight;
    public Label flightData;
    public Button confirm;
    private Controller controller;
    private Stage stage;
    private TreeMap<Integer, Vector<Flight>> offers;
    private Vector<Flight> flights;
    private String pendingFlight;
    private String chosenFlight;
    private PendingToSwapMessages pendingToSwapMessages;



    public void setController(Controller controller, Stage primaryStage) {
        this.controller = controller;
        this.stage = primaryStage;
        this.offers = controller.readOfferedToSwapFlights(controller.getUserName());
        setChoiceBox();
        //setOffers();
    }


    public void setChoiceBox(){
        for (Map.Entry<Integer, Vector<Flight>> entry: offers.entrySet()){
            if (entry.getValue().size() > 0)
                chooseFlight.getItems().add(entry.getKey());
        }

        chooseFlight.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                flights = offers.get(chooseFlight.getItems().get((Integer) number2));
                pendingFlight = String.valueOf(chooseFlight.getItems().get((Integer) number2));
                flightData.setText(getFlightDate(pendingFlight));
                setOffers();
            }
        });
    }

    public void setOffers(){
        origin.setCellValueFactory(new PropertyValueFactory<Flight,String>("origin"));
        destination.setCellValueFactory(new PropertyValueFactory<Flight,String>("destination"));
        price.setCellValueFactory(new PropertyValueFactory<Flight,String>("price"));
        DateOfDeparture.setCellValueFactory(new PropertyValueFactory<Flight,String>("dateOfDeparture"));
        DateOfArrival.setCellValueFactory(new PropertyValueFactory<Flight,String>("dateOfArrival"));
        numberOfTickets.setCellValueFactory(new PropertyValueFactory<Flight,String>("numOfTickets"));
        buy.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        decline.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Flight, String>, TableCell<Flight, String>> cellFactory
                = //
                new Callback<TableColumn<Flight, String>, TableCell<Flight, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Flight, String> param) {
                        final TableCell<Flight, String> cell = new TableCell<Flight, String>() {

                            final javafx.scene.control.Button btn = new Button("בחר חופשה להחלפה");

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
                                        chosenFlight = String.valueOf(flight.getFlightId());
                                        SwappedFlight swappedFlight = new SwappedFlight(Integer.valueOf(pendingFlight),Integer.valueOf(chosenFlight));
                                        controller.insertSwappedFlight(swappedFlight);
                                        controller.deleteOfferToSwapFlight(Integer.valueOf(pendingFlight),Integer.valueOf(chosenFlight));
                                        controller.deletePendingToSwapFlight(Integer.valueOf(pendingFlight));
                                        String buyer = controller.readPurchasedFlightBuyer(Integer.valueOf(chosenFlight));
                                        RegisteredUser registeredUser = controller.readUsers(buyer,false);
                                        String email = registeredUser.getEmail();
                                        String message = "צור קשר עם מחליף החופשה במייל " + email + " להעברת הבעלות";
                                        alert(message, Alert.AlertType.INFORMATION);
                                        deleteAllOffersFlights();
                                        stage.close();
                                        newStage("PendingToSwapMessages.fxml", "החלפת חופשות", pendingToSwapMessages, 907, 457,controller);
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

                            final Button btn = new Button("סרב");

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
                                        chosenFlight = String.valueOf(flight.getFlightId());
                                        controller.deleteOfferToSwapFlight(Integer.valueOf(pendingFlight),Integer.valueOf(chosenFlight));
                                       // String buyer = controller.readPurchasedFlightBuyer(Integer.valueOf(chosenFlight));
                                        //in the future the app will send an email declaring the decline
                                        alert("משובך יועבר למציע ההחלפה", Alert.AlertType.INFORMATION);
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
        decline.setCellFactory(cellFactoryDecline);
        ObservableList<Flight> data = FXCollections.observableArrayList(flights);
        flightBoard.setItems(data);
    }

    private void deleteAllOffersFlights() {
        for (Flight flight: flights) {
            controller.deleteOfferToSwapFlight(Integer.valueOf(pendingFlight),flight.getFlightId());

        }
    }

    public String getFlightDate (String flightId){
        Flight flight = controller.getFlight(Integer.valueOf(flightId));
        return "טיסה מ " + flight.getOrigin() + " אל " + flight.getDestination() + " בתאריכים " + flight.getDateOfArrival() + " - " + flight.getDateOfDeparture();
    }

}
