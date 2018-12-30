package View;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;


public class InsertVacation extends HomePage {

    private Controller controller;
    private Stage stage;
    public final Tooltip tooltip = new Tooltip();

    public javafx.scene.control.TextField tf_origin;
    public javafx.scene.control.TextField tf_destination;
    public javafx.scene.control.DatePicker dp_departure;
    public javafx.scene.control.DatePicker dp_arrival;
    public javafx.scene.control.TextField tf_numOfTickets;
    public javafx.scene.control.TextField tf_destinationAirport;
    public javafx.scene.control.TextField tf_AirlineCompany;
    public javafx.scene.control.TextField tf_originalPrice;
    public javafx.scene.control.TextField tf_requestedPrice;
    public javafx.scene.control.ComboBox<String> cb_baggage;
    public javafx.scene.control.ComboBox<String> cb_ticketsType;
    public javafx.scene.control.ComboBox<String> cb_vacationStyle;

    public void setController(Controller controller, Stage primaryStage) {
        this.controller = controller;
        this.stage = primaryStage;
        tooltip.setText("\nהכנס מיקום בפורמט:\n"+"עיר,מדינה"+"\n");
        tf_origin.setTooltip(tooltip);
        tf_destination.setTooltip(tooltip);

    }


    public void createVacation(ActionEvent actionEvent){
        if(tf_origin.getText()==null || tf_destination.getText()==null  || dp_departure.getValue()==null || dp_arrival.getValue() == null || tf_numOfTickets.getText()==null|| tf_destinationAirport.getText()==null || tf_AirlineCompany.getText()==null || tf_originalPrice.getText()==null ||tf_requestedPrice.getText()==null|| cb_baggage.getValue()==null ||  cb_ticketsType.getValue()==null || cb_vacationStyle.getValue()==null ){
            alert( "אחד השדות או יותר ריקים", Alert.AlertType.ERROR);
        }
        else{
            //invalid number (not empty)
            if((tf_numOfTickets.getText()!=null && !StringUtils.isNumeric(tf_numOfTickets.getText()))||(tf_originalPrice.getText()!=null && !StringUtils.isNumeric(tf_originalPrice.getText()))||(tf_requestedPrice.getText()!=null && !StringUtils.isNumeric(tf_requestedPrice.getText()))) {
                alert("אופס! הערך שהוזן בשדה מספרי איננו תקין.", Alert.AlertType.ERROR);
                return;
            }
            String origin = tf_origin.getText();
            String destination = tf_destination.getText();
            int price = Integer.valueOf(tf_requestedPrice.getText());
            String destinationAirport = tf_destinationAirport.getText();
            String dateDepart = controller.changeToRightDateFormat(dp_departure.getValue().toString());
            String dateArriv = controller.changeToRightDateFormat(dp_arrival.getValue().toString());
//            String dateDepart = dp_departure.getValue().toString();
//            String dateArriv = dp_arrival.getValue().toString();
            String airlineCompany = tf_AirlineCompany.getText();
            int numberOfTicktes = Integer.valueOf(tf_numOfTickets.getText());
            //String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice
            String baggage = cb_baggage.getValue();
            String ticketsType = cb_ticketsType.getValue();
            String vacationStyle = cb_vacationStyle.getValue();
            String seller = controller.getUserName();
            int originalPrice = Integer.valueOf(tf_originalPrice.getText());
            controller.insertVacation(origin,destination,price,destinationAirport,dateDepart,dateArriv,airlineCompany,numberOfTicktes,baggage,ticketsType,vacationStyle,seller,originalPrice);
            alert("חופשה נוספה בהצלחה", Alert.AlertType.INFORMATION);
            stage.close();
            int vacationID = controller.getVacationID();

        }






    }

}
