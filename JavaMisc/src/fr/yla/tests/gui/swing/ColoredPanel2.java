package fr.yla.tests.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class ColoredPanel2 {

	private enum ColorsItems{
		GREEN (Color.GREEN),
		RED (Color.RED),
		BLUE (Color.BLUE),
		YELLOW (Color.YELLOW),
		ORANGE (Color.ORANGE),
		MAGENTA (Color.MAGENTA),
		PINK (Color.PINK);

		private final Color color;

		private ColorsItems(Color color) {
			this.color = color;
		}

		private final static String lookUp(Color color){
			for(ColorsItems c : ColorsItems.values())
				if(c.color.equals(color))
					return c.name();
			return "uncolored";
		}
	}

	private static void createDisplayBox(final JComponent component, final JTextArea textArea) {
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(400,100));
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				/* can solve the automatic scroll down issue but block the scroll up on the scrollbar...
				 * e.getAdjustable().setValue(e.getAdjustable().getMaximum());
				 */
				System.out.println("Scrollpane adjusted at "+LocalDateTime.now());}
		});

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(Box.createVerticalGlue());
		box.add(scrollPane);
		box.add(Box.createVerticalGlue());
		component.add(box);
	}

	private static JButton createClearButton(final JTextArea textArea){

		JButton button = new JButton("CLEAR");
		button.addActionListener(e->textArea.setText(null));
		return button;
	}

	private static void createPopupMenu(final JComponent component, final JTextArea textArea){
		JMenuItem whatIs = new JMenuItem("What is the current color?");

		whatIs.addActionListener(e->textArea.append("->"+ColorsItems.lookUp(component.getBackground())+"\n"));
		JPopupMenu popMenu = new JPopupMenu();
		popMenu.add(whatIs);
		component.setComponentPopupMenu(popMenu);
	}

	private static JMenu createBackgroundColorMenu(final JComponent component, final JTextArea textArea) {
		JMenu menu=new JMenu("Background color"); 
		ButtonGroup group=new ButtonGroup();
		for(final ColorsItems item:ColorsItems.values()) {
			JCheckBoxMenuItem menuItem=new JCheckBoxMenuItem(item.name()); 
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					component.setBackground(item.color);	
					textArea.append("["+item.name()+"]\n");
				}
			});
			menuItem.setForeground(item.color);
			menu.add(menuItem);
			group.add(menuItem);
		}
		return menu;
	}

	private static void addKeyListener(JFrame frame,JComponent component, JTextArea textArea){
		frame.setFocusable(true);
		/*
		 * Focus is lost when another item is selected on the frame...
		 */
		Map<Character, ColorsItems> colorMap = new HashMap<>();

		for(ColorsItems c : ColorsItems.values())
			colorMap.put(c.name().toLowerCase().charAt(0), c);

		frame.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				ColorsItems item = colorMap.get(c);
				if(item!=null){
					component.setBackground(item.color);
					textArea.append("("+item.name()+")\n");
				}
				else{
					JOptionPane.showMessageDialog(frame,"Key \'"+c+"\' : Unknown shortcut ! \nThe current color is displayed...","Unknown Color",JOptionPane.WARNING_MESSAGE);
					textArea.append("->"+ColorsItems.lookUp(component.getBackground())+"\n");
				}
			}
		});
	}

	public static void main(String[] args) {

		JPanel panel=new JPanel();
		JTextArea textArea = new JTextArea("What color will you select...\n");
		textArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		createPopupMenu(panel, textArea);

		JMenuBar menuBar=new JMenuBar(); 
		JMenu edit=new JMenu("Edit"); 
		menuBar.add(edit);

		edit.add(createBackgroundColorMenu(panel,textArea));

		panel.setLayout(new BorderLayout());
		createDisplayBox(panel,textArea);
		panel.add(createClearButton(textArea),BorderLayout.SOUTH);

		JFrame frame=new JFrame("Color the Panel"); 
		addKeyListener(frame, panel, textArea);

		frame.setJMenuBar(menuBar); 
		frame.setContentPane(panel);

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point screenCenter = env.getCenterPoint();
		Rectangle appBounds = new Rectangle(screenCenter.x-400/2, screenCenter.y-300/2, 400, 300);

		frame.setBounds(appBounds);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}

