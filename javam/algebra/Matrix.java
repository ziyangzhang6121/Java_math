package javam.algebra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javam.exception.NullOperateException;
import javam.number.GreekAlphabet;

/**
 * @author Zhang Ziyang
 * @param <A>
 */
public class Matrix<A> implements Construct<Matrix<A>>, Serializable, Collection<A> {

	private static final long serialVersionUID = 7618237696516095230L;

	/**
	 * Point to matrix's row.
	 */
	public int row;
	
	/**
	 * Point to matrix's col.
	 */
	public int col;
	
	/**
	 * Point to all elements of this matrix.
	 */
	protected ArrayList<ArrayList<A>> mat = new ArrayList<>();
	
	/**
	 * Point to this matrix's operators.
	 */
	private MatOperator<? super A> opr;
	
	/**
	 * Constructs a matrix.
	 */
	public Matrix() {}
	
	/**
	 * Constructs a matrix only with its operator interface.
	 * @param opr is an operator interface.
	 */
	public Matrix( MatOperator<? super A> opr ) {
		this.opr = opr;
	}
	
	/**
	 * Constructs a matrix by an array and its operators is basic numbers' operators.
	 * @param m is an array storing matrix's elements.
	 */
	@SuppressWarnings("unchecked")
	public Matrix( A[][] m ) {
		this.row = m.length;
		this.col = m[0].length;
		this.opr = (MatOperator<? super A>) new MatOperator<Number>() {
			@Override
			public Number multiply(Number a, Number b) {				
				return a.doubleValue() * b.doubleValue();
			}
			@Override
			public Number add(Number a, Number b) {				
				return a.doubleValue() + b.doubleValue();
			}
			@Override
			public Number simplify(Number a) {				
				return a.doubleValue();
			}
			@Override
			public Number negate(Number a) {				
				return -a.doubleValue();
			}
			@Override
			public Number unit() {				
				return 1.0;
			}
			@Override
			public Number devide(Number a, Number b) {				
				return a.doubleValue() / b.doubleValue();
			}
			@Override
			public Number zero() {				
				return 0.0;
			}
			@Override
			public Number pow(Number a, int n) {				
				return Math.pow( (double) a, n );
			}
			@Override
			public Number construct(String s) {				
				return Double.parseDouble( s );
			}
		};
		for (int i = 0; i < m.length; i++) {
			ArrayList<A> tem = new ArrayList<>();
			for (int j = 0; j < m[0].length; j++) {
				tem.add( (A) opr.simplify( m[i][j] ) );
			}
			mat.add( tem );
		}
	}
	
	/**
	 * Constructs a matrix by an arraylist and its operators is basic numbers' operators.
	 * @param m is an arraylist storing matrix's elements.
	 */
	@SuppressWarnings("unchecked")
	public Matrix( ArrayList<ArrayList<A>> m ) {
		this.row = m.size();
		this.col = m.get(0).size();
		this.opr = (MatOperator<? super A>) new MatOperator<Number>() {
			@Override
			public Number multiply(Number a, Number b) {			
				return a.doubleValue() * b.doubleValue();
			}
			@Override
			public Number add(Number a, Number b) {				
				return a.doubleValue() + b.doubleValue();
			}
			@Override
			public Number simplify(Number a) {				
				return a.doubleValue();
			}
			@Override
			public Number negate(Number a) {				
				return -a.doubleValue();
			}
			@Override
			public Number unit() {				
				return 1.0;
			}
			@Override
			public Number devide(Number a, Number b) {				
				return a.doubleValue() / b.doubleValue();
			}
			@Override
			public Number zero() {				
				return 0.0;
			}
			@Override
			public Number pow(Number a, int n) {				
				return Math.pow( (double) a, n );
			}
			@Override
			public Number construct(String s) {
				return Double.parseDouble( s );
			}
		};
		for (int i = 0; i < m.size(); i++) {
			ArrayList<A> tem = new ArrayList<>();
			for (int j = 0; j < m.get(0).size(); j++) {
				tem.add( (A) opr.simplify( m.get(i).get(j) ) );
			}
			mat.add( tem );
		}
	}
	
