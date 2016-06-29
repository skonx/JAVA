package fr.yla.tests.gui.javafx;

import java.util.Objects;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * Got this issue running on linux : 
 * Prism-ES2 Error : GL_VERSION (major.minor) = 1.5
 * @author root
 *
 */
public class LoginForm extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("JavaFX Welcome");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Welcome");
		scenetitle.setId("welcome-text");
		/* Use without css style
		 * scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		 */
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);



		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);
		actiontarget.setId("actiontarget");

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				String user = userTextField.getText();
				String pwd = pwBox.getText();
			
	
				if(Objects.nonNull(user) && Objects.nonNull(pwd) && !user.isEmpty() && !pwd.isEmpty()){
					/* Use without css style
					 * actiontarget.setFill(Color.FIREBRICK);
					 */
					actiontarget.setText("Sign in button pressed");

					System.out.println("user = "+user+" / password = "+pwd);
				}
			}
		});

		//grid.setGridLinesVisible(true);
		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(LoginForm.class.getResource("LoginForm.css").toExternalForm()); //"fr/yla/tests/gui/javafx/LoginForm.css" also works :)
		
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
