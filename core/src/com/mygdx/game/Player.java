package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    public int coordX;
    public int coordY;

    public int width;
    public int height;

    public int speed = 2;

    public int health;
    public int defence;

    public Rectangle body;
    public String playerOrientation;
    public String runningOrientation;

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
        this.playerOrientation = "right";
        this.runningOrientation = "right";
    }

    public void playerRunLeft(int counter, Player player) {
        if(counter % 12 == 0) {
            Texture playerSprite = new Texture("player_left.png");
		    TextureRegion[][] splitTilesPlayer = TextureRegion.split(playerSprite, 48, 48);
            if(counter >=0 && counter <= 60) {
               player.currentTexture = splitTilesPlayer[1][counter/12];

                if(counter % 36 == 0) {
                    long id = MyGdxGame.runningSound.play(1.0f);
                    MyGdxGame.runningSound.setPitch(id, 2);
                    MyGdxGame.runningSound.setLooping(id, false);
                }
            }
        }
    }

    public void playerRunRight(int counter, Player player) {
        if(counter % 12 == 0) {
            Texture playerSprite = new Texture("player_right.png");
		    TextureRegion[][] splitTilesPlayer = TextureRegion.split(playerSprite, 48, 48);
            if(counter >=0 && counter <= 60) {
               player.currentTexture = splitTilesPlayer[1][counter/12];

               if(counter % 36 == 0) {
                long id = MyGdxGame.runningSound.play(1.0f);
                MyGdxGame.runningSound.setPitch(id, 2);
                MyGdxGame.runningSound.setLooping(id, false);
                }
            }
        }
    }

    public void playerRunUp(int counter, Player player, String runningOrientation) {

        if(runningOrientation.equals("right")){
            if(counter % 12 == 0) {
                Texture playerSprite = new Texture("player_right.png");
                TextureRegion[][] splitTilesPlayer = TextureRegion.split(playerSprite, 48, 48);
                if(counter >=0 && counter <= 60) {
                   player.currentTexture = splitTilesPlayer[1][counter/12];

                   if(counter % 36 == 0) {
                    long id = MyGdxGame.runningSound.play(1.0f);
                    MyGdxGame.runningSound.setPitch(id, 2);
                    MyGdxGame.runningSound.setLooping(id, false);
                    }
                }
            }
        }
        else if(runningOrientation.equals("left")) {
            if(counter % 12 == 0) {
                Texture playerSprite = new Texture("player_left.png");
                TextureRegion[][] splitTilesPlayer = TextureRegion.split(playerSprite, 48, 48);
                if(counter >=0 && counter <= 60) {
                   player.currentTexture = splitTilesPlayer[1][counter/12];

                   if(counter % 36 == 0) {
                    long id = MyGdxGame.runningSound.play(1.0f);
                    MyGdxGame.runningSound.setPitch(id, 2);
                    MyGdxGame.runningSound.setLooping(id, false);
                    }
                }
            }
        }
    }

    public void playerRunDown(int counter, Player player, String runningOrientation) {

        if(runningOrientation.equals("right")){
            if(counter % 12 == 0) {
                Texture playerSprite = new Texture("player_right.png");
                TextureRegion[][] splitTilesPlayer = TextureRegion.split(playerSprite, 48, 48);
                if(counter >=0 && counter <= 60) {
                   player.currentTexture = splitTilesPlayer[1][counter/12];

                   if(counter % 36 == 0) {
                    long id = MyGdxGame.runningSound.play(1.0f);
                    MyGdxGame.runningSound.setPitch(id, 2);
                    MyGdxGame.runningSound.setLooping(id, false);
                    }
                }
            }
        }
        else if(runningOrientation.equals("left")) {
            if(counter % 12 == 0) {
                Texture playerSprite = new Texture("player_left.png");
                TextureRegion[][] splitTilesPlayer = TextureRegion.split(playerSprite, 48, 48);
                if(counter >=0 && counter <= 60) {
                   player.currentTexture = splitTilesPlayer[1][counter/12];
                   if(counter % 36 == 0) {
                    long id = MyGdxGame.runningSound.play(1.0f);
                    MyGdxGame.runningSound.setPitch(id, 2);
                    MyGdxGame.runningSound.setLooping(id, false);
                    }
                }
            }
        }
    }

}