	/**
	 * Constructs a matrix by another matrix.
	 * @param m is another matrix.
	 */
	public Matrix( Matrix<A> m ) {
		this( m.mat );
	}

	/**
	 * Constructs a matrix by an array and an operator interface.
	 * @param m is an array storing matrix's elements.
	 * @param opr is an operator interface.
	 */
	@SuppressWarnings("unchecked")
	public Matrix( A[][] m, MatOperator<? super A> opr ) {
		this.row = m.length;
		this.col = m[0].length;
		this.opr = opr;
		for (int i = 0; i < m.length; i++) {
			ArrayList<A> tem = new ArrayList<>();
			for (int j = 0; j < m[0].length; j++) {
				tem.add( (A) opr.simplify( m[i][j] ) );
			}
			mat.add( tem );
		}
	}
	
	/**
	 * Constructs a matrix by an arraylist and an operator interface.
	 * @param m is an arraylist storing matrix's elements.
	 * @param opr is an operator interface.
	 */
	@SuppressWarnings("unchecked")
	public Matrix( ArrayList<ArrayList<A>> m, MatOperator<? super A> opr ) {
		this.row = m.size();
		this.col = m.get(0).size();
		this.opr = opr;
		for (int i = 0; i < m.size(); i++) {
			ArrayList<A> tem = new ArrayList<>();
			for (int j = 0; j < m.get(0).size(); j++) {
				tem.add( (A) opr.simplify( m.get(i).get(j) ) );
			}
			mat.add( tem );
		}
	}
	
	/**
	 * Constructs a matrix by another matrix and an operator interface.
	 * @param m is another matrix.
	 * @param opr is an operator interface.
	 */
	public Matrix( Matrix<A> m, MatOperator<? super A> opr ) {
		this( m.mat, opr );
	}
	
	/**
	 * Returns the matrix element where the subscript is i,j.
	 * @param i is row subscript.
	 * @param j is col subscript.
	 * @return the matrix element where the subscript is i,j.
	 */
	public A element( int i, int j ) {
		return mat.get(i-1).get(j-1);
	}
	
	/**
	 * Calculate two matrixs' multiplication.
	 * @param m is another matrix.
	 * @return the multiplication of two matrixs.
	 * @throws NullOperateException when these two matrixs cannot multiply.
	 */
	@SuppressWarnings("unchecked")
	public Matrix<A> multiply( Matrix<A> m ) throws NullOperateException {
		if( this.col != m.row ) throw new NullOperateException();
		Matrix<A> r = new Matrix<>( this.opr );
		for( int i = 0; i < this.row; i++ ) {
			ArrayList<A> tem = new ArrayList<>();
			for( int j = 0; j < m.col; j++ ) {
				A a = (A) opr.multiply( mat.get(i).get(0), m.mat.get(0).get(j) );
				for( int k = 1; k < this.col; k++ ) {
					a = (A) opr.add( a, 
						(A) opr.multiply( mat.get(i).get(k), m.mat.get(k).get(j) ) ); 
				}
				tem.add( a );
			}
			r.mat.add( tem );
		}
		r.setMat();
		return r;
	}
	
	/**
	 * Calculate the product of n matrixs.
	 * @param a is the target element.
	 * @return the product of n matrixs.
	 * @throws NullOperateException if these matrixs cannot multiply.
	 */
	@SuppressWarnings({ "unchecked" })
	public Matrix<A> multiply( A a ) throws NullOperateException {
		Matrix<A> r = new Matrix<>( this.opr );
		for( int i = 1; i <= this.row; i++ ) {
			ArrayList<A> tem = new ArrayList<>();
			for( int j = 1; j <= this.col; j++ ) {
				tem.add( (A) opr.multiply( a, this.element( i, j ) ) );
			}
			r.mat.add( tem );
		}
		r.setMat();
		return r;
	}
	
