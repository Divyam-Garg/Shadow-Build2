import org.newdawn.slick.SlickException;
/**
 * @author Divyam Garg
 * Scout unit. Fastest unit in the game. Has no special attributes
 */
public class Scout extends Unit{
	
	/**
	 * Speed of Scout in pixels/second 
	 */
	public static final double SPEED = 0.3;
	
	/**
	 * path to image of scout
	 */
	public static final String IMAGE = "assets/units/scout.png";
	
	/**
	 * List of actions scout can perform. None!
	 */
	public static final String LIST_ACTIONS = "";
	
	/**
	 * @param pos position of Scout
	 * @throws SlickException
	 */
	public Scout(Position pos) throws SlickException {
		super(IMAGE, pos, SPEED, LIST_ACTIONS);
	}
	
	/**
	 *update Scout
	 */
	public void update(World world) {
		super.update(world);
	}
}
