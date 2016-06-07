package fr.yla.tests.gui.swing;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SpinnerTest {

	public static void main(String[] args) {
		SpinnerNumberModel model=new SpinnerNumberModel(50,0,100,1); JPanel panel=new JPanel(null);
		BoxLayout layout=new BoxLayout(panel,BoxLayout.Y_AXIS); panel.setLayout(layout);
		for(int i=0;i<5;i++) {
		JSpinner spinner=new JSpinner(model); panel.add(spinner);
		}
		JFrame frame=new JFrame(); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.setContentPane(panel);
		frame.setSize(400,300);
		frame.setVisible(true);
	}

}
