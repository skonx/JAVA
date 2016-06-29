
package fr.yla.tests.time;

import java.util.Map;
import java.util.TreeMap;

/**
 * Conversion methods for Time
 * Creation : 06/02/2016
 * @version 1.0
 * @author jsie
 */
public class TimeConverter {

    private TimeConverter() {
        //Garbage constructor
    }

    /**
     * <pre>
     * Convert a specified time in ms to a human readable String.
     * Ex: 23567889 ms will be translated into : 6 hour 32 min 47 sec 889 ms
     * </pre>
     * @param time the time to convert
     * @return a String representation, return O ms if time value equals 0
     * @throws IllegalArgumentException if time value is less than 0
     */
    public static String convertMilliSecondsToString(final long time){
        long t = time;
        
        if(t < 0l)
            throw new IllegalArgumentException(t+" is not a valid time");

        if (t == 0l) {
            return "0 ms";
        }

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

        for (int u = units.length - 1; u >= 0; u--) {
            long v = t / bases.get(units[u]);
            t %= bases.get(units[u]);
            if (v != 0) {
                sb.append(v).append(" ").append(units[u]).append(" ");
            }
        }

        return sb.toString();
    }
}
