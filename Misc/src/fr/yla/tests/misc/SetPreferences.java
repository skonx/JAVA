package fr.yla.tests.misc;

import java.util.prefs.*;
public class SetPreferences {

	private final static String USAGE = "Usage : java SetPreferences [ <-d> <-u | -s> <class> ] | [ <-u | -s> <class> <pref> <value> ]";

	private static void clearMyWork(Preferences prefs) throws BackingStoreException{
		prefs.clear();
		prefs.removeNode();
		prefs.flush();
	}

	private static void addPreferences(String pref, String value, Preferences prefs) throws BackingStoreException{
		System.out.println("Absolute path = "+prefs.absolutePath());
		System.out.println(prefs);

		//get the latest value
		String oldValue = prefs.get(pref, null);

		if (oldValue != null)
			System.out.println("Old value : "+oldValue);
		else
			System.out.println("Old value is empty");
		//add or replace the value 
		prefs.put(pref, value);

		System.out.println("Value of "+pref+" is now : "+value);
		//commit the update
		prefs.flush();

		//show the key/value contained
		String[] keys = prefs.keys();

		System.out.println("=> All key/value inventory");

		for (int i = 0; i<keys.length ; i++){

			String val = prefs.get(keys[i], null);

			if (val != null)
				System.out.println("Value of "+keys[i]+" = "+val);
			else
				System.out.println("Old value is empty");

			//prefs.put(keys[i], val);

		}
	}

	public static void main(String[] args) {
		switch(args.length){
		case 3:
			if(args[0].contains("-d")){
				try {
					switch(args[1]){
					case "-u":
						clearMyWork(Preferences.userNodeForPackage(Class.forName(args[2])));
						break;
					case "-s":
						clearMyWork(Preferences.systemNodeForPackage(Class.forName(args[2])));
						break;
					default:
						System.err.println(USAGE);
						System.exit(1);
					}
				} catch (ClassNotFoundException | BackingStoreException e) {
					e.printStackTrace();
				}
			}
			else{
				System.err.println(USAGE);
				System.exit(1);
			}
			break;
		case 4:
			try {
				switch(args[0]){
				case "-u":
					addPreferences(args[2],args[3],Preferences.userNodeForPackage(Class.forName(args[1])));
					break;
				case "-s":
					addPreferences(args[2],args[3],Preferences.systemNodeForPackage(Class.forName(args[1])));
					break;
				default:
					System.err.println(USAGE);
					System.exit(1);
				}
			} catch (ClassNotFoundException | BackingStoreException e) {
				e.printStackTrace();
			}
			break;
		default:
			System.err.println(USAGE);
			System.exit(1);
		}
	}
}
