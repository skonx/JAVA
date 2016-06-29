/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import org.junit.Ignore;

/**
 *
 * @author jsie
 */
public class MyExceptionTest {

    public MyExceptionTest() {
    }

    @Test
    public void testExceptionThrown() {
        try {
            start();
        } catch (Exception e) {
            System.err.println("Exception catched in testExceptionTrown()");
        }
    }

    @Ignore
    private void start() {
        try {
            if (true) {
                throw new Exception("BOOM");
            }
        } catch (Exception e) {
            assert e != null;
            System.err.println("Exception catched in start(): " + e.getMessage());
        }
    }
}
