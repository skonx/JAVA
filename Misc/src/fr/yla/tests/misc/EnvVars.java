package fr.yla.tests.misc;

public class EnvVars {

	public static void main(String[] args) {
		System.getProperties().list(System.out);
		
		System.out.println("\nJAVA HOME = "+System.getProperty("java.home"));
		System.out.println("JAVA VERSION = "+System.getProperty("java.version"));
	}
}
