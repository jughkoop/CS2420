package assign10;

import java.util.Comparator;
import java.util.NoSuchElementException;

import assign06.List;

public class BinaryMaxHeap<E> implements PriorityQueue<E> {
	private E[] heap;
	private int size;
	private int nextIndex;
	private Comparator<? super E> cmp;
	
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap() {
		heap = (E[]) new Object[16];
		size = 0;
		nextIndex = 1;
		cmp = (Comparator<? super E>) Comparator.naturalOrder();
	}
	
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap(Comparator<? super E> cmp) {
		heap = (E[]) new Object[16];
		size = 0;
		nextIndex = 1;
		this.cmp = cmp;
	}
	
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap(List<? extends E> list) {
		size = list.size();
		nextIndex = 1 + list.size();
		buildHeap(list);
		cmp = (Comparator<? super E>) Comparator.naturalOrder();
	}
	
	@Override
	public void add(E item) {
		heap[nextIndex] = item;
		percolateUp(nextIndex);
		
		nextIndex++;
		size++;
		
		if(size + 1 >= heap.length)
			resize();
	}

	@Override
	public E peek() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		
		return heap[1];
	}

	@Override
	public E extractMax() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		
		E max = heap[1];
		int lastIndex = nextIndex - 1;
		swap(1, lastIndex);
		percolateDown(1);
		
		heap[lastIndex] = null;
		size--;
		
		return max;
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
		nextIndex = 1;
		size = 0;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		for(int i = 0; i < size; i++)
			array[i] = heap[i + 1];
		
		return array;
	}
	
	private void percolateUp(int index) {
		int i = index;
		for(; i > 0 && cmp.compare(heap[i], heap[i / 2]) > 0; i /= 2)
			swap(i, i / 2);
	}
	
	private void percolateDown(int index) {
		int childIndex;
		if(cmp.compare(heap[2 * index], heap[(2 * index) + 1]) > 0)
			childIndex = 2 * index;
		else
			childIndex = (2 * index) + 1;
		
		int i = index;
		while(cmp.compare(heap[i], heap[childIndex]) < 0) {
			swap(i, childIndex);
			
			i = childIndex;
			if(2 * childIndex >= heap.length)
				break;
			else if(cmp.compare(heap[2 * childIndex], heap[(2 * childIndex) + 1]) > 0)
				childIndex *= 2;
			else
				childIndex = (2 * childIndex) + 1;
		}
	}
	
	private void swap(int i, int j) {
		E temp = heap[j];
		heap[j] = heap[i];
		heap[i] = temp;
	}
	
	@SuppressWarnings("unchecked")
	private void buildHeap(List<? extends E> list) {
		int height = (int) Math.floor(Math.log(list.size()));
		heap = (E[]) new Object[(int) (list.size() + 1 + Math.pow(2, height + 1))];
		
		for(int i = 1; i < heap.length; i++)
			heap[i] = list.get(i - 1);
		
		for(int i = (heap.length - 1) / 2; i > 0; i--)
			percolateDown(i);
	}
	
	@SuppressWarnings("unchecked")
	private void resize() {
		E[] newHeap = (E[]) new Object[(heap.length * 2) + 1];
		for(int i = 1; i < heap.length; i++)
			newHeap[i] = heap[i];
		heap = newHeap;
	}

}
