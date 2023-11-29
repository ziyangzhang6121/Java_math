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
import java.util.Arrays;

public class Prime {
	
	private static ArrayList<BigInteger> primeList = new ArrayList<>();
	
	private Prime() {}
	
	public static void lessPrimeOf( int i ) {
		lessPrimeOf( i );
	}
	
	public static void lessPrimeOf( String s ) {
		lessPrimeOf( Integer.parseInt(s) );
	}
	
	public static void lessPrimeOf( BigInteger b ) {
		File f = new File("E:\\java file\\prime.txt");
		try {
			BufferedReader br = new BufferedReader( new FileReader( f ) );
			String contentLine;
			while ( (contentLine = br.readLine()) != null ) {
				BigInteger bi = new BigInteger( contentLine );
				primeList.add( bi );
				if( bi.compareTo(b) > 0 ) {
					br.close();
					break;
				}
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		BigInteger i = primeList.get( primeList.size()-1 );
        if( b.compareTo( i ) < 0 ) 
        	for( BigInteger bb : primeList ) {
        		if( b.compareTo( bb ) >= 0 ) System.out.println( bb );
        	}
        else {
        	addPrimeList( b );
        	for( BigInteger bb : primeList ) {
        		if( b.compareTo( bb ) >= 0 ) System.out.println( bb );
        	}
        }
	}
	
	public static boolean isPrime( int i ) {
		return isPrime( BigInteger.valueOf( i ) );
	}
	
	public static boolean isPrime( String s ) {
		return isPrime( Integer.parseInt(s) );
	}
	
	public static boolean isPrime( BigInteger b ) {
		File f = new File("E:\\java file\\prime.txt");
		try {
			BufferedReader br = new BufferedReader( new FileReader( f ) );
			String contentLine;
			while ( (contentLine = br.readLine()) != null ) {
				BigInteger bi = new BigInteger( contentLine );
				primeList.add( bi );
				if( bi.compareTo(b) > 0 ) {
					br.close();
					break;
				}
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		BigInteger i = primeList.get( primeList.size()-1 );
        if( b.compareTo( i ) < 0 ) return primeList.contains( b );
        else {
        	addPrimeList( b );
        	return primeList.contains( b );
        }
	}
	
	private static BigInteger sqrt(BigInteger num) {
        String s = num.toString();
        int mlen = s.length();    //被开方数的长度
        int len;    //开方后的长度
        BigInteger beSqrtNum = new BigInteger(s);//被开方数
        BigInteger sqrtOfNum;    //存储开方后的数
        BigInteger sqrtOfNumMul;    //开方数的平方
        String sString;//存储sArray转化后的字符串
        if (mlen % 2 == 0) len = mlen / 2;
        else len = mlen / 2 + 1;
        char[] sArray = new char[len];
        Arrays.fill(sArray, '0');//开方数初始化为0
        for (int pos = 0; pos < len; pos++) {
            //从最高开始遍历数组，
            //每一位都转化为开方数平方后刚好不大于被开方数的程度
            for (char ch = '1'; ch <= '9'; ch++) {
                sArray[pos] = ch;
                sString = String.valueOf(sArray);
                sqrtOfNum = new BigInteger(sString);
                sqrtOfNumMul = sqrtOfNum.multiply(sqrtOfNum);
                if (sqrtOfNumMul.compareTo(beSqrtNum) == 1) {
                    sArray[pos] -= 1;
                    break;
                }
            }
        }
        return new BigInteger(String.valueOf(sArray));
    }
	
	private static boolean isDivide( BigInteger i ) {
		int c = 0;
		for ( int k = 0; primeList.get( k ).compareTo( sqrt( i ) ) != 1; k++ ) {
			if( i.mod( primeList.get( k ) ).equals( BigInteger.ZERO ) ) c++;
		}
		if( c == 0 ) return false;
		else return true;
	}
	
	@SuppressWarnings({ "unused" })
	private static void setPrimeList( BigInteger n ) {
		try {
            FileOutputStream out = 
            		new FileOutputStream( "E:\\java file\\prime.txt", false );
            PrintStream p = new PrintStream( out );
            p.println("2");
    		for ( BigInteger i = new BigInteger( "3" ); i.compareTo( n ) != 1;
    				i = i.add( BigInteger.ONE ) ) {
    			if( !isDivide(i) ) {
    				p.println( i );
    			}
    		}
    		p.close();
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }
	}
	
	private static void addPrimeList( BigInteger n ) {
		File f = new File("E:\\java file\\prime.txt");
		try {
			BufferedReader br = new BufferedReader( new FileReader( f ) );
			String contentLine;
			while ( (contentLine = br.readLine()) != null ) {
				primeList.add( new BigInteger( contentLine ) );
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FileOutputStream out = new FileOutputStream( f, true );
            PrintStream p = new PrintStream( out );
            BigInteger i = primeList.get( primeList.size()-1 ).add( BigInteger.ONE );
            if( n.compareTo( i ) == 1 ) {
            	for ( ; i.compareTo( n ) != 1; i = i.add( BigInteger.ONE ) ) {
            		if( !isDivide( i ) ) {
            			primeList.add( i );
            			p.println( i );
            		}
            	}
            }
            p.close();
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }
	}
	
}