/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Optional;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author jsie
 */
public class MyExceptionTest {

    public MyExceptionTest() {
    }

    @Test
    public void testExceptionThrown() {
        final String message = "BOO";
        try {
            start(message);
        } catch (Exception e) {
            assert false;
        }
    }

    @Ignore
    private void start(final String message) {

        try {
            if (true) {
                throw new Exception(message);
            }
        } catch (Exception e) {
            assert e != null;
            assert message.equals(e.getMessage());
        }
    }

    @Test
    /**
     * Test Optional.ofNullable & ifPresent
     */
    public void testExceptionInLambda() {
        Object something = null; //= new String("something");
        final String message = "BOO";
        try {
            Optional.ofNullable(something)
                    .ifPresent(s -> {
                        throw new IllegalStateException(message);
                    });
        } catch (Exception ex) {
            assert ex instanceof IllegalStateException;
        }

        something = new String("something");

        try {
            Optional.ofNullable(something)
                    .ifPresent(s -> {
                        throw new IllegalStateException(message);
                    });
        } catch (Exception ex) {
            assert ex instanceof IllegalStateException;
        }

    }
}
