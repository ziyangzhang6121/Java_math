package javam.algebra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javam.exception.NullOperateException;

/**
 * @author Zhang Ziyang
 * @param <A>
 */
public class Polynomial<A> implements Serializable, Collection<A> {
	
	private static final long serialVersionUID = -9184913918553767905L;
		
	/**
	 * Point to the exponent of polynomial.
	 */
	public int exponent = 0;
	
	/**
	 * Point to the letter of polynomial.
	 */
	protected String letter;
	
	/**
	 * Store the quotient of devision operator.
	 */
	protected Polynomial<A> quotient;
	
	/**
	 * Point to this polynomial's operators.
	 */
	protected PolyOperator<? super A> opr;
	
	/**
	 * Store any monomial's of this polynomial.
	 */
	public LinkedList<Monomial<A>> poly = new LinkedList<>();
	
	/**
	 * Constructs a polynomial.
	 */
	public Polynomial() {}
	
	/**
	 * Constructs a polynomial only with its operator interface.
	 * @param opr is an operator interface.
	 */
	public Polynomial( PolyOperator<? super A> opr ) {
		this.opr = opr;
		this.letter = "x";
	}
	
	/**
	 * Constructs a polynomial with a string and number's operators.
	 * @param p is a string of polynamial's former whose letter must be {@code "x"}.
	 * @throws NullOperateException when add operator cannot operate.
	 */
	@SuppressWarnings("unchecked")
	public Polynomial( String p ) throws NullOperateException {
		this.opr = (PolyOperator<? super A>) Algebra.INT_POLY_OPERATOR;
		this.letter = "x";
		p = p.replaceAll( "-", "+-" ).replaceAll( " ", "" );
		String[] t = p.split( "\\+" );
		for( String m : t ) {
			if( m.length() == 0 ) continue;
			Monomial<A> mon = new Monomial<>( m );
			this.addMonomial( mon );
			if( mon.exponent > this.exponent ) this.exponent = mon.exponent;
		}
	}
	
	/**
	 * Constructs a polynomial with a string and number's operators, besides, set 
	 * this polynomial's letter.
	 * @param p is a string of polynamial's former.
	 * @param l is this polynomial's letter.
	 * @throws NullOperateException when add operator cannot operate.
	 */
	@SuppressWarnings("unchecked")
	public Polynomial( String p, String l ) throws NullOperateException {
		this.opr = (PolyOperator<? super A>) Algebra.INT_POLY_OPERATOR;
		this.letter = l;
		p = p.replaceAll( "-", "+-" ).replaceAll( " ", "" );
		String[] t = p.split( "\\+" );
		for( String m : t ) {
			if( m.length() == 0 ) continue;
			Monomial<A> mon = new Monomial<>( m );
			this.addMonomial( mon );
			if( mon.exponent > this.exponent ) this.exponent = mon.exponent;
		}
	}

	/**
	 * Constructs a polynomial with a collection and number's operators.
	 * @param c is a collection storing polynamial's coefficients and exponents.
	 * @throws NullOperateException when add operator cannot operate.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Polynomial( Collection<? extends A> c ) throws NullOperateException {
		this.opr = (PolyOperator<? super A>) Algebra.INT_POLY_OPERATOR;
		Iterator<? extends A> iterator = c.iterator();
		for( int i = 0; iterator.hasNext(); i++ ) {
			A a = iterator.next();
			if( a.equals( opr.zero() ) ) continue;
			Monomial<A> mon = new Monomial( a, i );
			this.addMonomial( mon );
			if( mon.exponent > this.exponent ) this.exponent = mon.exponent;
		}
		this.letter = "x";
	}
	
	/**
	 * Constructs a polynomial with an array and number's operators.
	 * @param c is an array storing polynamial's coefficients and exponents.
	 * @throws NullOperateException when add operator cannot operate.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Polynomial( A[] c ) throws NullOperateException {
		this.opr = (PolyOperator<? super A>) Algebra.INT_POLY_OPERATOR;
		for( int i = 0; i < c.length; i++ ) {
			if( c[i].equals( opr.zero() ) ) continue;
			Monomial<A> mon = new Monomial( c[i], i );
			this.addMonomial( mon );
			if( mon.exponent > this.exponent ) this.exponent = mon.exponent;
		}
		this.letter = "x";
	}
	
	/**
	 * Constructs a polynomial with a string and polynomial's operators.
	 * @param p is a string of polynamial's former whose letter must be {@code "x"}.
	 * @param opr is an operator interface.
	 * @throws NullOperateException when add operator cannot operate.
	 */
	public Polynomial( String p, PolyOperator<? super A> opr ) 
			throws NullOperateException {
		this.opr = opr;
		this.letter = "x";
		p = p.replaceAll( "-", "+-" ).replaceAll( " ", "" );
		String[] t = p.split( "\\+" );
		for( String m : t ) {
			if( m.length() == 0 ) continue;
			Monomial<A> mon = new Monomial<>( m );
			this.addMonomial( mon );
			if( mon.exponent > this.exponent ) this.exponent = mon.exponent;
		}
	}
	
