/**
 * @author Tiago Costa Sousa
 * @author Rodrigo Lemos Fernandes 
 * Handles the information concerning all the teams.
 */

public class TeamCollection {
	
	// constants
	
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int COLLECTION_SIZE = 10;
	
	// instance variables

	private Team[] participatingTeams;
	private int numberOfTeams;

	// constructors
	
	public TeamCollection() {
		participatingTeams = new Team[COLLECTION_SIZE];
		numberOfTeams = ZERO;
	}
	
	// methods

	/**
	 * Adds a team to the team collection.
	 * @param team Team to add.
	 * @pre team != null
	 */
	public void addTeam(Team team) {
		participatingTeams[numberOfTeams++] = team;
	}
	
	/**
	 * Removes a team from the team collection.
	 * @param number Number of the team to remove.
	 * @pre number != null && number >= 0 && number < numberOfTeams
	 */
	public void removeFrom(int number) {
		for (int i = number; i < numberOfTeams - 1; i++)
			participatingTeams[i] = participatingTeams[i + ONE];
		numberOfTeams--;
	}
	
	/**
	 * Checks if a team should be in play or not, and updates its status accordingly.
	 * @param number Number of the team to check.
	 * @pre number != null && number >= 0 && number < numberOfTeams
	 */
	public void setInPlayStatus(int number) {
		participatingTeams[number].setInPlayStatus();
	}
	
	/**
	 * @param number Number of the team to check.
	 * @return whether a team is in play or not.
	 * @pre number != null && number >= 0 && number < numberOfTeams
	 */
	public boolean isInPlay(int number) {
		return participatingTeams[number].isInPlay();
	}
	
	/**
	 * Checks if the current player to dig in a certain team has a license or not.
	 * @param number Number of the team to check.
	 * @return whether the player has a license or not.
	 * @pre number != null && number >= 0 && number < numberOfTeams
	 */
	public boolean currentPlayerHasLicense(int number) {
		return participatingTeams[number].currentPlayerHasLicense();
	}
	
	/**
	 * @param number Number of the team to get name.
	 * @return the name of a team.
	 * @pre number != null && number >= 0 && number < numberOfTeams
	 */
	public String getTeamName(int number) {
		return participatingTeams[number].getName();
	}
	
	/**
	 * Checks if a team name corresponds to a team in the team collection.
	 * @param name Name of the team to check.
	 * @return whether the team name exists in the collection or not.
	 * @pre name != null
	 */
	public boolean checkTeam(String name) {
		boolean exists = false;
		for (int i = ZERO; i < numberOfTeams; i++) {
			if (name.equals(participatingTeams[i].getName()))
				exists = true;
		}
		return exists;
	}
	
	/**
	 * Advances the order of the next player to dig in a certain team.
	 * @param number Number of the team to advance the player.
	 * @pre number != null && number >= 0 && number < numberOfTeams
	 */
	public void advancePlayer(int number) {
		participatingTeams[number].advancePlayer();
	}
	
	/**
	 * Moves the current player to dig in a certain team.
	 * @param jumpR Amount of space the player will move row-wise.
	 * @param jumpC Amount of space the player will move column-wise.
	 * @param number Number of the team to move the player.
	 * @pre number != null && number >= 0 && number < numberOfTeams &&
	 * jumpR != null && jumpC != null && if (jumpC == 0 || jumpR == 0) jumpC != jumpR
	 */
	public void movePlayer(int jumpR, int jumpC, int number) {
		participatingTeams[number].movePlayer(jumpR, jumpC);
	}
	
	/**
	 * Updates the merit of current player to dig in a certain team.
	 * @param number Number of the team to update the merit.
	 * @param merit Amount of merit to change.
	 * @pre number != null && number >= 0 && number < numberOfTeams
	 */
	public void updateTreasureMerit(int number, int merit) {
		participatingTeams[number].updateMerit(merit);
	}
	
	/**
	 * Gets the row location of the current player to dig in a certain team.
	 * @param number Number of the team to get location.
	 * @return location of the current player to dig, row-wise.
	 * @pre number != null && number >= 0 && number < numberOfTeams
	 */
	public int getLocationR(int number) {
		return participatingTeams[number].getLocationR();
	}
	
	/**
	 * Gets the column location of the current player to dig in a certain team.
	 * @param number Number of the team to get location.
	 * @return location of the current player to dig, column-wise.
	 * @pre number != null && number >= 0 && number < numberOfTeams
	 */
	public int getLocationC(int number) {
		return participatingTeams[number].getLocationC();
	}
	
	/**
	 * Disqualifies the current player from a certain team and removes his merit from the team score.
	 * @param number Number of the team to disqualify the player.
	 * @pre number != null && number >= 0 && number < numberOfTeams
	 */
	public void disqualifyPlayer(int number) {
		participatingTeams[number].disqualifyPlayer();
		participatingTeams[number].removeDisqualifiedScore();
	}
	
	/**
	 * Gets the team number through the name of the team.
	 * @param teamName Name of the team.
	 * @return team number.
	 * @pre teamName != null
	 */
	public int getTeamByName(String teamName) {
		int number = -1;
		for (int i = ZERO; i < numberOfTeams; i++) {
			if (teamName.equals(participatingTeams[i].getName()))
				number = i;
		}
		return number;
	}
	
	/**
	 * @param number Number of the team to get an iterator from.
	 * @return ordered player iterator from a certain team.
	 * @pre number != null && number >= 0 && number < numberOfTeams
	 */
	public PlayerIterator getOrderedPlayerIterator(int number) {
		return participatingTeams[number].getOrderedPlayerIterator();
	}

	/**
	 * @return number of teams in the team collection.
	 */
	public int getNumberOfTeams() {
		return numberOfTeams;
	}
	
	/**
	 * Auxiliary method that sorts teams by their ranking.
	 * @param teamCollection Team collection to sort.
	 * @param numberOfTeams Number of teams to sort.
	 * @pre teamCollection != null && numberOfTeams != null
	 */
	private void sortTeams(Team[] teamCollection, int numberOfTeams) {
		for (int i = ONE; i < numberOfTeams; i++) {
			for(int j = numberOfTeams-ONE; j>=i ;j--) {
				if(teamCollection[j-ONE].compareTo(teamCollection[j]) < ZERO) {
					Team aux = teamCollection[j-ONE];
					teamCollection[j-ONE] = teamCollection[j];
					teamCollection[j] = aux;
				}
			}
		}
	}
		
	/**
	 * Temporarily orders the team collection and returns an iterator with that ordered team collection.
	 * @return an ordered team iterator.
	 */
	public TeamIterator getOrderedTeamIterator() {
		Team[] aux = new Team[numberOfTeams];
		for(int i = ZERO; i < numberOfTeams; i++) {
			aux[i] = participatingTeams[i];
		}
		sortTeams(aux, numberOfTeams);
		return new TeamIterator(aux, numberOfTeams);
	}
}
