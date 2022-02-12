package com.mygdx.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
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
	private Texture playerSprite;
	private Texture texture;
	private BitmapFont font;
	private SpriteBatch batch;
	public KeyboardController controller;

	private Texture bucketImage;
	

	public static int numTilesHorizontal = 120;
	public static int numTilesVertical = 68;

	public Player player;

	float w;
	float h;

	@Override
	public void create () {

		controller = new KeyboardController();
		Gdx.input.setInputProcessor(controller);

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		int corridorTileX = 1;
		int corridorTileY = 8;

		int floorTileX = 4;
		int floorTileY = 4;
		
		camera = new OrthographicCamera();
		// camera.setToOrtho(false, (w/h)*320, 320);
		camera.setToOrtho(false, 1920, 1080);
		camera.update();

		// cameraController = new OrthoCamController(camera);
		// Gdx.input.setInputProcessor(cameraController);

		font = new BitmapFont();
		batch = new SpriteBatch();

		//SCALE IMAGES/SPRITES
		Pixmap pixmap200 = new Pixmap(Gdx.files.internal("bucket.png"));
		Pixmap pixmap100 = new Pixmap(54, 54, pixmap200.getFormat());
		pixmap100.drawPixmap(pixmap200,
						0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
						0, 0, pixmap100.getWidth(), pixmap100.getHeight()
		);

		bucketImage = new Texture(pixmap100);
		
		tiles = new Texture("DarkCastle.png");

		TextureRegion[][] splitTiles = TextureRegion.split(tiles, 16, 16);
		map = new TiledMap();

		MapLayers layers = map.getLayers();

		//----------------------- WALL SECTION - LAYER 1 -----------------------//

		int tileCoordX = 0;
		int tileCoordY = 0;
		
		TiledMapTileLayer layer1 = new TiledMapTileLayer(numTilesHorizontal, numTilesVertical, 16, 16);
		for (int x = 0; x < numTilesHorizontal; x++) {
			for (int y = 0; y < numTilesVertical; y++) {
				// int ty = (int)(Math.random() * splitTiles.length);
				// int tx = (int)(Math.random() * splitTiles[ty].length);
				
				//----------------------- SELECT TYPE OF WALL -----------------------//
				if (Math.random() <= 1) {
					tileCoordX = 0;
					tileCoordY = 0;
				}
				else if (Math.random() < 0.95) {
					tileCoordX = 0;
					tileCoordY = 1;
				}
				else {
					tileCoordX = 0;
					tileCoordY = 0;
				}
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

		tilesFloor = new Texture("Dungeon_Tileset.png");
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

			//----------------------- CORIDOR SECTION -----------------------//
			tilesCorridor = new Texture("Dungeon_Tileset.png");
			TextureRegion[][] splitTilesCorridor = TextureRegion.split(tilesFloor, 16, 16);

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
						}
					}
					//down-1
					else if(x1 == chosenConnectionX && chosenConnectionY < y1) {
						for(int k = chosenConnectionY; k < y1; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(x1, k, cell);
						}
					}
					//right-1
					else if(y1 == chosenConnectionY && chosenConnectionX > x1) {
						for(int k = x1; k < chosenConnectionX+1; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(k, y1, cell);
						}
					}
					//left-1
					else if(y1 == chosenConnectionY && chosenConnectionX < x1) {
						for(int k = chosenConnectionX; k < x1; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(k, y1, cell);
						}
					}


					//up-2
					if(x2 == chosenConnectionX && chosenConnectionY > y2) {
						for(int k = y2; k < chosenConnectionY; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(x2, k, cell);
						}
					}
					//down-2
					else if(x2 == chosenConnectionX && chosenConnectionY < y2) {
						for(int k = chosenConnectionY; k < y2; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(x2, k, cell);
						}
					}
					//right-2
					else if(y2 == chosenConnectionY && chosenConnectionX > x2) {
						for(int k = x2; k < chosenConnectionX+1; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(k, y2, cell);
						}
					}
					//left-2
					else if(y2 == chosenConnectionY && chosenConnectionX < x2) {
						for(int k = chosenConnectionX; k < x2; k++) {
							Cell cell = new Cell();
							cell.setTile(new StaticTiledMapTile(splitTilesCorridor[corridorTileX][corridorTileY]));
							layer2.setCell(k, y2, cell);
						}
					}	
				}

			}
			
			
		}
		//ENDS HERE

		//----------------------- DRAW ROOMS STARTS -----------------------//

		int firstRoomCoordX = customRooms.get(0).coordX;
		int firstRoomCoordY = customRooms.get(0).coordY;
		int firstRoomWidth = customRooms.get(0).width;
		int firstRoomHeight = customRooms.get(0).height;

		for(int y = firstRoomCoordY; y < firstRoomCoordY + firstRoomHeight; y++) {
			for(int x = firstRoomCoordX; x < firstRoomCoordX + firstRoomWidth; x++) {
				Cell cellFirst = new Cell();
				cellFirst.setTile(new StaticTiledMapTile(splitTilesFloor[4][4]));
				layer2.setCell(x, y, cellFirst);

			}
		}

		for (int i = 1; i < num_of_rooms; i++) {
			int coordX = rooms.get(i).coordX;
			int coordY = rooms.get(i).coordY;

			int width = rooms.get(i).width;
			int height = rooms.get(i).height;

			for(int y = coordY; y < coordY + height; y++) {
				for(int x = coordX; x < coordX + width; x++) {
					Cell cell = new Cell();
					cell.setTile(new StaticTiledMapTile(splitTilesFloor[floorTileX][floorTileY]));
					layer2.setCell(x, y, cell);
			
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
				cell.setTile(new StaticTiledMapTile(splitTilesFloor[0][3]));
				layer2.setCell(x, y, cell);
			}
		}

		//----------------------- DRAW ROOMS ENDS -----------------------//

		layers.add(layer2);

		//----------------------- PLAYER/ITEM/MONSTERS - LAYER 3 -----------------------//
		// player = new Texture("player_right.png");
		// TextureRegion[][] splitTilesPlayer = TextureRegion.split(player, 48, 48);
		// TiledMapTileLayer layer3 = new TiledMapTileLayer(numTilesHorizontal, numTilesVertical, 16, 16);
		// Cell cell = new Cell();
		// cell.setTile(new StaticTiledMapTile(splitTilesPlayer[0][0]));
		// layer3.setCell(customRooms.get(0).centerCoordX - 1, customRooms.get(0).centerCoordY, cell);

		playerSprite = new Texture("player_right.png");
		TextureRegion[][] splitTilesPlayer = TextureRegion.split(playerSprite, 48, 48);
		TiledMapTileLayer layer3 = new TiledMapTileLayer(numTilesHorizontal, numTilesVertical, 54, 54);

		player = new Player(
			(customRooms.get(0).centerCoordX), 
			(customRooms.get(0).centerCoordY),
			16,
			16,
			100,
			20,
			splitTilesPlayer[0][0]
		);

		layers.add(layer3);

		renderer = new OrthogonalTiledMapRenderer(map);
	}

	@Override
	public void render () {
		ScreenUtils.clear(100f / 255f, 100f / 255f, 250f / 255f, 1f);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		batch.draw(bucketImage, player.body.x, player.body.y);
		batch.end();

		// camera.position.x = player.body.x;
    // camera.position.y = player.body.y;
    // camera.update();

		if(controller.left){
			player.body.x -= 1;
			// playerSprite = new Texture("player_right.png");
			// TextureRegion[][] splitTilesPlayer = TextureRegion.split(playerSprite, 48, 48);
		} 
		if(controller.right){
			player.body.x += 1;
		} 
		if(controller.down){
			player.body.y -= 1;
		} 
		if(controller.up){
			player.body.y += 1;
		} 
	}

	public double calculateDistanceBetweenPoints(
	double x1, 
	double y1, 
	double x2, 
	double y2) {       
    return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
}
}
