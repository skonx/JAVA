package fr.yla.tests.gui.swing;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingWorker;

public class JFileList {
	public static void main(String[] args) throws InterruptedException, ExecutionException { 
		final DefaultListModel<File> model=new DefaultListModel<>();
		SwingWorker<Integer,File> worker=new SwingWorker<Integer,File>() {

			@Override protected Integer doInBackground() { 
				return traverse(model,new File("/Users/jsie/git"));
			}

			private int traverse(final ListModel<File> model,File file) {
				final File[] files=file.listFiles(); 

				if (files==null)
					return 0;

				int sum=0;

				for(File f:files)
					if (f.isDirectory()) 
						sum+=traverse(model,f);

				publish(files);

				return sum+files.length; 
			}

			@Override protected void process(List<File> files) { 
				files.forEach(model::addElement);	
			} 
		};
		
		worker.execute(); 
		
		JList<File> list=new JList<>(model);
		JFrame frame=new JFrame("Swing Worker Sample"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setContentPane(new JScrollPane(list)); 
		frame.pack();
		frame.setVisible(true);
		System.out.println("#files : "+worker.get());
	}
}
