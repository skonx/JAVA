/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.yla.tests.polymorphism;

/**
 *
 * @author jsie
 */
public class MethodCaller {

    public static void main(String[] args) {

        MethodCaller mc = new MethodCaller();

        MyAbstractClass o = mc.new FirstClass();

        //should throw an exception
        System.out.println("method() ==> " + mc.method(o));
    }

    abstract class MyAbstractClass {

    }

    class FirstClass extends MyAbstractClass {

    }

    class SecondClass extends MyAbstractClass {

    }

    public int method(MyAbstractClass o) {
        throw new IllegalStateException("BOOM");
    }

    public int method(FirstClass o) {
        return 1;
    }

    public int method(SecondClass o) {
        return 2;
    }

}
