package calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/calculator/view/calculator.fxml"));

        Scene scene = new Scene(root, 550, 200);
        primaryStage.setTitle("Calculator - JavaFX MVC");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.toFront();
        primaryStage.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
