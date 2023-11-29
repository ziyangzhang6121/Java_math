import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import javac.adt.AvlTree;
import javam.algebra.Algebra;
import javam.algebra.Group;
import javam.algebra.GroupOperator;
import javam.algebra.Mapping;
import javam.algebra.Matrix;
import javam.algebra.Polynomial;
import javam.algebra.Ring;
import javam.algebra.RingOperator;
import javam.number.AlgNumber;
import javam.number.Fraction;
import javam.number.GreekAlphabet;

public class AlgebraDemo {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws Exception {
		
		AvlTree<Integer> b = new AvlTree<>( new A() );
		Integer[] arr = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,
				17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
		b.add(arr);
		b.printTreeInOrder();
		System.out.println("   ");
		b.printTreeInHeight();
		System.out.println(" ");
		System.out.println("Max = "+b.findMax());
		System.out.println("Size = "+b.size());
		System.out.println("Height = "+b.height());
		System.out.println("------------------------------------------\n");
		
		LinkedList<Group<Integer>> l = new LinkedList<>();
		LinkedList<Group<Integer>> ll = new LinkedList<>();
		LinkedList<LinkedList<Group<Integer>>> lll = new LinkedList<>();
		Group<Integer> g = new Group<>( new O() );
		g.addAll(arr);
		System.out.println(g.isGroup());
		g.print();
		g.subGroup(l);
		System.out.println("-------------");
		for ( Group<Integer> group : l ) {
			group.print();
		}
		System.out.println("-------------");
		g.cgtGroup( g.subGroup(2), ll );
		for ( Group<Integer> group : ll ) {
			group.print();
		}
		System.out.println("-------------");
		g.cgtGroup( lll );
		for ( LinkedList<Group<Integer>> group : lll ) {
			for ( Group<Integer> group0 : group ) {
				group0.print();
			}
			System.out.println("---");
		}
		Group<Mapping<Integer, Integer>> G = g.LeftRegularExpression();
		System.out.println( G.get(28) );
		System.out.println( g );
		System.out.println("------------------------------------------\n");

		Ring<Integer> r = new Ring<>( new P() );
		Ring<Integer>.Domain<Integer> d = r.new Domain<>( new P() );
		LinkedList<Ring<Integer>> s = new LinkedList<>();
		r.addAll( arr );
		d.addAll( arr );
		System.out.println( r.ideal(2) );
		r.subRing( s );
		for (Ring<Integer> ring : s) {
			ring.print();
		}
		System.out.println( d );
		System.out.println("------------------------------------------\n");
		
		System.out.println( new Mapping<>() );
		Mapping<Integer, Double> aaa = new Mapping<>();
		aaa.put( 2, 9.8 );
		aaa.put( 78, 8.7 );
		aaa.put( 283, 9.08 );
		aaa.put( 23, 9.1 );
		System.out.println( aaa );
		Mapping<Integer, Integer> x = new Mapping<>();
		Mapping<Integer, Integer> y = new Mapping<>();
		x.put( 1, 4 );
		x.put( 2, 5 );
		y.put( 2, 5 );
		y.put( 1, 4 );
		System.out.println( x );
		System.out.println( y );
		System.out.println( x.equals(y) );
		System.out.println("------------------------------------------\n");
		
		System.out.println( new int[3] instanceof Object );
		Integer[][] mat = { { 1, 3, 6 }, { 2, 4, 8 }, { 3, 9, 7 } };
		Matrix<Integer> m = new Matrix<>( mat );
		Matrix<Integer> i = m.inverse();
		System.out.println( i );
		System.out.println( m.adjugate() );
		System.out.println( m.cofactor(2, 2) );
		System.out.println( new Fraction( "-34.68798" ).pow(7) );
		Double[][] matf = { { 1.0, 3.0, 6.0 }, { 2.0, 4.0, 8.0 }, { 3.0, 9.0, 7.0 } };
		Fraction[][] matff = new Fraction[3][3];
		for( int j1 = 0; j1 < matff.length; j1++ ) {
			for( int j2 = 0; j2 < matff[0].length; j2++ ) {
				matff[j1][j2] = new Fraction( matf[j1][j2] ); 
			}
		}
		Matrix<Fraction> mf = new Matrix<>( matff, Algebra.FRAC_MAT_OPERATOR );
		System.out.println( mf.cofactor( 3, 1 ) );
		System.out.println( mf.inverse() );
		System.out.println
		( "The characteristic polynomial = " + mf.inverse().charPolynomial() );
		System.out.println("------------------------------------------\n");
		
