package sjsu.vo.cs146.project2;


import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {
	Graph graph;
	Maze maze;
	Solve solve;
	Scanner in;
	int input;
	
	@Before
	public void setUp() {
		in = new Scanner(System.in);
		System.out.println("Please enter a number (4,5,6,7,8,9,10):");
		System.out.print("Enter: ");
		int input = in.nextInt();
		assertTrue("Please enter a number within the given set next time.", (input >= 4 && input <= 10));
		graph = new Graph(input);
		maze = new Maze(graph);
		solve = new Solve(maze);
	}

	@Test
	public void test() {
		maze.GenerateMaze();
		solve.BreathFirstSearch();
		solve.BFSShortestPath();
		solve.DepthFirstSearch();
		solve.DFSShortestPath();
	}
}
