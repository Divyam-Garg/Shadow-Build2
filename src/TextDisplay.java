import org.newdawn.slick.Graphics;
/**
 * @author Divyam Garg
 * Stores how much of each resource player has. Also displays what actions currently selected Object can perform
 */
public class TextDisplay {
	
	/**
	 * x coordinate where resources are to be diplayed on screen
	 */
	public static final float DRAW_RES_X = 32;
	
	/**
	 * y coordinate where resources are to be displayed on screen
	 */
	public static final float DRAW_RES_Y = 32;
	
	/**
	 * x coordinate where list of actions are to be displayed on screen
	 */
	public static final float DRAW_ACTION_X = 32;
	
	/**
	 * y coordinate where list of actions are to be displayed on screen
	 */
	public static final float DRAW_ACTION_Y = 100;
	
	private int amountMetal;
	private int amountUnobtainium;
	private String resString;
	private String actionString;
	
	/**
	 * Constructor
	 */
	public TextDisplay() {
		this.amountMetal = 0;
		this.amountUnobtainium = 0;
		this.resString = String.format("Metal: %d\nUnobtainium: %d", amountMetal, amountUnobtainium);
		this.actionString = "";
	}
	
	/**
	 * updates TextDisplay
	 * @param world world object
	 */
	public void update(World world) {
		ActionableObject selectedObject = world.getSelectedObject();
		
		//update actionString
		if (selectedObject != null) {
			this.actionString = selectedObject.getListActions();			
		}
		else {
			this.actionString = "";
		}
		
		//update resString
		this.resString = String.format("Metal: %d\nUnobtainium: %d", amountMetal, amountUnobtainium);
	}
	
	/**
	 * renders TextDisplay
	 * @param g
	 */
	public void render(Graphics g) {
		g.drawString(resString, DRAW_RES_X, DRAW_RES_Y);
		g.drawString(actionString, DRAW_ACTION_X, DRAW_ACTION_Y);
	}
	
	//Getters and Setters ------------------------------
	/**
	 * @return amount of metal
	 */
	public int getAmountMetal() {
		return this.amountMetal;
	}
	
	/**
	 * @return amount of unobtainium
	 */
	public int getAmountUnobtainium() {
		return this.amountUnobtainium;
	}
	
	/**
	 * @param newAmount new amount of metal
	 */
	public void setAmountMetal(int newAmount) {
		if (newAmount < 0) {
			this.amountMetal = 0;
		}
		this.amountMetal = newAmount;
	}
	
	/**
	 * @param newAmount new amount of Unobtainium
	 */
	public void setAmountUnobtainium(int newAmount) {
		if (newAmount < 0) {
			this.amountUnobtainium = 0;
		}
		this.amountUnobtainium = newAmount;
	}
	//--------------------------------------------------
}
