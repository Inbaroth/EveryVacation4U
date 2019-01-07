package View;

import Controller.Controller;
import Model.Flight;
import Model.PendingFlight;
import Model.RegisteredUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.lang3.StringUtils;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class HomePage implements Observer, EventHandler<ActionEvent>{
    private Controller controller;

    public javafx.scene.control.Button btn_createUser;
    public javafx.scene.control.Button btn_signIn;
    public ImageView iv_homePageImage;

    public javafx.scene.control.TextField tf_origin;
    public javafx.scene.control.TextField tf_destination;
    public javafx.scene.control.DatePicker dp_departure;
    public javafx.scene.control.DatePicker dp_arrival;
    public javafx.scene.control.TextField tf_numOfTickets;

    //use this for board of all Flights
    public VBox VB_buttons;
    public VBox VB_labels;
    private ArrayList<Button> buttonsList;
    private ArrayList<Label> labelList;
    private ArrayList<Flight> availableFlights;

    private SignUp signUpWindow;
    private SignIn signInWindow;
    private UserHomePage userHomeWindow;
    private Payment payment;
    private SearchUser searchUser;
    private UpdateUserDetails updateUserDetailsWindow;
    private DisplaySearchedFlights displaySearchedFlights;
    private Stage primaryStage;

    public final Tooltip tooltip = new Tooltip();

    public static Stage stage;

    public void setController(Controller controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;
        stage = primaryStage;
        setImage();
        tooltip.setText("\nהכנס מיקום בפורמט:\n"+"עיר,מדינה"+"\n");
        tf_origin.setTooltip(tooltip);
        tf_destination.setTooltip(tooltip);
        this.availableFlights = controller.getAllAvailableFlights();
        displayAvailableFlights();
    }
    public void create(ActionEvent actionEvent) {
        newStage("SignUp.fxml", "", signUpWindow, 583, 493,controller);
    }

    public void signIn(ActionEvent actionEvent){
        newStage("SignIn.fxml", "כניסת משתמש רשום", signInWindow, 432, 383 , controller);
    }

    public void setImage()  {
    try {
        Image img2 = new Image(getClass().getResource("/mainImage.jpg").toURI().toString());
        iv_homePageImage.setImage(img2);
    }catch (URISyntaxException e){
        System.out.println(e.getReason() + "," + e.getMessage());
     }
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

        if (windowName instanceof UpdateUserDetails){
            RegisteredUser registeredUserDetails = controller.readUsers(controller.getUserName(),false);
            updateUserDetailsWindow = (UpdateUserDetails) windowName;
            updateUserDetailsWindow.setUserDetails(registeredUserDetails);
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
                    // ... user chose OK
                    // Close program

                    //disable  buttons as needed
                    //btn_update.setDisable(false);
                    //btn_delete.setDisable(false);
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();

                }
            }
        });
    }


    public void search(ActionEvent actionEvent){
        if(tf_origin.getText()==null || tf_destination.getText()==null  || dp_departure.getValue()==null || dp_arrival.getValue() == null ) {
            alert("אופס! אחד או יותר משדות החיפוש ריקים", Alert.AlertType.ERROR);
            return;
        } else {
            int numberOfTickets = 0;
            //valid number (not empty)
            if (!tf_numOfTickets.getText().equals("") && StringUtils.isNumeric(tf_numOfTickets.getText()))
                numberOfTickets = Integer.valueOf(tf_numOfTickets.getText());
                //invalid number (not empty)
            if (!tf_numOfTickets.getText().equals("") && !StringUtils.isNumeric(tf_numOfTickets.getText())) {
                alert("אופס! הערך שהוזן במספר טיסות איננו תקין.", Alert.AlertType.ERROR);
                return;
            }
            //empty, make default 1
            else if (tf_numOfTickets.getText().equals("") || StringUtils.isNumeric(tf_numOfTickets.getText())) {
                //tf_numOfTickets.setText("1");
                numberOfTickets = 1;
                String dateDepart = controller.changeToRightDateFormat(dp_departure.getValue().toString());
                String dateArriv = controller.changeToRightDateFormat(dp_arrival.getValue().toString());
//                String dateDepart = dp_departure.getValue().toString();
//                String dateArriv = dp_arrival.getValue().toString();
                Flight flight= new Flight(tf_origin.getText(), tf_destination.getText(), dateDepart, dateArriv, numberOfTickets);
                if(!controller.setMatchesFlights(flight))
                    newStage("DisplaySearchedFlights.fxml", "", displaySearchedFlights, 635, 525, controller);
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

    public void displayAvailableFlights(){
        //check this is the right order HERE
        ArrayList<Button> buttonlist = new ArrayList<Button>(); //our Collection to hold newly created Buttons
        String buttonTitle = "רכוש חופשה"; //extract button text, adapt the String to the columnname that you are interested in
        ArrayList<Label> detailsLabels = new ArrayList<Label>();
        //change this to flight details
        for (Flight flight : availableFlights) {
            Button btn = new Button(buttonTitle);
            btn.setId(String.valueOf( "buy,"+flight.getFlightId() + "," + flight.getSeller()));
            btn.setFont(new Font("Calibri Light", 15));
            btn.setPrefHeight(38.0);
            btn.setOnAction(this);
            // btn.setTextFill();
            buttonlist.add(btn);
            String details =  "מ"+ flight.getOrigin() + " אל "+ flight.getDestination() +" בתאריכים: " +flight.getDateOfDeparture() + "-" + flight.getDateOfArrival() + '\n' +"שדה תעופה ביעד:"+flight.getDestinationAirport() + " מס' כרטיסים: " + flight.getNumOfTickets() + " " +  " כבודה:"+ flight.getBaggage() + " סוג כרטיס: " + flight.getTicketsType() + "\n" + " מחיר: "+ flight.getPrice();
            Label lbl = new Label();
            lbl.setText(details);
            lbl.setFont(new Font("Calibri Light", 15));
            lbl.setPrefSize(500.0,38.0);
            lbl.setTextAlignment(TextAlignment.RIGHT);
            detailsLabels.add(lbl);
        }
        VB_buttons.getChildren().clear();
        VB_buttons.getChildren().addAll(buttonlist);

        VB_labels.getChildren().clear();
        VB_labels.getChildren().addAll(detailsLabels);
    }

    @Override
    public void handle(ActionEvent event) {
        if (controller.getUserName() == null) {
            alert("על מנת לרכוש חופשה עליך להתחבר למערכת תחילה", Alert.AlertType.ERROR);
            stage.close();
        } else {

            Button button = (Button) event.getSource();
            if(button.getId().contains("buy")) {
                String[] split = button.getId().split(",");
                String actionType = split[0];
                String vacationID = split[1];
                String seller = split[2];
                PendingFlight PF = new PendingFlight(Integer.valueOf(vacationID), seller, controller.getUserName());
                controller.insertPendingFlight(PF);
                controller.deleteAvailableFlight(Integer.valueOf(vacationID));
                alert("בקשתך נשלחה למוכר", Alert.AlertType.CONFIRMATION);
                stage.close();
            }
        }
    }
}