	/**
	 * Calculate two matrixs' add.
	 * @param m is another matrix.
	 * @return the add of two matrixs.
	 * @throws NullOperateException when these two matrixs cannot add.
	 */
	@SuppressWarnings("unchecked")
	public Matrix<A> add( Matrix<A> m ) throws NullOperateException {
		if( this.row != m.row || this.col != m.col ) throw new NullOperateException();
		Matrix<A> r = new Matrix<>( this.opr );
		for( int i = 0; i < this.row; i++ ) {
			ArrayList<A> tem = new ArrayList<>();
			for( int j = 0; j < this.col; j++ ) {
				tem.add( (A) opr.add( this.mat.get(i).get(j), m.mat.get(i).get(j) ) );
			}
			r.mat.add( tem );
		}
		r.setMat();
		return r;
	}
	
	/**
	 * Calculate this matrix's pow.
	 * @param n is the exponent of the matrix.
	 * @return the <strong>n</strong> pows of this matrix.
	 * @throws NullOperateException when this matrix cannot do pow operator.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Matrix<A> pow( int n ) throws NullOperateException {
		if( n == 0 ) {
			Matrix<A> r = new Matrix( this.opr );
			for( int i = 1; i <= this.row; i++ ) {
				ArrayList<A> tem = new ArrayList<>();
				for( int j = 1; j <= this.col; j++ ) {
					if( i != j ) tem.add( (A) this.opr.zero() );
					else tem.add( (A) this.opr.unit() );
				}
				r.mat.add( tem );
			}
			r.setMat();
			return r;
		}
		if( n == 1 ) return this;
		else return this.multiply( this.pow( n-1 ) );
	}
	
	/**
	 * Calculate this matrix's inverse matrix.
	 * @return the matrix's inverse matrix.
	 * @throws NullOperateException when its determinant equal to zero element.
	 */
	@SuppressWarnings("unchecked")
	public Matrix<A> inverse() throws NullOperateException {
		if( this.determinant().equals( opr.zero() ) ) throw new NullOperateException();
		Matrix<A> m = new Matrix<>( this.opr );
		A d = this.determinant();
		for( int i = 1; i <= this.row; i++ ) {
			ArrayList<A> tem = new ArrayList<>();
			for( int j = 1; j <= this.col; j++ ) {
				tem.add( (A) opr.devide( this.adjugate().element(i, j), d ) );
			}
			m.mat.add( tem );
		}
		m.setMat();
		return m;
	}
	
	/**
	 * Calculate this matrix's adjugate matrix.
	 * @return the matrix's adjugate matrix.
	 * @throws NullOperateException when cofactor's determinant not exists.
	 */
	@SuppressWarnings("unchecked")
	public Matrix<A> adjugate() throws NullOperateException {
		Matrix<A> m = new Matrix<>( this.opr );
		for( int i = 1; i <= this.row; i++ ) {
			ArrayList<A> tem = new ArrayList<>();
			for( int j = 1; j <= this.col; j++ ) {
				tem.add( (A) opr.multiply
					( (A) opr.pow( (A) opr.negate( (A) opr.unit() ),
						j+i ), this.cofactor( j, i ).determinant() ));
			}
			m.mat.add( tem );
		}
		m.setMat();
		return m;
	}
	
	/**
	 * Calculate this matrix's determinant.
	 * @return the matrix's determinant.
	 * @throws NullOperateException when this matrix is not a square.
	 */
	@SuppressWarnings("unchecked")
	public A determinant() throws NullOperateException {
		A sum;
		if( this.row != this.col ) throw new NullOperateException();
		if( this.row == 1 ) return this.element( 1, 1 );
		else {
			sum = (A) opr.multiply( this.element( 1, 1 ),
					this.cofactor( 1, 1 ).determinant() );
			A k = (A) opr.unit();
			for(int i = 2; i <= this.row; i++){
				k = (A) opr.negate( k );
				sum = (A) opr.add
					( sum, (A) opr.multiply( k, (A) opr.multiply
					( this.element( 1, i ), this.cofactor( 1, i ).determinant() ) ) );
			}
		}
		return sum;
	}
	
	/**
	 * Calculate element_i,j's cofacter matrix.
	 * @param a is row subscript.
	 * @param b is col subscript.
	 * @return the element_i,j's cofacter matrix.
	 */
	public Matrix<A> cofactor( int a, int b ) {
		Matrix<A> m = new Matrix<>( this.opr );
		for( int i = 0; i < this.row; i++ ) {
			if( i == a-1 ) continue;
			ArrayList<A> tem = new ArrayList<>();
			for( int j = 0; j < this.col; j++ ) {
				if( j == b-1 ) continue;
				tem.add( this.mat.get(i).get(j) );
			}
			m.mat.add( tem );
		}
		m.setMat();
		return m;
	}
	
