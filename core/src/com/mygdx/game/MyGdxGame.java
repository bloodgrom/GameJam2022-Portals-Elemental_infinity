package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.bullet.linearmath.int4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {

	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	//private OrthoCamController cameraController;
	private AssetManager assetManager;
	private Texture tiles;
	private Texture tilesFloor;
	private Texture tilesCorridor;
	private Texture additionalWalls;
	private Texture playerSprite;
	private Texture texture;
  private Texture textureTest;
	private BitmapFont font;
	private SpriteBatch batch;
	public KeyboardController controller;

	private Texture bucketImage;
	

	public static int numTilesHorizontal = 120;
	public static int numTilesVertical = 68;

	public Player player;

    public Portal portal;

	float w;
	float h;

	float viewPortWidth;
	float viewPortHeight;

	int counter;
    int animationPortalCounter;
	int animationUnstuckCounter;

    float previousCoordX;
    float previousCoordY;

    float initialCoordsX;
    float initialCoordsY;

    ArrayList<Texture> portal_variations;

	int corridorTileX;
	int corridorTileY;

	int floorTileX;
	int floorTileY;

	int tileCoordX;
	int tileCoordY;

	int additionalWallsHorizontalTopX;
	int additionalWallsHorizontalTopY;

	int additionalWallsHorizontalBottomX;
	int additionalWallsHorizontalBottomY;

	int additionalWallsHorizontalTopLeftX;
	int additionalWallsHorizontalTopLeftY;

	int additionalWallsHorizontalTopRightX;
	int additionalWallsHorizontalTopRightY;

	int additionalWallsHorizontalBottomLeftX;
	int additionalWallsHorizontalBottomLeftY;

	int additionalWallsHorizontalBottomRightX;
	int additionalWallsHorizontalBottomRightY;

	TextureRegion[][] splitTiles;

	boolean portalTouched;


	int additionalWallsVerticalX;
	int additionalWallsVerticalY;

	//0 is wall, 1 is floor
	int[][] collisionLayerBoolean;

	ArrayList<Rectangle> finalCollisionLayer;

	int tileSize;

	int levelVariation;
	String levelVariationName;

	Integer levelCounter;



	@Override
	public void create () {

		

		if(levelCounter == null) {
			levelVariationName = "";
			levelCounter = 1;
		}
		else {
			levelCounter ++;
		}

		portalTouched = false;

		collisionLayerBoolean = new int[numTilesHorizontal][numTilesVertical];

		for (int row = 0; row < collisionLayerBoolean.length; row++) {
				for (int col = 0; col < collisionLayerBoolean[row].length; col++) {
						collisionLayerBoolean[row][col] = 0;
				}
		}

		counter = 0;
		animationPortalCounter = 0;
		animationUnstuckCounter= 0;

		controller = new KeyboardController();
		Gdx.input.setInputProcessor(controller);

		additionalWalls = new Texture("new_dungeon2.png");

        Pixmap pixmap2000 = new Pixmap(Gdx.files.internal("badlogic.jpg"));
        Pixmap pixmap1000 = new Pixmap(16, 16, pixmap2000.getFormat());
        pixmap1000.drawPixmap(pixmap2000,
                        0, 0, pixmap2000.getWidth(), pixmap2000.getHeight(),
                        0, 0, pixmap1000.getWidth(), pixmap1000.getHeight()
        );

    

        textureTest = new Texture(pixmap1000);

		additionalWallsHorizontalTopX = 0;
		additionalWallsHorizontalTopY = 2;

		additionalWallsHorizontalBottomX = 1;
		additionalWallsHorizontalBottomY = 2;

		additionalWallsHorizontalTopLeftX = 0;
		additionalWallsHorizontalTopLeftY = 1;

		additionalWallsHorizontalTopRightX = 0;
		additionalWallsHorizontalTopRightY = 3;

		additionalWallsHorizontalBottomLeftX = 1;
		additionalWallsHorizontalBottomLeftY = 1;

		additionalWallsHorizontalBottomRightX = 1;
		additionalWallsHorizontalBottomRightY = 3;

        tileSize = 16;

		additionalWallsVerticalX = 3;
		additionalWallsVerticalY = 0;

		TextureRegion[][] additionalWallsRegion = TextureRegion.split(additionalWalls, 16, 16);

		tilesFloor = new Texture("new_dungeon2.png");
		tilesCorridor = new Texture("new_dungeon2.png");

		corridorTileX = 8;
		corridorTileY = 0;

		floorTileX = 8;
		floorTileY = 0;

				
        finalCollisionLayer = new ArrayList<>();


		int pickTileSet = (int) (Math.random()*(4));

		//System.out.println(levelVariationName);


		if(levelVariationName.equals("fire") || (pickTileSet==0 && levelCounter==1)) {
			Pixmap pixmap200 = new Pixmap(Gdx.files.internal("lava.png"));
			Pixmap pixmap100 = new Pixmap(16, 16, pixmap200.getFormat());
			pixmap100.drawPixmap(pixmap200,
							0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
							0, 0, pixmap100.getWidth(), pixmap100.getHeight()
			);

			tiles = new Texture(pixmap100);
			splitTiles = TextureRegion.split(tiles, 16, 16);

			tileCoordX = 0;
			tileCoordY = 0;
		}

		else if(levelVariationName.equals("ice") || (pickTileSet==1 && levelCounter==1)) {
			tiles = new Texture("IceSet.png");
			splitTiles = TextureRegion.split(tiles, 16, 16);

			tileCoordX = 0;
			tileCoordY = 0;

		}

		else if(levelVariationName.equals("water") || (pickTileSet==2 && levelCounter==1)) {
			tiles = new Texture("Water.png");
			splitTiles = TextureRegion.split(tiles, 16, 16);

			tileCoordX = 0;
			tileCoordY = 2;
		}

		else if(levelVariationName.equals("nature") || (pickTileSet==3 && levelCounter==1)){
			tiles = new Texture("new_forest.png");
			splitTiles = TextureRegion.split(tiles, 16, 16);

			tileCoordX = 2;
			tileCoordY = 11;

		}


		w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        viewPortWidth = (w/h)*320;
        viewPortHeight = 320;
		
		camera = new OrthographicCamera();  // The camera will take the viewport (what you can see looking through the camera) of the screen size (1280, 720) if you don't specify otherwise
		camera.setToOrtho(false, viewPortWidth, viewPortHeight); // We want (0,0) in the bottom left corner
		camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0); // this will render the camera so that 0,0 (of everything inside batch.begin() - batch.end()) will be rendered at 0,0 on your screen
		camera.update(); // Updates the camera

		font = new BitmapFont();
		batch = new SpriteBatch();

		map = new TiledMap();

		MapLayers layers = map.getLayers();

		//----------------------- WALL SECTION - LAYER 1 -----------------------//
		
		TiledMapTileLayer layer1 = new TiledMapTileLayer(numTilesHorizontal, numTilesVertical, 16, 16);
		for (int x = 0; x < numTilesHorizontal; x++) {
			for (int y = 0; y < numTilesVertical; y++) {
				// int ty = (int)(Math.random() * splitTiles.length);
				// int tx = (int)(Math.random() * splitTiles[ty].length);
				
				//----------------------- SELECT TYPE OF WALL -----------------------//
				Cell cell = new Cell();
				cell.setTile(new StaticTiledMapTile(splitTiles[tileCoordX][tileCoordY]));
				layer1.setCell(x, y, cell);
			}
		}
		layers.add(layer1);
		
		//----------------------- ROOM GENERATION - LAYER 2 -----------------------//
		TiledMapTileLayer layer2 = new TiledMapTileLayer(numTilesHorizontal, numTilesVertical, 16, 16);
		int num_of_rooms = (int) (Math.random()*(Room.MAX_NUM_ROOMS-Room.MIN_NUM_ROOMS)) + Room.MIN_NUM_ROOMS;
		ArrayList<Room> rooms = new ArrayList<Room>();

		//here add custom rooms
		ArrayList<Room> customRooms = new ArrayList<Room>();
		customRooms.addAll(Room.generateCustomRooms());

		rooms.add(customRooms.get(0));

		TextureRegion[][] splitTilesFloor = TextureRegion.split(tilesFloor, 16, 16);

		for(int i = 1; i < num_of_rooms; i++) {

			//generate width and height for room
			int roomWidth = (int) (Math.random()*(Room.MAX_WIDTH-Room.MIN_WIDTH)) + Room.MIN_WIDTH;
			int roomHeight = (int) (Math.random()*(Room.MAX_HEIGHT-Room.MIN_HEIGHT)) + Room.MIN_HEIGHT;

			boolean intersect = true;

			while(intersect) {
				int coordX = (int) (Math.random()*((numTilesHorizontal - roomWidth - 1) - 1)) + 1;
				int coordY = (int) (Math.random()*((numTilesVertical - roomHeight - 1) - 1)) + 1;
				
				Room newRoom = new Room(coordX, coordY, roomWidth, roomHeight);

				if((!newRoom.checkRoomIntersects(rooms)) && (!newRoom.checkRoomIntersects(customRooms))) {
					intersect = false;
					rooms.add(newRoom);
					break;
				}
			}

			if(i == num_of_rooms - 1) {
				rooms.add(customRooms.get(1));
			}

			//----------------------- DRAW ROOMS WALLS STARTS -----------------------//
			int firstRoomCoordX = customRooms.get(0).coordX;
			int firstRoomCoordY = customRooms.get(0).coordY;
			int firstRoomWidth = customRooms.get(0).width;
			int firstRoomHeight = customRooms.get(0).height;

			for(int x = firstRoomCoordX - 1; x < firstRoomCoordX + firstRoomWidth + 1; x++) {
				Cell cellFirst = new Cell();
				if(x == firstRoomCoordX - 1) {
					cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalBottomLeftX][additionalWallsHorizontalBottomLeftY]));
				}
				else if(x == (firstRoomCoordX + firstRoomWidth + 1) - 1) {
					cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalBottomRightX][additionalWallsHorizontalBottomRightY]));
				}
				else {
					cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalBottomX][additionalWallsHorizontalBottomY]));
				}
				layer2.setCell(x, firstRoomCoordY - 1, cellFirst);
			}

			for(int x = firstRoomCoordX - 1; x < firstRoomCoordX + firstRoomWidth + 1; x++) {
				Cell cellFirst = new Cell();
				if(x == firstRoomCoordX - 1) {
					cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalTopLeftX][additionalWallsHorizontalTopLeftY]));
				}
				else if(x == (firstRoomCoordX + firstRoomWidth + 1) - 1) {
					cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalTopRightX][additionalWallsHorizontalTopRightY]));
				}
				else {
					cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalTopX][additionalWallsHorizontalTopY]));
				}
				layer2.setCell(x, firstRoomCoordY + firstRoomHeight, cellFirst);
			}

			for(int y = firstRoomCoordY; y < firstRoomCoordY + firstRoomHeight; y++) {
				Cell cellFirst = new Cell();
				cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsVerticalX][additionalWallsVerticalY]));
				layer2.setCell(firstRoomCoordX - 1, y, cellFirst);
			}

			for(int y = firstRoomCoordY; y < firstRoomCoordY + firstRoomHeight; y++) {
				Cell cellFirst = new Cell();
				cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsVerticalX][additionalWallsVerticalY]));
				layer2.setCell(firstRoomCoordX + firstRoomWidth, y, cellFirst);
			}

			int coordX = rooms.get(i).coordX;
			int coordY = rooms.get(i).coordY;

			int width = rooms.get(i).width;
			int height = rooms.get(i).height;

			for(int x = coordX - 1; x < coordX + width + 1; x++) {
				Cell cellFirst = new Cell();
				if(x == coordX - 1) {
					cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalBottomLeftX][additionalWallsHorizontalBottomLeftY]));
				}
				else if(x == (coordX + width + 1) - 1) {
					cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalBottomRightX][additionalWallsHorizontalBottomRightY]));
				}
				else {
					cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalBottomX][additionalWallsHorizontalBottomY]));
				}
				layer2.setCell(x, coordY - 1, cellFirst);
			}

			for(int x = coordX - 1; x < coordX + width + 1; x++) {
				Cell cellFirst = new Cell();
				if(x == coordX - 1) {
					cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalTopLeftX][additionalWallsHorizontalTopLeftY]));
				}
				else if(x == (coordX + width + 1) - 1) {
					cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalTopRightX][additionalWallsHorizontalTopRightY]));
				}
				else {
					cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalTopX][additionalWallsHorizontalTopY]));
				}
				layer2.setCell(x, coordY + height, cellFirst);
			}

			for(int y = coordY; y < coordY + height; y++) {
				Cell cellFirst = new Cell();
				cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsVerticalX][additionalWallsVerticalY]));
				layer2.setCell(coordX - 1, y, cellFirst);
			}

			for(int y = coordY; y < coordY + height; y++) {
				Cell cellFirst = new Cell();
				cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsVerticalX][additionalWallsVerticalY]));
				layer2.setCell(coordX + width, y, cellFirst);
			}
			

			int endRoomCoordX = customRooms.get(1).coordX;
			int endRoomCoordY = customRooms.get(1).coordY;
			int endRoomWidth = customRooms.get(1).width;
			int endRoomHeight = customRooms.get(1).height;

			for(int x = endRoomCoordX - 1; x < endRoomCoordX + endRoomWidth + 1; x++) {
				Cell cellFirst = new Cell();
				cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalBottomX][additionalWallsHorizontalBottomY]));
				layer2.setCell(x, endRoomCoordY - 1, cellFirst);
			}

			for(int x = endRoomCoordX - 1; x < endRoomCoordX + endRoomWidth + 1; x++) {
				Cell cellFirst = new Cell();
				cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsHorizontalTopX][additionalWallsHorizontalTopY]));
				layer2.setCell(x, endRoomCoordY + endRoomHeight, cellFirst);
			}

			for(int y = endRoomCoordY; y < endRoomCoordY + endRoomHeight; y++) {
				Cell cellFirst = new Cell();
				cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsVerticalX][additionalWallsVerticalY]));
				layer2.setCell(endRoomCoordX - 1, y, cellFirst);
			}

			for(int y = endRoomCoordY; y < endRoomCoordY + endRoomHeight; y++) {
				Cell cellFirst = new Cell();
				cellFirst.setTile(new StaticTiledMapTile(additionalWallsRegion[additionalWallsVerticalX][additionalWallsVerticalY]));
				layer2.setCell(endRoomCoordX + endRoomWidth, y, cellFirst);
			}

			//----------------------- DRAW ROOMS WALLS ENDS -----------------------//

			//----------------------- CORIDOR SECTION -----------------------//
			TextureRegion[][] splitTilesCorridor = TextureRegion.split(tilesCorridor, 16, 16);

			for(int l = 0; l < rooms.size(); l++) {
				ArrayList<Room> roomsByDistance = new ArrayList<>();
				ArrayList<Double> roomsDistance = new ArrayList<>();

				for(int k = 0; k < rooms.size(); k++) {

					if(k != l) {
						int x1 = rooms.get(l).centerCoordX;
						int y1 = rooms.get(l).centerCoordY;
						int x2 = rooms.get(k).centerCoordX;
						int y2 = rooms.get(k).centerCoordY;

						Double distance = Corridor.calculateDistanceBetweenPoints(x1, y1, x2, y2);
						
						if(roomsByDistance.size() < 4) {
							roomsByDistance.add(rooms.get(k));
							roomsDistance.add(distance);
						}
						else {
							Double tempBiggestDistance = distance;
							int tempRoomIndex = 0;

							for(int z = 0; z < roomsDistance.size(); z++) {
								if(roomsDistance.get(z) > tempBiggestDistance) {
									tempBiggestDistance = roomsDistance.get(z);
									tempRoomIndex = z;
								}
							}

							if (tempBiggestDistance != distance) {
								roomsByDistance.set(tempRoomIndex, rooms.get(k));
								roomsDistance.set(tempRoomIndex, distance);
							}
						}

					}
			
				}


				//sort rooms by distance
				Utils.bubbleSortRooms(roomsDistance, roomsByDistance);

				int numOfConnections;

				numOfConnections = 1;

				//draw corridors between room i and chosen rooms
				for(int z = 0; z < numOfConnections; z++) {
					int x1 = rooms.get(l).centerCoordX;
					int y1 = rooms.get(l).centerCoordY;

					int x2 = roomsByDistance.get(z).centerCoordX;
					int y2 = roomsByDistance.get(z).centerCoordY;

					ArrayList<Integer> chosenConnection = Corridor.chooseConnection(x1, y1, x2, y2);

					int chosenConnectionX = chosenConnection.get(0);
					int chosenConnectionY = chosenConnection.get(1);
					int chosenDirection = chosenConnection.get(2);
			
					//up-1
					if(x1 == chosenConnectionX && chosenConnectionY > y1) {
						for(int k = y1; k < chosenConnectionY; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(x1, k, cell);
                            collisionLayerBoolean[x1][k] = 1;
							layer2.setCell(x1+1, k, cell);
							collisionLayerBoolean[x1+1][k] = 1;
						}
					}
					//down-1
					else if(x1 == chosenConnectionX && chosenConnectionY < y1) {
						for(int k = chosenConnectionY; k < y1; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(x1, k, cell);
                            collisionLayerBoolean[x1][k] = 1;
							layer2.setCell(x1+1, k, cell);
                            collisionLayerBoolean[x1+1][k] = 1;
						}
                    }
					//right-1
					else if(y1 == chosenConnectionY && chosenConnectionX > x1) {
						for(int k = x1; k < chosenConnectionX+1; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(k, y1, cell);
                            collisionLayerBoolean[k][y1] = 1;
							layer2.setCell(k, y1+1, cell);
                            collisionLayerBoolean[k][y1+1] = 1;
						}
                    }
					//left-1
					else if(y1 == chosenConnectionY && chosenConnectionX < x1) {
						for(int k = chosenConnectionX; k < x1; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(k, y1, cell);
                            collisionLayerBoolean[k][y1] = 1;
							layer2.setCell(k, y1+1, cell);
                            collisionLayerBoolean[k][y1+1] = 1;
						}
                    }

					//up-2
					if(x2 == chosenConnectionX && chosenConnectionY > y2) {
						for(int k = y2; k < chosenConnectionY; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(x2, k, cell);
                            collisionLayerBoolean[x2][k] = 1;
							layer2.setCell(x2+1, k, cell);
                            collisionLayerBoolean[x2+1][k] = 1;
						}
                    }
					//down-2
					else if(x2 == chosenConnectionX && chosenConnectionY < y2) {
						for(int k = chosenConnectionY; k < y2; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(x2, k, cell);
                            collisionLayerBoolean[x2][k] = 1;
							layer2.setCell(x2+1, k, cell);
                            collisionLayerBoolean[x2+1][k] = 1;
						}
                    }
					//right-2
					else if(y2 == chosenConnectionY && chosenConnectionX > x2) {
						for(int k = x2; k < chosenConnectionX+1; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(k, y2, cell);
                            collisionLayerBoolean[k][y2] = 1;
							layer2.setCell(k, y2+1, cell);
                            collisionLayerBoolean[k][y2+1] = 1;
						}
                    }
					//left-2
					else if(y2 == chosenConnectionY && chosenConnectionX < x2) {
						for(int k = chosenConnectionX; k < x2; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(k, y2+1, cell);
                            collisionLayerBoolean[k][y2+1] = 1;
						}
                    }	
				}

			}
			
			
		}
		//ENDS HERE

		//----------------------- DRAW ROOMS WALLS STARTS -----------------------//

		int firstRoomCoordX = customRooms.get(0).coordX;
		int firstRoomCoordY = customRooms.get(0).coordY;
		int firstRoomWidth = customRooms.get(0).width;
		int firstRoomHeight = customRooms.get(0).height;

        
        

		for(int y = firstRoomCoordY; y < firstRoomCoordY + firstRoomHeight; y++) {
			for(int x = firstRoomCoordX; x < firstRoomCoordX + firstRoomWidth; x++) {
				Cell cellFirst = new Cell();
				cellFirst.setTile(new StaticTiledMapTile(splitTilesFloor[floorTileX][floorTileY]));
				layer2.setCell(x, y, cellFirst);
                collisionLayerBoolean[x][y] = 1;
			}
		}

		for (int m = 1; m < num_of_rooms; m++) {
			int coordX = rooms.get(m).coordX;
			int coordY = rooms.get(m).coordY;

			int width = rooms.get(m).width;
			int height = rooms.get(m).height;

			for(int y = coordY; y < coordY + height; y++) {
				for(int x = coordX; x < coordX + width; x++) {
					Cell cell = new Cell();
					cell.setTile(new StaticTiledMapTile(splitTilesFloor[floorTileX][floorTileY]));
					layer2.setCell(x, y, cell);
                    collisionLayerBoolean[x][y] = 1;
				}
			}

          
        }

		int endRoomCoordX = customRooms.get(1).coordX;
		int endRoomCoordY = customRooms.get(1).coordY;
		int endRoomWidth = customRooms.get(1).width;
		int endRoomHeight = customRooms.get(1).height;

		for(int y = endRoomCoordY; y < endRoomCoordY + endRoomHeight; y++) {
			for(int x = endRoomCoordX; x < endRoomCoordX + endRoomWidth; x++) {
				Cell cell = new Cell();
				cell.setTile(new StaticTiledMapTile(splitTilesFloor[floorTileX][floorTileY]));
				layer2.setCell(x, y, cell);
                collisionLayerBoolean[x][y] = 1;
			}
		}

		//----------------------- DRAW ROOMS ENDS -----------------------//

		layers.add(layer2);

		//----------------------- PLAYER/ITEM/MONSTERS - LAYER 3 -----------------------//

		playerSprite = new Texture("player_right.png");
		TextureRegion[][] splitTilesPlayer = TextureRegion.split(playerSprite, 48, 48);
		TiledMapTileLayer layer3 = new TiledMapTileLayer(numTilesHorizontal, numTilesVertical, 54, 54);

		player = new Player(
			(customRooms.get(1).centerCoordX), 
			(customRooms.get(1).centerCoordY) + 2,
			14,
			14,
			100,
			20,
			splitTilesPlayer[0][0]
		);

        previousCoordX = player.body.x;
        previousCoordY = player.body.y;

        initialCoordsX = player.body.x;
        initialCoordsY = player.body.y;

        portal = new Portal(
            (customRooms.get(1).centerCoordX) - 1, 
            (customRooms.get(1).centerCoordY),
            48,
            32
        );

		layers.add(layer3);

        //create final collsion layer
        for(int i=0; i< collisionLayerBoolean.length; i++) {
            for(int j=0; j< collisionLayerBoolean[i].length; j++) {
                    if(collisionLayerBoolean[i][j] == 0) {
                        finalCollisionLayer.add(new Rectangle(i*tileSize - tileSize, j*tileSize, tileSize, tileSize));
                    }
            }
        }


		renderer = new OrthogonalTiledMapRenderer(map);
	}

	@Override
	public void render () {
		ScreenUtils.clear(100f / 255f, 100f / 255f, 250f / 255f, 1f);

		if(counter < 1) {
			if(Room.startRoomLocationIndex == 0) {
				camera.position.x = player.body.x + player.width/2 + viewPortWidth/2 - 64;
				camera.position.y = player.body.y + player.height/2 + viewPortHeight/2 - 64;
			}
			else if(Room.startRoomLocationIndex == 1) {
				camera.position.x = player.body.x + player.width/2 - viewPortWidth/2 + 64;
				camera.position.y = player.body.y + player.height/2 + viewPortHeight/2 - 64;
			}
			else if(Room.startRoomLocationIndex == 2) {
				camera.position.x = player.body.x + player.width/2 + viewPortWidth/2 - 64;
				camera.position.y = player.body.y + player.height/2 - viewPortHeight/2 + 64;
			}
			else {
				camera.position.x = player.body.x + player.width/2 - viewPortWidth/2 + 64;
				camera.position.y = player.body.y + player.height/2 - viewPortHeight/2 + 64;
			}
		}

		counter++;
		if(((player.body.x + viewPortWidth/2 > 1920 - 16) && (player.body.y + viewPortHeight/2 > 1080)) || 
			((player.body.x - viewPortWidth/2 < 0) && (player.body.y + viewPortHeight/2 > 1080)) ||
			((player.body.x + viewPortWidth/2 > 1920 -16) && (player.body.y - viewPortHeight/2 < 0)) ||
			((player.body.x - viewPortWidth/2 < 0) && (player.body.y - viewPortHeight/2 < 0))) {
			
		}
		else if((player.body.x + viewPortWidth/2 > 1920 - 16) || (player.body.x - viewPortWidth/2 < 0)) {
			camera.position.y = player.body.y + player.height/2;
		}
		else if ((player.body.y + viewPortHeight/2 > 1080) || (player.body.y - viewPortHeight/2 < 0)) {
			camera.position.x = player.body.x + player.width/2;
		}
		else {
			camera.position.x = player.body.x + player.width/2;
			camera.position.y = player.body.y + player.height/2;
		}
	
		camera.update();
		batch.setProjectionMatrix(camera.combined);	
		batch.begin();
		// font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		renderer.setView(camera);
		renderer.render();
		batch.draw(portal.currentTexture, portal.coordX, portal.coordY);
		batch.draw(player.currentTexture, player.body.x, player.body.y);

		font.draw(batch, "Level: " + levelCounter.toString(), camera.position.x + viewPortWidth/2 - 65, camera.position.y + viewPortHeight/2 - 10);
		
		batch.end();


		boolean canMoveUp = true;
		boolean canMoveDown = true;
		boolean canMoveLeft = true;
		boolean canMoveRight = true;

		
		for(int i=0; i<finalCollisionLayer.size(); i++) {
				if(Intersector.intersectRectangles(player.body, finalCollisionLayer.get(i), new Rectangle())) {

						float pLeft = player.body.x;
						float pRight = player.body.x + player.body.width;
						float pTop = player.body.y + player.body.height;
						float pBot = player.body.y;

						float wLeft = finalCollisionLayer.get(i).x;
						float wRight = finalCollisionLayer.get(i).x + finalCollisionLayer.get(i).width;
						float wTop = finalCollisionLayer.get(i).y + finalCollisionLayer.get(i).height;
						float wBot = finalCollisionLayer.get(i).y;


						if(animationPortalCounter < 6*9) {
								animationPortalCounter++;
								if(animationPortalCounter % 9 == 0 ) {
										portal.currentTexture = portal.portal_animations.get(animationPortalCounter/9);
								}
						}
						else {
								animationPortalCounter = 0;
						}
						
						//left
						if(pLeft < wRight && pRight > wRight) {
								canMoveLeft = false;
								
						}
						//right
						if(pLeft < wLeft && pRight > wLeft) {
								canMoveRight = false;
								
						}
						//up
						if(pTop > wBot && pBot < wBot) {
								canMoveUp = false;
								
						}
						//down
						if(pTop > wTop && pBot < wTop) {
								canMoveDown = false;
								
						}

						if(!canMoveLeft && !canMoveRight && !canMoveUp && !canMoveDown) {
								if(player.body.x == previousCoordX && player.body.y == previousCoordY) {
										player.body.x = initialCoordsX;
										player.body.y = initialCoordsY;
								}
								else {
										player.body.x = previousCoordX;
										player.body.y = previousCoordY;
								}
						}

						
				}
				
		}
		

		if(controller.left && canMoveLeft){	
			player.body.x -= player.speed;
			playerSprite = new Texture("player_left.png");  
			TextureRegion[][] splitTilesPlayer = TextureRegion.split(playerSprite, 48, 48);
			player.currentTexture = splitTilesPlayer[0][0];
		} 
		if(controller.right && canMoveRight){
			player.body.x += player.speed;
			playerSprite = new Texture("player_right.png");	
			TextureRegion[][] splitTilesPlayer = TextureRegion.split(playerSprite, 48, 48);
			player.currentTexture = splitTilesPlayer[0][0];
		} 
		if(controller.down && canMoveDown){
			player.body.y -= player.speed;
		} 
		if(controller.up && canMoveUp){
			player.body.y += player.speed;
		} 

		//check for collision with portal
		if(Intersector.intersectRectangles(player.body, portal.leftRect, new Rectangle()) && !portalTouched) {
				portalTouched = true;
				//get portal variation
				String portalVariation = portal.variation;

				System.out.println(portalVariation);
				if(portalVariation.equals("nature_ice")) {
					levelVariationName = "nature";
				}
				if(portalVariation.equals("nature_fire")) {
					levelVariationName = "nature";
				}
				if(portalVariation.equals("nature_water")) {
					levelVariationName = "nature";
				}
				if(portalVariation.equals("ice_fire")) {
					levelVariationName = "ice";
				}
				if(portalVariation.equals("ice_water")) {
					levelVariationName = "ice";
				}
				if(portalVariation.equals("fire_water")) {
					levelVariationName = "fire";
				}
				System.out.println(levelVariationName);

				this.create();
		}

		if(Intersector.intersectRectangles(player.body, portal.rightRect, new Rectangle()) && !portalTouched) {
				portalTouched = true;
				
				String portalVariation = portal.variation;
				if(portalVariation.equals("nature_ice")) {
					levelVariationName = "ice";
				}
				if(portalVariation.equals("nature_fire")) {
					levelVariationName = "fire";
				}
				if(portalVariation.equals("nature_water")) {
					levelVariationName = "water";
				}
				if(portalVariation.equals("ice_fire")) {
					levelVariationName = "fire";
				}
				if(portalVariation.equals("ice_water")) {
					levelVariationName = "water";
				}
				if(portalVariation.equals("fire_water")) {
					levelVariationName = "water";
				}
				//System.out.println(levelVariationName);

				this.create();
		}

	
	}

	
}

