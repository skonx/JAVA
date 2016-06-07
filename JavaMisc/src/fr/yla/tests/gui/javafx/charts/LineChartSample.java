package fr.yla.tests.gui.javafx.charts;

import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;


public class LineChartSample extends Application {

	@Override public void start(Stage stage) {
		stage.setTitle("Line Chart Sample");
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();

		xAxis.setLabel("Month");
		
		final LineChart<String,Number> lineChart = 
				new LineChart<>(xAxis,yAxis);

		lineChart.setTitle("Stock Monitoring, 2014");
		
		XYChart.Series<String,Number> series1 = new XYChart.Series<>();
		series1.setName("Portfolio 1");

		series1.getData().add(new XYChart.Data<String, Number>("Jan", 23));
		series1.getData().add(new XYChart.Data<String, Number>("Feb", 14));
		series1.getData().add(new XYChart.Data<String, Number>("Mar", 15));
		series1.getData().add(new XYChart.Data<String, Number>("Apr", 24));
		series1.getData().add(new XYChart.Data<String, Number>("May", 34));
		series1.getData().add(new XYChart.Data<String, Number>("Jun", 36));
		series1.getData().add(new XYChart.Data<String, Number>("Jul", 22));
		series1.getData().add(new XYChart.Data<String, Number>("Aug", 45));
		series1.getData().add(new XYChart.Data<String, Number>("Sep", 43));
		series1.getData().add(new XYChart.Data<String, Number>("Oct", 17));
		series1.getData().add(new XYChart.Data<String, Number>("Nov", 29));
		series1.getData().add(new XYChart.Data<String, Number>("Dec", 25));

		XYChart.Series<String,Number> series2 = new XYChart.Series<String,Number>();
		series2.setName("Portfolio 2");
		series2.getData().add(new XYChart.Data<String, Number>("Jan", 33));
		series2.getData().add(new XYChart.Data<String, Number>("Feb", 34));
		series2.getData().add(new XYChart.Data<String, Number>("Mar", 25));
		series2.getData().add(new XYChart.Data<String, Number>("Apr", 44));
		series2.getData().add(new XYChart.Data<String, Number>("May", 39));
		series2.getData().add(new XYChart.Data<String, Number>("Jun", 16));
		series2.getData().add(new XYChart.Data<String, Number>("Jul", 55));
		series2.getData().add(new XYChart.Data<String, Number>("Aug", 54));
		series2.getData().add(new XYChart.Data<String, Number>("Sep", 48));
		series2.getData().add(new XYChart.Data<String, Number>("Oct", 27));
		series2.getData().add(new XYChart.Data<String, Number>("Nov", 37));
		series2.getData().add(new XYChart.Data<String, Number>("Dec", 29));

		XYChart.Series<String,Number> series3 = new XYChart.Series<String,Number>();
		series3.setName("Portfolio 3");
		series3.getData().add(new XYChart.Data<String, Number>("Jan", 44));
		series3.getData().add(new XYChart.Data<String, Number>("Feb", 35));
		series3.getData().add(new XYChart.Data<String, Number>("Mar", 36));
		series3.getData().add(new XYChart.Data<String, Number>("Apr", 33));
		series3.getData().add(new XYChart.Data<String, Number>("May", 31));
		series3.getData().add(new XYChart.Data<String, Number>("Jun", 26));
		series3.getData().add(new XYChart.Data<String, Number>("Jul", 22));
		series3.getData().add(new XYChart.Data<String, Number>("Aug", 25));
		series3.getData().add(new XYChart.Data<String, Number>("Sep", 43));
		series3.getData().add(new XYChart.Data<String, Number>("Oct", 44));
		series3.getData().add(new XYChart.Data<String, Number>("Nov", 45));
		series3.getData().add(new XYChart.Data<String, Number>("Dec", 44));

		lineChart.getData().add(series1);
		lineChart.getData().add(series2);
		lineChart.getData().add(series3);

		//remove the symbols when false
		lineChart.setCreateSymbols(true);

		//compute the combined values of the 3 previous series
		XYChart.Series<String, Number> series4 = new XYChart.Series<>();
		series4.setName("Total");
		int max = series1.getData().size();
		if(max == series2.getData().size() && max == series3.getData().size()){
			for(int i = 0 ; i < max ; i++){

				series4.getData().add(new XYChart.Data<String,Number>(series1.getData().get(i).getXValue(),
						new Integer(series1.getData().get(i).getYValue().intValue()
								+series2.getData().get(i).getYValue().intValue()+
								series3.getData().get(i).getYValue().intValue())));

				final int index = i;

				ChangeListener<Number> listener = new ChangeListener<Number>() {

					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						series4.getData().get(index).setYValue(series4.getData().get(index).getYValue().intValue()+newValue.intValue()-oldValue.intValue());
					}
				};

				series1.getData().get(i).YValueProperty().addListener(listener);
				series2.getData().get(i).YValueProperty().addListener(listener);
				series3.getData().get(i).YValueProperty().addListener(listener);

			}
		}

