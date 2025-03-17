package assign08;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Implements a binary search tree (BST), a special type of graph
 * where each vertex (node) points only up to two other nodes.
 * All "left" nodes are lesser than its parent, while all
 * "right" nodes are greater than its parent.
 * 
 * @author Daniel Lee and Mi Zeng
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
		
		return addRecursive(item, head, null, 0);
	}
	
	/**
	 * Helper recursive method that traverses the BST
	 * and adds the given item into a valid location.
	 * 
	 * @param item - the item to add to the BST
	 * @param current - the current node of the BST being looked at
	 * @param parent - where the current node came from
	 * @param direction - an int that indicates if the current node is 
	 * 					  a left child (-1), right child (1) or the head (0)
	 * @return true once the item is added successfully into the BST
	 */
	private boolean addRecursive(Type item, Node current, Node parent, int direction) {
		// if the current node is null, the method has traversed to the proper location
		// to add the item
		if(current == null) {
			// updates current to hold the given item and sets
			// its parent
			current = new Node(item);
			current.parent = parent;
			// adds current into the bst as a left child, right child
			// of the parent, or the head of the BST
			if(direction == -1)
				parent.leftChild = current;
			else if(direction == 1)
				parent.rightChild = current;
			else
				head = current;
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
		
		return containsRecursive(item, head);
	
	}
	
	/**
	 * Helper recursive method that traverses the BST and checks
	 * if the BST holds the given item.
	 * 
	 * @param item - the item the check if the BST holds
	 * @param current - the current node being looked at
	 * @return true if the data in the current node is equal to the given item,
	 * 		   false if the method has traversed to where the item should be in
	 * 		   the BST but the current node is null
	 */
	private boolean containsRecursive(Type item, Node current) {
		if(current == null)
			return false;
		if(current.data.equals(item))
			return true;
		
		if(item.compareTo(current.data) < 0)
			return containsRecursive(item, current.leftChild);
		else
			return containsRecursive(item, current.rightChild);
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
		
		// finds the node that contains the item to remove and its parent node
		Node toRemove = searchRemove(item, head);
		Node parentToRemove = toRemove.parent;
		
		// case where the node to remove has 0 children
		if(toRemove.leftChild == null && toRemove.rightChild == null) {
			// if the node to remove is parent's left child
			if(parentToRemove.leftChild != null && parentToRemove.leftChild.data.equals(toRemove.data))
				parentToRemove.leftChild = null;
			// if the node to remove is parent's right child
			else
				parentToRemove.rightChild = null;
			size--;
			return true;
		}
		
		// case where the node to remove has only a right child
		if(toRemove.leftChild == null) {
			if(head.data.equals(item))
				head = toRemove.rightChild;
			// if the node to remove is the parent's left child
			if(parentToRemove.leftChild != null && parentToRemove.leftChild.data.equals(toRemove.data))
				parentToRemove.leftChild = toRemove.rightChild;
			// if the node to remove is the parent's right child
			else
				parentToRemove.rightChild = toRemove.rightChild;
			toRemove.rightChild.parent = parentToRemove;
			size--;
			return true;
		}
		// case where the node to remove only has a left child
		if(toRemove.rightChild == null) {
			if(head.data.equals(item))
				head = toRemove.leftChild;
			// if the node to remove is the parent's left child
			if(parentToRemove.leftChild != null && parentToRemove.leftChild.data.equals(toRemove.data))
				parentToRemove.leftChild = toRemove.leftChild;
			// if the node to remove is the parent's right child
			else
				parentToRemove.rightChild = toRemove.leftChild;
			toRemove.leftChild.parent = parentToRemove;
			size--;
			return true;
		}
		
		// case where the node to remove has 2 children
		// finds the successor of the node to remove and removes the
		// successor node from the BST
		Type successorData = toRemove.getSuccessor().data;
		remove(successorData);
		// updates the data in the node to remove to the successor node's data
		toRemove.data = successorData;
		return true;
	}
	
	/**
	 * Helper recursive method that finds the node that holds 
	 * the given item to remove from the BST.
	 * Assumes that the BST contains this item.
	 * 
	 * @param item
	 * @param current
	 * @return
	 */
	private Node searchRemove(Type item, Node current) {
		if(current.data.equals(item))
			return current;
		
		if(item.compareTo(current.data) < 0)
			return searchRemove(item, current.leftChild);
		else
			return searchRemove(item, current.rightChild);
	}

	@Override
	public Type first() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		
		return head.getLeftmostNode().data;
	}

	@Override
	public Type last() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		
		return head.getRightmostNode().data;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		head = null;
		size = 0;
	}
	
	@Override
	public Iterator<Type> iterator() {
		Iterator<Type> iterator = new BSTIterator();
		return iterator;
	}
	

	private class Node {
		Type data;
		Node leftChild;
		Node rightChild;
		Node parent;
		
		public Node(Type data) {
			this.data = data;
			leftChild = null;
			rightChild = null;
			parent = null;
		}
		
		/**
		 * Gets the reference to the leftmost node in the binary tree rooted at this binary node.
		 * 
		 * @return reference to the leftmost node in the binary tree rooted at this node
		 */
		public Node getLeftmostNode() {
			if(leftChild == null)
				return this;
			return leftChild.getLeftmostNode();
		}

		/**
		 * Gets the reference to the rightmost node in the binary tree rooted at this binary node.
		 * 
		 * @return reference to the rightmost node in the binary tree rooted at this node
		 */
		public Node getRightmostNode() {
			if(rightChild == null)
				return this;
			return rightChild.getRightmostNode();
		}
		
		/**
		 * Gets the height of the binary tree rooted at this binary node, where the height of a 
		 * tree is the length of the longest path to a leaf node (e.g., a tree with a single 
		 * node has a height of zero).
		 * 
		 * @return the height of the binary tree rooted at this node
		 */
		public int height() {
			if(leftChild == null && rightChild == null)
				return 0;
			else if(leftChild == null)
				return 1 + rightChild.height();
			else if (rightChild == null)
				return 1 + leftChild.height();
			else
				return 1 + Math.max(leftChild.height(), rightChild.height());
		}
		
		/**
		 * Helper method that gets the successor of this node.
		 * The successor of any node is the leftmost node of this node's
		 * right child's subtree.
		 * There is no successor if this node has no right child.
		 * 
		 * @return the successor of this node, or null if this node has 
		 * 		   no right child
		 */
		public Node getSuccessor() {
			if(rightChild == null)
				return null;
			return rightChild.getLeftmostNode();
		}
		
	}
	
	
	private class BSTIterator implements Iterator<Type> {
		private int index;
		private boolean canRemove;
		private Node current;
		
		public BSTIterator() {
			index = 0;
			canRemove = false;
			current = null;
		}
		
		@Override
		public boolean hasNext() {
			return index < size;
		}
		
		@Override
		public Type next() throws NoSuchElementException {
			if(!hasNext())
				throw new NoSuchElementException();
			
			
			if(index == 0)
				current = head.getLeftmostNode();
			else if(current.rightChild == null) {
				if(current.parent.leftChild != null && current.parent.leftChild.data.equals(current.data))
					current = current.parent;
				else {
					while(current.data.compareTo(current.parent.data) > 0)
						current = current.parent;
					current = current.parent;
				}
			}
			else
				current = current.getSuccessor();
			
			index++;
			canRemove = true;
			return current.data;
		}
		
		public void remove() throws IllegalStateException {
			if(!canRemove)
				throw new IllegalStateException();
			
			BinarySearchTree.this.remove(current.data);
			index--;
		}
	}

}
