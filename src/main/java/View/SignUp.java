package View;

import Controller.Controller;
import Model.RegisteredUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class Insert extends HomePage implements Observer {

    private Controller controller;
    private Stage stage;
    private UserHomePage userHomePage;

    //<editor-fold desc="Text Fields">
    public javafx.scene.control.TextField txtfld_userName;
    public javafx.scene.control.PasswordField txtfld_password;
    public javafx.scene.control.PasswordField txtfld_confirmPassword;
    public javafx.scene.control.TextField txtfld_firstName;
    public javafx.scene.control.TextField txtfld_lastName;
    public javafx.scene.control.TextField txtfld_Address;
    public javafx.scene.control.TextField txtfld_email;
    public javafx.scene.control.ComboBox combo_box_day;
    public javafx.scene.control.ComboBox combo_box_month;
    public javafx.scene.control.ComboBox combo_box_year;
    //</editor-fold>


    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
    }

    public void submit(ActionEvent actionEvent) {
        String userName = txtfld_userName.getText().trim();
        String email = txtfld_email.getText().trim();
        String password = txtfld_password.getText().trim();
        String confirmPassword = txtfld_confirmPassword.getText().trim();
        String firstName = txtfld_firstName.getText().trim();
        String lastName = txtfld_lastName.getText().trim();
        String address = txtfld_Address.getText().trim();

        if (!validation()){
            alert("שדה אחד או יותר ריקים", Alert.AlertType.ERROR);
        }
        else{
            RegisteredUser registeredUser = new RegisteredUser(userName,password,firstName,lastName,getBirthday(),address,email);
            String ans = controller.insertUser(registeredUser,confirmPassword);
            if (!ans.equals("התחברת בהצלחה"))
                alert(ans, Alert.AlertType.ERROR);
            else {
                alert("התחברת בהצלחה", Alert.AlertType.INFORMATION);
                stage.close();
                controller.setUserName(userName);
                newStage("UserHomePage.fxml", "כניסת משתמש רשום", userHomePage, 944, 650,controller);
                HomePage.stage.close();

            }
        }

    }

    /**
     * This method checks if the user filled all the Text Fields
     * @return true if the user filled all the Text Fields, otherwise return false
     */
    private boolean validation() {
        if (txtfld_userName.getText() == null || txtfld_userName.getText().trim().isEmpty() || txtfld_userName.getText().trim().equals(""))
            return false;
        if (txtfld_password.getText() == null || txtfld_password.getText().trim().isEmpty() || txtfld_password.getText().trim().equals(""))
            return false;
        if (txtfld_confirmPassword.getText() == null || txtfld_confirmPassword.getText().trim().isEmpty() || txtfld_confirmPassword.getText().trim().equals(""))
            return false;
        if (txtfld_firstName.getText() == null || txtfld_firstName.getText().trim().isEmpty() || txtfld_firstName.getText().trim().equals(""))
            return false;
        if (txtfld_lastName.getText() == null || txtfld_lastName.getText().trim().isEmpty() ||  txtfld_lastName.getText().trim().equals(""))
            return false;
        if (txtfld_Address.getText() == null || txtfld_Address.getText().trim().isEmpty() || txtfld_Address.getText().trim().equals(""))
            return false;
        if (txtfld_email.getText() == null || txtfld_email.getText().trim().isEmpty() || txtfld_email.getText().trim().equals(""))
            return false;
        return true;
    }

    /**
     * get the user birthday
     * @return the value of the combo_box_day, combo_box_month, combo_box_year in the format: DD/MM/YYYY
     */
    private String getBirthday (){
        String day = (String) combo_box_day.getValue();
        String month = (String) combo_box_month.getValue();
        String year = (String) combo_box_year.getValue();
        return day  + "/" + month + "/" + year;
    }


    /**
     * if the user clicked on cancel, close the program
     * @param actionEvent
     */
    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }



    @Override
    public void update(Observable o, Object arg) {
        if (o == controller){
            stage.close();
        }
    }

    public Stage getStage() {
        return stage;
    }


}
