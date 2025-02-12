package assign05;

import java.util.ArrayList;

public class QuickSorter<E extends Comparable<? super E>> implements Sorter<E>{

	private PivotChooser<E> chooser;
	
	public QuickSorter(PivotChooser<E> chooser) {
		this.chooser = chooser;
	}
	public QuickSorter() {
		this.chooser = null;
	}
	
	@Override
	public void sort(ArrayList<E> list) {
		quickSort(list, 0, list.size() - 1);
	}
	
	private void quickSort(ArrayList<E> list, int low, int high) {
		if(high - low < 1)
			return;
		int pivot = partition(list, low, high);
		quickSort(list, low, pivot-1);
		quickSort(list, pivot+1, high);
		
	}
	
	private  int partition(ArrayList<E> list, int low, int high) {
//		//int pivot = chooser.getPivotIndex(list, low, high);
//		int pivot = high;
//		
//		int k = low-1;
//		
//		for(int j = low; j< high; j ++) {
//			if(list.get(j).compareTo(list.get(pivot)) < 0){
//				k++;
//				swap(list, k, j);
//				
//			}
//		swap(list, k+1, high);
//		}
//		return k+1;
		
		int pivot = low + (high - low) / 2;
		swap(list, pivot, high);
		int i = low;
		int j = high - 1;
		
		while(true) {
			while(list.get(i).compareTo(list.get(pivot)) < 0 && i <= high)
				i++;
			while(list.get(j).compareTo(list.get(pivot)) > 0 && j >= low)
				j--;
			if(i >= j)
				break;
			swap(list, i, j);
			i++;
			j--;
		}
		swap(list, i, high);
		return i + 1;
	}
	
	private void swap(ArrayList<E> list, int i, int j) {
		E temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}
} 
