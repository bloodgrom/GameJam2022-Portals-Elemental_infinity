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

    public float speed;

    public Spike(float coordX, float coordY, int width, int height, String levelVariationName, float speed) {
        this.coordX = coordX*16;
        this.coordY = coordY*16;
        this.width = width;
        this.height = height;

        this.speed = speed;

        this.body = new Rectangle(this.coordX, this.coordY, this.width, this.height);

        //velocity
        int randomVelocityX = (int) (Math.random()*(3-0)) + 0;
        int randomVelocityY = (int) (Math.random()*(3-0)) + 0;

        if(randomVelocityX == 0) {
            this.velocityX = speed;
        }
        else if(randomVelocityX == 1) {
            this.velocityX = -speed;
        }
        else if(randomVelocityX == 2) {
            this.velocityX = 0;
        }
        

        if(randomVelocityY == 0) {
            this.velocityY = speed;
        }
        else if(randomVelocityY == 1) {
            this.velocityY = -speed;
        }
        else if(randomVelocityX == 2) {
            this.velocityY = 0;
        }

        //set texture;
        String fileName = "";
        if(levelVariationName.equals("fire")) {
            int randomMonster = (int) (Math.random()*(4-1)) + 1;
            fileName = "fire_monster" + String.valueOf(randomMonster) + ".png";
        }
        else if(levelVariationName.equals("ice") || levelVariationName.equals("water")) {
            int randomMonster = (int) (Math.random()*(4-1)) + 1;
            fileName = "ice_monster" + String.valueOf(randomMonster) + ".png";
        }
        else {
            int randomMonster = (int) (Math.random()*(4-1)) + 1;
            fileName = "forest_monster" + String.valueOf(randomMonster) + ".png";
        }
        
        Texture monsterSprite = new Texture(fileName);
        TextureRegion[][] splitMonsterSprite = TextureRegion.split(monsterSprite, 16, 16);

        this.currentTexture = splitMonsterSprite[0][0];
    }


    
}
