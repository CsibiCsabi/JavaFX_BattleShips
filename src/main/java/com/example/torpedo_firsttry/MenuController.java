package com.example.torpedo_firsttry;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.IOException;

public class MenuController {
    // string adatok...
    @FXML private Spinner<Integer> hajo1;
    @FXML private Spinner<Integer> hajo2;
    @FXML private Spinner<Integer> hajo3;
    @FXML private Spinner<Integer> hajo4;
    @FXML private ComboBox<Integer> sizeCombo;
    @FXML private RadioButton singlePlayer;




    private Parent root;
    private Stage stage;
    private Scene scene;

    public void initialize() {
        // Lehetséges pályaméretek
        singlePlayer.setSelected(true);
        sizeCombo.getItems().addAll(5, 7, 10, 12);
        sizeCombo.setValue(7); // alapértelmezett
        hajo1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,6,0));
        hajo2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,6,3));
        hajo3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,6,2));
        hajo4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,6,1));
    }

    public void startGame(ActionEvent event) throws IOException {
        //valasztott value-k
        int meret = sizeCombo.getValue();
        int egyes = hajo1.getValue();
        int kettes = hajo2.getValue();
        int harmas = hajo3.getValue();
        int negyes = hajo4.getValue();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Torpedo.fxml"));
        root = loader.load();
        TorpedoController torpedoController = loader.getController();

        torpedoController.setMode(singlePlayer.isSelected());

        //vizsgalat, h jok e az adatok
        int availableSpace = (int)Math.pow(meret, 2);
        int takenSpace = 0;
        takenSpace += egyes * 3;
        takenSpace += kettes * 4;
        takenSpace += harmas * 6;
        takenSpace += negyes * 9;
        if(takenSpace > availableSpace * 0.9){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Too many Ships!");
            alert.setContentText("This way, you won't be able to put down all of your ships without restrictions!.");
            alert.show();
            return;
        } else {
            // adatok betoltese
            torpedoController.setSize(meret);
            torpedoController.setShips(egyes, kettes, harmas, negyes);
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }

    }
}
