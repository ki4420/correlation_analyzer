package kr.ac.hanyang.webstart;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.tree.TreePath;

import kr.ac.hanyang.collector.jmx.JMXCollectorEditorDlg;
import kr.ac.hanyang.common.TableModel;
import kr.ac.hanyang.common.Util;
import kr.ac.hanyang.entity.AbstractEntity;
import kr.ac.hanyang.entity.Collector;
import kr.ac.hanyang.entity.DataSets;
import kr.ac.hanyang.entity.Group;
import kr.ac.hanyang.entity.Root;
import kr.ac.hanyang.webstart.internal_frame.CollectorFrm;
import kr.ac.hanyang.webstart.internal_frame.CorrelationFrm;
import kr.ac.hanyang.webstart.internal_frame.RealtimeCollectorFrm;
import kr.ac.hanyang.webstart.internal_frame.ShiftCorrelationFrm;

public class ActionProcessor {

	public final static String WORKSPACE_EXTENSION = "ws";
	public final static String[] WS_EXTENSION_ARRAY = { WORKSPACE_EXTENSION };

	public final static String OPEN_WS = "OPEN WORKSPACE";
	public final static String SAVE_WS = "SAVE WORKSPACE";

	public final static String LOAD_CSV = "LOAD CSV";
	public final static String SAVE_CSV = "SAVE CSV";
	public final static String ADD_DATASETS = "ADD DATA SETS";
	
	public final static String ADD_COLLECTOR = "ADD COLLECTOR";
	public final static String ADD_GROUP = "ADD GROUP";

	public final static String COR = "COR";
	public final static String SHIFT_COR = "SHIFT COR";
	public final static String COLLECT = "COLLECT";
	public final static String REALTIME_COLLECT = "REALTIME COLLECT";
	public final static String STOP = "STOP";

//	public final static String GET = "GET";
//	public final static String GET_NEXT = "GET NEXT";
//	public final static String GET_BULK = "GET BULK";
//	public final static String GET_SUBTREE = "GET SUBTREE";
//	public final static String SET = "SET";
//	public final static String WALK = "WALK";

	public final static String REPORT = "REPORT";

	public final static String EXIT = "Exit";
	public final static String FIND_IN_TREE = "Find in Tree";
	public final static String FIND_IN_RESULT = "Find in Result";
	public final static String OPTIONS = "Options";
	public final static String USER_GUIDE = "User Guide";
	public final static String ABOUT = "About";
	public final static String TREE_SELECTION = "TREE_SELECTION";

	/**
	 * @uml.property  name="layoutPanel"
	 * @uml.associationEnd  
	 */
	private LayoutPanel layoutPanel = null;
	
	/**
	 * @uml.property  name="rootFrm"
	 * @uml.associationEnd  
	 */
	private MainFrame rootFrm;

	/**
	 * @uml.property  name="fileChooser"
	 * @uml.associationEnd  
	 */
	private JFileChooser fileChooser = null;// new JFileChooser();

	public ActionProcessor(MainFrame mf) {
		rootFrm = mf;
	}

	/**
	 * @param layoutPanel
	 * @uml.property  name="layoutPanel"
	 */
	public void setLayoutPanel(LayoutPanel layoutPanel) {
		this.layoutPanel = layoutPanel;
	}
	
	private AbstractEntity getLastSelectedNode() {
		AbstractEntity result = null;
		TreePath path = layoutPanel.getTree().getSelectionPath();
		
		if (path != null) {
			result = (AbstractEntity)path.getLastPathComponent();
		} else {
			result = (AbstractEntity) layoutPanel.getTree().getModel().getRoot();
			if(result != null)
				path = new TreePath(result);
		}
		
		result.setTreePath(path);
		return result;
	}
	
	private void findDataSets(ArrayList<DataSets> result, AbstractEntity currentNode) {
		// 종료시점
		if(currentNode == null)
			return;
		//검사
		if(currentNode instanceof DataSets) {
			result.add((DataSets)currentNode);
		}
		//재귀호출
		Vector<AbstractEntity> children = currentNode.getChildren();
		for(int i = 0, end = children.size(); i < end; i++) {
			AbstractEntity child = (AbstractEntity)children.get(i);
			findDataSets(result, child);
		}
	}

