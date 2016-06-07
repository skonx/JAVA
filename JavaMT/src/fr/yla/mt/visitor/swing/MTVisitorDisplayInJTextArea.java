package fr.yla.mt.visitor.swing;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import fr.yla.mt.core.AbstractMT;
import fr.yla.mt.core.OptimizedInitMT;
import fr.yla.mt.gui.swing.MTAPP;

public class MTVisitorDisplayInJTextArea extends AbstractSwingMTVisitor {

	private final JTabbedPane tabbedPane;

	public MTVisitorDisplayInJTextArea(JTabbedPane tabbedPane) {
		super(null,null);
		this.tabbedPane = tabbedPane;
	}

	@Override
	public void visit(AbstractMT mt) {
		JTextArea textArea = new JTextArea();
		textArea.setForeground(this.getFgColor());
		textArea.setBackground(this.getBgColor());
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);

		int capacity = mt.getCapacity();
		int cellsize = mt.getCellSize();

		StringBuilder sb = new StringBuilder();

		sb.append("*** x TABLE "+capacity+" / "+capacity+" ["+mt.getClass().getSimpleName()+"] ***\n");
		for(int i = 0;i<capacity;i++){
			sb.append("\n");
			for(int j = 0;j<capacity;j++){
				int valuesize = Integer.toString(mt.getAt(i, j)).length();
				for(int s = 0;s<(cellsize - valuesize);s++)
					sb.append(" ");
				sb.append(mt.getAt(i, j));
			}		
		}
		sb.append("\n");

		textArea.setText(sb.toString());
		tabbedPane.addTab(mt.getClass().getSimpleName().substring(0, 2).toUpperCase()+(++MTAPP.MTcount), scrollPane);
	}

	@Override
	public void visit(OptimizedInitMT mt) {
		JTextArea textArea = new JTextArea();
		textArea.setForeground(this.getFgColor());
		textArea.setBackground(this.getBgColor());
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);

		StringBuilder sb = new StringBuilder();
		int cap = mt.getCapacity();

		sb.append("*** x TABLE "+cap+" / "+cap+" ["+mt.getClass().getSimpleName()+"] ***\n");

		for(int i = 0;i<cap;i++){
			sb.append("\n");
			for(int j = 0;j<cap;j++){
				int value = mt.getAt(i, j);

				if(j>i)
					value = mt.getAt(j, i);

				int valuesize = Integer.toString(value).length();

				for(int s = 0;s<(mt.getCellSize() - valuesize);s++)
					sb.append(" ");
				sb.append(value);
			}		
		}
		sb.append("\n");
		textArea.setText(sb.toString());
		tabbedPane.addTab(mt.getClass().getSimpleName().substring(0, 2).toUpperCase()+(++MTAPP.MTcount), scrollPane);
	}

}
