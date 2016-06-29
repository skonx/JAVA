package fr.yla.mt.visitor.swing;

import java.awt.Color;

import fr.yla.mt.core.MTComposite;
import fr.yla.mt.visitor.MTVisitor;

public abstract class AbstractSwingMTVisitor implements MTVisitor {
	private Color bgColor;
	private Color fgColor;
	
	AbstractSwingMTVisitor(Color bgColor, Color fgColor) {
		super();
		this.bgColor = bgColor;
		this.fgColor = fgColor;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public Color getFgColor() {
		return fgColor;
	}

	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
	}
	
	@Override
	public void visit(MTComposite mtc) {
		mtc.stream().forEach(mt->mt.accept(this));
	}
	
}
