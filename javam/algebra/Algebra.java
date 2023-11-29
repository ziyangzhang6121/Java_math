package javam.algebra;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import javam.exception.NullAbelException;
import javam.exception.NullAssociateException;
import javam.exception.NullClosedException;
import javam.exception.NullInverseException;
import javam.exception.NullOperateException;
import javam.exception.NullUnitException;
import javam.exception.NullZeroException;
import javam.exception.ZeroFactorException;
import javam.number.Fraction;

public class Algebra {

	private Algebra() {}
	
	public static final Group<Object> group = group();
	
	public static final Ring<Object> ring = ring();
	
	public static final Matrix<Object> matrix = matrix();
	
	public static final Polynomial<Object> polynomial = polynomial();

	public static final Ring<Object>.Domain<Object> domain = domain();
	
	public static final Ring<Object>.Skew<Object> skew = skew();
	
	public final static PolyOperator<Fraction> FRAC_POLY_OPERATOR = 
			new PolyOperator<Fraction>() {
		@Override
		public Fraction multiply(Fraction a, Fraction b) {
			return a.multiply( b );
		}
		@Override
		public Fraction add(Fraction a, Fraction b) {
			return a.add( b );
		}
		@Override
		public Fraction subtract(Fraction a, Fraction b) {
			return a.add( b.negate() );
		}
		@Override
		public Fraction devide(Fraction a, Fraction b) {
			return a.devide( b );
		}
		@Override
		public Fraction negate(Fraction a) {
			return a.negate();
		}
		@Override
		public Fraction construct(String s) {
			return new Fraction( s );
		}
		@Override
		public Fraction zero() {
			return Fraction.ZERO;
		}
		@Override
		public Fraction unit() {
			return Fraction.ONE;
		}
		@Override
		public String toString() {
			return "FRAC_POLY_OPERATOR";
		}
	};
	
	public final static MatOperator<Fraction> FRAC_MAT_OPERATOR = 
			new MatOperator<Fraction>() {
		@Override
		public Fraction multiply(Fraction a, Fraction b) {
			return a.multiply( b );
		}
		@Override
		public Fraction add(Fraction a, Fraction b) {
			return a.add( b );
		}
		@Override
		public Fraction devide(Fraction a, Fraction b) {
			return a.devide( b );
		}
		@Override
		public Fraction pow(Fraction a, int n) {
			return a.pow( n );
		}
		@Override
		public Fraction negate(Fraction a) {
			return a.negate();
		}
		@Override
		public Fraction zero() {
			return Fraction.ZERO;
		}
		@Override
		public Fraction unit() {
			return Fraction.ONE;
		}
		@Override
		public Fraction simplify(Fraction a) {
			return a;
		}
		@Override
		public Fraction construct(String s) {
			return new Fraction( s );
		}
		@Override
		public String toString() {
			return "FRAC_MAT_OPERATOR";
		}
	};
	
	public final static PolyOperator<Integer> MOD_POLY_OPERATOR( int prime ) {
		return new PolyOperator<Integer>(){
			@Override
			public Integer multiply(Integer a, Integer b) {
				return ( a * b ) % prime;
			}
			@Override
			public Integer add(Integer a, Integer b) {
				return ( a + b ) % prime;
			}
			@Override
			public Integer subtract(Integer a, Integer b) {
				return ( a - b ) % prime;
			}
			@Override
			public Integer devide(Integer a, Integer b) {
				if( b % prime == 0 ) return a/b;
				else {
					int i = 1;
					for( ; i < prime ; i++ ) 
						if( (b * i) % prime == 1 ) break;
					return ( a * i ) % prime;
				}
			}
			@Override
			public Integer negate(Integer a) {
				return (prime-a) % prime;
			}
			@Override
			public Integer construct(String s) {
				int a = Integer.parseInt(s);
				return (prime+a) % prime;
			}
			@Override
			public Integer zero() {
				return 0;
			}
			@Override
			public Integer unit() {
				return 1;
			}
			@Override
			public String toString() {
				return "MOD_POLY_OPERATOR(" + prime + ")";
			}
		};
	}
	
