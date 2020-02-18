import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * @author Divyam Garg
 * Builder Unit. Can create Factories
 */
public class Builder extends Unit{
	
	/**
	 * Builder's speed
	 */
	public static final double SPEED = 0.1;
	
	/**
	 * Image path for Builder's image
	 */
	public static final String IMAGE = "assets/units/builder.png";
	
	/**
	 * List of actions builder can perform, just 1 in builder's case
	 */
	public static final String LIST_ACTIONS = "1- Build Factory";
	
	/**
	 * Amount of metal required to build Factory
	 */
	public static final int FACTORY_COST = 100;
	
	/**
	 * Amount of time in seconds required to build factory
	 */
	public static final int FACTORY_TIME = 10;
	
	private double buildTimeLeft;
	
	/**
	 * Constructor
	 * @param pos position of Builder
	 * @throws SlickException
	 */
	public Builder(Position pos) throws SlickException {
		super(IMAGE, pos, SPEED, LIST_ACTIONS);
		this.buildTimeLeft = 0;
	}
	
	/**
	 *updates the builder for 1 frame
	 */
	@Override
	public void update(World world) {
		super.update(world);
		
		//check if this has been instructed to build anything
		getNewBuildOrder(world);
		
		//if yes, build it
		executeNewBuildOrder(world);

	}
	
	
	/**
	 * checks if this has been asked to make a new building, starts building if it has
	 * @param world world object
	 */
	private void getNewBuildOrder(World world) {
		if ((this.getIsSelected()) && (!this.getIsBuilding())) {
			Input input = world.getInput();
			
			//check which building needs to be built
			if (input.isKeyDown(Input.KEY_1)) {
					
				TextDisplay display = world.getTextDisplay();
				
				//train if there's sufficient metal and builder is on an unoccupied tile
				if (display.getAmountMetal() >= FACTORY_COST && !world.isPositionOccupied(this.getPos().getX(), this.getPos().getY())) {
					display.setAmountMetal(display.getAmountMetal() - FACTORY_COST);
					this.setIsBuilding(true);
					buildTimeLeft = FACTORY_TIME;
				}
			}
		}
	}
	
	
	/**
	 * if this is building something, keeps track of time to finish build, and once finished, actually creates the new building object in the world
	 * @param world world object
	 */
	private void executeNewBuildOrder(World world) {
		if (this.getIsBuilding()) {
					
			//keep track of how long before build complete
			buildTimeLeft -= world.getDelta()/NUMBER_MILISEC_IN_SEC;
			
			//Factory is ready 
			if (buildTimeLeft <= 0) {

				//Make new factory
				try {
					this.build("Factory", world);
				} catch (SlickException e) {
					e.printStackTrace();
				}

				//No building is being built anymore
				setIsBuilding(false);
				buildTimeLeft = 0;
			}
		}
	}
}
