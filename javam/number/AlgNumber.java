package javam.number;

import java.util.ArrayList;

import javam.algebra.Algebra;
import javam.algebra.Polynomial;
import javam.exception.NullOperateException;

public class AlgNumber extends Polynomial<Fraction>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Polynomial<Fraction> p;
	
	public AlgNumber( String str, Polynomial<Fraction> p ) throws NullOperateException {
		super( str, Algebra.FRAC_POLY_OPERATOR );
		this.p = p;
	}
	
	public AlgNumber( Polynomial<Fraction> f, Polynomial<Fraction> p ) 
			throws NullOperateException {
		this( f.toString(), p );
	}
	
	public AlgNumber( String s, String p ) throws NullOperateException {
		this( s, new Polynomial<Fraction>( p, Algebra.FRAC_POLY_OPERATOR ) );
	}
	
	public AlgNumber( int i, String s ) throws NullOperateException {
		this( String.valueOf(i), s );
	}
	
	public AlgNumber add( AlgNumber a ) throws NullOperateException {
		if( !this.p.equals( a.p ) ) return null;
		else return new AlgNumber( super.add( a ).devideR( this.p ), this.p );
	}
	
	public AlgNumber subtract( AlgNumber a ) throws NullOperateException {
		if( !this.p.equals( a.p ) ) return null;
		else return new AlgNumber( super.subtract( a ).devideR( this.p ), this.p );
	}
	
	public AlgNumber multiply( AlgNumber a ) throws NullOperateException {
		if( !this.p.equals( a.p ) ) return null;
		else return new AlgNumber( super.multiply( a ).devideR( this.p ), this.p );
	}

	public AlgNumber devide( AlgNumber a ) throws NullOperateException {
		if( !this.p.equals( a.p ) ) return null;
		else {
			AlgNumber b = new AlgNumber( Bazout( this.p, a ).get(1), this.p );
			return this.multiply( b );
		}
	}
	
	public AlgNumber negate() throws NullOperateException {
		AlgNumber ZERO = new AlgNumber( "0", this.p );
		return ZERO.subtract( this );
	}
	
	public AlgNumber inverse() throws NullOperateException {
		return new AlgNumber( "1", this.p ).devide( this );
	}
	
	private ArrayList<Polynomial<Fraction>> Bazout( 
			Polynomial<Fraction> a, Polynomial<Fraction> b ) throws NullOperateException {
		Polynomial<Fraction> p1, p2 = null;
		if( b.devideR( a.devideR(b) ).poly.size() == 0 ) {
			ArrayList<Polynomial<Fraction>> arr = new ArrayList<>();
			Fraction[] arr0 = { a.devideR(b).poly.get(0).coefficient.inverse() };
			Fraction[] arr1 = { a.devideR(b).poly.get(0).coefficient.inverse().negate() };
			p1 = new Polynomial<Fraction>( arr0, Algebra.FRAC_POLY_OPERATOR );
			p2 = new Polynomial<Fraction>( arr1, Algebra.FRAC_POLY_OPERATOR ).
					multiply( a.devideQ( b ) );
			arr.add( p1 );
			arr.add( p2 );
			return arr;
		}else {
			ArrayList<Polynomial<Fraction>> temarr = Bazout( b, a.devideR( b ) );
			p1 = temarr.get(1);
			p2 = temarr.get(0).subtract( temarr.get(1).multiply( a.devideQ( b ) ) );
			ArrayList<Polynomial<Fraction>> arr = new ArrayList<>();
			arr.add( p1 );
			arr.add( p2 );
			return arr;
		}
	}
	
	@Override
	public String toString() {
		String str = null;
		try {
			str = new Polynomial<Fraction>( super.toString(), 
					Algebra.FRAC_POLY_OPERATOR ).devideR( this.p ).toString();
		} catch (NullOperateException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@SuppressWarnings("null")
	@Override
	public boolean equals(Object obj) {
		if( !(obj instanceof AlgNumber) ) return false;
		AlgNumber a = ( AlgNumber ) obj;
		try {
			return this.devideR( p ).equals( a.devideR( p ) );
		} catch (NullOperateException e) {
			return (Boolean) null;
		}
	}
	
}
