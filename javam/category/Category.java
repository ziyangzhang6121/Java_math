package javam.category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class Category<O> implements Collection<O> {

	protected String name;
	
	public Set<Object> Ob = new HashSet<>();
	
	public Set<Morphism> Mor = new HashSet<>();
	
	public Morphism compose( Morphism f, Morphism g ) {
		return new Morphism( f.domain, g.codomain );
	}
	
	public Set<Morphism> Hom( O s, O t ) {
		ArrayList<Object> obj = getObject( s, t );
		Set<Morphism> Hom = new HashSet<>();
		for( Morphism mor : Mor ) 
			if( mor.domain.equals( obj.get( 0 ) ) && mor.codomain.equals( obj.get( 1 ) ) )
				Hom.add( mor );
		return Hom;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
	
	protected ArrayList<Object> getObject( O s, O t ) {
		Object domain = null, codomain = null;
		int tem1 = 0, tem2 = 0;
		for( Object ob : Ob ) {
			if( ob.ob.equals( s ) ) { tem1++; domain = ob; }
			if( ob.ob.equals( t ) ) { tem2++; codomain = ob; }
			if( tem1 == 1 && tem2 == 1 ) break;
		}
		if( tem1 == 0 ) { domain = new Object( s ); Ob.add( domain ); }
		if( tem2 == 0 ) { codomain = new Object( t ); Ob.add( codomain ); }
		ArrayList<Object> obj = new ArrayList<>();
		obj.add( domain ); obj.add( codomain );
		return obj;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@SuppressWarnings({"unchecked"})
	protected class Object {
		
		public O ob;
		
		public Object( O ob ) {
			this.ob = ob;
			Ob.add( this );
			Morphism id = new Morphism( this, this );
			id.setName( "id_" + ob );
			Mor.add( id );
		}
		
		@Override
		public String toString() {
			return ob.toString();
		}
		
		@Override
		public int hashCode() {
			return 0;
		}
		
		@Override
		public boolean equals( java.lang.Object obj ) {
			if( !(obj instanceof Category.Object) ) return false;
			Object ob = (Object) obj;
			return this.ob.equals( ob.ob );
		}
		
	}
	
	protected class Morphism {
		
		protected String name;
		
		public Object domain;
		
		public Object codomain;
		
		public Morphism( Object domain, Object codomain ) {
			this.domain = domain;
			this.codomain = codomain;
		}
		
		public void setName( String name ) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}

	}
	
	@Override
	public int size() {
		return Ob.size();
	}

	@Override
	public boolean isEmpty() {
		return Ob.isEmpty();
	}

	@Override
	public boolean contains(java.lang.Object o) {
		return Ob.contains(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<O> iterator() {
		return (Iterator<O>) Ob.iterator();
	}

	@Override
	public java.lang.Object[] toArray() {
		return Ob.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return Ob.toArray(a);
	}

	@Override
	public boolean add(O e) {
		return false;
	}

	@Override
	public boolean remove(java.lang.Object o) {
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return Ob.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends O> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {
		Ob.clear(); Mor.clear();
	}
	
}