	public final static MatOperator<Integer> MOD_MAT_OPERATOR( int prime ) {
		return new MatOperator<Integer>() {
			@Override
			public Integer zero() {
				return 0;
			}
			@Override
			public Integer unit() {
				return 1;
			}
			@Override
			public Integer simplify(Integer a) {
				return a % prime;
			}
			@Override
			public Integer pow(Integer a, int n) {
				return ( (int)Math.pow( a, n ) ) % prime;
			}
			@Override
			public Integer negate(Integer a) {
				return ( -a ) % prime;
			}
			@Override
			public Integer multiply(Integer a, Integer b) {
				return ( a * b ) % prime;
			}
			@Override
			public Integer devide(Integer a, Integer b) {
				if( b % prime == 0 ) return a/b;
				else {
					int i = 0;
					for( ; i < prime ; i++ ) 
						if( (b * i) % prime == 1 ) break;
					return ( a * i ) % prime;
				}
			}
			@Override
			public Integer construct(String s) {
				return Integer.parseInt(s) % prime;
			}
			@Override
			public Integer add(Integer a, Integer b) {
				return ( a + b ) % prime;
			}
			@Override
			public String toString() {
				return "MOD_MAT_OPERATOR(" + prime + ")";
			}
		};
	}
	
	public final static PolyOperator<Integer> INT_POLY_OPERATOR = 
			new PolyOperator<Integer>() {
		@Override
		public Integer multiply(Integer a, Integer b) {
			return a * b;
		}
		@Override
		public Integer add(Integer a, Integer b) {
			return a + b;
		}
		@Override
		public Integer subtract(Integer a, Integer b) {
			return a - b;
		}
		@Override
		public Integer devide(Integer a, Integer b) {
			return a / b;
		}
		@Override
		public Integer negate(Integer a) {
			return -a;
		}
		@Override
		public Integer construct(String s) {
			return Integer.parseInt( s );
		}
		@Override
		public Integer zero() {
			return 0;
		}
		@Override
		public Integer unit() {
			return 1;
		}
		@Override
		public String toString() {
			return "INT_POLY_OPERATOR";
		}
	};
	
	public final static MatOperator<Integer> INT_MAT_OPERATOR = 
			new MatOperator<Integer>() {
		@Override
		public Integer multiply(Integer a, Integer b) {
			return a * b;
		}
		@Override
		public Integer add(Integer a, Integer b) {
			return a + b;
		}
		@Override
		public Integer devide(Integer a, Integer b) {
			return a / b;
		}
		@Override
		public Integer pow(Integer a, int n) {
			return (int) Math.pow( a, n );
		}
		@Override
		public Integer negate(Integer a) {
			return -a;
		}
		@Override
		public Integer zero() {
			return 0;
		}
		@Override
		public Integer unit() {
			return 1;
		}
		@Override
		public Integer simplify(Integer a) {
			return a;
		}
		@Override
		public Integer construct(String s) {
			return Integer.parseInt( s );
		}
		@Override
		public String toString() {
			return "INT_MAT_OPERATOR";
		}
	};
	
	public final static PolyOperator<Double> DOUBLE_POLY_OPERATOR = new PolyOperator<Double>() {
		
		@Override
		public Double zero() {
			// TODO Auto-generated method stub
			return 0.0;
		}
		
		@Override
		public Double unit() {
			// TODO Auto-generated method stub
			return 1.0;
		}
		
		@Override
		public Double subtract(Double a, Double b) {
			// TODO Auto-generated method stub
			return a-b;
		}
		
		@Override
		public Double negate(Double a) {
			// TODO Auto-generated method stub
			return -a;
		}
		
		@Override
		public Double multiply(Double a, Double b) {
			// TODO Auto-generated method stub
			return a*b;
		}
		
		@Override
		public Double devide(Double a, Double b) {
			// TODO Auto-generated method stub
			return a/b;
		}
		
		@Override
		public Double construct(String s) {
			// TODO Auto-generated method stub
			return Double.valueOf( s );
		}
		
		@Override
		public Double add(Double a, Double b) {
			// TODO Auto-generated method stub
			return a+b;
		}
	};
	
