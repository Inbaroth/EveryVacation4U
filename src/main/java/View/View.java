/*
package View;

import Controller.Controller;
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
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class View  implements Observer {

    private Controller controller;

    private Insert insertWindow;
    private Update updateWindow;
    private Read readWindow;
    private InsertUserName insertUserNameWindow;
    private SignIn signInWindow;

    private Stage primaryStage;

    public javafx.scene.control.Button bth_createUser;
    public javafx.scene.control.Button btn_read;
    public javafx.scene.control.Button btn_update;
    public javafx.scene.control.Button btn_delete;
    public javafx.scene.control.Button btn_signIn;


    public void setController(Controller controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o == controller) {
            if (btn_delete.isDisable() && (boolean) arg) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("כן");
                ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("לא");
                alert.setContentText("האם אתה בטוח שברצונך למחוק את המשתמש?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    controller.deleteUser(controller.getUserName());
                    // Close program
                }
                btn_delete.setDisable(false);
            }
            if (btn_delete.isDisable() && !(boolean) arg){
                alert("הסיסמאות אינן תואמות", Alert.AlertType.ERROR);
                newStage("SignIn.fxml", "כניסת משתמש רשום", signInWindow, 432, 383 );
            }
            if (btn_update.isDisable() && (boolean) arg) {
                newStage("Update.fxml", "עדכון פרטים אישיים", updateWindow, 600, 400);
                btn_update.setDisable(false);
            }
            if (btn_update.isDisable() && !(boolean) arg){
                alert("הסיסמאות אינן תואמות", Alert.AlertType.ERROR);
                newStage("SignIn.fxml", "כניסת משתמש רשום", signInWindow, 432, 383 );

            }

        }

    }

    public void create(ActionEvent actionEvent) {
        newStage("insertNewUser.fxml", "", insertWindow, 600, 466);
    }



    public void read(ActionEvent actionEvent){
        newStage("read.fxml", "חיפוש משתמש", readWindow, 364, 284);
    }

    public void update(ActionEvent actionEvent){
        btn_update.setDisable(true);
        signIn(actionEvent);

    }

    public void deleteUser(ActionEvent actionEvent){
        btn_delete.setDisable(true);
        signIn(actionEvent);
    }

    public void signIn(ActionEvent actionEvent){
        newStage("SignIn.fxml", "כניסת משתמש רשום", signInWindow, 432, 383 );
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
                    btn_update.setDisable(false);
                    btn_delete.setDisable(false);
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();

                }
            }
        });
    }

    */
/**
     * @param actionEvent
     *//*

    public void exit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("יציאה");
        alert.setContentText("אתה בטוח שברצונך לעזוב?");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("כן");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("חזור");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            // Close program
            primaryStage.close();
        }
        alert.close();

    }

    protected void alert(String messageText, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(messageText);
        alert.showAndWait();
        alert.close();
    }

    //create a new stage
    protected void newStage(String fxmlName,String title, View windowName, int width, int height){
        */
/*FXMLLoader fxmlLoader = new
                FXMLLoader(getClass().getResource(fxmlName));*//*

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("/" + fxmlName).openStream());
            //root = (Parent) fxmlLoader.load(getClass().getResource(fxmlName).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        //set what you want on your scene
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

        if (windowName instanceof Update){
            String userDetails = controller.read(controller.getUserName(),false);
            updateWindow = (Update) windowName;
            updateWindow.setUserDetails(userDetails);
        }
    }
}
*/
