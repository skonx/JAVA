package fr.yla.mt.gui.javafx;

import fr.yla.mt.visitor.javafx.ColorUtils;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorRectComboBoxCell extends ListCell<Color> {
	@Override
	public void updateItem(Color item, boolean empty) {
		super.updateItem(item, empty);
		Rectangle rect = new Rectangle(20, 20);
		if (item != null) {
			HBox box = new HBox();//The cell renderer will be based on a HBox pane
			box.setSpacing(10);
			rect.setFill(item);
			//use an Optional value instead of a raw string in order to avoid NullPointerExceptions...
			ColorUtils.getOptionalColorName(item).ifPresent(e->{
				Label label = new Label(e.getValue());
				label.setTextFill(Color.BLACK);
				box.getChildren().addAll(rect,label);
				setGraphic(box);
			});
		} else {
			setGraphic(null);
		}
	}
}