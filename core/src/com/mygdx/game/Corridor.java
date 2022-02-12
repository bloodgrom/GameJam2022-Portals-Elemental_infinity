package com.mygdx.game;

import java.util.ArrayList;

public class Corridor {
    public int startCoordX;
    public int startCoordY;
    public int width;
    public int length;

    public Corridor(int startCoordX, int startCoordY, int width) {
      
        this.startCoordX = startCoordX;
        this.startCoordY = startCoordY;
        this.width = width;
    }

    public static double calculateDistanceBetweenPoints(double x1, double y1, double x2, double y2) {       
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    } 

    public static ArrayList<Integer> chooseConnection(int x1, int y1, int x2, int y2) {
        
        ArrayList<Integer> chosenConnection = new ArrayList<Integer>();

        if(Math.random() < 0.5) {
            chosenConnection.add(x2);
            chosenConnection.add(y1);
            //0 means we connect horizontaly
            chosenConnection.add(0);

            return chosenConnection;  
        }
        else {
            chosenConnection.add(x1);
            chosenConnection.add(y2);
            //1 means we connect vertically
            chosenConnection.add(1);

            return chosenConnection;
        }

    }
}

