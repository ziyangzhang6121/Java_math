package javam.analysis;

import java.util.ArrayList;

import javac.adt.Heap;
import javac.adt.Stack;

public class Function {
	
	private char x;
	
	private String str;
	
	private Stack<Character> sc = new Stack<>();
	
	private ArrayList<Heap<String>> func = new ArrayList<>();
	
	public Function() {}
	
	public Function( String f ) {
		this( f, 'x' );
	}
	
	public Function( String f, char x ) {
		this.x = x;
		this.setFunction( f );
		for( Heap<String> ccc : func ) {
			for (String d : ccc) {
				System.out.println(d);
			}
			System.out.println("--------");
		}
		System.out.println(str);
		System.out.println( postfixOf(f) );
	}
	
	private void setFunction( String f ) {
		char[] ch = f.toCharArray();
		Heap<String> ts = new Heap<>();
		int pointer = 0, index = 0;
		char targer;
		outer : do {
			sc.push( ch[pointer] );
			if( ch[pointer] == '(' ) index++;
			if( ch[pointer] == ')' ) {
				index--;
				if( index == 0 ) {
					Stack<Character> sh = new Stack<>();
					while( (targer = sc.pop()) != '(' ) sh.push( targer );
					Stack<Character> tem = new Stack<>();
					tem.addAll( sc );
					sc.push( '(' );
					while( !sh.isEmpty() ) sc.push( sh.pop() );
					if( ( tem.isEmpty() || tem.peek() == '+' || tem.peek() == '-' 
						|| tem.peek() == '*' || tem.peek() == '/' ) &&
						( pointer == ch.length-1 || ch[pointer+1] == '+' || 
						ch[pointer+1] == '-' || ch[pointer+1] == '*' || 
						ch[pointer+1] == '/' ) ) continue outer;
				}
				StringBuilder sb = new StringBuilder();
				while( (targer = sc.pop()) != '(' ) sb.append( targer );
				ts.offer( sb.deleteCharAt(0).reverse().toString() );
				sc.push( x );
				if( index == 0 ) {
					Heap<String> tem = new Heap<>();
					while( !ts.isEmpty() ) tem.push( ts.poll() );
					func.add( tem );
				}
			}
			if( pointer == ch.length-1 ) {
				StringBuilder sb = new StringBuilder();
				while( !sc.isEmpty() ) sb.append( sc.pop() );
				str = sb.reverse().toString();
			}
			pointer++;
		} while( pointer < ch.length );
	}
	
	private String postfixOf( String str ) {
		char[] ch = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		Stack<Character> opr = new Stack<>();
		for( char c : ch ) {
			if( c == '+' || c == '-' ) {
				while( !opr.empty() && (opr.peek()) != '(' ) 
					sb.append( opr.pop() );
				opr.push( c );
			} else if( c == '*' || c == '/' ) {
				while( !opr.empty() && opr.peek() != '(' 
						&& opr.peek() != '+' && opr.peek() != '-' ) 
					sb.append( opr.pop() );
				opr.push( c );
			} else if( c == ')' ) {
				while( !opr.empty() && opr.peek() != '(' ) 
					sb.append( opr.pop() );
				opr.pop();
			} else if( c == '(' ) opr.push( c );
			else sb.append( c );
		}
		while( !opr.empty() ) sb.append( opr.pop() );
		return sb.toString();
	}
	
}
