package fr.yla.mt.core;
/**
 * <pre>
 * Regarding the fact that our Multiplication Tables are square matrix,
 * A MirroredMT is a Multiplication Table matrix initialized mirroring the computed value of a couple row/column in its mirrored cell column/row.
 * Ex: value of row 5, column 2 if the same in row 2, column 5 : 5 x 2 = 2 x 5 = 10.
 * Initialization is performed only in the MTFactory !!!
 * </pre>
 * @author jsie
 *
 */
public class MirroredMT extends AbstractMT{

	private static final long serialVersionUID = 3935159864120073258L;

	MirroredMT(int capacity) {
		super(capacity);
	}

	MirroredMT(){
		super();
	}
}
