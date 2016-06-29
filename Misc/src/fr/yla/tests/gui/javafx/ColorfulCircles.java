package fr.yla.tests.gui.javafx;

import static java.lang.Math.random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ColorfulCircles extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("ColorfulCircles");
		Group root = new Group();
		Scene scene = new Scene(root, 800, 600, Color.BLACK);

		Group circles = createGroupCircles(50);

		Rectangle colors = createColors(scene);

		Group blendModeGroup = 
				new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight(),
						Color.BLACK), circles), colors);
		colors.setBlendMode(BlendMode.OVERLAY);
		root.getChildren().add(blendModeGroup);

		primaryStage.setScene(scene);

		Timeline timeline = new Timeline();
		double timelaps = 10000;

		initPaths(timeline, circles, timelaps);

		timeline.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				initPaths(timeline, circles, timelaps);
				timeline.play();
			}
		});

		timeline.play();

		primaryStage.show();
	}


	private void initPaths(final Timeline timeline, final Group circles, double timelaps){
		if(timeline.getKeyFrames().isEmpty()){//initialize the timeline for the first time
			for (Node circle: circles.getChildren()) {
				timeline.getKeyFrames().addAll(
						new KeyFrame(Duration.ZERO, 
								new KeyValue(circle.translateXProperty(), random() * 800),
								new KeyValue(circle.translateYProperty(), random() * 600)
								),
						new KeyFrame(new Duration(timelaps), 
								new KeyValue(circle.translateXProperty(), random() * 800),
								new KeyValue(circle.translateYProperty(), random() * 600)
								)
						);
			}
		}
		else{//then clear the timeline keyframes and add a new target
			timeline.getKeyFrames().clear();
			for(Node circle : circles.getChildren()){
				timeline.getKeyFrames().addAll(
						new KeyFrame(new Duration(timelaps), 
								new KeyValue(circle.translateXProperty(), random() * 800),
								new KeyValue(circle.translateYProperty(), random() * 600)
								)
						);
			}
		}
	}

	private Rectangle createColors(final Scene scene) {
		Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
				new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new 
						Stop[]{
								new Stop(0, Color.web("#f8bd55")),
								new Stop(0.14, Color.web("#c0fe56")),
								new Stop(0.28, Color.web("#5dfbc1")),
								new Stop(0.43, Color.web("#64c2f8")),
								new Stop(0.57, Color.web("#be4af7")),
								new Stop(0.71, Color.web("#ed5fc2")),
								new Stop(0.85, Color.web("#ef504c")),
								new Stop(1, Color.web("#f2660f")),}));
		colors.widthProperty().bind(scene.widthProperty());
		colors.heightProperty().bind(scene.heightProperty());
		return colors;
	}

	private Group createGroupCircles(int capacity) {
		Group circles = new Group();
		for (int i = 0; i < capacity; i++) {
			Circle circle = new Circle(150, Color.web("white", 0.05));
			circle.setStrokeType(StrokeType.OUTSIDE);
			circle.setStroke(Color.web("white", 0.16));
			circle.setStrokeWidth(4);
			circles.getChildren().add(circle);
		}
		circles.setEffect(new BoxBlur(10, 10, 3));
		return circles;
	}

	public static void main(String[] args) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		launch(args);
	}
}