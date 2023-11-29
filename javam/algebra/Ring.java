package javam.algebra;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import javam.exception.NullAbelException;
import javam.exception.NullAssociateException;
import javam.exception.NullClosedException;
import javam.exception.NullInverseException;
import javam.exception.NullUnitException;
import javam.exception.NullZeroException;
import javam.exception.ZeroFactorException;

/**
 * Ring use the interface {@code RingOperator} as the operation of this ring.
 * 
 * @author Zhang Ziyang
 * @param <A>
 */
public class Ring<A> implements Serializable, Collection<A> {
	
	private static final long serialVersionUID = -7477366364214269290L;

	/**
	 * Point to zero element.
	 */
	protected RingElement<A> zero;
	
	/**
	 * Point to unit element.
	 */
	protected RingElement<A> one;
	
	/**
	 * Pointer to Group-Operation.
	 */
	protected RingOperator<? super A> opr;
	
	/**
	 * Pointer to comparison class.
	 */
	protected Comparator<? super A> cmp;
	
	/**
	 * Store element'value of ring.
	 */
	protected LinkedList<A> r = new LinkedList<>();
	
	/**
	 * Store element of ring.
	 */
	protected LinkedList<RingElement<A>> R = new LinkedList<>();
	
	/**
	 * Constructs a ring.
	 */
	public Ring() {}
	
	/**
	 * Constructs a ring with operation and zero element.
	 * @param o is a class with operation method implement interface Operator.
	 * @throws NullClosedException
	 * @throws NullInverseException
	 * @throws NullAssociateException
	 * @throws NullZeroException 
	 */
	public Ring( RingOperator<? super A> o ) throws NullInverseException, 
			NullClosedException, NullAssociateException, NullZeroException {
		this( o, null, null );
	}
	
	/**
	 * Constructs a ring with operation and zero element and generators.
	 * @param o is a class with operation method implement interface Operator.
	 * @param a is the generator.
	 * @throws NullClosedException
	 * @throws NullInverseException
	 * @throws NullAssociateException
	 * @throws NullZeroException 
	 */
	public Ring( RingOperator<? super A> o, A a ) throws NullInverseException,
				NullClosedException, NullAssociateException, NullZeroException {
		this( o, a, null );
	}
	
	/**
	 * Constructs a ring with operation and zero element and generators and compare class.
	 * @param o is a class with operation method implement interface Operator.
	 * @param a is the generator.
	 * @param c is the compare class.
	 * @throws NullClosedException
	 * @throws NullInverseException
	 * @throws NullAssociateException
	 * @throws NullZeroException 
	 */
	public Ring( RingOperator<? super A> o, A a, Comparator<? super A> c  ) 
						throws NullInverseException, NullClosedException, 
								NullAssociateException, NullZeroException {
		cmp = c;
		opr = o;
		zero();
	}
	
	/**
     * Returns an iterator over the elements in this ring in ascending order.
     *
     * @return an iterator over the elements in this ring in ascending order
     */
    public Iterator<A> iterator() {
        return r.iterator();
    }
    
    /**
     * Returns the number of elements in this ring (its cardinality).
     * @return the number of elements in this ring (its cardinality)
     */
 	public int order() {
         return r.size();
 	}
 	
 	/**
	 * Return the element as the {@code add()} order.
	 * @param i is the index of the {@code add()} order.
	 * @return the element as the {@code add()} order.
	 */
	public A get( int i ) {
		return r.get( i );
	}
	
	/**
     * Returns {@code true} if this group contains no elements or no operation.
     * @return {@code true} if this group contains no elements or no operation.
     */
    public boolean isEmpty() {
        return r.isEmpty() || opr.zero() == null;
    }
    
    /**
     * @return {@code true} if this collection construct a ring, otherwise throws 
     * corresponding exception {@code NullClosedException NullAssociateException 
     * NullZeroException NullInverseException NullAbelException }.
     * @throws NullInverseException 
     * @throws NullClosedException 
     * @throws NullAssociateException 
     * @throws NullAbelException 
     * @throws NullZeroException 
     */
    public boolean isRing() throws NullInverseException, NullClosedException, 
    			NullAssociateException, NullZeroException, NullAbelException {
    	setRing();
		return true;
	}
    
