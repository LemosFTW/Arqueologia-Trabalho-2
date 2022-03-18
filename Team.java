/**
 * @author Tiago Costa Sousa
 * @author Rodrigo Lemos Fernandes 
 * Handles the information concerning a singular team.
 */

public class Team {
	
	// constants
	
	private static final int ZERO = 0;
	private static final int ONE = 1;
	
	// instance variables

	private Player[] playerList;
	private boolean isInPlay;
	private String name;
	private int teamSize;
	private int score;
	private Player nextInLine;
	private int nextPlayer;

	// constructors

	public Team(String name, int size) {
		this.name = name;
		playerList = new Player[size];
		teamSize = ZERO;
		nextPlayer = ZERO;
		nextInLine = null;
		isInPlay = true;
		score = ZERO;
	}
	
	// methods

	/**
	 * Adds a player to the team.
	 * @param player Player to add.
	 * @pre player != null
	 */
	public void addPlayer(Player player) {
		playerList[teamSize++] = player;
		nextInLine = playerList[ZERO];
	}
	
	/**
	 * Compares this team to another team.
	 * Compares by score. If score is the same, compares by number of disqualified archaeologists. If the number of disqualified archaeologists is the same,
	 * compares by number of licensed archaeologists. If the number of licensed archaeologists is the same, compares by name.
	 * @param other team to compare to.
	 * @return result of comparison.
	 * @pre other != null
	 */
	public int compareTo(Team other) { 
		int score = (this.score - other.getScore());
		if (score != ZERO)
			return score;
		else if (getDisqualifiedArchaeologists() - other.getDisqualifiedArchaeologists() != ZERO)
			return -1 * (getDisqualifiedArchaeologists() - other.getDisqualifiedArchaeologists());
		else if (getLicensedArchaeologists() - other.getLicensedArchaeologists() != ZERO)
			return -1 * (getLicensedArchaeologists() - other.getLicensedArchaeologists());
		else
			return this.name.compareTo(other.getName())*-1;
			
	}

	/**
	 * @return score of the team.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Checks if there are any remaining players with a license in the team. If there aren't, removes the team from being in play.
	 */
	public void setInPlayStatus() {
		isInPlay = false;
		for (int i = ZERO; i < teamSize; i++) {
			if(playerList[i].HasLicense())
				isInPlay = true;
		}
	}
	
	/**
	 * Moves the current player in line to dig.
	 * @param rows jump that the player will do row-wise.
	 * @param cols jump that the player will do column-wise.
	 * @pre rows != null && cols != null && if (cols == 0 || rows == 0) cols != rows
	 */
	public void movePlayer(int rows, int cols) {
		nextInLine.move(rows, cols);
	}
	
	/**
	 * Removes the value of a player's merit from the score when that player is disqualified.
	 */
	public void removeDisqualifiedScore() {
		score -= nextInLine.getMerit();
	}
	
	/**
	 * @return row location of the current player in line to dig.
	 */
	public int getLocationR() {
		return nextInLine.getLocationR();
	}
	
	/**
	 * @return column location of the current player in line to dig.
	 */
	public int getLocationC() {
		return nextInLine.getLocationC();
	}
	
	/**
	 * Updates the merit of the current player in line to dig and updates the score of the team accordingly.
	 * @param merit Merit to add.
	 */
	public void updateMerit(int merit) {
		nextInLine.updateMerit(merit);
		score += merit;
	}
	
	/**
	 * Disqualifies the current player in line to dig.
	 */
	public void disqualifyPlayer() {
		nextInLine.disqualifyPlayer();
	}
	
	/**
	 * @return whether the current player in line to dig has a license or not.
	 */
	public boolean currentPlayerHasLicense() {
		return nextInLine.HasLicense();
	}
	
	/**
	 * @return number of disqualified archaeologists.
	 */
	public int getDisqualifiedArchaeologists() {
		int number = ZERO;
		for(int i = ZERO; i < teamSize; i++) {
			if(!playerList[i].HasLicense())
				number++;
		}
		return number;
	}
	
	/**
	 * Advances to the next player in line to dig.
	 */
	public void advancePlayer() {
		nextPlayer++;
		int number = ZERO;
		if(nextPlayer >= teamSize) {
			nextPlayer = ZERO;
		}
		Player player = playerList[nextPlayer];
		while(!player.HasLicense() && number < teamSize) {
			nextPlayer++;
			if(nextPlayer >= teamSize) {
				nextPlayer = ZERO;
			}
			player = playerList[nextPlayer];
			number++;
		}
		nextInLine = player;
	}
	
	/**
	 * @return number of licensed archaeologists.
	 */
	public int getLicensedArchaeologists() {
		int number = ZERO;
		for(int i = ZERO; i < teamSize; i++) {
			if(playerList[i].HasLicense())
				number++;
		}
		return number;
	}

	/**
	 * @return name of the team.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return whether the team is in play or not.
	 */
	public boolean isInPlay() {
		return isInPlay;
	}
	
	/**
	 * Auxiliary method that sorts players in a player list.
	 * @param players player list.
	 * @param numberOfPlayers number of licensed players.
	 * @pre players != null && numberOfPlayers != null
	 */
	private void sortPlayers(Player[] players, int numberOfPlayers) {
		for (int i = ONE; i < numberOfPlayers; i++) {
			for(int j = numberOfPlayers-ONE; j>=i ;j--) {
				if(players[j-ONE].compareTo(players[j]) < ZERO) {
					Player aux = players[j-ONE];
					players[j-ONE] = players[j];
					players[j] = aux;
				}
			}
		}
	}
	
	/**
	 * Creates an ordered player iterator.
	 * @return ordered player iterator.
	 */
	public PlayerIterator getOrderedPlayerIterator() {
		Player[] aux = new Player[teamSize];
		int number = ZERO;
		for(int i = ZERO; i < teamSize; i++) {
			if(playerList[i].HasLicense())
				aux[number++] = playerList[i];
		}
		sortPlayers(aux, number);
		return new PlayerIterator(aux, number);
	}
}
