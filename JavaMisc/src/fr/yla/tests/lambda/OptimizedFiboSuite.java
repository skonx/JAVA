package fr.yla.tests.lambda;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class OptimizedFiboSuite {

	private static class MyString {

		private final String s;

		public MyString(String s){
			this.s = s;
		}

		public MyString(){
			s = null;
		}

		@SuppressWarnings("unused")
		public String getS(){
			return s;
		}

		public int length(String s){
			return s.length();
		}
		public int length(){
			return s.length();
		}
	}

	private final List<Long> fiboValues;
	private final static String USAGE = "Usage : tests.OptimizedFiboSuite <integer>";

	OptimizedFiboSuite (int capacity){
		fiboValues = new ArrayList<Long>(capacity+1);
		fiboValues.add(1l);
		fiboValues.add(1l);
	}

	private long fiboCompute(long value) {
		if ( (value == 0) || (value == 1))
			return 1;
		else{
			if(fiboValues.size()<=value)
				fiboValues.add(fiboCompute(value - 2) + fiboCompute(value - 1));
			return fiboValues.get((int)value);
		}
	}

	private void printAll(){
		//fiboValue.forEac
		fiboValues.forEach(v -> System.out.println(fiboValues.indexOf(v)+":"+v));
	}

	private Long[] filterSmallerThan(long v){
		Predicate<Long> predicate = a -> (a > v);
		//we will use the negative of the predicate !!!
		//return fiboValues.stream().filter(predicate.negate()).distinct().map(Math::negateExact).sorted(Comparator.comparingLong(a->-a)).collect(Collectors.toList());
		return fiboValues.stream().parallel().filter(predicate.negate()).distinct().map(Math::negateExact).sorted(Comparator.comparingLong(a->-a)).toArray(Long[]::new);
	}

	private Long filterSmallerThanAndSumAll(long v){
		Predicate<Long> predicate = a -> (a > v);
		return fiboValues.stream().parallel().filter(predicate.negate()).distinct().map(Math::negateExact).sorted(Comparator.comparingLong(a->-a)).reduce(0l, (a,b)->a+b);
	}

	public static void main(String[] args) { 

		if (args.length == 1){
			System.out.println(args.length+" args : and first one is "+args[0]); 

			long value = Long.parseLong(args[0]); 

			OptimizedFiboSuite suite = new OptimizedFiboSuite((int)value);

			for (long i=0;i<=value;i++){ 
				long startTime = System.currentTimeMillis();
				System.out.print("Fibo("+i+") = "+suite.fiboCompute(i)); 
				long elapseTime = System.currentTimeMillis() - startTime; 
				System.out.println(" =>> "+elapseTime+ " ms elapsed."); 
			}

			OptimizedFiboSuite suite2 = new OptimizedFiboSuite((int)value);
			System.out.println("*** Last test ***"+" List size = "+suite2.fiboValues.size());
			long startTime = System.currentTimeMillis();
			System.out.print("Fibo("+value+") = "+suite2.fiboCompute(value)); 
			long elapseTime = System.currentTimeMillis() - startTime; 
			System.out.println(" =>> "+elapseTime+ " ms elapsed.");
			System.out.println("*** Last test ***"+" List size = "+suite2.fiboValues.size());

			suite2.printAll();

			for(Long l : suite2.filterSmallerThan(10000))
				System.out.println(l);

			List<Long> suite3 = Arrays.asList(suite2.filterSmallerThan(10000l));
			//suite3.stream().forEach(suite3::add);
			//e -> Math.incrementExact(e)
			suite3.forEach(Math::incrementExact);
			//e -> e.intValue();
			suite3.forEach(Long::intValue);
			//e -> System.out.println(e);
			suite3.forEach(System.out::println);
			System.out.println("filterSmallerThanAndSumAll = "+suite2.filterSmallerThanAndSumAll(10000l));

			String hello = "hello";
			Predicate<String> predicate = hello::equals;
			System.out.println(predicate.test("bonjour"));	

			ToIntFunction<String> fun = Integer::parseInt;
			System.out.println(fun.applyAsInt("3"));
			ToIntFunction<String> fun2 = String::length;
			//is equivalent to ToIntFunction<String> fun2 = s -> s.length() which is the same for than fun3 lambda expression
			System.out.println(fun2.applyAsInt(hello));

			MyString s = new MyString();
			//will find length(String) method in MyString class
			ToIntFunction<String> fun3 = s::length;
			System.out.println(fun3.applyAsInt("hello"));
			//will find length() method in MyString class
			MyString s2 = new MyString(hello);
			ToIntFunction<MyString> fun4 = MyString::length;
			System.out.println(fun4.applyAsInt(s2));
		}
		else
			System.err.println(USAGE);
	}

}
