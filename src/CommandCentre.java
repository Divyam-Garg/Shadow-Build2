import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author Divyam Garg
 * CommandCentre building. This can create a variety of units
 */
public class CommandCentre extends Building {
	
	/**
	 * Cost of training 1 Scout
	 */
	public static final int SCOUT_COST = 5;
	
	/**
	 * Cost of training 1 Builder
	 */
	public static final int BUILDER_COST = 10;
	
	/**
	 * Cost of training 1 Engineer
	 */
	public static final int ENGINEER_COST = 20;
	
	/**
	 * The path to image of CommandCentre
	 */
	public static final String IMAGE_PATH = "assets/buildings/command_centre.png";
	
	/**
	 * List of actions CommandCentre can perform
	 */
	public static final String LIST_ACTIONS = "1- Create Scout\n2- Create Builder\n3- Create Engineer\n";
	private boolean unitTraining;
	private double trainTimeLeft;
	private String unitType;
	
	
	/**
	 * Constructor
	 * @param pos position of Command Centre
	 * @throws SlickException
	 */
	public CommandCentre(Position pos) throws SlickException {
		super(IMAGE_PATH, pos, LIST_ACTIONS);
		this.unitTraining = false;
		this.trainTimeLeft = 0;
		this.unitType = "";
	}
	
	
	/**
	 *udpate the CommandCentre
	 */
	@Override
	public void update(World world) {
		
		super.update(world);
		
		//check if command centre got instructed to train something
		getNewTrainOrder(world);
		
		//execute the order if any
		executeTrainOrder(world);

	}
	
	/**
	 * checks if command centre was asked to train something, starts training that
	 * @param world world Object
	 */
	private void getNewTrainOrder(World world) {

		//Can only train units if this is selected and a unit isnt already training
		if ((this.getIsSelected()) && (!unitTraining)) {
			Input input = world.getInput();

			boolean key1Down =  input.isKeyDown(Input.KEY_1);
			boolean key2Down =  input.isKeyDown(Input.KEY_2);
			boolean key3Down =  input.isKeyDown(Input.KEY_3);

			TextDisplay display = world.getTextDisplay();
			
			//check which unit needs to be trained
			if (key1Down) {

				//train if there's sufficient metal
				if (display.getAmountMetal() >= SCOUT_COST) {
					display.setAmountMetal(display.getAmountMetal() - SCOUT_COST);
					unitType = "Scout";
					unitTraining = true;
					trainTimeLeft = TRAIN_TIME;
				}
			}
			else if(key2Down) {

				if (display.getAmountMetal() >= BUILDER_COST) {
					display.setAmountMetal(display.getAmountMetal() - BUILDER_COST);
					unitType = "Builder";
					unitTraining = true;
					trainTimeLeft = TRAIN_TIME;
				}
			}
			else if(key3Down) {

				if (display.getAmountMetal() >= ENGINEER_COST) {
					display.setAmountMetal(display.getAmountMetal() - ENGINEER_COST);
					unitType = "Engineer";
					unitTraining = true;
					trainTimeLeft = TRAIN_TIME;
				}
			}
		}
	}
	
	/**
	 * keeps track of how long remains until unit trained, actually creates a new object and puts it in the world
	 * @param world world object
	 */
	private void executeTrainOrder(World world) {

		//Check if unit ready
		if (unitTraining) {

			trainTimeLeft -= world.getDelta()/NUMBER_MILISEC_IN_SEC;

			//Unit is ready 
			if (trainTimeLeft <= 0) {

				//Make new unit
				try {

					this.trainUnit(unitType, world);
				} catch (SlickException e) {
					e.printStackTrace();
				}
				
				//No unit is being trained anymore
				unitTraining = false;
				trainTimeLeft = 0;
				unitType = "";
			}
		}
	}
	
}
