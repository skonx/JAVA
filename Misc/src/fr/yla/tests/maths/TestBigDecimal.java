/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.yla.tests.maths;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author jsie
 */
public class TestBigDecimal {

    public static void main(String[] args) {
        int price = 1500;
        BigDecimal vtarate = new BigDecimal("5.5");
        int netprice = (new BigDecimal(price).multiply(vtarate.add(
                new BigDecimal(100)))).divide(new BigDecimal(100)).setScale(2,
                RoundingMode.HALF_UP).intValue();
        System.out.println(netprice);
    }
}
