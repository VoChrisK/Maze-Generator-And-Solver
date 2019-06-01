package sjsu.vo.cs146.project2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import sjsu.vo.cs146.project2.LinkedList.Node;

/**
 * This class constructs a graph using adjacency list representation. The edges are randomized and stored in
 * the respective lists.
 * @authors Chris Vo and Wendy Chen
 * @Version 1.0
 */
public class Graph {
	//class variable declarations
	private Random myRandGen; //a random number generator to randomize the edges of the graph
	private LinkedList[] adjList; //the adjacency list to store the edges (note: indices represents the nodes/vertices)
	private int TotalCells; //the total number of vertices. Also the total number of rooms in the maze
	private int dimension; //the total number of vertices per row or column
	
	/**
	 * Constructor #1: This constructor creates a new instance of Graph. Set the random generator's seed to 0,
	 * instantiate the adjacency list, and initialize other class variables.
	 * @param dimension - the total number of vertices per row/column
	 * @return none
	 */
	public Graph(int dimension) {
		myRandGen = new java.util.Random(0);
		this.dimension = dimension;
		TotalCells = dimension * dimension;
		adjList = new LinkedList[TotalCells];
		
		for(int i = 0; i < TotalCells; i++)
			adjList[i] = new LinkedList(i);
	}
	
	/**
	 * This method returns the next double number generated from the random generator
	 * @param none
	 * @return double - randomized double value from the random generator
	 */
	public double myRandom() {
		return myRandGen.nextDouble();
	}
	
	/**
	 * This method returns the maximum number of rows and columns in the maze
	 * @param none
	 * @return dimension - the total number of vertices per row/column
	 */
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * This method returns the total number of nodes/vertices
	 * @param none
	 * @return TotalCells - the total number of vertices. Also the total number of rooms in the maze
	 */
	public int getTotalCells() {
		return TotalCells;
	}
	
	/**
	 * This method returns the adjacency list of the graph
	 * @param none
	 * @return adjList - the adjacency list to store the edges
	 */
	public LinkedList[] getadjList() {
		return adjList;
	}
	
