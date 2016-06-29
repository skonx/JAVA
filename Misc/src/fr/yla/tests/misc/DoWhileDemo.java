package fr.yla.tests.misc;

public class DoWhileDemo {
	//demonstrate that with a do-while loop, you will always iterate for a first time.
	public static void main(String[] args){
		int count = 1;
		do {
			System.out.println("Count is: " + count);
			count++;
		} while (count < 0);
		System.out.println("Count is: " + count);
	}
}
