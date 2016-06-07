package fr.yla.tests.polymorphism;

public class C extends B {
	
	public void display(A a){
		System.out.println("C.display(A)");
	}
	public void display(C c){
		System.out.println("C.display(C)");
	}
}
