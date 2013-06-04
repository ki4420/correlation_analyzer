package kr.ac.hanyang.webstart;

/*
 * $Id: TreePanel.java,v 1.1 2011/02/08 01:54:53 space32 Exp $
 * Copyright (C) 2005 Rui Pedro Lopes (rlopes at ipb dot pt)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 *
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import kr.ac.hanyang.common.ImageManager;
import kr.ac.hanyang.common.Util;
import kr.ac.hanyang.entity.AbstractEntity;
import kr.ac.hanyang.entity.Collector;
import kr.ac.hanyang.entity.DataSet;
import kr.ac.hanyang.entity.DataSets;
import kr.ac.hanyang.entity.Group;
import kr.ac.hanyang.entity.Root;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class TreePanel extends JPanel implements TreeSelectionListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1998322648183697530L;
	/**
	 * @uml.property  name="model"
	 * @uml.associationEnd  
	 */
	WorkspaceTreeModel model = null;
	/**
	 * @uml.property  name="verticalSplitPane"
	 * @uml.associationEnd  
	 */
	private JSplitPane verticalSplitPane;
	/**
	 * @uml.property  name="selectedNodesPanel"
	 * @uml.associationEnd  
	 */
	private JPanel selectedNodesPanel;
	/**
	 * @uml.property  name="workspace"
	 * @uml.associationEnd  
	 */
	private JPanel workspace;

	/**
	 * @uml.property  name="searchComboBox"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JComboBox searchComboBox = new JComboBox();

	/**
	 * @uml.property  name="tree"
	 * @uml.associationEnd  
	 */
	Tree tree = null;
	
	/**
	 * @uml.property  name="removeListAction"
	 * @uml.associationEnd  
	 */
	Action removeListAction = null;
	
	/**
	 * @uml.property  name="removeTreeNodeAction"
	 * @uml.associationEnd  
	 */
	Action removeTreeNodeAction = null;

	/**
	 * @uml.property  name="refreshAction"
	 * @uml.associationEnd  
	 */
	Action refreshAction = null;
	
	/**
	 * @uml.property  name="upAction"
	 * @uml.associationEnd  
	 */
	Action upAction = null;

//	Action openAction = null;

