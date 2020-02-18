import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * @author Divyam Garg
 * Trucks can create CommandCentres, they get destroyed once they do
 */
public class Truck extends Unit{
	
	/**
	 * Speed of truck in pixels/second
	 */
	public static final double SPEED = 0.25;
	
	/**
	 * path of image of Truck
	 */
	public static final String IMAGE = "assets/units/truck.png";
	
	/**
	 * List of actions Truck can perform
	 */
	public static final String LIST_ACTIONS = "1- Create Command Centre";
	
	/**
	 * Time taken to Train a CommandCentre
	 */
	public static final int TRAIN_TIME = 15;
	
	/**
	 * Cost of building CommandCentre in amount of metal
	 */
	public static final int COMMCENTRE_COST = 0;
	private double buildTimeLeft;

	/**
	 * Constructors
	 * @param pos position of Truck
	 * @throws SlickException
	 */
	public Truck(Position pos) throws SlickException {
		super(IMAGE, pos, SPEED, LIST_ACTIONS);
	}
	
	/**
	 *updates the state of Truck
	 */
	public void update(World world) {
		super.update(world);
		
		//check if this has been instructed to build anything
		getNewBuildOrder(world);
		
		//if yes, build it
		executeBuildOrder(world);
		
	}
	
	
	/**
	 * checks if this has been asked to make a new building, starts building if it has
	 * @param world world object
	 */
	private void getNewBuildOrder(World world) {
		
		//builder can't build if this not selected or while already building
		if ((this.getIsSelected()) && (!this.getIsBuilding())) {
			Input input = world.getInput();
			
			//check which unit needs to be trained
			if (input.isKeyDown(Input.KEY_1)) {
					
				TextDisplay display = world.getTextDisplay();
				
				//train if there's sufficient metal
				if (display.getAmountMetal() >= COMMCENTRE_COST && !world.isPositionOccupied(this.getPos().getX(), this.getPos().getY())) {
					display.setAmountMetal(display.getAmountMetal() - COMMCENTRE_COST);
					this.setIsBuilding(true);
					buildTimeLeft = TRAIN_TIME;
				}
			}
		}
	}
	
	/**
	 * if this is building something, keeps track of time to finish build, and once finished
	 *actually creates the new building object in the world
	 * @param world world object
	 */
	private void executeBuildOrder(World world) {
			if (this.getIsBuilding()) {
			
			//keep track of how long before build complete
			buildTimeLeft -= world.getDelta()/NUMBER_MILISEC_IN_SEC;
			
			//Command Centre is ready 
			if (buildTimeLeft <= 0) {
	
				//Make new Command Centre
				try {
					this.build("CommandCentre", world);
				} catch (SlickException e) {
					e.printStackTrace();
				}
	
				//No building is being built anymore
				setIsBuilding(false);
				buildTimeLeft = 0;
				
				
				//destroy truck after creating Command centre
				ArrayList<GameObject> gameObjects = world.getGameObjects();
	
				world.removeGameObject(this);
			}
		}
	}
}
