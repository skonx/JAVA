package fr.yla.mt.core;

import java.security.SecureRandom;

public class MTFactory {

	/**
	 * Create and initialize a BasicMT
	 * @param cap1 number of rows & columns (square matrix)
	 * @return the initialized BasicMT
	 */
	public static MultiplicationTable createBasicMultiplicationTable(int cap){
		BasicMT mt = new BasicMT(cap);
		int[][] array = mt.getArray();

		for(int i = 0; i<cap ; i++)
			for(int j = 0; j<cap; array[i][j] = (i+1)*(++j));
		return mt;
	}

	/**
	 * Create and initialize a MirroredMT
	 * @param cap1 number of rows & columns (square matrix)
	 * @return the initialized MirroredMT
	 */
	public static MultiplicationTable createMirroredMultiplicationTable(int cap){
		MirroredMT mt = new MirroredMT(cap);
		int[][] array = mt.getArray();

		for(int i = 0; i<cap ; i++)
			for(int j = 0; j<=i; array[j][i] = array[i][j] = (i+1)*(++j));
		return mt;
	}

	/**
	 * Create and initialize a OptimizedInitMT
	 * @param cap1 number of rows & columns (square matrix)
	 * @return the initialized OptimizedInitMT
	 */
	public static MultiplicationTable createOptimizedInitMultiplicationTable(int cap){
		OptimizedInitMT mt = new OptimizedInitMT(cap);
		int[][] array = mt.getArray();

		for(int i = 0; i<cap ; i++)
			for(int j = 0; j<=i; j++)
				array[i][j] = (i+1)*(j+1);

		return mt;
	}
	/**
	 * <pre>
	 * Create a MTComposite (composite of multiplication tables) randomly using the MultiplicationTable types 
	 * defined in the <i>enum</i> MTTypes.
	 * Multi-threading used on a thread-safe structure : calls the factory methods in order to create and initialize a single multiple table, add it to the composite and die.
	 * </pre>
	 * @param cap number of rows & columns (square matrix)
	 * @param size amount of MultiplicationTable to create and add to the composite.
	 * @return the initialized MTComposite with <i>size</i> different MultiplicationTables.
	 * @throws InterruptedException if a thread is interrupted
	 */
	public static MTComposite generateRandomMultiplicationTables(int cap, int size) throws InterruptedException{
		MTComposite mtc = new MTComposite(size);

		MTTypes[] types = MTTypes.values();

		SecureRandom random = new SecureRandom();

		Thread[] threads = new Thread[size];

		for(int i = 0; i<size ; i++){
			threads[i] = new Thread( () -> mtc.add(types[random.nextInt(types.length)].create(cap)));
			threads[i].start();
		}

		//check that all threads are dead
		for(Thread t : threads)
			t.join();
		
		return mtc;
	}
}
