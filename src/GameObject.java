import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * @author Divyam Garg
 * Every kind of object that can be rendered on the map is a GameObject
 */
public abstract class GameObject {
	private Image image;
	private Position pos;
	
	/**
	 * @param image_path path of the object image
	 * @param pos position of the object
	 * @throws SlickException
	 */
	public GameObject(String image_path, Position pos) throws SlickException {
		this.image = new Image(image_path);
		this.pos = pos;
	}
	
	//implemented by children of resource, building and unit
	public abstract void update(World world);
	
	/**
	 * @param world world object
	 */
	public void render(World world) {
		image.drawCentered((int)world.getCamera().globalXToScreenX(pos.getX()),
				   (int)world.getCamera().globalYToScreenY(pos.getY()));
	};
	
	// Getters and Setters ------------------------------------------------
	/**
	 * @return the position of object
	 */
	public Position getPos() {
		return pos;
	}
	
	/**
	 * @param x x coordinate to set position to
	 * @param y y coordinate to set position to
	 */
	public void setPos(double x, double y) {
		this.pos.setX(x);
		this.pos.setY(y);
	}
	
	/**
	 * @param newImagePath Path to the new image
	 * @throws SlickException
	 */
	public void setImage(String newImagePath) throws SlickException {
		image = new Image(newImagePath);
	}
	//----------------------------------------------------------------------
}

