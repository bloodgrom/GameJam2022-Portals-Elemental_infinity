package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Utils {

  public static void bubbleSort1(int[] a, int[] b) {
    boolean sorted = false;
    int temp;
    int temp2;
    while(!sorted) {
      sorted = true;
      for (int i = 0; i < a.length - 1; i++) {
        if (a[i] > a[i+1]) {
          temp = a[i];
          temp2 = b[i];
          a[i] = a[i+1];
          a[i+1] = temp;
          b[i] = b[i+1];
          b[i+1] = temp2;
          sorted = false;
        }
      }
    }
  }

  public static void bubbleSortRooms(ArrayList<Double> distances, ArrayList<Room> rooms) {
    boolean sorted = false;
    Double tempDistance;
    Room tempRoom;
    while(!sorted) {
      sorted = true;
      for (int i = 0; i < distances.size() - 1; i++) {
        if (distances.get(i) > distances.get(i+1)) {
          tempDistance = distances.get(i);
          tempRoom = rooms.get(i);

          distances.set(i, distances.get(i+1));
          distances.set(i+1, tempDistance);

          rooms.set(i, rooms.get(i+1));
          rooms.set(i+1, tempRoom);

          sorted = false;
        }
      }
    }
  }
  
}