	/**
	 * This method randomly generates the edges of neighboring nodes and store them in the proper list in the
	 * adjacency list. The method first searches for any available neighbor, adds them in a data structure,
	 * randomizes which neighbor to create an edge with, and stores them in the appropriate list.
	 * @param none
	 * @return none
	 */
	public void RandomizeEdges() {
		//method variable declarations
		Cell[][] maze = new Cell[dimension][dimension]; //to create the nodes/vertices of the graph in place
		int counter = 0; //to keep track of setting each cell with the appropriate ID
		
		for(int i = 0; i < dimension; i++) { //to instantiate each cell and set the ID of each cell
			for(int j = 0; j < dimension; j++) {
				maze[i][j] = new Cell(counter, i, j);
				counter++;
			}
		}
		
		//method variable declarations
		Stack<Cell> CellStack = new Stack<Cell>(); //To keep track of the nodes during the depth first search process
		ArrayList<Cell> UnvisitedNeighbor = new ArrayList<Cell>(); //data structure to store neighbors
		Cell CurrentCell = maze[(int)(myRandom() * dimension)][(int)(myRandom() * dimension)]; //set the starting point in the maze
		int VisitedCells = 1; //counter to keep track of nodes that have been discovered
		
		//this entire loop handles the bulk of the randomization utilizing depth first search
		while(VisitedCells < TotalCells) { //while all nodes have not been checked yet
			if(CurrentCell.getColor() == Color.WHITE) 
				CurrentCell.setColor(Color.GREY);
		
			//a series of if statements to find neighboring cells of the CurrentCell and add them to the data structure
			//first condition is to check if it's outside the maze and to prevent ArrayOutOfBounds error
			//second condition is to check whether the neighbor has been checked or not
			if((CurrentCell.getRow() - 1) >= 0 && maze[CurrentCell.getRow() - 1][CurrentCell.getColumn()].getColor() == Color.WHITE)
				UnvisitedNeighbor.add(maze[CurrentCell.getRow() - 1][CurrentCell.getColumn()]);
			if((CurrentCell.getRow() + 1) < dimension && maze[CurrentCell.getRow() + 1][CurrentCell.getColumn()].getColor() == Color.WHITE)
				UnvisitedNeighbor.add(maze[CurrentCell.getRow() + 1][CurrentCell.getColumn()]);
			if((CurrentCell.getColumn() - 1) >= 0 && maze[CurrentCell.getRow()][CurrentCell.getColumn() - 1].getColor() == Color.WHITE)
				UnvisitedNeighbor.add(maze[CurrentCell.getRow()][CurrentCell.getColumn() - 1]);
			if((CurrentCell.getColumn() + 1) < dimension && maze[CurrentCell.getRow()][CurrentCell.getColumn() + 1].getColor() == Color.WHITE)
				UnvisitedNeighbor.add(maze[CurrentCell.getRow()][CurrentCell.getColumn() + 1]);
			
			if(UnvisitedNeighbor.size() >= 1) { //if there are 1 or more neighbors, begin the randomization process
				int NewNeighborIndex = (int)(myRandom() * UnvisitedNeighbor.size()); //the index of the neighboring cell
				Cell Neighbor = UnvisitedNeighbor.get(NewNeighborIndex); //retrieve the neighboring cell
				adjList[CurrentCell.getID()].addNode(Neighbor); //add the neighbor to the CurrentCell's list
				adjList[Neighbor.getID()].addNode(new Node(CurrentCell.getID())); //add the CurrentCell to the neighbor's list
				CellStack.push(CurrentCell);
				CurrentCell = Neighbor;
				VisitedCells++;
			}
			else {
				CurrentCell.setColor(Color.BLACK);
				CurrentCell = CellStack.pop(); //go back to the previous cell
			}
			
			UnvisitedNeighbor.clear();
		}
	}
	
	/**
	 * This class constructs an extension of node to remember the position of the node in the maze 
	 * @authors Chris Vo and Wendy Chen
	 * @Version 1.0
	 */
	public static class Cell extends Node {
		//class variable declarations
		private int row; //used to identify the row a node would be on 
		private int column; //used to identify the column a node would be on
		
		/**
		 * Constructor #1: This constructor creates a new instance of Cell with class variables initialized to 0 or equivalent.
		 * @param none
		 * @return none
		 */
		public Cell() {
			super(); //calls the superclass's no parameter constructor
			row = 0;
			column = 0;
		}
		
		/**
		 * Constructor #2: This constructor creates a new instance of Cell, setting the ID of the node
		 * @param ID - the node's identity number
		 * @return none
		 */
		public Cell(int ID) {
			super(ID); //calls the superclass's second constructor and set the ID of the Node
			row = 0; 
			column = 0;
		}
		
		/**
		 * Constructor #3: This constructor creates a new instance of Cell, setting the ID of the node and the
		 * position of the node in the maze
		 * @param ID - the node's identity number
		 * @param row - which row the node is in the maze
		 * @param column - which column the node is in the maze
		 * @return none
		 */
		public Cell(int ID, int row, int column) {
			super(ID);
			this.row = row;
			this.column = column;
		}
		
		/**
		 * This method sets the row # of the node to a new value
		 * @param row - which row the node is in the maze
		 * @return none
		 */
		public void setRow(int row) {
			this.row = row;
		}
		
		/**
		 * This method returns the row # of the node
		 * @param none
		 * @return row - which row the node is in the maze
		 */
		public int getRow() {
			return row;
		}
		
		/**
		 * This method sets the column # of the node to a new value
		 * @param column - which column the node is in the maze
		 * @return none
		 */
		public void setColumn(int column) {
			this.column = column;
		}
		
		/**
		 * This method returns the column # of the node
		 * @param none
		 * @return column - which column the node is in the maze
		 */
		public int getColumn() {
			return column;
		}
	}
}