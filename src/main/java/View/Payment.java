package View;

import Controller.Controller;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Observer;

public class Payment  extends HomePage implements Observer {

    public javafx.scene.control.TextField txtfld_creditCardNumber;
    public javafx.scene.control.TextField txtfld_CSV;
    public javafx.scene.control.ComboBox combo_box_yearForCredit;
    public javafx.scene.control.ComboBox combo_box_monthForCredit;

    private Controller controller;
    private Stage stage;
    public static int creditCardNumber;
    public static String expirationDate;
    public static int CSV;


    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
    }

    public void setDetails(String details){
        String [] userDetailsSplited = details.split(",");
        txtfld_creditCardNumber.setText(userDetailsSplited[8]);
        txtfld_CSV.setText(userDetailsSplited[10]);
        String [] exp = userDetailsSplited[9].split("/");
        combo_box_monthForCredit.setValue(exp[0]);
        combo_box_yearForCredit.setValue(exp[1]);
    }

    /**
     *
     */
    public void confirm (){
        String newCreditCardNumber = txtfld_creditCardNumber.getText();
        String newExp = getExp();
        String newCSV = txtfld_CSV.getText();
        //creditCardNumber = Integer.valueOf(newCreditCardNumber);
        //expirationDate = newExp;
        //CSV = Integer.valueOf(newCSV);
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = LocalDateTime.now().toString().substring(0,localDateTime.toString().indexOf("T"));
        String time = LocalTime.now().toString();
        controller.deleteConfirmedVacation(ConfirmMessages.vacationID);
        controller.insertPurchasedVacation(ConfirmMessages.vacationID,date,time,controller.getUserName());
        alert("הרכישה בוצעה בהצלחה", Alert.AlertType.INFORMATION);
        stage.close();
    }

    private String getExp() {
        String newMonth = (String) combo_box_monthForCredit.getValue();
        String newYear = (String) combo_box_yearForCredit.getValue();
        return newMonth + "/" + newYear;
    }
}
