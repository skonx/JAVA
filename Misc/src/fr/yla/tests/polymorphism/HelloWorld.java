package fr.yla.tests.polymorphism;

import java.util.ArrayList;
import java.util.List;

public class HelloWorld {

	public static void main(String[] args) {
		System.out.println("Let's play with Polymorphism and ENUMs");
		A a = new A();
		A b = new B();
		A c = new C();
		A d = new D();

		List<A> list = new ArrayList<A>();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		
		for(A x : list)
			for(A y : list)
				x.display(y);
		
		System.out.println("Enum names =");
		for (DirectionEnum directionEnum : DirectionEnum.values())
			System.out.println(directionEnum.name());
		
		A[] array = new A[list.size()];
		for(int i=0;i<list.size();i++)
			array[i] = list.get(i);
		
		for(A elt : array)
			System.out.println(elt);
		
		array[1] = null;
		
		for(A elt : array)
			System.out.println(elt);
		
		list.set(1, null);
		
		list.forEach(System.out::println);
                
                a.display(new String());
		
	}

}
