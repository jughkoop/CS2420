package assign08;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Implements a binary search tree (BST), a special type of graph
 * where each vertex (Node) points only up to two other Nodes.
 * All "left" Nodes are lesser than its parent, while all
 * "right" Nodes are greater than its parent.
 * 
 * @author Daniel Lee and Mi Zeng and CS 2420 staff
 * @version 3-17-2025
 * @param <Type> - the type of data that the BST stores
 */
public class BinarySearchTree<Type extends Comparable<? super Type>> implements SortedSet<Type>{
	private Node head;
	private int size;
	
	/**
	 * Creates a new BinarySearchTree.
	 */
	public BinarySearchTree() {
		head = null;
		size = 0;
	}
	
	/**
	 * Adds the given item to the BST.
	 * 
	 * @param item - the item to add to the BST
	 * @return true if the item was successfully added to the BST,
	 * 		   false if the BST already contains the item or the given
	 * 		   item to add was null
	 */
	@Override
	public boolean add(Type item) {
		if(item == null || contains(item))
			return false;
		
		// if the BST is empty, add the given item to the head Node
		if(isEmpty()) {
			head = new Node(item);
			
			size++;
			return true;
		}
		
		return addRecursive(item, head, null, 0);
	}
	
	/**
	 * Helper recursive method that traverses the BST
	 * and adds the given item into a valid location.
	 * 
	 * @param item - the item to add to the BST
	 * @param current - the current Node of the BST being looked at
	 * @param parent - where the current Node came from
	 * @param direction - an int that indicates if the current Node is 
	 * 					  a left child (-1) or right child (1)
	 * @return true once the item is added successfully into the BST
	 */
	private boolean addRecursive(Type item, Node current, Node parent, int direction) {
		// if the current Node is null, the method has traversed to the proper location
		// to add the item
		if(current == null) {
			// updates current to hold the given item and sets
			// its parent
			current = new Node(item);
			current.parent = parent;
			// if direction is negative current is its parent's left child
			if(direction < 0)
				parent.leftChild = current;
			// if direction is positive current is its parent's right child
			else
				parent.rightChild = current;
			
			size++;
			return true;
		}
		
		if(item.compareTo(current.data) < 0)
			// update direction to -1 to indicate left
			return addRecursive(item, current.leftChild, current, -1);
		else
			// update direction to 1 to indicate going right
			return addRecursive(item, current.rightChild, current, 1);
	}

	/**
	 * Adds the given collection of items into the BST.
	 */
	@Override
	public void addAll(Collection<? extends Type> items) {
		for(Type item : items)
			add(item);
	}

	/**
	 * Checks if the BST holds the given item.
	 * 
	 * @param item - the item to check if the BST holds
	 * @return true if the given item is present in the BST,
	 * 		   false if the given item is not present in the BST,
	 * 		   the given item is null or the BST is empty
	 */
	@Override
	public boolean contains(Type item) {
		if(item == null || isEmpty())
			return false;
		
		Node found = search(item, head);
		// if the Node returned is null, the BST doesn't hold this item
		if(found == null)
			return false;
		// otherwise it does hold this item
		return true;
	
	}
	
	/**
	 * Helper recursive method that finds the Node that holds 
	 * the given item.
	 * 
	 * @param item - the item to remove from the BST
	 * @param current - the current Node being looked at
	 * @return the Node that holds the given item, or null if the BST
	 * 		   doesn't contain a Node that holds the given item
	 */
	private Node search(Type item, Node current) {
		if(current == null || current.data.equals(item))
			return current;
		
		if(item.compareTo(current.data) < 0)
			return search(item, current.leftChild);
		else
			return search(item, current.rightChild);
	}

	/**
	 * Removes the given item from the BST.
	 * 
	 * @param item - the item to remove from the BST
	 * @return true if the item is successfully removed from the BST,
	 * 		   false if the BST doesn't already contain the item
	 */
	@Override
	public boolean remove(Type item) {
		if(!contains(item))
			return false;
		
		// finds the Node that contains the item to remove and its parent Node
		Node toRemove = search(item, head);
		Node parentToRemove = toRemove.parent;
		
		// case where the Node to remove has 0 children
		if(toRemove.leftChild == null && toRemove.rightChild == null) {
			// if the Node to remove is the head
			if(head.data.equals(item))
				head = null;
			// if the Node to remove is parent's left child
			else if(toRemove.checkDirection())
				parentToRemove.leftChild = null;
			// if the Node to remove is parent's right child
			else
				parentToRemove.rightChild = null;
		}
		
		// case where the Node to remove has only a right child
		else if(toRemove.leftChild == null) {
			// if the Node to remove is the head
			if(head.data.equals(item))
				head = toRemove.rightChild;
			// if the Node to remove is the parent's left child
			else if(toRemove.checkDirection())
				parentToRemove.leftChild = toRemove.rightChild;
			// if the Node to remove is the parent's right child
			else
				parentToRemove.rightChild = toRemove.rightChild;
			toRemove.rightChild.parent = parentToRemove;
		}
		// case where the Node to remove only has a left child
		else if(toRemove.rightChild == null) {
			// if the Node to remove is the head
			if(head.data.equals(item))
				head = toRemove.leftChild;
			// if the Node to remove is the parent's left child
			else if(toRemove.checkDirection())
				parentToRemove.leftChild = toRemove.leftChild;
			// if the Node to remove is the parent's right child
			else
				parentToRemove.rightChild = toRemove.leftChild;
			toRemove.leftChild.parent = parentToRemove;
		}
		
		// case where the Node to remove has 2 children
		else {
			// finds the successor of the Node to remove
			Type successorData = toRemove.getSuccessor().data;
			// removes the successor Node from the BST
			remove(successorData);
			// updates the data in the Node to remove to the successor Node's data
			toRemove.data = successorData;
		}
		
		size--;
		return true;
	}
	
