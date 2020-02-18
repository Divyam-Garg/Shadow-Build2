import org.newdawn.slick.SlickException;
/**
 * @author Divyam Garg
 * Metal Resource
 */
public class MetalMine extends Resource{
	
	/**
	 * Amount of resources a Metal Mine has when it's formed
	 */
	public static final int START_AMOUNT = 500;
	
	/**
	 * Path to image of Metal Mine
	 */
	public static final String MINE_PATH = "assets/resources/metal_mine.png";
	
	/**
	 * Constructor
	 * @param location location of the Metal Mine
	 * @throws SlickException
	 */
	public MetalMine(Position location) throws SlickException {
		super(MINE_PATH, location, START_AMOUNT);
	}
	
}
