package fr.yla.mt.gui.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JWindow;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.yla.mt.core.MTComposite;
import fr.yla.mt.core.MTFactory;
import fr.yla.mt.core.MTTypes;
import fr.yla.mt.core.MultiplicationTable;
import fr.yla.mt.visitor.swing.AbstractSwingMTVisitor;
import fr.yla.mt.visitor.swing.MTVisitorFactory;
import fr.yla.mt.visitor.swing.MTVisitorTypes;

public class MTAPP {

	private final JFrame frame;
	private final JTabbedPane tabbedPane;
	private final JWindow loadingWindow;
	private AbstractSwingMTVisitor visitor;
	private final MTVisitorFactory factory;
	private final ColorsMap colorsMap;
	private JMenu displaytypemenu;
	private JMenuItem clearmenu;
	private JMenuItem generatecolor;
	private int newcolorcount;

	/**
	 * Single Multiplication Table occurrences
	 */
	public static int MTcount = 0;

	private MTAPP(){
		frame = new JFrame("Multiplication Table");
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		loadingWindow = new JWindow();
		factory = MTVisitorFactory.build(tabbedPane);
		visitor = factory.getVisitor(MTVisitorTypes.TABLE.name());//default visitor
		colorsMap = ColorsMap.build();
		newcolorcount = 0;
	}

	private void init(){
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		JLabel label = new JLabel("Loading...");
		Font defaultFont = label.getFont();
		label.setFont(new Font(defaultFont.getName(), Font.BOLD, 30));

		loadingWindow.getContentPane().setBackground(Color.YELLOW);
		loadingWindow.add(label);
		loadingWindow.pack();
		loadingWindow.setLocationRelativeTo(frame);
		loadingWindow.setAlwaysOnTop(true);
		clearmenu = new JMenuItem("Reset");
		generatecolor = new JMenuItem("Create Color");

	}

	public static void main(String[] args){
		MTAPP app = new MTAPP();
		app.init();
		app.start();

	}

