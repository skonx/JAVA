/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;

/**
 *
 * @author jsie
 */
public class TimeConverterTest {
    
    public TimeConverterTest() {
    }
    
    @Test
    public void testConvertMilliSecondsToString() {
        final long time = 23567889l;
        long t = time;
        
        StringBuilder sb = new StringBuilder();
        
        String[] units = new String[]{"ms", "sec", "min", "hour", "Day", "Week", "Month", "Year"};
        
        Map<String, Long> bases = new TreeMap<>();
        bases.put(units[0], 1l);
        bases.put(units[1], 1l * 1000);
        bases.put(units[2], 1l * 1000 * 60);
        bases.put(units[3], 1l * 1000 * 60 * 60);
        bases.put(units[4], 1l * 1000 * 60 * 60 * 24);
        bases.put(units[5], 1l * 1000 * 60 * 60 * 24 * 7);
        bases.put(units[6], 1l * 1000 * 60 * 60 * 24 * 30);
        bases.put(units[7], 1l * 1000 * 60 * 60 * 24 * 365);
        
        Map<String, Long> results = new TreeMap<>();
        
        assert bases.isEmpty() != true;
        assert bases.size() == units.length;
        
        for (int u = units.length - 1; u >= 0; u--) {
            long v = t / bases.get(units[u]);
            t %= bases.get(units[u]);
            results.put(units[u], v);
            if (v != 0) {
                sb.append(v).append(" ").append(units[u]).append(" ");
            }
        }
        
        System.out.println(time + " ms => " + sb.toString());
        
        assert results.size() == bases.size();
        
        long result = 0;
        
        for (int u = 0; u < units.length; u++) {
            result += results.get(units[u]) * bases.get(units[u]);
        }
        
        assert result == time;
        
    }
}
