package javac.adt;

import java.util.Comparator;

import javac.exception.UnderflowException;

/**
 * AVL Tree used the interface {@code Comparator} as the function to 
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
public class AvlTree<A> {
	
	/**
	 * Pointer to root node.
	 */
	private AvlNode<A> root;
	
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
	 * Pointer to balanced position.
	 */
	private static final int ALLOWED_IMBALANCE = 1;
	
	/**
     * Constructs an empty tree.
     */
	public AvlTree() {
		this(null);
	}

	/**
     * Constructs an empty tree with comparison method.
     * @param c is a class with comparison method implement interface Comparator.
     */
	public AvlTree( Comparator<? super A> c ) {
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
		getHeight( root );
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
			printTreeInOrder(root);
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
			printTreeInHeight(root);
		}
	}
	
	/**
	 * Input an array to AvlTree.
	 * @param arr the array to input.
	 */
	public void add( A[] arr ) {
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
	private boolean contains( A x, AvlNode<A> t ) {
		if( t == null ) return false;
		int compareResult = compare(x,t.element);
		if( compareResult < 0 ) {
			return contains(x, t.left);
		}else if( compareResult > 0 ) {
			return contains(x, t.right);
		}else return true;
	}	
	
	/**
	 * Internal method to find the smallest item in a subtree.
	 * @param t the node that roots the subtree.
	 * @return node containing the smallest item.
	 */
	private AvlNode<A> findMin( AvlNode<A> t ) {
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
	private AvlNode<A> findMax( AvlNode<A> t ) {
		if( t == null ) {
			return null;
		}else if( t.right == null ) {
			return t;
		}else return findMax(t.right);
	}
	
	/**
	 * Assume t is either balanced or within one of being balanced.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private AvlNode<A> balance( AvlNode<A> t ) {
		if( t == null ) return t;
		if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE ) {
			if( height( t.left.left ) >= height( t.left.right ) ) {
				t = rotateWithLeftChild( t );
			}else {
				t = doubleWithLeftChild( t );
			}
		}else if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE ) {
			if( height( t.right.right ) >= height( t.right.left ) ) {
				t = rotateWithRightChild( t );
			}else {
				t = doubleWithRightChild( t );
			}
		}
		t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
		return t;
	}
	
	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private AvlNode<A> insert( A x, AvlNode<A> t ) {
		if( t == null ) {
			return new AvlNode<>(x, null, null );	
		}else {
			int compareResult = compare(x,t.element);
			if( compareResult < 0 ) {
				t.left = insert(x, t.left);
			}else if( compareResult > 0 ){
				t.right = insert(x, t.right);
			}
			return balance( t );
		}
	}
	
	/**
	 * Internal method to remove from a subtree.
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private AvlNode<A> remove( A x, AvlNode<A> t ) {
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
	 * Rotate binary tree node with left child. 
	 * For AVL trees, this is a single rotation for case 1. 
	 * Update heights, then return new root.
	 * @param k2 the node that roots the subtree.
	 * @return the new node that roots the subtree.
	 */
	private AvlNode<A> rotateWithLeftChild( AvlNode<A> k2 ) {
		AvlNode<A> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
		k1.height = Math.max( height( k1.left ), k2.height ) +1;
		return k1;
	}
	
	/**
	 * Rotate binary tree node with right child. 
	 * For AVL trees, this is a single rotation for case 1. 
	 * Update heights, then return new root.
	 * @param k1 the node that roots the subtree.
	 * @return the new node that roots the subtree.
	 */
	private AvlNode<A> rotateWithRightChild( AvlNode<A> k1 ) {
		AvlNode<A> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max( height( k1.right ), height( k1.left ) ) + 1;
		k2.height = Math.max( height( k2.right ), k1.height ) +1;
		return k2;
	}
	
	/**
	 * Double rotate binary tree node: first left child with its 
	 * right child; then node k3 with new left child.
	 * For AVL trees, this is a double rotate for case 2.
	 * Update heights, then return new root.
	 * @param k3 the node that roots the subtree.
	 * @return the new node that roots the subtree.
	 */
	private AvlNode<A> doubleWithLeftChild( AvlNode<A> k3 ) {
		k3.left = rotateWithRightChild( k3.left );
		return rotateWithLeftChild( k3 );
	}
	
	/**
	 * Double rotate binary tree node: first right child with its 
	 * left child; then node k3 with new right child.
	 * For AVL trees, this is a double rotate for case 2.
	 * Update heights, then return new root.
	 * @param k3 the node that roots the subtree.
	 * @return the new node that roots the subtree.
	 */
	private AvlNode<A> doubleWithRightChild( AvlNode<A> k3 ) {
		k3.right = rotateWithLeftChild( k3.right );
		return rotateWithRightChild( k3 );
	}
	
	/**
	 * Internal method to get the size of a subtree.
	 * @param t the node that roots the subtree.
	 */
	private void size( AvlNode<A> t ) {
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
	private void getHeight( AvlNode<A> t ) {
		if(t != null ) {
			if( t == root ) t.height = 1;
			if( t.left != null ) t.left.height = t.height + 1;
				if( height < t.height ) height = t.height;
			getHeight(t.left);
			if( t.right != null ) t.right.height = t.height + 1;	
				if( height < t.height ) height = t.height;
			getHeight(t.right);
		}
	}
	
	/**
	 * Internal method to return the height.
	 * @param t the node that roots the subtree.
	 * @return the height of node t, or -1, if null.
	 */
	private int height( AvlNode<A> t ) {
		return t == null ? -1 : t.height;
	}
	
	/**
	 * Internal method to print a subtree in sorted order.
	 * @param t the node that roots the subtree.
	 */
	private void printTreeInOrder( AvlNode<A> t ) {
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
	private void printTreeInHeight( AvlNode<A> t ) {
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
	 * Internal class to declare a node with its elements.
	 * @author Group
	 *
	 * @param <A>
	 */
	private static class AvlNode<A> {
		//Constructors
		AvlNode<A> left;  //LeftChild
		AvlNode<A> right; //RightChild
		A element;           //The data in the node
		int height;
		
		@SuppressWarnings({ "unused" })
		public AvlNode( A theElement ) {
			// TODO Auto-generated constructor stub
			this(theElement, null, null );
		}
		
		public AvlNode( A theElement, AvlNode<A> lt, AvlNode<A> rt ) {
			// TODO Auto-generated constructor stub
			element = theElement;
			left = lt;
			right = rt;
			height = 0;
		}
	}
}
