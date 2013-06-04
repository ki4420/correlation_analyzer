/**
 * 
 */
package kr.ac.hanyang.webstart.internal_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import kr.ac.hanyang.common.ImageManager;
import kr.ac.hanyang.common.TableModel;
import kr.ac.hanyang.common.Util;

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
/**
 * @author KimChul
 * 
 */
abstract public class AbstractInternalFrame extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3108062747565917355L;
	
	/**
	 * @uml.property  name="mainPanel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JPanel mainPanel;
	/**
	 * @uml.property  name="contentScrollPane"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JScrollPane contentScrollPane;
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
	 * @uml.property  name="table"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	protected JTable table;
	/**
	 * @uml.property  name="controlPanel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JToolBar controlPanel;
	private final static Color tableOddBGColor = new Color(179, 204, 230);
	/**
	 * @uml.property  name="model"
	 * @uml.associationEnd  
	 */
	protected TableModel model;

	/**
	 * 
	 */
	public AbstractInternalFrame() {
		super();
		initGUI();
		init();
	}

	/**
	 * @param title
	 */
	public AbstractInternalFrame(String title) {
		super(title, true, true, true, true);
		initGUI();
		init();
	}
	/**
	 * 아래 메소드를 이용하여 구현해야됨.
	 * addButton(..)
	 * setTableColumns(..)
	 */
	abstract protected void init();
	
	abstract public void buttonAction(MouseEvent e);

	private void setRenderer(JTable t) {
		TableColumn tColumn = null;
		for (int i = 0; i < t.getModel().getColumnCount(); i++) {
			tColumn = t.getColumnModel().getColumn(i);
			ColorRenderer colorRender = new ColorRenderer();
			tColumn.setCellRenderer(colorRender);
		}

	}

	public void appendResult(final Object[] row) {

		Runnable doRun = new Runnable() {
			public void run() {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(row);
				contentScrollPane.getVerticalScrollBar().setValue(
						contentScrollPane.getVerticalScrollBar().getMaximum());
			}
		};
		SwingUtilities.invokeLater(doRun);
		// this.
	}

	public void clear() {
		Runnable doRun = new Runnable() {
			public void run() {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(model.getRowCount() - 1);
				}
			}
		};
		SwingUtilities.invokeLater(doRun);
	}
	
	protected void addButton(String name, String imageName) {
		JButton newButton = new JButton();
		controlPanel.add(newButton);
		newButton.setToolTipText(name);
		newButton.setText(name);
		newButton.setIcon(ImageManager.getImageIcon(imageName));
		newButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		newButton.setOpaque(false);
		newButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				buttonAction(evt);
			}
		});
	}
	
	protected void setTableColumns(Object[] columns) {
		model = new TableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);
	}
	
	protected void setStatus(String msg) {
		this.statusLabel.setText(msg);
	}
	
	protected void setStatus(int progress) {
		this.statusBar.setValue(progress);
	}

	private void initGUI() {
		try {
			this.setFrameIcon(ImageManager.getImageIcon("ico_polestar.gif"));
			{

				{
					mainPanel = new JPanel();
					BorderLayout mainPanelLayout = new BorderLayout();
					mainPanel.setLayout(mainPanelLayout);
					getContentPane().add(mainPanel, BorderLayout.CENTER);
					controlPanel = new JToolBar();
					FlowLayout controlPanelLayout = new FlowLayout();
					controlPanelLayout.setAlignment(FlowLayout.LEFT);
					controlPanelLayout.setHgap(3);
					controlPanelLayout.setVgap(3);
					controlPanel.setLayout(controlPanelLayout);
					mainPanel.add(controlPanel, BorderLayout.NORTH);
					controlPanel.setPreferredSize(new java.awt.Dimension(666, 26));
					contentScrollPane = new JScrollPane();
					mainPanel.add(contentScrollPane, BorderLayout.CENTER);
					
					table = new JTable();
					setRenderer(table);
					contentScrollPane.setViewportView(table);
					statusPanel = new JPanel();
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
					mainPanel.add(statusPanel, BorderLayout.SOUTH);
					statusPanel
							.setPreferredSize(new java.awt.Dimension(
									600, 23));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveCSV() {
		Util.saveCSVFileByTableModel(this, model); 
	}

	private class ColorRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component comp = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			if (!isSelected) {
				if (row % 2 == 0) {
					comp.setBackground(tableOddBGColor);
				} else {
					comp.setBackground(Color.white);
				}
			}

			return comp;
		}
	}


}
