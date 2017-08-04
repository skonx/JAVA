package fr.yla.tests.misc;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EnvVars {

    public static void main(String[] args) {
        //System.getProperties().list(System.out);
        try {
            while (true) {

                System.out.println("\nJAVA HOME = " + System.
                        getProperty("java.home"));
                System.out.println("JAVA VERSION = " + System.
                        getProperty("java.version"));

                Thread.sleep(1000);

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(EnvVars.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
}
