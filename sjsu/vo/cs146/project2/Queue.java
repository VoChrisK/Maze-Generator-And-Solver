package sjsu.vo.cs146.project2;

import sjsu.vo.cs146.project2.LinkedList.Node;

/**
 * This class constructs a FIFO queue as a linked list.
 * @authors Chris Vo and Wendy Chen
 * @Version 1.0
 */
public class Queue {
	//class variable declarations
	private Node head; //pointer to point the first node in the queue
	private Node tail; //pointer to point the last node in the queue
	private int size; //the total number of nodes in the queue
	
	/**
	 * Constructor #1: This constructor creates a new instance of Queue with class variables initialized to 0 or equivalent.
	 * @param none
	 * @return none
	 */
	public Queue() {
		head = new Node();
		tail = new Node();
		tail.setNext(head); //have the tail point to the head instead of null
		size = 0;
	}
	
	/**
	 * This method checks whether the queue has nodes or not
	 * @param none
	 * @return boolean value - true if the queue has no nodes, false otherwise
	 */
	public boolean IsEmpty() {
		if(size == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * This method appends a new node to the front of the queue
	 * @param node - the node to be added in the queue
	 * @return none
	 */
	public void Enqueue(Node node) {
		if(size < 1) //if there are no nodes in the list, set tail to point to the new node, since it will be the first one to be removed
			tail.setNext(node);
		else //else have the node the head is currently pointing to connect to the new node
			head.getNext().setPrev(node);
		node.setNext(head.getNext()); //have the node point to the node the head is currently pointing to
		node.setPrev(head); //have the node point to the head
		head.setNext(node); //have the head point to the node
		size++; //increment size of the queue by 1
	}
	
	/**
	 * This method returns the first node in the queue and remove it from the queue
	 * @param none
	 * @return Node - the first node in the queue
	 */
	public Node Dequeue() {
		Node node = tail.getNext(); //store the first node in the queue in a temporary variable
		tail.setNext(tail.getNext().getPrev()); //tail points to the node before the first node
		tail.getNext().setNext(null); //set the dequeued node to null, effectively deleting it
		size--; //decrement size of the queue by 1
		return node;
	}
}
