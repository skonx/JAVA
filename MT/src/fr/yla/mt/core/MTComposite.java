package fr.yla.mt.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import fr.yla.mt.visitor.MTVisitor;

public class MTComposite extends AbstractMT {

	private static final long serialVersionUID = 2946036073076109930L;

	public final static int default_size = 500;

	private final List<MultiplicationTable> mts;

	/**
	 * Create a MTComposite with a synchronized list
	 * @param size the size of the synchronized list, if size is less-equal to 0 or greater than Integer.MAX_VALUE, default_size is used instead.
	 */
	MTComposite(int size) {
		if(size <= 0 || size > Integer.MAX_VALUE)
			mts = Collections.synchronizedList(new ArrayList<>(default_size));
		else
			mts = Collections.synchronizedList(new ArrayList<>(size));
	}

	MTComposite() {
		mts = Collections.synchronizedList(new ArrayList<>());
	}

	@Override
	public String toString(){
		if(isEmpty())
			return "- No matrix to print -";

		StringBuilder sb = new StringBuilder();
		if (mts != null)
			for (MultiplicationTable mt : mts)
				sb.append(mt+"\n");
		return sb.toString();
	}

	public boolean isEmpty(){
		if(mts == null)
			return true;

		return mts.isEmpty();
	}

	public int size(){
		return mts.size();
	}

	public MultiplicationTable getAt(int index){
		return mts.get(index);
	}

	/**
	 * Prohibits null elements!
	 * @param index position to store the Multiplication Table
	 * @param mt the MultiplicationTable to insert
	 * @return the original value contained in the structure
	 */
	boolean add(MultiplicationTable mt){
		if(mt == null)
			return false;

		return mts.add(mt);
	}

	MultiplicationTable setAt(int index, MultiplicationTable mt){
		return mts.set(index, mt);
	}

	void clear(){
		System.out.println("## Clear the matrix ##");
		mts.clear();
	}

	public Stream<MultiplicationTable> stream(){
		return mts.stream();
	}

	@Override
	public void accept(MTVisitor v) {
		v.visit(this);
	}


}
