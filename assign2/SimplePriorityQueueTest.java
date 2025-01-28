package assign03;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * For testing the SimplePriorityQueue class.
 * 
 * @author Daniel Lee and Mi Zeng
 * @version 1-27-2025
 */
class SimplePriorityQueueTest {

	private SimplePriorityQueue<Integer> integerQueue;
	private SimplePriorityQueue<Double> doubleQueue;
	private SimplePriorityQueue<Integer> randomQueue;
	private SimplePriorityQueue<String> stringQueue;
	private int[] arr = new int[100];
	
	@BeforeEach
	public void setUp() throws Exception {
		integerQueue = new SimplePriorityQueue<Integer>();
		integerQueue.insert(15);
		integerQueue.insert(25);
		integerQueue.insert(35);
		integerQueue.insert(45);
		integerQueue.insert(55);
		
		doubleQueue = new SimplePriorityQueue<Double>();
		doubleQueue.insert(1.8);
		doubleQueue.insert(3.4);
		doubleQueue.insert(6.8);
		
		randomQueue= new SimplePriorityQueue<Integer>();
		Random randomNumber = new Random();
		for(int i = 0;  i < arr.length; i++) {
			arr[i] = randomNumber.nextInt(100);
			randomQueue.insert(arr[i]);
		}
		Arrays.sort(arr);
		
		stringQueue = new SimplePriorityQueue<String>((String str1, String str2) -> str2.compareTo(str1));
		stringQueue.insert("apple");
		stringQueue.insert("banana");
		stringQueue.insert("carrot");
		stringQueue.insert("date");
		
	}
	
	@Test
	public void testClear(){
		integerQueue.clear();
		assertEquals(0, integerQueue.size(), "The array size should be 0");
	}
	
	@Test
	public void testFindMaxValue(){
		assertEquals(55, integerQueue.findMax(),"The maxiumum value shoule be 55");
		assertEquals(6.8, doubleQueue.findMax(), "The maxiumum value should be 6.8");
		assertEquals(arr[arr.length-1], randomQueue.findMax(),"The maxiumum value should be the last index" );
		assertEquals("apple", stringQueue.findMax(), "The maxiumum value should be according to the custom comparator");
	}
	
	@Test
	public void testFindMaxNoSuchElementException() {
		doubleQueue.clear();
		assertThrows(NoSuchElementException.class, () -> { doubleQueue.findMax(); });
	}
	
	@Test
	public void testContainsElements(){
		assertTrue(integerQueue.contains(55), "The value should be in the array");
		assertFalse(integerQueue.contains(100),"The value should not be in the array ");
		assertTrue(doubleQueue.contains(6.8), "The value should be in the array");
		assertFalse(doubleQueue.contains(8.8),"The value should not be in the array");
		assertTrue(stringQueue.contains("apple"),"The value should be in the array");
		assertFalse(stringQueue.contains("orange"),"The value should not be in the array");
	}
	
	@Test
	public void testContainsAll() {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList.add(15);
		intList.add(25);
		intList.add(35);
		intList.add(45);
		intList.add(55);
		assertTrue(integerQueue.containsAll(intList),"The array should contain all the elements");
		intList.add(65);
		assertFalse(integerQueue.containsAll(intList), "The array should not contain all the elements");
	}
	
	@Test
	public void testDeleteMax() {
		assertEquals(55, integerQueue.deleteMax(), "The maximum value should be 55");
		assertEquals(6.8, doubleQueue.deleteMax(), "The maxiumum value should be 6.8");
		assertEquals(arr[arr.length-1], randomQueue.deleteMax(),"The maxiumum value should be the last index" );
	}
	
	@Test
	public void testDeleteMaxSize() {
		integerQueue.deleteMax();
		doubleQueue.deleteMax();
		randomQueue.deleteMax();
		assertEquals(4, integerQueue.size(),"The array size should be 4");
		assertEquals(2, doubleQueue.size(), "The array size should be 2");
		assertEquals(99, randomQueue.size(),"The array size should be 99");
	}
	
	@Test
	public void testDeleteMaxNoSuchElementException() {
		doubleQueue.clear();
		assertThrows(NoSuchElementException.class, () -> { doubleQueue.deleteMax(); });
	}
	
	@Test
	public void testInsertSize() {
		integerQueue.insert(65);
		doubleQueue.insert(9.7);
		randomQueue.insert(257);
		assertEquals(6, integerQueue.size(), "The array size should be 6");
		assertEquals(4, doubleQueue.size(),"The array size should be 4");
		assertEquals(101, randomQueue.size(),"The array size should be 101");
	}
	
	@Test
	public void testInsertAllSize() {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList.add(15);
		intList.add(65);
		intList.add(35);
		intList.add(75);
		intList.add(55);
		integerQueue.insertAll(intList);
		assertEquals(10, integerQueue.size(),"The array size should be 10");
	}
	
	@Test
	public void testIsEmpty() {
		assertFalse(integerQueue.isEmpty(), "The array should not be empty");
		integerQueue.clear();
		assertTrue(integerQueue.isEmpty(), "The array should be empty");
	}
	
	@Test
	public void testComparatorFindMax() {
		ArrayList<String> strList = new ArrayList<String>();
		strList.add("date");
		strList.add("carrot");
		strList.add("banana");
		strList.add("apple");
		
		assertEquals("apple", stringQueue.findMax(), "The maximum should be "
				+ "reverse lexocographical order according to the comparator");
	}

}
