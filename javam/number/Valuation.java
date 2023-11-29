package javam.number;

import java.math.BigInteger;

import javam.algebra.Polynomial;

@SuppressWarnings("null")
public class Valuation {
	
	public static int valOf( BigInteger bi, BigInteger p ) {
		if( !Prime.isPrime( p ) ) return (Integer) null;
		else if( bi.equals( BigInteger.ZERO )  ){
			return 1000000000;
		}else {
			int v = 0;
			while( bi.divideAndRemainder( p )[1].equals( BigInteger.ZERO ) ) {
				bi = bi.divide( p );
				v++;
			}
			return v;
		}
	}
	
	public static int valOf( int i, int p ) {
		if( !Prime.isPrime( p ) ) return (Integer) null;
		else if( i == 0 ){
			return 1000000000;
		}else {
			int v = 0;
			while( i % p == 0 ) {
				i = i / p;
				v++;
			}
			return v;
		}
	}
	
	public static int valOf( Fraction f, BigInteger p ) {
		try {
			return valOf( f.molecule, p ) - valOf( f.denominator, p );
		} catch ( NullPointerException n ) {
			return (Integer) null;
		}
	}
	
	public static int valOf( Fraction f, int p ) {
		return valOf( f, BigInteger.valueOf( p ) );
	}
	
	public static int valOf( Polynomial<Fraction> pf, BigInteger p ) {
		try {
			int v = 1000000000, b = 0;
			for( int i = 0; i <= pf.exponent; i++ ) {
				if( (b = valOf( pf.coefficientAt( i ), p )) < v  ) {
					v = b;
				}
			}
			return v;
		} catch( NullPointerException n ) {
			return (Integer) null;
		}
	}
	
	public static int valOf( Polynomial<Fraction> pf, int p ) {
		return valOf( pf, BigInteger.valueOf( p ) );
	}
	
}
