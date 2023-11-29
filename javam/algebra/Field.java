package javam.algebra;

import java.util.Comparator;

import javam.exception.NullAbelException;
import javam.exception.NullAssociateException;
import javam.exception.NullClosedException;
import javam.exception.NullInverseException;
import javam.exception.NullUnitException;
import javam.exception.NullZeroException;
import javam.exception.ZeroFactorException;

public class Field<A> extends Ring<A> {

	private static final long serialVersionUID = 7356266661221889252L;

	/**
	 * Constructs a field.
	 */
	public Field() {}
	
	/**
	 * Constructs a field with operation and zero element.
	 * @param o is a class with operation method implement interface Operator.
	 * @throws NullInverseException
	 * @throws NullClosedException
	 * @throws NullAssociateException
	 * @throws NullZeroException
	 * @throws NullUnitException 
	 */
	public Field( RingOperator<? super A> o ) throws NullInverseException,
								NullClosedException, NullAssociateException, 
								NullZeroException, NullUnitException {
		super( o );
		one();
	}
	
	/**
	 * Constructs a field with operation and zero element and generators.
	 * @param o is a class with operation method implement interface Operator.
	 * @param a is the generator.
	 * @throws NullInverseException
	 * @throws NullClosedException
	 * @throws NullAssociateException
	 * @throws NullZeroException
	 * @throws NullUnitException 
	 */
	public Field( RingOperator<? super A> o, A a ) throws NullInverseException,
									NullClosedException, NullAssociateException, 
									NullZeroException, NullUnitException {
		super( o, a );
		one();
	}
	
	/**
	 * Constructs a field with operation and zero element and generators 
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
	public Field( RingOperator<? super A> o, A a, Comparator<? super A> c  ) 
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
	 * @throws NullUnitException 
	 */
	public boolean isField() throws NullClosedException, NullInverseException,
									NullAssociateException, NullZeroException, 
									NullAbelException, ZeroFactorException,
									NullUnitException {
		setField();
		return true;
	}
	
	/**
	 * Judge whether multiply operation is abel.
	 * @return {@code true} if all the element is abel, {@code false} otherwise.
	 */
	@SuppressWarnings("unchecked")
	private boolean isMultAbel() {
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
	 * @throws NullUnitException make sure field is well defined.
	 */
	@SuppressWarnings("unchecked")
	private void one() throws NullUnitException {
		if( opr.one() == null ) throw new NullUnitException();
		this.add( ( A ) opr.one() );
		this.one = new RingElement<>( ( A ) opr.one() );
		this.one.inverse = this.one;
	}
	
	/**
	 * Internal method to instance FieldElement.
	 * @throws NullAbelException
	 * @throws NullInverseException
	 * @throws NullClosedException
	 * @throws NullAssociateException
	 * @throws NullZeroException
	 * @throws ZeroFactorException
	 * @throws NullUnitException 
	 */
	@SuppressWarnings("unchecked")
	private void setField() throws NullAbelException, NullInverseException, 
									NullClosedException, NullAssociateException, 
									NullZeroException, ZeroFactorException,
									NullUnitException {
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
	
}
