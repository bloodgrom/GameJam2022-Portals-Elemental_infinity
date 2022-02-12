package com.mygdx.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
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


// public class MyGdxGame extends ApplicationAdapter {

// 	SpriteBatch batch;
// 	Texture img;
	
// 	@Override
// 	public void create () {
// 		batch = new SpriteBatch();
// 		// img = new Texture("badlogic.jpg");
// 	}

// 	@Override
// 	public void render () {
// 		ScreenUtils.clear(1, 0, 0, 1);
// 		batch.begin();
// 		// batch.draw(img, 0, 0);
// 		batch.end();
// 	}
	
// 	@Override
// 	public void dispose () {
// 		batch.dispose();
// 		// img.dispose();
// 	}
// }

public class MyGdxGame extends ApplicationAdapter {

	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	//private OrthoCamController cameraController;
	private AssetManager assetManager;
	private Texture tiles;
	private Texture tilesFloor;
	private Texture tilesCorridor;
	private Texture texture;
	private BitmapFont font;
	private SpriteBatch batch;

	public static int numTilesHorizontal = 120;
	public static int numTilesVertical = 68;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		// camera.setToOrtho(false, (w/h)*320, 320);
		camera.setToOrtho(false, 1920, 1080);
		camera.update();

		// cameraController = new OrthoCamController(camera);
		// Gdx.input.setInputProcessor(cameraController);

		font = new BitmapFont();
		batch = new SpriteBatch();

		
		tiles = new Texture("DarkCastle.png");

		TextureRegion[][] splitTiles = TextureRegion.split(tiles, 16, 16);
		map = new TiledMap();

		MapLayers layers = map.getLayers();

		//----------------------- WALL SECTION -----------------------//

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
		
		//----------------------- ROOM GENERATION -----------------------//
		int num_of_rooms = (int) (Math.random()*(Room.MAX_NUM_ROOMS-Room.MIN_NUM_ROOMS)) + Room.MIN_NUM_ROOMS;
		ArrayList<Room> rooms = new ArrayList<Room>();

		for(int i = 0; i < num_of_rooms; i++) {

			//generate width and height for room
			int roomWidth = (int) (Math.random()*(Room.MAX_WIDTH-Room.MIN_WIDTH)) + Room.MIN_WIDTH;
			int roomHeight = (int) (Math.random()*(Room.MAX_HEIGHT-Room.MIN_HEIGHT)) + Room.MIN_HEIGHT;

			boolean intersect = true;

			while(intersect) {
				int coordX = (int) (Math.random()*((numTilesHorizontal - roomWidth - 1) - 1)) + 1;
				int coordY = (int) (Math.random()*((numTilesVertical - roomHeight - 1) - 1)) + 1;
				
				Room newRoom = new Room(coordX, coordY, roomWidth, roomHeight);

				if(!newRoom.checkRoomIntersects(rooms)) {
					intersect = false;
					rooms.add(newRoom);
					break;
				}
			}
		}


		//----------------------- FLOOR SECTION -----------------------//
		tilesFloor = new Texture("Dungeon_Tileset.png");
		TextureRegion[][] splitTilesFloor = TextureRegion.split(tilesFloor, 16, 16);

		TiledMapTileLayer layer2 = new TiledMapTileLayer(numTilesHorizontal, numTilesVertical, 16, 16);
		
		for(int i = 0; i < rooms.size(); i++) {
			int coordX = rooms.get(i).coordX;
			int coordY = rooms.get(i).coordY;

			int width = rooms.get(i).width;
			int height = rooms.get(i).height;

			for(int y = coordY; y < coordY + height; y++) {
				for(int x = coordX; x < coordX + width; x++) {

					Cell cell = new Cell();
					cell.setTile(new StaticTiledMapTile(splitTilesFloor[1][8]));
					layer2.setCell(x, y, cell);

				}
			}
		}

		//----------------------- COORIDOR SECTION -----------------------//
		tilesCorridor = new Texture("Dungeon_Tileset.png");
		TextureRegion[][] splitTilesCorridor = TextureRegion.split(tilesFloor, 16, 16);

		
		// CHANGE TO ALL ROOMS LATER
		for(int i = 0; i < rooms.size(); i++) {
			ArrayList<Room> roomsByDistance = new ArrayList<>();
			ArrayList<Double> roomsDistance = new ArrayList<>();

			for(int k = 0; k < rooms.size(); k++) {

				if(k != i) {
					int x1 = rooms.get(i).centerCoordX;
					int y1 = rooms.get(i).centerCoordY;
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

			//take between 1 and 4 closest rooms
			int numOfConnections = (int) (Math.random()*(4 - 1)) + 1;

			//draw corridors between room i and chosen rooms
			for(int z = 0; z < numOfConnections; z++) {
				int x1 = rooms.get(i).centerCoordX;
				int y1 = rooms.get(i).centerCoordY;

				int x2 = roomsByDistance.get(z).centerCoordX;
				int y2 = roomsByDistance.get(z).centerCoordY;

				Cell cell1 = new Cell();
				cell1.setTile(new StaticTiledMapTile(splitTilesCorridor[5][1]));
				layer2.setCell(x1, y1, cell1);

				Cell cell2 = new Cell();
				cell2.setTile(new StaticTiledMapTile(splitTilesCorridor[8][1]));
				layer2.setCell(x2, y2, cell2);
				
				
			}


			
			// Cell cell = new Cell();
			// cell.setTile(new StaticTiledMapTile(splitTilesCorridor[8][1]));
			// int tempX = rooms.get(i).centerCoordX;
			// int tempY = rooms.get(i).centerCoordY;
			// layer2.setCell(tempX, tempY, cell);
		}


		layers.add(layer2);

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
		batch.end();
	}

	public double calculateDistanceBetweenPoints(
	double x1, 
	double y1, 
	double x2, 
	double y2) {       
    return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
}
}
