package fr.yla.mt.visitor.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.yla.mt.core.AbstractMT;
import fr.yla.mt.core.OptimizedInitMT;
import fr.yla.mt.gui.swing.MTAPP;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class MTVisitorDisplayInTableView extends AbstractJavaFXMTVisitor {

	private final TabPane tabPane;

	private ObservableList<int[]> data;

	private List<TableColumn<int[], Integer>> columns;

	private List<MTCellValueFactory> cellValueFactory;
	
	private static class MTCellValueFactory implements  Callback<TableColumn.CellDataFeatures<int[],Integer>, ObservableValue<Integer>>{

		private final int index;

		private MTCellValueFactory(final int index) {
			this.index = index;
		}

		@Override
		public ObservableValue<Integer> call(CellDataFeatures<int[], Integer> param) {
			return new SimpleIntegerProperty(param.getValue()[index]).asObject();
		}
	}

	public MTVisitorDisplayInTableView(final TabPane tabPane) {
		//should be the same colors than the CSS file
		super(Color.TRANSPARENT,Color.BLACK);
		this.tabPane = tabPane;
		allowBlended = false;
	}

	private final ObservableList<int[]> getData(AbstractMT mt){
		if(Objects.nonNull(data) && !data.isEmpty())
			return data;

		int capacity = mt.getCapacity();

		data = FXCollections.observableArrayList();

		for(int i = 0; i < capacity ; i++){
			int[] row = new int[capacity];
			for(int j = 0 ; j < capacity ; j++)
				row[j] = (i>j)?mt.getAt(i, j):mt.getAt(j, i);
				data.add(row);
		}

		return data;
	}


	private final List<TableColumn<int[], Integer>> getTableColumns(AbstractMT mt){
		if(Objects.nonNull(columns) && !columns.isEmpty())
			return columns;

		int capacity = mt.getCapacity();

		columns = new ArrayList<>(capacity);

		for(int i = 0; i < capacity ; i++){
			TableColumn<int[], Integer> tc = new TableColumn<>(new Integer(mt.getAt(i, 0)).toString());

			tc.setCellValueFactory(getMTCellValueFactory(mt).get(i));

			tc.setSortable(false);
			columns.add(tc);
		}

		return columns;

	}

	private final List<MTCellValueFactory> getMTCellValueFactory(AbstractMT mt){

		if(Objects.nonNull(cellValueFactory) && !cellValueFactory.isEmpty())
			return cellValueFactory;

		int capacity = mt.getCapacity();

		cellValueFactory = new ArrayList<>(capacity);

		for(int i = 0 ; i < mt.getCapacity() ; i++)
			cellValueFactory.add(new MTCellValueFactory(i));

		return cellValueFactory;
	}


	private void performVisit(AbstractMT mt) {

		TableView<int[]> tableView = new TableView<>();

		tableView.setItems(getData(mt));

		getTableColumns(mt).forEach(tc->tableView.getColumns().add(tc));;

		Tab tab = new Tab(mt.getClass().getSimpleName().substring(0, 2).toUpperCase()+(++MTAPP.MTcount));
		String bgcolor = ColorUtils.formatColorToRGBA(this.getBgcolor());
		String fgcolor = ColorUtils.formatColorToRGBA(this.getFgcolor());
		tableView.setStyle("fgcolor: "+fgcolor+"; bgcolor: "+bgcolor);
		tab.setStyle("-fx-background-color: "+bgcolor+"; tablabel-text-color: "+fgcolor);

		tab.setContent(tableView);

		Platform.runLater(()->tabPane.getTabs().add(tab));
	}

	@Override
	public void visit(AbstractMT mt){
		performVisit(mt);
	}

	@Override
	public void visit(OptimizedInitMT mt) {
		performVisit(mt);
	}

	

}
