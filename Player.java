/**
 * @author Tiago Costa Sousa
 * @author Rodrigo Lemos Fernandes Handles the information concerning a player.
 */

public class Player {

	// constants

	private static final int ZERO = 0;
	private static final int STARTING_LOCATION = -1;

	// instance variables

	private int merit;
	private String name;
	private boolean hasLicense;
	private int locationR, locationC;
	private int penalties;

	// constructors

	public Player(String name) {
		this.name = name;
		merit = ZERO;
		hasLicense = true;
		locationR = STARTING_LOCATION;
		locationC = STARTING_LOCATION;
		penalties = ZERO;

	}

	// methods

	/**
	 * @return the merit of the player.
	 */
	public int getMerit() {
		return merit;
	}

	/**
	 * Moves the player on the terrain.
	 * 
	 * @param row Amount of space the player will move row-wise.
	 * @param col Amount of space the player will move column-wise.
	 * @pre row != null && col != null && if (col == 0 || row == 0) col != row
	 */
	public void move(int row, int col) {
		locationR += row;
		locationC += col;
	}

	/**
	 * Updates the merit of the player, either by awarding merit, or penalizing
	 * merit.
	 * 
	 * @param merit amount of merit to update.
	 * @pre merit != null
	 */
	public void updateMerit(int merit) {
		this.merit += merit;
		if (merit < ZERO)
			penalties++;
	}

	/**
	 * @return whether the player has a license or not.
	 */
	public boolean HasLicense() {
		return hasLicense;
	}

	/**
	 * @return the row location of the player.
	 */
	public int getLocationR() {
		return locationR;
	}

	/**
	 * @return the column location of the player.
	 */
	public int getLocationC() {
		return locationC;
	}

	/**
	 * Disqualifies a player, removing his license.
	 */
	public void disqualifyPlayer() {
		hasLicense = false;
	}

	/**
	 * Compares a player with another player. If the score is the same, compares by
	 * penalties. If the penalties are the same, compares by name.
	 * 
	 * @param other The other player to compare to.
	 * @pre other != null
	 */
	public int compareTo(Player other) {
		int score = (getMerit() - other.getMerit());
		if (score != ZERO)
			return score;
		else if (getPenalties() - other.getPenalties() != ZERO)
			return -1 * (getPenalties() - other.getPenalties());
		else
			return this.name.compareTo(other.getName()) * -1;

	}

	/**
	 * @return the number of penalties of the player.
	 */
	public int getPenalties() {
		return penalties;
	}

	/**
	 * @return the name of the player.
	 */
	public String getName() {
		return name;
	}
}
