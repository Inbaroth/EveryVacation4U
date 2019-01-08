package View;

import Controller.Controller;
import Model.Flight;
import Model.PendingFlight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.util.Callback;

public class DisplayVacations extends HomePage implements EventHandler<ActionEvent> {

    private Controller controller;
    private Stage stage;
    private SignIn signInWindow;
    private UserHomePage userHomePage;
    private ArrayList<Flight> matchFlights;
    public javafx.scene.control.TableView flightBoard;
    public javafx.scene.control.TableColumn origin;
    public javafx.scene.control.TableColumn destination;
    public javafx.scene.control.TableColumn price;
    public javafx.scene.control.TableColumn DateOfDeparture;
    public javafx.scene.control.TableColumn DateOfArrival;
    public javafx.scene.control.TableColumn numberOfTickets;

    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
        matchFlights = new ArrayList<>();
        matchFlights = controller.getMatchesFlights();
        offerVacations();
    }
             //(ActionEvent event)
    public void offerVacations() {
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

                            final Button btn = new Button("רכש חופשה");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                this.setAlignment(Pos.CENTER);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        if (controller.getUserName() == null)
                                            alert("עלייך להתחבר למערכת תחילה", Alert.AlertType.ERROR);
                                        else {
                                            Flight flight = getTableView().getItems().get(getIndex());
                                            flight = controller.getFlight(flight.getFlightId());
                                            if (!controller.getUserName().equals(flight.getSeller())) {
                                                PendingFlight PF = new PendingFlight(flight.getFlightId(), flight.getSeller(), controller.getUserName());
                                                controller.insertPendingFlight(PF);
                                                controller.deleteAvailableFlight(flight.getFlightId());
                                                alert("בקשתך נשלחה למוכר", Alert.AlertType.CONFIRMATION);
                                                btn.setDisable(true);
                                                //newStage("UserHomePage.fxml", "כניסת משתמש רשום", userHomePage, 995, 716,controller);
                                                //   stage.close();
                                            }
                                            else{
                                                alert("אתה לא יכול לרכוש חופשה שבבעלותך", Alert.AlertType.ERROR);

                                            }
                                        }
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
        ObservableList<Flight> data = FXCollections.observableArrayList(controller.getMatchesFlights());
        flightBoard.setItems(data);
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
            PendingFlight PF = new PendingFlight(Integer.valueOf(vacationID), seller, controller.getUserName());
            controller.insertPendingFlight(PF);
            controller.deleteAvailableFlight(Integer.valueOf(vacationID));
            alert("בקשתך נשלחה למוכר", Alert.AlertType.CONFIRMATION);
            stage.close();
        }

    }
}
