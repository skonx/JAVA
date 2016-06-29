package fr.yla.tests.gui.swing;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class FileTreeNode implements TreeNode {

	private final FileTreeNode parent;
	private final File file;
	private List<FileTreeNode> children;

	FileTreeNode(FileTreeNode parent, File file) {
		super();
		this.parent = parent;
		this.file = file;
	}

	private List<FileTreeNode> processChildren() {
		if (children!=null)
			return children;

		File[] files=file.listFiles();
		ArrayList<FileTreeNode> list = new ArrayList<FileTreeNode>(files.length);

		for(File file:files)
			list.add(new FileTreeNode(this,file));

		this.children=list;

		return list;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return processChildren().get(childIndex);
	}

	@Override
	public int getChildCount() {
		return processChildren().size();
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public int getIndex(TreeNode node) {
		return processChildren().indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return !file.isDirectory();
	}

	@Override
	public Enumeration<?> children() {
		return Collections.enumeration(processChildren());
	}

	public static void main(String[] args) {
		String dir = "/Users/jsie/";
		FileTreeNode root = new FileTreeNode(null, new File(dir));
		DefaultTreeModel model = new DefaultTreeModel(root);
		JTree tree = new JTree(model);

		//possible to implement toString() in FileTreeNode
		tree.setCellRenderer(new DefaultTreeCellRenderer() {

			private static final long serialVersionUID = 1L;

			public Component getTreeCellRendererComponent(
					JTree tree,Object value,boolean selected,
					boolean expanded,boolean leaf,
					int row,boolean hasFocus) {
				super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);
				FileTreeNode node=(FileTreeNode)value;
				String label = node.file.getName();
				if(leaf)
					label=label+" : "+node.file.length()/1024+" KB";
				setText(label);

				return this;
			}
		});
		
		JFrame frame = new JFrame(dir);
		JScrollPane scrollPane = new JScrollPane(tree);
		frame.add(scrollPane);

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point screenCenter = env.getCenterPoint();
		Rectangle appBounds = new Rectangle(screenCenter.x-500/2, screenCenter.y-400/2, 500, 400);

		frame.setBounds(appBounds);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);



	}

}
