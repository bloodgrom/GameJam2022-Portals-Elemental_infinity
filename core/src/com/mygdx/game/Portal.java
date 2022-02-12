package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList; 

public class Portal {

  public int coordX;
  public int coordY;

  public int width;
  public int height;

  public Texture currentTexture;

  public String variation;

  public Portal(int coordX, int coordY, int width, int height) {
    this.coordX = coordX*16;
    this.coordY = coordY*16;

    this.width = width;
    this.height = height;

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