package javam.category;

import java.util.ArrayList;

public class AbstractCategory<O> extends Category<O>{

	public Morphism add( O s, O t ) {
		ArrayList<Object> obj = getObject( s, t );
		Morphism mor = new Morphism( obj.get( 0 ), obj.get( 1 ) );
		Mor.add( mor );
		return mor;
	}
	
}
