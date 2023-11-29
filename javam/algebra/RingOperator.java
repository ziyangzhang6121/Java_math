package javam.algebra;

public interface RingOperator<A> {
	
	/**
	 * State zero element of ring.
	 * @return zero element's value
	 */
	public A zero();
	
	/**
	 * State one element of ring, sometimes might be null.
	 * @return one element's value
	 */
	public A one();
	
	/**
	 * State addition of ring.
	 * @param a is the left element of addition.
	 * @param b is the right element of addition.
	 * @return the result of addition.
	 */
	public A add( A a, A b );
	
	/**
	 * State multiplication of ring.
	 * @param a is the left element of multiply.
	 * @param b is the right element of multiply.
	 * @return the result of multiplication.
	 */
	public A multiply( A a, A b );
	
	/**
	 * Simplify the result of element's value.
	 * <p> Override it while using.
	 * @param a is the result of element's value.
	 * @return the simple value.
	 */
	public A simplify( A a );
	
}
