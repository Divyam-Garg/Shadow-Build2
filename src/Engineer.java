import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * @author Divyam Garg
 * Engineer unit. Mines Resources
 */
public class Engineer extends Unit{
	
	/**
	 * Speed with which Engineer moves in pixel/second
	 */
	public static final double SPEED = 0.1;
	
	/**
	 * Path for Engineer's image
	 */
	public static final String IMAGE = "assets/units/engineer.png";
	
	/**
	 * List of actions engineer can perform. None!
	 */
	public static final String LIST_ACTIONS="";
	
	/**
	 * Time it takes for engineer to mine carryCapacity amount of resources in seconds
	 */
	public static final int MINING_TIME = 5;
	private static int carryCapacity=2;
	private Resource currResource;
	private CommandCentre currCommCentre;
	private int amountResource;
	private String typeResource;
	private double miningTimeLeft;
	private boolean goingToResource;

	
	/**
	 * @param pos position of Engineer 
	 * @throws SlickException
	 */
	public Engineer(Position pos) throws SlickException {
		super(IMAGE, pos, SPEED, LIST_ACTIONS);
		this.currResource = null;
		this.currCommCentre = null;
		this.amountResource = 0;
		this.miningTimeLeft = 0;
	}
	
	/**
	 * Implement the Engineers Resrouce mining functionalities
	 * update Engineer
	 */
	public void update(World world) {
		super.update(world);
		
		//check if engineer is being sent somewhere
		if (this.getIsSelected()) {
			Input input = world.getInput();
			
			if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
				double clickX = world.getCamera().screenXToGlobalX(input.getMouseX());
				double clickY = world.getCamera().screenYToGlobalY(input.getMouseY());
				
				//Check if engineer was sent to resource
				if (clickOnResource(clickX, clickY, world)) {
					
					//store the resource assigned to engineer
					currResource = clickedResource(clickX, clickY, world);
					miningTimeLeft = MINING_TIME;
					goingToResource = true;

					//go to the resource
					this.moveTo(currResource.getPos());
					
					
					if (currResource == null) {
						System.out.println("currResource shouldnt be null, we checked the click was on a resource above");
						System.exit(1);
					}
				}
			}
		}
		
		//engineer has reached commCentre, drop resources and go back to resource
		if (this.nearCurrCommCentre(world) && !goingToResource) {
			dropResources(world);
			if (currResource != null) {
				moveTo(currResource.getPos());
				goingToResource = true;
				miningTimeLeft = MINING_TIME;
			}
		}

		//Engineer has reached mine, mine resources and go to commCentre
		else if (nearCurrResource() && goingToResource) {
			miningTimeLeft -= (world.getDelta()/NUMBER_MILISEC_IN_SEC);
			if (miningTimeLeft <= 0) {
				gatherResources(world);

				//If resource has been completely depleted, stop going to it
				if (currResource.getAmount() == 0) {
					currResource = null;
				}
				
				//find nearest commcentre, make that currcommcentre
				currCommCentre = getNearestCommCentre(world);
				moveTo(currCommCentre.getPos());
				goingToResource = false;
			}
		}
	}
	
	/**
	 * engineer drops off any resources its carrying at command centre, increases count in textDisplay
	 * @param world world object
	 */
	private void dropResources(World world) {
		
		TextDisplay display = world.getTextDisplay();
		
		//update the appropriate type of mine
		if (this.typeResource == "Metal") {
			display.setAmountMetal(display.getAmountMetal() + this.amountResource);
		}
		else {
			display.setAmountUnobtainium(display.getAmountUnobtainium() + this.amountResource);
		}
		
		//engineer doesnt have any resources anymore
		this.amountResource = 0;
		this.typeResource = "";
	}
	
	
	/**
	 * reduces amount of resource in currResource mine, engineer starts carrying some of the resource 
	 * @param world world object
	 */
	private void gatherResources(World world) {
		
		//get the resources
		this.amountResource = min(carryCapacity, currResource.getAmount());
		
		if (currResource instanceof MetalMine) {
			this.typeResource = "Metal";
		}
		else this.typeResource = "Unobtainium";
		
		//reduce amount of resource in mine
		currResource.setAmount(currResource.getAmount()-this.amountResource);
	}
	
	
	/**
	 * returns the smaller of 2 integers
	 * @param n1 first integer
	 * @param n2 second integer
	 * @return
	 */
	private int min(int n1, int n2) {
	
		if (n1<=n2) {
			return n1;
		}
		return n2;
	}
	
	
	/**
	 * @param world world object
	 * @return true if this is near the comm centre this is dropping off resources at 
	 */
	private boolean nearCurrCommCentre(World world) {
		if (currCommCentre == null) {
			return false;
		}
		
		//returns true if this is close enough as well
		return currCommCentre.getPos().distance(this.getPos()) <= CLICK_RANGE;
	}
	
	
	
	/**
	 * @param world world object
	 * @return the command centre nearest to this
	 */
	private CommandCentre getNearestCommCentre(World world) {
		
		ArrayList<GameObject> gameObjects = world.getGameObjects();
		CommandCentre nearest = null;
		double min_dist = Double.MAX_VALUE;
		
		for (GameObject gameObject: gameObjects) {
			
			//only compare command centres
			if (gameObject instanceof CommandCentre) {
				
				//get the one closest to this
				if (gameObject.getPos().distance(this.getPos()) <= min_dist) {
					nearest = (CommandCentre) gameObject;
					min_dist = gameObject.getPos().distance(this.getPos());
				}
			}
		}
		
		if (nearest == null) {
			System.out.println("Theres always a commCentre in game,"
					+ " hence always a nearest commCentre, this is an impossible state");
			System.exit(1);
		}
		return nearest;
	}
	
	
	/**
	 * @return true if this is close to currResource, false otherwise
	 */
	private boolean nearCurrResource() {
		if (currResource == null) {
			return false;
		}
		return (currResource.getPos().distance(this.getPos()) <= CLICK_RANGE);
	}

	
	
	/**
	 * returns the resource that was clicked on
	 * @param clickX global x coordinate of mouse click
	 * @param clickY global y coordinate of mouse click
	 * @param world world object
	 * @return
	 */
	private Resource clickedResource(double clickX, double clickY, World world) {
		ArrayList<GameObject> gameObjects = world.getGameObjects();
		
		for (GameObject gameObject: gameObjects) {
			if (gameObject instanceof Resource) {
				if (gameObject.getPos().distance(new Position(clickX, clickY)) <= CLICK_RANGE) {
					Resource res = (Resource) gameObject;
					return res;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * returns if the passed in click was on a resource
	 * @param clickX global x coordinate of mouse click
	 * @param clickY global y coordinate of mouse click
	 * @param world world object
	 * @return
	 */
	private boolean clickOnResource(double clickX, double clickY, World world) {
		ArrayList<GameObject> gameObjects = world.getGameObjects();
		
		for (GameObject gameObject: gameObjects) {
			if (gameObject instanceof Resource) {
				if (gameObject.getPos().distance(new Position(clickX, clickY)) <= CLICK_RANGE) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * Increases carrycapacity by 1, used by pylons when they're activated
	 */
	public static void incrementCarryCapacity() {
		carryCapacity++;
	}
}
