package javam.algebra;

public interface MatOperator<A> {

	public A multiply( A a, A b );
	
	public A add( A a, A b );
	
	public A devide( A a, A b );
	
	public A pow( A a, int n );
	
	public A negate( A a );
	
	public A construct( String s );
	
	public A zero();
	
	public A unit();
	
	public A simplify( A a );
	
}

