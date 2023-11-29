package javam.number;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;

public class Fibonacci {
	
	private static ArrayList<BigInteger> fiboList = new ArrayList<>();
	
	private Fibonacci() {}
	
	public static BigInteger IndexOf( int i ) {
		File f = new File("E:\\java file\\fibonacci.txt");
		int n = 0;
		try {
			BufferedReader br = new BufferedReader( new FileReader( f ) );
			String contentLine;
			while ( (contentLine = br.readLine()) != null ) {
				BigInteger bi = new BigInteger( contentLine );
				if( n >= i ) {
					br.close();
					return bi;
				}
				n++;
			}
			br.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		addFiboList( i );
		return fiboList.get( fiboList.size()-1 );
	}
	
	@SuppressWarnings({ "unused" })
	private static void setFiboList( int index ) {
		int n = 0;
		try {
            FileOutputStream out = 
            		new FileOutputStream( "E:\\java file\\fibonacci.txt", false );
            PrintStream p = new PrintStream( out );
    		BigInteger a = BigInteger.ZERO;
    		BigInteger b = BigInteger.ONE;
    		BigInteger c = BigInteger.ZERO;
    		for ( ; ; ) {
    			if( n <= index ) {
    				a = b.add( c );
    				p.println( a );
    				n++;
    			} else break;
    			if( n <= index ) {
    				b = c.add( a );
    				p.println( b );
    				n++;
    			} else break;
    			if( n <= index ) {
    				c = a.add( b );
    				p.println( c );
    				n++;
    			} else break;
    		}
    		p.close();
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }
	}
	
	private static void addFiboList( int index ) {
		File f = new File("E:\\java file\\fibonacci.txt");
		try {
			BufferedReader br = new BufferedReader( new FileReader( f ) );
			String contentLine;
			while ( (contentLine = br.readLine()) != null ) {
				fiboList.add( new BigInteger( contentLine ) );
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int n = 0;
		try {
			FileOutputStream out = new FileOutputStream( f, true );
            PrintStream p = new PrintStream( out );
            BigInteger a = fiboList.get( fiboList.size()-2 ).
            		add( fiboList.get( fiboList.size()-1 ) );
    		BigInteger b = fiboList.get( fiboList.size()-1 ).add( a );
    		BigInteger c = BigInteger.ZERO;
    		for ( ; ; ) {
    			if( n <= index ) {
    				a = b.add( c );
    				fiboList.add( a );
    				p.println( a );
    				n++;
    			} else break;
    			if( n <= index ) {
    				b = c.add( a );
    				fiboList.add( b );
    				p.println( b );
    				n++;
    			} else break;
    			if( n <= index ) {
    				c = a.add( b );
    				fiboList.add( c );
    				p.println( c );
    				n++;
    			} else break;
    		}
    		p.close();
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }
	}
}
