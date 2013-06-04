package kr.ac.hanyang.webstart;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import kr.ac.hanyang.common.ImageManager;
import kr.ac.hanyang.entity.AbstractEntity;

public class NodeRenderer extends DefaultTreeCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3058360828693812029L;

	
	
	/**
	 * @uml.property  name="nodeoIcon"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private ImageIcon nodeoIcon;
	/**
	 * @uml.property  name="nodecIcon"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private ImageIcon nodecIcon;
	
//	private ImageIcon rootIcon;
//	private ImageIcon groupIcon;
//	private ImageIcon collectorIcon;
//	private ImageIcon dataSetsIcon;
//	private ImageIcon dataSetIcon;
//	private ImageIcon deviceIcon;

	public NodeRenderer() {
		
	    
//		rootIcon = ImageManager.getImageIcon("root.gif");
//		groupIcon = ImageManager.getImageIcon("group.gif");
//		collectorIcon = ImageManager.getImageIcon("index.gif");
//	    dataSetsIcon = ImageManager.getImageIcon("module.gif");
//		dataSetIcon = ImageManager.getImageIcon("walk.gif");
//		deviceIcon = ImageManager.getImageIcon("leaf_rc.gif");
		
		nodecIcon = ImageManager.getImageIcon("folderc.gif");
	    nodeoIcon = ImageManager.getImageIcon("foldero.gif");
		setOpenIcon(nodeoIcon);
		setClosedIcon(nodecIcon);
	}

	public Dimension getPreferredSize() {
		Dimension superD = super.getPreferredSize();
		return new Dimension(superD.width, 14);
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		if (value instanceof AbstractEntity) {
			AbstractEntity ae = (AbstractEntity) value;
			setFont(getFont());
			setIcon(ae.getIcon());
			setText(ae.getName());
			setToolTipText(ae.getName());
		} else {
			Font f = getFont();
			setFont(new Font(f.getName(), Font.PLAIN, f.getSize()));
			setToolTipText(null);
		}

		return this;
	}

}