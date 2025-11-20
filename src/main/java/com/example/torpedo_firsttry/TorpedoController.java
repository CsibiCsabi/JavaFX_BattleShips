package com.example.torpedo_firsttry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class TorpedoController {

    Computer computer;
    @FXML private GridPane myGrid;
    @FXML private GridPane myGrid2;
    @FXML private Label endTitle;

    int MERET;
    int singleShipCount, doubleShipCount, tripleShipCount, quadShipCount;
    Button[][] mezokComputer, mezokPlayer;
    Tabla t1, t2;
    private boolean initialized = false;
    private boolean sizeSet = false;
    private boolean shipsSet = false;
    private boolean p1Won;
    private boolean singlePlayer;
    private boolean p1Turn;

    public void setSize(int size){
        MERET = size;
        System.out.println("MERET: " + MERET);
        mezokComputer = new Button[MERET][MERET];
        mezokPlayer = new Button[MERET][MERET];
        sizeSet = true;
        tryInitGame();
    }

    public void setMode(boolean singlePlayer){
        this.singlePlayer = singlePlayer;
    }

    public void endGame(){
        for(int i = 0; i < MERET; i++){
            for(int j = 0; j < MERET; j++){
                mezokComputer[i][j].setDisable(true);
                mezokPlayer[i][j].setDisable(true);
            }
        }
        int sunkenShips = 0;
        for (Ship ship : t1.ships) {
            if (ship.elsullyedve){
                sunkenShips++;
            }
        }
        if (sunkenShips == t1.ships.size()){
            p1Won = true;
        } else {
            p1Won = false;
        }
        Color col = Color.rgb(5,30,40, 0.88);
        CornerRadii corn = new CornerRadii(10);
        Background background = new Background(new BackgroundFill(col, corn, Insets.EMPTY));
        endTitle.setBackground(background);
        endTitle.setAlignment(Pos.CENTER);
        if (singlePlayer){
            endTitle.setText(p1Won ? "You've Won!" : "You've Lost!");
        } else {
            endTitle.setText(p1Won ? "Player 1 won!" : "Player 2 won!");
        }

        endTitle.setVisible(true);
    }


    public void visible(){
        endGame();
    }

    public void setShips(int one, int two, int three, int four){
        singleShipCount = one;
        doubleShipCount = two;
        tripleShipCount = three;
        quadShipCount = four;

        System.out.println("setting ships");
        shipsSet = true;
        tryInitGame();
    }

    public void back(ActionEvent event) throws IOException {
        System.out.println("should go back...");
        Parent root;

        root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Stage stage;
        Scene scene;
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void tryInitGame(){
        System.out.println("trying...");
        if (MERET > 0 && singleShipCount >= 0 && doubleShipCount >= 0 && tripleShipCount >= 0 && quadShipCount >= 0 && !initialized && shipsSet && sizeSet){
            letsInitGame1();
        }
    }

    public void letsInitGame1(){

        t1 = new Tabla(MERET, singleShipCount, doubleShipCount, tripleShipCount, quadShipCount);
        for (int sor = 0; sor < MERET; sor++) {
            for (int oszlop = 0; oszlop < MERET; oszlop++) {
                Button cella = new Button();
                cella.setPrefSize(40, 40);
                int x = sor;
                int y = oszlop;
                mezokComputer[x][y] = cella;
                cella.setOnAction(e -> {
                    boolean talalt = t1.tamadas(new Koordinata(x, y));

                    if (talalt) {
                        cella.setStyle("-fx-background-color: #09a7c8;");
                    } else {
                        cella.setStyle("-fx-background-color: gray;");
                    }
                    int sunkedShips = 0;
                    for (Ship ship : t1.ships) {

                        if (ship.elsullyedve) {
                            sunkedShips++;
                            for (Koordinata koordinata : ship.getKoordinatak()) {
                                mezokComputer[koordinata.sor][koordinata.oszlop].setStyle("-fx-background-color: #730000;");
                            }
                        }
                    }
                    if (sunkedShips == t1.ships.size())
                        endGame();

                    cella.setDisable(true);
                    if (singlePlayer)
                        computerTamad();
                    else {
                        // making it, so it's the eother player's turn
                        for (Button[] buttons : mezokComputer) {
                            for (Button button : buttons) {
                                button.setDisable(true);
                            }
                        }
                        for (int i = 0; i < MERET; i++) {
                            for (int j = 0; j < MERET; j++) {
                                mezokPlayer[i][j].setDisable(!(t2.eloTabla.get(i).get(j) == '0'));
                            }
                        }
                    }


                });

                myGrid.add(cella, oszlop, sor);
            }
        }
        if (singlePlayer)
            letsInitPlayer();
        else
            letsInitGame2();
    }

    public void letsInitGame2(){

        t2 = new Tabla(MERET, singleShipCount, doubleShipCount, tripleShipCount, quadShipCount);
        t2.kiirHattabla();
        t2.shipekKiiratasa();
        for (int sor = 0; sor < MERET; sor++) {
            for (int oszlop = 0; oszlop < MERET; oszlop++) {
                Button cella = new Button();
                cella.setPrefSize(40, 40);
                int x = sor;
                int y = oszlop;
                mezokPlayer[x][y] = cella;
                cella.setDisable(true);
                cella.setOnAction(e -> {
                    boolean talalt = t2.tamadas(new Koordinata(x, y));

                    if (talalt) {
                        cella.setStyle("-fx-background-color: #09a7c8;");
                    } else {
                        cella.setStyle("-fx-background-color: gray;");
                    }
                    int sunkedShips = 0;
                    for (Ship ship : t2.ships) {

                        if (ship.elsullyedve) {
                            sunkedShips++;
                            for (Koordinata koordinata : ship.getKoordinatak()) {
                                mezokPlayer[koordinata.sor][koordinata.oszlop].setStyle("-fx-background-color: #730000;");
                            }
                        }
                    }
                    if (sunkedShips == t2.ships.size())
                        endGame();

                    cella.setDisable(true);
                    // making it, so it's the eother player's turn
                    for (Button[] buttons : mezokPlayer) {
                        for (Button button : buttons) {
                            button.setDisable(true);
                        }
                    }
                    for (int i = 0; i < MERET; i++) {
                        for (int j = 0; j < MERET; j++) {
                            mezokComputer[i][j].setDisable(!(t1.eloTabla.get(i).get(j) == '0'));
                        }
                    }


                });

                myGrid2.add(cella, oszlop, sor);
            }
        }
    }

    public void computerTamad(){
        Koordinata koor = computer.getCoords();
        boolean talalt = t2.tamadas(koor);
        if (talalt) {
            mezokPlayer[koor.sor][koor.oszlop].setStyle("-fx-background-color: #c80969;");
            computer.foundShip(koor);
        } else {
            mezokPlayer[koor.sor][koor.oszlop].setStyle("-fx-background-color: #353031;");
        }
        int sunkedShips = 0;
        for (Ship ship : t2.ships) {

            if (ship.elsullyedve)
                sunkedShips++;

            if (ship.elsullyedve && !ship.szinezve) {
                System.out.println("beszinezzuk");
                ship.szinezve = true;
                computer.sankShip();
                for (Koordinata koordinata : ship.getKoordinatak()) {
                    mezokPlayer[koordinata.sor][koordinata.oszlop].setStyle("-fx-background-color: #730000;");
                }
            }
        }
        if (sunkedShips == t2.ships.size())
            endGame();
    }

    public void letsInitPlayer(){
        System.out.println("making t2...");
        t2 = new Tabla(MERET, singleShipCount, doubleShipCount, tripleShipCount, quadShipCount);
        t2.kiirHattabla();
        t2.shipekKiiratasa();
        for (int sor = 0; sor < MERET; sor++) {
            for (int oszlop = 0; oszlop < MERET; oszlop++) {
                Button cella = new Button();
                cella.setPrefSize(40, 40);
                int x = sor;
                int y = oszlop;
                mezokPlayer[x][y] = cella;
                cella.setDisable(true);

                if (t2.tabla.get(sor).get(oszlop) == 1)
                    cella.setStyle("-fx-background-color: #41ff24;");

                myGrid2.add(cella, oszlop, sor);
            }
        }
        computer = new Computer(t2);
    }
}