	/**
	 * Constructs a polynomial with a collection and number's operators.
	 * @param c is a collection storing polynamial's coefficients and exponents.
	 * @param opr is an operator interface.
	 * @throws NullOperateException when add operator cannot operate.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Polynomial( Collection<? extends A> c, PolyOperator<? super A> opr ) 
			throws NullOperateException {
		this.opr = opr;
		Iterator<? extends A> iterator = c.iterator();
		for( int i = 0; iterator.hasNext(); i++ ) {
			A a = iterator.next();
			if( a.equals( opr.zero() ) ) continue;
			Monomial<A> mon = new Monomial( a, i );
			this.addMonomial( mon );
			if( mon.exponent > this.exponent ) this.exponent = mon.exponent;
		}
		this.letter = "x";
	}
	
	/**
	 * Constructs a polynomial with an array and number's operators.
	 * @param c is an array storing polynamial's coefficients and exponents.
	 * @param opr is an operator interface.
	 * @throws NullOperateException when add operator cannot operate.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Polynomial( A[] c, PolyOperator<? super A> opr ) throws NullOperateException {
		this.opr = opr;
		for( int i = 0; i < c.length; i++ ) {
			if( c[i].equals( opr.zero() ) ) continue;
			Monomial<A> mon = new Monomial( c[i], i );
			this.addMonomial( mon );
			if( mon.exponent > this.exponent ) this.exponent = mon.exponent;
		}
		this.letter = "x";
	}
	
	/**
	 * Set this polynamial's letter.
	 * @param s is the string of the target letter.
	 */
	public Polynomial<A> setLetter( String s ) {
		this.letter = s;
		return this;
	}
	
	/**
	 * Calculate two polynomials' add.
	 * @param p is another polynomial.
	 * @return the add of two polynomials.
	 * @throws NullOperateException when add operator cannot operate.
	 */
	public Polynomial<A> add( Polynomial<A> p ) throws NullOperateException {
		Polynomial<A> r = new Polynomial<>( this.opr );
		for( Monomial<A> m1 : poly ) {
			r.addMonomial( m1 );
		}
		p.setLetter( this.letter );
		for( Monomial<A> m2 : p.poly ) {
			r.addMonomial( m2 );
		}
		for( Monomial<A> m : r.poly ) {
			if( m.exponent > r.exponent ) r.exponent = m.exponent;
		}
		return r;
	}
	
	/**
	 * Calculate two polynomials' subtraction.
	 * @param p is another polynomial.
	 * @return the subtraction of two polynomials.
	 * @throws NullOperateException when subtraction operator cannot operate.
	 */
	@SuppressWarnings("unchecked")
	public Polynomial<A> subtract( Polynomial<A> p ) throws NullOperateException {
		Polynomial<A> r = new Polynomial<>( this.opr );
		for( Monomial<A> m1 : poly ) {
			r.addMonomial( m1 );
		}
		p.setLetter( this.letter );
		for( Monomial<A> m2 : p.poly ) {
			m2.coefficient = (A) opr.negate( m2.coefficient );
			r.addMonomial( m2 );
		}
		for( Monomial<A> m : r.poly ) {
			if( m.exponent > r.exponent ) r.exponent = m.exponent;
		}
		return r;
	}
	
	/**
	 * Calculate two polynomials' multiplication.
	 * @param p is another polynomial.
	 * @return the multiplication of two polynomials.
	 * @throws NullOperateException when multiplication operator cannot operate.
	 */
	public Polynomial<A> multiply( Polynomial<A> p ) throws NullOperateException {
		Polynomial<A> r = new Polynomial<>( this.opr );
		for (Monomial<A> m1 : poly) {
			p.setLetter( this.letter );
			for (Monomial<A> m2 : p.poly) {
				r.addMonomial( m1.multiply( m2 ) );
			}
		}
		for( Monomial<A> m : r.poly ) {
			if( m.exponent > r.exponent ) r.exponent = m.exponent;
		}
		return r;
	}
	
