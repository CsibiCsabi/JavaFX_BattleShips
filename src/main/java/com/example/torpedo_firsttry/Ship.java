package com.example.torpedo_firsttry;


import java.util.ArrayList;

public class Ship {
    boolean elsullyedve;
    boolean szinezve;
    int hossz;
    boolean orientation;
    ArrayList<Egyseg> helyek = new ArrayList<Egyseg>(); //

    public Ship(int hossz, Koordinata[] helyek, boolean orientation) {
        this.hossz = hossz;
        this.orientation = orientation;
        for (Koordinata koor : helyek) {
            this.helyek.add(new Egyseg(koor.oszlop, koor.sor));
        }
        elsullyedve = false;
    }



    public Koordinata[] getKoordinatak(){
        Koordinata[] koordinatak = new Koordinata[helyek.size()];
        for (int i = 0; i < koordinatak.length; i++) {
            koordinatak[i] = helyek.get(i).koordinata;
        }
        return koordinatak;
    }

    public boolean loves(int oszlop, int sor) {
        boolean talalat = false;
        for (Egyseg egyseg : helyek) {
            if ((egyseg.koordinata.oszlop == oszlop) && (egyseg.koordinata.sor == sor)) {
                talalat = true;
                egyseg.shot = true;
                int elsullyedtEgysegek = 0;
                for (Egyseg egyseg2 : helyek) {
                    if (egyseg2.shot) {
                        elsullyedtEgysegek++;
                    }
                }
                if (elsullyedtEgysegek >= helyek.size()) {
                    sullyed();
                }
            }
        }
        return talalat;

    }

    public void kesz(){
        System.out.println("asd");
    }
    public void sullyed() {
        elsullyedve = true;
        System.out.println("Ez a hajo elsullyedt");
    }
}
