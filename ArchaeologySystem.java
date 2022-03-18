/**
 * @author Tiago Costa Sousa
 * @author Rodrigo Lemos Fernandes 
 * Bridges together all classes and organizes information to be sent to the main class.
 */

public class ArchaeologySystem {

	// constants
	
	private static final int ZERO = 0;
	private static final int PENALTY = -10;

	// instance variables

	private TeamCollection tc;
	private Terrain terrain;

	// constructors

	public ArchaeologySystem(int rows, int cols) {
		tc = new TeamCollection();
		terrain = new Terrain(rows, cols);
	}

	// methods

	/**
	 * Adds a team to the team collection.
	 * @param team Team.
	 * @pre team != null
	 */
	public void addTeam(Team team) {
		tc.addTeam(team);
	}

	/**
	 * Checks if a team exists or not.
	 * @param teamName name of the team.
	 * @return whether input team exists or not.
	 * @pre teamName != null
	 */
	public boolean checkTeam(String teamName) {
		return tc.checkTeam(teamName);
	}

	/**
	 * Starts the process of digging.
	 * Moves a player to the desired location, and checks if the player has a license. If so, updates the player's merit and removes
	 * any treasure from that location.
	 * In the end, advances the order of the next player to dig.
	 * @param teamName name of the team.
	 * @param jumpR jump that the player will do row-wise.
	 * @param jumpC jump that the player will do column-wise.
	 * @pre teamName != null && jumpR != null && jumpC != null && if (jumpC == 0 || jumpR == 0) jumpC != jumpR
	 */
	public void dig(String teamName, int jumpR, int jumpC) {
		movePlayer(teamName, jumpR, jumpC);
		if (hasLicense(teamName)) {
			updateMerit(teamName);
			removeTreasure(teamName);
		}
		nextPlayer(teamName);
	}

	/**
	 * Auxiliary method that checks if the current player to dig has a license.
	 * @param teamName name of the team.
	 * @return whether the current player has a license or not.
	 * @pre teamName != null
	 */
	private boolean hasLicense(String teamName) {
		int number = tc.getTeamByName(teamName);
		return tc.currentPlayerHasLicense(number);
	}

	/**
	 * Removes a team from the team collection.
	 * @param teamName name of the team.
	 * @pre teamName != null
	 */
	public void removeTeam(String teamName) {
		int number = tc.getTeamByName(teamName);
		tc.removeFrom(number);
	}

	/**
	 * Auxiliary method that changes a player's location.
	 * @param teamName name of the team.
	 * @param jumpR jump that the player will do row-wise.
	 * @param jumpC jump that the player will do column-wise.
	 * @pre teamName != null && jumpR != null && jumpC != null && if (jumpC == 0 || jumpR == 0) jumpC != jumpR
	 */
	private void movePlayer(String teamName, int jumpR, int jumpC) {
		int number = tc.getTeamByName(teamName);
		int row = tc.getLocationR(number);
		int col = tc.getLocationC(number);
		if (row + jumpR >= terrain.getRows() || col + jumpC >= terrain.getCols() || row + jumpR < ZERO || col + jumpC < ZERO)
			tc.disqualifyPlayer(number);
		else
			tc.movePlayer(jumpR, jumpC, number);
		tc.setInPlayStatus(number);
	}

	/**
	 * Checks if a team is in play or not.
	 * @param teamName name of the team.
	 * @return whether a team is in play or not.
	 * @pre teamName != null
	 */
	public boolean teamIsInPlay(String teamName) {
		int number = tc.getTeamByName(teamName);
		return tc.isInPlay(number);
	}

	/**
	 * Auxiliary method that updates a player's merit.
	 * @param teamName name of the team.
	 * @pre teamName != null
	 */
	private void updateMerit(String teamName) {
		int number = tc.getTeamByName(teamName);
		int row = tc.getLocationR(number);
		int col = tc.getLocationC(number);
		int treasureWorth = terrain.getWorth(row, col);
		tc.updateTreasureMerit(number, terrain.getTimesDug(row, col) * PENALTY);
		tc.updateTreasureMerit(number, treasureWorth);
	}

	/**
	 * Auxiliary method that removes the treasure from a certain location.
	 * @param teamName name of the team.
	 * @pre teamName != null
	 */
	private void removeTreasure(String teamName) {
		int number = tc.getTeamByName(teamName);
		int row = tc.getLocationR(number);
		int col = tc.getLocationC(number);
		terrain.removeTreasure(row, col);
	}

	/**
	 * Auxiliary method that advances the order of the next player to dig.
	 * @param teamName name of the team.
	 * @pre teamName != null
	 */
	private void nextPlayer(String teamName) {
		int number = tc.getTeamByName(teamName);
		tc.advancePlayer(number);
	}

	/**
	 * @return size of the terrain.
	 */
	public int getTerrainSize() {
		return terrain.getCols() * terrain.getRows();
	}

	/**
	 * Adds a plot to the terrain.
	 * @param plot Plot to add.
	 * @pre plot != null
	 */
	public void addPlot(Plot plot) {
		terrain.addPlot(plot);
	}

	/**
	 * @return combined value of treasures in the terrain.
	 */
	public int getWealth() {
		return terrain.getWealth();
	}

	/**
	 * @return number of teams in the team collection.
	 */
	public int getNumberOfTeams() {
		return tc.getNumberOfTeams();
	}

	/**
	 * @return ordered team iterator.
	 */
	public TeamIterator getOrderedTeamIterator() {
		return tc.getOrderedTeamIterator();
	}

	/**
	 * @return ordered player iterator.
	 * @param teamName name of the team.
	 * @pre teamName != null
	 */
	public PlayerIterator getOrderedPlayerIterator(String teamName) {
		int number = tc.getTeamByName(teamName);
		return tc.getOrderedPlayerIterator(number);
	}

	/**
	 * @return boolean two-dimensional array containing information about the existence of treasure of each plot of the terrain.
	 */
	public boolean[][] getTreasureMap() {
		return terrain.treasureMap();
	}
}
