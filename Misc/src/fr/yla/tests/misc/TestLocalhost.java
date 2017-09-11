/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.yla.tests.misc;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jsie
 */
public class TestLocalhost {

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();
            InetAddress localhost = InetAddress.getLocalHost();
            long end = System.currentTimeMillis();
            System.out.println("Started at " + start + "(ms) : " + new Date(
                    start));
            System.out.println("Stopped at " + end + "(ms) : " + new Date(end));
            System.out.println("===> " + (end - start) + "(ms)");
        } catch (UnknownHostException ex) {
            Logger.getLogger(TestLocalhost.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

}
