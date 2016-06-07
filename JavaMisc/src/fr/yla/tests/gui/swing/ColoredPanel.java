package fr.yla.tests.gui.swing;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ColoredPanel {

	private static long click = 0L;

	private static void addButton(JPanel panel, String name, Color color){
		JButton button = new JButton(name);
		button.setName(name);//mandatory to use event.getElement() method, otherwise will return null
		button.setForeground(color);
		button.addActionListener(e->panel.setBackground(color));
		button.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseExited(MouseEvent e){
				System.out.println("Move from "+name);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Mouse click : "+(++click));
			}
		});
		panel.add(button);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("ColoredPanel");

		JPanel panel = new JPanel();

		addButton(panel, "GREEN", Color.GREEN);
		addButton(panel, "RED", Color.RED);
		addButton(panel, "BLUE", Color.BLUE);
		addButton(panel, "YELLOW", Color.YELLOW);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e){
				System.out.println("BYE");
			}
		});

		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//DO_NOTHING_ON_CLOSE otherwise unable to display the exit window
		frame.setBounds(100, 100, 450, 300);
		frame.setVisible(true);
	}

}