    /**
     * Returns {@code true} if this ring contains the specified element.
     * More formally, returns {@code true} if and only if this ring
     * contains an element {@code e} such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o object to be checked for containment in this ring
     * @return {@code true} if this ring contains the specified element
     * @throws ClassCastException if the specified object cannot be compared
     *         with the elements currently in the ring
     * @throws NullPointerException if the specified element is null
     *         and this ring uses natural ordering, or its comparator
     *         does not permit null elements
     */
    public boolean contains( Object o ) {
        return r.contains( o );
    }
    
    /**
     * Adds the specified element to this ring if it is not already present.
     * More formally, adds the specified element {@code e} to this ring if
     * the ring contains no element {@code e2} such that
     * <tt>(e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2))</tt>.
     * If this ring already contains the element, the call leaves the ring
     * unchanged and returns {@code false}.
     *
     * @param e element to be added to this ring
     * @return {@code true} if this set did not already contain the specified
     *         element
     * @throws ClassCastException if the specified object cannot be compared
     *         with the elements currently in this ring
     * @throws NullPointerException if the specified element is null
     *         and this ring uses natural ordering, or its comparator
     *         does not permit null elements
     */
    @SuppressWarnings("unchecked")
	public boolean add( A e ) {
    	A a = (A) opr.simplify(e);
    	if( r.contains( a ) ) return false;
    	return r.add( a );
    }
    
    /**
     * Removes all of the elements from this ring.
     * The ring will be empty after this call returns.
     */
    public void clear() {
        r.clear();
    }
    
    /**
     * Adds all of the elements in the specified collection to this ring.
     *
     * @param c collection containing elements to be added to this ring.
     * @return {@code true} if this ring changed as a result of the call.
     * @throws ClassCastException if the elements provided cannot be compared
     *         with the elements currently in the ring.
     * @throws NullPointerException if the specified collection is null or
     *         if any element is null and this ring uses natural ordering, or
     *         its comparator does not permit null elements.
     */
    @SuppressWarnings("unchecked")
	public boolean addAll( Collection<? extends A> c ) {
    	if( c == null || c.size() == 0 ) return false;
    	for ( A value : c ) {
			this.add( (A) opr.simplify( value ) );
		}
    	return true;
    }
    
    /**
     * Adds all of the elements in the specified array to this ring.
     * @param c array containing elements to be added to this ring.
     * @return {@code true} if this ring changed as a result of the call.
     * @throws ClassCastException if the elements provided cannot be compared
     *         with the elements currently in the ring.
     * @throws NullPointerException if the specified collection is null or
     *         if any element is null and this ring uses natural ordering, or
     *         its comparator does not permit null elements.
     */
    @SuppressWarnings("unchecked")
	public boolean addAll( A[] c ) {
    	if( c == null || c.length == 0 ) return false;
    	for ( A value : c ) {
			this.add( (A) opr.simplify( value ) );
		}
    	return true;
    }
    
    /**
     * Print the ring in its set's order.
     * @return {@code true} if ring is not empty, {@code false} otherwise.
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
	 * State multiplication of ring.
	 * @param a is the left element of add.
	 * @param b is the right element of add.
	 * @return the result of add.
     * @throws NullInverseException make sure ring is well defined.
	 */
    @SuppressWarnings("unchecked")
	public A add( A a, A b ) throws NullInverseException {
    	if( opr.add(a, b) == null ) throw new NullInverseException();
    	A a1 = (A) opr.simplify( a );
    	A b1 = (A) opr.simplify( b );
    	return ( A ) opr.simplify( (A) opr.add( a1, b1 ) );
	}
    
