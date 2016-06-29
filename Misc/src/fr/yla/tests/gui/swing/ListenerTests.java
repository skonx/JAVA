package fr.yla.tests.gui.swing;

import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ListenerTests {
	
	private static void createActionListener(JButton button,int count){
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Action #"+count+" : "+System.nanoTime());				
			}
		});
	}
	
	public static void main(String[] args) {

		int width = 200;
		int height = 100;

		JFrame frame = new JFrame("Listener tests");
		JPanel panel = new JPanel(new GridBagLayout());

		JButton button1 = new JButton("button 1");

		for(int i = 0; i<5;)
			createActionListener(button1, ++i);

		panel.add(button1);

		frame.setContentPane(panel);

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point screenCenter = env.getCenterPoint();
		Rectangle appBounds = new Rectangle(screenCenter.x-width/2, screenCenter.y-height/2, width, height);

		frame.setBounds(appBounds);


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
