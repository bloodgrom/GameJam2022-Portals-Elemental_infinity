package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Potion {
    public float coordX;
    public float coordY;

    public int width;
    public int height;

    public float healAmount;

    public Rectangle body;

    public TextureRegion currentTexture;

    public int type;

    public Potion(float coordX, float coordY, int width, int height) {
        this.coordX = coordX*16;
        this.coordY = coordY*16;
        this.width = width;
        this.height = height;
        
        this.body = new Rectangle(this.coordX, this.coordY, this.width, this.height);

        type = (int) (Math.random()*(100-1)) + 1;


        if(type < 70) {
            healAmount = 0.1f;

            Texture redPotions = new Texture("red_potions.png");
		    TextureRegion[][] splitRedPotions = TextureRegion.split(redPotions, 16, 16);

            int randomRow = (int) (Math.random()*(2-0)) + 0;
            int randomColumn = (int) (Math.random()*(2-0)) + 0;

            this.currentTexture = splitRedPotions[randomRow][randomColumn];

        }
        else if(type < 90) {
            healAmount = 0.3f;

            Texture purplePotions = new Texture("purple_potions.png");
		    TextureRegion[][] splitPurplePotions = TextureRegion.split(purplePotions, 16, 16);

            int randomRow = (int) (Math.random()*(2-0)) + 0;
            int randomColumn = (int) (Math.random()*(2-0)) + 0;

            this.currentTexture = splitPurplePotions[randomRow][randomColumn];

            
        }
        else {
            healAmount = 0.6f;

            Texture yellowPotions = new Texture("yellow_potions.png");
		    TextureRegion[][] splitYellowPotions = TextureRegion.split(yellowPotions, 16, 16);

            int randomRow = (int) (Math.random()*(2-0)) + 0;
            int randomColumn = (int) (Math.random()*(2-0)) + 0;

            this.currentTexture = splitYellowPotions[randomRow][randomColumn];

        }
    }
}
