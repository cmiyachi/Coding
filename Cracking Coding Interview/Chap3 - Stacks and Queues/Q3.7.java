/*
An animal shelter holds only dogs and cats, and operates on a strictly "first in, first out" basis. 
People must adopt either the "oldest" (based on arrival time) of all animals at the shelter, or they can 
select whether they would prefer a dog or a cat (and will receive the oldest animal of that type). 
They cannot select which specific animal they would like. Create the data structures to maintain 
this system and implement opera- tions such as enqueue, dequeueAny, dequeueDog and dequeueCat. 
You may use the built-in LinkedList data structure.
*/

import java.util.*;

class MyNode {
	String type;
	String name;
	MyNode next;

	public MyNode(String type, String name) {
		this.name = name;
		this.type = type;
	}

	public String toString() {
		return "(" + type + ", " + name + ")";
	}
}

class MyQueue {
	MyNode front, tail;

	public void enqueue(String type, String name) {
		MyNode node = new MyNode(type, name);
		if(front == null) {
			front = node;
			tail = node;
		}
		else {
			tail.next = node;
			tail = node;
		}
	}

	public String dequeueAny() {
		MyNode node = front;
		front = front.next;
		if(front == null)
			tail = null;
		return node.toString();
	}

	public String dequeueDog() {
		MyNode current = front;
		String pet = "";
		if(current != null) {
			if(current.type.equals("dog"))
				return dequeueAny();
			while(current.next != null) {
				if(current.next.type.equals("dog")) {
					pet = current.next.toString();
					current.next = current.next.next;
					break;
				}
				current = current.next;
			}
		}
		return pet;
	}

	public String dequeueCat() {
		MyNode current = front;
		String pet = "";
		if(current != null) {
			if(current.type.equals("cat"))
				return dequeueAny();
			while(current.next != null) {
				if(current.next.type.equals("cat")) {
					pet = current.next.toString();
					current.next = current.next.next;
					break;
				}
				current = current.next;
			}
		}
		return pet;
	}

	public boolean isEmpty() {
		return front == null;
	}

	public void display() {
		MyNode current = front;
		while(current != null) {
			System.out.print(current.toString() + "\t");
			current = current.next;
		}
		System.out.println();
	}
}

class Solution {
	public static void cong(String pet) {
		System.out.println();
		System.out.println("Congratulation! You get " + pet);
	}

	public static void main(String[] args) {
		MyQueue shelterQ = new MyQueue();

		shelterQ.enqueue("dog", "d1");
		shelterQ.enqueue("dog", "d2");
		shelterQ.enqueue("cat", "c1");
		shelterQ.enqueue("dog", "d3");
		shelterQ.enqueue("cat", "c2");
		shelterQ.enqueue("cat", "c3");
		shelterQ.display();

		cong(shelterQ.dequeueAny());
		shelterQ.display();

		cong(shelterQ.dequeueCat());
		shelterQ.display();

		cong(shelterQ.dequeueCat());
		shelterQ.display();

		cong(shelterQ.dequeueDog());
		shelterQ.display();

		cong(shelterQ.dequeueAny());
		shelterQ.display();

	}
}





