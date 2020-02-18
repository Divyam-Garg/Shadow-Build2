import org.newdawn.slick.SlickException;
/**
 * @author Divyam Garg
 * Unobtainium Mine
 */
public class UnobtainiumMine extends Resource{

	/**
	 * Amount of resource in Unobtainium Mine at start
	 */
	public static final int START_AMOUNT = 50;
	
	/**
	 * Path to image of Unobtainium mine
	 */
	public static final String MINE_PATH = "assets/resources/unobtainium_mine.png";
	
	/**
	 * Constructor
	 * @param location location of Mine
	 * @throws SlickException
	 */
	public UnobtainiumMine(Position location) throws SlickException {
		super(MINE_PATH, location, START_AMOUNT);
		;
	}
}
