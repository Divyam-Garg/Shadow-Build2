import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author Divyam Garg
 * Class for GameObjects that can be selected and can perform some action
 */
public abstract class ActionableObject extends GameObject{
	
	/**
	 * Number of miliseconds in a second. Used to convert values of delta in miliseconds to seconds
	 */
	public static final double NUMBER_MILISEC_IN_SEC = 1000;
	/**
	 * The tolerance range. To select something you can click within 32 pixels of its centre.If something
	 * is within 32 pixels of something else, its considered to be coinciding
	 */
	public static final int CLICK_RANGE = 32;
	private boolean isSelected;
	private String highlightPath;
	private String listActions; 
	
	
	/**
	 * Constructor
	 * @param image_path path of the object
	 * @param pos position of object
	 * @param highlightPath path to the highlight image of object
	 * @param listActions list of actions object can perform
	 * @throws SlickException
	 */
	public ActionableObject(String image_path, Position pos, String highlightPath, String listActions) throws SlickException {
		super(image_path, pos);
		this.setHighlightPath(highlightPath);
		this.listActions = listActions;
	}
	
	/**
	 *updates the object for one frame
	 */
	public void update(World world) {
		
		//This update handles selection of Units and Buildings
		checkIfSelected(world);
	};
	
	
	/**
	 * Checks if the actionable object has been selected
	 * @param world world object
	 */
	private void checkIfSelected(World world) {
		

		Input input = world.getInput();
		
		//If left button was not pressed, nothing was selected
		if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			return;
		}
		
		double clickX = world.getCamera().screenXToGlobalX(input.getMouseX());
		double clickY = world.getCamera().screenYToGlobalY(input.getMouseY());

		//the click was too far away from this, this will not be selected
		if (!objectInSelectionRange(this, new Position(clickX, clickY))) {
			return;
		}
		
		//prioritize selection of units over buildings, if selectedObject is a unit, this will not get selected
		//if this is a building, then units have higher priority,
		//if this is a unit, then among units there's no priority, hence selectedObject remains same
		ActionableObject selectedObject = world.getSelectedObject();
		if (selectedObject instanceof Unit) {
			return;
		}

		//select this
		if (selectedObject != null) {
			selectedObject.setIsSelected(false);
		}
		this.isSelected = true;
		world.setSelectedObject(this);
		
		//camera follows the selected object
		world.getCamera().followTarget(this);
	}
	
	
	/**
	 * Renders the highlight of the actionable object at the location of the object to be called if the object is selected
	 * @param world world object
	 * @throws SlickException
	 */
	public void renderHighlight(World world) throws SlickException {
		Image image = new Image(this.highlightPath);
		image.drawCentered((int)world.getCamera().globalXToScreenX(this.getPos().getX()),
				   (int)world.getCamera().globalYToScreenY(this.getPos().getY()));
	}
	
	/**
	 * @param object object we're checking for being in selection range
	 * @param clickPos position of mouse click
	 * @return true if object is within 32 pixels of click, false otherwise
	 */
	private boolean objectInSelectionRange(ActionableObject object, Position clickPos) {
		return (object.getPos().distance(clickPos) < CLICK_RANGE);
	}
	
	// Getters and Setters --------------------------------------
	
	/**
	 * @return list of actions object can perform
	 */
	public String getListActions() {
		return this.listActions;
	}
	
	
	/**
	 * @return whether object is currently selected
	 */
	public boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected whether object is selected or not
	 */
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * @return 
	 */
	public String getHighlightPath() {
		return highlightPath;
	}

	/**
	 * @param highlightPath the path of the highlight image
	 */
	public void setHighlightPath(String highlightPath) {
		this.highlightPath = highlightPath;
	}
	//------------------------------------------------------------
	
}
