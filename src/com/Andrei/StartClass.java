package com.Andrei;

import com.BusinessLayer.Restaurant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class StartClass extends Application {

    @Override
    public void start(Stage primaryStage) {
        launchGUI("/com/PresentationLayer/administratorGUI.fxml", "Administrator", 50,10);
        launchGUI("/com/PresentationLayer/waiterGUI.fxml", "Waiter", 860, 10);
        launchGUI("/com/PresentationLayer/chefGUI.fxml", "Chef", 700, 850);
    }

    private void launchGUI(String fxmlFile, String stageTitle, double xPosition, double yPosition) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setX(xPosition);
            stage.setY(yPosition);
            stage.setTitle(stageTitle);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        Restaurant.getRestaurant().setInputFileName(args[0]);
        launch(args);
    }
}
