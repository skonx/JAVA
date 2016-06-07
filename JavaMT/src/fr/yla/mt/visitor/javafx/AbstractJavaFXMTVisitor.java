package fr.yla.mt.visitor.javafx;

import fr.yla.mt.core.MTComposite;
import fr.yla.mt.visitor.MTVisitor;
import javafx.scene.paint.Color;

public abstract class AbstractJavaFXMTVisitor implements MTVisitor {

	private Color bgcolor;
	private Color fgcolor;

	/**
	 * a flag which allows (or not) to use a display visitor for blended generators.
	 */
	boolean allowBlended;

	public AbstractJavaFXMTVisitor(Color bgcolor, Color fgcolor) {
		this.bgcolor = bgcolor;
		this.fgcolor = fgcolor;
	}

	public Color getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(Color bgcolor) {
		this.bgcolor = bgcolor;
	}

	public Color getFgcolor() {
		return fgcolor;
	}

	public void setFgcolor(Color fgcolor) {
		this.fgcolor = fgcolor;
	}

	public boolean allowBlended() {
		return allowBlended;
	}


	@Override
	public void visit(MTComposite mtc) {
		mtc.stream().forEach(mt->mt.accept(this));
	}
/**
* Returns true if the Display Mode is allowed with Blended MT creation
*/
	public boolean isBlendedAllowed(){
		return allowBlended;
	}
}