	/**
	 * Calculate this matrix's characteristic polynomial.
	 * @return this matrix's characteristic polynomial.
	 * @throws NullOperateException while the specific operators are not well difined.
	 */
	@SuppressWarnings("unchecked")
	public Polynomial<A> charPolynomial() throws NullOperateException {
		PolyOperator<A> popr = new PolyOperator<A>() {
			@Override
			public A multiply(A a, A b) {			
				return (A) Matrix.this.opr.multiply( a, b );
			}
			@Override
			public A add(A a, A b) {			
				return (A) Matrix.this.opr.add( a, b );
			}
			@Override
			public A subtract(A a, A b) {		
				return (A) Matrix.this.opr.add
						( a, (A) Matrix.this.opr.negate( b ) );
			}
			@Override
			public A devide(A a, A b) {			
				return (A) Matrix.this.opr.devide( a, b );
			}
			@Override
			public A negate(A a) {
				return (A) Matrix.this.opr.negate( a );
			}
			@Override
			public A construct(String s) {
				return null;
			}
			@Override
			public A zero() {
				return (A) Matrix.this.opr.zero();
			}
			@Override
			public A unit() {
				return (A) Matrix.this.opr.unit();
			}
		};
		@SuppressWarnings("rawtypes")
		Matrix<Polynomial<A>> matrix = new Matrix( Algebra.PolyMatOperator( popr ) );
		for( int i = 0; i < this.row; i++ ) {
			ArrayList<Polynomial<A>> tem = new ArrayList<>();
			for( int j = 0; j < this.col; j++ ) {
				Polynomial<A> p = new Polynomial<>( popr );
				p.setLetter( GreekAlphabet.LAMBDA.lowercase );
				Polynomial<A>.Monomial<A> m = p.new Monomial<>
					( popr.negate( this.element( i+1, j+1 ) ), 0 );
				p.addMonomial( m );
				if( i == j ) {
					Polynomial<A>.Monomial<A> m1
						= p.new Monomial<>( popr.unit(), 1 );
					p.addMonomial( m1 );
				}
				tem.add( p );
			}
			matrix.mat.add( tem );
		}
		matrix.setMat();
		return matrix.determinant();
	}
	
	/**
	 * Internal method to set matrix's row and col.
	 */
	protected void setMat() {
		this.row = mat.size();
		this.col = mat.get(0).size();
	}
	
