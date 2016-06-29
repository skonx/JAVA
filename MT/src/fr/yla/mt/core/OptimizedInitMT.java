package fr.yla.mt.core;

import fr.yla.mt.visitor.MTVisitor;
/**
 * <pre>
 * Regarding the fact that our Multiplication Tables are square matrix, it's possible to initialize only half of the matrix.
 * Half of the matrix will be setup with 0 value (should be improved).
 * Keep in mind that initialization can only be performed by the MTFactory.
 * </pre>
 * @author jsie
 *
 */
public class OptimizedInitMT extends AbstractMT{

	private static final long serialVersionUID = -4350612876735785205L;

	OptimizedInitMT(int capacity) {
		super(capacity);
	}

	OptimizedInitMT() {
		super();
	}
	/**
	 * This MT requires to override the default toString method provided by the Abstract class.
	 */
	@Override public String toString(){
		StringBuilder sb = new StringBuilder();
		int cap = super.getCapacity();

		sb.append("*** x TABLE "+cap+" / "+cap+" ["+getClass().getSimpleName()+"] ***\n");

		int[][] array = super.getArray();

		for(int i = 0;i<cap;i++){
			sb.append("\n");
			for(int j = 0;j<cap;j++){
				int value = array[i][j];

				if(j>i)
					value = array[j][i];

				int valuesize = Integer.toString(value).length();

				for(int s = 0;s<(super.getCellSize() - valuesize);s++)
					sb.append(" ");
				sb.append(value);
			}		
		}
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public void accept(MTVisitor v) {
		v.visit(this);		
	}
}
