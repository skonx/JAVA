package fr.yla.tests.gui.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
 
public class HTMLEditorSample extends Application {    
    private final String INITIAL_TEXT = "Lorem ipsum dolor sit "
            + "amet, consectetur adipiscing elit. Nam tortor felis, pulvinar "
            + "in scelerisque cursus, pulvinar at ante. Nulla consequat"
            + "congue lectus in sodales. Nullam eu est a felis ornare "
            + "bibendum et nec tellus. Vivamus non metus tempus augue auctor "
            + "ornare. Duis pulvinar justo ac purus adipiscing pulvinar. "
            + "Integer congue faucibus dapibus. Integer id nisl ut elit "
            + "aliquam sagittis gravida eu dolor. Etiam sit amet ipsum "
            + "sem.";
 
    @Override
    public void start(Stage stage) {
        stage.setTitle("HTMLEditor Sample");
        stage.setWidth(650);
        stage.setHeight(600);
        Scene scene = new Scene(new Group());
    
        VBox root = new VBox();      
        root.setPadding(new Insets(8, 8, 8, 8));
        root.setSpacing(5);
        root.setAlignment(Pos.BOTTOM_LEFT);
              
        final HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setPrefHeight(245);
        htmlEditor.setHtmlText(INITIAL_TEXT);       
 
        final TextArea htmlCode = new TextArea();
        htmlCode.setWrapText(true);
    
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("noborder-scroll-pane");
        scrollPane.setContent(htmlCode);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(180);
 
        Button showHTMLButton = new Button("Produce HTML Code");
        root.setAlignment(Pos.CENTER);
        showHTMLButton.setOnAction((ActionEvent arg0) -> {
            htmlCode.setText(htmlEditor.getHtmlText());
        });
        
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
     
        ScrollPane scrollPane2 = new ScrollPane();
        scrollPane2.getStyleClass().add("noborder-scroll-pane");
        scrollPane2.setStyle("-fx-background-color: white");
        scrollPane2.setContent(browser);
        scrollPane2.setFitToWidth(true);
        scrollPane2.setPrefHeight(180);
 
        Button loadContentButton1 = new Button("Load Content in Browser");
        root.setAlignment(Pos.CENTER);
        loadContentButton1.setOnAction((ActionEvent arg0) -> {
            webEngine.loadContent(htmlEditor.getHtmlText());
        });
        
        root.getChildren().addAll(htmlEditor, showHTMLButton, scrollPane, new Separator(),loadContentButton1, scrollPane2);
        scene.setRoot(root);
 
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}