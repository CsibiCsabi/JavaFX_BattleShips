package com.example.torpedo_firsttry;

import java.util.ArrayList;

public class Computer {
    Tabla tabla;
    boolean shipFound;
    boolean shipSank;
    Koordinata where;
    ArrayList<Koordinata> coordinates;
    boolean orientOfFoundShip;
    boolean foundOrient;

    public Computer(Tabla tabla) {
        this.tabla = tabla;
        shipFound = false;
        shipSank = true;
    }

    public void foundShip(Koordinata koor) {
        System.out.println("Talált");
        if (!shipFound){
            System.out.println("első találat");
            shipFound = true;
            shipSank = false;
            where = koor;
            coordinates = new ArrayList<>();
            coordinates.add(koor);
        } else {
            System.out.println("nem első találat");
            coordinates.add(koor);
            if (coordinates.get(0).sor == coordinates.get(1).sor-1 || coordinates.get(0).sor == coordinates.get(1).sor+1){
                foundOrient = true;
                orientOfFoundShip = true; // vertical
            } else {
                foundOrient = true;
                orientOfFoundShip = false;
            }
            System.out.println(orientOfFoundShip ? "vertical" :  "horizontal");
        }
    }

    public void sankShip() {
        shipSank = true;
        shipFound = false;
        foundOrient = false;
        orientOfFoundShip = false;

    }

    public Koordinata getCoords() {
        System.out.println("\nLő a gép... ");
        Koordinata koor;
        if (shipFound && !shipSank) {
            System.out.println("Talált egy hajót");
            if (foundOrient) {
                System.out.println("megvan az orient");
                if (orientOfFoundShip){ //vertical
                    System.out.println("vertical");
                    int minY = coordinates.get(0).sor;
                    int maxY = coordinates.get(0).sor;
                    for (Koordinata coordinate : coordinates) {
                        if (coordinate.sor < minY){
                            minY = coordinate.sor;
                        } else if (coordinate.sor > maxY){
                            maxY = coordinate.sor;
                        }
                    }

                    if (maxY + 1 < tabla.size && tabla.eloTabla.get(maxY+1).get(where.oszlop) == '0') {
                        return new Koordinata(maxY+1, where.oszlop);
                    } else if (minY - 1 >= 0 && tabla.eloTabla.get(minY-1).get(where.oszlop) == '0'){
                        return new Koordinata(minY-1, where.oszlop);
                    } else {
                        System.out.println("valami baj van");
                        return getRandomKoordinata();
                    }
                } else {
                    //vertical
                    int minX = coordinates.get(0).oszlop;
                    int maxX = coordinates.get(0).oszlop;
                    for (Koordinata coordinate : coordinates) {
                        if (coordinate.oszlop < minX){
                            minX = coordinate.oszlop;
                        } else if (coordinate.oszlop > maxX){
                            maxX = coordinate.oszlop;
                        }
                    }
                    if (maxX + 1 < tabla.size && tabla.eloTabla.get(where.sor).get(maxX+1) == '0') {
                        return new Koordinata(where.sor, maxX + 1);
                    } else if (minX - 1 >= 0 && tabla.eloTabla.get(where.sor).get(minX-1) == '0'){
                        return new Koordinata(where.sor, minX - 1);
                    } else {
                        System.out.println("valami baj van");
                        return getRandomKoordinata();
                    }
                }
            } else {
                do {
                    boolean orient = Math.random() < 0.5; // if true, vertical
                    boolean muvelet = Math.random() < 0.5; // if true, +
                    if (orient) { //vertical
                        koor = new Koordinata(where.sor + (muvelet? 1 : -1), where.oszlop);
                    } else {
                        koor = new Koordinata(where.sor, where.oszlop + (muvelet? 1 : -1));
                    }
                } while (koor.sor >= tabla.size ||koor.sor < 0 || koor.oszlop >= tabla.size || koor.oszlop < 0 || tabla.eloTabla.get(koor.sor).get(koor.oszlop) == 'X' || tabla.eloTabla.get(koor.sor).get(koor.oszlop) == '!');
                return koor;
            }
        } else {
            return getRandomKoordinata();
        }
    }
    public Koordinata getRandomKoordinata() {
        Koordinata koor;
        int sor, oszlop;
        do {
            sor = (int) (Math.random() * tabla.size);
            oszlop = (int) (Math.random() * tabla.size);
        } while (tabla.eloTabla.get(sor).get(oszlop) == 'X' || tabla.eloTabla.get(sor).get(oszlop) == '!');
        koor = new Koordinata(sor, oszlop);
        tabla.tamadas(koor);
        return koor;
    }


}
