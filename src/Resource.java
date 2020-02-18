import java.util.ArrayList;
//import java.util.Iterator;

import org.newdawn.slick.SlickException;
/**
 * @author Divyam Garg
 * Resources can be mined by Engineers
 */
public abstract class Resource extends GameObject {
	private int amount;
	
	/**
	 * @param image_path path of resources image
	 * @param pos position of resource
	 * @param amount amount of resource
	 * @throws SlickException 
	 */
	public Resource(String image_path, Position pos, int amount) throws SlickException {
		super(image_path, pos);
		this.setAmount(amount);
	}
	
	/**
	 *Updates the resource
	 */
	@Override
	public void update(World world) {
		ArrayList<GameObject> gameObjects = world.getGameObjects();
		
		//If resource is depleted, remove it
		if (amount == 0) {
			
			world.removeGameObject(this);
		}
	}
	
	//Getters and Setters --------------------------

	/**
	 * @return the amount of resource
	 */
	public int getAmount() {
		return amount;
	}

	
	/**
	 * @param amount the value of amount of resource to be set
	 */
	public void setAmount(int amount) {
		if (amount>=0) {
			this.amount = amount;
		}
	}
	//---------------------------------------------
}
