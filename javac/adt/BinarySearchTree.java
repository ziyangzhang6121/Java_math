package javac.adt;

import java.util.ArrayList;
import java.util.Comparator;

import javac.exception.UnderflowException;

/**
 * Binary-Search Tree used the interface {@code Comparator} as the function to 
 * compare each items of this tree. Implements all optional list operations, and
 * permits all elements (including {@code null}).
 * 
 * <p>All of the operations perform as could be expected for a tree. Operations 
 * that index into the tree ordinarily will not traverse the tree from the beginning 
 * or the end, whichever is searching from the middle to the sides. Its elements should
 * be sorted in an specified order, which could be defined in an instance of a class
 * implements the interface {@code Comparator}, otherwise, the order is considered as 
 * the value of number, which means that you needn't implement {@code Comparator} if
 * and only if A can be compared use method {@code compareTo()} of Comparable.
 * 
 * @author Group
 * @param <A> the type of elements held in this collection
 */
public class BinarySearchTree<A> {
	/**
	 * Pointer to root node.
	 */
	private BinaryNode<A> root;

	/**
	 * Pointer to comparison class.
	 */
	private Comparator<? super A> cmp;
	
	/**
	 * Pointer to the size of the tree.
	 */
	private int size = 0;
	
	/**
	 * Pointer to the height of the tree.
	 */
	private int height = 0;
	
	/**
	 * Make height visible.
	 */
	private String tab = "  ";
	
	/**
     * Constructs an empty tree.
     */
	public BinarySearchTree() {
		this(null);
	}

	/**
     * Constructs an empty tree with comparison method.
     * @param c is a class with comparison method implement interface Comparator.
     */
	public BinarySearchTree( Comparator<? super A> c ) {
		root = null;
		cmp = c;
	}
	
	/**
	 * Make the tree empty.
	 */
	public void makeEmpty() {
		root = null;
	}
	
	/**
	 * Judge the tree is empty or not.
	 * @return true if the tree is empty; false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * Get the size of the tree.
	 * @return the size of the tree.
	 */
	public int size() {
		this.size = 0;
		if( isEmpty() )return 0;
		size( root );
		return size+1;
	}
	
	/**
	 * Get the height of the tree.
	 * @return the height of the tree.
	 */
	public int height() {
		this.height = 0;
		if( isEmpty() )return 0;
		root.height = 1;
		height( root );
		return this.height;
	}
	
	/**
	 * Find an item in the tree.
	 * @param x is item to search for.
	 * @return true if the item is found; false otherwise.
	 */
	public boolean contains( A x ) {
		return contains( x, root );
	}
	
	/**
	 * Find the smallest item in the tree.
	 * @return The smallest item of the tree.
	 * @throws UnderflowException if the tree is empty.
	 */
	public A findMin() throws UnderflowException {
		if( isEmpty() ) throw new UnderflowException();
		return findMin( root ).element;
	}
	
	/**
	 * Find the largest item in the tree.
	 * @return The largest item of the tree.
	 * @throws UnderflowException if the tree is empty.
	 */
	public A findMax() throws UnderflowException {
		if( isEmpty() ) throw new UnderflowException();
		return findMax( root ).element;
	}
	
	/**
	 * Insert specified item in the tree in sorted order.
	 * @param x the item to insert.
	 */
	public void insert( A x ) {
		root = insert( x, root );
	}
	
	/**
	 * Remove specified item in the tree.
	 * @param x the item to remove.
	 */
	public void remove( A x ) {
		root = remove( x, root );
	}
	
	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTreeInOrder() {
		if( isEmpty() ) {
			System.out.println("Empty tree");
		}else {
			printTreeInOrder( root );
		}
	}
	
	/**
	 * Print the tree contents in height.
	 */
	public void printTreeInHeight() {
		if( isEmpty() ) {
			System.out.println("Empty tree");
		}else {
			height();
			printTreeInHeight( root );
		}
	}
	
	/**
	 * Input an array to Binary-Search tree.
	 * @param arr the array to input.
	 */
	public void add( A[] arr ) {
		for( int i = 0; i < arr.length; i++ ) {
			insert(arr[i]);
		}
	}

	/**
	 * Input an array to Binary-Search tree and assign the root.
	 * @param arr the array to input.
	 * @param root the root item.
	 */
	public void add( A[] arr , A root ) {
		insert(root);
		for( int i = 0; i < arr.length; i++ ) {
			insert(arr[i]);
		}
	}
	
	/**
	 * Internal method to define a method for comparing two items.
	 * @param lhs the former compare item. 
	 * @param rhs the latter compare item.
	 * @return
	 * {@code -1} if the former item is less than the latter item; 
	 * <p>{@code 0} if the former item is equal to the latter item; 
	 * <p>{@code 1} if the former item is more than the latter item;
	 */ 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int compare( A lhs, A rhs ) {
		if( cmp != null ) {
			return cmp.compare( lhs, rhs );
		}else {
			return ((Comparable)lhs).compareTo( rhs );
		}
	}
	