	public final static MatOperator<Double> DOUBLE_MAT_OPERATOR = new MatOperator<Double>() {
		
		@Override
		public Double zero() {
			// TODO Auto-generated method stub
			return 0.0;
		}
		
		@Override
		public Double unit() {
			// TODO Auto-generated method stub
			return 1.0;
		}
		
		@Override
		public Double simplify(Double a) {
			// TODO Auto-generated method stub
			return a;
		}
		
		@Override
		public Double pow(Double a, int n) {
			// TODO Auto-generated method stub
			return Math.pow( a, n );
		}
		
		@Override
		public Double negate(Double a) {
			// TODO Auto-generated method stub
			return -a;
		}
		
		@Override
		public Double multiply(Double a, Double b) {
			// TODO Auto-generated method stub
			return a * b;
		}
		
		@Override
		public Double devide(Double a, Double b) {
			// TODO Auto-generated method stub
			return a / b;
		}
		
		@Override
		public Double construct(String s) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Double add(Double a, Double b) {
			// TODO Auto-generated method stub
			return a + b;
		}
	}; 
	
	public final static PolyOperator<BigInteger> BIGINT_POLY_OPERATOR = 
			new PolyOperator<BigInteger>() {
		@Override
		public BigInteger multiply(BigInteger a, BigInteger b) {
			return a.multiply( b );
		}
		@Override
		public BigInteger add(BigInteger a, BigInteger b) {
			return a.add(b);
		}
		@Override
		public BigInteger subtract(BigInteger a, BigInteger b) {
			return a.subtract(b);
		}
		@Override
		public BigInteger devide(BigInteger a, BigInteger b) {
			return a.divide(b);
		}
		@Override
		public BigInteger negate(BigInteger a) {
			return a.negate();
		}
		@Override
		public BigInteger construct(String s) {
			return new BigInteger( s );
		}
		@Override
		public BigInteger zero() {
			return BigInteger.ZERO;
		}
		@Override
		public BigInteger unit() {
			return BigInteger.ONE;
		}
		@Override
		public String toString() {
			return "BIGINT_POLY_OPERATOR";
		}
	};
	
	public final static MatOperator<BigInteger> BIGINT_MAT_OPERATOR = 
			new MatOperator<BigInteger>() {
		@Override
		public BigInteger multiply(BigInteger a, BigInteger b) {
			return a.multiply(b);
		}
		@Override
		public BigInteger add(BigInteger a, BigInteger b) {
			return a.add(b);
		}
		@Override
		public BigInteger devide(BigInteger a, BigInteger b) {
			return a.divide(b);
		}
		@Override
		public BigInteger pow(BigInteger a, int n) {
			return a.pow(n);
		}
		@Override
		public BigInteger negate(BigInteger a) {
			return a.negate();
		}
		@Override
		public BigInteger zero() {
			return BigInteger.ZERO;
		}
		@Override
		public BigInteger unit() {
			return BigInteger.ONE;
		}
		@Override
		public BigInteger simplify(BigInteger a) {
			return a;
		}
		@Override
		public BigInteger construct(String s) {
			return new BigInteger( s );
		}
		@Override
		public String toString() {
			return "BIGINT_MAT_OPERATOR";
		}
	};
	
