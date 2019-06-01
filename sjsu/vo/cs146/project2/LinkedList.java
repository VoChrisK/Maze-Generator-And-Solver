package sjsu.vo.cs146.project2;

enum Color { //colors to check whether the node has been discovered or not
	WHITE, GREY, BLACK //WHITE is undiscovered, GREY is discovered but not fully explored, BLACK is fully explored
}

/**
 * This class defines a Linked List to be used in the Adjacency List
 * @authors Chris Vo and Wendy Chen
 * @Version 1.0
 */
public class LinkedList {
	//class variable declarations
	private Node head; //a pointer to always point to the first node in the list.
	private Node pointer; //to point to the current node in the list
	private Node current; //to store the ID of the vertex in the maze
	private int size; //the total number of nodes in the list
	private int charindex; //the room # of the node in the ASCII maze
	
	/**
	 * Constructor #1: This constructor creates a new instance of LinkedList with class variables initialized to 0 or equivalent.
	 * @param none
	 * @return none
	 */
	public LinkedList() {
		head = new Node();
		pointer = new Node();
		current = new Node();
		size = 0;
		charindex = 0;
	}
	
	/**
	 * Constructor #2: This constructor creates a new instance of LinkedList, setting the vertex's ID
	 * to distinguish between each list
	 * @param ID - the node's identity number
	 * @return none
	 */
	public LinkedList(int ID) {
		head = new Node();
		pointer = new Node();
		current = new Node(ID);
		size = 0;
		charindex = 0;
	}
	
	/**
	 * This method returns the size of the list
	 * @param none
	 * @return size - the total number of nodes in the list
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * This method returns the pointer of the list
	 * @param none
	 * @return pointer - a Node that point to the current node in the list
	 */
	public Node getPointer() {
		return pointer;
	}
	
	/**
	 * This method sets the pointer to the next node (or the head) in the list
	 * @param pointer - a Node that point to the current node in the list
	 * @return none
	 */
	public void setPointer(Node next) {
		this.pointer = next;
	}
	
	/**
	 * This method returns the head of the list
	 * @param none
	 * @return head - a Node that references the first node in the list
	 */
	public Node getHead() {
		return head;
	}
	
	/**
	 * This method returns a vertex in the adjacency list representation of the graph
	 * @param none
	 * @return current - a vertex in the graph
	 */
	public Node getCurrent() {
		return current;
	}
	
	/**
	 * This method sets the value of CharIndex to the value of the parameter
	 * @param CharIndex - the room number of the node
	 * @return none
	 */
	public void setCharIndex(int charindex) {
		this.charindex = charindex;
	}
	
	/**
	 * This method returns the value of CharIndex
	 * @param none
	 * @return CharIndex - the room number of the node
	 */
	public int getCharIndex() {
		return charindex;
	}
	
	/**
	 * This method checks whether the list has nodes or not
	 * @param none
	 * @return boolean value - true if the list has no nodes, false otherwise
	 */
	public boolean isEmpty() {
		if(size == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * This method appends a new node to the front of the list
	 * @param neighbor - a node the current vertex shares an edge with
	 * @return none
	 */
	public void addNode(Node neighbor) {
		neighbor.next = head.next; //the new node points to the node head is currently pointing
		head.next = neighbor; //the head will point to the new node
		size++; //increments the total amount of nodes in the list
	}
	
	/**
	 * This inner class defines a customized node for the Linked List class
	 * @authors Chris Vo and Wendy Chen
	 * @Version 1.0
	 */
	public static class Node {
		//class variable declarations
		private Node next; //points to the vertex it shares an edge with
		private Node prev; //points to the previous node. This is used for a Queue
		private Color color; //color of the node to remember traversal order
		private Node parent; //the parent node of the current node
		private int start;
		private int finish;
		private int ID; //used to identify the nodes in the maze
		
		/**
		 * Constructor #1: This constructor creates a new instance of Node with class variables initialized to 0 or equivalent.
		 * @param none
		 * @return none
		 */
		public Node() {
			next = null;
			prev = null;
			parent = null;
			start = 0;
			finish = 0;
			color = Color.WHITE;
			ID = 0;
		}
		
		/**
		 * Constructor #2: This constructor creates a new instance of Node and initializes it's ID while setting
		 * all other variables to 0 or equivalent
		 * @param ID - the node's identity
		 * @return none
		 */
		public Node(int ID) {
			next = null;
			prev = null;
			parent = null;
			start = 0;
			finish = 0;
			color = Color.WHITE;
			this.ID = ID;
		}
		
		/**
		 * This method sets the node's ID to a new value
		 * @param ID - the node's identity number
		 * @return none
		 */
		public void setID(int ID) {
			this.ID = ID;
		}
		
		/**
		 * This method returns the node's ID
		 * @param none
		 * @return ID - the node's identity number
		 */
		public int getID() {
			return ID;
		}
		
		/**
		 * This method connects the current node to an indicated node
		 * @param next - a node the current node points to
		 * @return none
		 */
		public void setNext(Node next) {
			this.next = next;
		}
		
		/**
		 * This method returns the node the current node points to
		 * @param none
		 * @return next - a node the current node points to
		 */
		public Node getNext() {
			return next;
		}
		
		/**
		 * This method connects the current node to an indicated node
		 * @param prev - a node the current node points to
		 * @return none
		 */
		public void setPrev(Node prev) {
			this.prev = prev;
		}
		
		/**
		 * This method returns the node the current node points to
		 * @param none
		 * @return prev - a node the current node points to
		 */
		public Node getPrev() {
			return prev;
		}
		
		/**
		 * This method sets the color of the node to a new value
		 * @param color - the color of the node to remember traversal order
		 * @return none
		 */
		public void setColor(Color color) {
			this.color = color;
		}
		
		/**
		 * This method returns the color of the node
		 * @param none
		 * @return color - the color of the node to remember traversal order
		 */
		public Color getColor() {
			return color;
		}
		
		/**
		 * This method returns the parent node
		 * @param none
		 * @return parent - the parent of this node
		 */
		public void setParent(Node parent) {
			this.parent = parent;
		}
		
		/**
		 * This method sets the parent node to a new value
		 * @param parent - the parent of this node
		 * @return none
		 */
		public Node getParent() {
			return parent;
		}
		
		public void setStart(int start) {
			this.start = start;
		}
		
		public int getStart() {
			return start;
		}
		
		public void setFinish(int finish) {
			this.finish = finish;
		}
		
		public int getFinish() {
			return finish;
		}
	}
}
