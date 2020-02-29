## Maze Generator and Solver

A two-persons team project for `CS146 - Data Structures and Algorithms` in _San Jose State University_. This project takes in a number _N_ between 4 and 10 inclusive and randomly generates a square-sized (_NxN_) maze. It also subsequently solves the maze and output both the maze and the solution's path.

![maze console](https://i.imgur.com/5rRCwiU.png)

## Languages/Technologies
* Java
* JUnit

## Background and Overview

### Maze Generator

The maze generator framework is built using the graph data structure. We define the graph using an adjacency list, or an array of 
linked lists containing all neighbors of particular vertices. We define our own Linked List class with additional, custom methods and 
use it for the adjacency matrix.

We implemented a depth first search algorithm to determine the edges. Our algorithm involves randomizing the edges for each 
vertex with a couple of constraints:

* The maze must be perfect. In other words, it must have one and only one path from the starting point to the ending point. 
Additionally, it cannot have any accessible sections, circular paths, or open areas.
* The maze also must be fully connected. Every generated room should be reachable from the starting point. 
There cannot be no rooms or areas that are completely blocked off from the rest of the maze.

Here is a code snippet of our algorithm:

```Java
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
```

### Printing the Maze

The maze is printed in ASCII and in console, where `|` and `-` represents the walls, `+` represents the corners, and  ` (space)` 
represents the rooms and removed walls. When using the search algorithms to solve the maze, `0` and other numbers represent
the order in which each room is visited and `#` represents the visited rooms. Both of them highlight the path from start to finish.
Rooms are vertices and removed walls between two adjacent rooms indicate that there exists an edge between the two vertices.

![ASCII maze](https://i.imgur.com/vkQDu6c.png)
![ASCII maze](https://i.imgur.com/n9J8BoR.png)
![ASCII maze](https://i.imgur.com/trQ0vpt.png)

### Maze Solver

After the maze has been generated and printed, the program solves it. The program uses both `breadth-first search` and `depth-first search`
to solve the maze and prints both solution side-by-side. The search algorithms begin at the starting point and terminates as soon as they
reach to the ending point.

Here is a code snippet of the breadth-first search algorithm to solve the maze:

```Java
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
```


## Team Members
* [Chris Vo](https://www.linkedin.com/in/chris-vo-/)
* [Wendy Chen](https://www.linkedin.com/in/wendy-chen-639bb7170/)
