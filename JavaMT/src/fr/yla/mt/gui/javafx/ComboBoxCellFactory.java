package fr.yla.mt.gui.javafx;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ComboBoxCellFactory implements Callback<ListView<Color>, ListCell<Color>> {

	@Override
	public ListCell<Color> call(ListView<Color> param) {
		return new ColorRectComboBoxCell();
	}

}
