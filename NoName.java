import java.util.ArrayList;

import javam.algebra.MatOperator;
import javam.algebra.Matrix;
import javam.number.Fraction;

public class NoName {
	
	static Matrix<Fraction> m = new Matrix<>();
	
	static ArrayList<Matrix<Fraction>> arr = new ArrayList<>();
	
	static ArrayList<ArrayList<Fraction>> marr = new ArrayList<>();
	
	public static Fraction getM_i( int i, Matrix<Fraction> m ) {
		Fraction m_i = Fraction.ZERO;
		for (int j = 1; j <= m.col; j++) {
			m_i = m_i.add( m.element( i, j ) );
		}
		return m_i.devide( new Fraction( m.col, 1 ) );
	}
	
	public static Fraction getM_j( int j, Matrix<Fraction> m ) {
		Fraction m_j = Fraction.ZERO;
		for (int i = 1; i <= m.row; i++) {
			m_j = m_j.add( m.element( i, j ) );
		}
		return m_j.devide( new Fraction( m.row, 1 ) );
	}
	
	public static Matrix<Fraction> getM_k( int k, Matrix<Fraction> m ) {
		Matrix<Fraction> m_k;
		if( k == 1 ) {
			arr.clear();
			m_k = m;
			arr.add( m_k );
		} else {
			getM_k( k-1, m );
			m_k = arr.get( k-1 );
		}
		
		for (int i = 1; i <= m_k.row; i++) {
			ArrayList<Fraction> I = new ArrayList<>();
			for (int j = 1; j <= m_k.col; j++) {
				Fraction m_s = Fraction.ZERO;
				Fraction m_is2 = Fraction.ZERO;
				Fraction m_js2 = Fraction.ZERO;
				for (int s = 1; s <= m_k.col; s++) {
					Fraction t_is = m_k.element( i, s ).subtract( getM_i( i, m_k ) );
					Fraction t_js = m_k.element( j, s ).subtract( getM_j( j, m_k ) );
					m_s = m_s.add( t_is.multiply( t_js ) );
					m_is2 = m_is2.add( t_is.pow( 2 ) );						
					m_js2 = m_js2.add( t_js.pow( 2 ) );
				}
				Fraction ff = m_is2.multiply( m_js2 ).pow( 0.5 );
				I.add( m_s.devide( ff ) );
			}
			marr.add( I );
		}
		m_k = new Matrix<>( marr, new N() );
		arr.add( m_k );
		marr.clear();
		return m_k;
	}
	
	public static void main(String[] args) {
		
		Fraction[][] f = { { new Fraction( "2/3" ), new Fraction( "1/2" ), new Fraction( "1/6" ) },
				{ new Fraction( "3/8" ), new Fraction( "3/4" ), new Fraction( "3/5" ) },
				{ new Fraction( "11/3" ), new Fraction( "6/9" ), new Fraction( "3/8" ) } };
		m = new Matrix<>( f, new N() );
				
		System.out.println( getM_k( 1, m ) );
		System.out.println( arr.size() );	
		
	}
	
}


class N implements MatOperator<Fraction>{
	
	public Fraction multiply(Fraction a, Fraction b) { return a.multiply( b ); }
	
	public Fraction add(Fraction a, Fraction b) { return a.add( b ); }
	
	public Fraction devide(Fraction a, Fraction b) { return a.devide( b ); }

	public Fraction pow(Fraction a, int n) { return a.pow( n );}

	public Fraction negate(Fraction a) { return a.negate(); }
	
	public Fraction construct(String s) { return new Fraction( s );}

	public Fraction zero() { return Fraction.ZERO; }

	public Fraction unit() { return Fraction.ONE;}

	public Fraction simplify(Fraction a) { return a; }
	
}