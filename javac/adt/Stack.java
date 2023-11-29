package javac.adt;

import java.io.Serializable;
import java.util.Collection;

public class Stack<E> extends java.util.Stack<E> implements Serializable {

	public Stack() {}
	
	public Stack(Collection<? extends E> c) {
		for( E e : c ) {
			this.push(e);
		}
	}
	
	@Override
	public synchronized String toString() {
		return super.toString();
	}
	
	private static final long serialVersionUID = 6206776748489754232L;
	
}
