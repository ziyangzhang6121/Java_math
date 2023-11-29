package javam.geometry;

import java.util.ArrayList;
import java.util.LinkedList;

import javam.algebra.Polynomial;
import javam.exception.NullOperateException;
import javam.number.Fraction;
import javam.number.Valuation;

public class NewtonPoly {
	
	public LinkedList<Integer> val = new LinkedList<>();
	
	public ArrayList<Integer> node = new ArrayList<>();
	
	public NewtonPoly( Polynomial<Fraction> f, Polynomial<Fraction> v, int p ) 
			throws NullOperateException {
		for( Polynomial<Fraction> pp : f.polyOf( v ) ) {
			val.add( Valuation.valOf( pp, p) );
		}
		int k = 0;
		for( node.add( k ); (k = nextIndex( k )) < val.size(); );
	}
	
	private int nextIndex( int i ) {
		if( i == val.size() ) return i;
		else {
			int t = i;
			double a = 1000000000, tem;
			for( int k = i + 1; k < val.size(); k++ ) {
				if( (tem = (double)(val.get( k ) - val.get( i ) )/(double)(k - i)) < a ) {
					a = tem;
					t = k;
				}
			}
			return t;
		}
	}
	
}