	@Override
	public String toString() {
		if( mat.size() == 0 ) return "( null )";
		if( !mat.get(0).get(0).toString().contains("\n") ) {
		int length = 0;
		for( ArrayList<A> arrayList : mat ) {
			for( A a : arrayList ) {
				if( a.toString().length() > length ) length = a.toString().length();
			}
		}
		StringBuffer s = new StringBuffer();
		if( mat.size() == 1 ) {
			s.append("( ");
			for( int k = 0; k < mat.get(0).size(); k++ ) {
				int i = length - mat.get(0).get(k).toString().length();
				if( mat.get(0).get(k).equals( this.opr.zero() ) ) {
					for( int j = 0; j <= length; j++ ) {
						s.append( ' ' );
					}
				}else {
					s.append( mat.get(0).get(k) );
					for( int j = 0; j <= i; j++ ) {
						s.append( ' ' );
					}
				} 
			}
			s.append(")");
			return s.toString();
		}
		s.append("秀 ");
		for( int k = 0; k < mat.get(0).size(); k++ ) {
			int i = length - mat.get(0).get(k).toString().length();
			if( mat.get(0).get(k).equals( this.opr.zero() ) ) {
				for( int j = 0; j <= length; j++ ) {
					s.append( ' ' );
				}
			}else {
				s.append( mat.get(0).get(k) );
				for( int j = 0; j <= i; j++ ) {
					s.append( ' ' );
				}
			} 
		}
		s.append( "禿" + "\n" );
		for( int i = 1; i < mat.size()-1; i++ ){
			s.append("| ");
			for( int j = 0; j < mat.get(i).size(); j++ ){
				int c = length - mat.get(i).get(j).toString().length();
				if( mat.get(i).get(j).equals( this.opr.zero() ) ) {
					for( int d = 0; d <= length; d++ ) {
						s.append( ' ' );
					}
				}else {
					s.append( mat.get(i).get(j) );
					for( int d = 0; d <= c; d++ ) {
						s.append( ' ' );
					}	
				}
			}
			s.append( "|" + "\n" );
		}
		s.append("系 ");
		for( int k = 0; k < mat.get( mat.size()-1 ).size(); k++ ) {
			int i = length - mat.get( mat.size()-1 ).get(k).toString().length();
			if( mat.get( mat.size()-1 ).get(k).equals( this.opr.zero() ) ) {
				for( int j = 0; j <= length; j++ ) {
					s.append( ' ' );
				}
			}else {
				s.append( mat.get( mat.size()-1 ).get(k) );
				for( int j = 0; j <= i; j++ ) {
					s.append( ' ' );
				}
			}
		}
		s.append( "究" );
		return s.toString();
		}
		int length = 0;
		int height[] = new int[mat.size()];
		for( int i = 1; i <= mat.size(); i++ ) {
			for( int j = 1; j <= mat.get(0).size(); j++ ) {
				String[] s = this.element( i, j ).toString().split( "\n" );
				if( s[0].length() > length ) length = s[0].length();
				if( s.length > height[i-1] ) height[i-1] = s.length;	
			}
		}
		StringBuffer sb = new StringBuffer();
		for( int i = 1; i <= mat.size(); i++ ) {
			StringBuffer[] sbarr = new StringBuffer[height[i-1]];
			for( int l = 0; l < sbarr.length; l++ ) {
				sbarr[l] = new StringBuffer();
			}
			for( int j = 1; j <= mat.get(i-1).size(); j++ ) {
				String[] s = this.element( i, j ).toString().split( "\n" );
				if( j == 1 ) {
					for( int k = 0; k < height[i-1]; k++ ) {
						if( i == 1 && k == 0 ) sbarr[k].append( "秀 " );
						else if( i == mat.size() && k == height[i-1]-1 )
							sbarr[k].append( "系 " );
						else sbarr[k].append( "| " );
					}
				} 
				for( int k = 0; k < height[i-1]; k++ ) {
					sbarr[k].append( s[k] );
					int c = 0;
					if( k < s.length ) c = length-s[k].length();
					else c = length;
					if( j != mat.get(i-1).size() )
						for( int n = 0; n <= c; n++ ) {
							sbarr[k].append( " " );
						}
					else sbarr[k].append( " " );
				} 
				if( j == mat.get(i-1).size() ) {
					for( int k = 0; k < height[i-1]; k++ ) {
						if( i == 1 && k == 0 ) {
							sbarr[k].append( "禿" );
							sb.append( sbarr[k] + "\n" );
						} else if( i == mat.size() && k == height[i-1]-1 ) {
							sbarr[k].append( "究" );
							sb.append( sbarr[k] );
						} else {
							sbarr[k].append( "|" );
							sb.append( sbarr[k] + "\n" );
						}
					}
				}
			}
		}
		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override 
	public boolean equals( Object obj ) {
		if( !(obj instanceof Matrix) ) return false;
		Matrix<A> mat = ( Matrix ) obj;
		return this.mat.equals( mat.mat );
	}
	
	@Override
	public Matrix<A> valueOf(String s) {
		return null;
	}

	@Override
	public int size() {
		return mat.size();
	}

	@Override
	public boolean isEmpty() {
		return mat.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return mat.contains(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<A> iterator() {
		return (Iterator<A>) mat.iterator();
	}

	@Override
	public Object[] toArray() {
		return mat.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return mat.toArray(a);
	}

	@Override
	public boolean add(A e) {
		return false;
	}

	@Override
	public boolean remove(Object o) {
		return mat.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return mat.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends A> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return mat.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return mat.retainAll(c);
	}

	@Override
	public void clear() {
		mat.clear();
	}

}

