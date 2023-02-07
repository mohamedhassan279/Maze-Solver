public interface IMazeSolver {
	/**
	* Read the maze file, and solve it using Breadth First Search
	* @param maze maze file
	* @return the coordinates of the found path from point ’S’
	*
	to point ’E’ inclusive, or null if no path is found.
	*/
	public int[][] solveBFS(java.io.File maze);
	/**
	* Read the maze file, and solve it using Depth First Search
	* @param maze maze file
	* @return the coordinates of the found path from point ’S’
	*
	to point ’E’ inclusive, or null if no path is found.
	*/
	public int[][] solveDFS(java.io.File maze);
	
	/**
	* Read the maze file
	* and create the grid of the maze
	* @param maze maze file
	* @return the grid in 2d array
	*/
	public void gridGenerator(java.io.File maze);
	
	/**
	* Get the neighbors of the point
	* @param node the node
	* @param visited the visited array which
	* contain the father of each point 
	* @return 2d array of size 5*2
	* which contain the neighbors in form that x in first column
	* and y in the second column
	* the last row first column contains the number of neighbors
	*/
	public int[][] getNeighbors(Point node, boolean[][] visited);
}
