package javam.category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@SuppressWarnings({"rawtypes"})
public class ConcreteCategory<O extends Collection, V> extends Category<O> {
	
	public Set<Morphism> Mor = new HashSet<>();
	
	public Morphism add( O s, O t, Function<V, V> f ) {
		Morphism mor = null;
		if( s.equals(t) ) {
			ArrayList<Object> tem = getObject( s, s );
			(mor = new Morphism( tem.get( 0 ), tem.get( 1 ), f )).setName( "id_" + s );
			Mor.add( mor );
		} else {
			ArrayList<Object> obj = getObject( s, t );
			int tem1 = 0, tem2 = 0;
			for( Morphism mo : Mor ) {
				if( mo.name.equals( "id_" + s ) ) tem1++;
				if( mo.name.equals( "id_" + t ) ) tem2++;
			}
			if( tem1 == 0 ) add( s, s, x -> x );
			if( tem2 == 0 ) add( t, t, x -> x );
			mor = new Morphism( obj.get( 0 ), obj.get( 1 ), f );
			Mor.add( mor );
		}
		return mor;
	}
	
	public V get( String name, V key ) {
		Morphism mor = getMorphism( name );
		if( mor == null ) return null;
		return mor.apply( key );
	}
	
	protected Morphism getMorphism( String name ) {
		Morphism mor = null;
		for( Morphism mo : Mor ) {
			if( mo.toString().equals( name ) ) {
				mor = mo; break;
			}
		}
		return mor;
	}
	
	public class Morphism extends Category<O>.Morphism implements Function<V, V> {
		
		protected Function<V, V> function;
		
		public Morphism( Object domain, Object codomain, Function<V, V> f ) {
			super( domain, codomain );
			this.function = f;
		}

		@Override
		public V apply( V t ) {
			return function.apply( t );
		}
		
	}
	
}