	public void actionProcess(String actionCommand) {
		AbstractEntity node = getLastSelectedNode();
		Util.debug(this, "[actionProcess]actionCommand=" + actionCommand+", selectedNode="+node);
		
		if (COLLECT.equals(actionCommand)) {
			if (node instanceof Collector)  { 
				CollectorFrm frm = new CollectorFrm((Collector)node);
				MainFrame.addFrame(frm);
			}
		} else if (REALTIME_COLLECT.equals(actionCommand)) {
			if (node instanceof Collector)  { 
				RealtimeCollectorFrm frm = new RealtimeCollectorFrm((Collector)node);
				MainFrame.addFrame(frm);
			}
		} else if (COR.equals(actionCommand)) {
			ArrayList<DataSets> sources = new ArrayList<DataSets>();
			findDataSets(sources, getLastSelectedNode());

			CorrelationFrm frm = new CorrelationFrm(sources);
			MainFrame.addFrame(frm);
			
		}  else if (SHIFT_COR.equals(actionCommand)) {
			ArrayList<DataSets> sources = new ArrayList<DataSets>();
			findDataSets(sources, getLastSelectedNode());

			ShiftCorrelationFrm frm = new ShiftCorrelationFrm(sources);
			MainFrame.addFrame(frm);
			
		} else if (OPEN_WS.equals(actionCommand)) {

			Root root = (Root) Util.openFile(layoutPanel, OPEN_WS,
					WS_EXTENSION_ARRAY);
			layoutPanel.getTreePanel().setTreeModel(root);

		} else if (SAVE_WS.equals(actionCommand)) {

			Util.saveFile(layoutPanel, layoutPanel.getTree().getModel()
					.getRoot(), SAVE_WS, WORKSPACE_EXTENSION);

		} else if (LOAD_CSV.equals(actionCommand)) {
			TableModel model = Util.loadCSV(layoutPanel);
			
			DataSets dataSets = new DataSets();
			dataSets.setTableModel(model);
			dataSets.setProperty(DataSets.NAME, model.getPath());
			dataSets.setProperty(DataSets.PATH, model.getPath());
			
			addTree(dataSets);
			
			CollectorFrm frm = new CollectorFrm(model);
			MainFrame.addFrame(frm);

		} else if (ADD_COLLECTOR.equals(actionCommand)) {
			Collector collector = null;
			if (node instanceof Collector)  { 
				collector = (Collector)node;
			}
			new JMXCollectorEditorDlg(this, collector);

		} else if (ADD_GROUP.equals(actionCommand)) {
			InputDialog inputDialog = new InputDialog(rootFrm, "Input Group name");
			inputDialog.setVisible(true);
			String value = inputDialog.getInputValue();
			Util.debug(this, "Create group name, value="+value);
			if (value == null || value.equals(""))
				return;

			try {
				Group group = new Group();
				group.setProperty(AbstractEntity.NAME, value);

				TreePath path = layoutPanel.getTree().getSelectionPath();
				
				if ((path == null) || (path.getPathCount() <= 1)) {
					
					AbstractEntity root = (AbstractEntity) layoutPanel.getTree().getModel()
					.getRoot();
					root.getChildren().add(group);
					layoutPanel.getTreePanel().setTreeModel((Root)root);

				} else {
					Object o = path.getLastPathComponent();

					if (o instanceof AbstractEntity) {

						AbstractEntity ae = (AbstractEntity) o;
						ae.getChildren().add(group);
						layoutPanel.getTree().expandPath(path);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		} else if (REPORT.equals(actionCommand)) {

			if (node instanceof AbstractEntity && node != null) {
				layoutPanel.setReport(node.report());
			}
		} else if (TREE_SELECTION.equals(actionCommand)) { // TREE_SELECTION
			if (node instanceof AbstractEntity && node != null) {
				layoutPanel.setReport(node.report());
				if (node instanceof Collector) {
					new JMXCollectorEditorDlg(this, (Collector) node);
				} else if (node instanceof Group) {
					InputDialog inputDialog = new InputDialog(rootFrm, "Input Group name", node.getName());
					inputDialog.setVisible(true);
					String value = inputDialog.getInputValue();
					Util.debug(this, "Create group name, value="+value);
					if (value == null || value.equals(""))
						return;
					node.setProperty(AbstractEntity.NAME, value);
					updateTree(node);
				}
			}
		} else {
			Util.debug(this, "[actionProcess] unknown command=" + actionCommand);
		}
	}
	
	

	public void updateTree(AbstractEntity node) {
		WorkspaceTreeModel wTreeModel = (WorkspaceTreeModel) layoutPanel
				.getTree().getModel();
		wTreeModel.update(node);
	}

	public void addTree(AbstractEntity node) {
		WorkspaceTreeModel wTreeModel = (WorkspaceTreeModel) layoutPanel
				.getTree().getModel();
		AbstractEntity parent = (AbstractEntity) layoutPanel.getTree()
				.getLastSelectedPathComponent();
		if (parent == null)
			parent = (AbstractEntity) layoutPanel.getTree().getModel()
					.getRoot();
		wTreeModel.add(parent, node);
		
		TreePath path = layoutPanel.getTree().getSelectionPath();
		
		if ((path == null) || (path.getPathCount() <= 1)) {
			
			AbstractEntity root = (AbstractEntity) layoutPanel.getTree().getModel()
			.getRoot();
			layoutPanel.getTreePanel().setTreeModel((Root)root);

		} else {
			Object o = path.getLastPathComponent();
			layoutPanel.getTree().expandPath(path);
		}
	}

	public void removeTree(AbstractEntity node) {
		WorkspaceTreeModel wTreeModel = (WorkspaceTreeModel) layoutPanel
				.getTree().getModel();
		wTreeModel.remove(node);
	}
}
