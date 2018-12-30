package View;

import Controller.Controller;
import Model.RegisteredUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Observer;
import java.util.Optional;

public class InsertUserName extends HomePage implements Observer {

    private Controller controller;
    private Stage stage;
    private Update updateWindow;
    private RegisteredUser registeredUser;

    public javafx.scene.control.TextField userName;




    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
    }


    public void search(ActionEvent actionEvent){

        String username = userName.getText();

        if (userName.getText() == null || userName.getText().trim().isEmpty()) {
            controller.alert("שדה אחד או יותר ריקים");
        }

        // readUsers the registeredUser name from the data base
        // if doesn't exist showing alert message
        registeredUser = controller.readUsers(username,false);
        if (registeredUser != null){
            //btn_create.setDisable(true);
            /*FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("Update.fxml"));*/
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root1 = null;
            try {
                //root1 = (Parent) fxmlLoader.load(getClass().getResource("Update.fxml").openStream());
                root1 = fxmlLoader.load(getClass().getResource("/" + "Update.fxml").openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            //set what you want on your scene
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("עדכון פרטים אישיים");
            Scene scene = new Scene(root1, 600, 400);
            stage.setScene(scene);
            //scene.getStylesheets().add(getClass().getResource("Welcome.css").toExternalForm());
            //stage.setScene(scene);
            stage.setResizable(false);
            SetStageCloseEvent(stage);
            stage.show();
            updateWindow = fxmlLoader.getController();
            //view.setResizeEvent(scene);
            updateWindow.setController(controller, stage);
            controller.addObserver(updateWindow);
            updateWindow.setUserDetails(registeredUser);
        }
    }

    /**
     *
     * @return the userDetails field
     */
    public RegisteredUser getUserDetails(){
        return registeredUser;

    }

    /**
     * This method close the window according to registeredUser request
     * @param primaryStage
     */
    protected void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                ((Button)alert.getDialogPane().lookupButton(ButtonType.OK)).setText("כן");
                ((Button)alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("חזור");
                alert.setContentText("האם אתה בטוח שברצונך לעזוב?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... registeredUser chose OK
                    // Close program
                } else {
                    // ... registeredUser chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }

}
