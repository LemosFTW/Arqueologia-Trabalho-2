/**
 * @author Tiago Costa Sousa
 * @author Rodrigo Lemos Fernandes 
 * Iterator for Team objects.
 */

public class TeamIterator {
	
	// instance variables

	private Team[] teams;
	private int size;
	private int nextIndex;
	
	// constructors

	public TeamIterator(Team[] teams, int size) {
		this.teams = teams;
		this.size = size;
		nextIndex = 0;
	}

	// methods
	
	/**
	 * Returns a team and advances to the next.
	 * @return a team.
	 */
	public Team next() {
		return teams[nextIndex++];
	}
	
	/**
	 * @return whether there is a next team in the list or not.
	 */
	public boolean hasNext() {
		return nextIndex < size;
	}	
}
	