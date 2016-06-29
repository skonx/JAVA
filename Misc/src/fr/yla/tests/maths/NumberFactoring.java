package fr.yla.tests.maths;

import static java.lang.Math.sqrt;
import static java.lang.Math.floor;

public class NumberFactoring {

	public static void main(String[] args) {

		if(args.length == 1){
			double value = Double.parseDouble(args[0]);
			findFactorsAndDisplay(value);
		}
		else
			System.err.println("Specify a integer on the command line...");
	}
	
	public static void findFactorsAndDisplay(double value){
		display(findFactors(value));
	}
	
	private static void display(double[] result){
		if(result[1] == -1)
			System.out.println(String.format("%.0f", result[0])+" is a prime number and can not be factorized ! Try a greater number instead...");
		else
			System.out.println(String.format("%.0f", result[0])+" = "+String.format("%.0f", result[1])+" x "+String.format("%.0f", result[2]));
	}
	
	public static double[] findFactors(double value){
		double l,b;
		double[] result = {value,-1,-1};

		l = floor(sqrt(value));

		while(value%l != 0)
			l++;

		if(l != value){
			b = value/l;
			result[0] = value;
			result[1] = l;
			result[2] = b;
		}

		return result;
	}
}
