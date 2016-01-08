/*
 * Name: Namho An
 * Email:namho8211@gmail.com
 * 
 * This class implements the AVL binary search tree.
 * It is also used to store the tree node.
 */
import java.util.List;

public class BinarySearchTree {
	
	// left and right children
	private BinarySearchTree left;
	private BinarySearchTree right;
	
	// parent of this tree node.
	// if this is the root node of the tree, set parent to null.
	private BinarySearchTree parent;
	
	// key and value in this tree node
	private String key;
	private Object value;
	
	// the height of this tree node
	private int height;
	
	// construct a tree with the given (key, value) as the
	// only node (root) in the tree.
	private BinarySearchTree(BinarySearchTree parent, String key, Object value) {
		this.key = key;
		this.value = value;
		this.height = 0;
		this.parent = parent;
	}
	
	// create an empty binary search tree
	public BinarySearchTree() {
	}
	
	// has left child?
	public boolean hasLeft() {
		return left != null;
	}
	
	// has right child?
	public boolean hasRight() {
		return right != null;
	}
	
	// is this a leaf node (has no children)?
	public boolean isLeaf() {
		return !hasLeft() && !hasRight();
	}
	
	// is the tree empty?
	public boolean isEmpty() {
		return key == null;
	}
	
	// is this the root node of the tree?
	public boolean isRoot() {
		return parent == null;
	}
	
	// is this the left child?
	public boolean isLeftChild() {
		return !isRoot() && parent.left == this;
	}
	
	// is this the right child?
	public boolean isRightChild() {
		return !isRoot() && parent.right == this;
	}
	
	// has parent node?
	public boolean hasParent() {
		return parent != null;
	}
	
	// return the node that contains the key.
	// if the key is not in the tree, return null.
	public BinarySearchTree findNode(String key) {
		if (isEmpty()) { // the tree is empty
			return null;
		}
		
		if (this.key.equals(key)) { // found
			return this;
		}
		if (this.key.compareTo(key) > 0) {
			// search in the left tree if exist
			if (hasLeft()) {
				return left.findNode(key);
			}
			// otherwise, not in the tree
			return null;
		} else {
			// search in the right tree if it exists
			if (hasRight()) {
				return right.findNode(key);
			}
			// otherwise, not in the tree
			return null;
		}
	}
	
	// find the node with the minimal value.
	// which should be the left most leaf of the tree.
	public BinarySearchTree findMin() {
		if (isEmpty()) { // tree is empty
			return null;
		}
		BinarySearchTree result = this;
		// loop to go to the left-most node
		while (result.hasLeft()) {
			result = result.left;
		}
		return result;
	}
	
	// return the node that is the successor of the key
	public BinarySearchTree findSuccessor(String key) {
		// first find the node that has the key
		BinarySearchTree node = findNode(key);
		if (node == null) {
			// the key is not in the tree, return null.
			return null;
		}
		// otherwise,
		// if the node has right child.
		// the successor is the minimal node of the right child
		if (hasRight()) {
			return right.findMin();
		}
		// otherwise, the successor is the parent
		return parent;
	}
	
	// add the key value pair as the root node of the tree
	public void addRoot(String key, Object value) {
		if (!isEmpty()) {
			throw new IllegalStateException("the root node already exists");
		}
		this.key = key;
		this.value = value;
	}
	
	// the recursive insertion function
	private static BinarySearchTree insert(BinarySearchTree node, String key, Object value) {
		if (node == null) {
			return new BinarySearchTree(null, key, value);
		}
		if (node.key.equals(key)) {
			// the key already exists in the tree,
			throw new IllegalArgumentException("the key already exists in the tree");
		}
		
		BinarySearchTree result = node;
		
		if (node.key.compareTo(key) > 0) {
			// insert into the left sub tree
			node.left = insert(node.left, key, value);
			node.left.parent = node;
		} else {
			// insert into the right sub tree
			node.right = insert(node.right, key, value);
			node.right.parent = node;
		}
		
		// update the height, if the tree is not balanced, balance it
		node.updateHeight();
		if (Math.abs(node.balanceFactor()) >= 2) {
			// the balance factor is not -1, 0, 1.
			// the tree is not balance, other balance it
			result = node.balance(key);
		}
		
		return result;
	}
	
	public BinarySearchTree insert(String key, Object value) {
		
		if (isEmpty()) { // add into an empty tree (so it is the root node)
			addRoot(key, value);
			return this;
		} else {
			return insert(this, key, value);
		}
	}
	
	// update the height of the current tree node
	// which is 1 + the maximal height of its children
	private void updateHeight() {
		this.height = 0;
		// check the left child
		if (hasLeft() && left.height + 1 > this.height) {
			this.height = left.height + 1;
		}
		// check the right child
		if (hasRight() && right.height + 1 > this.height) {
			this.height = right.height + 1;
		}
	}
	
	// return the object associated with the key in the tree
	public Object search(String key) {
		BinarySearchTree node = findNode(key);
		if (node != null) {
			return node.value;
		}
		// otherwise, the key does not exist
		return null;
	}
	
