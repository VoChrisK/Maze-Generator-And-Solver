package sjsu.vo.cs146.project2;

import sjsu.vo.cs146.project2.LinkedList.Node;

/**
 * This class solves solves the maze using breath first search and depth first search.
 * @authors Chris Vo and Wendy Chen
 * @Version 1.0
 */
public class Solve {
	//class variable declarations
	Maze maze;
		
	/**
	 * Constructor #1: This constructor creates a new instance of Solve, creating a new maze
	 * @param none
	 * @return none
	 */
	public Solve(Maze maze) {
		this.maze = maze;
	}
		
	/**
	 * This method solves the maze using breath first search. Numbers are used to indicate the traversal order.
	 * @param none
	 * @return none
	 */
	public void BreathFirstSearch() {
		char[] numbers = new char[] {'0','1','2','3','4','5','6','7','8','9'}; //to print the traversal order in the maze
		int counter = 0; //keep track of the array of number characters and to print the number characters appropriately
		Queue queue = new Queue(); //to keep track of the nodes during the breath first search process
		Node temp; //to store a neighboring node in the current node's list (mimics the variable v)
		
		maze.ResetMaze(); //method call to restore the maze to it's normal state
		
		//set all nodes to white and their pointer to point to the next neighboring node in their lists
		for(int i = 0; i < maze.getGraph().getTotalCells(); i++) { //while there are vertices in the graph
			maze.getGraph().getadjList()[i].getCurrent().setColor(Color.WHITE);
			maze.getGraph().getadjList()[i].getCurrent().setParent(null);
			maze.getGraph().getadjList()[i].setPointer(maze.getGraph().getadjList()[i].getHead().getNext()); //traverse to the next neighbor
		}
			
		queue.Enqueue(maze.getGraph().getadjList()[0].getCurrent());
			
		while(queue.IsEmpty() == false) { //as long as it continues to discover new nodes
			Node node = queue.Dequeue(); 
			maze.getBuild().setCharAt(maze.getGraph().getadjList()[node.getID()].getCharIndex(), numbers[counter]); //to print the room with a number instead of a space
			if(counter < (numbers.length - 1)) //to maintain the low order digits
				counter++;
			else
				counter = 0;
			if(node.getID() == (maze.getGraph().getTotalCells() - 1)) //terminates the loop if it reaches the end of the maze
				break;
				
			while(maze.getGraph().getadjList()[node.getID()].getPointer() != null) { //while there are neighbors in the current node's list
				temp = maze.getGraph().getadjList()[node.getID()].getPointer(); //temp is now the neighboring node
				//the head of the adjacency list is the ID of the current node
				//using the ID of the neighboring node as an index in order to access its list
				if(maze.getGraph().getadjList()[temp.getID()].getCurrent().getColor().equals(Color.WHITE)) { //equivalent to if temp is colored white
					maze.getGraph().getadjList()[temp.getID()].getCurrent().setColor(Color.GREY); //set the color of temp to grey
					maze.getGraph().getadjList()[temp.getID()].getCurrent().setParent(node); //sets the parent to the original node
					queue.Enqueue(maze.getGraph().getadjList()[temp.getID()].getCurrent()); //put temp in the queue
				}
				maze.getGraph().getadjList()[node.getID()].setPointer(maze.getGraph().getadjList()[node.getID()].getPointer().getNext()); //traverse to the next neighboring node
			}
			node.setColor(Color.BLACK);
		}
			
		System.out.println("BFS:");
		System.out.println(maze.getBuild());
	}
	
	/**
	 * This method finds the shortest path of Breath First Search. The paths are marked with '#'.
	 * @param none
	 * @return none
	 */
	public void BFSShortestPath() {
		maze.ResetMaze(); //method call to restore the maze to it's normal state
		Node pathToExit = maze.getGraph().getadjList()[maze.getGraph().getadjList().length - 1].getCurrent(); //create a node and set it to the last vertex of the graph
		maze.getBuild().setCharAt(maze.getGraph().getadjList()[0].getCharIndex(), '#');
		maze.getBuild().setCharAt(maze.getGraph().getadjList()[maze.getGraph().getTotalCells() - 1].getCharIndex(), '#');
		while(pathToExit.equals(maze.getGraph().getadjList()[0].getCurrent()) == false) { //while the node does not equal to the first vertex of the graph
			int cellIndex = maze.getGraph().getadjList()[pathToExit.getID()].getCharIndex();
			maze.getBuild().setCharAt(cellIndex, '#'); //places "#' at the spaces representing a cell
			pathToExit = maze.getGraph().getadjList()[pathToExit.getID()].getCurrent().getParent(); //set the node to the vertex's parent
			maze.getBuild().setCharAt(((cellIndex + maze.getGraph().getadjList()[pathToExit.getID()].getCharIndex()) / 2), '#');
		}
		
		System.out.println("BFS - Shortest Path:");
		System.out.println(maze.getBuild());
	}
		
