package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Spike {

    public float coordX;
    public float coordY;

    public int width;
    public int height;

    public Rectangle body;

    public float velocityX;
    public float velocityY;

    public TextureRegion currentTexture;

    public Spike(float coordX, float coordY, int width, int height) {
        this.coordX = coordX*16;
        this.coordY = coordY*16;
        this.width = width;
        this.height = height;

        this.body = new Rectangle(this.coordX, this.coordY, this.width, this.height);

        //velocity
        int randomVelocityX = (int) (Math.random()*(2-0)) + 0;
        int randomVelocityY = (int) (Math.random()*(2-0)) + 0;

        float factor = 10.0f;

        if(randomVelocityX == 0) {
            this.velocityX = 1/factor;
        }
        else if(randomVelocityX == 1) {
            this.velocityX = (-1)/factor;
        }
        

        if(randomVelocityY == 0) {
            this.velocityY = 1/factor;
        }
        else if(randomVelocityY == 1) {
            this.velocityY = (-1)/factor;
        }

        //set texture;
        Texture playerSprite = new Texture("player_right.png");
		TextureRegion[][] splitTilesPlayer = TextureRegion.split(playerSprite, 48, 48);

        this.currentTexture = splitTilesPlayer[1][1];
    }


    
}
