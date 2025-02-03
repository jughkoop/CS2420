package assign04;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;

public class LargestNumberSolver {
	
	public static <T> void insertionSort(T[] arr, Comparator<? super T> cmp) {
		for(int i = 1; i < arr.length - 1; i++) {
			T temp = arr[i];
			for(int j = i - 1; j >= 0 && cmp.compare(arr[i - 1], arr[i]) > 0; j--) {
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
	}
	
	public static BigInteger findLargestNumber(Integer[] arr) {
		// copy arr to new array arrayCopy
		Integer[] arrayCopy = new Integer[arr.length];
		for(int i = 0; i < arr.length; i++)
			arrayCopy[i] = arr[i];
		// sorts the array copy
		insertionSort(arrayCopy, (i, j) -> Integer.parseInt(intConcatenate(i, j)) - Integer.parseInt(intConcatenate(j, i)));
		BigInteger bigInt = BigInteger.valueOf(arrayCopy[0]);
		// concatenates each value of the sorted array into a BigInteger
		for(int i = 1; i < arr.length; i ++)
			bigInt = BigInteger.valueOf(Integer.parseInt((intConcatenate(bigInt, arrayCopy[i]))));
		
		return bigInt;
	}
	
	public static int findLargestInt(Integer[] arr) throws OutOfRangeException {
		// copy arr to new array arrayCopy
				Integer[] arrayCopy = new Integer[arr.length];
				for(int i = 0; i < arr.length; i++)
					arrayCopy[i] = arr[i];
				// sorts the array copy
				insertionSort(arrayCopy, (i, j) -> Integer.parseInt(intConcatenate(i, j)) - Integer.parseInt(intConcatenate(j, i)));
				int largestInt = arrayCopy[0];
				// concatenates each value of the sorted array into a BigInteger
				for(int i = 1; i < arr.length; i++) {
					if(Integer.parseInt((intConcatenate(largestInt, arrayCopy[i]))) > Integer.MAX_VALUE)
						throw new OutOfRangeException("int");
					largestInt = Integer.parseInt((intConcatenate(largestInt, arrayCopy[i])));
				}
				
				return largestInt;
		
	}
	
	public static long findLargestLong(Integer[] arr) throws OutOfRangeException {
		// copy arr to new array arrayCopy
		Long[] arrayCopy = new Long[arr.length];
		for(int i = 0; i < arr.length; i++)
			arrayCopy[i] = (long)arr[i];
		// sorts the array copy
		insertionSort(arrayCopy, (i, j) -> Integer.parseInt(intConcatenate(i, j)) - Integer.parseInt(intConcatenate(j, i)));
		long largestLong = arrayCopy[0];
		// concatenates each value of the sorted array into a BigInteger
		for(int i = 1; i < arr.length; i++) {
			if(Integer.parseInt((intConcatenate(largestLong, arrayCopy[i]))) > Long.MAX_VALUE)
				throw new OutOfRangeException("int");
			largestInt = Integer.parseInt((intConcatenate(largestInt, arrayCopy[i])));
		}
		
		return largestInt;
	}
	
	public static BigInteger sum(List<Integer[]> list) {
		
	}
	
	public static Integer[] findKthLargest(List<Integer[]> list, int k) {
		
	}
	
	public static List<Integer[]> readFile(String filename) {
		
	}
	
	private static <T> String intConcatenate(T a, T b) {
		return a.toString() + b.toString();
	}
}
