import java.util.ArrayList;

import org.newdawn.slick.SlickException;

/**
 * @author Divyam Garg
 * Parent class for different kinds of buildings. All buildings can train Units
 */
public abstract class Building extends ActionableObject{
	
	/**
	 * Time taken in seconds to train any unit
	 */
	public static final int TRAIN_TIME = 5;
	
	/**
	 * Path to highlight image for all buildings
	 */
	public static final String HIGHLIGHT = "assets/highlight_large.png";
	
	/**
	 * @param image_path Path of image
	 * @param pos position of Building
	 * @param listActions list of actions building can perform
	 * @throws SlickException
	 */
	public Building(String image_path, Position pos, String listActions) throws SlickException {
		super(image_path, pos, HIGHLIGHT, listActions);
	}
	
	/**
	 *updates building
	 */
	public void update(World world) {
		super.update(world);
	}
	
	
	
	/**
	 * Creates a new object of unitType at the buildings position and puts it in the world 
	 * @param unitType Type of unit to train
	 * @param world world object
	 * @throws SlickException
	 */
	public void trainUnit(String unitType, World world) throws SlickException {
		ArrayList<GameObject> newObjects = world.getNewObjects();
		double x = this.getPos().getX();
		double y = this.getPos().getY();
		Unit newUnit=null;
		
		//Create the correct kind of unit according to unitType
		if (unitType == "Scout") {
			newUnit = new Scout(new Position(x, y));
		}
		else if (unitType == "Builder") {
			newUnit = new Builder(new Position(x, y));
		}
		else if (unitType == "Engineer") {
			newUnit = new Engineer(new Position(x, y));
		}
		else if (unitType == "Truck") {
			newUnit = new Truck(new Position(x, y));
		}
		else {
			System.out.println("newUnit needed to be one of the above values, check why not");
			System.exit(1);
		}
		
		//Add the new unit to the world
		world.addNewGameObject(newUnit);
	}
}
