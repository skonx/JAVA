package fr.yla.tests.misc;

public class FiboSuite {

	public static long fiboComputeStatic (long value){
		if ((value == 0) || (value == 1))
			return 1;
		else
			return (fiboComputeStatic(value-2)+fiboComputeStatic(value-1));
	}

	public long fiboCompute (long value){
		if ((value == 0) || (value == 1))
			return 1;
		else
			return (fiboCompute(value-2)+fiboCompute(value-1));
	}

	public static void main(String[] args) {
		if (args.length == 1){
			System.out.println(args.length+" args : and first one is "+args[0]);
			long value = Long.parseLong(args[0]);
			System.out.println("*** Using static method ***");
			for (long i=0;i<=value;i++){
				long startTime = System.currentTimeMillis();	
				System.out.print("Fibo("+i+") = "+fiboComputeStatic(i));
				long elapseTime = System.currentTimeMillis() - startTime;
				System.out.println(" =>> "+elapseTime+ " ms elapsed.");
			}

			FiboSuite suite = new FiboSuite();

			System.out.println("### Using non static method ###");
			for (long i=0;i<=value;i++){
				long startTime = System.currentTimeMillis();	
				System.out.print("Fibo("+i+") = "+suite.fiboCompute(i));
				long elapseTime = System.currentTimeMillis() - startTime;
				System.out.println(" =>> "+elapseTime+ " ms elapsed.");
			}

		}
	}	
}
