/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.yla.tests.maths;

import java.util.stream.IntStream;

/**
 *
 * @author jsie
 */
public class PrimeNumber {

    public static boolean isPrime(int number) {
        return number == 1
                || number == 2
                || (number > 2
                && IntStream.rangeClosed(2, (int) Math.sqrt(number))
                        .noneMatch(n -> (number % n == 0)));
    }
}
