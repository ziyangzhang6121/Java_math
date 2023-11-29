package javam.number;

import java.math.BigDecimal;
import java.math.BigInteger;

import javam.algebra.Construct;

public class Fraction implements Construct<Fraction> {
	
	public BigInteger molecule;
	
	public BigInteger denominator;
	
	public static final Fraction ZERO = new Fraction( 0, 1 );
	
	public static final Fraction ONE = new Fraction( 1, 1 );
	
	public Fraction() {
		super();
	}
	
	public Fraction( String s ) {
		if( s.contains( "." ) ) {
			BigDecimal d = new BigDecimal( s );
			BigDecimal _10 = new BigDecimal( "10" );
			int length = d.toString().length();
			BigInteger a = d.multiply( _10.pow( length ) ).toBigInteger();
			BigInteger b = _10.pow( length ).toBigInteger();
			setNumber( a, b );
		}else {
			String[] t = s.split( "/" );
			BigInteger a, b;
			if( t[0].equals( "-" ) ) a = new BigInteger( "-1" );
			else a = new BigInteger( t[0] );
			if( t.length == 1 ) b = BigInteger.ONE;
			else b = new BigInteger( t[1] );
			if( b.equals( BigInteger.ZERO ) ) throw new ArithmeticException("/ by zero");
			setNumber( a, b );
		}
	}
	
	public Fraction( Double dd ) {
		this( BigDecimal.valueOf( dd ) );
	}
	
	public Fraction( BigDecimal d ) {
		BigDecimal _10 = new BigDecimal( "10" );
		int length = d.toString().length();
		BigInteger a = d.multiply( _10.pow( length ) ).toBigInteger();
		BigInteger b = _10.pow( length ).toBigInteger();
		setNumber( a, b );
	}
	
	public Fraction( int aa, int bb ) {
		BigInteger a = BigInteger.valueOf( aa );
		BigInteger b = BigInteger.valueOf( bb );
		if( b.equals( BigInteger.ZERO ) ) throw new ArithmeticException("/ by zero");
		setNumber( a, b );
	}
	
	public Fraction( BigInteger a, BigInteger b ) {
		if( b.equals( BigInteger.ZERO ) ) throw new ArithmeticException("/ by zero");
		setNumber( a, b );
	}
	
	public Fraction( String as, String bs ) {
		BigInteger a = new BigInteger( as );
		BigInteger b = new BigInteger( bs );
		if( b.equals( BigInteger.ZERO ) ) throw new ArithmeticException("/ by zero");
		setNumber( a, b );
	}
	
	public static Fraction valueOf( double q ) {
	    long i = Double.doubleToRawLongBits(q);
	    boolean negate = (i & -9223372036854775808L) != 0L;
	    long M = i & 4503599627370495L; // 获取实际数字位 M
	    int E = (int) ((i & 9218868437227405312L) >> 52); // 获取指数位 E
	    if (E == 2047) { // 特殊数字
	        if (M == 0) // Infinity
	            return null;
	        else // 不是一个合法的double
	            return null;
	    } else {
	        if (E == 0) {
	            if (M == 0)
	                return new Fraction(0, 1);
	            int back = Long.numberOfLeadingZeros(M) - 11;
	            M <<= back;
	            E = 1 - back;
	        } else
	            M |= 4503599627370496L;
	        E -= 1023;
	        long b = 1;
	        for (int w = E; w < 52; w++)
	            b <<= 1;
	        if (negate) M = -M;
	        return new Fraction( String.valueOf( M ), String.valueOf( b ) );
	    }
	}

	public static Fraction valueOf( float q ) {
	    int i = Float.floatToRawIntBits(q);
	    boolean negate = (i & -2147483648) != 0; // 是否为负数
	    int M = i & 8388607; // 获取实际数字位 M
	    int E = (i & 2139095040) >> 23; // 获取指数位 E
	    if (E == 255) { // 特殊数字
	        if ((long) M == 0L) // Infinity
	            return null;
	        else // 非法的float
	            return null;
	    } else {
	        if (E == 0) {
	            if (M == 0)
	                return new Fraction(0, 1);
	            int back = Integer.numberOfLeadingZeros(M) - 8; // 获取顶部共有几个连续的0，-8即减去小数位
	            M <<= back;
	            E = 1 - back;
	        } else
	            M |= 8388608; // 自动在顶位或上1，也就是第9位|=1
	        E -= 127;
	        int b = 1;
	        for (int w = E; w < 23; w++)
	            b <<= 1;
	        if (negate) M = -M;
	        return new Fraction( M, b );
	    }
	}
	
	private BigInteger gcd( BigInteger a, BigInteger b ) {
		a = a.abs();
		b = b.abs();
		while ( true ) {
			if( ( a = a.mod( b ) ).compareTo( BigInteger.ZERO ) == 0 ) return b;
			if( ( b = b.mod( a ) ).compareTo( BigInteger.ZERO ) == 0 ) return a;
		}
	}
	
	private void setNumber( BigInteger a, BigInteger b ) {
		BigInteger g = gcd( a, b );
		if( a.multiply( b ).compareTo( BigInteger.ZERO ) < 0 ) {
			a = a.abs().negate();
			b = b.abs();
		} else {
			a = a.abs();
			b = b.abs();
		}
		this.molecule = a.divide( g );
		this.denominator = b.divide( g );
	}
	
	public Fraction multiply( Fraction a ) {
		return new Fraction( this.molecule.multiply( a.molecule), 
				this.denominator.multiply(a.denominator) );
	}

	public Fraction add( Fraction a ) {
		return new Fraction( this.molecule.multiply( a.denominator ).add
				( this.denominator.multiply( a.molecule ) ),
				this.denominator.multiply( a.denominator ) );
	}
	
	public Fraction subtract( Fraction a ) {
		return new Fraction( this.molecule.multiply( a.denominator ).subtract
				( this.denominator.multiply( a.molecule ) ),
				this.denominator.multiply( a.denominator ) );
	}

	public Fraction devide( Fraction a ) {
		return new Fraction( this.molecule.multiply( a.denominator ), 
				this.denominator.multiply( a.molecule ) );
	}

	public Fraction pow( int n ) {
		return new Fraction( this.molecule.pow( n ), this.denominator.pow( n ) );
	}
	
	public Fraction pow( double d ) {
		double dd = this.doubleValue();
		double val = Math.pow( dd, d );
		return new Fraction( val );
	}

	public Fraction negate() {
		return new Fraction( this.molecule.negate(), this.denominator );
	}
	
	public Fraction inverse() {
		return new Fraction( this.denominator, this.molecule );
	}
	
	public BigDecimal toBigDecimal() {
		return new BigDecimal( molecule ).divide( new BigDecimal( denominator ) );
	}
	
	public double doubleValue() {
		return ( ((double)molecule.intValue()) / ((double)denominator.intValue()) );
	}
	
	@Override
	public String toString() {
		if( this.denominator.equals( BigInteger.ZERO ) ) throw new ArithmeticException("/ by zero");
		if( this.denominator.equals( BigInteger.ONE ) ) return molecule + "";
		if( this.molecule.equals( BigInteger.ZERO ) ) return 0 + "";
		return molecule + "/" + denominator;
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( !(obj instanceof Fraction) ) return false;
		Fraction f = (Fraction) obj;
		return this.molecule.multiply( f.denominator ).equals
				( this.denominator.multiply( f.molecule ) );
	}

	@Override
	public Fraction valueOf(String s) {
		return new Fraction( s );
	}
	
}