		lineChart.getData().add(series4);

		//display the symbols
		lineChart.setCreateSymbols(true);

		lineChart.setAnimated(false);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		HBox box = new HBox();
		box.setSpacing(10);
		box.setAlignment(Pos.CENTER);

		//add a tooltip on each points
		for(XYChart.Series<String,Number> series : lineChart.getData()){

			series.getData().forEach(d->Tooltip.install(d.getNode(),  new Tooltip(d.getYValue().toString())));

			if(!series.equals(series4))
				series.getData().forEach(d->d.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						int value = d.getYValue().intValue();
						d.setYValue(value+10);
					}
				}));


			Button button = new Button(series.getName());
			button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					/* Will hide the series and the symbols
					 * series.getNode().setVisible(!series.getNode().isVisible());

					//should be easier to remove the symbols but all symbols will be removed instead...
					for(Data<String, Number> data : series.getData()){
						data.getNode().setVisible(!data.getNode().isVisible());
					}*/

					/*
					 * Will remove the series and re-scale the chart
					 */

					if(lineChart.getData().contains(series))
						lineChart.getData().remove(series);
					else
						lineChart.getData().add(series);

					//will display a useless message
					Alert msg = new Alert(AlertType.INFORMATION);
					msg.setContentText("Hello from "+series.getName());
					msg.setHeaderText("Message for you");
					msg.setTitle(series.getName());
					//msg.show();
				}
			});
			box.getChildren().add(button);

		}


		Task<Void> t = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				int max = 240;
				for(int i = 0 ; i < max ; i++){
					if (isCancelled() || Thread.currentThread().isInterrupted()) {
						updateMessage("Cancelled or Interrupted");
						return null;
					}
					final int index = i+1;
					Platform.runLater(()->{
						int v1 = new Double(Math.random()*50).intValue();
						int v2 = new Double(Math.random()*50).intValue();
						int v3 = new Double(Math.random()*200).intValue();

						series1.getData().add(new XYChart.Data<String, Number>("m"+index%12+"-y"+index/12, v1));
						series2.getData().add(new XYChart.Data<String, Number>("m"+index%12+"-y"+index/12, v2));
						series3.getData().add(new XYChart.Data<String, Number>("m"+index%12+"-y"+index/12, v3));
						series4.getData().add(new XYChart.Data<String, Number>("m"+index%12+"-y"+index/12, v1+v2+v3));
						
						series1.getData().remove(0);
						series2.getData().remove(0);
						series3.getData().remove(0);
						series4.getData().remove(0);
						
					});

					updateProgress(i, max-1);

					try {
						Thread.sleep(1000);
					} catch (InterruptedException interrupted) {
						if (isCancelled() || Thread.currentThread().isInterrupted()) {
							updateMessage("Cancelled or Interrupted");
							return null;
						}
					}
				}
				updateMessage("OK");
				return null;
			}
		};

		ProgressIndicator progressIndicator = new ProgressIndicator();
		progressIndicator.visibleProperty().bind(t.runningProperty());
		progressIndicator.progressProperty().bind(t.progressProperty());

		Executors.newSingleThreadExecutor().submit(t);//or new Thread(t).start();

		yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis,null," Units"));

		grid.add(lineChart, 0, 0);
		GridPane.setConstraints(lineChart, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER,Priority.ALWAYS,Priority.ALWAYS);
		grid.add(box, 0, 1);
		grid.add(progressIndicator, 0, 2);
		GridPane.setConstraints(progressIndicator, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER);


		Scene scene  = new Scene(grid);    
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
		//System.exit doesn't care about the remaining active background threads
		//Platform.exit will leave remaining active background threads alive, 
		//should be better to specify that the task is started as a deamon thread.deamon(true)
		stage.setOnCloseRequest(e->System.exit(0));
	}


	public static void main(String[] args) {
		launch(args);
	}
}