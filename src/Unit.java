import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * @author Divyam Garg
 * Units class is parent to the 4 kinds of units in the game. This class handles all the movement and building of the units
 */
public abstract class Unit extends ActionableObject{
	
	/**
	 * Path to image of Highlight of units
	 */
	public static final String HIGHLIGHT = "assets/highlight.png";
	private double speed;
	private boolean isBuilding;
	private double targetX;
	private double targetY;

	/**
	 * Constructors
	 * @param image_path path of unit's image
	 * @param pos position of unit
	 * @param speed unit's speed
	 * @param listActions list of actions unit can perform
	 * @throws SlickException
	 */
	public Unit(String image_path, Position pos, double speed, String listActions) throws SlickException {
		super(image_path, pos, HIGHLIGHT, listActions);
		this.speed = speed;
		this.targetX = pos.getX();
		this.targetY = pos.getY();
		this.isBuilding = false;
	}
	
	/**
	 *update Unit, this function handles all the movement of unit
	 */
	public void update(World world) {
		super.update(world);

		//Remaining code in this update takes care of movement of units
		
		//can't move if this is building something
		if (isBuilding) {
			resetTarget();
		}
		else {
			
			//cant change movement target unless this is selected
			if (this.getIsSelected()) {			
				Input input = world.getInput();
				// If the mouse button is being clicked, set the target to the cursor location
				if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
					targetX = world.getCamera().screenXToGlobalX(input.getMouseX());
					targetY = world.getCamera().screenYToGlobalY(input.getMouseY());
				}
			}
			
			//If we're close to our target, reset to our current position
			if (this.getPos().distance(new Position(targetX, targetY)) <= speed) {
				resetTarget();
			} else {
				double x = this.getPos().getX();
				double y = this.getPos().getY();
				
				// Calculate the appropriate x and y distances
				double theta = Math.atan2(targetY - y, targetX - x);
				double dx = (double)Math.cos(theta) * world.getDelta() * this.speed;
				double dy = (double)Math.sin(theta) * world.getDelta() * this.speed;
				
				// Check the tile is free before moving; otherwise, we stop moving
				if (world.isPositionFree(x + dx, y + dy)) {
					this.setPos(x+dx, y+dy);
				} else {
					resetTarget();
				}
			}
		}
	}
	
	
	/**
	 * builds a new building of type buildingtype at the location of this
	 * @param buildingType The kind of building to build
	 * @param world world object
	 * @throws SlickException
	 */
	public void build(String buildingType, World world) throws SlickException {
		Building newBuilding=null;
		ArrayList<GameObject> newObjects = world.getNewObjects();
		double x = this.getPos().getX();
		double y = this.getPos().getY();
		
		//Create the correct type of building
		if (buildingType == "Factory") {
			newBuilding = new Factory(new Position(x, y));
		}
		else if (buildingType == "CommandCentre") {
			
			newBuilding = new CommandCentre(new Position(x, y));
		}
		else {
			System.out.println("newUnit needed to be one of the above values, check why not");
			System.exit(1);
		}
		
		//add it to new objects, world takes care of adding these to game objects
		world.addNewGameObject(newBuilding);
	}
	
	/**
	 * instructs unit to move to position
	 * @param pos position unit should move to
	 */
	public void moveTo(Position pos) {
		targetX = pos.getX();
		targetY = pos.getY();
	}
	
	/**
	 * Reset the target to units current position
	 */
	private void resetTarget() {
		targetX = this.getPos().getX();
		targetY = this.getPos().getY();
	}
	
	//Getters and Setters ---------------------
	
	/**
	 * @return the speed of unit
	 */
	public double getSpeed() {
		return speed;
	}
	
	/**
	 * @param speed new speed of unit
	 */
	public void setSpeed(double speed) {
		if (speed >= 0) {
			this.speed = speed;
		}
	}
	
	/**
	 * @return the value of isBuilding, true or false
	 */
	public boolean getIsBuilding() {
		return isBuilding;
	}
	
	/**
	 * @param status the new value of isBuilding
	 */
	public void setIsBuilding(boolean status) {
		this.isBuilding = status;
	}
	// -------------------------------------------
}
