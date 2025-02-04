package assign04;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class LargestNumberSolver {
	
	/**
	 * Sorts the given array with an insertion sort, sorting items
	 * by the given Comparator.
	 * 
	 * @param <T> the generic type placeholder
	 * @param arr - the given array
	 * @param cmp - the given Comparator
	 */
	public static <T> void insertionSort(T[] arr, Comparator<? super T> cmp) {
		for(int i = 1; i < arr.length; i++) {
			T temp = arr[i];
			int j;
			for(j = i - 1; j >= 0 && cmp.compare(arr[j], temp) > 0; j--) {
				arr[j + 1] = arr[j];
			}
			arr[j + 1] = temp;
		}
	}
	
	/**
	 * Finds the largest possible number that can be formed
	 * from the given array of Integers.
	 * Does not modify the given array.
	 * 
	 * @param arr - the given array of Integers
	 * @return a BigInteger that is the largest possible number formed
	 *         from the array
	 */
	public static BigInteger findLargestNumber(Integer[] arr) {
		// copy arr to new array arrayCopy
		Integer[] arrCopy = new Integer[arr.length];
		for(int i = 0; i < arr.length; i++)
			arrCopy[i] = arr[i];
		// sorts the array copy
		insertionSort(arrCopy, (i, j) -> new BigInteger(concatenate(j, i)).compareTo(new BigInteger(concatenate(i, j))));
		BigInteger bigInt = BigInteger.valueOf(0);
		// concatenates each value of the sorted array into a BigInteger
		for(int i = 0; i < arr.length; i ++)
			bigInt = new BigInteger(concatenate(bigInt, arrCopy[i]));
		return bigInt;
	}
	
	/**
	 * Finds the largest possible int that can be formed
	 * from the given array of Integers.
	 * Does not modify the given array.
	 * If the number formed is larger than the max int, it throws an OutOfRangeException.
	 * 
	 * @param arr - the given array of Integers
	 * @throws OutOfRangeException if the number formed is larger than 
	 *         the max int value
	 * @return an int that is the largest possible number formed
	 *         from the array
	 */
	public static int findLargestInt(Integer[] arr) throws OutOfRangeException {
		// copy arr to new array arrayCopy
		Integer[] arrayCopy = new Integer[arr.length];
		for(int i = 0; i < arr.length; i++)
			arrayCopy[i] = arr[i];
		// sorts the array copy
		insertionSort(arrayCopy, (i, j) -> Integer.parseInt(concatenate(j, i)) - Integer.parseInt(concatenate(i, j)));
		int largestInt = 0;
		String concat = "";
		// concatenates each value of the sorted array into an int
		for(int i = 0; i < arr.length; i++) {
			concat = concatenate(largestInt, arrayCopy[i]);
			// checks if the number is larger than the largest possible int value
			if(BigInteger.valueOf(Long.parseLong(concat)).compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0)
					throw new OutOfRangeException("int");
			largestInt = Integer.parseInt(concat);
		}
				
		return largestInt;
		
	}
	
	/**
	 * Finds the largest possible long that can be formed
	 * from the given array of Integers.
	 * Does not modify the given array.
	 * If the number formed is larger than the max long, it throws an OutOfRangeException.
	 * 
	 * @param arr - the given array of Integers
	 * @throws OutOfRangeException if the number formed is larger than 
	 *         the max long value
	 * @return an long that is the largest possible number formed
	 *         from the array
	 */
	public static long findLargestLong(Integer[] arr) throws OutOfRangeException {
		// copy arr to new array arrayCopy
		Integer[] arrayCopy = new Integer[arr.length];
		for(int i = 0; i < arr.length; i++)
			arrayCopy[i] = arr[i];
		// sorts the array copy
		insertionSort(arrayCopy, (i, j) -> Long.valueOf(Long.parseLong(concatenate(j, i))).compareTo(Long.parseLong(concatenate(i, j))));
		long largestLong = 0;
		String concat = "";
		// concatenates each value of the sorted array into a long
		for(int i = 0; i < arr.length; i++) {
			concat = concatenate(largestLong, arrayCopy[i]);
			// checks if the number is larger than the largest long value
			if(BigInteger.valueOf(Long.parseLong(concat)).compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0)
				throw new OutOfRangeException("long");
			largestLong = Long.parseLong(concat);
		}
		
		return largestLong;
	}
	
	/**
	 * Sums all the largest possible numbers formed from a
	 * list of Integer arrays.
	 * Does not modify the given list.
	 * 
	 * @param list - the list to calculate the sum from
	 * @return the sum of all the largest possible numbers of each
	 *         array of Integers
	 */
	public static BigInteger sum(List<Integer[]> list) {
		BigInteger sum = BigInteger.valueOf(0);
		for(Integer[] arr : list)
			sum = sum.add(findLargestNumber(arr));
		System.out.println(sum);
		return sum;
	}
	
	/**
	 * Finds the Integer array that has the kth largest number from a list of Integer arrays.
	 * For example, 0 returns the largest array, 1 the second largest,
	 * and list.size() - 1 the smallest.
	 * 
	 * @param list - the list to find the kth largest from
	 * @param k - the relative magnitude of the number compared to the list
	 * @return the Integer array that forms the kth largest number
	 */
	public static Integer[] findKthLargest(List<Integer[]> list, int k) {
		// copies the given list into an ArrayList
		ArrayList<Integer[]> listCopy = new ArrayList<Integer[]>();
		Integer[] arr;
		Integer[] arrCopy;
		for(int i = 0; i < list.size(); i++) {
			arr = list.get(i);
			arrCopy = new Integer[arr.length];
			// copies the array at list index i
			for(int j = 0; j < arr.length; j++)
				arrCopy[j] = arr[j];
			// adds the array copy to the list copy
			listCopy.add(arrCopy);
		}
		// converts the list copy into an array for use in insertionSort
		Object[] listArray = listCopy.toArray();
		// sorts the list copy
		insertionSort(listArray, (arr1, arr2) -> 
		(findLargestNumber((Integer[])arr2).subtract(findLargestNumber((Integer[])arr1))).intValue());
		
		return (Integer[])listArray[k];
	}
	
	/**
	 * Forms a list of Integer arrays from the given file.
	 * If the file does not exist, returns an empty list.
	 * 
	 * @param filename - the path of the file
	 * @return a list of Integer arrays or an empty list if the file
	 *         does not exist.
	 */
	public static List<Integer[]> readFile(String filename) {
		// 
		ArrayList<Integer[]> list = new ArrayList<Integer[]>();
		ArrayList<Integer> intsList = new ArrayList<Integer>();
		Integer[] ints;
		try {
			File file = new File(filename);
			Scanner reader = new Scanner(file);
			while(reader.hasNextLine()) {
				while(reader.hasNextInt()) {
					intsList.add(reader.nextInt());
				}
				ints = new Integer[intsList.size()];
				ints = intsList.toArray(ints);
				list.add(ints);
				intsList.clear();
				reader.nextLine();
			}
			reader.close();
			return list;
		} 
		catch(FileNotFoundException e) {
			return list;
		}
	}
	
	/**
	 * Concatenates two items together.
	 * 
	 * @param <T> the generic type placeholder
	 * @param a - the first item
	 * @param b - the second item
	 * @return a and b concatenated into a String
	 */
	private static <T> String concatenate(T a, T b) {
		return a.toString() + b.toString();
	}
}
