package fr.yla.tests.time;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTest {

	/**
         * <pre>
	 * Enum used for display color on command line.
         * This application perfectly work on NetBeans.
         * It seems that there is an issue with Eclipse, the command line
         * cannot be colorized...</pre>
	 * @author jsie
	 *
	 */
	private static enum PrintStreamEnumColors{
		ANSI_RESET("\u001B[0m"),
		ANSI_BLACK("\u001B[30m"),
		ANSI_RED("\u001B[31m"),
		ANSI_GREEN("\u001B[32m"),
		ANSI_YELLOW("\u001B[33m"),
		ANSI_BLUE("\u001B[34m"),
		ANSI_PURPLE("\u001B[35m"),
		ANSI_CYAN("\u001B[36m"),
		ANSI_WHITE("\u001B[37m");

		private final String colorValue;

		private PrintStreamEnumColors(String colorValue){
			this.colorValue = colorValue;
		}
	}

	/**
	 * Will display the current time during few seconds in different Time Zone
	 * Color the current time zone if your console supports ANSI escape codes
	 * @param args unused
	 * @throws InterruptedException if there is the main Thread is interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		ZonedDateTime now = ZonedDateTime.now();
		String[] zones = new String[] {"Australia/Sydney","Asia/Tokyo","Asia/Hong_Kong","Asia/Dubai","Asia/Tel_Aviv","Europe/Paris","Europe/London","America/New_York","America/Los_Angeles","Pacific/Tahiti"};
		int lap = zones.length;
		ZonedDateTime futur = now.plusSeconds(lap);
		
		String pattern = "EEEE dd MMMM uuuu HH:mm:ss - VV - zzzz - XXX";

		while(now.isBefore(futur)){
			ZoneId zoneId = ZoneId.of(zones[--lap]);
			if(zoneId.equals(now.getZone()))
				System.out.println(PrintStreamEnumColors.ANSI_YELLOW.colorValue+now.withZoneSameInstant(zoneId).format(DateTimeFormatter.ofPattern(pattern, Locale.FRENCH))+PrintStreamEnumColors.ANSI_RESET.colorValue);
			else
				System.out.println(now.withZoneSameInstant(zoneId).format(DateTimeFormatter.ofPattern(pattern, Locale.FRENCH)));

			Thread.sleep(1000);
			now = ZonedDateTime.now();
		}

		/**
		 * Usage examples of java.time classes:
		 * DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd-Hms");
		 * System.out.println(now.isEqual(now.withZoneSameInstant(ZoneId.of(zones[2]))));
		 * Set<String> zoneIds = ZoneId.getAvailableZoneIds();
		 * zoneIds.stream().sorted(String::compareTo).forEach(System.out::println);
		 * System.out.println(zoneIds.stream().count()+ " Time Zones !!!");
		 * 
		 */
	}

}
