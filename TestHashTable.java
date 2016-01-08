/*
 * Name: Namho An
 * Email: namho8211@gmail.com
 * 
 * This class tests the hash table implementation.
 */

import java.util.Random;


public class TestHashTable {

	private static Random random = new Random();
	
	public static void main(String[] args) {
		HashTable ht = new HashTable(13);
		
		// add random data into the hash table
		for (int i = 0; i < 1000; i++) {
			// generate a random string and add it into the table
			String key = generateRandom();
			String value = ""; // value does not matter
			ht.put(key, value);
			// System.out.printf("inserted key=%s, size=%d\n", key, ht.size());
		}
		
		// it is possible that size < 1000, since 
		System.out.println("size: " + ht.size());
		System.out.println("distribution:");
		// display the distribution
		int[] distribution = ht.distribution();
		for (int i = 0; i < distribution.length; i++) {
			System.out.print(distribution[i] + " ");
		}
		System.out.println();
	}
	
	// generate a random string
	private static String generateRandom() {
		int length = random.nextInt(10) + 10; // each word has 10-20 letters
		String result = "";
		for (int i = 0; i < length; i++) {
			// generate (length) letters
			char next = (char)(random.nextInt(26) + 'a');
			result = result + next;
		}
		return result;
	}

}
