import java.math.BigInteger;
import java.util.Scanner;

import javam.number.Fibonacci;
import javam.number.Prime;

public class NumberDemo {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner sc = new Scanner( System.in );
		System.out.print( "Please enter an integer: " );
		BigInteger b = sc.nextBigInteger();
		long start = System.currentTimeMillis();
		//Prime.lessPrimeOf( b );
		boolean bo = Prime.isPrime( b );
		long end = System.currentTimeMillis();
		System.out.println( "The cost time is " + ( end - start ) + " ms." );
		System.out.println( b + " is a prime: " + bo );
		System.out.println( Fibonacci.IndexOf(5345) );
	}
}
