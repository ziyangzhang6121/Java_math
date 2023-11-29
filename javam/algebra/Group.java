package javam.algebra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import javam.exception.NullAssociateException;
import javam.exception.NullClosedException;
import javam.exception.NullInverseException;
import javam.exception.NullUnitException;

/**
 * Group use the interface {@code GroupOperator} as the operation of this group.
 *
 * <p><strong>In order to make this group is well defined</strong>, four arithmetic 
 * rules of a group must be considered. For convenient, we define four exception 
 * handling {@code NullClosedException, NullAssociateException, NullUnitException, 
 * NullInverseException} to figure out some mistakes we may make.
 * 
 * <p>This version(at v.1.0) could handle some generated meta-expressions limited 
 * only by one generator, and meanwhile, the subGroup we gotten may not travel all 
 * of them, because we just use one generator to make a conclusion.
 * 
 * <p>Another version(at v.2.0) has added several operations on a group, which belong 
 * to the role of group in collection, yet it just handles some simple operations 
 * that the grouped collections only can be a set of the group or its subset, such 
 * as these methods {@code LeftRegularExpression(), RightRegularExpression(), 
 * LeftInducedExpression(Group<A>), RightInducedExpression(Group<A>), 
 * ConjugateExpression(Group<A>)}. Furthermore, an internal method {@code 
 * symGroup(A[])} is used for creating a symmetry group for any finate collection, 
 * which will be used for handling the role of group in any abstract collection after 
 * the group homomorphism is carried through.
 * 
 * <p>Considering that comparing the value between two of a group's element sometimes 
 * is ridiculous, we do not implement the interface {@code Collection}. But several 
 * compare methods of interface {@code Comparator} is used, such as {@code contains(), 
 * compare()}. Another shortcoming of this none-extension handling is that enhanced
 * for loop <pre> {@code for( A g : Group )} </pre> can not be visited. So 
 * this Group use the method {@code iterator()} as its own iterator, which is the same
 * using as class {@code Linkedlist}.
 * 
 * @author Zhang Ziyang
 * @param <A> the type of elements held in this collection
 */
public class Group<A> implements Serializable,Collection<A> {

	private static final long serialVersionUID = -3061998631976912188L;

	public static final Group<Character> K_4 = K_4();
	
	public static final Group<Integer> Z_2 = Z_(2);
	
	public static final Group<Integer> Z_3 = Z_(3);
	
	public static final Group<Integer> Z_5 = Z_(5);
	
	public static final Group<Integer> Z_7 = Z_(7);
	
	public static final Group<Integer> Z_11 = Z_(11);
	
	public static final Group<Integer> Z_13 = Z_(13);
	
	public static final Group<Integer> Z_17 = Z_(17);
	
	public static final Group<Integer> Z_19 = Z_(19);
	
	public static final Group<Integer> Z_23 = Z_(23);
	
	public static final Group<Integer> Z_29 = Z_(29);
	
	public static final Group<Mapping<Object, Object>> S_1 = 
			new Group<>().symGroup( new Character[] { 'a' } );
	
	public static final Group<Mapping<Object, Object>> S_2 = 
			new Group<>().symGroup( new Character[] { 'a','b' } );
	
	public static final Group<Mapping<Object, Object>> S_3 = 
			new Group<>().symGroup( new Character[] { 'a','b','c' } );
	
	public static final Group<Mapping<Object, Object>> S_4 = 
			new Group<>().symGroup( new Character[] { 'a','b','c','d' } );
	
	public static final Group<Mapping<Object, Object>> S_5 = 
			new Group<>().symGroup( new Character[] { 'a','b','c','d','e' } );
	
	/**
	 * Pointer to unit element.
	 */
	private GroupElement<A> unit;
	
	/**
	 * Pointer to Group-Operation.
	 */
	private GroupOperator<? super A> opr;
	
	/**
	 * Pointer to comparison class.
	 */
	private Comparator<? super A> cmp;
	
	/**
	 * Store element'value of group.
	 */
	private LinkedList<A> g = new LinkedList<>();
	
