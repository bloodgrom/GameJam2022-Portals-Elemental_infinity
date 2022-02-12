package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    public int coordX;
    public int coordY;

    public int width;
    public int height;

    public float playerSpeed = 10.0f;

    public int health;
    public int defence;

    public Rectangle body;

    public TextureRegion currentTexture;


    public Player(int coordX, int coordY, int width, int height, int health, int defence, TextureRegion currentTexture) {
        this.coordX = coordX*16;
        this.coordY = coordY*16;

        this.width = width;
        this.height = height;

        this.body = new Rectangle();

        this.body.x = this.coordX;
        this.body.y = this.coordY;
        this.body.width = this.width;
        this.body.height = this.height;
        

        this.health = health;
        this.defence = defence;

        this.currentTexture = currentTexture;
    }
}
