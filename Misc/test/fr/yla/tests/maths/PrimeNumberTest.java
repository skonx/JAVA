/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.yla.tests.maths;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 *
 * @author jsie
 */
public class PrimeNumberTest {

    public PrimeNumberTest() {
    }

    /**
     * Test of isPrime method, of class PrimeNumber.
     */
    @Test
    public void testIsPrime() {
        int n = 100;

        List<Integer> pn = Arrays.asList(1,
                2,
                3,
                5,
                7,
                11,
                13,
                17,
                19,
                23,
                29,
                31,
                37,
                41,
                43,
                47,
                53,
                59,
                61,
                67,
                71,
                73,
                79,
                83,
                89,
                97);

        assert IntStream.rangeClosed(1, n)
                .filter(PrimeNumber::isPrime)
                .peek(System.out::println)
                .allMatch(i -> pn.contains(i));
    }

}
