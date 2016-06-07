package fr.yla.mt.core;

import fr.yla.mt.visitor.MTVisitor;

public abstract class AbstractMT implements MultiplicationTable {

    /**
     * Automatic serial number for serialization
     */
    private static final long serialVersionUID = 763572561399339788L;

    /**
     * The matrix structure
     */
    private final int[][] array;
    /**
     * The matrix dimension
     */
    private final int capacity;
    /**
     * The number of character in a cell, used for text display
     */
    private final int cellsize;
    /**
     * The space value used to separate the different matrix value
     */
    private final static int space_requirement = 2;

    /**
     * Default constructor, will allocate the matrix using the specified
     * capacity or a default capacity otherwise
     *
     * @param capacity the matrix dimension
     */
    AbstractMT(int capacity) {
        if (capacity <= 0) {
            this.capacity = MultiplicationTable.default_cap;
        } else {
            this.capacity = capacity;
        }

        array = new int[capacity][capacity];
        //the cell size will be the characters of the maximum number calculated + 1 space character
        cellsize = Integer.toString(capacity * capacity).length() + space_requirement;
    }

    /**
     * Will create a Multiplication Table with the defaut matrix dimension
     */
    AbstractMT() {
        this(MultiplicationTable.default_cap);
    }

    /**
     * Returns the matrix dimension
     *
     * @return the matrix dimension
     */
    public final int getCapacity() {
        return capacity;
    }

    /**
     * Returns the computed cell size
     *
     * @return the computed cell size
     */
    public final int getCellSize() {
        return cellsize;
    }

    /**
     * Returns the original matrix (it's not a copy)!
     *
     * @return the matrix
     */
    final int[][] getArray() {
        return array;
    }

    /**
     * Provides an element located in the matrix
     *
     * @param i the row
     * @param j the column
     * @return the element, should be (i+1)*(j+1)
     */
    public final int getAt(int i, int j) {
        return array[i][j];
    }

    /**
     * Provides a string representation of the Multiplication Table
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("*** x TABLE " + capacity + " / " + capacity + " [" + getClass().getSimpleName() + "] ***\n");
        for (int i = 0; i < array.length; i++) {
            sb.append("\n");
            for (int j = 0; j < array[i].length; j++) {
                int valuesize = Integer.toString(array[i][j]).length();
                for (int s = 0; s < (cellsize - valuesize); s++) {
                    sb.append(" ");
                }
                sb.append(array[i][j]);
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Accept method used in the visitor evaluation
     */
    @Override
    public void accept(MTVisitor v) {
        v.visit(this);
    }

}
