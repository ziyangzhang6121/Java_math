import java.util.ArrayList;

import javam.algebra.Algebra;
import javam.algebra.Matrix;
import javam.algebra.Polynomial;
import javam.exception.NullOperateException;

public class NoName2 {
	
	static Matrix<Double> m = new Matrix<>();
	
	static ArrayList<Matrix<Double>> arr = new ArrayList<>();
	
	static ArrayList<ArrayList<Double>> marr = new ArrayList<>();
	
	public static Double getM_i( int i, Matrix<Double> m ) {
		Double m_i = (double) 0;
		for (int j = 1; j <= m.col; j++) {
			m_i += m.element( i, j );
		}
		return m_i / m.col;
	}
	
	public static Double getM_j( int j, Matrix<Double> m ) {
		Double m_j = (double) 0;
		for (int i = 1; i <= m.row; i++) {
			m_j += m.element( i, j );
		}
		return m_j / m.row;
	}
	
	public static Matrix<Double> getM_k( int k, Matrix<Double> m ) {
		Matrix<Double> m_k;
		if( k == 1 ) {
			arr.clear();
			m_k = m;
			arr.add( m_k );
		} else {
			getM_k( k-1, m );
			m_k = arr.get( k-1 );
		}
		
		for (int i = 1; i <= m_k.row; i++) {
			ArrayList<Double> I = new ArrayList<>();
			for (int j = 1; j <= m_k.col; j++) {
				double m_s = 0.0;
				double m_is2 = 0.0;
				double m_js2 = 0.0;
				for (int s = 1; s <= m_k.col; s++) {
					double t_is = m_k.element( i, s ) - getM_i( i, m_k );
					double t_js = m_k.element( j, s ) - getM_j( j, m_k );
					m_s += t_is * t_js;
					m_is2 = m_is2 + Math.pow( t_is, 2 );					
					m_js2 = m_js2 + Math.pow( t_js, 2 );	
				}
				Double ff = Math.pow( m_is2 * m_js2, 0.5 );
				I.add( m_s / ff );
			}
			marr.add( I );
		}
		m_k = new Matrix<>( marr, Algebra.DOUBLE_MAT_OPERATOR );
		arr.add( m_k );
		marr.clear();
		return m_k;
	}
	
	public static Matrix<Double> setRandom( int n ) {
		ArrayList<ArrayList<Double>> aa = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ArrayList<Double> bb = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				bb.add( Math.random() );
			}
			aa.add( bb );
		}
		return new Matrix<>( aa, Algebra.DOUBLE_MAT_OPERATOR );
	}
	
	public static void main(String[] args) throws NullOperateException {
		
		/*
		Double[][] f = { { 0.1, 0.3, 3.2 },
				{ 9.6, 3.4, 2.1 },
				{ 4.4, 5.4, 7.9 } };
		m = new Matrix<>( f, new M() );
		*/
		
		int n = 1;
		getM_k( 1, m = setRandom( 5 ) );
		while ( ! arr.get( n ).equals( arr.get( n - 1 ) ) ) {
			n++;
			getM_k( n, m );
		}
		
		Polynomial<Matrix<Double>> p = new Polynomial<>( arr,
				Algebra.MatPolyOperator( Algebra.DOUBLE_MAT_OPERATOR, 5 ) );
		
		System.out.println( p );
		
	}
	
}