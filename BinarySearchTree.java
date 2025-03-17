package assign08;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinarySearchTree<Type extends Comparable<? super Type>> implements SortedSet<Type>{
	private Node head;
	private int size;
	
	public BinarySearchTree() {
		head = null;
		size = 0;
	}
	
	@Override
	public boolean add(Type item) {
		if(item == null || contains(item))
			return false;
		
		return addRecursive(item, head, null, 0);
	}
	
	private boolean addRecursive(Type item, Node current, Node parent, int direction) {
		if(current == null) {
			current = new Node(item);
			current.parent = parent;
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
			return addRecursive(item, current.leftChild, current, -1);
		else
			return addRecursive(item, current.rightChild, current, 1);
	}

	@Override
	public void addAll(Collection<? extends Type> items) {
		for(Type item : items)
			add(item);
	}

	@Override
	public boolean contains(Type item) {
		if(item == null || isEmpty())
			return false;
		
		return containsRecursive(item, head);
	
	}
	
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

	@Override
	public boolean remove(Type item) {
		if(!contains(item))
			return false;
		
		Node toRemove = searchRemove(item, head);
		Node parentToRemove = toRemove.parent;
		
		if(toRemove.leftChild == null && toRemove.rightChild == null) {
			if(parentToRemove.leftChild != null && parentToRemove.leftChild.data.equals(toRemove.data)) {
				parentToRemove.leftChild = null;
				size--;
				return true;
			}
			parentToRemove.rightChild = null;
			size--;
			return true;
		}
		
		if(toRemove.leftChild == null) {
			if(parentToRemove.leftChild != null && parentToRemove.leftChild.data.equals(toRemove.data))
				parentToRemove.leftChild = toRemove.rightChild;
			else
				parentToRemove.rightChild = toRemove.rightChild;
			toRemove.rightChild.parent = parentToRemove;
			size--;
			return true;
		}
		if(toRemove.rightChild == null) {
			if(parentToRemove.leftChild != null && parentToRemove.leftChild.data.equals(toRemove.data))
				parentToRemove.leftChild = toRemove.leftChild;
			else
				parentToRemove.rightChild = toRemove.leftChild;
			toRemove.leftChild.parent = parentToRemove;
			size--;
			return true;
		}
		
		Type successorData = toRemove.getSuccessor().data;
		remove(successorData);
		toRemove.data = successorData;
		return true;
	}
	
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
		private Node previous;
		
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
