import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Divyam Garg
 * 
 * Some of this code is based on the sample solution for project 1 of this subject
 * 
 * This class should be used to contain all the different objects in your game world, and schedule their interactions.
 * 
 * You are free to make ANY modifications you see fit.
 * These classes are provided simply as a starting point. You are not strictly required to use them.
 * 
 * World Class, Stores all the objects in the game and manipulates them
 */
public class World {
	private static final String MAP_PATH = "assets/main.tmx";
	private static final String SOLID_PROPERTY = "solid";
		
	private ArrayList<GameObject> gameObjects;
	private ArrayList<GameObject> newObjects;
	private ActionableObject selectedObject;
	private TiledMap map;
	private Camera camera = new Camera();
	private TextDisplay textDisplay = new TextDisplay();
	private Input lastInput;
	private int lastDelta;
	
	// Getters and Setters ----------------------------------------------------
	/**
	 * @return the list of game objects in world
	 */
	public ArrayList<GameObject> getGameObjects() {
		return this.gameObjects;
	}
	
	/**
	 * @return the list of new objects that have been created in last update
	 */
	public ArrayList<GameObject> getNewObjects() {
		return this.newObjects;
	}
	
	/**
	 * @return the object that is currently selected
	 */
	public ActionableObject getSelectedObject() {
		return this.selectedObject;
	}
	
	/**
	 * set the value of the selected object
	 * @param selectedObject the GameObject currently selected 
	 */
	public void setSelectedObject(ActionableObject selectedObject) {
		this.selectedObject = selectedObject;
	}
	
	/**
	 * @return last input to the game
	 */
	public Input getInput() {
		return lastInput;
	}
	
	/**
	 * @return time lapsed since last frame in miliseconds
	 */
	public int getDelta() {
		return lastDelta;
	}
	
	/**
	 * @return the TextDisplay
	 */
	public TextDisplay getTextDisplay() {
		return textDisplay;
	}
	
	/**
	 * @return the Camera
	 */
	public Camera getCamera() {
		return camera;
	}
	
	/**
	 * @return maps width in pixels
	 */
	public double getMapWidth() {
		return map.getWidth() * map.getTileWidth();
	}
	
	/**
	 * @return maps height in pixels
	 */
	public double getMapHeight() {
		return map.getHeight() * map.getTileHeight();
	}
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for world
	 * @throws SlickException
	 */
	public World() throws SlickException {
		map = new TiledMap(MAP_PATH);
		gameObjects = new ArrayList<>();
		newObjects = new ArrayList<>();
	}
	
	
	/**
	 * @param input Input from the user
	 * @param delta Time passed since last frame
	 */
	public void update(Input input, int delta) {
		lastInput = input;
		lastDelta = delta;
		
		//check if the currently selected object, if any, has been deselected
		checkIfDeselected();

		camera.update(this);
		updateGameObjects();
		textDisplay.update(this);
	}
	
	/**
	 * @param g
	 * @throws SlickException
	 */
	public void render(Graphics g) throws SlickException {
		map.render((int)camera.globalXToScreenX(0),
				   (int)camera.globalYToScreenY(0));

		renderGameObjects();
		textDisplay.render(g);
	}
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return true if given coordinates are on a solid tile, else false
	 */
	public boolean isPositionFree(double x, double y) {
		int tileId = map.getTileId(worldXToTileX(x), worldYToTileY(y), 0);
		return !Boolean.parseBoolean(map.getTileProperty(tileId, SOLID_PROPERTY, "false"));
	}
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return true if given coordinates are on an occupied tile, else false
	 */
	public boolean isPositionOccupied(double x, double y) {
		int tileId = map.getTileId(worldXToTileX(x), worldYToTileY(y), 0);
		return Boolean.parseBoolean(map.getTileProperty(tileId, "occupied", "false"));
	}
	
	/**
	 *Checks if currently selected object has been deselected 
	 */
	private void checkIfDeselected() {
		
		Input input = this.getInput();
		
		//nothing to deselect if nothing is selected
		if (selectedObject == null) {
			return;
		}
		
		//deselect the last thing
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			this.selectedObject.setIsSelected(false);
			this.selectedObject = null;
			this.camera.followTarget(null);
		}
		
	}
	
	/**
	 *updates all game objects 
	 */
	private void updateGameObjects() {
		
		//update all game objects
		for (GameObject gameObject: gameObjects) {
			if (gameObject != null) {
				gameObject.update(this);
			}
		}
		
		
		//remove objects that no longer exist form gameObjects
		Iterator<GameObject> itr = gameObjects.iterator();
		
		while (itr.hasNext()) {
			GameObject object = itr.next();			
			if (object == null) {
				itr.remove();
			}
		}
		
		//add new GameObjects created in this update cycle to GameObjects
		//remove added objects from newObjects
		Iterator<GameObject> itr2 = newObjects.iterator();
		
		while (itr2.hasNext()) {
			GameObject newObject = itr2.next();			
			gameObjects.add(newObject);
			itr2.remove();
		}
	}
	
	//adds a new GameObject to world
	/**
	 * @param newObject The object to be added
	 */
	public void addNewGameObject(GameObject newObject) {
		this.newObjects.add(newObject);
	}
	
	//Removed a game object currently in world
	/**
	 * @param gameObject The object to be added
	 */
	public void removeGameObject(GameObject gameObject) {
		
		for (int i=0; i<gameObjects.size(); i++) {
			if (gameObjects.get(i) == gameObject) {
				gameObjects.set(i, null);
			}
		}
	}
	
	//Renders all game objects
	private void renderGameObjects() throws SlickException {
		ArrayList<GameObject> gameObjects = this.getGameObjects();
		if (selectedObject != null) {
			
			//Render the highlight of the selected object
			selectedObject.renderHighlight(this);
		}
		
		for (GameObject gameObject: gameObjects) {
			gameObject.render(this);			
		}
		

	}
	
	private int worldXToTileX(double x) {
		return (int)(x / map.getTileWidth());
	}
	private int worldYToTileY(double y) {
		return (int)(y / map.getTileHeight());
	}
	
	/*Reads the gameObjects initally placed on Map. method public
	 * so it can be called during initialization from App*/
	/**
	 * @param csv Path to the csv file to parse 
	 * @throws SlickException 
	 * @throws IOException
	 */
	public void readGameObjects(String csv) throws SlickException, IOException {
		Scanner scanner = new Scanner(new FileReader(csv));
		String input, name;
		String[] inputArray;
		double x, y;
		GameObject newObject = null;
		Position pos;
		
		while (scanner.hasNext()) {
			input = scanner.nextLine();
			inputArray = input.split(",");
			name = inputArray[0];
			x = Double.parseDouble(inputArray[1]);
			y = Double.parseDouble(inputArray[2]);

			pos = new Position(x,y);
			
			
			if (name.equals("command_centre")) {
				newObject = new CommandCentre(pos);
			}
			else if(name.equals("metal_mine")) {
				newObject = new MetalMine(pos);
			}
			else if (name.equals("unobtainium_mine")) {
				newObject = new UnobtainiumMine(pos);
			}
			else if (name.equals("pylon")) {
				newObject = new Pylon(pos);
			}
			else if (name.equals("engineer")) {
				newObject = new Engineer(pos);
			}

			if (newObject != null) {
				this.gameObjects.add(newObject);
			}
			else {
				throw new IOException("The entered name is a case not coded for according to given csv, add it in");
			}

		}
		scanner.close();
	}
}
