package fr.yla.tests.gui.javafx;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ImageOpsTest extends Application {

	private Mirror m = Mirror.HORIZONTAL;

	@Override
	public void start(Stage primaryStage) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Scene scene = new Scene(grid);

		// Create Image and ImageView objects
		Image image = new Image("http://cdn.playbuzz.com/cdn/97b5e87c-0cfd-4805-8b3d-4f0725d93b02/651cbaf2-15d8-4c92-8caa-acd908930fff.jpg",1000,0,true,true);
		ImageView imageView = new ImageView();
		imageView.setImage(image);

		// Obtain PixelReader
		PixelReader pixelReader = image.getPixelReader();
		System.out.println("Image Width: "+image.getWidth());
		System.out.println("Image Height: "+image.getHeight());
		System.out.println("Pixel Format: "+pixelReader.getPixelFormat());

		ImageView imageView2 = new ImageView();
		// Display image on screen
		imageView2.setImage(createMirrorImage(image, pixelReader));

		final Accordion accordion = new Accordion();

		TitledPane original = new TitledPane("Original",imageView);
		TitledPane modified = new TitledPane("Invert & MirrorFunction / "+m.name(),imageView2);

		createContextMenu(imageView,primaryStage);
		createContextMenu(imageView2,primaryStage);

		HBox box = new HBox();
		box.setAlignment(Pos.CENTER_LEFT);
		box.setSpacing(20);
		box.getChildren().add(new Label("Mirror Orientation : "));
		for(Mirror type : Mirror.values()){
			Button button = new Button(type.name());
			button.setOnAction(e->{
				m=type;
				imageView2.setImage(createMirrorImage(image, pixelReader));
				modified.setText("Invert & MirrorFunction / "+type.name());
			});
			box.getChildren().add(button);

		}

		final ColorPicker colorPicker = new ColorPicker(null);
		colorPicker.setOnAction(e->{
			Color c = colorPicker.getValue();
			grid.setStyle("-fx-background: rgb("+c.getRed()*255+","+c.getGreen()*255+","+c.getBlue()*255+");"+
			"-fx-font-weight: bold;");
		});
		box.getChildren().addAll(new Separator(), new Label("Change Background Color"), colorPicker);

		accordion.getPanes().addAll(original,modified);
		accordion.setExpandedPane(original);

		grid.add(box, 0, 0);
		grid.add(accordion, 0, 1);
		//grid.setGridLinesVisible(true);

		primaryStage.setTitle("Image Write Test");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	private void createContextMenu(ImageView image, Stage stage) {
		final ContextMenu cm = new ContextMenu();
		MenuItem cmItem1 = new MenuItem("Copy Image");
		cmItem1.setOnAction((ActionEvent e) -> {
			Clipboard clipboard = Clipboard.getSystemClipboard();
			ClipboardContent content = new ClipboardContent();
			content.putImage(image.getImage());
			clipboard.setContent(content);
		});
		
		MenuItem cmItem2 = new MenuItem("Save Image");
		cmItem2.setOnAction((ActionEvent e) -> {
		    FileChooser fileChooser = new FileChooser();
		    fileChooser.setTitle("Save Image");
		    fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png"));
		    File file = fileChooser.showSaveDialog(stage);
		    if (file != null) {
		        try {
		            ImageIO.write(SwingFXUtils.fromFXImage(image.getImage(),
		                    null), "png", file);
		        } catch (IOException ex) {
		             System.out.println(ex.getMessage());
		        }
		    }
		});

		cm.getItems().addAll(cmItem1,cmItem2);
		image.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			if (e.getButton() == MouseButton.SECONDARY)
				cm.show(image, e.getScreenX(), e.getScreenY());
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	private final WritableImage createMirrorImage(final Image image, final PixelReader pixelReader){
		// Create WritableImage
		WritableImage wImage = new WritableImage(
				(int)image.getWidth(),
				(int)image.getHeight());
		PixelWriter pixelWriter = wImage.getPixelWriter();

		// Determine the color of each pixel in a specified row
		for(int readY=0;readY<image.getHeight();readY++){
			for(int readX=0; readX<image.getWidth();readX++){
				Color color = pixelReader.getColor(readX,readY);
				/*
				 * System.out.println("\nPixel color at coordinates ("+readX+","+readY+") "
						+color.toString());
				System.out.println("R = "+color.getRed());
				System.out.println("G = "+color.getGreen());
				System.out.println("B = "+color.getBlue());
				System.out.println("Opacity = "+color.getOpacity());
				System.out.println("Saturation = "+color.getSaturation());
				 */

				// Invert color + mirror the image
				color = color.invert();

				int[] coords = m.mirror((int)image.getWidth(), (int)image.getHeight(), readX, readY);

				pixelWriter.setColor(coords[0],coords[1],color);
			}
		}

		return wImage;
	}

	@FunctionalInterface
	public static interface MirrorFunction{
		int[] apply(int w,int h, int x, int y);
		static int[] vertical (int w,int h, int x, int y){
			int [] coords = new int[2];
			coords[0] = w-1-x;
			coords[1] = y;
			return coords;
		}
		static int[] horizontal (int w,int h, int x, int y){
			int [] coords = new int[2];
			coords[0] = x;
			coords[1] = h-1-y;
			return coords;
		}
	}

	public static enum Mirror{
		VERTICAL(MirrorFunction::vertical),
		HORIZONTAL(MirrorFunction::horizontal);

		private final MirrorFunction m;

		private Mirror(MirrorFunction m) {
			this.m = m;
		}

		public int[] mirror(int w,int h, int x, int y){
			return m.apply(w, h, x, y);
		}
	}
}