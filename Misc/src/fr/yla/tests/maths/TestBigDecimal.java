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
        int price = 1502;
        BigDecimal vtarate = new BigDecimal("5.5");
        BigDecimal netprice = (new BigDecimal(price).multiply(vtarate.add(
                new BigDecimal(100)))).divide(new BigDecimal(100)).setScale(0,
                RoundingMode.HALF_UP);
        System.out.println(netprice);

        BigDecimal bg1, bg2;

        bg1 = new BigDecimal("123.12678");

        // set scale of bg1 to 2 in bg2 using floor as rounding mode
        bg2 = bg1.setScale(2, RoundingMode.HALF_UP);

        String str = bg1 + " after changing the scale to 2 and rounding is "
                + bg2;

        // print bg2 value
        System.out.println(str);
    }
}