	/**
	 * Calculate two polynomials' devision.
	 * @param p is another polynomial.
	 * @return an array of length two of the devision of two polynomials, the frist 
	 * polynomial is the quotient of devision, the second polynomial is the remainder.
	 * @throws NullOperateException when devision operator cannot operate.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Polynomial<A>[] devide( Polynomial<A> p ) throws NullOperateException {
		Polynomial<A> [] res = new Polynomial[2];
		this.quotient = new Polynomial( this.opr );
		Polynomial<A> r = devide( this, p );
		res[0] = this.quotient;
		res[1] = r;
		for( Monomial<A> m : r.poly ) {
			if( m.exponent > r.exponent ) r.exponent = m.exponent;
		}
		for( Monomial<A> m : this.quotient.poly ) {
			if( m.exponent > this.quotient.exponent ) this.quotient.exponent = m.exponent;
		}
		return res;
	}
	
	/**
	 * Calculate two polynomials' devision.
	 * @param p is another polynomial.
	 * @return the remainder of devision of two polynomials.
	 * @throws NullOperateException when devision operator cannot operate.
	 */
	public Polynomial<A> devideR( Polynomial<A> p ) throws NullOperateException {
		return this.devide( p )[1];
	}
	
	/**
	 * Calculate two polynomials' devision.
	 * @param p is another polynomial.
	 * @return the quotient of devision of two polynomials.
	 * @throws NullOperateException when devision operator cannot operate.
	 */
	public Polynomial<A> devideQ( Polynomial<A> p ) throws NullOperateException {
		return this.devide( p )[0];
	}
	
	/**
	 * Calculate this polynomial's pow.
	 * @param n is the exponent of the polynomial.
	 * @return the <strong>n</strong> pows of this polynomial.
	 * @throws NullOperateException when this polynomial cannot do pow operator.
	 */
	public Polynomial<A> pow( int n ) throws NullOperateException {
		if( n == 1 ) return this;
		else return this.multiply( this.pow( n-1 ) );
	}
	
