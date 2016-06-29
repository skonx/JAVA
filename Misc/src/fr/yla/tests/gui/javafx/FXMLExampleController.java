package fr.yla.tests.gui.javafx;
 
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
 
public class FXMLExampleController {
    @FXML private Text actiontarget;
    
    @FXML private TextField user;
    
    @FXML private PasswordField pwd;
    
    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
    	String u = user.getText();
		String p = pwd.getText();
	

		if(Objects.nonNull(user) && Objects.nonNull(pwd) && !u.isEmpty() && !p.isEmpty()){
			actiontarget.setText("Sign in button pressed");
			System.out.println("user = "+u+" / password = "+p);
		}
    }

}