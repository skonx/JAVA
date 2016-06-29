/**
 * Multiplication Table Generator, based on multi-threading, streams, lambda, generics and function references ;)
 */
package fr.yla.mt.core;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;


class MTGenerator {
	private final static String USAGE = "USAGE : java fr.yla.tests.mt.MTGenerator <cap>\n "
			+ "USAGE : java fr.yla.tests.mt.MTGenerator <cap> <size for automatic creation>\n"
			+ "USAGE : java fr.yla.tests.mt.MTGenerator";

	private final static Scanner scanner = new Scanner(System.in);

	private static void displayAll(MTComposite mtc){

		System.out.print(">> Do you want to print the table(s)? (yes or no): ");

		String answer = "no";

		answer = scanner.nextLine();

		if (answer.toLowerCase().equals("yes"))
			System.out.println(mtc);			
	}

	public static void main(String[] args){

		try {
			if(args.length!=2 && args.length!= 1 && args.length!=0){
				System.err.println(USAGE);
				System.exit(1);
			}

			int cap = MultiplicationTable.default_cap;

			if(args.length>=1)
				cap = Integer.parseInt(args[0]);

			int size = MTComposite.default_size;

			if(args.length == 2)
				size = Integer.parseInt(args[1]);

			System.out.println("Let's allocate and initiate the "+size+" matrix...");

			MTComposite mtc = MTFactory.generateRandomMultiplicationTables(cap,size);

			MTSaver<ObjectOutputStream> saver = MTSaver.createMTSaver(ObjectIOStreamWrapper.Out::ObjectOutputStreamFactory, ObjectIOStreamWrapper.Out::writeMultiplicationTableInObjectOutputStream, ObjectIOStreamWrapper.Out::closeObjectOutputStream);
			saver.save(mtc);

			displayAll(mtc);
			mtc.clear();
			displayAll(mtc);

			MTLoader<ObjectInputStream> loader = MTLoader.createMTLoader(ObjectIOStreamWrapper.In::ObjectInputStreamFactory, ObjectIOStreamWrapper.In::readMultiplicationTableFromObjectInputStream, ObjectIOStreamWrapper.In::closeObjectInputStream);
			mtc = loader.loadIntoComposite();

			displayAll(mtc);

			scanner.close();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

}