	/**
	 * Store element of group.
	 */
	private LinkedList<GroupElement<A>> G = new LinkedList<>();
	
	/**
	 * Store element's value of symGroup.
	 */
	private ArrayList<ArrayList<A>> res = new ArrayList<>();
	
	/**
	 * Constructs a group;
	 */
	public Group() {}
	
	/**
	 * Constructs a group with operation and unit element.
	 * @param o is a class with operation method implement interface Operator.
	 * @throws NullUnitException
	 * @throws NullClosedException
	 * @throws NullInverseException
	 * @throws NullAssociateException
	 */
	public Group( GroupOperator<? super A> o ) throws NullUnitException,
			NullInverseException, NullClosedException, NullAssociateException {
		this( o, null, null );
	}
	
	/**
	 * Constructs a group with operation and unit element and generators.
	 * @param o is a class with operation method implement interface Operator.
	 * @param a is the generator.
	 * @throws NullUnitException
	 * @throws NullClosedException
	 * @throws NullInverseException
	 * @throws NullAssociateException
	 */
	public Group( GroupOperator<? super A> o, A a ) throws NullUnitException, 
				NullInverseException, NullClosedException, NullAssociateException {
		this( o, a, null );
	}
	
	/**
	 * Constructs a group with operation and unit element and generators and compare class.
	 * @param o is a class with operation method implement interface Operator.
	 * @param a is the generator.
	 * @param c is the compare class.
	 * @throws NullUnitException
	 * @throws NullClosedException
	 * @throws NullInverseException
	 * @throws NullAssociateException
	 */
	public Group( GroupOperator<? super A> o, A a, Comparator<? super A> c  ) 
				throws NullUnitException, NullInverseException, 
				NullClosedException, NullAssociateException {
		cmp = c;
		opr = o;
		unit();
		generate( (A) a );
	}
	
	/**
     * Returns an iterator over the elements in this group in ascending order.
     *
     * @return an iterator over the elements in this group in ascending order
     */
    public Iterator<A> iterator() {
        return g.iterator();
    }
	
	/**
    * Returns the number of elements in this group (its cardinality).
    * @return the number of elements in this group (its cardinality)
    */
	public int order() {
        return g.size();
    }
	
	/**
	 * Returns the order of the element <strong>g</strong> in this group.
	 * @param g is the element of this group.
	 * @return the order of the element <strong>g</strong> in this group.
	 * @throws NullInverseException make sure group is well defined.
	 */
	@SuppressWarnings("unchecked")
	public int order( A g ) throws NullInverseException {
		int n = 1;
		for( A h = g ; h != ( A ) opr.unit() ; h = multiply( h, g ) ) {
			n++;
		}
		return n;
	}
	
	/**
	 * Return the element as the {@code add()} order.
	 * @param i is the index of the {@code add()} order.
	 * @return the element as the {@code add()} order.
	 */
	public A get( int i ) {
		return g.get( i );
	}
	
	/**
     * Returns {@code true} if this group contains no elements or no operation.
     * @return {@code true} if this group contains no elements or no operation.
     */
    public boolean isEmpty() {
        return g.isEmpty() || opr.unit() == null;
    }
    
    /**
     * @return {@code true} if this collection construct a group, otherwise throws 
     * corresponding exception {@code NullClosedException NullAssociateException 
     * NullUnitException NullInverseException }.
     * @throws NullInverseException 
     * @throws NullUnitException 
     * @throws NullClosedException 
     * @throws NullAssociateException 
     */
    public boolean isGroup() throws NullInverseException, NullClosedException, 
    								NullAssociateException, NullUnitException {
    	setGroup();
		return true;
	}
    
    /**
     * Returns {@code true} if this group contains the specified element.
     * More formally, returns {@code true} if and only if this group
     * contains an element {@code e} such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o object to be checked for containment in this group
     * @return {@code true} if this group contains the specified element
     * @throws ClassCastException if the specified object cannot be compared
     *         with the elements currently in the group
     * @throws NullPointerException if the specified element is null
     *         and this group uses natural ordering, or its comparator
     *         does not permit null elements
     */
    public boolean contains( Object o ) {
        return g.contains( o );
    }
    
