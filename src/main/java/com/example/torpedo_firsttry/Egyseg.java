package com.example.torpedo_firsttry;

public class Egyseg {
    Koordinata koordinata;
    boolean shot;

    public Egyseg(int oszlop, int sor){
        this.koordinata = new Koordinata(sor, oszlop);
        shot = false;
    }
}

