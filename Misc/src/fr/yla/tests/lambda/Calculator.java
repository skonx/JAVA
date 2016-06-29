package fr.yla.tests.lambda;

import java.util.function.IntBinaryOperator;
/**
 * Signs and evaluation expression are defined for each enum.
 * @author jsie
 */
public class Calculator {
	private enum Operator{
		A('+',(a,b) -> a+b),
		S('-',(a,b) -> a-b),
		D('/',(a,b) -> a/b),
		M('*',(a,b) -> a*b),
		MOD('%',(a,b) -> a%b);
		
		private final char sign;
		private final IntBinaryOperator op;

		Operator (char sign, IntBinaryOperator op){
			this.sign = sign;
			this.op = op;
		}

		void apply(int a, int b) {
			System.out.println(a+" "+sign+" "+b+" = "+op.applyAsInt(a, b));
		}

		void negateApply(int a, int b){
			apply(b, a);
		}
	}

	public static void main(String[] args) {

		Operator.A.apply(40,2);
		Operator.S.apply(20, 10);
		Operator.S.negateApply(20,10);
		Operator.M.apply(10, 20);
		Operator.D.apply(20, 9);
		Operator.D.apply(140, 2);
		Operator.MOD.apply(20, 9);
		Operator.D.apply(40, 2);
	}
}