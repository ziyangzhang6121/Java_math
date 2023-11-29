package javam.algebra;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class Mapping<K,V> implements Serializable {

	private static final long serialVersionUID = -353575168126757697L;
	
	private int size = 0;
	
	private LinkedList<K> k = new LinkedList<>();
	private LinkedList<V> v = new LinkedList<>();
	
	public Mapping() {
		super();
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void clear() {
		k.clear();
		v.clear();
		size = 0;
	}
	
	@SuppressWarnings("unchecked")
	public Mapping( String str, Type t ) {
		K tem = null;
		try {
			tem = (K) Class.forName( t.getTypeName() ).newInstance();
		} catch (InstantiationException | IllegalAccessException 
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		if( str.indexOf( '(' ) == 0 ) {
			String[] s = str.split( " " );
			for( int i = 1; i < s.length-2; i++ ) {
				k.add( Algebra.construct( s[i], tem ) );
				v.add( (V) Algebra.construct( s[i+1], tem ) );
			}
			k.add( Algebra.construct( s[s.length-2], tem ) );
			v.add( (V) Algebra.construct( s[1], tem ) );
		}
	}
	
	public V put( K key, V value ) {
        k.add( key );
        v.add( value );
        size++;
        int i = 0;
        for ( ; i < k.size(); i++ ) {
			if( k.get(i).equals( key ) ) break;
		}
		if( i == 0 ) return null;
		return v.get( i - 1 );
    }
	
	public K get( int index ) {
		return k.get( index );
	}
	
	public V get( Object key ) {
		int i = 0;
        for ( ; i < k.size(); i++ ) {
			if( k.get(i).equals(key) ) break;
		}
		return v.get( i );
	}
	
	private LinkedList<V> get( LinkedList<K> k ) {
		LinkedList<V> v = new LinkedList<>();
		for( K k1 : k ) {
			v.add( get(k1) );
		}
		return v;
	}
	
	@SuppressWarnings({ "unlikely-arg-type", "unchecked" })
	@Override
	public String toString() {
		if( k.size() == 0 ) return "[ null ]";
		if( !k.get(0).toString().contains( "\n" ) ) {
			if( k.get(0).getClass().getName() != v.get(0).getClass().getName() 
					|| !k.containsAll(v) || !v.containsAll(k) ) {	
				int[] length = new int[k.size()];
				for( int i = 0; i < length.length; i++ ) {
					length[i] = Math.max( k.get(i).toString().length(), 
							v.get(i).toString().length());
				}
				StringBuffer sk = new StringBuffer();
				sk.append( "©° " );
				for( int i = 0; i < length.length; i++ ) {
					int c = length[i] - k.get(i).toString().length();
					sk.append( k.get(i) );
					for( int j = 0; j <= c; j++ ) {
						sk.append( ' ' );
					}
					if( i != k.size()-1 ) sk.append( "  " );
				}
				sk.append( "©´" );
				StringBuffer sv = new StringBuffer();
				sv.append( "©¸ " );
				for( int i = 0; i < length.length; i++ ) {
					int c = length[i] - v.get(i).toString().length();
					sv.append( v.get(i) );
					for( int j = 0; j <= c; j++ ) {
						sv.append( ' ' );
					}
					if( i != v.size()-1 ) sv.append( "  " );
				}
				sv.append( "©¼" );
				return sk.toString() + "\n" + sv.toString();
			}else if( k.equals(v) ) {
				return "id";
			}else {
				StringBuffer s = new StringBuffer();
				for( int i = 0; i < k.size(); i++ ) {
					String str = " " + k.get(i) + " ";
					if( i != 0 && s.toString().indexOf(str) != -1 ) continue;
					K index = k.get(i);
					if( index.equals( get( index ) ) ) continue;
					s.append( "( " + index.toString() + " "  );
					do {
						s.append( get( index ) + " "  );	
						index = (K) get( index );
					} while ( !get(index).equals(k.get(i)) );
					s.append( ")" );
				}
				return s.toString();
			}
		} else {
			if( k.get(0).getClass().getName() != v.get(0).getClass().getName() 
					|| !k.containsAll(v) || !v.containsAll(k) ) {	
				int length[] = new int[k.size()];
				int height = 0;
				for( int i = 0; i < k.size(); i++ ) {
					String[] s = k.get(i).toString().split( "\n" );
					String[] s1 = v.get(i).toString().split( "\n" );
					if( s.length > height ) height = s.length;
					if( s1.length > height ) height = s1.length;
					length[i] = Math.max
							( s[0].toString().length(), s1[0].toString().length() );
				}
				StringBuffer sk = new StringBuffer();
				StringBuffer[] sbarr = new StringBuffer[height];
				for( int l = 0; l < sbarr.length; l++ ) {
					sbarr[l] = new StringBuffer();
				}
				for( int i = 0; i < k.size(); i++ ) {
					String[] s = k.get(i).toString().split( "\n" );
					if( i == 0 ) {
						for( int j = 0; j < height; j++ ) {
							if( j >= s.length ) continue;
							if( j == 0 ) sbarr[j].append( "©° " );
							else sbarr[j].append( "| " );
						}
					} 
					for( int j = 0; j < height; j++ ) {
						if( j >= s.length ) continue;
						sbarr[j].append( s[j] );
						int c = 0;
						if( j < s.length ) c = length[i]-s[j].length();
						else c = length[i];
						for( int n = 0; n <= c; n++ ) {
							sbarr[j].append( " " );
						}
					} 
					if( i == k.size()-1 ) {
						for( int j = 0; j < height; j++ ) {
							if( j == 0 ) {
								sbarr[j].append( "©´" );
								sk.append( sbarr[j] + "\n" );
							} else {
								if( j >= s.length ) continue;
								sbarr[j].append( "|" );
								sk.append( sbarr[j] + "\n" );
							}
						}
					}
				}
				StringBuffer sv = new StringBuffer();
				StringBuffer[] sbarr1 = new StringBuffer[height];
				for( int l = 0; l < sbarr1.length; l++ ) {
					sbarr1[l] = new StringBuffer();
				}
				for( int i = 0; i < v.size(); i++ ) {
					String[] s = v.get(i).toString().split( "\n" );
					if( i == 0 ) {
						for( int j = 0; j < height; j++ ) {
							if( j >= s.length ) continue;
							if( j == height-1 ) sbarr1[j].append( "©¸ " );
							else sbarr1[j].append( "| " );
						}
					} 
					for( int j = 0; j < height; j++ ) {
						if( j >= s.length ) continue;
						sbarr1[j].append( s[j] );
						int c = 0;
						if( j < s.length ) c = length[i]-s[j].length();
						else c = length[i];
						for( int n = 0; n <= c; n++ ) {
							sbarr1[j].append( " " );
						}
					} 
					if( i == v.size()-1 ) {
						for( int j = 0; j < height; j++ ) {
							if( j == height-1 ) {
								sbarr1[j].append( "©¼" );
								sv.append( sbarr1[j] );
							} else {
								if( j >= s.length ) continue;
								sbarr1[j].append( "|" );
								sv.append( sbarr1[j] + "\n" );
							}
						}
					}
				}
				return sk.toString() + sv.toString();
			}else if( k.equals(v) ) {
				return "ID";
			}else {
				int height = 0;
				for( int i = 0; i < k.size(); i++ ) {
					String[] s = k.get(i).toString().split( "\n" );
					if( s.length > height ) height = s.length;
				}
				StringBuffer sb = new StringBuffer();
				StringBuffer[] sbarr = new StringBuffer[height];
				for( int l = 0; l < sbarr.length; l++ ) {
					sbarr[l] = new StringBuffer();
				}
				for( int i = 0; i < k.size(); i++ ) {
					String[] str = k.get(i).toString().split( "\n" );
					boolean b = false;
					for( int j = 0; j < height; j++ ) {
						if( sbarr[j].indexOf( str[j] ) == -1 ) b = true;
					}
					if( i != 0 && !b ) continue;
					K index = k.get(i);
					if( index.equals( get( index ) ) ) continue;
					String[] s = index.toString().split( "\n" );
					for( int j = 0; j < height; j++ ) {
						if( j == height/2 ) sbarr[j].append( "( " + s[j] + " " );
						else sbarr[j].append( "  " + s[j] + " " );
					}
					do {
						String[] s1 = get( index ).toString().split( "\n" );
						for( int j = 0; j < height; j++ ) {
							if( j == height/2 ) sbarr[j].append( s1[j] + " " );
							else sbarr[j].append( s1[j] + " " );
						}	
						index = (K) get( index );
					} while ( !get(index).equals(k.get(i)) );
					for( int j = 0; j < height; j++ ) {
						if( j == height/2 ) sbarr[j].append( ")" );
						else sbarr[j].append( " " );
					}	
				}
				for( int j = 0; j < height; j++ ) {
					sb.append( sbarr[j] );
					if( j != height-1 ) sb.append( "\n" );
				}	
				return sb.toString();
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean equals( Object obj ) {
		if( !(obj instanceof Mapping) ) return false;
		Mapping<K, V> map = ( Mapping )obj;
		return k.containsAll( map.k ) && v.containsAll( map.v ) && v.equals( map.get(k) );
	}
	
}
