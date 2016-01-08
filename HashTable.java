/*
 * Name: Namho An	
 * Email: namho8211@gmail.com
 * 
 * This class implements the Hash table and it uses
 * the binary search tree in each entry to handle collisions. `
 */

import java.util.ArrayList;

public class HashTable {

	// the entries in the hash table
	private BinarySearchTree[] entries;
	
	public HashTable(int size) {
		// create the table and a binary search tree for each entry in the table
		this.entries = new BinarySearchTree[size];
		for (int i = 0; i < size; i++) {
			this.entries[i] = new BinarySearchTree();
		}
	}
	
	// use only the first letter
	public int hash1(String string) {
		return string.charAt(0) % entries.length;
	}
	
	// use only the last letter
	public int hash3(String string) {
		return string.charAt(string.length() - 1) % entries.length;
	}
	
	// return the hash code of the string
	public int hash(String string) {
		int result = string.hashCode() % entries.length;
		if (result < 0) {
			result += entries.length;
		}
		return result;
	}
	
	// add the key value pair into the hash table
	public boolean put(String key, Object value) {
		// calculate the hash code.
		int hc = hash(key);
		// if it already exists in the corresponding binary search tree
		// return false
		BinarySearchTree entry = this.entries[hc];
		if (entry.findNode(key) != null) {
			// exist already
			return false;
		}
		// add into the table
		this.entries[hc] = entry.insert(key, value);
		return true;
	}
	
	// get the value associated with the key
	public Object get(String key) {
		// calculate the hash code.
		int hc = hash(key);
		BinarySearchTree entry = this.entries[hc];
		return entry.search(key);
	}
	
	// remove the key value pair in the hash table
	public void remove(String key) {
		int hc = hash(key);
		BinarySearchTree entry = this.entries[hc];
		this.entries[hc] = entry.delete(key);
	}
	
	public int size() {
		// accumulate the total size of the binary search tree nodes
		int result = 0;
		for (int i = 0; i < entries.length; i++) {
			result = result + entries[i].size();
		}
		return result;
	}
	
	public int[] distribution() {
		// return an array, each entry in the array is the number of nodes of the binary
		// search tree of the corresponding entry in the table
		int[] result = new int[entries.length];
		for (int i = 0; i < entries.length; i++) {
			result[i] = entries[i].size();
		}
		return result;
	}
	
	// return all keys in the table
	public String[] keys() {
		ArrayList<String> result = new ArrayList<String>();
		// accumulate keys in each entry
		for (int i = 0; i < entries.length; i++) {
			entries[i].keys(result);
		}
		// convert the list into array
		String[] array = new String[result.size()];
		return result.toArray(array);
	}
}
