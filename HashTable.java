package assign09;

import java.util.LinkedList;
import java.util.List;

public class HashTable<K, V> implements Map<K, V> {
	private Object[] table;
	private int size;
	private double maxLoadFactor;
	
	public HashTable() {
		table = new Object[10];
		for(int i = 0; i < table.length; i++)
			table[i] = new LinkedList<MapEntry<K, V>>();
		size = 0;
		maxLoadFactor = 10;
	}
	
	public HashTable(double maxLoadFactor) {
		if(((Double)maxLoadFactor).compareTo(1.0) < 0)
			throw new IllegalArgumentException();
		
		table = new Object[10];
		for(int i = 0; i < table.length; i++)
			table[i] = new LinkedList<MapEntry<K, V>>();
		size = 0;
		this.maxLoadFactor = maxLoadFactor;
	}
	
	@Override
	public void clear() {
		for(int i = 0; i < table.length; i++)
			getChain(i).clear();
	}

	@Override
	public boolean containsKey(K key) {
		LinkedList<MapEntry<K, V>> chain = getChain(key.hashCode() % table.length);
		for(MapEntry<K, V> entry : chain)
			if(entry.getKey().equals(key))
				return true;
		return false;
	}

	@Override
	public boolean containsValue(V value) {
		for(int i = 0; i < table.length; i++) {
			LinkedList<MapEntry<K, V>> chain = getChain(i);
			for(MapEntry<K, V> entry : chain)
				if(entry.getValue().equals(value))
					return true;
		}
		return false;
	}

	@Override
	public List<MapEntry<K, V>> entries() {
		LinkedList<MapEntry<K, V>> mapping = new LinkedList<MapEntry<K, V>>();
		for(int i = 0; i < table.length; i++) {
			LinkedList<MapEntry<K, V>> chain = getChain(i);
			for(MapEntry<K, V> entry : chain)
				mapping.addLast(entry);
		}
		return mapping;
	}

	@Override
	public V get(K key) {
		if(containsKey(key)) {
			LinkedList<MapEntry<K, V>> chain = getChain(key.hashCode() % table.length);
			for(MapEntry<K, V> entry : chain)
				if(entry.getKey().equals(key))
					return entry.getValue();
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public V put(K key, V value) {
		LinkedList<MapEntry<K, V>> chain = getChain(key.hashCode() % table.length);
		if(containsKey(key)) {
			for(MapEntry<K, V> entry : chain)
				if(entry.getKey().equals(key)) {
					V originalValue = entry.getValue();
					entry.setValue(value);
					return originalValue;
				}
		}
		chain.add(new MapEntry<K, V>(key, value));
		size++;
		Double loadFactor = (double)table.length / size;
		if(loadFactor.compareTo(maxLoadFactor) > 0)
			remap();
		return null;
	}

	@Override
	public V remove(K key) {
		LinkedList<MapEntry<K, V>> chain = getChain(key.hashCode() % table.length);
		if(containsKey(key)) {
			for(MapEntry<K, V> entry : chain)
				if(entry.getKey().equals(key)) {
					V value = entry.getValue();
					chain.remove(entry);
					size--;
					return value;
				}
		}
		return null;
	}

	@Override
	public int size() {
		return size;
	}
	
	@SuppressWarnings("unchecked")
	private LinkedList<MapEntry<K, V>> getChain(int index) {
		return (LinkedList<MapEntry<K, V>>)table[index];
	}
	
	@SuppressWarnings("unchecked")
	private void remap() {
		Object[] newTable = new Object[table.length * 2];
		for(int i = 0; i < newTable.length; i++)
			newTable[i] = new LinkedList<MapEntry<K, V>>();
		
		for(int i = 0; i < table.length; i++) {
			LinkedList<MapEntry<K, V>> chain = getChain(i);
			for(MapEntry<K, V> entry : chain)
				((LinkedList<MapEntry<K, V>>)newTable[entry.getKey().hashCode() % newTable.length]).add(entry);
		}
		table = newTable;
	}

}
