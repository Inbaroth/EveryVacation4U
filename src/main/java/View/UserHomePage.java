package View;

import Controller.Controller;
import Model.Flight;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import java.time.LocalDate;
import java.util.Optional;

public class UserHomePage extends HomePage {

    private Update updateWindow;
    private HomePage homePage;
    private InsertFlight insertFlight;
    private DisplayVacations displayVacations;
    private Read read;
    private Controller controller;
    private Stage stage;


    public javafx.scene.control.Label lbl_user;
    public javafx.scene.control.TextField tf_origin;
    public javafx.scene.control.TextField tf_destination;
    public javafx.scene.control.DatePicker dp_arrival;
    public javafx.scene.control.DatePicker dp_departure;
    public javafx.scene.control.TextField tf_numOfTickets;

    //use this for board of flights available for swap
    public VBox VB_buttons;
    public VBox VB_labels;
    private ArrayList<Button> buttonsList;
    private ArrayList<Label> labelList;

    public void setController(Controller controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
        lbl_user.setText("שלום " + controller.getUserName());
        super.setImage();
        tooltip.setText("\nהכנס מיקום בפורמט:\n"+"עיר,מדינה"+"\n");
        tf_origin.setTooltip(tooltip);
        tf_destination.setTooltip(tooltip);


    }


    public void update(ActionEvent actionEvent) {
        newStage("Update.fxml", "עדכון פרטים אישיים", updateWindow, 615, 490,controller);
    }

    public void delete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("כן");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("לא");
        alert.setContentText("האם אתה בטוח שברצונך למחוק את שם המשתמש מהמערכת?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            // Close program
                controller.deleteUser(controller.getUserName());
                alert("החשבון נמחק בהצלחה", Alert.AlertType.INFORMATION);
                stage.close();
                newStage("HomePage.fxml", "כניסת משתמש רשום", homePage, 940, 581,controller);
        } else {
            // ... user chose CANCEL or closed the dialog
            //windowEvent.consume();

        }
    }

    public void search(ActionEvent actionEvent) {
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
            if(dp_departure.getValue().compareTo(dp_arrival.getValue()) > 1 || dp_departure.getValue().compareTo(LocalDate.now()) < 1) {
                alert("אנא הזן טווח תאריכים חוקי", Alert.AlertType.ERROR);
                return;
            }
            //empty, make default 1
            else if (tf_numOfTickets.getText().equals("") || StringUtils.isNumeric(tf_numOfTickets.getText())) {
                //tf_numOfTickets.setText("1");
                numberOfTickets = 1;
                String dateDepart = controller.changeToRightDateFormat(dp_departure.getValue().toString());
                String dateArriv = controller.changeToRightDateFormat(dp_arrival.getValue().toString());
                System.out.println(dateDepart);
                System.out.println(dateArriv);
//                String dateDepart = dp_departure.getValue().toString();
//                String dateArriv = dp_arrival.getValue().toString();
                Flight flight = new Flight(tf_origin.getText(), tf_destination.getText(), dateDepart, dateArriv, numberOfTickets);
                if(!controller.setMatchesFlights(flight))
                    newStage("DisplayVacations.fxml", "", displayVacations, 635, 525, controller);
                else
                    alert("מתנצלים אך אין חופשה שתואמת את החיפוש שלך", Alert.AlertType.INFORMATION);
                //tf_numOfTickets.clear();
            }
        }


    }

    public void sellTickets(ActionEvent actionEvent) {

        newStage("InsertVacation.fxml", "יצירת חופשה", insertFlight, 760, 430,controller);


    }

    public void logOut(ActionEvent actionEvent){
        stage.close();
        controller.setUserName(null);
        newStage("HomePage.fxml", "כניסת משתמש רשום", homePage, 940, 581,controller);
    }

    public void searchUser(){
        newStage("read.fxml", "חיפוש משתמש",read,364, 284, controller);
    }


}
