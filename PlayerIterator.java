/**
 * @author Tiago Costa Sousa
 * @author Rodrigo Lemos Fernandes 
 * Iterator for Player objects.
 */

public class PlayerIterator {
	
	// instance variables

	private Player[] players;
	private int size;
	private int nextIndex;
	
	// constructors

	public PlayerIterator(Player[] players, int size) {
		this.players = players;
		this.size = size;
		nextIndex = 0;
	}

	// methods

	/**
	 * Returns a player and advances to the next.
	 * @return a player.
	 * @pre hasNext()
	 */
	public Player next() {
		return players[nextIndex++];
	}

	/**
	 * @return whether there is a next player in the list or not.
	 */
	public boolean hasNext() {
		return nextIndex < size;
	}	
}
	