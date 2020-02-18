import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * @author Divyam Garg
 * Factory building. Can create trucks
 */
public class Factory extends Building{
	
	/**
	 * Cost to train 1 Truck in amount of metal
	 */
	public static final int TRUCK_COST = 150;
	
	/**
	 * Path to Factory's image
	 */
	public static final String IMAGE_PATH = "assets/buildings/factory.png";
	
	/**
	 * List of actions factory can perform
	 */
	public static final String LIST_ACTIONS = "1- Create Truck";
	private boolean unitTraining;
	private double trainTimeLeft;	
	
	/**
	 * Constructor
	 * @param pos position of factory
	 * @throws SlickException
	 */
	public Factory(Position pos) throws SlickException {
		super(IMAGE_PATH, pos, LIST_ACTIONS);
		this.unitTraining = false;
		this.trainTimeLeft = 0;
	}

	/**
	 *update Factory
	 */
	@Override
	public void update(World world) {
		super.update(world);
		
		//check if this got instructed to train a Truck
		getNewTrainOrder(world);
		
		//execute the order if any
		executeNewTrainOrder(world);
	
	}
	
	/**
	 * checks if factory was asked to train a truck, if so, starts training it
	 * @param world world object
	 */
	private void getNewTrainOrder(World world) {

		//Can only train units if this is selected and a unit isnt already training
		if ((this.getIsSelected()) && (!unitTraining)) {
			Input input = world.getInput();
			
			//check which unit needs to be trained
			if (input.isKeyDown(Input.KEY_1)) {
				
				TextDisplay display = world.getTextDisplay();

				//train if there's sufficient metal
				if (display.getAmountMetal() >= TRUCK_COST) {
					display.setAmountMetal(display.getAmountMetal() - TRUCK_COST);
					unitTraining = true;
					trainTimeLeft = TRAIN_TIME;
				}
			}
		}
	}
	
	
	/**
	 *keeps track of how long remains until truck trained, actually creates a new object and puts it in the world 
	 * @param world world object
	 */
	private void executeNewTrainOrder(World world) {
		
		//Check if unit ready
		if (unitTraining) {
			trainTimeLeft -= world.getDelta()/NUMBER_MILISEC_IN_SEC;
			
			//Unit is ready 
			if (trainTimeLeft <= 0) {
				
				System.out.println("here");
				//Make new unit
				try {
					this.trainUnit("Truck", world);
				} catch (SlickException e) {
					e.printStackTrace();
				}
				
				//No unit is being trained anymore
				unitTraining = false;
				trainTimeLeft = 0;
			}
		}
	}
	
	
}
