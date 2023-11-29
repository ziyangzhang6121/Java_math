package javam.algebra;

public interface PolyOperator<A> {

	public A multiply( A a, A b );
	
	public A add( A a, A b );
	
	public A subtract( A a, A b );
	
	public A devide( A a, A b );
	
	public A negate( A a );
	
	public A construct( String s );
	
	public A zero();
	
	public A unit();
	
}
