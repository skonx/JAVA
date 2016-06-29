package fr.yla.mt.visitor.swing;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

import fr.yla.mt.core.AbstractMT;
import fr.yla.mt.core.OptimizedInitMT;
import fr.yla.mt.gui.swing.MTAPP;

public class MTVisitorDisplayInJTable extends AbstractSwingMTVisitor {

	private final JTabbedPane tabbedPane;

	MTVisitorDisplayInJTable(JTabbedPane tabbedPane) {
		super(null, null);
		this.tabbedPane = tabbedPane;
	}

	private void supply(AbstractMT mt, MTAbstractTableModel model){
		JTable table = new JTable(model);

		table.setForeground(this.getFgColor());
		table.setBackground(this.getBgColor());

		table.setCellSelectionEnabled(true);

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.convertRowIndexToModel(table.getSelectedRow());
				int j = table.convertColumnIndexToModel(table.getSelectedColumn());
				if((i != -1) && (j != -1))
					System.out.println((i+1)+" x "+(j+1)+" = "+table.getValueAt(i, j));
			}

		});

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setGridColor(Color.LIGHT_GRAY);
		//table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(table);

		Integer[] rowHeaders = new Integer[mt.getCapacity()];
		for(int i = 0 ; i<mt.getCapacity();i++)
			rowHeaders[i] = (i+1);

		JList<Integer> rowheader = new JList<Integer>(rowHeaders);
		rowheader.setBackground(Color.LIGHT_GRAY);

		rowheader.setFixedCellHeight(table.getRowHeight());
		scrollPane.setRowHeaderView(rowheader);

		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.LIGHT_GRAY);

		tabbedPane.addTab(mt.getClass().getSimpleName().substring(0, 2).toUpperCase()+(++MTAPP.MTcount), scrollPane);

	}

	@Override
	public void visit(AbstractMT mt) {
		MTAbstractTableModel model = new MTAbstractTableModel(mt);
		supply(mt, model);
	}

	@Override
	public void visit(OptimizedInitMT mt) {
		supply(mt, new MTAbstractTableModel(mt){

			/**
			 * 
			 */
			private static final long serialVersionUID = -4510023928222488135L;

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				return (columnIndex>rowIndex)?mt.getAt(columnIndex, rowIndex):mt.getAt(rowIndex, columnIndex);
			}

		});
	}

	private class MTAbstractTableModel extends AbstractTableModel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4259672552242284608L;
		private final AbstractMT mt;

		private MTAbstractTableModel(AbstractMT mt) {
			super();
			this.mt = mt;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return mt.getAt(rowIndex, columnIndex);
		}

		@Override
		public int getRowCount() {
			return mt.getCapacity();
		}

		@Override
		public int getColumnCount() {
			return mt.getCapacity();
		}

		@Override
		public String getColumnName(int column) {
			return Integer.toString(column+1);
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

	}
}

