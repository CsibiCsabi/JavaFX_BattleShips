package com.example.torpedo_firsttry;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args); // JavaFX indítása
    }

    @Override
    public void start(Stage stage){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root);
            //Scene scene2 = new Scene(myGrid);
            stage.setScene(scene);
            stage.setTitle("Battle Ships JavaFX");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}