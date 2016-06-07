package fr.yla.tests.generics;

public class B extends A {
	public <U extends B> void z(U u) {
		System.out.println("B");
	}
	public static void main(String[] args) {
		A b=new B();
		b.z(b);//will print A. Borns are differents. We should recommand to use U extends A on void method instead U extends B if we really wand to execule B's method.
	}
}
