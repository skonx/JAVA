package fr.yla.tests.polymorphism;

public class D extends A {

	private static int value = 0;
	
	public void display(A a){
		System.out.println("value = "+(++value));
	}

}
