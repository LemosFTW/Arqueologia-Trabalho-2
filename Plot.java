/**
 * @author Tiago Costa Sousa
 * @author Rodrigo Lemos Fernandes 
 * Handles the information concerning a plot.
 */

public class Plot {
	
	// constants
	
	private static final int ZERO = 0;
		
	// instance variables
	
	private int worth;
	private int timesDug;
	
	// constructors

	public Plot(int worth) {
		this.worth = worth;
		timesDug = ZERO;
	}

	// methods

	/**
	 * @return the value of the treasure in this plot.
	 */
	public int getWorth() {
		return worth;
	}
	
	/**
	 * Removes the treasure from the plot, increasing the times this plot was dug.
	 */
	public void removeTreasure() {
		worth = ZERO;
		timesDug++;
	}
	
	/**
	 * @return the amount of this the plot was dug.
	 */
	public int getTimesDug() {
		return timesDug;
	}

}
