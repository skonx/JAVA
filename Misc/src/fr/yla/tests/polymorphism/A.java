package fr.yla.tests.polymorphism;

public class A implements I,J {

    void display(A a) {
        System.out.println("A.display(A)");
    }

    public static A createA() {
        return new A();
    }

    @Override
    public void display(String message) {
        System.out.println("A.display(String)");
    }

    @Override
    public void display(Object message) {
        System.out.println("A.display(Object)");
    }
}
