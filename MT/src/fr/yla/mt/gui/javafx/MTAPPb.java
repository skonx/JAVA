package fr.yla.mt.gui.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MTAPPb extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		StackPane root = new StackPane();

		Image image = new Image(getClass().getResourceAsStream("Transport-Under-Construction-icon.png"));
		Label label = new Label("In progress...", new ImageView(image));

		label.setFont(new Font("Cambria", 32));

		Paint defaultColor = label.getTextFill();

		label.setOnMouseEntered((e)->{
			label.setScaleX(1.5);
			label.setScaleY(1.5);
			label.setTextFill(Color.web("#007695"));
			label.setEffect(new DropShadow());
		});

		label.setOnMouseExited((e)->{
			label.setScaleX(1);
			label.setScaleY(1);
			label.setTextFill(defaultColor);
			label.setEffect(null);
		});

		root.getChildren().add(label);

		Scene scene = new Scene(root, 500, 350);

		primaryStage.setTitle("Multiplication Table - Java FX Application");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e->System.exit(0));


	}

}