    /**
   	 * State add of ring.
   	 * @param a is the left element of add.
   	 * @param R is the middle subRing.
   	 * @param b is the right element of add.
   	 * @return the result of add.
   	 * @throws NullInverseException make sure ring is well defined.
   	 */
    public LinkedList<A> add( A a, Ring<A> R, A b ) throws NullInverseException {
    	LinkedList<A> s = new LinkedList<>();
    	for( A value : R.r ) {
       		s.add( add( add( a, value ), b) );
       	}
    	return s;
   	}
    
    /**
   	 * State add of ring.
   	 * @param a is the left element of add.
   	 * @param G is the middle subRing.
   	 * @return the result of add.
   	 * @throws NullInverseException make sure ring is well defined.
   	 */
    @SuppressWarnings("unchecked")
	public LinkedList<A> add( A a, Ring<A> G ) throws NullInverseException {
    	return add( a, G, (A) opr.zero() );
   	}
    
    /**
   	 * State add of ring.
   	 * @param G is the middle subRing.
   	 * @param b is thr right element of add.
   	 * @return the result of add.
   	 * @throws NullInverseException make sure ring is well defined.
   	 */
    @SuppressWarnings("unchecked")
	public LinkedList<A> add( Ring<A> G, A b ) throws NullInverseException {
    	return add( (A) opr.zero(), G, b );
   	}
    
    /**
	 * State multiplication of ring.
	 * @param a is the left element of multiply.
	 * @param b is the right element of multiply.
	 * @return the result of multiplication.
     * @throws NullClosedException make sure ring is well defined.
	 */
    @SuppressWarnings("unchecked")
	public A multiply( A a, A b ) throws NullClosedException {
    	if( opr.multiply(a, b) == null ) throw new NullClosedException();
    	A a1 = (A) opr.simplify( a );
    	A b1 = (A) opr.simplify( b );
    	return ( A ) opr.simplify( (A) opr.multiply( a1, b1 ) );
	}
    
    /**
     * Calculate negate element.
     * @param a is the element of the ring.
     * @return the negate element's value of <strong>a</strong>
     */
    @SuppressWarnings("unchecked")
	public A negate( A a ) {
    	a = (A) this.opr.simplify( a );
    	RingElement<A> result = null;
    	for ( RingElement<A> value : R ) {
			if( value.element == a ) result = value;
		}
    	return result.negate.element;
	}
    
    /**
     * Calculate all the subRing of this ring.
     * @param l is the linkedList stores all the subRing.
     * @throws NullZeroException
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     */
    public void subRing( LinkedList<Ring<A>> l ) throws NullZeroException, 
    		NullInverseException, NullClosedException, NullAssociateException {
    	next : for( A value : r ) {
    		for( Ring<A> ring : l ) {
    			if( subRing( value ).r.containsAll( ring.r ) && 
    					ring.r.containsAll( subRing( value ).r ) )
    				continue next;
			}
    		l.add( subRing( value ) );
    	}
	}
    
    /**
     * Calculate the subRing generate by <strong>r</strong>.
     * @param r is the current element
     * @return the subRing generate by <strong>r</strong>.
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     * @throws NullZeroException 
     */
    public Ring<A> subRing( A r ) throws NullInverseException, NullClosedException,
    									NullAssociateException, NullZeroException {
		if( !isEmpty() || !contains(r) ) {
			Ring<A> subRing = new Ring<>( opr );
			LinkedList<A> curl = new LinkedList<>();
			LinkedList<A> list = new LinkedList<>();
			curl.add( r );
			generate( curl, list );
			subRing.addAll( list );
			return subRing;
		}
		return null;
	}
    
    /**
     * Calculate the right ideal generate by <strong>I</strong>.
     * @param I is the current element
     * @return the right ideal generate by <strong>I</strong>.
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     * @throws NullZeroException
     */
    public Ring<A> rightIdeal( A I ) throws NullInverseException, NullClosedException,
    										NullAssociateException, NullZeroException {
		Ring<A> i = subRing( I );
		for (A a : r ) {
			for (A s : i.r ) {
				if ( i.contains( multiply( a, s ) ) ) return i;
			}
		}
		return null;
	}
    
