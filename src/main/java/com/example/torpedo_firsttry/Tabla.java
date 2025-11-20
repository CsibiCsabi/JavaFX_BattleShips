package com.example.torpedo_firsttry;

import java.util.ArrayList;


public class Tabla {
    int size;
    int singleShipCount;
    int doubleShipCount;
    int tripleShipCount;
    int quadShipCount;
    int maxAttempts;
    int createGameCounter;
    ArrayList<Ship> ships = new ArrayList<Ship>();

    ArrayList<ArrayList<Integer>> tabla = new ArrayList<>();
    ArrayList<ArrayList<Character>> eloTabla = new ArrayList<>();


    // self
    public Tabla(int size, int singleShipCount, int doubleShipCount, int tripleShipCount, int quadShipCount) {
        this.size = size;
        this.singleShipCount = singleShipCount;
        this.doubleShipCount = doubleShipCount;
        this.tripleShipCount = tripleShipCount;
        this.quadShipCount = quadShipCount;
        createGameCounter = 0;
        maxAttempts = size * size * 2;
        createGame();
    }

    private void createGame(){
        ships.clear();
        if (createGameCounter > 5) {
            System.out.println("Baj történt a generálás közben :(");

        } else {
            System.out.println("probalkozing...");
            createGameCounter++;
            createTabla();
            addQuadShips();
            addTripleShips();
            addDoubleShips();
            addSingleShips();
        }
    }

