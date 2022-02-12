package com.mygdx.game;

public class Player {
    public int coordX;
    public int coordY;

    public int health;
    public int defence;



    public Player(int coordX, int coordY,int health, int defence) {
        this.coordX = coordX;
        this.coordY = coordY;

        this.health = health;
        this.defence = defence;
    }
}
