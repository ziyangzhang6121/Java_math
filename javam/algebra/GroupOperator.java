package javam.algebra;

import java.util.ArrayList;

public interface GroupOperator<A> {
	
	/**
	 * State unit element of group.
	 * @return unit element's value
	 */
	public A unit();
	
	/**
	 * State multiplication of group.
	 * @param a is the left element of multiply.
	 * @param b is the right element of multiply.
	 * @return the result of multiplication.
	 */
	public A multiply( A a, A b );
	
	/**
	 * Simplify the result of element's value.
	 * <p> Override it while using.
	 * @param a is the result of element's value.
	 * @return the simple value.
	 */
	public A simplify( A a );
	
	/**
	 * Judge whether {@code isGenerate()} method is used.
	 * <p> Override it while using.
	 * @return {@code false} if do not use {@code isGenerate()} method.
	 * 		   <p>{@code true} if the group is a Generating Group.
	 */
	public boolean isGenerate();
	
	/**
	 * Provide a method to generate a group in one generator.
	 * @param a is the generator.
	 * @return the generate element.
	 */
	public default A generate( A a ) { return null; }
	
	/**
	 * Provide a method to generate a group in generators.
	 * <p> Override it while using.
	 * @param arr is the list stores generators.
	 * @param a is left current element.
	 * @param b is right current element. 
	 * @return the generate element.
	 */
	public default A generate( ArrayList<A> arr, A a, A b ) { return null; }

	public abstract class Represent<A> {
		
		ArrayList<A> arr = null;
		
		public Represent() {
			// TODO Auto-generated constructor stub
		}
		
		public ArrayList<A> generator( ArrayList<A> grt ) {
			return this.arr = grt;
		}
		
	}
	
}
