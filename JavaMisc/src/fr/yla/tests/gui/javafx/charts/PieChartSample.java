package fr.yla.tests.gui.javafx.charts;

import java.time.LocalDateTime;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PieChartSample extends Application {

	@Override public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("Imported Fruits");

		ObservableList<PieChart.Data> pieChartData =
				FXCollections.observableArrayList(
						new PieChart.Data("Grapefruit", 13),
						new PieChart.Data("Oranges", 25),
						new PieChart.Data("Plums", 10),
						new PieChart.Data("Pears", 22),
						new PieChart.Data("Apples", 30));
		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle("Imported Fruits");

		chart.setLabelLineLength(10);
		chart.setLegendSide(Side.LEFT);

		final Label caption = new Label("");
		caption.setTextFill(Color.BLACK);
		caption.setStyle("-fx-font: 24 arial;");

		for (final PieChart.Data data : chart.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
					new EventHandler<MouseEvent>() {
				@Override public void handle(MouseEvent e) {
					caption.setTranslateX(e.getSceneX());
					caption.setTranslateY(e.getSceneY());
					caption.setText(String.valueOf(data.getPieValue()));
				}
			});

		}
		
		chart.getData().addListener(new ListChangeListener<PieChart.Data>() {

			@Override
			public void onChanged(ListChangeListener.Change<? extends Data> c) {
				System.out.println("Something has been added/changed"+LocalDateTime.now());
				c.getList().stream().forEach(System.out::println);
				System.out.println("Entering in while");
				while(c.next())
					c.getAddedSubList().forEach(System.out::println);
			}
		});		
		
		chart.getData().add(0,new PieChart.Data("Banana", 80.0));
		System.out.println("Main>> "+LocalDateTime.now());
		chart.getData().add(0,new PieChart.Data("Kiwi", 12.0));
		System.out.println("Main>> "+LocalDateTime.now());
		
		Group root = new Group();
		root.getChildren().addAll(chart,caption);
		scene.setRoot(root);

		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}