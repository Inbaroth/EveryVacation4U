package View;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MyFlightsForSwap  extends HomePage implements MyFlights, EventHandler<ActionEvent> {

    private Controller controller;
    private Stage stage;

    //use this to display the flights
    public VBox VB_buttons;
    public VBox VB_labels;
    private ArrayList<Button> buttonsList;
    private ArrayList<Label> labelList;

    public void setController(Controller controller, Stage primaryStage) {
        this.controller = controller;
        this.stage = primaryStage;

    }

    /**
     *
     *
     */
    public void displayFlights(){

    }


    @Override
    public void handle(ActionEvent event) {

    }



}