//	Action closeAction = null;

	/**
	 * @uml.property  name="fileChooser"
	 * @uml.associationEnd  
	 */
	JFileChooser fileChooser = null;

	/**
	 * @uml.property  name="actionProcessor"
	 * @uml.associationEnd  
	 */
	ActionProcessor actionProcessor;

	public static final Font FONT12_PLAIN = new Font("SansSerif", Font.PLAIN, 12);
	
	
	
	public WorkspaceTreeModel getTreeModel() {

		return model;
	}
	
	private void makeRootMenu(JPopupMenu _popupMenu) {
		JMenuItem AddGroupItem = new JMenuItem("Add Group");
		AddGroupItem.setFont(FONT12_PLAIN);
		_popupMenu.add(AddGroupItem);
		AddGroupItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Add Group");
				actionProcessor.actionProcess(ActionProcessor.ADD_GROUP);
			}
		});
		JMenuItem addCollectorItem = new JMenuItem("Add Collector");
		addCollectorItem.setFont(FONT12_PLAIN);
		_popupMenu.add(addCollectorItem);
		addCollectorItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Add Collector");
				actionProcessor.actionProcess(ActionProcessor.ADD_COLLECTOR);
				
			}
		});
		JMenuItem AddCsvItem = new JMenuItem("Add CSV");
		AddCsvItem.setFont(FONT12_PLAIN);
		_popupMenu.add(AddCsvItem);
		AddCsvItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Add CSV");
				//TODO
			}
		});
	}
	
	private void makeDataSetsMenu(JPopupMenu _popupMenu) {
		
		JMenuItem copyItem = new JMenuItem("Copy");
		copyItem.setFont(FONT12_PLAIN);
		_popupMenu.add(copyItem);
		copyItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Copy");
				//TODO
			}
		});
		JMenuItem pasteItem = new JMenuItem("Paste");
		pasteItem.setFont(FONT12_PLAIN);
		_popupMenu.add(pasteItem);
		pasteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Paste");
				//TODO
			}
		});
		_popupMenu.addSeparator();
		JMenuItem deleteItem = new JMenuItem("Delete");
		deleteItem.setFont(FONT12_PLAIN);
		_popupMenu.add(deleteItem);
		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Delete");
				//TODO
				deleteNode();
			}
		});
		
	}
	
	private void makeCollectorMenu(JPopupMenu _popupMenu) {
		JMenuItem collectItem = new JMenuItem(ActionProcessor.COLLECT);
		collectItem.setFont(FONT12_PLAIN);
		_popupMenu.add(collectItem);
		collectItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Collect");
				actionProcessor.actionProcess(ActionProcessor.COLLECT);
			}
		});
		JMenuItem stopItem = new JMenuItem(ActionProcessor.STOP);
		stopItem.setFont(FONT12_PLAIN);
		_popupMenu.add(stopItem);
		stopItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Stop");
				actionProcessor.actionProcess(ActionProcessor.STOP);
			}
		});
		_popupMenu.addSeparator();
		
		
		
		JMenuItem editItem = new JMenuItem("Edit");
		editItem.setFont(FONT12_PLAIN);
		_popupMenu.add(editItem);
		editItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Edit");
				
			}
		});
		JMenuItem copyItem = new JMenuItem("Copy");
		copyItem.setFont(FONT12_PLAIN);
		_popupMenu.add(copyItem);
		copyItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Copy");
				//TODO
			}
		});
		JMenuItem pasteItem = new JMenuItem("Paste");
		pasteItem.setFont(FONT12_PLAIN);
		_popupMenu.add(pasteItem);
		pasteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Paste");
				//TODO
			}
		});
		_popupMenu.addSeparator();
		JMenuItem deleteItem = new JMenuItem("Delete");
		deleteItem.setFont(FONT12_PLAIN);
		_popupMenu.add(deleteItem);
		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Delete");
				//TODO
				deleteNode();
			}
		});
		
	}
	
	
	private void makeGroupMenu(JPopupMenu _popupMenu) {
		JMenuItem editItem = new JMenuItem("Edit");
		editItem.setFont(FONT12_PLAIN);
		_popupMenu.add(editItem);
		editItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Edit");
				//TODO
				
			}
		});
		JMenuItem copyItem = new JMenuItem("Copy");
		copyItem.setFont(FONT12_PLAIN);
		_popupMenu.add(copyItem);
		copyItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Copy");
				//TODO
			}
		});
		JMenuItem pasteItem = new JMenuItem("Paste");
		pasteItem.setFont(FONT12_PLAIN);
		_popupMenu.add(pasteItem);
		pasteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Paste");
				//TODO
			}
		});
		_popupMenu.addSeparator();
		JMenuItem addGroupItem = new JMenuItem("Add Group");
		addGroupItem.setFont(FONT12_PLAIN);
		_popupMenu.add(addGroupItem);
		addGroupItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Add Group");
				actionProcessor.actionProcess(ActionProcessor.ADD_COLLECTOR);
			}
		});
		JMenuItem addCollectorItem = new JMenuItem("Add Collector");
		addCollectorItem.setFont(FONT12_PLAIN);
		_popupMenu.add(addCollectorItem);
		addCollectorItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Add Collector");
				actionProcessor.actionProcess(ActionProcessor.ADD_COLLECTOR);
			}
		});
		
		JMenuItem addCsvItem = new JMenuItem("Add CSV");
		addCsvItem.setFont(FONT12_PLAIN);
		_popupMenu.add(addCsvItem);
		addCsvItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Add CSV");
				//TODO
				
			}
		});
		_popupMenu.addSeparator();
		JMenuItem deleteItem = new JMenuItem("Delete");
		deleteItem.setFont(FONT12_PLAIN);
		_popupMenu.add(deleteItem);
		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.debug(this, "Delete");
				//TODO
				deleteNode();
			}
		});
		
	}
	
	private void deleteNode() {
		AbstractEntity ae = (AbstractEntity)tree.getLastSelectedPathComponent();
		this.actionProcessor.removeTree(ae);
//		tree
	}

	public TreePanel(ActionProcessor ap) {
		this(new WorkspaceTreeModel(new Root()));
		this.actionProcessor = ap;
		tree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				actionProcessor.actionProcess(ActionProcessor.REPORT);
				
				if(evt.getClickCount() > 1) {
					actionProcessor.actionProcess(ActionProcessor.TREE_SELECTION);
				}
			}
			
			
			public void mouseReleased(MouseEvent e) {
				try {
					Object source = e.getSource();
					JPopupMenu popupMenu = null;
					// 팝업을 띄우기 위해 마우스의 오른쪽 버튼을 클릭 했을 때.
					if (e.isPopupTrigger() && source instanceof Tree) {
						Util.debug(this, "source="+source);
						AbstractEntity ae = (AbstractEntity)((Tree)source).getLastSelectedPathComponent();
						if(ae instanceof Collector) {
							Util.debug(this, "Collector");
							popupMenu = new JPopupMenu();
							makeCollectorMenu(popupMenu);
							
						} else if(ae instanceof Group) {
							Util.debug(this, "Group");
							popupMenu = new JPopupMenu();
							makeGroupMenu(popupMenu);	
							
						} else if(ae instanceof DataSets) {
							Util.debug(this, "DataSets");
							popupMenu = new JPopupMenu();
							makeDataSetsMenu(popupMenu);	
							
						} else if(ae instanceof Root) {
							Util.debug(this, "Root");
							popupMenu = new JPopupMenu();
							makeRootMenu(popupMenu);	
							
						} else {
							Util.debug(this, "ae="+ae);
							popupMenu = new JPopupMenu();
						}
					}
					
					if (popupMenu != null && popupMenu.getComponentCount() > 0) {
						int popupHeight = getPopupHeight(popupMenu.getComponents());// 보이려는 팝업의 높이
						int popupWidth = maxWidth;// 보이려는 팝업의 너비

						int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
						int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

						int frmX = getX();
						int frmY = getY();

						int resultX = e.getX();
						int resultY = e.getY();

						Point xy = SwingUtilities.convertPoint((Component) e.getSource(), e.getX(), e.getY(), tree);
						if (width - frmX - (int)xy.getX() - popupWidth < 0) {
							resultX -= popupWidth;
						}
						if (height - frmY - (int)xy.getY() - popupHeight < 0) {
							resultY -= popupHeight;
						}
						popupMenu.show(e.getComponent(), resultX, resultY);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

	}
	/**
	 * @uml.property  name="maxWidth"
	 */
	private int maxWidth = 0;
	private int getPopupHeight(Component[] comps) {
		int height = 0;
		maxWidth = 0;
		
		for (int i=0, end = comps.length ; i< end; i++) {
			if (comps[i] instanceof JMenuItem) {
				height += comps[i].getHeight();
			} else {
				height += 2;
			}

			if (comps[i].getWidth()+3 > maxWidth) {
				maxWidth = comps[i].getWidth()+3;
			}
		}
		return height + 6;
	}
	
	public TreePanel(WorkspaceTreeModel m) {
		createActions();
		setLayout(new BorderLayout());
		this.model = m;

		this.add(getVerticalSplitPane(), BorderLayout.CENTER);

		// fileChooser = new JFileChooser();

		// Listen for when the selection changes.

	}

	public void valueChanged(TreeSelectionEvent e) {
//		closeAction.setEnabled(!tree.isSelectionEmpty());
	}

	/**
	 * @return
	 * @uml.property  name="tree"
	 */
	public JTree getTree() {
		return tree;
	}
	
	public Object[] getTargetDataSets() {
		Object[] result = null;
		DefaultComboBoxModel model = (DefaultComboBoxModel)selectedNodeList.getModel();
		result = selectedNodeList.getSelectedValues();
		return result;
		
	}

	protected void createActions() {
		removeListAction = new AbstractAction("RemoveList", getCloseIcon()) {
			public void actionPerformed(ActionEvent e) {
				DefaultComboBoxModel model = (DefaultComboBoxModel)selectedNodeList.getModel();
				DataSet dataSet = (DataSet)selectedNodeList.getSelectedValue();
				if(dataSet != null) {
					
					model.removeElement(dataSet);
					if(model.getSize() > 0) {
						selectedNodeList.setSelectedIndex(0);
					}
				}
			}
		};
		removeTreeNodeAction = new AbstractAction("RemoveTreeNode", getCloseIcon()) {
			public void actionPerformed(ActionEvent e) {
				// model.refresh();
			}
		};
		
		refreshAction = new AbstractAction("Refresh", getRefreshIcon()) {
			public void actionPerformed(ActionEvent e) {
				// model.refresh();
			}
		};

		upAction = new AbstractAction("Up", getUpIcon()) {
			public void actionPerformed(ActionEvent e) {
				DefaultComboBoxModel model = (DefaultComboBoxModel)selectedNodeList.getModel();
				DataSet ds = (DataSet)tree.getLastSelectedPathComponent();
				if(ds != null) {
					model.addElement(ds);
					if(model.getSize() > 0) {
						selectedNodeList.setSelectedValue(ds, true);
					}
				}
			}
		};
		
	}
	
	protected JToolBar createSelectedToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
		
		JLabel infoLabel = new JLabel("분석할 항목을 드래그 하시오.");
		toolBar.add(infoLabel);
		infoLabel.setPreferredSize(new java.awt.Dimension(1000, 25)); 
		JButton button = toolBar.add(removeListAction);
		button.setText(""); // an icon-only button
		return toolBar;
	}

	protected JToolBar createWSToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		searchComboBox = new JComboBox();
		ComboBoxModel searchComboBoxModel = new DefaultComboBoxModel(
				new String[] { "검색 대상을 입력 하시오." });
		searchComboBox.setModel(searchComboBoxModel);

		searchComboBox.setEditable(true);
		toolBar.add(searchComboBox);
		searchComboBox.setPreferredSize(new java.awt.Dimension(100, 25));
		JButton button = toolBar.add(upAction);
		button.setText(""); // an icon-only button
		button = toolBar.add(refreshAction);
		button.setText(""); // an icon-only button

		button = toolBar.add(removeTreeNodeAction);
		button.setText(""); // an icon-only button
		return toolBar;
	}

	public ImageIcon getCloseIcon() {
		return ImageManager.getImageIcon("close.gif");
	}

	public ImageIcon getUpIcon() {
		return ImageManager.getImageIcon("get.gif");
	}
	
	public ImageIcon getRefreshIcon() {
		return ImageManager.getImageIcon("refresh.gif");
	}
	
	public void setTreeModel(Root root) {
		model.updateRoot(root);
	}
	

	/**
	 * @return
	 * @uml.property  name="workspace"
	 */
	private JPanel getWorkspace() {
		if (workspace == null) {
			workspace = new JPanel();
			BorderLayout workspaceLayout = new BorderLayout();
			workspace.setLayout(workspaceLayout);
			workspace.add(createWSToolBar(), BorderLayout.NORTH);
			
			tree = new Tree(model);
			tree.setCellRenderer(new NodeRenderer());
			tree.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree.addTreeSelectionListener(this);
			JScrollPane scrollPane = new JScrollPane(tree);
			workspace.add(scrollPane, BorderLayout.CENTER);

		}
		return workspace;
	}

	/**
	 * @return
	 * @uml.property  name="selectedNodesPanel"
	 */
	private JPanel getSelectedNodesPanel() {
		
		if (selectedNodesPanel == null) {
			selectedNodesPanel = new JPanel();//new JScrollPane();
			BorderLayout layout = new BorderLayout();
			selectedNodesPanel.setLayout(layout);
			selectedNodesPanel.add(createSelectedToolBar(), BorderLayout.NORTH);
			
			
			
			ComboBoxModel jComboBoxIntervalModel = 
				new DefaultComboBoxModel(
						new Object[] { 
							
						}
				);
			selectedNodeList = new JList(jComboBoxIntervalModel);
			selectedNodeList.setCellRenderer(new IconListRenderer());
		
			JScrollPane scrollPane = new JScrollPane(selectedNodeList);
			selectedNodesPanel.add(scrollPane, BorderLayout.CENTER);
		}
		return selectedNodesPanel;
	}
	
	public class IconListRenderer extends DefaultListCellRenderer {
	    public Component getListCellRendererComponent(
	            JList list, Object value, int index,
	            boolean isSelected, boolean cellHasFocus) {
	        JLabel label = (JLabel) super.getListCellRendererComponent(
	                list, value, index, isSelected, cellHasFocus);
	        Icon icon = ((AbstractEntity)value).getIcon();
	        label.setIcon(icon);
	        return label;
	    }
	}
	
	/**
	 * @uml.property  name="selectedNodeList"
	 * @uml.associationEnd  
	 */
	private JList selectedNodeList;

	/**
	 * @return
	 * @uml.property  name="verticalSplitPane"
	 */
	private JSplitPane getVerticalSplitPane() {
		if (verticalSplitPane == null) {
			verticalSplitPane = new JSplitPane();
			verticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			verticalSplitPane.setDividerLocation(100);
			verticalSplitPane.setDoubleBuffered(true);
			verticalSplitPane.add(getSelectedNodesPanel(), JSplitPane.TOP);
			verticalSplitPane.add(getWorkspace(), JSplitPane.BOTTOM);

		}
		return verticalSplitPane;
	}

	public void actionPerformed(ActionEvent e) {
		Util.debug(this, "actionPerformed, e="+e);
		
	}

}