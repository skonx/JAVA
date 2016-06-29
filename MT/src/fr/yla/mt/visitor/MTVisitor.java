package fr.yla.mt.visitor;

import fr.yla.mt.core.AbstractMT;
import fr.yla.mt.core.MTComposite;
import fr.yla.mt.core.OptimizedInitMT;

public interface MTVisitor {

	public void visit(AbstractMT mt);
	public void visit(OptimizedInitMT mt);
	public void visit(MTComposite mtc);

}
