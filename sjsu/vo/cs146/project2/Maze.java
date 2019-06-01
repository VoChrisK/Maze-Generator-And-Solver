package sjsu.vo.cs146.project2;

/**
 * This class constructs the ASCII representation of the maze using the randomized adjacency list.
 * @authors Chris Vo and Wendy Chen
 * @Version 1.0
 */
public class Maze {
	//class variable declarations
	private Graph graph; //an instance of graph to be used for the maze
	private StringBuilder build; //to build the maze and print it in ASCII format
	
	/**
	 * Constructor #1: This constructor creates a new instance of Maze with class variables initialized to 0 or equivalent.
	 * @param none
	 * @return none
	 */
	public Maze(Graph graph) {
		this.graph = graph;
		build = new StringBuilder("+ ");
	}
	
	/**
	 * This method returns the graph of the maze
	 * @param none
	 * @return graph - an instance of graph to be used for the maze
	 */
	public Graph getGraph() {
		return graph;
	}
	
	/**
	 * This method returns the ASCII representation of the maze
	 * @param none
	 * @return build - to build the maze and print it in ASCII format
	 */
	public StringBuilder getBuild() {
		return build;
	}
	
	/**
	 * This method removes the walls of the maze based on the edges in the adjacency list
	 * @param none
	 * @return none
	 */
	public void GenerateMaze() {
		//method calls to generate the grid of rooms and to randomize the edges in the graph
		GenerateGrid();	
		graph.RandomizeEdges();
		
		for(int i = 0; i < graph.getTotalCells(); i++) { //while there are vertices in the graph
			graph.getadjList()[i].setPointer(graph.getadjList()[i].getHead().getNext()); //pointer points to the first node the current node shares in edge with
			int wall = 0; //the position # of the wall to be removed
			
			while(graph.getadjList()[i].getPointer() != null) { //while there are neighbors in the current node's list
				//calculate the position of the wall by taking the midpoint of the current node and neighboring node
				wall = (graph.getadjList()[i].getCharIndex() + graph.getadjList()[graph.getadjList()[i].getPointer().getID()].getCharIndex())/2;
				build.setCharAt(wall, ' '); //remove the wall
				graph.getadjList()[i].setPointer(graph.getadjList()[i].getPointer().getNext()); //traverse to the next neighbor
			}
		}
		
		System.out.println("Maze:");
		System.out.println(build);
	}
	
	/**
	 * This method creates a grid of rooms with walls in between them in ASCII representation 
	 * @param none
	 * @return none
	 */
	private void GenerateGrid() {
		int idcounter = 0; //to keep track of the adjacency list
		
		//nested loop to generate the grid (there are 2n+1 layers to generate in an n x n grid)
		for(int i = 0; i < (2 * graph.getDimension() + 1); i++) {
			for(int j = 0; j < graph.getDimension(); j++) {
				if(i % 2 == 0) { //to generate the horizontal layers of the maze '+-+'
					if(j == 0)
						build.append("+");
					if(i == 0 && j == 0)
						j++; //increments j again so the first layer doesn't append an extra '-+'
					build.append("-+");
				}
				else { //to generate the vertical layers of the maze a.k.a. rooms and walls
					if(j < graph.getDimension() - 1) {
						if(j == 0) {
							build.append("| ");
							graph.getadjList()[idcounter].setCharIndex(build.length() - 1); //set the room position # for the current node
							idcounter++;
						}
						build.append("| ");
						graph.getadjList()[idcounter].setCharIndex(build.length() - 1); //set the room position # for the current node
						idcounter++;
					}
					else
						build.append("|");
				}
			}
			build.append("\n");
		}
		
		build.setCharAt(build.length() - 3, ' '); //create an opening for the end of the maze
	}
	
	/**
	 * This method removes all the characters that are not spaces in the rooms, effectively cleaning the maze
	 * @param none
	 * @return none
	 */
	public void ResetMaze() {
		for(int i = 0; i < graph.getTotalCells(); i++) {
			build.setCharAt(graph.getadjList()[i].getCharIndex(), ' ');
			graph.getadjList()[i].setPointer(graph.getadjList()[i].getHead().getNext());
			while(graph.getadjList()[i].getPointer() != null) {
				build.setCharAt(((graph.getadjList()[i].getCharIndex() + graph.getadjList()[graph.getadjList()[i].getPointer().getID()].getCharIndex()) / 2), ' ');
				graph.getadjList()[i].setPointer(graph.getadjList()[i].getPointer().getNext());
			}
		}
	}
	
	/**
	 * This method prints each list in the adjacency list. It prints out the total size of the list as well
	 * as the the neighboring nodes the current nodes shares an edge with.
	 * @param none
	 * @return none
	 */
	public void PrintList() {
		for(int i = 0; i < graph.getTotalCells(); i++) {
			System.out.print("Size: " + graph.getadjList()[i].getSize() + " | ");
			System.out.print("Node #" + i + ": ");
			graph.getadjList()[i].setPointer(graph.getadjList()[i].getHead().getNext());
			while(graph.getadjList()[i].getPointer() != null) {
					System.out.print(graph.getadjList()[i].getPointer().getID());
					if(graph.getadjList()[i].getPointer().getNext() != null)
						System.out.print(" -> ");
					graph.getadjList()[i].setPointer(graph.getadjList()[i].getPointer().getNext());
			}
			System.out.print("\n");
		}
	}
}