	// the recursive function to delete a key from the tree
	private static BinarySearchTree delete(BinarySearchTree node, String key) {
		if (node == null) {
			throw new IllegalArgumentException("the key does not exist");
		}
		if (node.key.equals(key)) {
			// found the node, remove it from the tree
			if (node.isLeaf()) {
				// this is the leaf node, remove it directly
				return null;
			}
			if (node.left == null) {
				return node.right;
			}
			if (node.right == null) {
				return node.left;
			}
			// both children are not empty.
			// find the minimal node in the right child, and put it in the current node
			BinarySearchTree min = node.right.findMin();
			node.key = min.key;
			node.value = min.value;
			// now remove the minimal node in the right child recursively
			node.right = delete(node.right, min.key);
			if (node.right != null) {
				node.right.parent = node;
			}
			
		} else if (node.key.compareTo(key) > 0) {
			// remove from the left child
			node.left = delete(node.left, key);
			if (node.left != null) {
				node.left.parent = node; 
			}
		} else {
			// remove from the right child
			node.right = delete(node.right, key);
			if (node.right != null) {
				node.right.parent = node;
			}
		}
		
		return node;
	}
	
	// delete the key associated with the node
	public BinarySearchTree delete(String key) {
		if (isEmpty()) {
			throw new IllegalArgumentException("the key does not exist");
		}
		return delete(this, key);
	}
	
	public int size() {
		// return the number of nodes in the tree
		if (isEmpty()) {
			return 0;
		}
		int count = 1;
		if (hasLeft()) {
			count = count + left.size(); // add the nodes in the left child
		}
		if (hasRight()) {
			count = count + right.size(); // add the nodes in the right child
		}
		return count;
	}
	
	public int balanceFactor() {
		return height(left) - height(right);
	}
	
	// return the height of the tree
	private static int height(BinarySearchTree tree) {
		if (tree == null || tree.isEmpty()) {
			return -1;
		}
		return tree.height;
	}
	
	// the recursive method to return the height of the tree
	public int height() {
		int h = 0;
		// check the left child recursively
		if (hasLeft()) {
			int newHeight = left.height() + 1;
			if (newHeight > h) {
				h = newHeight;
			}
		}
		// check the right child recursively
		if (hasRight()) {
			int newHeight = right.height() + 1;
			if (newHeight > h) {
				h = newHeight;
			}
		}
		return h;
	}
	
	// calculate the depth of the tree recursively
	// the depth is the distance from the node to the root of the tree.
	public int depth() {
		if (hasParent()) {
			return 1 + parent.depth();
		}
		return 0;
	}
	
	// balance the tree node because of the insertion of the key
	// the caller has to make sure the tree is already unbalanced.
	private BinarySearchTree balance(String key) {
		if (this.key.compareTo(key) > 0) {
			// since the key was inserted into the left child
			// need to rotate the left child
			if (this.left.key.compareTo(key) < 0) {
				// insert to right of left child
				// first need to do a left rotation on the left child
				leftRotate(this.left);
			}
			// then do a right rotation on the left child
			rightRotate(this);
			
		} else {
			// since the key was inserted into the right child
			// need to rotate the right child
			if (this.right.key.compareTo(key) > 0) {
				// insert to the left of right child
				// first need to do a right rotation on the right child
				rightRotate(this.right);
			}
			leftRotate(this);
		}
		return this.parent;
	}
	
	// perform a left rotation on the given node
	private static void leftRotate(BinarySearchTree node) {
		BinarySearchTree parent = node.parent;
		BinarySearchTree right = node.right;
		BinarySearchTree rightLeft = right.left;
		
		node.right = rightLeft;
		right.left = node;
		
		// update the parent nodes
		if (rightLeft != null) {
			rightLeft.parent = node;
		}
		node.parent = right;
		right.parent = parent;
		if (parent != null) {
			if (parent.left == node) {
				parent.left = right;
			} else {
				parent.right = right;
			}
		}
		
		// update the height of the nodes
		node.updateHeight();
		right.updateHeight();
		if (parent != null) {
			parent.updateHeight();
		}
	}
	
	// perform a right rotation on the given node
	private static void rightRotate(BinarySearchTree node) {
		BinarySearchTree parent = node.parent;
		BinarySearchTree left = node.left;
		BinarySearchTree leftRight = left.right;
		
		node.left = leftRight;
		left.right = node;
		
		// update the parent nodes
		if (leftRight != null) {
			leftRight.parent = node;
		}
		node.parent = left;
		left.parent = parent;
		if (parent != null) {
			if (parent.left == node) {
				parent.left = left;
			} else {
				parent.right = left;
			}
		}
		
		// update the height of the nodes
		node.updateHeight();
		left.updateHeight();
		if (parent != null) {
			parent.updateHeight();
		}
	}
	
	// return the mirror tree of the current tree.
	// the resulting tree is not a binary search tree any more.
	public BinarySearchTree mirrorTree() {
		BinarySearchTree result = new BinarySearchTree(parent, key, value);
		result.height = this.height;
		if (hasLeft()) {
			// the left child, become the right child of the result tree
			result.right = left.mirrorTree();
		}
		if (hasRight()) {
			result.left = right.mirrorTree();
		}
		return result;
	}
	
	public void printInorder() {
		// print the tree in inorder
		if (isEmpty()) {
			System.out.println("The tree is empty");
			return;
		}
		
		// print the left child
		if (hasLeft()) {
			left.printInorder();
		}
		
		// print the current node
		System.out.println(key);
		
		// print the right child
		if (hasRight()) {
			right.printInorder();
		}
	}
	
	// recursively collect the keys in the binary search tree
	// store the result into the list
	public void keys(List<String> result) {
		if (!isEmpty()) {
			// in-order traverse the tree
			if (hasLeft()) { // left
				left.keys(result);
			}
			result.add(key); // current node
			if (hasRight()) { // right
				right.keys(result);
			}
		}
	}
}