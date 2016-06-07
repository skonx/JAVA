package fr.yla.tests.gui.javafx;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
 
public class ProgressSample extends Application {
 
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Progress Controls");
        
        int max = 100;
 
        final Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(max);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        
        final ProgressBar pb = new ProgressBar(-1);
        final ProgressIndicator pi = new ProgressIndicator(-1);
        
        slider.valueProperty().addListener(
            (ObservableValue<? extends Number> ov, Number old_val, 
            Number new_val) -> {
                pb.setProgress(new_val.doubleValue()/max);
                pi.setProgress(new_val.doubleValue()/max);
        });
 
        final HBox hb = new HBox();
        
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(slider, pb, pi);
        HBox.setHgrow(slider, Priority.ALWAYS);
        
        scene.setRoot(hb);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}