    /**
     * Adds the specified element to this group if it is not already present.
     * More formally, adds the specified element {@code e} to this group if
     * the group contains no element {@code e2} such that
     * <tt>(e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2))</tt>.
     * If this group already contains the element, the call leaves the group
     * unchanged and returns {@code false}.
     *
     * @param e element to be added to this group
     * @return {@code true} if this set did not already contain the specified
     *         element
     * @throws ClassCastException if the specified object cannot be compared
     *         with the elements currently in this group
     * @throws NullPointerException if the specified element is null
     *         and this group uses natural ordering, or its comparator
     *         does not permit null elements
     */
    @SuppressWarnings("unchecked")
	public boolean add( A e ) {
    	A a = (A) opr.simplify(e);
    	if( g.contains( a ) ) return false;
    	return g.add( a );
    }
        
    /**
     * Removes all of the elements from this group.
     * The group will be empty after this call returns.
     */
    public void clear() {
        g.clear();
    }
	
    /**
     * Adds all of the elements in the specified collection to this group.
     *
     * @param c collection containing elements to be added to this group
     * @return {@code true} if this group changed as a result of the call
     * @throws ClassCastException if the elements provided cannot be compared
     *         with the elements currently in the group
     * @throws NullPointerException if the specified collection is null or
     *         if any element is null and this group uses natural ordering, or
     *         its comparator does not permit null elements
     */
    @SuppressWarnings("unchecked")
	public boolean addAll( Collection<? extends A> c ) {
    	if( c == null || c.size() == 0 ) return false;
    	for ( A value : c ) {
			this.add( (A) opr.simplify(value) );
		}
    	return true;
    }
    
    /**
     * Adds all of the elements in the specified array to this group.
     * @param c array containing elements to be added to this group
     * @return {@code true} if this group changed as a result of the call
     * @throws ClassCastException if the elements provided cannot be compared
     *         with the elements currently in the group
     * @throws NullPointerException if the specified collection is null or
     *         if any element is null and this group uses natural ordering, or
     *         its comparator does not permit null elements
     */
    @SuppressWarnings("unchecked")
	public boolean addAll( A[] c ) {
    	if( c == null || c.length == 0 ) return false;
    	for ( A value : c ) {
			this.add( (A) opr.simplify(value) );
		}
    	return true;
    }
    
    /**
     * Print the group in its set's order.
     * @return {@code true} if group is not empty, {@code false} otherwise.
     */
    public boolean print() {
    	if( this == null || this.order() == 0 ) return false;
    	for( Iterator<A> iterator = this.iterator(); iterator.hasNext(); ) {
			System.out.print( iterator.next() + " " );
		}
    	System.out.println(" ");
    	return true;
    }
    
    /**
	 * State multiplication of group.
	 * @param a is the left element of multiply.
	 * @param b is the right element of multiply.
	 * @return the result of multiplication.
     * @throws NullInverseException make sure group is well defined.
	 */
    @SuppressWarnings("unchecked")
	public A multiply( A a, A b ) throws NullInverseException {
    	if( opr.multiply(a, b) == null ) throw new NullInverseException();
    	a = (A) opr.simplify( a );
    	b = (A) opr.simplify( b );
    	return ( A ) opr.simplify( (A) opr.multiply( a, b ) );
	}
    
    /**
   	 * State multiplication of group.
   	 * @param a is the left element of multiply.
   	 * @param G is the middle subGroup.
   	 * @param b is the right element of multiply.
   	 * @return the result of multiplication.
   	 * @throws NullInverseException make sure group is well defined.
   	 */
    public LinkedList<A> multiply( A a, Group<A> G, A b ) throws NullInverseException {
    	LinkedList<A> s = new LinkedList<>();
    	for( A value : G.g ) {
       		s.add( multiply( multiply( a, value ), b) );
       	}
    	return s;
   	}
    
