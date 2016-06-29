package fr.yla.tests.misc;

public class DualIncrCompute {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int sum = 0;
		for(int i=0; i<Integer.MAX_VALUE; i++) 
			for(int j=0; j<Integer.MAX_VALUE; j++) 
				sum++; 

		long stop = System.currentTimeMillis() - start;
		System.out.println(Integer.MAX_VALUE+"^2 = "+sum+" in "+stop+" ms");
	}

}
