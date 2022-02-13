package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList; 

public class Portal {

  public int coordX;
  public int coordY;

  public int width;
  public int height;

  public Texture currentTexture;

  public String variation;

  public int leftX;
  public int leftY;
  public int leftWidth;
  public int leftHeight;

  public int rightX;
  public int rightY;
  public int rightWidth;
  public int rightHeight;

  public Rectangle leftRect;
  public Rectangle rightRect;

  ArrayList<Texture> portal_animations;


  public Portal(int coordX, int coordY, int width, int height) {
    this.coordX = coordX*16;
    this.coordY = coordY*16;

    this.width = width;
    this.height = height;

    this.leftX = this.coordX-16;
    this.leftY = this.coordY;
    this.leftHeight = this.height;
    this.leftWidth = this.width/2;

    this.rightX = this.coordX + (this.width/2)-16;
    this.rightY = this.coordY;
    this.rightHeight = this.height;
    this.rightWidth = this.width/2;

    this.leftRect = new Rectangle(this.leftX + 4, this.leftY + 2, this.leftWidth - 8, this.leftHeight - 4);
    this.rightRect = new Rectangle(this.rightX + 4, this.rightY + 2, this.rightWidth - 8, this.rightHeight - 4);

    int pickVariation = (int) (Math.random()*(5 - 0 + 1 + 0));
    ArrayList<String> variations = new ArrayList<String>();

    variations.add("nature_ice");
    variations.add("nature_fire");
    variations.add("nature_water");
    variations.add("ice_fire");
    variations.add("ice_water");
    variations.add("fire_water");

    this.variation = variations.get(pickVariation);

    
    this.portal_animations = new ArrayList<Texture>();

    if(this.variation.equals("nature_ice")) {
      for(int i = 1; i < 8; i++) {
        portal_animations.add(new Texture("green_ice_" + String.valueOf(i) + ".png"));
      }
    }
    else if(this.variation.equals("nature_fire")) {
      for(int i = 1; i < 8; i++) {
        portal_animations.add(new Texture("green_red_" + String.valueOf(i) + ".png"));
      }
    }
    else if(this.variation.equals("nature_water")) {
      for(int i = 1; i < 8; i++) {
        portal_animations.add(new Texture("green_water_" + String.valueOf(i) + ".png"));
      }
    }
    else if(this.variation.equals("ice_fire")) {
      for(int i = 1; i < 8; i++) {
        portal_animations.add(new Texture("ice_red_" + String.valueOf(i) + ".png"));
      }
    }
    else if(this.variation.equals("ice_water")) {
      for(int i = 1; i < 8; i++) {
        portal_animations.add(new Texture("ice_water_" + String.valueOf(i) + ".png"));
      }
    }
    else if(this.variation.equals("fire_water")) {
      for(int i = 1; i < 8; i++) {
        portal_animations.add(new Texture("red_water_" + String.valueOf(i) + ".png"));
      }
    }

    this.currentTexture = portal_animations.get(0);
  }
}