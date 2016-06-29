package fr.yla.mt.visitor.javafx;

import fr.yla.mt.core.AbstractMT;
import fr.yla.mt.core.OptimizedInitMT;
import fr.yla.mt.gui.swing.MTAPP;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class MTVisitorDisplayInGridPaneTF extends AbstractJavaFXMTVisitor {

	private final TabPane tabPane;

	public MTVisitorDisplayInGridPaneTF(final TabPane tabPane) {
		//should be the same colors than the CSS file
		super(Color.TRANSPARENT,Color.BLACK);
		this.tabPane = tabPane;
		allowBlended = false;
	}

	@Override
	public void visit(AbstractMT mt) {
		int capacity = mt.getCapacity();

		GridPane gridPane = new GridPane();

		for(int i = 0;i<capacity;i++){
			for(int j = 0;j<capacity;j++){
				TextField textField = new TextField(""+mt.getAt(i, j));
				String bgcolor = ColorUtils.formatColorToRGBA(this.getBgcolor());
				String fgcolor = ColorUtils.formatColorToRGBA(this.getFgcolor());
				textField.setStyle("-fx-background-color: "+bgcolor+"; -fx-text-fill: "+fgcolor+";");
				gridPane.add(textField,i,j);
			}		
		}

		createTab(mt, gridPane);

	}

	@Override
	public void visit(OptimizedInitMT mt) {
		int capacity = mt.getCapacity();

		GridPane gridPane = new GridPane();

		for(int i = 0;i<capacity;i++){
			for(int j = 0;j<capacity;j++){
				TextField textField = new TextField(""+((j>i)?mt.getAt(j, i):mt.getAt(i, j)));
				String bgcolor = ColorUtils.formatColorToRGBA(this.getBgcolor());
				String fgcolor = ColorUtils.formatColorToRGBA(this.getFgcolor());
				textField.setStyle("-fx-background-color: "+bgcolor+"; -fx-text-fill: "+fgcolor+";");
				gridPane.add(textField,i,j);
			}		
		}

		createTab(mt, gridPane);

	}

	private void createTab(AbstractMT mt, GridPane gridPane){
		Tab tab = new Tab(mt.getClass().getSimpleName().substring(0, 2).toUpperCase()+(++MTAPP.MTcount));

		String bgcolor = ColorUtils.formatColorToRGBA(this.getBgcolor());
		String fgcolor = ColorUtils.formatColorToRGBA(this.getFgcolor());
		gridPane.setStyle("-fx-background-color: "+bgcolor+"; -fx-text-fill: "+fgcolor+";");
		tab.setStyle("-fx-background-color: "+bgcolor+";tablabel-text-color: "+fgcolor);

		tab.setContent(new ScrollPane(gridPane));

		Platform.runLater(()->tabPane.getTabs().add(tab));
	}


}
