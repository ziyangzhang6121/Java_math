import javam.algebra.Algebra;
import javam.algebra.Polynomial;
import javam.exception.NullOperateException;
import javam.number.AlgNumber;
import javam.number.Fraction;

public class Nanimo {
	
	public static void main(String[] args) throws NullOperateException {
		
		Polynomial<Fraction> p = 
				new Polynomial<>("3+x^3",Algebra.FRAC_POLY_OPERATOR);
		Polynomial<Fraction> v = 
				new Polynomial<>("x^2+9/7x+67x^5",Algebra.FRAC_POLY_OPERATOR);
		
		System.out.println( p.multiply( v ) );
		System.out.println( p.gcdOf( v ) );
		System.out.println("-------------------------------");
		
		AlgNumber a = new AlgNumber( v, p );
		AlgNumber b = new AlgNumber( 
				new Polynomial<Fraction>( "x^7", Algebra.FRAC_POLY_OPERATOR ), p ); 
		AlgNumber c = a.devide( b );
		
		System.out.println( c );
		System.out.println( c.multiply( b ).devide(a) );
		System.out.println("-------------------------------");
		
		Polynomial<Integer> q1 = 
				new Polynomial<Integer>( "x^5+x^3+x^2+x-1", Algebra.MOD_POLY_OPERATOR(3) );		
		Polynomial<Integer> q2 = 
				new Polynomial<>( "x^4+x^3+x-1", Algebra.MOD_POLY_OPERATOR(3) );
		
		System.out.println( q1.multiply( q2 ) );
		System.out.println( Polynomial.primeFieldFactor( q1.multiply( q2 ), 3) );
		
		for (int i = 0; i < 10; i++) {
			System.out.println( i*Math.PI/5 + "   " + Math.cos( i*Math.PI/5 ) );
		}
		
	}
	
}