	/**
	 * Returns the first (i.e., smallest) item in the BST.
	 * 
	 * @throws NoSuchElementException if the set is empty
	 */
	@Override
	public Type first() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		
		return head.getLeftmostNode().data;
	}

	/**
	 * Returns the last (i.e., largest) item in the BST.
	 * 
	 * @throws NoSuchElementException if the set is empty
	 */
	@Override
	public Type last() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		
		return head.getRightmostNode().data;
	}

	/**
	 * Returns the number of items in the BST.
	 * 
	 * @return the number of items in the BST
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Checks if the BST is empty.
	 * 
	 * @return true if the size of the BST is 0,
	 * 		   false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Clears the BST.
	 */
	@Override
	public void clear() {
		head = null;
		size = 0;
	}
	
	/**
	 * Creates and returns an iterator to iterate through the BST.
	 * 
	 * @return an iterator to iterate through the BST
	 */
	@Override
	public Iterator<Type> iterator() {
		Iterator<Type> iterator = new BSTIterator();
		
		return iterator;
	}
	
	
	/**
	 * Represents a singular Node of the BST.
	 * Each Node of the BST holds a data,
	 * and references to its left child, right child, and parent.
	 */
	private class Node {
		Type data;
		Node leftChild;
		Node rightChild;
		Node parent;
		
		/**
		 * Creates a new Node that holds the given data.
		 * 
		 * @param data - the data the Node will hold
		 */
		public Node(Type data) {
			this.data = data;
			leftChild = null;
			rightChild = null;
			parent = null;
		}
		
		/**
		 * Gets the reference to the leftmost Node in the binary tree rooted at this binary Node.
		 * 
		 * @return reference to the leftmost Node in the binary tree rooted at this Node
		 */
		public Node getLeftmostNode() {
			if(leftChild == null)
				return this;
			
			return leftChild.getLeftmostNode();
		}

		/**
		 * Gets the reference to the rightmost Node in the binary tree rooted at this binary Node.
		 * 
		 * @return reference to the rightmost Node in the binary tree rooted at this Node
		 */
		public Node getRightmostNode() {
			if(rightChild == null)
				return this;
			
			return rightChild.getRightmostNode();
		}
		
		/**
		 * Gets the successor of this Node.
		 * The successor of any Node is the leftmost Node of this Node's
		 * right child's subtree.
		 * There is no successor if this Node has no right child.
		 * 
		 * @return the successor of this Node, or null if this Node has 
		 * 		   no right child
		 */
		public Node getSuccessor() {
			if(rightChild == null)
				return null;
			
			return rightChild.getLeftmostNode();
		}
		
		/**
		 * Determines if this Node is its parent's left child or right child.
		 * 
		 * @return true if this Node is its parent's left child,
		 * 		   false if it is its parent's right child
		 */
		public boolean checkDirection() {
			if(parent.leftChild != null && parent.leftChild.data.equals(data))
				return true;
			return false;
		}
		
	}
	
	/**
	 * A custom Iterator designed to iterate through all elements
	 * of the BST from smallest to largest by traversing the BST inorder.
	 */
	private class BSTIterator implements Iterator<Type> {
		private int index;
		private boolean canRemove;
		private Node current;
		
		/**
		 * Creates a new BSTIterator.
		 */
		public BSTIterator() {
			index = 0;
			canRemove = false;
			current = null;
		}
		
		/**
		 * Checks if there is a next element.
		 * 
		 * @return true if the current index flag is less than 
		 * 		   the size of the BST,
		 * 		   false otherwise
		 */
		@Override
		public boolean hasNext() {
			return index < size;
		}
		
		/**
		 * Returns the next element.
		 * 
		 * @return the next element of the BST
		 * @throws NoSuchElementException if there is no next element
		 */
		@Override
		public Type next() throws NoSuchElementException {
			if(!hasNext())
				throw new NoSuchElementException();
			
			// the first element is always the leftmost Node from the head Node
			if(index == 0)
				current = head.getLeftmostNode();
			// if the current Node has no right child
			else if(current.rightChild == null) {
				// if current is the left child of its parent, the next Node is its parent
				if(current.checkDirection())
					current = current.parent;
				// if current is the right child of its parent, travel up the BST until an
				// ancestor that is greater than current is reached, the next Node is this ancestor
				else {
					while(current.data.compareTo(current.parent.data) > 0)
						current = current.parent;
					current = current.parent;
				}
			}
			// if current has a right child the next Node is its successor
			else
				current = current.getSuccessor();
			
			index++;
			// the Node can now be removed
			canRemove = true;
			
			return current.data;
		}
		
		/**
		 * Removes this element from the BST.
		 * 
		 * @throws IllegalStateException if next was not previously called
		 */
		public void remove() throws IllegalStateException {
			if(!canRemove)
				throw new IllegalStateException();
			
			// calls the BST remove method to remove the current Node
			BinarySearchTree.this.remove(current.data);
			index--;
		}
	}

}
