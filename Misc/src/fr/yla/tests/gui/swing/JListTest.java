package fr.yla.tests.gui.swing;

import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JListTest {

	private static int count = 0;
	
	public static void main(String[] args) {
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> list = new JList<>(model);

		JFrame frame = new JFrame("JListTest");

		JButton newButton = new JButton("NEW");
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.addElement("element #"+(++count));
			}
		});

		JButton removeButton = new JButton("REMOVE");
		removeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(index != -1)
					model.removeElementAt(index);
				else
					if(!model.isEmpty())
						model.removeElementAt(model.size()-1);//remove the end of the list
			}
		});

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.gridheight=1;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.anchor=GridBagConstraints.CENTER;
		gbc.fill=GridBagConstraints.BOTH;
		panel.add(new JScrollPane(list),gbc);
		gbc.weighty=0.0;//the buttons will keep their original dimensions after a window resizing
		panel.add(newButton,gbc);
		panel.add(removeButton,gbc);

		frame.setContentPane(panel);
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point screenCenter = env.getCenterPoint();
		Rectangle appBounds = new Rectangle(screenCenter.x-400/2, screenCenter.y-300/2, 400, 300);

		frame.setBounds(appBounds);


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