    /**
     * Calculate the left ideal generate by <strong>I</strong>.
     * @param I is the current element
     * @return the left ideal generate by <strong>I</strong>.
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     * @throws NullZeroException
     */
    public Ring<A> leftIdeal( A I ) throws NullInverseException, NullClosedException, 
    										NullAssociateException, NullZeroException {
		Ring<A> i = subRing( I );
		for (A a : r ) {
			for (A s : i.r ) {
				if ( i.contains( multiply( s, a ) ) ) return i;
			}
		}
		return null;
	}
    
    /**
     * Calculate the ideal generate by <strong>I</strong>.
     * @param I is the current element
     * @return the ideal generate by <strong>I</strong>.
     * @throws NullInverseException
     * @throws NullClosedException
     * @throws NullAssociateException
     * @throws NullZeroException
     */
    public Ring<A> ideal( A I ) throws NullInverseException, NullClosedException, 
    									NullAssociateException, NullZeroException {
		Ring<A> i = subRing( I );
		for (A a : r ) {
			for (A s : i.r ) {
				if ( i.contains( multiply( s, a ) ) && i.contains( multiply( a, s ) ) ) return i;
			}
		}
		return null;
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
	protected int compare( A lhs, A rhs ) {
		if( cmp != null ) {
			return cmp.compare( lhs, rhs );
		}else {
			return ((Comparable)lhs).compareTo( rhs );
		}
	}
    
    /**
	 * Internal method to initialize zero element.
	 * @throws NullZeroException make sure ring is well defined.
	 */
	@SuppressWarnings("unchecked")
	protected void zero() throws NullZeroException {
		if( opr.zero() == null ) throw new NullZeroException();
		this.add( ( A ) opr.zero() );
		this.zero = new RingElement<>( ( A ) opr.zero() );
		this.zero.negate = this.zero;
		this.zero.inverse = null;
	}
	
	/**
	 * Judge whether operation is associate.
	 * @return {@code true} if all the element is associate, {@code false} otherwise.
	 */
	@SuppressWarnings("unchecked")
	protected boolean isAssociate() {
		for( A a : r ) {
			for( A b : r ) {
				for( A c : r ) {
					if( ( A ) opr.simplify( (A) opr.add( (A) opr.add( a, b ), c ) ) !=
						( A ) opr.simplify( (A) opr.add( a, (A) opr.add( b, c ) ) ) )
						return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Judge whether add operation is abel.
	 * @return {@code true} if all the element is abel, {@code false} otherwise.
	 */
	@SuppressWarnings("unchecked")
	protected boolean isAddAbel() {
		for( A a : r ) {
			for( A b : r ) {
				if( (A) opr.add( a, b ) != (A) opr.add( b, a ) )
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Internal method generate a subRing.
	 * @param list stores all the element'value.
	 * @param n is a current number judges whether the method should be stopped or not.
	 * @throws NullInverseException
	 * @throws NullClosedException
	 * @throws NullAssociateException
	 * @throws NullZeroException
	 */
	protected void generate( LinkedList<A> curl, LinkedList<A> list ) 
					throws NullInverseException, NullClosedException, 
							NullAssociateException, NullZeroException {
		if( curl.size() != list.size() ) {
			list.clear();
			list.addAll( curl );
			for ( A a : list ) {
				for ( A b : list ) {
					A p = add( a, b );
					A m = multiply( a, b );
					if( !curl.contains( p ) ) {
						curl.add( p );
					} 
					if( !curl.contains( m ) ) {
						curl.add( m );
					}
				}
			}
			generate( curl, list );
		}
	}
	
	/**
	 * Internal method to instance RingElement.
	 * @throws NullClosedException
	 * @throws NullAssociateException
	 * @throws NullZeroException
	 * @throws NullInverseException
	 * @throws NullAbelException 
	 */
	@SuppressWarnings("unchecked")
	protected void setRing() throws NullInverseException, NullClosedException, 
				NullAssociateException, NullZeroException, NullAbelException {
		if( isEmpty() ) throw new NullZeroException();
		if( r.size() == 1 && r.get(0).equals( opr.zero() ) ) return;
		for ( A value : r ) {
			R.add( new RingElement<A>( value ) );
		}
		for( RingElement<A> a : R ) {
			for( RingElement<A> b : R ) {
				if( !contains( add( a.element, b.element ) ) || 
						!contains( multiply( a.element, b.element ) ) ) 
					throw new NullClosedException();
			}
		}
		for( RingElement<A> Element : R ) {
			if( Element.element == ( A ) opr.zero() ) Element.negate = Element;
			for( RingElement<A> neElement : R ) {
				if( neElement.element == ( A ) opr.zero() ) continue;
				if( add( Element.element, neElement.element ) == ( A ) opr.zero() )
					Element.negate = neElement;
			}
		}
		for( RingElement<A> Element : R ) {
			if( Element.negate == null ) throw new NullInverseException();
		}
		if( !isAssociate() ) throw new NullAssociateException();
		if( !isAddAbel() ) throw new NullAbelException();
	}
	
	/**
	 * Internal method returns the add order of the element 
	 * <strong>r</strong> in this ring.
	 * @param r is the element of this ring.
	 * @return the order of the element <strong>r</strong> in this ring.
	 * @throws NullInverseException make sure ring is well defined.
	 */
	@SuppressWarnings("unchecked")
	protected int order( A r ) throws NullInverseException {
		int n = 1;
		for( A s = r ; s != ( A ) opr.zero() ; s = add( s, r ) ) {
			n++;
		}
		return n;
	}
 	
	/**
	 * Internal method returns the multiply order of the element 
	 * <strong>r</strong> in this ring.
	 * @param r is the element of this ring.
	 * @return the level of the element <strong>r</strong> in this ring.
	 * @throws NullClosedException
	 */
	@SuppressWarnings("unchecked")
	protected int level( A r ) throws NullClosedException {
		int n = 1;
		for( A s = r ; s != ( A ) opr.one() ; s = multiply( s, r ) ) {
			n++;
		}
		return n;
	}
	
	@Override
	public String toString() {
		if( this == null || this.order() == 0 ) return "< null >";
		if( !r.get(0).toString().contains( "\n" ) ) {
			StringBuffer s = new StringBuffer();
			s.append( "Ring < " );
	    	for( Iterator<A> iterator = this.iterator(); iterator.hasNext(); ) {
				s.append( iterator.next() + " " );
			}
	    	s.append( ">" );
			return s.toString();
		}
		int height = 0;
		for( int i = 0; i < r.size(); i++ ) {
			String[] s = r.get(i).toString().split( "\n" );
			if( s.length > height ) height = s.length;
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer[] sbarr = new StringBuffer[height];
		for( int l = 0; l < sbarr.length; l++ ) {
			sbarr[l] = new StringBuffer();
		}
		for( int i = 0; i < r.size(); i++ ) {
			String[] s = r.get(i).toString().split( "\n" );
			if( i == 0 ) {
				for( int k = 0; k < height; k++ ) {
					if( k == height/2 ) sbarr[k].append( "Ring < " );
					else sbarr[k].append( "       " );
				}
			} 
			for( int k = 0; k < height; k++ ) {
				sbarr[k].append( s[k] + " " );
			} 
			if( i == r.size()-1 ) {
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
	 * Internal class to declare a RingElement with its elements.
	 * @author Zhang Ziyang
	 * @param <A>
	 */
	@SuppressWarnings("hiding")
	protected class RingElement<A> {
		//Constructors
		protected RingElement<A> negate;
		protected RingElement<A> inverse = null;
		protected A element;
		
		public RingElement( A theElement ) {
			this( theElement, null );
		}
		
		public RingElement( RingElement<A> negate ) {
			this( null, negate );
		}
		
		public RingElement( A theElement, RingElement<A> negate ) {
			this.element = theElement;
			this.negate = negate;
		}
		
		protected void setInverse( RingElement<A> inverse ) {
			this.inverse = inverse;
		}
		
	}
	
	/**
	 * Internal class extends class Ring called Domain.
	 * @author Zhang Ziyang
	 * @param <A>
	 */
	@SuppressWarnings("hiding")
	public class Domain<A> extends Ring<A>{

		private static final long serialVersionUID = 6715809282444449585L;

		/**
		 * Constructs a domain.
		 */
		public Domain() {}
		
		/**
		 * Constructs a domain with operation and zero element.
		 * @param o is a class with operation method implement interface Operator.
		 * @throws NullInverseException
		 * @throws NullClosedException
		 * @throws NullAssociateException
		 * @throws NullZeroException
		 * @throws NullUnitException 
		 */
		public Domain( RingOperator<? super A> o ) throws NullInverseException,
									NullClosedException, NullAssociateException, 
									NullZeroException, NullUnitException {
			super( o );
			one();
		}
		
		/**
		 * Constructs a domain with operation and zero element and generators.
		 * @param o is a class with operation method implement interface Operator.
		 * @param a is the generator.
		 * @throws NullInverseException
		 * @throws NullClosedException
		 * @throws NullAssociateException
		 * @throws NullZeroException
		 * @throws NullUnitException 
		 */
		public Domain( RingOperator<? super A> o, A a ) throws NullInverseException,
										NullClosedException, NullAssociateException, 
										NullZeroException, NullUnitException {
			super( o, a );
			one();
		}
		
		/**
		 * Constructs a domain with operation and zero element and generators 
		 * and compare class.
		 * @param o is a class with operation method implement interface Operator.
		 * @param a is the generator.
		 * @param c is the compare class.
		 * @throws NullInverseException
		 * @throws NullClosedException
		 * @throws NullAssociateException
		 * @throws NullZeroException
		 * @throws NullUnitException 
		 */
		public Domain( RingOperator<? super A> o, A a, Comparator<? super A> c  ) 
								throws NullInverseException, NullClosedException, 
										NullAssociateException, NullZeroException, 
										NullUnitException {
			super( o, a, c );
			one();
		}
		
		/**
		 * @return {@code true} if this collection construct a ring, otherwise throws 
		 * corresponding exception {@code NullClosedException NullAssociateException 
		 * NullZeroException NullInverseException NullAbelException ZeroFactorException }.
		 * @throws NullClosedException
		 * @throws NullInverseException
		 * @throws NullAssociateException
		 * @throws NullZeroException
		 * @throws NullAbelException
		 * @throws ZeroFactorException 
		 */
		public boolean isDomain() throws NullClosedException, NullInverseException,
										NullAssociateException, NullZeroException, 
										NullAbelException, ZeroFactorException {
			setDomain();
			return true;
		}
		
		/**
		 * Judge whether multiply operation is abel.
		 * @return {@code true} if all the element is abel, {@code false} otherwise.
		 */
		@SuppressWarnings("unchecked")
		protected boolean isMultAbel() {
			for( A a : r ) {
				for( A b : r ) {
					if( (A) opr.multiply( a, b ) != (A) opr.multiply( b, a ) )
						return false;
				}
			}
			return true;
		}
		
		/**
		 * Internal method to initialize one element.
		 * @throws NullUnitException make sure domain is well defined.
		 */
		@SuppressWarnings("unchecked")
		protected void one() throws NullUnitException {
			if( opr.one() == null ) throw new NullUnitException();
			this.add( ( A ) opr.one() );
			this.one = new RingElement<>( ( A ) opr.one() );
			this.one.inverse = this.one;
		}
		
		/**
		 * Internal method to instance DomainElement.
		 * @throws NullAbelException
		 * @throws NullInverseException
		 * @throws NullClosedException
		 * @throws NullAssociateException
		 * @throws NullZeroException
		 * @throws ZeroFactorException
		 */
		protected void setDomain() throws NullAbelException, NullInverseException, 
										NullClosedException, NullAssociateException, 
										NullZeroException, ZeroFactorException {
			setRing();
			if( r.size() == 1 && r.get(0).equals( opr.zero() ) ) return;
			if( !isMultAbel() ) throw new NullAbelException();
			for ( RingElement<A> A : this.R ) {
				for ( RingElement<A> B : this.R ) {
					if( A.element != zero && B.element != zero && 
							multiply( A.element, B.element ) == zero )
						throw new ZeroFactorException();
				}
			}
		}
		
		@Override
		public String toString() {
			if( this == null || this.order() == 0 ) return "< null >";
			if( !r.get(0).toString().contains( "\n" ) ) {
				StringBuffer s = new StringBuffer();
				s.append( "Domain < " );
		    	for( Iterator<A> iterator = this.iterator(); iterator.hasNext(); ) {
					s.append( iterator.next() + " " );
				}
		    	s.append( ">" );
				return s.toString();
			}
			int height = 0;
			for( int i = 0; i < r.size(); i++ ) {
				String[] s = r.get(i).toString().split( "\n" );
				if( s.length > height ) height = s.length;
			}
			StringBuffer sb = new StringBuffer();
			StringBuffer[] sbarr = new StringBuffer[height];
			for( int l = 0; l < sbarr.length; l++ ) {
				sbarr[l] = new StringBuffer();
			}
			for( int i = 0; i < r.size(); i++ ) {
				String[] s = r.get(i).toString().split( "\n" );
				if( i == 0 ) {
					for( int k = 0; k < height; k++ ) {
						if( k == height/2 ) sbarr[k].append( "Domain < " );
						else sbarr[k].append( "         " );
					}
				} 
				for( int k = 0; k < height; k++ ) {
					sbarr[k].append( s[k] + " " );
				} 
				if( i == r.size()-1 ) {
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
	}
	
	/**
	 * Internal class extends class Skew called Domain.
	 * @author Zhang Ziyang
	 * @param <A>
	 */
	@SuppressWarnings("hiding")
	public class Skew<A> extends Ring<A> {

		private static final long serialVersionUID = -4557014929416607258L;

		/**
		 * Constructs a skew.
		 */
		public Skew() {}
		
		/**
		 * Constructs a skew with operation and zero element.
		 * @param o is a class with operation method implement interface Operator.
		 * @throws NullInverseException
		 * @throws NullClosedException
		 * @throws NullAssociateException
		 * @throws NullZeroException
		 * @throws NullUnitException 
		 */
		public Skew( RingOperator<? super A> o ) throws NullInverseException, 
									NullClosedException, NullAssociateException, 
									NullZeroException, NullUnitException {
			super( o );
			one();
		}
		
		/**
		 * Constructs a skew with operation and zero element and generators.
		 * @param o is a class with operation method implement interface Operator.
		 * @param a is the generator.
		 * @throws NullInverseException
		 * @throws NullClosedException
		 * @throws NullAssociateException
		 * @throws NullZeroException
		 * @throws NullUnitException 
		 */
		public Skew( RingOperator<? super A> o, A a ) throws NullInverseException,
										NullClosedException, NullAssociateException, 
										NullZeroException, NullUnitException {
			super( o, a );
			one();
		}
		
		/**
		 * Constructs a skew with operation and zero element and generators 
		 * and compare class.
		 * @param o is a class with operation method implement interface Operator.
		 * @param a is the generator.
		 * @param c is the compare class.
		 * @throws NullInverseException
		 * @throws NullClosedException
		 * @throws NullAssociateException
		 * @throws NullZeroException
		 * @throws NullUnitException 
		 */
		public Skew( RingOperator<? super A> o, A a, Comparator<? super A> c  ) 
								throws NullInverseException, NullClosedException, 
										NullAssociateException, NullZeroException, 
										NullUnitException {
			super( o, a, c );
			one();
		}
		
		/**
		 * @return {@code true} if this collection construct a ring, otherwise throws 
		 * corresponding exception {@code NullClosedException NullAssociateException 
		 * NullZeroException NullInverseException NullAbelException ZeroFactorException }.
		 * @throws NullInverseException
		 * @throws NullClosedException
		 * @throws NullAssociateException
		 * @throws NullZeroException
		 * @throws NullAbelException
		 * @throws ZeroFactorException
		 * @throws NullUnitException
		 */
		public boolean isSkew() throws NullInverseException, NullClosedException, 
										NullAssociateException, NullZeroException, 
										NullAbelException, ZeroFactorException,
										NullUnitException {
			setSkew();
			return true;
		}
		
		/**
		 * Internal method to initialize one element.
		 * @throws NullUnitException make sure skew is well defined.
		 */
		@SuppressWarnings("unchecked")
		protected void one() throws NullUnitException {
			if( opr.one() == null ) throw new NullUnitException();
			this.add( ( A ) opr.one() );
			this.one = new RingElement<>( ( A ) opr.one() );
			this.one.inverse = this.one;
		}
		
		/**
		 * Internal method to instance SkewElement.
		 * @throws NullInverseException
		 * @throws NullClosedException
		 * @throws NullAssociateException
		 * @throws NullZeroException
		 * @throws NullAbelException
		 * @throws ZeroFactorException
		 * @throws NullUnitException
		 */
		@SuppressWarnings("unchecked")
		protected void setSkew() throws NullInverseException, NullClosedException, 
										NullAssociateException, NullZeroException, 
										NullAbelException, ZeroFactorException, 
										NullUnitException {
			setRing();
			if( r.size() == 1 && r.get(0).equals( opr.zero() ) ) return;
			for ( RingElement<A> A : this.R ) {
				for ( RingElement<A> B : this.R ) {
					if( A.element != zero && B.element != zero && 
							multiply( A.element, B.element ) == zero )
						throw new ZeroFactorException();
				}
			}
			for( RingElement<A> Element : R ) {
				if( Element.element == ( A ) opr.zero() ) continue;
				if( Element.element == ( A ) opr.one() ) Element.inverse = Element;
				for( RingElement<A> inElement : R ) {
					if( inElement.element == ( A ) opr.one() || 
							inElement.element == ( A ) opr.zero() ) continue;
					if( multiply( Element.element, inElement.element ) == ( A ) opr.one() )
						Element.inverse = inElement;
				}
			}
			for( RingElement<A> Element : R ) {
				if( Element.inverse == null ) throw new NullUnitException();
			}
		}
		
		@Override
		public String toString() {
			if( this == null || this.order() == 0 ) return "< null >";
			if( !r.get(0).toString().contains( "\n" ) ) {
				StringBuffer s = new StringBuffer();
				s.append( "Skew < " );
		    	for( Iterator<A> iterator = this.iterator(); iterator.hasNext(); ) {
					s.append( iterator.next() + " " );
				}
		    	s.append( ">" );
				return s.toString();
			}
			int height = 0;
			for( int i = 0; i < r.size(); i++ ) {
				String[] s = r.get(i).toString().split( "\n" );
				if( s.length > height ) height = s.length;
			}
			StringBuffer sb = new StringBuffer();
			StringBuffer[] sbarr = new StringBuffer[height];
			for( int l = 0; l < sbarr.length; l++ ) {
				sbarr[l] = new StringBuffer();
			}
			for( int i = 0; i < r.size(); i++ ) {
				String[] s = r.get(i).toString().split( "\n" );
				if( i == 0 ) {
					for( int k = 0; k < height; k++ ) {
						if( k == height/2 ) sbarr[k].append( "Skew < " );
						else sbarr[k].append( "       " );
					}
				} 
				for( int k = 0; k < height; k++ ) {
					sbarr[k].append( s[k] + " " );
				} 
				if( i == r.size()-1 ) {
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
	}

	@Override
	public int size() {
		return r.size();
	}

	@Override
	public Object[] toArray() {
		return r.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return r.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		return r.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return r.containsAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return r.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return r.retainAll(c);
	}
	
}
