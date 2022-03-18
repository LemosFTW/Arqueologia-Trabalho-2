/**
 * @author Tiago Costa Sousa
 * @author Rodrigo Lemos Fernandes 
 * Handles the information concerning the terrain.
 */

public class Terrain {
	
	// constants
	
	private static final int ZERO = 0;
		
	// instance variables

	private Plot[][] terrain;
	private int rows, cols;
	private int usedRows, usedCols;
	
	// constructors

	public Terrain(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		terrain = new Plot[rows][cols];
		usedRows = ZERO;
		usedCols = ZERO;
	}

	// methods

	/**
	 * @return the number of rows in the terrain.
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @return the number of columns in the terrain.
	 */
	public int getCols() {
		return cols;
	}
	
	/**
	 * Creates a two-dimensional array with information of which plot has treasure or not.
	 * @return boolean two-dimensional array with information of which plot has treasure.
	 */
	public boolean[][] treasureMap() {
		boolean[][] treasureMap = new boolean[rows][cols];
		for (int i = ZERO; i < rows; i++) {
			for (int j = ZERO; j < cols; j++) {
				treasureMap[i][j] = (terrain[i][j].getWorth() != ZERO);
			}
		}
		return treasureMap;
	}
	
	/**
	 * Adds a plot to the terrain.
	 * @param plot Plot to add.
	 * @pre plot != null
	 */
	public void addPlot(Plot plot) { 
		if(usedCols < cols) {
			terrain[usedRows][usedCols++] = plot;
		}
		else {
			usedRows++;
			usedCols = ZERO;
			terrain[usedRows][usedCols++] = plot;
		}
	}
	
	/**
	 * @param row Row location of the plot to get treasure value.
	 * @param col Column location of the plot to get treasure value.
	 * @return the value of a treasure in a certain plot.
	 * @pre row != null && col != null
	 */
	public int getWorth(int row, int col) {
		return terrain[row][col].getWorth();
	}
	
	/**
	 * Removes the treasure from a certain plot.
	 * @param row Row location of the plot to remove treasure from.
	 * @param col Column location of the plot to remove treasure from.
	 * @pre row != null && col != null
	 */
	public void removeTreasure(int row, int col) {
		terrain[row][col].removeTreasure();
	}
	
	/**
	 * @param row Row location of the plot to get times dug.
	 * @param col Column location of the plot to get times dug.
	 * @return the number of times a certain plot was dug.
	 */
	public int getTimesDug(int row, int col) {
		return terrain[row][col].getTimesDug();
	}
	
	/**
	 * Gets the sum of the value of all the treasures in the terrain.
	 * @return the sum of the value of all the treasures in the terrain.
	 */
	public int getWealth() {
		int wealth = ZERO;
		for (int i = ZERO; i < rows; i++) {
			for (int j = ZERO; j < cols; j++) {
				wealth += terrain[i][j].getWorth();
			}
		}
		return wealth;
	}
}
