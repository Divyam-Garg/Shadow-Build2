/**
 * @author Divyam Garg
 * Class for manipulating positions in 2 dimensions
 */
public class Position {
	private double x;
	private double y;
	
	/**
	 * Constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @param other the second point
	 * @return the euclidean distance between this and other
	 */
	public double distance(Position other) {
		return (double)Math.sqrt((this.x - other.x) * (this.x - other.x) +
				(this.y - other.y) * (this.y - other.y));
	}
	
	
	/**
	 * @param other the second point to compare to
	 * @return true if this and other are the same, false otherwise
	 */
	public boolean equals(Position other) {
		return ((this.x == other.x) && (this.y == other.y));
	}
	
	//Getters and Setters -----------------------
	/**
	 * @return the x coordinate of position
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * @return the y coordinate of position
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * set a new value for x in position
	 * @param x new value for x coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * set a new value for x in position
	 * @param y new value for y coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}
	//--------------------------------------------
}

