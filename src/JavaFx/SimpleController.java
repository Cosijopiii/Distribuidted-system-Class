package JavaFx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class SimpleController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/JavaFx/GUI.fxml"));
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Simple JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText("Button was clicked!");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}