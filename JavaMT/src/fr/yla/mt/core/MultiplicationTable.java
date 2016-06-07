package fr.yla.mt.core;

import java.io.Serializable;

import fr.yla.mt.visitor.MTVisitor;

public interface MultiplicationTable extends Serializable {
	public final static int default_cap = 100;
	
	public void accept(MTVisitor v);
}
