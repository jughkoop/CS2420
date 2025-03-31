package assign09;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class HashTableTester {
	private HashTable<String, Integer> emptyTable;
	private HashTable<String, Integer> smallTable;
	private HashTable<Integer, String> numberTable;
	private HashTable<StudentGoodHash, Double> studentTable;

	@BeforeEach
	public void setUp() {
		// Empty table with default max load factor
		emptyTable = new HashTable<String, Integer>();

		// Small table with some string keys and integer values
		smallTable = new HashTable<String, Integer>();
		smallTable.put("apple", 1);
		smallTable.put("banana", 2);
		smallTable.put("cherry", 3);

		// Small table with integer keys and string values
		numberTable = new HashTable<Integer, String>();
		numberTable.put(1, "one");
		numberTable.put(2, "two");
		numberTable.put(3, "three");

		// Table with student keys and GPA values
		studentTable = new HashTable<StudentGoodHash, Double>(6.0); // Custom max load factor
		studentTable.put(new StudentGoodHash(1234567, "Amy", "Zhang"), 3.5);
		studentTable.put(new StudentGoodHash(6666666, "Braxdon", "Lee"), 4.0);
		studentTable.put(new StudentGoodHash(8888888, "Coco", "Li"), 3.8);
		studentTable.put(new StudentGoodHash(7654321, "Lilian", "Xu"), 3.6);
		studentTable.put(new StudentGoodHash(9999999, "David", "Jason"), 3.2);

	}

	@Test
	public void testInvalidLoadFactorinConstructor() {
		// Test that constructor throws exception for load factor < 1.0
		assertThrows(IllegalArgumentException.class, () -> {
			new HashTable<>(0.8);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			new HashTable<>(0.1);
		});
	}

	// -------------------------------------test Clear Method
	// -------------------------------------------
	@Test
	public void testClear() {

		assertEquals(0, emptyTable.size());

		assertEquals(3, smallTable.size());
		smallTable.clear();
		assertEquals(0, smallTable.size());

		assertEquals(5, studentTable.size());
		studentTable.clear();
		assertEquals(0, studentTable.size());

	}

	// ----------------------------- test Contains(key) Method
	// -----------------------------------------
	@Test
	public void testContainsKey() {

		assertFalse(emptyTable.containsKey("Hello_Wrold"));

		assertTrue(smallTable.containsKey("apple"));
		smallTable.remove("apple");
		assertFalse(smallTable.containsKey("apple"));

		assertFalse(smallTable.containsKey("pear"));

		// Test with different key types
		assertTrue(numberTable.containsKey(1));
		numberTable.remove(1);
		assertFalse(numberTable.containsKey(1));

		assertFalse(numberTable.containsKey(4));

		// Test with student objects
		StudentGoodHash Amy = new StudentGoodHash(1234567, "Amy", "Zhang");
		StudentGoodHash Coco2 = new StudentGoodHash(8888888, "Coco", "Li");

		StudentGoodHash Coco1 = new StudentGoodHash(8888888, "David", "Li");
		StudentGoodHash Corby = new StudentGoodHash(8866888, "Corby", "Hello");

		assertTrue(studentTable.containsKey(Amy));
		assertTrue(studentTable.containsKey(Coco2));

		// the name is diffrent, so the result should be false;
		assertFalse(studentTable.containsKey(Coco1));
		assertFalse(studentTable.containsKey(Corby));
	}

	// ----------------------------- test Contains(value) Method
	// -----------------------------------------
	@Test
	public void testContainsValue() {

		assertFalse(emptyTable.containsValue(100));
		assertTrue(smallTable.containsValue(1));
		assertFalse(smallTable.containsValue(8));

		assertTrue(numberTable.containsValue("one"));
		assertFalse(numberTable.containsValue("eight"));

		// Test with null values
		smallTable.put("null-value", null);
		assertTrue(smallTable.containsValue(null));

		// Test with student values
		assertTrue(studentTable.containsValue(3.5));
		assertFalse(studentTable.containsValue(2.0));
	}

	// -------------------------- test Entries
	// Method-------------------------------------------

	@Test
	public void testEntries() {
		ArrayList<MapEntry<String, Integer>> entries = (ArrayList<MapEntry<String, Integer>>)smallTable.entries();

		boolean foundApple = false, foundBanana = false, foundCherry = false;

		for (MapEntry<String, Integer> entry : entries) {
			if (entry.getKey().equals("apple") && entry.getValue() == 1) {
				foundApple = true;

			} else if (entry.getKey().equals("banana") && entry.getValue() == 2) {
				foundBanana = true;

			} else if (entry.getKey().equals("cherry") && entry.getValue() == 3) {
				foundCherry = true;

			}
		}

		assertTrue(foundApple && foundBanana && foundCherry);
	}

	// -----------------------test Get Method
	// ------------------------------------------------
	@Test
	public void testGet() {

		assertNull(emptyTable.get("hello_world"));

		Integer value = 1;
		assertEquals(value, smallTable.get("apple"));

		assertNull(smallTable.get("hello_world"));

		// Test with student objects
		StudentGoodHash amy = new StudentGoodHash(1234567, "Amy", "Zhang");
		StudentGoodHash coco = new StudentGoodHash(8888888, "Coco", "Li");

		Double amyValue = 3.5;
		assertEquals(amyValue, studentTable.get(amy));

		Double cocoValue = 3.8;
		assertEquals(cocoValue, studentTable.get(coco));

	}

	// -----------------------------test isEmpty Method
	// ---------------------------------------

	@Test
	public void testIsEmpty() {
		assertTrue(emptyTable.isEmpty());

		assertFalse(smallTable.isEmpty());
		// Test that a table becomes empty after clearing
		smallTable.clear();
		assertTrue(smallTable.isEmpty());

		assertFalse(studentTable.isEmpty());
		// Test that a table becomes empty after clearing
		studentTable.clear();
		assertTrue(studentTable.isEmpty());

	}

	// -------------------------test Put Method
	// ----------------------------------------------
	@Test
	public void testPut() {

		// Test adding a new key-value pair
		assertNull(emptyTable.put("hello_world", 88));

		smallTable.put("data", 4);
		assertEquals(4, smallTable.size());

		// Test null values
		assertNull(smallTable.put("test", null));
		assertNull(smallTable.get("test"));
	}

	// ----------------------------test Remove
	// Method----------------------------------------
	@Test
	public void testRemove() {

		assertNull(emptyTable.remove("hello_world"));

		Integer value = 1;
		assertEquals(value, smallTable.remove("apple"));

		smallTable.remove("apple");
		assertNull(smallTable.remove("apple"));
		assertNull(smallTable.get("apple"));
		assertEquals(2, smallTable.size());

		// Test removing a nonexistent key
		assertNull(smallTable.remove("hello_world"));

		String stringValue = "one";
		assertEquals(stringValue, numberTable.remove(1));
		numberTable.remove(1);
		assertNull(numberTable.remove(1));
		assertNull(numberTable.get(1));
		assertEquals(2, numberTable.size());

	}

	// ----------------------test Size Method--------------------------------
	@Test
	public void testSize() {

		assertEquals(0, emptyTable.size());
		assertEquals(3, smallTable.size());
		assertEquals(5, studentTable.size());

		// Test size after adding elements
		emptyTable.put("Hello World", 1);
		assertEquals(1, emptyTable.size());

		// Test size after removing elements
		smallTable.remove("apple");
		assertEquals(2, smallTable.size());
		smallTable.clear();
		assertEquals(0, smallTable.size());

		assertEquals(5, studentTable.size());

	}

	// ------------------------------test Remap Method------------------------

	@Test
	public void testRemapping() {
		HashTable<Integer, String> remapTable = new HashTable<>(1.0);

		for (int i = 0; i < 20; i++) {
			remapTable.put(i, "Value " + i);
		}

		for (int i = 0; i < 20; i++) {
			assertEquals("Value " + i, remapTable.get(i));
		}
	}

	// ---------------------test edge Case-----------------

	@Test
	public void testCollisionException() {
		HashTable<CollisionKey, String> collisionTable = new HashTable<>();

		CollisionKey key1 = new CollisionKey(1);
		CollisionKey key2 = new CollisionKey(2);
		CollisionKey key3 = new CollisionKey(3);

		collisionTable.put(key1, "First");
		collisionTable.put(key2, "Second");
		collisionTable.put(key3, "Third");

		assertEquals("First", collisionTable.get(key1));
		assertEquals("Second", collisionTable.get(key2));
		assertEquals("Third", collisionTable.get(key3));

		assertEquals("Second", collisionTable.remove(key2));
		assertEquals("First", collisionTable.get(key1));
		assertNull(collisionTable.get(key2));
		assertEquals("Third", collisionTable.get(key3));
	}

	@Test
	public void testLargeNumberinEntries() {

		HashTable<Integer, Integer> largeTable = new HashTable<>();

		int numEntries = 1000;
		for (int i = 0; i < numEntries; i++) {
			largeTable.put(i, i * 100);
		}

		assertEquals(numEntries, largeTable.size());

		for (int i = 0; i < numEntries; i++) {
			assertEquals(Integer.valueOf(i * 100), largeTable.get(i));
		}
	}

// Helper class 
	private static class CollisionKey {
		private int value;

		public CollisionKey(int value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof CollisionKey)) {
				return false;
			}
			return this.value == ((CollisionKey) obj).value;
		}

		@Override
		public int hashCode() {
			return 42;
		}
	}

}
