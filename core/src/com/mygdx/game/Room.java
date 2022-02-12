package com.mygdx.game;

import java.util.ArrayList;

public class Room {
    public static final int MAX_NUM_ROOMS = 25;
    public static final int MIN_NUM_ROOMS = 15; 

    public static final int MAX_WIDTH = 15;
    public static final int MAX_HEIGHT = 15;
    public static final int MIN_WIDTH = 5;
    public static final int MIN_HEIGHT = 5;

    public int coordX;
    public int coordY;

    public int width;
    public int height;

    public int centerCoordX;
    public int centerCoordY;

    public Room(int coordX, int coordY, int width, int height) {

        this.coordX = coordX;
        this.coordY = coordY;
        this.width = width;
        this.height = height;

        this.centerCoordX = (int)Math.floor(coordX + width/2);
        this.centerCoordY = (int)Math.floor(coordY + height/2);
        
    }

    public boolean checkRoomBounds() {
      
      if((this.coordX + this.width < MyGdxGame.numTilesVertical)  && (this.coordY + this.height < MyGdxGame.numTilesHorizontal)) {
          return true;
      }
      else {
        return false;
      }
    }

    public boolean checkRoomIntersects(ArrayList<Room> rooms) {
        boolean intersects = false;

        for(int i=0; i<rooms.size(); i++) {

            int x1 = this.coordX;
            int x2 = this.coordX + this.width;
            int x3 = rooms.get(i).coordX;
            int x4 = rooms.get(i).coordX + rooms.get(i).width;
            
            int y1 = this.coordY;
            int y2 = this.coordY + this.height;
            int y3 = rooms.get(i).coordY;
            int y4 = rooms.get(i).coordY + rooms.get(i).height;

          if ((x1 < x4 + 3) && (x3 < x2 + 3) && (y1 < y4 + 3) && (y3 < y2 + 3))  {
              intersects = true;
              break;
          }
        }

        return intersects;
    }

    public static ArrayList<Room> generateCustomRooms() {
        ArrayList<Room> customRooms = new ArrayList<>();
        
        //---------------- START ROOM -------------------------------
        ArrayList<Integer> possibleStartRoomX = new ArrayList<>();
        ArrayList<Integer> possibleStartRoomY = new ArrayList<>();

        possibleStartRoomX.add(3);
        possibleStartRoomY.add(3);
        possibleStartRoomX.add(113);
        possibleStartRoomY.add(3);
        possibleStartRoomX.add(3);
        possibleStartRoomY.add(62);
        possibleStartRoomX.add(113);
        possibleStartRoomY.add(62);

        int startRoomLocationIndex = (int) (Math.random()*3);

        int startRoomX = possibleStartRoomX.get(startRoomLocationIndex);
        int startRoomY = possibleStartRoomY.get(startRoomLocationIndex);
        int startRoomWidth = 3;
        int startRoomHeight = 3;

        Room startRoom = new Room(startRoomX, startRoomY, startRoomWidth, startRoomHeight);
        customRooms.add(startRoom);
        //---------------- START ROOM -------------------------------

        //---------------- END ROOM -------------------------------
        int endRoomX;
        int endRoomY;

        if(startRoomLocationIndex == 0) {
            endRoomX = 113;
            endRoomY = 62;
        }
        else if(startRoomLocationIndex == 1) {
            endRoomX = 3;
            endRoomY = 62;
        }
        else if(startRoomLocationIndex == 2) {
            endRoomX = 113;
            endRoomY = 3;
        }
        else {
            endRoomX = 3;
            endRoomY = 3;
        }

        int endRoomWidth = 5;
        int endRoomHeight = 5;

        Room endRoom = new Room(endRoomX, endRoomY, endRoomWidth, endRoomHeight);
        customRooms.add(endRoom);
        
        return customRooms;
    }

}
