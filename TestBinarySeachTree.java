/*
 * Name:Namho An
 * Email: namho8211@gmail.com
 * 
 * This class tests the AVL binary search tree.
 */

public class TestBinarySeachTree {

	public static void main(String[] args) {
		// create an empty tree
		BinarySearchTree tree = new BinarySearchTree();

		// in order visit the tree, it should be an empty tree
		tree.printInorder();
		System.out.println();
		
		// insert a sequence of values into the tree
		for (char c = 'A'; c <= 'Z'; c ++) {
			String key = "" + c;
			String value = ""; // value does not matter in the test
			tree = tree.insert(key, value);
			System.out.printf("inserted key=%s, height=%d\n", key, tree.height());
		}
		
		// now print the tree again, it should display strings in order
		System.out.println();
		System.out.println("<inorder>");
		tree.printInorder();
		System.out.println();
		
		System.out.println("size = " + tree.size());
	}

}

