package javam.category;

import java.util.function.Function;

@SuppressWarnings({"rawtypes","unused","unchecked"})
public class Functor<E, F> extends ConcreteCategory<Category, Object> {
	
	private Category<E> e;
	
	private Category<F> f;
	
	private Function<java.lang.Object, java.lang.Object> fun;
	
	public Functor() {}

	public Functor( Category<E> e, Category<F> f, Function<E, F> fun ) {
		this.e = e;
		this.f = f;
		this.fun = (Function<java.lang.Object, java.lang.Object>) fun;
		this.add( e, f, this.fun ).setName( "f" );
	}
	
	public void setName( String name ) {
		getMorphism( "f" ).setName( name );
	}
	
}
