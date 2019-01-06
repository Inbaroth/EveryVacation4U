package View;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public interface MyFlights  {

     void setController(Controller controller, Stage primaryStage);

     void displayFlights();

     void handle(ActionEvent event);

}