	/**
	 * Calculate two polynomials' great common devisor.
	 * @param p is another polynomial.
	 * @return the great common devisor of devision of two polynomials.
	 * @throws NullOperateException when devision operator cannot operate.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Polynomial<A> gcdOf( Polynomial<A> p ) throws NullOperateException {
		Polynomial<A> gcd = new Polynomial<>( this.opr );
		Polynomial<A> tem = gcd( this, p );
		Collections.sort( tem.poly );
		A a = tem.poly.get( tem.poly.size()-1 ).coefficient;
		for( Monomial<A> m : tem.poly ) {
			gcd.addMonomial( new Monomial
					( this.opr.devide( m.coefficient, a ), m.exponent ) );
		}
		for( Monomial<A> m : gcd.poly ) {
			if( m.exponent > gcd.exponent ) gcd.exponent = m.exponent;
		}
		return gcd;
	}
	
	/**
	 * Calculate two polynomials' least common multiple.
	 * @param p is another polynomial.
	 * @return the great common devisor of devision of two polynomials.
	 * @throws NullOperateException when devision operator cannot operate.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Polynomial<A> lcmOf( Polynomial<A> p ) throws NullOperateException {
		Polynomial<A> lcm = new Polynomial<>( this.opr );
		Polynomial<A> tem = this.multiply( p ).devideQ( gcd( this, p ) );
		Collections.sort( tem.poly );
		A a = tem.poly.get( tem.poly.size()-1 ).coefficient;
		for( Monomial<A> m : tem.poly ) {
			lcm.addMonomial( new Monomial
					( this.opr.devide( m.coefficient, a ), m.exponent ) );
		}
		return lcm;
	}
	
	/**
	 * Calculate the value of this polynomial of value <strong>a</strong>.
	 * @param a is the target value.
	 * @return the value of this polynomial of value <strong>a</strong>.
	 * @throws NullOperateException when multiple operator cannot operate.
	 */
	@SuppressWarnings("unchecked")
	public A valueOf( A a ) throws NullOperateException {
		A r = (A) this.opr.zero();
		for (Monomial<A> m : poly) {
			r = (A) this.opr.add( r, (A) this.opr.
					multiply( m.coefficient, pow( a, m.exponent ) ) );
		}
		return r;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Matrix<A> valueOf( Matrix<A> m ) throws NullOperateException {
		MatOperator<A> opr = new MatOperator<A>() {
			@Override
			public A multiply(A a, A b) {
				return (A) Polynomial.this.opr.multiply( a, b );
			}
			@Override
			public A add(A a, A b) {
				return (A) Polynomial.this.opr.add( a, b );
			}
			@Override
			public A devide(A a, A b) {
				return (A) Polynomial.this.opr.devide( a, b );
			}
			@Override
			public A pow(A a, int n) {
				A a2 = null;
				try {
					a2 = Polynomial.this.pow( a, n );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return a2;
			}
			@Override
			public A negate(A a) {
				return (A) Polynomial.this.opr.negate( a );
			}
			@Override
			public A construct(String s) {
				return (A) Polynomial.this.opr.construct(s);
			}
			@Override
			public A zero() {
				return (A) Polynomial.this.opr.zero();
			}
			@Override
			public A unit() {
				return (A) Polynomial.this.opr.unit();
			}
			@Override
			public A simplify(A a) {
				return a;
			}
		};
		Matrix<A> r = new Matrix( opr );
		for( int i = 1; i <= m.row; i++ ) {
			ArrayList<A> tem = new ArrayList<>();
			for( int j = 1; j <= m.col; j++ ) {
				tem.add( (A) opr.zero() );
			}
			r.mat.add( tem );
		}
		r.setMat();
		for( Monomial<A> a : poly ) {
			r = r.add( m.pow( a.exponent ).multiply( a.coefficient ) );
		}
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public A coefficientAt( int e ) {
		for ( Monomial<A> monomial : poly ) {
			if( monomial.exponent == e ) return monomial.coefficient;
		}
		return (A) this.opr.zero();
	}
	
	public LinkedList<Polynomial<A>> polyOf( Polynomial<A> p ) throws NullOperateException {
		LinkedList<Polynomial<A>> lrr = new LinkedList<>();
		Polynomial<A> tem = this;
		Polynomial<A> temr;
		while( tem.exponent >= p.exponent ) {
			temr = tem.devideR( p );
			lrr.add( temr );
			tem = tem.devideQ( p );
		}
		lrr.add( tem );
		return lrr;
	}
	
	/**
	 * Calculate the eliminant's matrix of two polynomial.
	 * @param p is another polynomial.
	 * @return the eliminant's matrix.
	 */
	@SuppressWarnings("unchecked")
	public Matrix<A> eliminantOf( Polynomial<A> p ) {
		ArrayList<A> a = new ArrayList<>();
		Collections.sort( this.poly );
		for( int i = 0, j = 0; i <= this.exponent; i++ ) {
			if( this.exponent-i == this.poly.get( this.poly.size()-j-1 ).exponent ) {
				a.add( this.poly.get( this.poly.size()-j-1 ).coefficient );
				j++;
			}else a.add( (A) this.opr.zero() );
		}
		ArrayList<A> b = new ArrayList<>();
		Collections.sort( p.poly );
		for( int i = 0, j = 0; i <= p.exponent; i++ ) {
			if( p.exponent-i == p.poly.get( p.poly.size()-j-1 ).exponent ) {
				b.add( p.poly.get( p.poly.size()-j-1 ).coefficient );
				j++;
			}else b.add( (A) p.opr.zero() );
		}
		MatOperator<A> mopr = new MatOperator<A>() {
			@Override
			public A multiply(A a, A b) {
				return (A) Polynomial.this.opr.multiply( a, b );
			}
			@Override
			public A add(A a, A b) {
				return (A) Polynomial.this.opr.add( a, b );
			}
			@Override
			public A devide(A a, A b) {
				return (A) Polynomial.this.opr.devide( a, b );
			}
			@Override
			public A pow(A a, int n) {
				A r = null;
				try {
					r = Polynomial.this.pow( a, n );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public A negate(A a) {
				return (A) Polynomial.this.opr.negate( a );
			}
			@Override
			public A zero() {
				return (A) Polynomial.this.opr.zero();
			}
			@Override
			public A unit() {
				return (A) Polynomial.this.opr.unit();
			}
			@Override
			public A simplify(A a) {
				return a;
			}
			@Override
			public A construct(String s) {
				return (A) Polynomial.this.opr.construct( s );
			}
		};
		Matrix<A> mat = new Matrix<>( mopr );
		for( int i = 0; i < b.size()-1; i++ ) {
			ArrayList<A> tem = new ArrayList<>();
			for( int j = 0; j < i; j++ ) {
				tem.add( (A) Polynomial.this.opr.zero() );
			}
			for( A a_ : a ) {
				tem.add( a_ );
			}
			for( int j = b.size()-i-2; j > 0; j-- ) {
				tem.add( (A) Polynomial.this.opr.zero() );
			}
			mat.mat.add( tem );
		}
		for( int i = 0; i < a.size()-1; i++ ) {
			ArrayList<A> tem = new ArrayList<>();
			for( int j = 0; j < i; j++ ) {
				tem.add( (A) Polynomial.this.opr.zero() );
			}
			for( A b_ : b ) {
				tem.add( b_ );
			}
			for( int j = a.size()-i-2; j > 0; j-- ) {
				tem.add( (A) Polynomial.this.opr.zero() );
			}
			mat.mat.add( tem );
		}
		mat.setMat();
		return mat;
	}
	
	/**
	 * Calculate the derivative of this polynomial.
	 * @return the derivative of this polynomial.
	 * @throws NullOperateException if there are some problems with add operation.
	 */
	public Polynomial<A> derivative() throws NullOperateException {
		Polynomial<A> r = new Polynomial<>( this.opr );
		for( Monomial<A> mon : this.poly ) {
			if( mon.exponent == 0 ) continue;
			r.addMonomial( new Monomial<A>( multiply
					( mon.coefficient, mon.exponent ), mon.exponent - 1 ) );
		}
		for( Monomial<A> m : r.poly ) {
			if( m.exponent > r.exponent ) r.exponent = m.exponent;
		}
		return r;
	}
	
	/**
	 * Judge whether this polynomial has multiple roots.
	 * @return {@code true} if this polynomial has multiple roots, {@code false} otherwise.
	 * @throws NullOperateException when add operator cannot operate.
	 */
	@SuppressWarnings("unchecked")
	public boolean hasMultipleRoot() throws NullOperateException {
		if( this.poly.size() == 0 ) return false;
		Polynomial<A> unit = new Polynomial<A>( this.opr );
		unit.addMonomial( new Monomial<A>( (A) this.opr.unit(), 0 ) );
		if( this.gcdOf( this.derivative() ).equals( unit ) ) return false;
		return true;
	}
	
	public static Mapping<Polynomial<Integer>,Integer> primeFieldFactors
				( Polynomial<Integer> p, int prime ) throws NullOperateException {
		Mapping<Polynomial<Integer>,Integer> res = new Mapping<>();
		Polynomial<Integer> f = p;
		Polynomial<Integer> h = new Polynomial<Integer>( "x", f.opr );
		Polynomial<Integer> g = null;
		while( !f.equals( new Polynomial<>( "1", f.opr ) ) ) {
			h = h.pow( prime ).devideR( p );
			g = h.subtract( new Polynomial<Integer>( "x", f.opr ) ).gcdOf( f );
			ArrayList<Polynomial<Integer>> tem = new ArrayList<>();
			for( int i = 1; i < g.exponent; i++ ) {
				if( g.exponent % i != 0 ) continue;
				primeFieldFactor2( tem, g, i, prime );
			}
			if( tem.size() == 0 ) tem.add( g );
			for( Polynomial<Integer> poly : tem ) {
				if( poly.equals( new Polynomial<>( "1", f.opr ) ) ) continue;
				int j = 0;
				while( f.devideR( poly ).poly.size() == 0 ) {
					f = f.devideQ( poly );
					j++;
				}
				res.put( poly, j );
			}
		}
		return res;
	}
	
	public static String primeFieldFactor( Polynomial<Integer> p, int prime ) 
			throws NullOperateException {
		Mapping<Polynomial<Integer>,Integer> res = primeFieldFactors( p, prime );
		StringBuilder sb = new StringBuilder();
		for( int i = 0; i < res.size(); i++ ) {
			sb.append( "[( " + res.get(i) + " )" );
			int n = res.get( res.get(i) );
			if( n != 1 ) sb.append( "^" + n + "]" );
			else sb.append( "]" );
		}
		return sb.toString();
	}
	
	private static Polynomial<Integer> primeFieldFactor1
				( Polynomial<Integer> f, int d, int prime ) throws NullOperateException {
		ArrayList<Integer> i = new ArrayList<>();
		for( int j = 0; j < f.exponent; j++ ) i.add( (int)(Math.random() * prime) );
		Polynomial<Integer> a = new Polynomial<>( i, f.opr );
		if( a.exponent == 0 ) return null;
		a = new Polynomial<>( "x", f.opr );
		Polynomial<Integer> g1 = f.gcdOf(a);
		if( !g1.equals( new Polynomial<>( "1", f.opr ) ) && !g1.equals( f ) ) return g1;
		Polynomial<Integer> b = a.pow( ((int)Math.pow( prime, d )-1)/2 ).devideR( f );
		Polynomial<Integer> g2 = b.subtract( new Polynomial<>( "1", f.opr ) ).gcdOf( f );
		if( !g2.equals( new Polynomial<>( "1", f.opr ) ) && !g2.equals( f ) ) return g2;
		return null;
	}
	
	private static void primeFieldFactor2
			( ArrayList<Polynomial<Integer>> res, Polynomial<Integer> f, int d, int prime ) 
						throws NullOperateException {
		if( f.exponent == d ) res.add( f );
		else {
			Polynomial<Integer> g = primeFieldFactor1( f, d, prime );
			if( g == null ) return;
			primeFieldFactor2( res, g, d, prime );
			primeFieldFactor2( res, f.devideQ( g ), d, prime );
		}
	}
	
	/**
	 * Internal method to calculate the product of n a.
	 * @param a is the target element.
	 * @param n is the number of product.
	 * @return the product of n a.
	 */
	@SuppressWarnings("unchecked")
	private A multiply( A a, int n ) {
		if( n == 0 ) return (A) this.opr.zero();
		if( n == 1 ) return a;
		return (A) this.opr.add( a, multiply( a, n-1 ) );
	}
	
	/**
	 * Internal method to calculate two polynomials' devision.
	 * @param r is the first polynomial.
	 * @param p is the second polynomial.
	 * @return two polynomials' devision.
	 * @throws NullOperateException when devision operator cannot operate.
	 */
	private Polynomial<A> devide( Polynomial<A> r, Polynomial<A> p ) 
			throws NullOperateException {
		if( r.exponent < p.exponent || r.poly.size() == 0 ) return r;
		else if ( p.poly.size() == 0 ) throw new ArithmeticException( "\\ by zero" );
		else {
			Collections.sort( r.poly );
			Collections.sort( p.poly );
			for( int i = 0; i < r.poly.size(); i++ ) {
				if( r.poly.get(i).coefficient.equals( r.opr.zero() ) )
					r.poly.remove(i);
			}
			for( int i = 0; i < p.poly.size(); i++ ) {
				if( p.poly.get(i).coefficient.equals( p.opr.zero() ) )
					p.poly.remove(i);
			}
			Monomial<A> temM = 
				r.poly.get( r.poly.size()-1 ).devide( p.poly.get( p.poly.size()-1 ) );
			this.quotient.addMonomial( temM );
			if( temM.coefficient.equals( this.opr.zero() ) && temM.exponent != 0 )
				throw new ArithmeticException( "Accuracy overflow!" );
			Polynomial<A> temP = new Polynomial<>( this.opr );
			for( Monomial<A> m : p.poly ) {
				temP.addMonomial( temM.multiply( m ) );
			}
			return devide( r.subtract( temP ), p );
		}
	}
	
	/**
	 * Internal method to calculate an element's pow.
	 * @param a is the target element.
	 * @param n is the exponent of the pow.
	 * @return the <strong>n</strong> pows of this element.
	 * @throws NullOperateException when multiply operator cannot operate.
	 */
	@SuppressWarnings("unchecked")
	private A pow( A a, int n ) throws NullOperateException {
		if( n < 0 ) throw new NullOperateException();
		if( n == 0 ) return (A) this.opr.unit();
		if( n == 1 ) return a;
		return (A) this.opr.multiply( a, pow( a, n-1 ) );
	}

	/**
	 * Internal method to calcular two polynomials' great common devisor.
	 * @param a is the first polynomial.
	 * @param b is the second polynomial.
	 * @return the great common devisor of devision of two polynomials.
	 * @throws NullOperateException when devision operator cannot operate.
	 */
	private Polynomial<A> gcd( Polynomial<A> a, Polynomial<A> b ) 
			throws NullOperateException {
		while( true ) {
			if( ( a = a.devideR( b ) ).poly.size() == 0 ) return b;
			if( ( b = b.devideR( a ) ).poly.size() == 0 ) return a;
		}
	}
	
	/**
	 * Internal method to add monomials into this polynomial.
	 * @param a is the target monomial.
	 * @return {@code true} if the monomial is added, {@code false} otherwise.
	 * @throws NullOperateException when add operator cannot operate.
	 */
	protected boolean addMonomial( Monomial<A> a ) throws NullOperateException {
		for( Monomial<A> mon : poly ) {
			if( a.exponent == mon.exponent ) {
				poly.remove( mon );
				Monomial<A> tem = mon.add( a );
				if( !tem.coefficient.equals( opr.zero() ) ) {
					poly.add( tem );
				}
				return true;
			}
		}
		return poly.add( a );
	}
	
	@Override
	public String toString() {
		Collections.sort( poly );
		StringBuffer s = new StringBuffer();
		if( this.poly.size() == 0 ) return this.opr.zero() + "";
		boolean b = true;
		for( Monomial<A> mon : poly ) {
			if( mon.coefficient.toString().contains( "\n" ) ) b = false;
		}
		if( b && !letter.toString().contains( "\n" ) ) {
			for( Monomial<A> mon : poly ) {
				s.append( mon.toString() + " + " );
			}
			s.deleteCharAt( s.length()-1 ).
				deleteCharAt( s.length()-1 ).deleteCharAt( s.length()-1 );
			return s.toString().replaceAll( "\\+ -", "-" );
		} else {
			String[] sl = letter.toString().split( "\n" );
			int lht = sl.length;
			int llg = 0;
			for( int i = 0; i < sl.length; i++ ) {
				if( sl[i].length() > llg ) llg = sl[i].length(); 
			}
			int cht = 0;
			int[] clg = new int[this.poly.size()];
			for( int i = 0; i < this.poly.size(); i++ ) {
				String[] sc = this.poly.get(i).coefficient.toString().split( "\n" );
				if( sc.length > cht ) cht = sc.length;
				for( int j = 0; j < sc.length; j++ ) {
					if( this.poly.get(i).coefficient.equals( this.opr.unit() ) )
						clg[i] = 0;
					else if( sc[j].length() > clg[i] ) clg[i] = sc[j].length(); 
				}
			}
			StringBuffer sb = new StringBuffer();
			int height = Math.max( lht, cht );
			StringBuffer[] sbarr = new StringBuffer[height];
			for( int i = 0; i < sbarr.length; i++ ) {
				sbarr[i] = new StringBuffer();
			}
			for( int i = 0; i < this.poly.size(); i++ ) {
				if( this.poly.get(i).coefficient.equals( this.opr.zero() ) ) continue;
				int elg = String.valueOf( this.poly.get(i).exponent ).length();
				String[] sc = this.poly.get(i).coefficient.toString().split( "\n" );
				for( int j = 0; j < height; j++ ) {
					if( j < (height-cht)/2 || j >= cht+(height-cht)/2 ) {
						for( int k = 0; k < clg[i]; k++ ) sbarr[j].append( " " );
					} else if( !this.poly.get(i).coefficient.equals( this.opr.unit() ) )
						sbarr[j].append( sc[j-(height-cht)/2] );
					if( j < (height-lht)/2 || j >= lht+(height-lht)/2 ) {
						for( int k = 0; k < llg; k++ ) sbarr[j].append( " " );
					} else sbarr[j].append( sl[j-(height-lht)/2] ); 
					if( lht == 1 && this.poly.get(i).exponent != 1 ) {
						if( j == (height-lht)/2 ) sbarr[j].append( "^" );
						else sbarr[j].append( " " );
					}
					if( this.poly.get(i).exponent == 1 ) sbarr[j].append( " " );
					else if( j == (height-lht)/2 ) 
						sbarr[j].append( this.poly.get(i).exponent + " " );
					else for( int k = 0; k <= elg; k++ ) sbarr[j].append( " " );
					if( j == (height-1)/2 && i != this.poly.size()-1 )
						sbarr[j].append( "+ " );
					else if( i != this.poly.size()-1 ) sbarr[j].append( "  " );
				}
			}
			for( int i = 0; i < sbarr.length; i++ ) {
				sb.append( sbarr[i].deleteCharAt( sbarr[i].length()-1 ) );
				if( i != sbarr.length-1 ) sb.append( "\n" );
			}
			return sb.toString().replaceAll( "\\+ -", " - " );
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean equals( Object obj ) {
		if( !(obj instanceof Polynomial) ) return false;
		Polynomial<A> mon = ( Polynomial ) obj;
		if( this.poly.size() == 0 && mon.poly.size() == 0 ) return true;
		return this.poly.containsAll( mon.poly ) && mon.poly.containsAll( this.poly );
	}
	
	/**
	 * Internal class to define monomial of polynomial.
	 * @author Group
	 * @param <A>
	 */
	@SuppressWarnings("hiding")
	protected class Monomial<A> implements Comparable<Monomial<A>> {
		
		public int exponent;
		
		public A coefficient;
		
		protected PolyOperator<? super A> opr;
		
		@SuppressWarnings("unchecked")
		public Monomial( A cof, int exp ) {
			this.exponent = exp;
			this.coefficient = cof;
			this.opr = (PolyOperator<? super A>) Polynomial.this.opr;
		}
		
		@SuppressWarnings("unchecked")
		public Monomial( String s ) {
			if( !s.contains( letter ) ) {
				this.coefficient = (A) Polynomial.this.opr.construct( s );
				this.exponent = 0;
			} else {
				s = s.replace( "^", "" );
				if( s.indexOf( letter ) == 1 && s.charAt(0) == '-' )
					s = new StringBuilder(s).deleteCharAt(0).insert
					( 0, "-"+Polynomial.this.opr.unit().toString() ).toString();
				if( s.indexOf( letter ) == 0 )
					s = new StringBuffer( s ).insert
					( 0, Polynomial.this.opr.unit().toString() ).toString();
				String[] t = s.split( letter );
				this.coefficient = (A) Polynomial.this.opr.construct( t[0] );
				if( t.length == 1 ) this.exponent = 1;
				else this.exponent = Integer.parseInt( t[1] );
			}
			this.opr = (PolyOperator<? super A>) Polynomial.this.opr;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		protected Monomial<A> multiply( Monomial<A> a ) {
			return new Monomial( opr.multiply
					( this.coefficient, a.coefficient ), this.exponent + a.exponent );
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		protected Monomial<A> add( Monomial<A> a ) throws NullOperateException {
			if( this.exponent != a.exponent ) throw new NullOperateException();
			return new Monomial( opr.add
					( this.coefficient, a.coefficient ), this.exponent );
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		protected Monomial<A> devide( Monomial<A> a ) throws NullOperateException {
			if( this.exponent < a.exponent ) throw new NullOperateException();
			return new Monomial( opr.devide
					( this.coefficient, a.coefficient ), this.exponent - a.exponent );
		}
		
		@Override
		public String toString() {
			if( this.exponent == 0 ) return this.coefficient + "";
			if( this.exponent == 1 ) {
				if( this.coefficient.equals( Polynomial.this.opr.unit() ) ) return letter;
				return this.coefficient + letter;
			}
			if( this.coefficient.equals( Polynomial.this.opr.unit() ) )
				return letter + "^" + this.exponent;
			return this.coefficient + letter + "^" + this.exponent;
		}

		@Override
		public int compareTo( Monomial<A> o ) {
			if( this.exponent < o.exponent ) return -1;
			if( this.exponent > o.exponent ) return 1;
			return 0;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public boolean equals( Object obj ) {
			if( !(obj instanceof Monomial) ) return false;
			Monomial<A> mon = ( Monomial ) obj;
			return this.coefficient.equals( mon.coefficient ) 
					&& this.exponent == mon.exponent;
		}
		
	}

	@Override
	public int size() {
		return poly.size();
	}

	@Override
	public boolean isEmpty() {
		return poly.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return poly.contains(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<A> iterator() {
		return (Iterator<A>) poly.iterator();
	}

	@Override
	public Object[] toArray() {
		return poly.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return poly.toArray(a);
	}

	@Override
	public boolean add(A e) {
		return false;
	}

	@Override
	public boolean remove(Object o) {
		return poly.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return poly.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends A> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return poly.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return poly.retainAll(c);
	}

	@Override
	public void clear() {
		poly.clear();
	}
	
}
