import java.util.ArrayList;
import javam.category.ConcreteCategory;
import javam.category.ConcreteCategory.Morphism;

public class CategoryDemo {
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		
		ArrayList<Integer> arr1 = new ArrayList<>();
		arr1.add(1);arr1.add(2); arr1.add(3);
		ArrayList<Integer> arr2 = new ArrayList<>();
		arr2.add(2);arr2.add(3); arr2.add(4);
		
		ConcreteCategory<ArrayList, Integer> c = new ConcreteCategory<>();
		c.add( arr1, arr2, x -> ++x ).setName( "add_1" );
		c.add( arr2, arr1, x -> --x ).setName( "sub_1" );
		System.out.println( c.get( "add_1", 1 ) );
		for( Morphism mor : c.Mor ) {
			System.out.println( mor );
		}
		
	}
	
}