	private void start(){

		JMenuBar menuBar = new JMenuBar();

		JMenu generatorMenu = createMultiplicationTableGeneratorMenu();
		JMenu deleteMenu = createDeleteMenu();
		deleteMenu.setEnabled(false);


		JMenu editMenu = createEditMenu();

		menuBar.add(editMenu);
		menuBar.add(generatorMenu);
		menuBar.add(deleteMenu);

		DefaultComboBoxModel<String> bgcolorsmodel = createColorModel();
		DefaultComboBoxModel<String> fgcolorsmodel = createColorModel();

		JComboBox<String> bgcolorbox = createColorComboBox("Background Color",bgcolorsmodel,AbstractSwingMTVisitor::setBgColor);
		JComboBox<String> fgcolorbox = createColorComboBox("Foreground Color",fgcolorsmodel,AbstractSwingMTVisitor::setFgColor);

		menuBar.add(bgcolorbox);
		menuBar.add(fgcolorbox);

		updateColorsWithCurrentDisplayType(bgcolorbox,fgcolorbox);

		performColorReset(bgcolorbox,fgcolorbox);
		performColorCreation(bgcolorsmodel,fgcolorsmodel);

		/*
		 * If there is no tab, delete feature is disabled.
		 */
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int index = tabbedPane.getSelectedIndex();
				if(index == -1)
					deleteMenu.setEnabled(false);
				else
					deleteMenu.setEnabled(true);
			}
		});

		frame.setJMenuBar(menuBar);
		frame.add(tabbedPane);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point screenCenter = env.getCenterPoint();
		Rectangle appBounds = new Rectangle(screenCenter.x-500/2, screenCenter.y-400/2, 500, 400);

		frame.setBounds(appBounds);
		loadingWindow.setVisible(true);
		frame.setVisible(true);
		loadingWindow.setVisible(false);

	}

	private void performColorCreation(DefaultComboBoxModel<String> bgcolorsmodel,
			DefaultComboBoxModel<String> fgcolorsmodel) {
		generatecolor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newcolorcount++;
				String name = "Cust-"+newcolorcount;
				bgcolorsmodel.addElement(name);
				fgcolorsmodel.addElement(name);
				float r = Double.valueOf(Math.random()).floatValue();
				float g = Double.valueOf(Math.random()).floatValue();
				float b = Double.valueOf(Math.random()).floatValue();
				colorsMap.addColor(name, new Color(r,g,b));
				System.out.println("R="+r+" G="+g+" B="+b);
			}
		});
	}

	private void performColorReset(JComboBox<String> bgcolorbox, JComboBox<String> fgcolorbox) {
		clearmenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(tabbedPane, "Do you really want to reset the current colors ?","Reset Background and Foreground Colors",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(answer == JOptionPane.YES_OPTION){
					visitor.setBgColor(null);
					visitor.setFgColor(null);
					bgcolorbox.setSelectedItem(ColorsItems.UNCOLORED.name());
					fgcolorbox.setSelectedItem(ColorsItems.UNCOLORED.name());
				}		
			}
		});
	}

	private void updateColorsWithCurrentDisplayType(JComboBox<String> bgcolorbox, JComboBox<String> fgcolorbox) {
		for(int i = 0; i<displaytypemenu.getItemCount();i++){
			JMenuItem menuItem = displaytypemenu.getItem(i);
			menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					visitor = factory.getVisitor(e.getActionCommand());

					String bgcolor = colorsMap.lookupNameFromColor(visitor.getBgColor());

					String fgcolor = colorsMap.lookupNameFromColor(visitor.getFgColor());

					bgcolorbox.setSelectedItem(bgcolor);
					fgcolorbox.setSelectedItem(fgcolor);
				}
			});
		}
	}

	/**
	 * Will update the color of the display type visitor
	 * @param label the selected color
	 * @param colorsModel the MVC combox model 
	 * @param setter the setter function for the background color or the foreground color
	 * @return the created combo box
	 */
	private JComboBox<String> createColorComboBox(final String label,final DefaultComboBoxModel<String> colorsModel,final BiConsumer<AbstractSwingMTVisitor,Color> setter) {
		JComboBox<String> box = new JComboBox<>(colorsModel);
		box.setToolTipText(label);
		box.setSelectedItem(ColorsItems.UNCOLORED.name());
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String)box.getSelectedItem();
				setter.accept(visitor, colorsMap.getColor(name));
			}
		});

		box.setRenderer(createColorListRenderer(box));

		return box;
	}

	private DefaultComboBoxModel<String> createColorModel() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

		for(String color : colorsMap.getColors())
			model.addElement(color);

		return model;
	}

	private JMenu createEditMenu() {
		displaytypemenu = createDisplayTypeMenu();

		JMenu mgmtcolormenu = createManageColorMenu();

		JMenu mainMenu = new JMenu("Edit");
		JMenuItem exitmenu = new JMenuItem("Exit");

		exitmenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(tabbedPane, "Do you really want to exit ?","EXIT",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(answer == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});

		JMenu lafmenu = createLookAndFeelMenu();

		mainMenu.add(displaytypemenu);
		mainMenu.add(mgmtcolormenu);
		mainMenu.add(lafmenu);
		mainMenu.addSeparator();
		mainMenu.add(exitmenu);

		return mainMenu;
	}

	private JMenu createLookAndFeelMenu() {
		JMenu lafmenu = new JMenu("Look & Feel");

		LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();

		ButtonGroup group = new ButtonGroup();

		for(LookAndFeelInfo laf : lafs){
			JCheckBoxMenuItem item = new JCheckBoxMenuItem(laf.getName());
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						UIManager.setLookAndFeel(laf.getClassName());
						SwingUtilities.updateComponentTreeUI(frame);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						e1.printStackTrace();
					}

				}
			});
			if(UIManager.getLookAndFeel().getName().equals(laf.getName()))
				item.setSelected(true);
			group.add(item);
			lafmenu.add(item);
		}
		return lafmenu;
	}

	private JMenu createMultiplicationTableGeneratorMenu(){
		JMenu mainMenu = new JMenu("Generate");
		JMenu automaticMenu = createAutomaticGeneratorMenu();

		mainMenu.add(automaticMenu);

		return mainMenu;
	}

	private JMenu createAutomaticGeneratorMenu(){
		JMenu menu = new JMenu("Automatic");

		/*
		 * Group and Show the latest creation method used 
		 */
		ButtonGroup group = new ButtonGroup();

		/*
		 * Create a check box item for each MultiplicationTable type
		 */
		for(MTTypes type : MTTypes.values()){
			JCheckBoxMenuItem menuItem = createSingleMultiplicationTableGeneratorItem(type);
			menu.add(menuItem);
			group.add(menuItem);
		}

		menu.addSeparator();

		/*
		 * Create a check box for 10x Random MultiplicationTable type, using a Composite
		 */
		JCheckBoxMenuItem menuItem = createCompositeMultiplicationTableGeneratorItem();
		menu.add(menuItem);
		group.add(menuItem);

		return menu;
	}
	/**
	 * <pre>
	 * Create the Generator menu item for the specific MultiplicationTableType. A Checkbox is used in order to show the latest creation method used.
	 * Creation is asynchronously performed using EventQueue.invokeLater().
	 * Created MultiplicationTable resource is not stored and free for the GC at the end of the method call.
	 * </pre>
	 * @param type a type from the MultiplicationTableType enum
	 * @param loadingWindow the hidden loadingWindow splash to enable/disable during the loading
	 * @param tabbedPane the target JTabbedPane where the generated MultiplicationTable will be inserted
	 * @return the menu item created
	 */
	private JCheckBoxMenuItem createSingleMultiplicationTableGeneratorItem(MTTypes type){
		JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(type.name()); 

		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				loadingWindow.setLocationRelativeTo(tabbedPane);
				loadingWindow.setVisible(true);
				new Thread(new Runnable() {

					@Override
					public void run() {
						MultiplicationTable mt = type.create(MultiplicationTable.default_cap);
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								mt.accept(visitor);
							}
						});

						try {
							EventQueue.invokeAndWait(new Runnable() {

								@Override
								public void run() {
									loadingWindow.setVisible(false);								
								}
							});
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				}).start();
			}
		});

		return menuItem;
	}
	/**
	 * Same method than createSingleMultiplicationTableGeneratorItem but will create a Composite and add its content as different tabs.
	 * @see createSingleMultiplicationTableGeneratorItem
	 * @param loadingWindow the hidden loadingWindow splash to enable/disable during the loading
	 * @param tabbedPane the target JTabbedPane where the generated MultiplicationTable will be inserted
	 * @return the menu item created
	 */
	private JCheckBoxMenuItem createCompositeMultiplicationTableGeneratorItem(){
		JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem("MIXED - x"+MTComposite.default_size);

		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				loadingWindow.setLocationRelativeTo(tabbedPane);
				loadingWindow.setVisible(true);
				frame.setEnabled(false);

				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							MTComposite mt = MTFactory.generateRandomMultiplicationTables(MTComposite.default_cap,MTComposite.default_size);
							EventQueue.invokeLater(new Runnable() {

								@Override
								public void run() {
									mt.accept(visitor);									
								}
							});

						} catch (InterruptedException e1) {
							JOptionPane.showMessageDialog(tabbedPane, e1.getMessage(), e1.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
						}
						try {
							EventQueue.invokeAndWait(new Runnable() {

								@Override
								public void run() {
									loadingWindow.setVisible(false);
									frame.setEnabled(true);
								}
							});
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
						}
					}
				}).start();
			}
		});
		return menuItem;
	}

	/**
	 * Delete feature is disabled when there is no tab in the application (at the begin or when all tabs are deleted).
	 * @param loadingWindow the hidden loadingWindow splash to enable/disable during the loading
	 * @param tabbedPane the target JTabbedPane where the generated MultiplicationTable will be inserted
	 * @return
	 */
	private JMenu createDeleteMenu() {
		JMenu mainMenu = new JMenu("Delete");

		JMenuItem selectedMenu = createDeleteSelectedMenu();
		JMenuItem allMenu = createDeleteAllMenu();

		mainMenu.add(selectedMenu);
		mainMenu.add(allMenu);

		return mainMenu;
	}

	private JMenuItem createDeleteAllMenu() {
		JMenuItem allMenu = new JMenuItem("All");
		allMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(tabbedPane, "Do you really want to delete ALL ?","Remove All",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(answer == JOptionPane.YES_OPTION){
					loadingWindow.setLocationRelativeTo(tabbedPane);
					//loadingWindow.setVisible(true);

					tabbedPane.removeAll();							

					//loadingWindow.setVisible(false);
				}
			}
		});
		return allMenu;
	}

	/**
	 * <pre>Create a "delete selected" menu item. Menu item title is changed when a tab is selected/changed.
	 * Feature is disabled when there is no tab.
	 * </pre>
	 * @param tabbedPane the target JTabbedPane where the generated MultiplicationTable will be inserted
	 * @return the created menu item
	 */
	private JMenuItem createDeleteSelectedMenu() {
		JMenuItem selectedMenu = new JMenuItem("Selected");
		selectedMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tabbedPane.getSelectedIndex();
				if(index != -1){
					int answer = JOptionPane.showConfirmDialog(tabbedPane, "Do you really want to delete tab ["+tabbedPane.getTitleAt(index)+"] ?","Remove the selected tab",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(answer == JOptionPane.YES_OPTION)
						tabbedPane.remove(index);
				}

			}
		});

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int index = tabbedPane.getSelectedIndex();
				if(index != -1)
					selectedMenu.setText(tabbedPane.getTitleAt(index));
				else
					selectedMenu.setText("Selected");
			}
		});

		return selectedMenu;
	}

	private JMenu createDisplayTypeMenu() {
		JMenu mainMenu = new JMenu("Display Types");

		ButtonGroup group = new ButtonGroup();

		for(String name : factory.getVisitors()){
			JCheckBoxMenuItem button = new JCheckBoxMenuItem(name);

			group.add(button);
			mainMenu.add(button);
			if(factory.getVisitor(name).equals(visitor))
				button.setSelected(true);
		}

		return mainMenu;
	}

	private JMenu createManageColorMenu(){
		JMenu mainmenu = new JMenu("Manage Color");

		mainmenu.add(clearmenu);
		mainmenu.add(generatecolor);

		return mainmenu;
	}

	private ListCellRenderer<Object> createColorListRenderer(final JComboBox<String> box){
		return new DefaultListCellRenderer(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				//the box header is a specific index (-1). apply setXXX on the box will affect the different items of the list...
				if(index == -1){
					box.setForeground(colorsMap.getColor((String)value));
					if(ColorsItems.WHITE.name().equals(value))
						box.setForeground(Color.BLACK);//otherwise WHITE is invisible...
				}
				//color each lines. UNCOLORED will be transparent and need to have specific color 
				setForeground(colorsMap.getColor((String)value));
				
				/*
				 * UNCOLORED item must be recolored with a color, otherwise UNCOLORED will be paint with the previous selected foreground color.
				 * It seems to be due to the fact that box.setXXcolor will color the entire box and not only the selected item...
				 */
				if(ColorsItems.UNCOLORED.name().equals(value))
					setForeground(Color.BLACK);
				
				return this;
			}


		};
	}

}
