package fr.yla.mt.visitor.javafx;

import fr.yla.mt.core.AbstractMT;
import fr.yla.mt.core.OptimizedInitMT;
import fr.yla.mt.gui.swing.MTAPP;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class MTVisitorDisplayInTextArea extends AbstractJavaFXMTVisitor {

	private final TabPane tabPane;

	public MTVisitorDisplayInTextArea(final TabPane tabPane) {
		//should be the same colors than the CSS file
		super(Color.TRANSPARENT,Color.BLACK);
		this.tabPane = tabPane;
		allowBlended = true;
	}

	@Override
	public void visit(AbstractMT mt) {

		int capacity = mt.getCapacity();
		int cellsize = mt.getCellSize();

		StringBuilder sb = new StringBuilder();

		sb.append("*** x TABLE "+capacity+" / "+capacity+" ["+mt.getClass().getSimpleName()+"] ***\n");
		for(int i = 0;i<capacity;i++){
			sb.append("\n");
			for(int j = 0;j<capacity;j++){
				int valuesize = Integer.toString(mt.getAt(i, j)).length();
				for(int s = 0;s<(cellsize - valuesize);s++)
					sb.append(" ");
				sb.append(mt.getAt(i, j));
			}		
		}
		sb.append("\n");

		createTab(mt, sb.toString());


	}

	@Override
	public void visit(OptimizedInitMT mt) {

		StringBuilder sb = new StringBuilder();
		int cap = mt.getCapacity();

		sb.append("*** x TABLE "+cap+" / "+cap+" ["+mt.getClass().getSimpleName()+"] ***\n");

		for(int i = 0;i<cap;i++){
			sb.append("\n");
			for(int j = 0;j<cap;j++){
				int value = mt.getAt(i, j);

				if(j>i)
					value = mt.getAt(j, i);

				int valuesize = Integer.toString(value).length();

				for(int s = 0;s<(mt.getCellSize() - valuesize);s++)
					sb.append(" ");
				sb.append(value);
			}		
		}
		sb.append("\n");

		createTab(mt, sb.toString());

	}

	private void createTab(AbstractMT mt, String content){
		Tab tab = new Tab(mt.getClass().getSimpleName().substring(0, 2).toUpperCase()+(++MTAPP.MTcount));

		TextArea textArea = new TextArea();
		String bgcolor = ColorUtils.formatColorToRGBA(this.getBgcolor());
		String fgcolor = ColorUtils.formatColorToRGBA(this.getFgcolor());
		textArea.setStyle("-fx-background-color: "+bgcolor+"; text-area-background-color: "+bgcolor+"; -fx-text-fill: "+fgcolor+";");
		tab.setStyle("-fx-background-color: "+bgcolor+";tablabel-text-color: "+fgcolor);

		textArea.setEditable(false);

		textArea.setText(content);

		tab.setContent(textArea);
		
		Platform.runLater(()->tabPane.getTabs().add(tab));
	}

}
