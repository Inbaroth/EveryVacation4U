package View;

import Controller.Controller;
import Model.Model;
import Model.UserModel;
import Model.FlightModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Model model;
    private UserModel userModel;
    private FlightModel flightModel;
    private HomePage view;


    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("start");
        model = new Model("EveryVacation4U");
        userModel = new UserModel("EveryVacation4U");
        flightModel = new FlightModel("EveryVacation4U");
        Controller controller = new Controller(userModel,flightModel);
        model.addObserver(controller);
        userModel.addObserver(controller);
        flightModel.addObserver(controller);
        //FXMLLoader fxmlLoader = FXMLLoader.load(getClass().getClassLoader().getResource("view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader();
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent root = fxmlLoader.load(getClass().getResource("/HomePage.fxml").openStream());
//        Parent root = (Parent) fxmlLoader.load(getClass().getResource("HomePage.fxml").openStream());
        primaryStage.setTitle("Welcome to Vacation4U");
        Scene scene = new Scene(root, 995, 716);
        primaryStage.setScene(scene);

        view = fxmlLoader.getController();
        view.setController(controller,primaryStage);
        controller.addObserver(view);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
