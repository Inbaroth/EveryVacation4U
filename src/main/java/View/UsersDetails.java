package View;

import Controller.Controller;
import Model.RegisteredUser;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class UsersDetails extends HomePage implements Observer {


    private Controller controller;
    private Stage stage;
    private RegisteredUser registeredUser;

    public javafx.scene.control.Label userName;
    public javafx.scene.control.Label firstName;
    public javafx.scene.control.Label lastName;
    public javafx.scene.control.Label day;
    public javafx.scene.control.Label month;
    public javafx.scene.control.Label year;
    public javafx.scene.control.Label address;
    public javafx.scene.control.Label email;



    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
    }


    @Override
    public void update(Observable o, Object arg) {
    }

    public void setUserDetails(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
        splitToFields();
    }

    private void splitToFields(){
        userName.setText(registeredUser.getUserName());
        firstName.setText(registeredUser.getFirstName());
        lastName.setText(registeredUser.getLastName());
        String [] date = registeredUser.getBirthday().split("/");
        day.setText(date[0]);
        month.setText(date[1]);
        year.setText(date[2]);
        address.setText(registeredUser.getAddress());
        email.setText(registeredUser.getEmail());


    }


    public void loadPicture(){

    }

    public void close(){
        stage.close();
    }


}
