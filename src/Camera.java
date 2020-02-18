import org.newdawn.slick.Input;

/**
 * This class should be used to restrict the game's view to a subset of the entire world.
 * 
 * You are free to make ANY modifications you see fit.
 * These classes are provided simply as a starting point. You are not strictly required to use them.
 */
public class Camera {
	private double x = 300;
	private double y = 300;
	private ActionableObject target;
	
	/**
	 * Speed with which Camera moves if not following any target
	 */
	public static final double SPEED = 0.4;
	
	/**
	 * @param target target
	 */
	public void followTarget(ActionableObject target) {
		this.target = target;
	}
	
	/**
	 * @param x global x-coordinate
	 * @return 
	 */
	public double globalXToScreenX(double x) {
		return x - this.x;
	}
	
	/**
	 * @param y global y-coordinate
	 * @return the y coordinate on screen
	 */
	public double globalYToScreenY(double y) {
		return y - this.y;
	}

	/**
	 * @param x screen x coordinate
	 * @return the global x coordinate
	 */
	public double screenXToGlobalX(double x) {
		return x + this.x;
	}
	
	/**
	 * @param y screen y coordinate
	 * @return the global y coordinate
	 */
	public double screenYToGlobalY(double y) {
		return y + this.y;
	}
	
	/**
	 * updates camera
	 * @param world world object
	 */
	public void update(World world) {
		Input input = world.getInput();
		boolean W_down = input.isKeyDown(Input.KEY_W);
		boolean A_down = input.isKeyDown(Input.KEY_A);
		boolean S_down = input.isKeyDown(Input.KEY_S);
		boolean D_down = input.isKeyDown(Input.KEY_D);
		double targetX=x, targetY=y;
		
		if (W_down || A_down || S_down || D_down) {
			this.target = null;
		}
		
		//if not following a target at the moment
		if (this.target == null) {
			
			if (W_down) {
				targetY = y - SPEED*world.getDelta();
			}
			else if (A_down) {
				targetX = x - SPEED*world.getDelta();
			}
			else if (S_down) {
				targetY = y + SPEED*world.getDelta();
			}
			else if (D_down) {
				targetX = x + SPEED*world.getDelta();
			}
		}
		else {
			targetX = target.getPos().getX() - App.WINDOW_WIDTH / 2;
			targetY = target.getPos().getY() - App.WINDOW_HEIGHT / 2;
		}
		
		//check x,y dont go out of the map
		x = Math.min(targetX, world.getMapWidth() -	 App.WINDOW_WIDTH);
		x = Math.max(x, 0);
		y = Math.min(targetY, world.getMapHeight() - App.WINDOW_HEIGHT);
		y = Math.max(y, 0);
	}
}