    /**
   	 * State multiplication of group.
   	 * @param a is the left element of multiply.
   	 * @param G is the middle subGroup.
   	 * @return the result of multiplication.
   	 * @throws NullInverseException make sure group is well defined.
   	 */
    @SuppressWarnings("unchecked")
	public LinkedList<A> multiply( A a, Group<A> G ) throws NullInverseException {
    	return multiply( a, G, (A) opr.unit() );
   	}
    
    /**
   	 * State multiplication of group.
   	 * @param G is the middle subGroup.
   	 * @param b is thr right element of multiply.
   	 * @return the result of multiplication.
   	 * @throws NullInverseException make sure group is well defined.
   	 */
    @SuppressWarnings("unchecked")
	public LinkedList<A> multiply( Group<A> G, A b ) throws NullInverseException {
    	return multiply( (A) opr.unit(), G, b );
   	}
    
    /**
     * Calculate inverse element.
     * @param a is the element of the group.
     * @return the inverse element's value of <strong>a</strong>
     */
    @SuppressWarnings("unchecked")
	public A inverse( A a ) {
    	a = (A) this.opr.simplify(a);
    	GroupElement<A> result = null;
    	for ( GroupElement<A> value : G ) {
			if( value.element == a ) result = value;
		}
    	return result.inverse.element;
	}
    
    /**
     * Calculate all the subGroup of this group.
     * @param l is the linkedList stores all the subGroup.
     * @throws NullUnitException
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     */
    public void subGroup( LinkedList<Group<A>> l ) throws NullUnitException, 
    		NullInverseException, NullClosedException, NullAssociateException {
    	next : for( A value : g ) {
    		for( Group<A> group : l ) {
    			if( subGroup( value ).g.containsAll( group.g ) && 
    					group.g.containsAll( subGroup( value ).g ) )
    				continue next;
			}
    		l.add( subGroup( value ) );
    	}
	}
    
    /**
     * Calculate the subGroup generate by <strong>g</strong>.
     * @param g is the current element
     * @return the subGroup generate by <strong>g</strong>.
     * @throws NullUnitException
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     */
    @SuppressWarnings("unchecked")
	public Group<A> subGroup( A g ) throws NullUnitException, NullInverseException, 
										NullClosedException, NullAssociateException {
		if( !isEmpty() || !contains(g) ) {
			Group<A> subGroup = new Group<>( opr );
			for( A h = g ; h != ( A ) opr.unit() ; h = multiply( h, g ) ) {
				subGroup.add( h );
			}
			return subGroup;
		}
		return null;
	}
    
    /**
     * Calculate all the cgtGroup of this group.
     * @param l is the linkedList stores all the cgtGroup.
     * @throws NullUnitException
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     */
    public void cgtGroup( LinkedList<LinkedList<Group<A>>> l ) throws 
    					NullInverseException, NullUnitException, 
    					NullClosedException, NullAssociateException {
    	for ( A value : g ) {
    		LinkedList<Group<A>> ll = new LinkedList<>();
    		cgtGroup( subGroup(value) , ll );
    		l.add( ll );
    	}
	}
    
    /**
     * Calculate the cgtGroup generate by <strong>G</strong>.
     * @param G is the current group.
     * @param l is the linkedList stores all the cgtGroup.
     * @throws NullInverseException
     * @throws NullUnitException
     * @throws NullClosedException
     * @throws NullAssociateException
     */
    public void cgtGroup( Group<A> G, LinkedList<Group<A>> l ) throws 
    					NullInverseException, NullUnitException, 
    					NullClosedException, NullAssociateException {
    	setGroup();
    	boolean b = false;
    	for ( A value : g ) {
    		Group<A> group = new Group<A>(opr);
    		group.g = multiply( value, G, inverse( value ) );
    		for( Group<A> group2 : l ) {
    			if( group.g.equals( group2.g ) ) b = true;
    			break;
    		}
    		if( b ) continue;
    		l.add( group );
    	}
	}
    
