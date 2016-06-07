package fr.yla.mt.core;

/**
 * A BasicMT is a Multiplication Table matrix allocated iterating in all matrix
 * cells. Initialization is performed only in the MTFactory !!!
 *
 * @author jsie
 *
 */
public class BasicMT extends AbstractMT {

    private static final long serialVersionUID = -3849340144082210607L;

    BasicMT() {
        super();
    }

    BasicMT(int capacity) {
        super(capacity);
    }
}
