package fr.yla.mt.visitor.swing;

import java.util.function.Function;

import javax.swing.JTabbedPane;

public enum MTVisitorTypes {
	TEXT(MTVisitorDisplayInJTextArea::new),
	TABLE(MTVisitorDisplayInJTable::new);

	private final Function<JTabbedPane, AbstractSwingMTVisitor> creator;

	private MTVisitorTypes(Function<JTabbedPane, AbstractSwingMTVisitor> creator) {
		this.creator = creator;
	}

	public AbstractSwingMTVisitor create(final JTabbedPane tabbedPane){
		return creator.apply(tabbedPane);
	}
}