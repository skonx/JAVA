package fr.yla.tests.polymorphism;

public class A {
	void display(A a){
		System.out.println("A.display(A)");
	}

	public static A createA(){
		return new A();
	}
}
