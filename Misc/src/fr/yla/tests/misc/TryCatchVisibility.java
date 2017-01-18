/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.yla.tests.misc;

/**
 *
 * @author jsie
 */
public class TryCatchVisibility {

    public static void main(String[] args) {
        String message = "hello";

        try {
            message = provideMsg(false);
        } catch (Exception ex) {
            System.err.println("Exception... " + ex.getMessage());
        } finally {
            System.out.println("Message in finally = " + message);
        }

        System.out.println("Message out of the try/catch = " + message);
    }

    private static String provideMsg(boolean trigger) throws Exception {

        if (trigger) {
            throw new Exception("provideMsg() has thrown an exception");
        }

        return "bye";
    }

}