	/**
	 * This method solves the maze using depth first search. Numbers are used to indicate the traversal order.
	 * @param none
	 * @return none
	 */
	public void DepthFirstSearch() {
		//method variable declarations
		char[] numbers = new char[] {'0','1','2','3','4','5','6','7','8','9'}; //to print the traversal order in the maze
		int counter = 0; //keep track of the array of number characters and to print the number characters appropriately
		DFSVariables variable = new DFSVariables(); //to end recursive calls to check for neighboring nodes if end of maze is reached
		//also track of array of number characters to display the correct ordering of nodes visited
		
		maze.ResetMaze(); //method call to restore the maze to it's normal state
		
		//set all nodes to white and their pointer to point to the next neighboring node in their lists
		for(int i = 0; i < maze.getGraph().getTotalCells(); i++) { //while there are vertices in the graph
			maze.getGraph().getadjList()[i].getCurrent().setColor(Color.WHITE);
			maze.getGraph().getadjList()[i].getCurrent().setParent(null);
			maze.getGraph().getadjList()[i].setPointer(maze.getGraph().getadjList()[i].getHead().getNext());
		}
		
		for(int i = 0; i < maze.getGraph().getTotalCells(); i++) { //while there are vertices in the graph
			if(maze.getGraph().getadjList()[i].getCurrent().getColor().equals(Color.WHITE)) { //if the current node is white
				variable = DepthFirstSearchVisit(i, numbers, counter, variable);
				if(variable.isFlag() == true) //terminates the loop if it reaches the end of maze, prevent further exploration
					break;
			}
		}
		
		System.out.println("DFS:");
		System.out.println(maze.getBuild());
	}
	
	/**
	 * This method handles the bulk of the DFS process. It utilizes recursion to traverse the nodes until it reaches
	 * a wall. This method is only available for the DepthFirstSearch method.
	 * @param i - the current array index for the adjacency list
	 * @param numbers -  to print the traversal order in the maze
	 * @param counter - to keep track of the array of number characters
	 * @param variable - to end recursive calls to check for neighboring nodes if end of maze is reached and to keep
	 * track of array of number characters to display the correct ordering of nodes visited
	 * @return none
	 */
	private DFSVariables DepthFirstSearchVisit(int i, char[] numbers, int counter, DFSVariables variable) {
		variable.setTime(variable.getTime() + 1);
		maze.getGraph().getadjList()[i].getCurrent().setStart(variable.getTime());
		maze.getGraph().getadjList()[i].getCurrent().setColor(Color.GREY);
		maze.getBuild().setCharAt(maze.getGraph().getadjList()[i].getCharIndex(), numbers[counter]); //to print the room with a number instead of a space
		while(maze.getGraph().getadjList()[i].getPointer() != null) { //while there are neighbors in the current node's list
			int j = maze.getGraph().getadjList()[i].getPointer().getID(); //store the neighboring node's ID to a new array index (mimics the variable v)
			if(j == (maze.getGraph().getTotalCells() - 1)) //if the neighboring node's ID equals to the last node in the adjacency list
				variable.setFlag(true);
			if(maze.getGraph().getadjList()[j].getCurrent().getColor().equals(Color.WHITE)) { //if the neighboring node is white
				counter = variable.getCurrent(); //once DFS reaches a wall, recursively obtain the last value
				if(counter < (numbers.length - 1)) //to maintain the low order digits
					counter++;
				else
					counter = 0;
				variable.setCurrent(counter);
				maze.getGraph().getadjList()[j].getCurrent().setParent(maze.getGraph().getadjList()[i].getCurrent());
				variable = DepthFirstSearchVisit(maze.getGraph().getadjList()[j].getCurrent().getID(), numbers, counter, variable); //recursively call the method and discovering nodes using v's list
			}
			maze.getGraph().getadjList()[i].getCurrent().setColor(Color.BLACK);
			variable.setTime(variable.getTime() + 1);
			maze.getGraph().getadjList()[i].getCurrent().setFinish(variable.getTime());
			
			if(variable.isFlag() == true) 
				return variable; //to end recursive calls. To prevent the current node from finding any undiscovered neighbors in its list
			else {
				maze.getGraph().getadjList()[i].setPointer(maze.getGraph().getadjList()[i].getPointer().getNext()); //traverse to the next neighbor
			}
		}
		
		return variable;
	}	
	
