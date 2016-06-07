package fr.yla.mt.visitor.javafx;

import java.util.function.Function;

import javafx.scene.control.TabPane;

public enum MTVisitorTypes {
	TEXT(MTVisitorDisplayInTextArea::new),
	TABLE(MTVisitorDisplayInTableView::new),
	GRIDTF(MTVisitorDisplayInGridPaneTF::new),
	GRIDTX(MTVisitorDisplayInGridPaneTX::new);

	private final Function<TabPane, AbstractJavaFXMTVisitor> creator;

	private MTVisitorTypes(Function<TabPane, AbstractJavaFXMTVisitor> creator) {
		this.creator = creator;
	}

	public AbstractJavaFXMTVisitor create(final TabPane tabPane){
		return creator.apply(tabPane);
	}
}