/**
 * @author Lina Radwan
 * CS2210 Assignment 2
 * This class will be used to contain the elements in the dictionary. It's somehow similar to a list 
 */
public class Node {
	//variables
	private String key;
	private int value;
	private Node next; 

	//a constructor that takes a key,value,and the next node as its parameters 
	//this will be helpful when using the insert method in the dictionary
	public Node(String key, int value, Node next) {
		this.key=key;
		this.value=value;
		this.next=next;
	}
	//Getters and Setters for the key, value and the next node.
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}


}
