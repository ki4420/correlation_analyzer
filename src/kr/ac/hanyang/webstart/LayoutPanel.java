package kr.ac.hanyang.webstart;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTree;

import kr.ac.hanyang.webstart.internal_frame.AbstractInternalFrame;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class LayoutPanel extends JPanel {
	
	/**
	 * @uml.property  name="actionProcessor"
	 * @uml.associationEnd  
	 */
	private ActionProcessor actionProcessor = null;
	
	/**
	 * @uml.property  name="controlPanel"
	 * @uml.associationEnd  
	 */
	private ToolBar controlPanel;
	
	/**
	 * @uml.property  name="statusPanel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JPanel statusPanel;
	/**
	 * @uml.property  name="statusBar"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JProgressBar statusBar;
	/**
	 * @uml.property  name="statusLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JLabel statusLabel;
	/**
	 * @uml.property  name="reportPanel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JPanel reportPanel;
	/**
	 * @uml.property  name="treePanel"
	 * @uml.associationEnd  
	 */
	private TreePanel treePanel;

	/**
	 * @uml.property  name="contentPanel"
	 * @uml.associationEnd  
	 */
	private ContentPanel contentPanel;

	/**
	 * @uml.property  name="leftSplitPane"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JSplitPane leftSplitPane;
	/**
	 * @uml.property  name="contentSplitPane"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JSplitPane contentSplitPane;

	public LayoutPanel(ActionProcessor ap) {
		actionProcessor = ap;
		actionProcessor.setLayoutPanel(this);
		initGUI();
	}
	
	/**
	 * @return
	 * @uml.property  name="treePanel"
	 */
	public TreePanel getTreePanel() {
		return treePanel;
	}
	
	public JTree getTree() {
		return treePanel.getTree();
	}
	
	public void setReport(String r) {
		ReportPanel rp = null;
		if(reportPanel.getComponentCount() > 0) {
			rp = (ReportPanel)reportPanel.getComponent(0);
			rp.setReport(r);
		} else {
			rp = new ReportPanel(r);
			reportPanel.add(rp, BorderLayout.CENTER);
			
		}
		reportPanel.updateUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(1137, 775));
			controlPanel = new ToolBar(actionProcessor);
			this.add(controlPanel, BorderLayout.NORTH);
			controlPanel.setPreferredSize(new java.awt.Dimension(945, 35));
			statusPanel = new JPanel();
			this.add(statusPanel, BorderLayout.SOUTH);
			BorderLayout statusLayout = new BorderLayout();
			
			statusLayout.setHgap(5);
			statusPanel.setLayout(statusLayout);
			statusBar = new JProgressBar();
			statusBar.setValue(100);
			statusBar.setPreferredSize(new Dimension(120, 21));
			statusLabel = new JLabel();
			statusLabel.setText("데이터 수집을 시작합니다.");
			statusLabel.setPreferredSize(new Dimension(800, 21));
			statusPanel.add(statusBar, BorderLayout.WEST);
			statusPanel.add(statusLabel, BorderLayout.CENTER);
			statusPanel.setPreferredSize(new java.awt.Dimension(945, 21));
			contentSplitPane = new JSplitPane();
			this.add(contentSplitPane, BorderLayout.CENTER);
			contentSplitPane.setDividerLocation(400);
			
			{
				leftSplitPane = new JSplitPane();
				contentSplitPane.add(leftSplitPane, JSplitPane.LEFT);
				leftSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
				leftSplitPane.setDividerLocation(450);
				{
					treePanel = new TreePanel(actionProcessor);
					leftSplitPane.add(treePanel, JSplitPane.LEFT);
				}
				{
					reportPanel = new JPanel();
					BorderLayout reportPanelLayout = new BorderLayout();
					reportPanel.setLayout(reportPanelLayout);
					leftSplitPane.add(reportPanel, JSplitPane.RIGHT);
					
				}
			}
			contentPanel = new ContentPanel();
			contentSplitPane.add(contentPanel , JSplitPane.RIGHT);
			ReportPanel rp = new ReportPanel("<REPORT>");
			reportPanel.add(rp, BorderLayout.CENTER);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addFrame(AbstractInternalFrame frm) {
		contentPanel.addFrame(frm);
	} 
	
	public void setStatus(String msg) {
		this.statusLabel.setText(msg);
	}
	
	public void setStatus(int progress) {
		this.statusBar.setValue(progress);
	}

}
