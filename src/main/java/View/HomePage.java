package View;

import Controller.Controller;
import Model.Flight;
import Model.RegisteredUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;


import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class HomePage implements Observer {
    private Controller controller;

    public javafx.scene.control.Button btn_createUser;
    public javafx.scene.control.Button btn_signIn;
    public ImageView iv_homePageImage;
    public javafx.scene.control.TextField tf_origin;
    public javafx.scene.control.TextField tf_destination;
    public javafx.scene.control.DatePicker dp_departure;
    public javafx.scene.control.DatePicker dp_arrival;
    public javafx.scene.control.TextField tf_numOfTickets;
    public javafx.scene.control.TableView flightBoard;
    public javafx.scene.control.TableColumn origin;
    public javafx.scene.control.TableColumn destination;
    public javafx.scene.control.TableColumn price;
    public javafx.scene.control.TableColumn DateOfDeparture;
    public javafx.scene.control.TableColumn DateOfArrival;
    public javafx.scene.control.TableColumn numberOfTickets;
    public javafx.scene.control.TableColumn buy;
    public javafx.scene.control.TableView exchangeBoard;
    public javafx.scene.control.TableColumn origin1;
    public javafx.scene.control.TableColumn destination1;
    public javafx.scene.control.TableColumn price1;
    public javafx.scene.control.TableColumn DateOfDeparture1;
    public javafx.scene.control.TableColumn DateOfArrival1;
    public javafx.scene.control.TableColumn numberOfTickets1;
    public javafx.scene.control.TableColumn buy1;


    //use this for board of all Flights
    public VBox VB_buttons;
    public VBox VB_labels;
    private ArrayList<Button> buttonsList;
    private ArrayList<Label> labelList;

    private Insert insertWindow;
    private SignIn signInWindow;
    private UserHomePage userHomeWindow;
    private Payment payment;
    private Read read;
    private Update updateWindow;
    private DisplayVacations displayVacations;
    private Stage primaryStage;

    public final Tooltip tooltip = new Tooltip();

    public static Stage stage;

    public void setController(Controller controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;
        stage = primaryStage;
        //setImage();
        tooltip.setText("\nהכנס מיקום בפורמט:\n"+"עיר,מדינה"+"\n");
        tf_origin.setTooltip(tooltip);
        tf_destination.setTooltip(tooltip);
    }
    public void create(ActionEvent actionEvent) {
        newStage("insert.fxml", "", insertWindow, 583, 493,controller);
    }

    public void signIn(ActionEvent actionEvent){
        newStage("SignIn.fxml", "כניסת משתמש רשום", signInWindow, 432, 383 , controller);
    }

    public void setImage()  {
/*    try {
        Image img2 = new Image(getClass().getResource("/mainImage.jpg").toURI().toString());
        iv_homePageImage.setImage(img2);
    }catch (URISyntaxException e){
        System.out.println(e.getReason() + "," + e.getMessage());
     }*/
    }

    /**
     * creates a new window, based on given details and shows it
     * @param fxmlName - name of the stage fxml file
     * @param title - title of the window
     * @param windowName - name of the java class represents the stage
     * @param width of window
     * @param height of window
     * @param controller - controller of the program, link between the view and model
     */
    protected void newStage(String fxmlName,String title, HomePage windowName, int width, int height, Controller controller){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("/" + fxmlName).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);
        SetStageCloseEvent(stage);
        stage.show();
        windowName = fxmlLoader.getController();
        windowName.setController(controller, stage);
        controller.addObserver(windowName);

        if (windowName instanceof Update){
            RegisteredUser registeredUserDetails = controller.readUsers(controller.getUserName(),false);
            updateWindow = (Update) windowName;
            updateWindow.setUserDetails(registeredUserDetails);
        }



    }


    protected void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("כן");
                ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("חזור");
                alert.setContentText("האם אתה בטוח שברצונך לעצוב?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();

                }
            }
        });
    }


    public void search(ActionEvent actionEvent){
        if(tf_origin.getText()==null || tf_destination.getText()==null  || dp_departure.getValue()==null || dp_arrival.getValue() == null || tf_origin.getText().equals("") || tf_destination.getText().equals("")  || dp_departure.getValue().equals("") || dp_arrival.getValue().equals("")) {
            alert("אופס! אחד או יותר משדות החיפוש ריקים", Alert.AlertType.ERROR);
            return;
        } else {
            int numberOfTickets = 0;
            //valid number (not empty)
            if (!tf_numOfTickets.getText().trim().equals("") && StringUtils.isNumeric(tf_numOfTickets.getText()))
                numberOfTickets = Integer.valueOf(tf_numOfTickets.getText());
                //invalid number (not empty)
            if (!tf_numOfTickets.getText().trim().equals("") && !StringUtils.isNumeric(tf_numOfTickets.getText())) {
                alert("אופס! הערך שהוזן במספר טיסות איננו תקין.", Alert.AlertType.ERROR);
                return;
            }
            if(!isValidChosenDate(dp_departure, dp_arrival) ) {
                alert("אנא הזן טווח תאריכים חוקי", Alert.AlertType.ERROR);
                return;
            }
            //empty, make default 1
            else if (tf_numOfTickets.getText().trim().equals("") || StringUtils.isNumeric(tf_numOfTickets.getText())) {
                //tf_numOfTickets.setText("1");
                numberOfTickets = 1;
                String dateDepart = controller.changeToRightDateFormat(dp_departure.getValue().toString());
                String dateArriv = controller.changeToRightDateFormat(dp_arrival.getValue().toString());
//                String dateDepart = dp_departure.getValue().toString();
//                String dateArriv = dp_arrival.getValue().toString();
                Flight flight= new Flight(tf_origin.getText().trim(), tf_destination.getText().trim(), dateDepart, dateArriv, numberOfTickets);
                if(!controller.setMatchesFlights(flight))
                    newStage("DisplayVacations.fxml", "", displayVacations, 635, 525, controller);
                else
                    alert("מתנצלים אך אין חופשה שתואמת את החיפוש שלך", Alert.AlertType.INFORMATION);
                //tf_numOfTickets.clear();
            }
        }

    }

    public void sellTickets(ActionEvent actionEvent){
        newStage("SignIn.fxml", "כניסת משתמש רשום", signInWindow, 432, 383 , controller);
        //newStage("SignIn.fxml", "כניסת משתמש רשום", signInWindow, 432, 383 , controller);
    }


    @Override
    public void update(Observable o, Object arg) {

    }

    protected void alert(String messageText, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(messageText);
        alert.showAndWait();
        alert.close();
    }

    public void searchUser(){
        newStage("SearchUser.fxml", "חיפוש משתמש", searchUser,364, 284, controller);
    }


    public void displayAvailableFlights(Controller controller){
        this.availableFlights = controller.getAllAvailableFlights();
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
                                            PendingFlight PF = new PendingFlight(flight.getFlightId(), flight.getSeller(), controller.getUserName());
                                            controller.insertPendingFlight(PF);
                                            controller.deleteAvailableFlight(Integer.valueOf(flight.getFlightId()));
                                            alert("בקשתך נשלחה למוכר", Alert.AlertType.CONFIRMATION);
                                            stage.close();
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
        ObservableList<Flight> data = FXCollections.observableArrayList(availableFlights);
        flightBoard.setItems(data);

        origin1.setCellValueFactory(new PropertyValueFactory<Flight,String>("origin"));
        destination1.setCellValueFactory(new PropertyValueFactory<Flight,String>("destination"));
        price1.setCellValueFactory(new PropertyValueFactory<Flight,String>("price"));
        DateOfDeparture1.setCellValueFactory(new PropertyValueFactory<Flight,String>("dateOfDeparture"));
        DateOfArrival1.setCellValueFactory(new PropertyValueFactory<Flight,String>("dateOfArrival"));
        numberOfTickets1.setCellValueFactory(new PropertyValueFactory<Flight,String>("numOfTickets"));
        buy.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Flight, String>, TableCell<Flight, String>> cellFactory1
                = //
                new Callback<TableColumn<Flight, String>, TableCell<Flight, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Flight, String> param) {
                        final TableCell<Flight, String> cell = new TableCell<Flight, String>() {

                            final Button btn = new Button("הגש בקשה להחלפה");

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
                                            PendingFlight PF = new PendingFlight(flight.getFlightId(), flight.getSeller(), controller.getUserName());
                                            controller.insertPendingFlight(PF);
                                            controller.deleteAvailableFlight(Integer.valueOf(flight.getFlightId()));
                                            alert("בקשתך נשלחה למוכר", Alert.AlertType.CONFIRMATION);
                                            stage.close();
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
        buy1.setCellFactory(cellFactory1);
        ObservableList<Flight> data1 = FXCollections.observableArrayList(controller.readPendingToSwapFlights());
        exchangeBoard.setItems(data1);
    }



    public boolean isValidChosenDate(javafx.scene.control.DatePicker firstDate, javafx.scene.control.DatePicker secondDate) {
        LocalDate departure = firstDate.getValue();
        LocalDate arrival = secondDate.getValue();
        //no date chosen
        if (departure==null || arrival==null)
            return false;
        //if depart date in the past
        if ( LocalDate.from(departure).until(LocalDate.now(), ChronoUnit.DAYS) >= 0 )
            return false;
        //if arrive date in the past
        if( LocalDate.from(arrival).until(LocalDate.now(), ChronoUnit.DAYS) >= 0)
            return false;
        //if arrival before departure
        if(LocalDate.from(arrival).until(LocalDate.from(departure), ChronoUnit.DAYS) >=0)
            return false;

        return true;
    }


}
