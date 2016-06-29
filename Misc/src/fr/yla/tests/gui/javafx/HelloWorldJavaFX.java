package fr.yla.tests.gui.javafx;

import javafx.application.Application;
/*
 * import javafx.event.ActionEvent;
 * import javafx.event.EventHandler;
 */
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloWorldJavaFX extends Application {

	@Override
	public void start(Stage primaryStage) {
		Button btn = new Button();
		btn.setText("Display 'Hello World' on console");
		btn.setOnAction(e->System.out.println("Hello World using a lambda instead of an anonymous class >:)"));
		/*
		 * new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        }
		 */

		StackPane root = new StackPane();
		root.getChildren().add(btn);

		Scene scene = new Scene(root, 300, 250);

		primaryStage.setTitle("Hello World using JavaFX!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}