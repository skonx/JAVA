package fr.yla.tests.generics;

public class A {
	public <T extends A> void z(T t) {
        System.out.println("A");
      }
}
