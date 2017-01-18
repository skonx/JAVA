/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.yla.tests.polymorphism.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jsie
 */
public class G {

    public static void main(String[] args) {
        //implements H in an anonymous class and execute display() method
        H h = new H() {
            @Override
            public void display(String message) throws Exception {
                throw new Exception("Thrown"); //To change body of generated methods, choose Tools | Templates.
            }
        };
        try {
            h.display("Hello World");
        } catch (Exception ex) {
            Logger.getLogger(G.class.getName()).log(Level.SEVERE, ex.
                    getMessage());
        }
    }
}
