package fr.yla.tests.gui.javafx;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ListViewSample extends Application {

	private final ListView<String> list = new ListView<>();
	private final ObservableList<String> data = FXCollections.observableArrayList(
			"chocolate", "green", "salmon", "gold", "coral", "darkorchid",
			"darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
			"blueviolet", "brown");
	private final Label label = new Label();

	@Override
	public void start(Stage stage) {
		VBox box = new VBox();
		Scene scene = new Scene(box, 200, 200);
		stage.setScene(scene);
		stage.setTitle("ListViewSample");
		box.getChildren().addAll(list, label);
		VBox.setVgrow(list, Priority.ALWAYS);

		label.setLayoutX(10);
		label.setLayoutY(115);
		label.setFont(Font.font("Verdana", 18));

		list.setItems(data);

		list.setCellFactory((ListView<String> l) -> new ColorRectCell());

		list.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends String> ov, String old_val, 
						String new_val) -> {
							label.setText(new_val);
							label.setTextFill(Color.web(new_val));
						});
		
		stage.show();
		
		stage.setOnCloseRequest((e)->System.exit(0));

	}

	static class ColorRectCell extends ListCell<String> {
		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			Rectangle rect = new Rectangle(20, 20);
			if (item != null) {
				HBox box = new HBox();//The cell renderer will be based on a HBox pane
				box.setSpacing(10);
				rect.setFill(Color.web(item));
				box.getChildren().addAll(rect,new Label(item));
				setGraphic(box);
			} else {
				setGraphic(null);
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}