		Polynomial<Integer> p1 = new Polynomial<>( "x^2+1" );
		Polynomial<Integer> p2 = new Polynomial<>( "x^4+2x^2+1" );
		p1.setLetter( GreekAlphabet.DELTA.lowercase );
		System.out.println( p1.gcdOf( p2 ) + "\n" + p1.lcmOf( p2 ) );
		System.out.println( "Remainder = " + p2.devide( p1 )[1] + 
				"\n" + "Quotient = " + p2.devide( p1 )[0] );
		Polynomial<Fraction> p3 = 
				new Polynomial<>( "x^4+2/7x^2+1", Algebra.FRAC_POLY_OPERATOR );
		Polynomial<Fraction> p4 = 
				new Polynomial<>( "9x^3+2x^2+5x+3", Algebra.FRAC_POLY_OPERATOR );
		p4.setLetter( GreekAlphabet.ALPHA.lowercase );
		System.out.println( "p4 has multiple root = " + p4.hasMultipleRoot() );
		System.out.println( "Remainder = " + p4.devideR( p3 ) + 
				"\n" + "Quotient = " + p4.devideQ( p3 ) );
		System.out.println( p4.valueOf( new Fraction( "-3/8" ) ) );
		System.out.println( p4.derivative() + "\n" + p3.eliminantOf( p4 ) );
		System.out.println("------------------------------------------\n");
		
		Integer[][] i1 = { { 1, 3, 5 }, { 2, 4, 9 }, { 2, 7, 4 } };
		Integer[][] i2 = { { 5 }, { 5 }, { 3 } };
		Integer[][] i3 = { { 2, 5, 6 } };
		Integer[][] i4 = { { 4 } };
		Matrix<Integer> m1 = new Matrix<>( i1, Algebra.INT_MAT_OPERATOR );
		Matrix<Integer> m2 = new Matrix<>( i2, Algebra.INT_MAT_OPERATOR );
		Matrix<Integer> m3 = new Matrix<>( i3, Algebra.INT_MAT_OPERATOR );
		Matrix<Integer> m4 = new Matrix<>( i4, Algebra.INT_MAT_OPERATOR );
		Matrix[][] mm = { { m1, m2 }, { m3, m4 } };
		Matrix<Matrix<Integer>> kk = new Matrix
			( mm, Algebra.MatMatOperator( Algebra.INT_MAT_OPERATOR, 3, 3 ) );
		System.out.println( kk + "\n" );
		Integer[][] i5 = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
		Integer[][] i6 = { { -1, 0, 0 }, { 0, -1, 0 }, { 0, 0, -1 } };
		Matrix<Integer> m5 = new Matrix<>( i5, Algebra.INT_MAT_OPERATOR );
		Matrix<Integer> m6 = new Matrix<>( i6, Algebra.INT_MAT_OPERATOR );
		Group<Matrix<Integer>> gm = new Group<>
			( Algebra.MatGroupOperator( Algebra.INT_MAT_OPERATOR, 3 ) );
		gm.add( m5 );
		gm.add( m6 );
		System.out.println( gm + "\n" );
		Mapping<Matrix<Integer>, Matrix<Matrix<Integer>>> mmp = new Mapping<>();
		mmp.put( m5, kk );
		mmp.put( m6, kk );
		mmp.put( m1, kk );
		System.out.println( mmp + "\n" );
		Integer[][] i7 = { { 1, 0, 6 }, { 3, 1, 0 }, { 98, 0, 1 } };
		Matrix<Integer> m7 = new Matrix<>( i7, Algebra.INT_MAT_OPERATOR );
		Mapping<Matrix<Integer>, Matrix<Integer>> mth = new Mapping();
		mth.put( m5, m6 );
		mth.put( m6, m5 );
		mth.put( m1, m7 );
		mth.put( m7, m1 );
		System.out.println( mth + "\n" );
		Polynomial<Fraction> p5 = 
				new Polynomial<>( "x^4+2x^2 + 9/6x-2x^4", Algebra.FRAC_POLY_OPERATOR );
		p5.setLetter( mf.inverse().toString() );
		System.out.println( p5 + "\n = \n" + p5.valueOf( mf.inverse() ) + "\n" );
		ArrayList<Matrix<Integer>> ami = new ArrayList<>();
		ami.add( m1 );
		ami.add( m7 );
		ami.add( m6 );
		ami.add( m5 );
		Polynomial<Matrix<Integer>> pmi = new Polynomial<>
			( ami, Algebra.MatPolyOperator( Algebra.INT_MAT_OPERATOR, 3 ) );
		pmi.setLetter( m1.toString() );
		System.out.println( pmi + "\n" );
		System.out.println( Algebra.group.RightRegularExpression() );
		System.out.println( Group.S_5 );
		System.out.println( Group.K_4 );
		System.out.println( new Polynomial<>( "1+x", Algebra.BIGINT_POLY_OPERATOR )
				.setLetter( GreekAlphabet.BETA.lowercase ).pow(50).pow(2) );
		System.out.println("------------------------------");