    /**
	 * Declare a symmetric group.
	 * @param s is the set which generates the symmetric group.
	 * @return the symmetric group.
	 * @throws NullUnitException
	 * @throws NullInverseException
	 * @throws NullClosedException
	 * @throws NullAssociateException
	 */
	public Group<Mapping<A, A>> symGroup( A[] s ) {
		Group<Mapping<A, A>> symG = null;
		try {
			symG = new Group<>( Algebra.MapGroupOperator( Arrays.asList( s ) ) );
		} catch (NullUnitException | NullInverseException | 
				NullClosedException | NullAssociateException e) {
			e.printStackTrace();
		}
		perm( s, 0, s.length-1 );
		int count = 0;
		for ( ArrayList<A> arr : res ) {
			if( count == 0 ) {
				count++;
				continue;
			}
			Mapping<A, A> map = new Mapping<>();
			for ( int i = 0; i < arr.size(); i++ ) {
				map.put( s[i] , arr.get(i) );
			}
			symG.add( map );
		}
		return symG;
	}
	
	/**
	 * Declare a symmetric group.
	 * @param s is the collection which generates the symmetric group.
	 * @return the symmetric group.
	 * @throws NullUnitException
	 * @throws NullInverseException
	 * @throws NullClosedException
	 * @throws NullAssociateException
	 */
	@SuppressWarnings("unchecked")
	public Group<Mapping<A, A>> symGroup(  Collection<? extends A> c  ) 
			throws NullUnitException, NullInverseException, 
			NullClosedException, NullAssociateException {
		return symGroup( (A[]) c.toArray() );
	}
    
    /**
     * Calculate the group's left regular expression.
     * @return the left regular expression group.
     * @throws NullUnitException
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     */
	public Group<Mapping<A, A>> LeftRegularExpression() throws NullUnitException, 
				NullInverseException, NullClosedException, NullAssociateException {
    	Group<Mapping<A, A>> lreG = new Group<>( Algebra.MapGroupOperator( g ) );
		for ( A element : g ) {
			if( element == unit.element ) {
				continue;
			}
			Mapping<A, A> mapping = new Mapping<>();
			for ( A value : g ) {
				mapping.put( value , this.multiply( element, value ) );
			}
			lreG.add( mapping );
		}
    	return lreG;
	}
    
    /**
     * Calculate the group's right regular expression.
     * @return the right regular expression group.
     * @throws NullUnitException
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     */
	public Group<Mapping<A, A>> RightRegularExpression() throws NullUnitException, 
				NullInverseException, NullClosedException, NullAssociateException {
    	Group<Mapping<A, A>> rreG = new Group<>( Algebra.MapGroupOperator( g ) );
		for ( A element : g ) {
			if( element == unit.element ) {
				continue;
			}
			GroupElement<A> inv = null;
			for ( GroupElement<A> e : G ) {
				if( element == e.inverse.element ) inv = e;
			}
			Mapping<A, A> mapping = new Mapping<>();
			for ( A value : g ) {
				mapping.put( value , this.multiply( value, inv.element ) );
			}
			rreG.add( mapping );
		}
    	return rreG;
	}
    
    /**
     * Calculate the group's lift induced expression.
     * @param H is the sub group for lift induced expression.
     * @return the group's lift induced expression.
     * @throws NullUnitException
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     */
    public Group<Mapping<LinkedList<A>, LinkedList<A>>> LeftInducedExpression( Group<A> H )
    		throws NullUnitException, NullInverseException, 
    		NullClosedException, NullAssociateException {
    	LinkedList<LinkedList<A>> l = new LinkedList<>();
    	for( int i = 0; i < g.size(); i++ ) {
    		LinkedList<A> tem = this.multiply( g.get( i ), H );
    		for( LinkedList<A> li : l ) {
				if( li.containsAll( tem ) && tem.containsAll( li ) ) l.add( tem );
			}
		}
    	Group<Mapping<LinkedList<A>, LinkedList<A>>> lieG = 
    			new Group<>( Algebra.MapGroupOperator( l ) );
    	for ( A element : g ) {
			if( element == unit.element ) {
				continue;
			}
			Mapping<LinkedList<A>, LinkedList<A>> mapping = new Mapping<>();
			for ( A value : g ) {
				mapping.put( multiply( value, H ), 
						multiply( multiply( element, value ), H ) );
			}
			lieG.add( mapping );
		}
    	return lieG;
	}
    