    // tabla letrehozas
    private void createTabla() {
        tabla = new ArrayList<>();
        eloTabla = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> sor = new ArrayList<>();
            ArrayList<Character> sor2 = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                sor.add(0);
            }
            for (int j = 0; j < size; j++) {
                sor2.add('0');
            }
            this.tabla.add(sor);
            this.eloTabla.add(sor2);
        }
    }

    public boolean checkSurroundings(Koordinata koordinata) { // true if empty
        // self check
        if (tabla.get(koordinata.sor).get(koordinata.oszlop) == 1) {
            return false;
        }
        // oldaléles szomszédok check
        int osszeg = 0;
        osszeg += tabla.get(koordinata.sor).get(koordinata.oszlop);
        osszeg += koordinata.sor == 0 ? 0 : (tabla.get(koordinata.sor - 1).get(koordinata.oszlop));
        osszeg += koordinata.sor == (tabla.size() - 1) ? 0 : (tabla.get(koordinata.sor + 1).get(koordinata.oszlop));
        osszeg += koordinata.oszlop == 0 ? 0 : (tabla.get(koordinata.sor).get(koordinata.oszlop - 1));
        osszeg += koordinata.oszlop == (tabla.size() - 1) ? 0
                : (tabla.get(koordinata.sor).get(koordinata.oszlop + 1));

        System.out.println("Az osszeg: " + osszeg);
        if (osszeg > 0) {
            System.out.println(" ez szar");
            return false;
        } else {
            System.out.println(" ez jo");
            return true;
        }
    }

    public boolean checkSurroundings(Koordinata[] helyek) { // true if empty
        for (Koordinata koordinata : helyek) {
            // oldaléles szomszédok check
            int osszeg = 0;// tabla.get(koordinata.sor).get(koordinata.oszlop) + koordinata.sor == 0 ? 0 :
            osszeg += tabla.get(koordinata.sor).get(koordinata.oszlop);
            osszeg += koordinata.sor == 0 ? 0 : (tabla.get(koordinata.sor - 1).get(koordinata.oszlop));
            osszeg += koordinata.sor == (tabla.size() - 1) ? 0 : (tabla.get(koordinata.sor + 1).get(koordinata.oszlop));
            osszeg += koordinata.oszlop == 0 ? 0 : (tabla.get(koordinata.sor).get(koordinata.oszlop - 1));
            osszeg += koordinata.oszlop == (tabla.size() - 1) ? 0
                    : (tabla.get(koordinata.sor).get(koordinata.oszlop + 1));

            // System.out.println("Az osszeg: " + osszeg);
            if (osszeg > 0) {
                // System.out.println(" ez szar");
                return false;
            } else {
                // System.out.println(" ez eddig jo");
            }
        }
        return true;
    }

    public void addSingleShips() {
        for (int i = 0; i < singleShipCount; i++) {
            int counter = 0;
            int oszlop = (int) (Math.random() * size);
            int sor = (int) (Math.random() * size);
            while (!checkSurroundings(new Koordinata(sor, oszlop))) {
                counter++;
                oszlop = (int) (Math.random() * size);
                sor = (int) (Math.random() * size);
                counter++;
                if (counter > maxAttempts) {
                    System.out.println("baj tortent... asd");
                    createGame();
                    return;
                }
            }
            tabla.get(sor).set(oszlop, 1);
            ships.add(new Ship(1, new Koordinata[] { new Koordinata(sor, oszlop) }, true));
        }
    }

    public void addDoubleShips() {
        for (int i = 0; i < doubleShipCount; i++) {
            int counter = 0;
            // irany
            boolean orientation = (Math.random()) > 0.5; // if true => vertical
            int oszlop, sor;
            if (orientation) {
                oszlop = (int) (Math.random() * size);
                sor = (int) (Math.random() * (size-1));
                while (!checkSurroundings(new Koordinata[]{new Koordinata(sor, oszlop), new Koordinata(sor+1, oszlop)})) {
                    oszlop = (int) (Math.random() * size);
                    sor = (int) (Math.random() * (size-1));
                    counter++;
                    if (counter > maxAttempts) {
                        System.out.println("baj tortent... asd");
                        createGame();
                        return;
                    }
                }
                tabla.get(sor).set(oszlop, 1);
                tabla.get(sor+1).set(oszlop, 1);
                ships.add(new Ship(2, new Koordinata[]{new Koordinata(sor, oszlop), new Koordinata(sor+1, oszlop)}, orientation));
            } else {
                oszlop = (int) (Math.random() * (size-1));
                sor = (int) (Math.random() * size);
                while (!checkSurroundings(new Koordinata[]{new Koordinata(sor, oszlop), new Koordinata(sor, oszlop+1)})) {
                    oszlop = (int) (Math.random() * (size-1));
                    sor = (int) (Math.random() * size);
                    counter++;
                    if (counter > maxAttempts) {
                        System.out.println("baj tortent... asd");
                        createGame();
                        return;
                    }
                }
                tabla.get(sor).set(oszlop, 1);
                tabla.get(sor).set(oszlop+1, 1);
                ships.add(new Ship(2, new Koordinata[]{new Koordinata(sor, oszlop), new Koordinata(sor, oszlop+1)}, orientation));
            }
            // mindig a bal/felso az alap blokk

        }
    }

    public void addTripleShips() {
        for (int i = 0; i < tripleShipCount; i++) {
            int counter = 0;
            boolean orientation = (Math.random()) > 0.5; // if true => vertical
            if (orientation) { // vertical
                // ilyenkor az alap a hajo kozepe lesz, onnantol nezzuk a +/- 1et
                int sor = (int) (Math.random() * (size - 1)) + 1;
                int oszlop = (int) (Math.random() * size);
                while (sor == 0 || sor == tabla.size()-1 || !checkSurroundings(new Koordinata[]{new Koordinata(sor-1, oszlop), new Koordinata(sor, oszlop), new Koordinata(sor+1, oszlop),})){
                    sor = (int) (Math.random() * (size - 1)) + 1;
                    oszlop = (int) (Math.random() * size);
                    counter++;
                    if (counter > maxAttempts) {
                        System.out.println("baj tortent... asd");
                        createGame();
                        return;
                    }
                }

                tabla.get(sor).set(oszlop, 1);
                tabla.get(sor - 1).set(oszlop, 1);
                tabla.get(sor + 1).set(oszlop, 1);
                ships.add(new Ship(3, new Koordinata[] { new Koordinata(sor - 1, oszlop), new Koordinata(sor, oszlop),
                        new Koordinata(sor + 1, oszlop) }, orientation));
            } else {
                // ilyenkor az alap a hajo kozepe lesz, onnantol nezzuk a +/- 1et
                int sor = (int) (Math.random() * (size));
                int oszlop = (int) (Math.random() * (size - 1)) + 1;
                while (oszlop == 0 || oszlop == tabla.size()-1 || !checkSurroundings(new Koordinata[]{new Koordinata(sor, oszlop-1), new Koordinata(sor, oszlop), new Koordinata(sor, oszlop+1), })) {
                    sor = (int) (Math.random() * (size));
                    oszlop = (int) (Math.random() * (size - 1)) + 1;
                    counter++;
                    if (counter > maxAttempts) {
                        System.out.println("baj tortent... asd");
                        createGame();
                        return;
                    }
                }
                tabla.get(sor).set(oszlop, 1);
                tabla.get(sor).set(oszlop - 1, 1);
                tabla.get(sor).set(oszlop + 1, 1);
                ships.add(new Ship(3, new Koordinata[] { new Koordinata(sor, oszlop -1), new Koordinata(sor, oszlop),
                        new Koordinata(sor, oszlop + 1) }, orientation));
            }
        }
    }
    public void addQuadShips() {
        for (int i = 0; i < quadShipCount; i++) {
            int counter = 0;
            boolean orientation = (Math.random()) > 0.5; // if true => vertical
            if (orientation) { // vertical
                // ilyenkor az alap a hajo teteje, onnantol nezzuk
                int sor = (int) (Math.random() * (size - 4));
                int oszlop = (int) (Math.random() * size);
                while (sor >= tabla.size()-3 || !checkSurroundings(new Koordinata[]{new Koordinata(sor, oszlop), new Koordinata(sor+1, oszlop), new Koordinata(sor+2, oszlop), new Koordinata(sor+3, oszlop)})){
                    sor = (int) (Math.random() * (size - 3));
                    oszlop = (int) (Math.random() * size);
                    counter++;
                    if (counter > maxAttempts) {
                        System.out.println("baj tortent... asd");
                        createGame();
                        return;
                    }
                }

                tabla.get(sor).set(oszlop, 1);
                tabla.get(sor + 1).set(oszlop, 1);
                tabla.get(sor + 2).set(oszlop, 1);
                tabla.get(sor + 3).set(oszlop, 1);
                ships.add(new Ship(4, new Koordinata[] { new Koordinata(sor, oszlop), new Koordinata(sor + 1, oszlop),
                        new Koordinata(sor + 2, oszlop), new Koordinata(sor + 3, oszlop) }, orientation));
            } else {
                // ilyenkor az alap a hajo kozepe lesz, onnantol nezzuk a +/- 1et
                int sor = (int) (Math.random() * (size));
                int oszlop = (int) (Math.random() * (size - 3));
                while (oszlop >= tabla.size()-3 || !checkSurroundings(new Koordinata[]{new Koordinata(sor, oszlop), new Koordinata(sor, oszlop + 1), new Koordinata(sor, oszlop + 2), new Koordinata(sor, oszlop + 3) })) {
                    sor = (int) (Math.random() * (size));
                    oszlop = (int) (Math.random() * (size - 3));
                    counter++;
                    if (counter > maxAttempts) {
                        System.out.println("baj tortent... asd");
                        createGame();
                        return;
                    }
                }
                tabla.get(sor).set(oszlop, 1);
                tabla.get(sor).set(oszlop + 1, 1);
                tabla.get(sor).set(oszlop + 2, 1);
                tabla.get(sor).set(oszlop + 3, 1);
                ships.add(new Ship(4, new Koordinata[] { new Koordinata(sor, oszlop), new Koordinata(sor, oszlop + 1),
                        new Koordinata(sor, oszlop + 2), new Koordinata(sor, oszlop + 3) }, orientation));
            }
        }
    }

    // jatek
    public boolean tamadas(Koordinata koor) {


        boolean talalt = false;
        for (Ship ship : ships) {
            boolean loves = ship.loves(koor.oszlop, koor.sor);
            if (loves) {
                talalt = true;
            }
        }
        if (tabla.get(koor.sor).get(koor.oszlop) == 0) {
            eloTabla.get(koor.sor).set(koor.oszlop, 'X');
        } else if (tabla.get(koor.sor).get(koor.oszlop) == 1) {
            eloTabla.get(koor.sor).set(koor.oszlop, '!');
        }
        //System.out.println(talalt ? "Talált" : "Nem talált");
        return talalt;
    }

    // Kiiratasok
    public void kiirHattabla() {
        for (ArrayList<Integer> integers : tabla) {
            System.out.println(integers);
        }
        System.out.println();
    }

    public void kiirElotabla() {
        for (ArrayList<Character> integers : eloTabla) {
            System.out.println(integers);
        }
        System.out.println();
    }

    public void shipekKiiratasa() {
        for (Ship ship : ships) {
            System.out.print("hossz: " + ship.hossz);
            for (Egyseg hely : ship.helyek) {
                System.out.print(" (" + hely.koordinata.sor + ", " + hely.koordinata.oszlop + "),");
            }
            System.out.println();
        }
    }
}
