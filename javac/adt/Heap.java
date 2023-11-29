package javac.adt;

import java.util.Collection;

public class Heap<E> extends java.util.LinkedList<E> {
	 
	public Heap() {}
	
	public Heap(Collection<? extends E> c) {
		super(c);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	private static final long serialVersionUID = -7813995049812668482L;
	
}