    /**
     * Calculate the group's right induced expression.
     * @param H is the sub group for right induced expression.
     * @return the group's right induced expression.
     * @throws NullUnitException
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     */
    public Group<Mapping<LinkedList<A>, LinkedList<A>>> RightInducedExpression
    	( Group<A> H ) throws NullUnitException, NullInverseException, 
    						NullClosedException, NullAssociateException {
    	LinkedList<LinkedList<A>> l = new LinkedList<>();
    	for( int i = 0; i < g.size(); i++ ) {
    		LinkedList<A> tem = this.multiply( g.get( i ), H );
    		for( LinkedList<A> li : l ) {
				if( li.containsAll( tem ) && tem.containsAll( li ) ) l.add( tem );
			}
		}
    	Group<Mapping<LinkedList<A>, LinkedList<A>>> rieG = 
    			new Group<>( Algebra.MapGroupOperator( l ) );
    	for ( A element : g ) {
			if( element == unit.element ) {
				continue;
			}
			GroupElement<A> inv = null;
			for ( GroupElement<A> e : G ) {
				if( element == e.inverse.element ) inv = e;
			}
			Mapping<LinkedList<A>, LinkedList<A>> mapping = new Mapping<>();
			for ( A value : g ) {
				mapping.put( multiply( H, value ), 
						multiply( H, multiply( value, inv.element ) ) );
			}
			rieG.add( mapping );
		}
    	return rieG;
	}
    
    /**
     * Calculate the group's right induced expression.
     * @param H is the sub group for right induced expression.
     * @return the group's right induced expression.
     * @throws NullUnitException
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     */
    public Group<Mapping<LinkedList<A>, LinkedList<A>>> ConjugateExpression( Group<A> H ) 
    		throws NullUnitException, NullInverseException, 
    		NullClosedException, NullAssociateException {
    	LinkedList<LinkedList<A>> l = new LinkedList<>();
    	for( int i = 0; i < g.size(); i++ ) {
    		GroupElement<A> inv = null;
			for ( GroupElement<A> e : G ) {
				if( g.get( i ) == e.inverse.element ) inv = e;
			}
    		LinkedList<A> tem = this.multiply( g.get( i ), H, inv.element );
    		for( LinkedList<A> li : l ) {
				if( li.containsAll( tem ) && tem.containsAll( li ) ) l.add( tem );
			}
		}
    	Group<Mapping<LinkedList<A>, LinkedList<A>>> cjeG = 
    			new Group<>( Algebra.MapGroupOperator( l ) );
    	for ( A element : g ) {
			if( element == unit.element ) {
				continue;
			}
			GroupElement<A> i = null;
			for ( GroupElement<A> e : G ) {
				if( element == e.inverse.element ) i = e;
			}
			Mapping<LinkedList<A>, LinkedList<A>> mapping = new Mapping<>();
			for ( A value : g ) {
				GroupElement<A> inv = null;
				for ( GroupElement<A> e : G ) {
					if( value == e.inverse.element ) inv = e;
				}
				mapping.put( multiply( value, H, inv.element ), multiply( 
					multiply( element, value ), H, multiply( inv.element, i.element ) ) );
			}
			cjeG.add( mapping );
		}
    	return cjeG;
	}
    