		Polynomial<Integer> ppp = 
				new Polynomial<Integer>( "x^5+x^3+x^2+x-1", Algebra.MOD_POLY_OPERATOR(3) );		
		Polynomial<Integer> fff = 
				new Polynomial<>( "x^4+x^3+x-1", Algebra.MOD_POLY_OPERATOR(3) );
		System.out.println
			( Polynomial.primeFieldFactors( ppp.pow(3).multiply(fff).pow(2), 3 ) );
		System.out.println( Polynomial.primeFieldFactor(ppp, 3) + "\n" );
		
		
		AlgNumber alg = new AlgNumber( "x^4+9x+1", "x^6+10000x+1" );
		System.out.println( alg.inverse().negate() );
		System.out.println( alg.inverse().multiply( alg ) );
		
 	}
	
}

class A implements Comparator<Integer> {

	@Override
	public int compare( Integer o1, Integer o2 ) {
		// TODO Auto-generated method stub
		String s1 = o1.toString();
		String ss1 = s1.substring( 0,1 );
		Integer i1 = Integer.valueOf( ss1 );
		
		String s2 = o2.toString();
		String ss2 = s2.substring( 0,1 );
		Integer i2 = Integer.valueOf( ss2 );
		
		if( i1 > i2 ) return 1;
		if( i1 < i2 ) return -1;
		if( i1 == i2 && o1 == o2 ) return 0;
		if( i1 == i2 && o1 > o2 ) return 1;
		return -1;
	}
	
}

class O implements GroupOperator<Integer> {

	@Override
	public Integer unit() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Integer multiply( Integer a, Integer b ) {
		// TODO Auto-generated method stub
		return a * b;
	}
	
	@Override
	public Integer simplify( Integer a ) {
		// TODO Auto-generated method stub
		return a % 41;
	}
	
	@Override
	public boolean isGenerate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer generate( Integer a ) { 
		return a % 41;
	}

}

class P implements RingOperator<Integer> {

	@Override
	public Integer zero() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer one() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Integer add(Integer a, Integer b) {
		// TODO Auto-generated method stub
		return (a+b)%30;
	}

	@Override
	public Integer multiply(Integer a, Integer b) {
		// TODO Auto-generated method stub
		return (a*b)%30;
	}

	@Override
	public Integer simplify(Integer a) {
		// TODO Auto-generated method stub
		return a%30;
	}
	
}