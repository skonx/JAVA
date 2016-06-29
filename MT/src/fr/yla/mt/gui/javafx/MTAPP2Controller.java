package fr.yla.mt.gui.javafx;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;

import fr.yla.mt.core.MTComposite;
import fr.yla.mt.core.MTFactory;
import fr.yla.mt.core.MTTypes;
import fr.yla.mt.core.MultiplicationTable;
import fr.yla.mt.visitor.javafx.AbstractJavaFXMTVisitor;
import fr.yla.mt.visitor.javafx.MTVisitorFactory;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class MTAPP2Controller implements Initializable{

	public static int count = 0;

	@FXML private BorderPane root;

	@FXML private Menu deleteMenu;
	@FXML private Menu displayMenu;
	@FXML private MenuBar menuBar;
	@FXML private Menu generatorMenu;

	@FXML private TabPane tabPane;

	@FXML private ComboBox<Color> bgcombobox;
	@FXML private ComboBox<Color> fgcombobox;

	@FXML private RadioMenuItem blendedMenuItem;

	@FXML private ToggleGroup generatorToggleGroup;

	@FXML private ColorPicker colorPicker;

	@FXML private ProgressBar progressBar;

	private MTVisitorFactory visitorFactory;
	private AbstractJavaFXMTVisitor visitor;
	
	private Map<Boolean, Rectangle> status;
	private final StringProperty visitorName = new SimpleStringProperty();

	@FXML private void deleteAllTabs(){
		tabPane.getTabs().clear();

	}

	@FXML private void deleteSelectedTab(){
		int index = tabPane.getSelectionModel().getSelectedIndex();
		if(index != -1)
			tabPane.getTabs().remove(index);
	}

	@FXML private void exit(){

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("EXIT");
		alert.setHeaderText("Do you really want to exit ?");
		alert.setContentText("Please, confirm : ");

		alert.showAndWait().filter(b->b.equals(ButtonType.OK)).ifPresent(b->System.exit(0));

	}

	@FXML private void selectBGColor(){
		bindColorSelectionWithVisitorColor(bgcombobox, AbstractJavaFXMTVisitor::setBgcolor);
	}

	@FXML private void selectFGColor(){
		bindColorSelectionWithVisitorColor(fgcombobox, AbstractJavaFXMTVisitor::setFgcolor);
	}

	@FXML private void resetColor(){
		visitor.setBgcolor(Color.TRANSPARENT);
		visitor.setFgcolor(Color.BLACK);
		bgcombobox.getSelectionModel().select(visitor.getBgcolor());
		fgcombobox.getSelectionModel().select(visitor.getFgcolor());	
	}

	@FXML private void createNewColor(){
		Color color = colorPicker.getValue();

		if(!bgcombobox.getItems().contains(color) && !fgcombobox.getItems().contains(color)){
			bgcombobox.getItems().add(color);
			fgcombobox.getItems().add(color);
		}

	}

	@FXML private void generateBlendedMT(){



		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				menuBar.setDisable(true);

				MTComposite mtc = MTFactory.generateRandomMultiplicationTables(MTComposite.default_cap, MTComposite.default_size);

				int capacity = mtc.size();

				for(int i = 0; i <capacity ; i++){

					if (isCancelled() || Thread.currentThread().isInterrupted()) {
						updateMessage("Cancelled or Interrupted");
						return null;
					}

					int index = i;
					mtc.getAt(index).accept(visitor);

					updateProgress(index, capacity-1);
				}

				Platform.runLater(()->menuBar.setDisable(false));

				updateMessage("OK");
				return null;
			}

		};

		progressBar.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
	}


	private final void initializeStatusIcon(){
		status = new HashMap<>(2);
		status.put(false, new Rectangle(10,10,Color.GREENYELLOW));
		
		Tooltip t = new Tooltip();
		t.textProperty().bind(new SimpleStringProperty("Disabled for ").concat(visitorName).concat(" Display Type"));
		Rectangle redRect = new Rectangle(10,10,Color.ORANGERED);
		Tooltip.install(redRect, t);
		status.put(true, redRect);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		initializeStatusIcon();
		
		visitorFactory = MTVisitorFactory.build(tabPane);

		createDisplayMenu();

		createSingleMTGenerators();

		/*if the selected display type is not compatible with blended mode (for performance reasons),
		 * the blended menu item will be disabled.
		 * */
		blendedMenuItem.setText(blendedMenuItem.getText()+" - x"+MTComposite.default_size);
		blendedMenuItem.setDisable(!visitor.isBlendedAllowed());
		blendedMenuItem.setGraphic(status.get(blendedMenuItem.isDisable()));

	}

	/**
	 * <pre>
	 * Create the Display Menu (aka Visitor Menu) and select the default visitor:
	 * At the present time : MTVisitorTypes.TEXT
	 * </pre>
	 */
	private void createDisplayMenu(){
		final ToggleGroup group = new ToggleGroup();

		boolean init = true;

		for(String name : visitorFactory.getVisitors()){
			RadioMenuItem menuItem = new RadioMenuItem(name);

			menuItem.setOnAction(e->{
				bindVisitorColorsWithColorSelection(name);
				//udpate the blended menu item status with the current visitor flag
				blendedMenuItem.setDisable(!visitor.isBlendedAllowed());
				blendedMenuItem.setGraphic(status.get(blendedMenuItem.isDisable()));
			});

			//bind the visitor with the color boxes
			if(init){
				bindVisitorColorsWithColorSelection(name);
				//the first one created will be the first one selected
				menuItem.setSelected(true);
			}

			menuItem.setToggleGroup(group);
			displayMenu.getItems().add(menuItem);
		}

	}

	private void bindVisitorColorsWithColorSelection(String name){
		visitor = visitorFactory.getVisitor(name);
		visitorName.set(name);
		bgcombobox.getSelectionModel().select(visitor.getBgcolor());
		fgcombobox.getSelectionModel().select(visitor.getFgcolor());
	}

	private void bindColorSelectionWithVisitorColor(ComboBox<Color> comboBox, BiConsumer<AbstractJavaFXMTVisitor, Color> consumer){
		Color color = comboBox.getSelectionModel().getSelectedItem();
		if(color != null)
			consumer.accept(visitor, color);
	}

	private void createSingleMTGenerators(){
		for(MTTypes type : MTTypes.values()){
			RadioMenuItem radioMenuItem = new RadioMenuItem(type.name());

			radioMenuItem.setOnAction(e->{
				Task<Void> task = new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						menuBar.setDisable(true);
						updateProgress(0, 100);
						MultiplicationTable mt = type.create(MultiplicationTable.default_cap);
						mt.accept(visitor);
						updateProgress(100, 100);
						menuBar.setDisable(false);
						updateMessage("OK");
						return null;
					}
				};

				progressBar.progressProperty().bind(task.progressProperty());
				new Thread(task).start();
			});

			radioMenuItem.setToggleGroup(generatorToggleGroup);
			radioMenuItem.setGraphic(new Rectangle(10,10,Color.GREENYELLOW));
			generatorMenu.getItems().add(radioMenuItem);
		}
	}

}