    public static Group<Integer> Z( int n ){
		Group<Integer> Z = null;
		try {
			Z = new Group<>( new GroupOperator<Integer>() {
				@Override
				public Integer unit() {
					return 0;
				}
				@Override
				public Integer multiply(Integer a, Integer b) {
					return (a + b) % n;
				}
				@Override
				public Integer simplify(Integer a) {
					return a % n;
				}
				@Override
				public boolean isGenerate() {
					return false;
				}
			});
			for( int i = 1; i < n; i++ ) {
				Z.add( i );
			}
			Z.setGroup();
		} catch (NullUnitException | NullInverseException |
				NullClosedException | NullAssociateException e) {
			e.printStackTrace();
		}
		return Z;
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
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	private int compare( A lhs, A rhs ) {
		if( cmp != null ) {
			return cmp.compare( lhs, rhs );
		}else {
			return ((Comparable)lhs).compareTo( rhs );
		}
	}
    
	/**
	 * Internal method to initialize unit element.
	 * @throws NullUnitException make sure group is well defined.
	 */
	@SuppressWarnings("unchecked")
	private void unit() throws NullUnitException {
		if( opr.unit() == null ) throw new NullUnitException();
		this.add( ( A ) opr.unit() );
		this.unit = new GroupElement<>( ( A ) opr.unit() );
		this.unit.inverse = this.unit;
	}
	
	/**
	 * Judge whether operation is associate.
	 * @return {@code true} if all the element is associate, {@code false} otherwise.
	 */
	@SuppressWarnings("unchecked")
	private boolean isAssociate() {
		for( A a : g ) {
			for( A b : g ) {
				for( A c : g ) {
					if( ( A ) opr.simplify( (A) opr.multiply( (A) opr.multiply( a, b ), c ) ) !=
						( A ) opr.simplify( (A) opr.multiply( a, (A) opr.multiply( b, c ) ) ) )
						return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Provide a method to generate a group in one generator.
	 * @param g is the generator.
	 * @throws NullUnitException
	 * @throws NullInverseException
	 * @throws NullClosedException
	 * @throws NullAssociateException
	 */
	@SuppressWarnings("unchecked")
	private void generate( A g ) throws NullUnitException, NullInverseException, 
			NullClosedException, NullAssociateException {
		if( opr.isGenerate() == true ) {
			for( A h = g ; h != ( A ) opr.unit() ; h = multiply( h, g ) ) {
				this.add( h );
			}
		}
	}
	
	/**
	 * Internal method to instance GroupElement.
	 * @throws NullClosedException
	 * @throws NullAssociateException
	 * @throws NullUnitException
	 */
	@SuppressWarnings("unchecked")
	private void setGroup() throws NullInverseException, NullClosedException,
			NullAssociateException, NullUnitException {
		if( isEmpty() ) throw new NullUnitException();
		for ( A value : g ) {
			G.add( new GroupElement<A>( value ) );
		}
		for( GroupElement<A> a : G ) {
			for( GroupElement<A> b : G ) {
				if( !contains( multiply( a.element, b.element ) ) ) 
					throw new NullClosedException();
			}
		}
		for( GroupElement<A> Element : G ) {
			if( Element.element == ( A ) opr.unit() ) Element.inverse = Element;
			for( GroupElement<A> inElement : G ) {
				if( inElement.element == ( A ) opr.unit() ) continue;
				if( multiply( Element.element, inElement.element ) == ( A ) opr.unit() )
					Element.inverse = inElement;
			}
		}
		for( GroupElement<A> Element : G ) {
			if( Element.inverse == null ) throw new NullInverseException();
		}
		if( !isAssociate() ) throw new NullAssociateException();
	}
	
	/**
	 * Internal method to permulate group items.
	 * @param buf is the internal list to store permulation result.
	 * @param start is the first location for permulating.
	 * @param end is the last location for permulating.
	 */
	private void perm( A[] buf, int start, int end ) {
		if ( start == end ) {
			ArrayList<A> a = new ArrayList<>();
			for( int i = 0; i <= end; i++ ){  
                a.add( buf[i] );
            }  
            res.add( a );
		}else {
			for (int i = start; i <= end; i++) {
				A temp = buf[start];
				buf[start] = buf[i];
				buf[i] = temp;
				perm( buf, start + 1, end );
				temp = buf[start]; 
                buf[start] = buf[i];  
                buf[i] = temp;  
			}
		}
	}
	
	private static Group<Character> K_4() {
		Group<Character> K_4 = null;
		try {
			K_4 = new Group<Character>( new GroupOperator<Character>() {
				@Override
				public Character unit() {
					return '1';
				}
				@Override
				public Character multiply(Character a, Character b) {
					if( a == 'i' && b == 'j' || a == 'j' && b == 'i' ) return 'k';
					if( a == 'j' && b == 'k' || a == 'k' && b == 'j' ) return 'i';
					if( a == 'k' && b == 'i' || a == 'i' && b == 'k' ) return 'j';
					if( a == '1' && b == 'k' || a == 'k' && b == '1' ) return 'k';
					if( a == '1' && b == 'i' || a == 'i' && b == '1' ) return 'i';
					if( a == '1' && b == 'j' || a == 'j' && b == '1' ) return 'j';
					else return '1';
				}
				@Override
				public Character simplify(Character a) {
					if( a == 'i' || a == 'j' || a == 'k' ) return a;
					return '1';
				}
				@Override
				public boolean isGenerate() {
					return false;
				}
			});
			Character[] ch = { '1', 'i', 'j', 'k' };
			K_4.addAll( ch );
			K_4.setGroup();
		} catch (NullUnitException | NullInverseException 
				| NullClosedException | NullAssociateException e) {
			e.printStackTrace();
		}
		return K_4;
	}
	
	private static Group<Integer> Z_( int p ) {
		Group<Integer> Z = null;
		try {
			Z = new Group<>( new GroupOperator<Integer>() {
				@Override
				public Integer unit() {
					return 1;
				}
				@Override
				public Integer multiply(Integer a, Integer b) {
					return a * b;
				}
				@Override
				public Integer simplify(Integer a) {
					return a % p;
				}
				@Override
				public boolean isGenerate() {
					return false;
				}
			});
			for( int i = 1; i < p; i++ ) {
				Z.add( i );
			}
			Z.setGroup();
		} catch (NullUnitException | NullInverseException |
				NullClosedException | NullAssociateException e) {
			System.out.println('s');
			e.printStackTrace();
		}
		return Z;
	}
	
	@Override
	public String toString() {
		if( this == null || this.order() == 0 ) return "< null >";
		if( !g.get(0).toString().contains( "\n" ) ) {
			StringBuffer s = new StringBuffer();
			s.append( "Group < " );
	    	for( Iterator<A> iterator = this.iterator(); iterator.hasNext(); ) {
				s.append( iterator.next() + " " );
			}
	    	s.append( ">" );
			return s.toString();
		}
		int height = 0;
		for( int i = 0; i < g.size(); i++ ) {
			String[] s = g.get(i).toString().split( "\n" );
			if( s.length > height ) height = s.length;
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer[] sbarr = new StringBuffer[height];
		for( int l = 0; l < sbarr.length; l++ ) {
			sbarr[l] = new StringBuffer();
		}
		for( int i = 0; i < g.size(); i++ ) {
			String[] s = g.get(i).toString().split( "\n" );
			if( i == 0 ) {
				for( int k = 0; k < height; k++ ) {
					if( k == height/2 ) sbarr[k].append( "Group < " );
					else sbarr[k].append( "        " );
				}
			} 
			for( int k = 0; k < height; k++ ) {
				sbarr[k].append( s[k] + " " );
			} 
			if( i == g.size()-1 ) {
				for( int k = 0; k < height; k++ ) {
					if( k == height/2 ) {
						sb.append( sbarr[k] + ">\n" );
					}
					else {
						sb.append( sbarr[k] + " " );
						if( k != height-1 ) sb.append( "\n" );
					}
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * Internal class to declare a GroupElement with its elements.
	 * @author Zhang Ziyang
	 * @param <A>
	 */
	@SuppressWarnings({ "unused", "hiding" })
	private class GroupElement<A> {
		//Constructors
		private GroupElement<A> inverse;
		private A element;
		
		public GroupElement( A theElement ) {
			this( theElement, null );
		}
		
		public GroupElement( GroupElement<A> inverse ) {
			this( null, inverse );
		}
		
		public GroupElement( A theElement, GroupElement<A> inverse ) {
			this.element = theElement;
			this.inverse = inverse;
		}
		
	}

	@Override
	public int size() {
		return this.order();
	}

	@Override
	public Object[] toArray() {
		return g.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return g.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		return g.remove( o );
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return g.containsAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return g.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return g.retainAll(c);
	}
	
}