package View;

import Controller.Controller;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class PendingToSwapMessages extends HomePage {

public ChoiceBox<Integer> flightsID;
public VBox VB_toggle;
public ToggleGroup group;
private Controller controller;
private Stage stage;

    @Override
    public void setController(Controller controller, Stage primaryStage) {
        super.setController(controller, primaryStage);
        setOffers();
    }


    public void setOffers(){}


    public void confirm(){}
}
