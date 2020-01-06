package aamain;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import entities.*;
import event.Input;
import event.KeyListener;

public class Map extends Rect {
	public static final int TILE_SIZE = 100;
	public static final Image sandBackground = new Image("res/images/inProgress/sandBackground.jpg");

	int columns, rows;
	int mapSettingsPlaceholder;

	private Rect entityLayer;
	private Rect darknessLayer;

	//	ArrayList<SetEntity> setEntities = new ArrayList<SetEntity>(250);
	//	ArrayList<Entity> entities = new ArrayList<Entity>();

	PalmTree[] palmTrees;
	PalmTree[] smallPalmTrees;
	PlantedCarrot[] plantedCarrots;
	public CharacterEntity characterEntity;
	public Campfire campfire;

	public Map(int columns, int rows, int mapSettingsPlaceholder) {
		super(new GridLayout(columns, rows), null, false);

		this.columns = columns;
		this.rows = rows;
		this.mapSettingsPlaceholder = mapSettingsPlaceholder;

		entityLayer = new Rect(new CoordinatesLayout(), null, false);
		darknessLayer = new Rect(new EdgesLayout(), null, false);

		characterEntity = new CharacterEntity();
		palmTrees = new PalmTree[190];
		smallPalmTrees = new PalmTree[30];
		plantedCarrots = new PlantedCarrot[50];
		for (int i = 0; i < palmTrees.length; i++)
			palmTrees[i] = new PalmTree();
		for (int i = 0; i < smallPalmTrees.length; i++)
			smallPalmTrees[i] = new PalmTree();
		for (int i = 0; i < plantedCarrots.length; i++)
			plantedCarrots[i] = new PlantedCarrot();

		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				if ((i + j) % 2 == 0)
					add(new Rect(null, Color.GREEN_GRASS, false), new GridLayoutConstraints(i, j));
				else
					add(new Rect(null, Color.GREEN, false), new GridLayoutConstraints(i, j));
			}
		}

		entityLayer.add(characterEntity, new CoordinateConstraints(- this.x + Display.getWidth() * .5f - CharacterEntity.width * .5f, 
				- this.y + Display.getHeight() * .5f - CharacterEntity.height * .5f, CharacterEntity.width, CharacterEntity.height));
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			addNewLayer(entityLayer, new CoordinateConstraints(0, 0, this.w, this.h));

			for (int i = 0; i < palmTrees.length; i++) {
				entityLayer.add(palmTrees[i], new BottomCenterCoordinateConstraints(Util.RAND.nextFloat() * this.w, Util.RAND.nextFloat() * this.h, 
						PalmTree.WIDTH, PalmTree.HEIGHT));
			}

			for (int i = 0; i < smallPalmTrees.length; i++) {
				entityLayer.add(smallPalmTrees[i], new BottomCenterCoordinateConstraints(Util.RAND.nextFloat() * this.w, Util.RAND.nextFloat() * this.h, 
						PalmTree.WIDTH * .67f, PalmTree.HEIGHT * .67f));
			}

			for (int i = 0; i < plantedCarrots.length; i++) {
				entityLayer.add(plantedCarrots[i], new BottomCenterCoordinateConstraints(Util.RAND.nextFloat() * this.w, Util.RAND.nextFloat() * this.h,
						PlantedCarrot.WIDTH, PlantedCarrot.HEIGHT));
			}

			Input.addKeyListener(wKeyListener);
			Input.addKeyListener(aKeyListener);
			Input.addKeyListener(sKeyListener);
			Input.addKeyListener(dKeyListener);
		}
		else {
			Input.removeKeyListener(wKeyListener);
			Input.removeKeyListener(aKeyListener);
			Input.removeKeyListener(sKeyListener);
			Input.removeKeyListener(dKeyListener);
		}

		super.setVisible(visible);
	}

	public void addDarkness() {
		float x = 0, y = 0;

		if (campfire == null) {
			new YouDied("You be dead. You need campfire at night", "Map", 99);
		}
		else {
			x = campfire.getCenterX() - Campfire.LIGHT_FROM_CAMPFIRE_WIDTH * .5f;
			y = campfire.getBottomY() - Campfire.LIGHT_FROM_CAMPFIRE_HEIGHT * .5f;
		}

		darknessLayer.add(new Rect(Color.BLACK), new EdgesLayoutConstraints(EdgesLayout.Location.TOP, y));
		darknessLayer.add(new Rect(Color.BLACK), new EdgesLayoutConstraints(EdgesLayout.Location.RIGHT, this.w - (x + Campfire.LIGHT_FROM_CAMPFIRE_WIDTH)));
		darknessLayer.add(new Rect(Color.BLACK), new EdgesLayoutConstraints(EdgesLayout.Location.BOTTOM, this.h - (y + Campfire.LIGHT_FROM_CAMPFIRE_HEIGHT)));
		darknessLayer.add(new Rect(Color.BLACK), new EdgesLayoutConstraints(EdgesLayout.Location.LEFT, x));
		darknessLayer.add(new Rect(Campfire.lightFromCampfire), new EdgesLayoutConstraints(EdgesLayout.Location.LEFT, Campfire.LIGHT_FROM_CAMPFIRE_WIDTH));

		this.addNewLayer(darknessLayer, new CoordinateConstraints(0, 0, this.getWidth(), this.getHeight()));
	}

	public void removeDarkness() {
		darknessLayer.removeAll();
		this.removeLayer(darknessLayer);

	}

	public void campfireRemoved() {
		this.campfire = null;
	}

	KeyListener wKeyListener = new KeyListener(this, Keyboard.KEY_W) {

		@Override
		public void keyIsDown() {
			Map map = (Map)(client);
			float distance = CharacterModel.DEFAULT_SPEED / Main.currentFPS;
			if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_D)) {
				map.characterEntity.setCoordinates(characterEntity.x, characterEntity.y - (float)(Math.sqrt(.5f * distance * distance)), characterEntity.w, characterEntity.h);
				map.setCoordinates(x, y + (float)(Math.sqrt(.5f * distance * distance)), w, h);
			}
			else {
				map.characterEntity.setCoordinates(characterEntity.x, characterEntity.y - distance, characterEntity.w, characterEntity.h);
				map.setCoordinates(x, y + distance, w, h);
			}
		}

	};

	KeyListener aKeyListener = new KeyListener(this, Keyboard.KEY_A) {

		@Override
		public void keyIsDown() {
			//			if (characterEntity.getX()) { TODO don't let character move too close to edge
			//				
			//			}
			Map map = (Map)(client);
			float distance = CharacterModel.DEFAULT_SPEED / Main.currentFPS;
			if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S)) {
				map.characterEntity.setCoordinates(characterEntity.x  - (float)(Math.sqrt(.5f * distance * distance)), characterEntity.y, characterEntity.w, characterEntity.h);
				map.setCoordinates(x  + (float)(Math.sqrt(.5f * distance * distance)), y, w, h);
			}
			else {
				map.characterEntity.setCoordinates(characterEntity.x - distance, characterEntity.y, characterEntity.w, characterEntity.h);
				map.setCoordinates(x + distance, y, w, h);
			}
		}

	};

	KeyListener sKeyListener = new KeyListener(this, Keyboard.KEY_S) {

		//		@Override
		//		public void keyDepressed() {
		//			Map map = (Map)(client);
		//			float distance = CharacterModel.DEFAULT_SPEED / Main.currentFPS;
		//			if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_D)) {
		//				map.characterEntity.setCoordinates(characterEntity.x, characterEntity.y + (float)(Math.sqrt(.5f * distance * distance)), characterEntity.w, characterEntity.h);
		//				map.setCoordinates(x, y - (float)(Math.sqrt(.5f * distance * distance)), w, h);
		//			}
		//			else {
		//				map.characterEntity.setCoordinates(characterEntity.x, characterEntity.y + distance, characterEntity.w, characterEntity.h);
		//				map.setCoordinates(x, y - distance, w, h);
		//			}
		//		}

		@Override
		public void keyIsDown() {
			Map map = (Map)(client);
			float distance = CharacterModel.DEFAULT_SPEED / Main.currentFPS;
			if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_D)) {
				map.characterEntity.setCoordinates(characterEntity.x, characterEntity.y + (float)(Math.sqrt(.5f * distance * distance)), characterEntity.w, characterEntity.h);
				map.setCoordinates(x, y - (float)(Math.sqrt(.5f * distance * distance)), w, h);
			}
			else {
				map.characterEntity.setCoordinates(characterEntity.x, characterEntity.y + distance, characterEntity.w, characterEntity.h);
				map.setCoordinates(x, y - distance, w, h);
			}
		}

	};

	KeyListener dKeyListener = new KeyListener(this, Keyboard.KEY_D) {

		@Override
		public void keyIsDown() {
			Map map = (Map)(client);
			float distance = CharacterModel.DEFAULT_SPEED / Main.currentFPS;
			if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S)) {
				map.characterEntity.setCoordinates(characterEntity.x  + (float)(Math.sqrt(.5f * distance * distance)), characterEntity.y, characterEntity.w, characterEntity.h);
				map.setCoordinates(x  - (float)(Math.sqrt(.5f * distance * distance)), y, w, h);
			}
			else {
				map.characterEntity.setCoordinates(characterEntity.x + distance, characterEntity.y, characterEntity.w, characterEntity.h);
				map.setCoordinates(x - distance, y, w, h);
			}
		}

	};

	public void moveLeft(int distance) {
		x -= distance;
	}

	public void moveRight(int distance) {
		x += distance;
	}

	public void moveUp(int distance) {
		y -= distance;
	}

	public void moveDown(int distance) {
		y += distance;
	}

	public Rect getEntityLayer() {
		return entityLayer;
	}
}