	/**
	 * This method finds the shortest path of Depth First Search. The paths are marked with '#'.
	 * @param none
	 * @return none
	 */
	public void DFSShortestPath() {
		maze.ResetMaze(); //method call to restore the maze to it's normal state
		Node pathToExit = maze.getGraph().getadjList()[maze.getGraph().getadjList().length - 1].getCurrent(); //create a node and set it to the last vertex of the graph
		maze.getBuild().setCharAt(maze.getGraph().getadjList()[0].getCharIndex(), '#');
		maze.getBuild().setCharAt(maze.getGraph().getadjList()[maze.getGraph().getTotalCells() - 1].getCharIndex(), '#');
		
		while(pathToExit.equals(maze.getGraph().getadjList()[0].getCurrent()) == false) { //while the node does not equal to the first vertex of the graph
			Node parent = maze.getGraph().getadjList()[pathToExit.getID()].getCurrent().getParent();
			int cellIndex = maze.getGraph().getadjList()[pathToExit.getID()].getCharIndex();
			if(maze.getGraph().getadjList()[pathToExit.getID()].getCurrent().getFinish() < parent.getFinish()) { //if the finishing time of the current vertex is less than the finish time of the parent node, print '#' and go to the parent vertex
				maze.getBuild().setCharAt(cellIndex, '#'); //places "#' at the spaces representing a cell
				maze.getBuild().setCharAt(((maze.getGraph().getadjList()[parent.getID()].getCharIndex() + maze.getGraph().getadjList()[pathToExit.getID()].getCharIndex()) / 2), '#');
				pathToExit = maze.getGraph().getadjList()[pathToExit.getID()].getCurrent().getParent(); //set the node to the vertex's parent
			}
		}
		
		System.out.println("DFS - Shortest Path:");
		System.out.println(maze.getBuild());
	}
	
	/**
	 * This class helps to prevent further exploration of nodes once end of maze have been reached. It also keeps track
	 * of count for the character array to properly display the correct ordering of nodes visited. This class is exclusively
	 * used for DFS implementations only.
	 * @authors Chris Vo and Wendy Chen
	 * @Version 1.0
	 */
	private class DFSVariables {
		private boolean flag; //to end recursive calls to check for neighboring nodes if end of maze is reached
		private int current; //to keep track of the array of number characters to display the correct ordering of nodes visited
		private int time;
		
		/**
		 * Constructor #1: This constructor creates a new instance of LinkedList with class variables initialized to 0 or equivalent.
		 * @param none
		 * @return none
		 */
		public DFSVariables() {
			flag = false;
			current = 0;
			time = 0;
		}

		/**
		 * This method returns the flag
		 * @param none
		 * @return flag - to end recursive calls to check for neighboring nodes if end of maze is reached
		 */
		public boolean isFlag() {
			return flag;
		}

		/**
		 * This method sets the value of the flag to a new value
		 * @param flag - to end recursive calls to check for neighboring nodes if end of maze is reached
		 * @return none
		 */
		public void setFlag(boolean flag) {
			this.flag = flag;
		}

		/**
		 * This method returns current
		 * @param current - to keep track of the array of number characters to display the correct ordering of nodes visited
		 * @return none
		 */
		public int getCurrent() {
			return current;
		}

		/**
		 * This method sets the value of current to a new value
		 * @param none
		 * @return current - to keep track of the array of number characters to display the correct ordering of nodes visited
		 */
		public void setCurrent(int current) {
			this.current = current;
		}
		
		public void setTime(int time) {
			this.time = time;
		}
		
		public int getTime() {
			return time;
		}
	}
}