	/**
	 * Internal method to find an item in a subtree.
	 * @param x is item to search for.
	 * @param t the node that roots the subtree.
	 * @return true if the item is found; false otherwise.
	 */
	private boolean contains( A x, BinaryNode<A> t ) {
		if( t == null ) return false;
		int compareResult = compare(x,t.element);
		if( compareResult < 0 ) {
			return contains(x, t.left);
		}else if( compareResult > 0 ) {
			return contains(x, t.right);
		}else return true;
	}	
	
	/**
	 * Internal method to get the size of a subtree.
	 * @param t the node that roots the subtree.
	 */
	private void size( BinaryNode<A> t ) {
		if( t != null ) {
			if( t.left != null ) size++;
			size(t.left);
			if( t.right != null ) size++;
			size(t.right);
		}
	}
	
	/**
	 * Internal method to get the height of a subtree.
	 * @param t the node that roots the subtree.
	 */
	private void height( BinaryNode<A> t ) {
		if(t != null ) {
			if( t == root ) t.height = 1;
			if( t.left != null ) t.left.height = t.height + 1;
				if( height < t.height ) height = t.height;
			height(t.left);
			if( t.right != null ) t.right.height = t.height + 1;	
				if( height < t.height ) height = t.height;
			height(t.right);
		}
	}
	
	/**
	 * Internal method to find the smallest item in a subtree.
	 * @param t the node that roots the subtree.
	 * @return node containing the smallest item.
	 */
	private BinaryNode<A> findMin( BinaryNode<A> t ) {
		if( t == null ) {
			return null;
		}else if( t.left == null ) {
			return t;
		}else return findMin(t.left);
	}
	
	/**
	 * Internal method to find the largest item in a subtree.
	 * @param t the node that roots the subtree.
	 * @return node containing the largest item.
	 */
	private BinaryNode<A> findMax( BinaryNode<A> t ) {
		if( t == null ) {
			return null;
		}else if( t.right == null ) {
			return t;
		}else return findMax(t.right);
	}
	
	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<A> insert( A x, BinaryNode<A> t ) {
		if( t == null ) {
			return new BinaryNode<>(x, null, null );	
		}else {
			int compareResult = compare(x,t.element);
			if( compareResult < 0 ) {
				t.left = insert(x, t.left);
			}else if( compareResult > 0 ){
				t.right = insert(x, t.right);
			}
			return t;
		}
	}
	
	/**
	 * Internal method to remove from a subtree.
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<A> remove( A x, BinaryNode<A> t ) {
		if( t == null ) {
			return t;	
		}else {
			int compareResult = compare(x,t.element);
			if( compareResult < 0 ) {
				t.left = remove(x, t.left);
			}else if( compareResult > 0 ){
				t.right = remove(x, t.right);
			}else if( t.left != null && t.right != null ) {
				t.element = findMin(t.right).element;
				t.right = remove(t.element, t.right);
			}else {
				t = ( t.left != null ) ? t.left : t.right;
			}
			return t;
		}
	}
	
	/**
	 * Internal method to print a subtree in sorted order.
	 * @param t the node that roots the subtree.
	 */
	private void printTreeInOrder( BinaryNode<A> t ) {
		if( t != null ) {
			printTreeInOrder(t.left);
			System.out.println(t.element);
			printTreeInOrder(t.right);
		}
	}
	
	/**
	 * Internal method to print a subtree in height.
	 * @param t the node that roots the subtree.
	 */
	private void printTreeInHeight( BinaryNode<A> t ) {
		if( t != null ) {
			for (int i = 1; i < t.height; i++) {
				System.out.print(tab);
			}
			System.out.println(t.element);
			printTreeInHeight(t.left);
			printTreeInHeight(t.right);
		}
	}
	
	/**
	 * Internal method to insert a subtree in parent node.
	 * @param arr the sub array to insert
	 * @param t the node that roots the subtree.
	 */
	@SuppressWarnings("unused")
	private void add( ArrayList<A> arr, BinaryNode<A> t ) {
		if( t != null ) {
			insert(arr.get(arr.size()/2),t);
			ArrayList<A> arrL = new ArrayList<>();
			for (int i = 0; i < arr.size()/2; i++) {
				arrL.add(arr.get(i));
			}
			ArrayList<A> arrR = new ArrayList<>();
			for (int i = arr.size()/2+1; i < arr.size(); i++) {
				arrR.add(arr.get(i));
			}
			add(arrL, t.left);
			add(arrR, t.right);
		}
	}
	
	/**
	 * Internal class to declare a node with its elements.
	 * @author Group
	 *
	 * @param <A>
	 */
	private static class BinaryNode<A> {
		//Constructors
		BinaryNode<A> left;  //LeftChild
		BinaryNode<A> right; //RightChild
		A element;           //The data in the node
		int height;
		
		@SuppressWarnings("unused")
		public BinaryNode( A theElement ) {
			// TODO Auto-generated constructor stub
			this(theElement, null, null );
		}
		
		public BinaryNode( A theElement, BinaryNode<A> lt, BinaryNode<A> rt ) {
			// TODO Auto-generated constructor stub
			element = theElement;
			left = lt;
			right = rt;
			height = 0;
		}
	}
}