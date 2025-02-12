package assign05;

import java.util.ArrayList;

public class MergeSorter<E extends Comparable<? super E>> implements Sorter<E>{
	private int threshold;
	
	public MergeSorter(int threshold) throws IllegalArgumentException {
		if(threshold < 0)
			throw new IllegalArgumentException();
		this.threshold = threshold;
	}

	@Override
	public void sort(ArrayList<E> list) {
		if(list.size() < threshold)
			threshold = list.size();
		
		ArrayList<E> listCopy = new ArrayList<E>();
		for(int i = 0; i < list.size(); i++)
			listCopy.add(list.get(i));
		
		mergeSort(list, listCopy, 0, list.size() - 1);
	}
	
	
	
	private void mergeSort(ArrayList<E> list, ArrayList<E> listCopy, int low, int high) {
		if(high - low < 1) {
			return;
		}
		if(high - low < threshold) {
			insertionSort(list, low, high);
		}
		
		int mid = low+ (high-low)/2;
		mergeSort(list, listCopy, low,mid);
		mergeSort(list, listCopy, mid+1, high);
		merge(list,listCopy,low, mid , high);
	}
	
	private void merge(ArrayList<E> list, ArrayList<E> listCopy, int low, int middle, int high) {
		int i = low;
		int j = middle+1;
		int k = low;
		while(i <= middle && j<= high ) {
			if(list.get(i).compareTo(list.get(j))<0) {
				listCopy.set(k, list.get(i));
				k++;
				i++;
			}else {
				listCopy.set(k, list.get(j));
				k++;
				j++;
			}
			
		}
		
		while(i <= middle) {
			listCopy.set(k, list.get(i));
			i++;
			k++;
		}
		
		while(j <= high) {
			listCopy.set(k, list.get(j));
			j++;
			k++;
		}
		
		for(int n = low; n <= high; n++)
			list.set(n, listCopy.get(n));
	}
	
	private void insertionSort(ArrayList<E> list, int low, int high) {
		for (int i = low; i <= high; i++) {
			E temp = list.get(i);
			int j;
			for (j = i - 1; j >= 0 && list.get(j).compareTo(temp) > 0; j--)
				list.set(j + 1, list.get(j));
			list.set(j + 1, temp);
		}
	}

	
	
	
}
