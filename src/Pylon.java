import java.util.ArrayList;

import org.newdawn.slick.SlickException;
/**
 * @author Divyam Garg
 * Pylon building. Can be activated by units, increases Engineers carrying capacity
 */
public class Pylon extends Building{
	
	/**
	 * Path for image of an unactive Pylon
	 */
	public static final String UNACTIVE_IMAGE_PATH = "assets/buildings/pylon.png";
	
	/**
	 * Path for image of an active Pylon
	 */
	public static final String ACTIVE_IMAGE_PATH = "assets/buildings/pylon_active.png";
	
	/**
	 * List of actions Pylon can perform, none
	 */
	public static final String LIST_ACTIONS = "";
	private boolean active;
	
	/**
	 * Constructor
	 * @param pos position of Pylon
	 * @throws SlickException
	 */
	public Pylon(Position pos) throws SlickException {
	    super(UNACTIVE_IMAGE_PATH, pos, LIST_ACTIONS);
	    this.active = false;
	}

	/**
	 *updates the Pylon for one frame
	 */
	@Override
	public void update(World world) {
		super.update(world);
		
		//dont need to do anything once pylon is active
		if (!active) {
			
			//activate this if theres a unit nearby
			if (unitNearPylon(world)) {
				this.active = true;
				
				//allow engineers to carry one more resource
				Engineer.incrementCarryCapacity();
				
				//change
				try {
					this.setImage(ACTIVE_IMAGE_PATH);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//returns true if theres a unit within 32 pixels of this, else false
	/**
	 * @param world world object
	 * @return true if a unit is close to pylon, false otherwise
	 */
	private boolean unitNearPylon(World world) {
		ArrayList<GameObject> gameObjects = world.getGameObjects();
		
		//go over each gameobject, check if a unit is within 32 pixels
		for (GameObject gameObject: gameObjects) {
			if (gameObject instanceof Unit) {
				if (gameObject.getPos().distance(this.getPos()) <= CLICK_RANGE) {
					return true;
				}
			}
		}
		return false;
	}
}
