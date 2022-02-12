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


  public Portal(int coordX, int coordY, int width, int height) {
    this.coordX = coordX*16;
    this.coordY = coordY*16;

    this.width = width;
    this.height = height;

    this.leftX = this.coordX-16;
    this.leftY = this.coordY;
    this.leftHeight = this.height;
    this.leftWidth = this.width/3;

    this.rightX = this.coordX + 2*(this.width/3)-16;
    this.rightY = this.coordY;
    this.rightHeight = this.height;
    this.rightWidth = this.width/3;

    this.leftRect = new Rectangle(this.leftX, this.leftY, this.leftWidth, this.leftHeight);
    this.rightRect = new Rectangle(this.rightX, this.rightY, this.rightWidth, this.rightHeight);

    int pickVariation = (int) (Math.random()*(4 - 0 + 1 + 0));
    ArrayList<String> variations = new ArrayList<String>();

    variations.add("nature_ice");
    variations.add("nature_fire");
    variations.add("nature_water");
    variations.add("ice_fire");
    variations.add("ice_water");
    variations.add("fire_water");

    this.variation = variations.get(pickVariation);

    this.currentTexture = new Texture("green_ice_0.png");
}

}