package fr.yla.tests.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class WellDisciplinedToken {

	private static int value = 0;
	private static int max_active_threads = 0;
	private static int max_threads = 2000;
	private final static Map<Integer,Integer> token_map = new HashMap<>(max_threads);

	private final static void suspendMainThread(){

		while(Thread.activeCount() != 1)
			try {
				Thread.sleep(1000);
			} catch (OutOfMemoryError | Exception e) {
				//do nothing
			}
		System.out.println("Finally, value = "+value+" and max_active_threads = "+max_active_threads);
	}

	public static void main(String[] args) {
		try{
			int timer = 15;

			System.out.print("Wait "+timer+"s before starting...\r");
			while(timer-- > 0){
				Thread.sleep(1000);
				System.out.print("Wait "+timer+"s before starting...  \r");	
			}

			if(args.length == 1)
				max_threads = Integer.parseInt(args[0]);

			System.out.println();IntStream.range(0, max_threads).forEach(i-> new Thread(()->{

				try {
					synchronized (token_map) {
						while(i != value)
							token_map.wait();
						//System.out.println(">>increment>> "+Thread.currentThread().getName()+" - i  is = "+i+" - value is = "+value);
						token_map.put(i, value);
						value++;
						max_active_threads = (max_active_threads<Thread.activeCount())?Thread.activeCount():max_active_threads;
						token_map.notifyAll();
					}

				}
				catch (Exception e) {
					e.printStackTrace();
				}
			},"jsie-test-"+i).start());


			suspendMainThread();

			System.out.print("Non coherent value : ");

			Predicate<Integer> p = e->token_map.get(e).equals(e);

			long count = token_map.keySet().stream().filter(p.negate()).count();
			System.out.println(count);
			if(count != 0)
				token_map.keySet().stream().filter(p.negate()).forEach(e->System.out.println("Thread#"+e+" : "+token_map.get(e)));

		} catch (OutOfMemoryError | Exception e) {
			System.err.println("OutOfMemoryError | Exception catched");
			suspendMainThread();
		}
	}

}