	public static <A> MatOperator<Polynomial<A>> PolyMatOperator( PolyOperator<A> p ) {
		MatOperator<Polynomial<A>> m = new MatOperator<Polynomial<A>>() {
			@Override
			public Polynomial<A> multiply(Polynomial<A> a, Polynomial<A> b) {
				Polynomial<A> r = null;
				try {
					r = a.multiply( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> add(Polynomial<A> a, Polynomial<A> b) {
				Polynomial<A> r = null;
				try {
					r = a.add( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> devide(Polynomial<A> a, Polynomial<A> b) {
				Polynomial<A> r = null;
				try {
					r = a.devideQ( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> pow(Polynomial<A> a, int n) {
				Polynomial<A> r = null;
				try {
					r = a.pow( n );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> negate(Polynomial<A> a) {
				Polynomial<A> r = null;
				try {
					r = a.subtract( a ).subtract( a );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> zero() {
				return new Polynomial<>( p );
			}
			@Override
			public Polynomial<A> unit() {
				Polynomial<A> r = new Polynomial<>( p );
				try {
					Polynomial<A>.Monomial<A> m = r.new Monomial<>
						( (A)p.unit(), 0 );
					r.addMonomial( m );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> simplify(Polynomial<A> a) {
				return a;
			}
			@Override
			public Polynomial<A> construct(String s) {
				Polynomial<A> r = null;
				try {
					r = new Polynomial<>( s, p );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
		};
		return m;
	}
	
	public static <A> GroupOperator<Polynomial<A>> PolyGroupOperator( PolyOperator<A> p ) {
		GroupOperator<Polynomial<A>> g = new GroupOperator<Polynomial<A>>() {
			@Override
			public Polynomial<A> unit() {
				Polynomial<A> r = new Polynomial<>( p );
				try {
					Polynomial<A>.Monomial<A> m = r.new Monomial<>
						( (A)p.unit(), 0 );
					r.addMonomial( m );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> multiply(Polynomial<A> a, Polynomial<A> b) {
				Polynomial<A> r = null;
				try {
					r = a.multiply( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> simplify(Polynomial<A> a) {
				return a;
			}
			@Override
			public boolean isGenerate() {
				return false;
			}
		};
		return g;
	}
	
	public static <A> RingOperator<Polynomial<A>> PolyRingOperator( PolyOperator<A> p ) {
		RingOperator<Polynomial<A>> r = new RingOperator<Polynomial<A>>() {
			@Override
			public Polynomial<A> zero() {
				return new Polynomial<>( p );
			}
			@Override
			public Polynomial<A> one() {
				Polynomial<A> r = new Polynomial<>( p );
				try {
					Polynomial<A>.Monomial<A> m = r.new Monomial<>
						( (A)p.unit(), 0 );
					r.addMonomial( m );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> add(Polynomial<A> a, Polynomial<A> b) {
				Polynomial<A> r = null;
				try {
					r = a.add( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> multiply(Polynomial<A> a, Polynomial<A> b) {
				Polynomial<A> r = null;
				try {
					r = a.multiply( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> simplify(Polynomial<A> a) {
				return a;
			}
		};
		return r;
	}
	
	public static <A> PolyOperator<Polynomial<A>> PolyPolyOperator( PolyOperator<A> p ) {
		PolyOperator<Polynomial<A>> p1 = new PolyOperator<Polynomial<A>>() {
			@Override
			public Polynomial<A> multiply(Polynomial<A> a, Polynomial<A> b) {
				Polynomial<A> r = null;
				try {
					r = a.multiply( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> add(Polynomial<A> a, Polynomial<A> b) {
				Polynomial<A> r = null;
				try {
					r = a.add( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> subtract(Polynomial<A> a, Polynomial<A> b) {
				Polynomial<A> r = null;
				try {
					r = a.subtract( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> devide(Polynomial<A> a, Polynomial<A> b) {
				Polynomial<A> r = null;
				try {
					r = a.devideQ( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> negate(Polynomial<A> a) {
				Polynomial<A> r = null;
				try {
					r = a.subtract( a ).subtract( a );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> construct(String s) {
				Polynomial<A> r = null;
				try {
					r = new Polynomial<>( s, p );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Polynomial<A> zero() {
				return new Polynomial<>( p );
			}
			@Override
			public Polynomial<A> unit() {
				Polynomial<A> r = new Polynomial<>( p );
				try {
					Polynomial<A>.Monomial<A> m = r.new Monomial<>
						( (A)p.unit(), 0 );
					r.addMonomial( m );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
		};
		return p1;
	}
	
	public static <A> MatOperator<Matrix<A>> MatMatOperator
								( MatOperator<A> m, int row, int col ) {
		MatOperator<Matrix<A>> m1 = new MatOperator<Matrix<A>>() {
			@Override
			public Matrix<A> multiply(Matrix<A> a, Matrix<A> b) {
				Matrix<A> r = null;
				try {
					r = a.multiply( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Matrix<A> add(Matrix<A> a, Matrix<A> b) {
				Matrix<A> r = null;
				try {
					r = a.add( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Matrix<A> devide(Matrix<A> a, Matrix<A> b) {
				Matrix<A> r = null;
				try {
					r = a.multiply( b.inverse() );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Matrix<A> pow(Matrix<A> a, int n) {
				Matrix<A> r = null;
				try {
					r = a.pow( n );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Matrix<A> negate(Matrix<A> a) {
				Matrix<A> r = new Matrix<>( m );
				for( int i = 1; i <= a.row; i++ ) {
					ArrayList<A> tem = new ArrayList<>();
					for( int j = 1; j <= a.col; j++ ) {
						tem.add( m.negate( a.element( i, j ) ) );
					}
					r.mat.add( tem );
				}
				r.setMat();
				return r;
			}
			@Override
			public Matrix<A> construct(String s) {
				return Algebra.construct( s, new Matrix<>( m ) );
			}
			@Override
			public Matrix<A> zero() {
				Matrix<A> r = new Matrix<>( m );
				for( int i = 1; i <= row; i++ ) {
					ArrayList<A> tem = new ArrayList<>();
					for( int j = 1; j <= col; j++ ) {
						tem.add( m.zero() );
					}
					r.mat.add( tem );
				}
				r.setMat();
				return r;
			}
			@Override
			public Matrix<A> unit() {
				Matrix<A> r = new Matrix<>( m );
				for( int i = 1; i <= row; i++ ) {
					ArrayList<A> tem = new ArrayList<>();
					for( int j = 1; j <= col; j++ ) {
						if( i != j ) tem.add( m.zero() );
						else tem.add( m.unit() );
					}
					r.mat.add( tem );
				}
				r.setMat();
				return r;
			}
			@Override
			public Matrix<A> simplify(Matrix<A> a) {
				return a;
			}
		};
		return m1;
	}
	
	public static <A> PolyOperator<Matrix<A>> MatPolyOperator( MatOperator<A> m, int n ) {
		PolyOperator<Matrix<A>> pm = new PolyOperator<Matrix<A>>() {
			@Override
			public Matrix<A> multiply(Matrix<A> a, Matrix<A> b) {
				Matrix<A> r = null;
				try {
					r = a.multiply( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Matrix<A> add(Matrix<A> a, Matrix<A> b) {
				Matrix<A> r = null;
				try {
					r = a.add( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Matrix<A> subtract(Matrix<A> a, Matrix<A> b) {
				Matrix<A> r = null;
				try {
					r = a.add( negate( b ) );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Matrix<A> devide(Matrix<A> a, Matrix<A> b) {
				Matrix<A> r = null;
				try {
					r = a.multiply( b.inverse() );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Matrix<A> negate(Matrix<A> a) {
				Matrix<A> r = new Matrix<>( m );
				for( int i = 1; i <= a.row; i++ ) {
					ArrayList<A> tem = new ArrayList<>();
					for( int j = 1; j <= a.col; j++ ) {
						tem.add( m.negate( a.element( i, j ) ) );
					}
					r.mat.add( tem );
				}
				r.setMat();
				return r;
			}
			@Override
			public Matrix<A> construct(String s) {
				return Algebra.construct( s, new Matrix<>( m ) );
			}
			@Override
			public Matrix<A> zero() {
				Matrix<A> r = new Matrix<>( m );
				for( int i = 1; i <= n; i++ ) {
					ArrayList<A> tem = new ArrayList<>();
					for( int j = 1; j <= n; j++ ) {
						tem.add( m.zero() );
					}
					r.mat.add( tem );
				}
				r.setMat();
				return r;
			}
			@Override
			public Matrix<A> unit() {
				Matrix<A> r = new Matrix<>( m );
				for( int i = 1; i <= n; i++ ) {
					ArrayList<A> tem = new ArrayList<>();
					for( int j = 1; j <= n; j++ ) {
						if( i != j ) tem.add( m.zero() );
						else tem.add( m.unit() );
					}
					r.mat.add( tem );
				}
				r.setMat();
				return r;
			}
		};
		return pm;
	}
	
	public static <A> GroupOperator<Matrix<A>> MatGroupOperator( MatOperator<A> m, int n ) {
		GroupOperator<Matrix<A>> g = new GroupOperator<Matrix<A>>() {
			@Override
			public Matrix<A> unit() {
				Matrix<A> r = new Matrix<>( m );
				for( int i = 1; i <= n; i++ ) {
					ArrayList<A> tem = new ArrayList<>();
					for( int j = 1; j <= n; j++ ) {
						if( i != j ) tem.add( m.zero() );
						else tem.add( m.unit() );
					}
					r.mat.add( tem );
				}
				r.setMat();
				return r;
			}
			@Override
			public Matrix<A> multiply(Matrix<A> a, Matrix<A> b) {
				Matrix<A> r = null;
				try {
					r = a.multiply( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Matrix<A> simplify(Matrix<A> a) {
				return a;
			}
			@Override
			public boolean isGenerate() {
				return false;
			}
		};
		return g;
	}
	
	public static <A> RingOperator<Matrix<A>> MatRingOperator( MatOperator<A> m, int n ) {
		RingOperator<Matrix<A>> r = new RingOperator<Matrix<A>>() {
			@Override
			public Matrix<A> zero() {
				Matrix<A> r = new Matrix<>( m );
				for( int i = 1; i <= n; i++ ) {
					ArrayList<A> tem = new ArrayList<>();
					for( int j = 1; j <= n; j++ ) {
						tem.add( m.zero() );
					}
					r.mat.add( tem );
				}
				r.setMat();
				return r;
			}
			@Override
			public Matrix<A> one() {
				Matrix<A> r = new Matrix<>( m );
				for( int i = 1; i <= n; i++ ) {
					ArrayList<A> tem = new ArrayList<>();
					for( int j = 1; j <= n; j++ ) {
						if( i != j ) tem.add( m.zero() );
						else tem.add( m.unit() );
					}
					r.mat.add( tem );
				}
				r.setMat();
				return r;
			}
			@Override
			public Matrix<A> add(Matrix<A> a, Matrix<A> b) {
				Matrix<A> r = null;
				try {
					r = a.add( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Matrix<A> multiply(Matrix<A> a, Matrix<A> b) {
				Matrix<A> r = null;
				try {
					r = a.multiply( b );
				} catch (NullOperateException e) {
					e.printStackTrace();
				}
				return r;
			}
			@Override
			public Matrix<A> simplify(Matrix<A> a) {
				return a;
			}
		};
		return r;
	}
	
	public static <A> GroupOperator<Mapping<A,A>> MapGroupOperator
				( Collection<? extends A> c ) {
		GroupOperator<Mapping<A, A>> m = new GroupOperator<Mapping<A, A>>() {
    		@Override
    		public Mapping<A, A> unit() {
    			Mapping<A, A> idMap = new Mapping<>();
    			for ( A x : c ) {
    				idMap.put( x, x );
    			}
    			return idMap;
    		}
    		@Override
    		public Mapping<A, A> multiply( Mapping<A, A> a, Mapping<A, A> b ) {	
    			Mapping<A, A> mtMap = new Mapping<>();
    			for ( A value : c ) {
    				mtMap.put( value, a.get( b.get( value ) ) );
    			}
    			return mtMap;
    		}
    		@Override
    		public Mapping<A, A> simplify( Mapping<A, A> a ) {	
    			return a;
    		}
    		@Override
    		public boolean isGenerate() {
    			return false;
    		}
    	};
    	return m;
	}
	
	@SuppressWarnings("unchecked")
	public static <A> A construct( String s, A a ) {
		return ((Construct<A>) a).valueOf( s );
	}
	
	public static int[] indexOf( String s, char c ) {
		char[] ch = s.toCharArray();
		int[] tem = new int[ch.length];
		int num = 0;
		for( int i = 0, j = 0; i < ch.length; i++ ) {
			if( c == ch[i] ) {
				tem[j] = i;
				j++;
				num++;
			} 
		}
		int[] res = new int[num];
		System.arraycopy( tem, 0, res, 0, num);
		return res;
	}
	
	@SuppressWarnings("unused")
	private static void instanceActualTypeArguments( Type type ) throws Exception{		
		System.out.println("该类型是"+ type);			
		if ( type instanceof ParameterizedType ) {			
			Type[] typeArguments = ((ParameterizedType)type).getActualTypeArguments();			
			for (int i = 0; i < typeArguments.length; i++) {								
				if(typeArguments[i] instanceof TypeVariable){					
					System.out.println("第" + (i+1) +  "个泛型参数类型是类型变量" + 
							typeArguments[i] + "，无法实例化。");				
				} 						
				else if(typeArguments[i] instanceof WildcardType){				
					System.out.println("第" + (i+1) +  "个泛型参数类型是通配符表达式" +
							typeArguments[i] + "，无法实例化。");			
				}							
				else if(typeArguments[i] instanceof Class){				
					System.out.println("第" + (i+1) +  "个泛型参数类型是:" + 
							typeArguments[i] + "，可以直接实例化对象");			
				}			
			}		
		} else if ( type instanceof GenericArrayType) {		
			System.out.println("该泛型类型是参数化类型数组或类型变量数组，可以获取其原始类型。");	
			Type componentType = ((GenericArrayType)type).getGenericComponentType();
			if(componentType instanceof TypeVariable){		
				System.out.println("该类型变量数组的原始类型是类型变量" + 
						componentType + "，无法实例化。");		
			} 			
			else {							
				instanceActualTypeArguments(componentType);		
			}		
		} else if( type instanceof TypeVariable){		
			System.out.println("该类型是类型变量");		
		} else if( type instanceof WildcardType){	
			System.out.println("该类型是通配符表达式");		
		} else if( type instanceof Class ){		
			System.out.println("该类型不是泛型类型");		
		} else {		
			throw new Exception();	
		}	
	}
	
	private static Group<Object> group(){
		Group<Object> group = null;
		GroupOperator<Object> ZERO_GROUP_OPERATOR = new GroupOperator<Object>() {
			@Override
			public Integer unit() {
				return 0;
			}
			@Override
			public Integer simplify(Object a) {
				return 0;
			}
			@Override
			public Integer multiply(Object a, Object b) {
				return 0;
			}
			@Override
			public boolean isGenerate() {
				return false;
			}
		};
		try {
			group = new Group<>( ZERO_GROUP_OPERATOR );
			group.add(0);
			group.isGroup();
		} catch (NullUnitException | NullInverseException |
				NullClosedException | NullAssociateException e) {
			e.printStackTrace();
		}
		return group;
	}
	
	private static Ring<Object> ring(){
		Ring<Object> ring = null;
		RingOperator<Object> ZERO_RING_OPERATOR = new RingOperator<Object>() {
			@Override
			public Integer zero() {
				return 0;
			}
			@Override
			public Integer simplify(Object a) {
				return 0;
			}
			@Override
			public Integer one() {
				return 0;
			}
			@Override
			public Integer multiply(Object a, Object b) {
				return 0;
			}
			@Override
			public Integer add(Object a, Object b) {
				return 0;
			}
		};
		try {
			ring = new Ring<>( ZERO_RING_OPERATOR );
			ring.add( 0 );
			ring.isRing();
		} catch (NullInverseException | NullClosedException |
				NullAssociateException | NullZeroException | NullAbelException e) {
			e.printStackTrace();
		}
		return ring;
	}
	
	private static Matrix<Object> matrix(){
		Matrix<Object> matrix = null;
		MatOperator<Object> ZERO_MAT_OPERATOR = new MatOperator<Object>() {
			@Override
			public Integer zero() {
				return 0;
			}
			@Override
			public Integer unit() {
				return 0;
			}
			@Override
			public Integer simplify(Object a) {
				return 0;
			}
			@Override
			public Integer pow(Object a, int n) {
				return 0;
			}
			@Override
			public Integer negate(Object a) {
				return 0;
			}
			@Override
			public Integer multiply(Object a, Object b) {
				return 0;
			}
			@Override
			public Integer devide(Object a, Object b) {
				return 0;
			}
			@Override
			public Integer construct(String s) {
				return 0;
			}
			@Override
			public Integer add(Object a, Object b) {
				return 0;
			}
		};
		Integer[][] i = { { 0 } };
		matrix = new Matrix<>( i, ZERO_MAT_OPERATOR );
		matrix.setMat();
		return matrix;
	}
	
	private static Polynomial<Object> polynomial(){
		PolyOperator<Object> ZERO_POLY_OPERATOR = new PolyOperator<Object>() {
			@Override
			public Integer zero() {
				return 0;
			}	
			@Override
			public Integer unit() {
				return 0;
			}		
			@Override
			public Integer subtract(Object a, Object b) {
				return 0;
			}
			@Override
			public Integer negate(Object a) {
				return 0;
			}
			@Override
			public Integer multiply(Object a, Object b) {
				return 0;
			}			
			@Override
			public Integer devide(Object a, Object b) {
				return 0;
			}		
			@Override
			public Integer construct(String s) {
				return 0;
			}
			@Override
			public Integer add(Object a, Object b) {
				return 0;
			}
		};
		return new Polynomial<>( ZERO_POLY_OPERATOR );
	}
	
	private static Ring<Object>.Domain<Object> domain(){
		Ring<Object>.Domain<Object> domain = null;
		RingOperator<Object> ZERO_DOMAIN_OPERATOR = new RingOperator<Object>() {
			@Override
			public Integer zero() {
				return 0;
			}
			@Override
			public Integer simplify(Object a) {
				return 0;
			}
			@Override
			public Integer one() {
				return 0;
			}
			@Override
			public Integer multiply(Object a, Object b) {
				return 0;
			}
			@Override
			public Integer add(Object a, Object b) {
				return 0;
			}
		};
		try {
			domain = ring.new Domain<>( ZERO_DOMAIN_OPERATOR );
			domain.add( 0 );
			domain.isDomain();
		} catch (NullInverseException | NullClosedException | NullAssociateException
				| NullZeroException| NullUnitException | NullAbelException |
				ZeroFactorException e) {
			e.printStackTrace();
		}
		return domain;
	}
	
	private static Ring<Object>.Skew<Object> skew() {
		Ring<Object>.Skew<Object> skew = null;
		RingOperator<Object> ZERO_SKEW_OPERATOR = new RingOperator<Object>() {
			@Override
			public Integer zero() {
				return 0;
			}
			@Override
			public Integer simplify(Object a) {
				return 0;
			}
			@Override
			public Integer one() {
				return 0;
			}
			@Override
			public Integer multiply(Object a, Object b) {
				return 0;
			}
			@Override
			public Integer add(Object a, Object b) {
				return 0;
			}
		};
		try {
			skew = ring.new Skew<>( ZERO_SKEW_OPERATOR );
			skew.add( 0 );
			skew.isSkew();
		} catch (NullInverseException | NullClosedException | NullAssociateException
				| NullZeroException| NullUnitException | NullAbelException |
				ZeroFactorException e) {
			e.printStackTrace();
		}
		return skew;
	}
	
}