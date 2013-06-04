package kr.ac.hanyang.collector.jmx;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.management.ObjectName;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import kr.ac.hanyang.collector.ChannelManager;
import kr.ac.hanyang.common.Util;
import kr.ac.hanyang.common.date_panel.DatePanel;
import kr.ac.hanyang.entity.Collector;
import kr.ac.hanyang.webstart.ActionProcessor;


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
public class JMXCollectorEditorDlg extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -120127474422363613L;
	
	/**
	 * @uml.property  name="jPanelTop"
	 * @uml.associationEnd  
	 */
	private JPanel jPanelTop;
	/**
	 * @uml.property  name="jPanelBottom"
	 * @uml.associationEnd  
	 */
	private JPanel jPanelBottom;
	/**
	 * @uml.property  name="jTextFieldName"
	 * @uml.associationEnd  
	 */
	private JTextField jTextFieldName;
	/**
	 * @uml.property  name="jLabelInterval"
	 * @uml.associationEnd  
	 */
	private JLabel jLabelInterval;
	/**
	 * @uml.property  name="jButtonCancel"
	 * @uml.associationEnd  
	 */
	private JButton jButtonCancel;
	/**
	 * @uml.property  name="jButtonOk"
	 * @uml.associationEnd  
	 */
	private JButton jButtonOk;
	/**
	 * @uml.property  name="jButton1"
	 * @uml.associationEnd  
	 */
	private JButton jButton1;
	/**
	 * @uml.property  name="jPanelTargets"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="kr.ac.hanyang.collector.jmx.Location"
	 */
	private JPanel jPanelTargets;
	/**
	 * @uml.property  name="jScrollPaneTargets"
	 * @uml.associationEnd  
	 */
	private JScrollPane jScrollPaneTargets;
	/**
	 * @uml.property  name="jButtonSelectAll"
	 * @uml.associationEnd  
	 */
	private JButton jButtonSelectAll;
	/**
	 * @uml.property  name="jPanelTargetControl"
	 * @uml.associationEnd  
	 */
	private JPanel jPanelTargetControl;
	/**
	 * @uml.property  name="jButtonConnect"
	 * @uml.associationEnd  
	 */
	private JButton jButtonConnect;
	/**
	 * @uml.property  name="jTextFieldURL"
	 * @uml.associationEnd  
	 */
	private JTextField jTextFieldURL;
	/**
	 * @uml.property  name="jLabelURL"
	 * @uml.associationEnd  
	 */
	private JLabel jLabelURL;
	/**
	 * @uml.property  name="jLabelName"
	 * @uml.associationEnd  
	 */
	private JLabel jLabelName;
	/**
	 * @uml.property  name="jPanelCenter"
	 * @uml.associationEnd  
	 */
	private JPanel jPanelCenter;
	/**
	 * @uml.property  name="datePanel"
	 * @uml.associationEnd  
	 */
	private DatePanel datePanel;
	/**
	 * @uml.property  name="jComboBoxInterval"
	 * @uml.associationEnd  
	 */
	private JComboBox jComboBoxInterval;

	/**
	 * @uml.property  name="jmx"
	 * @uml.associationEnd  
	 */
	private JMXChannel jmx;
	
	/**
	 * @uml.property  name="collector"
	 * @uml.associationEnd  
	 */
	Collector collector;
	
	/**
	 * @uml.property  name="actionProcessor"
	 * @uml.associationEnd  
	 */
	ActionProcessor actionProcessor;
	
	public JMXCollectorEditorDlg() {
		try {
			UIManager
					.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exc) {
			System.err.println("Error loading L&F: " + exc);
			exc.printStackTrace();
		}
		initGUI();
	}
	
	public JMXCollectorEditorDlg(ActionProcessor ap, Collector c) {
		this();
		actionProcessor = ap;
		load(c);
		
	}
	
	public static void main(String[] args) {
		new  JMXCollectorEditorDlg();
	}
	
	private void save() {
		
		if(collector == null) {
			collector = new Collector();
			collector.setProperty(Collector.NAME, jTextFieldName.getText());
			collector.setProperty(Collector.URL, jTextFieldURL.getText());
			collector.setProperty(Collector.FROM_DATE, datePanel.getStartDate());
			collector.setProperty(Collector.TO_DATE, datePanel.getEndDate());
			collector.setProperty(Collector.TARGET_DEVICE_ON_ARRAY, getTargetDevices());
			collector.setProperty(Collector.INTERVAL, jComboBoxInterval.getSelectedIndex());
			//TODO 트리에 반영
			this.actionProcessor.addTree(collector);
		} else {
			collector.setProperty(Collector.NAME, jTextFieldName.getText());
			collector.setProperty(Collector.URL, jTextFieldURL.getText());
			collector.setProperty(Collector.FROM_DATE, datePanel.getStartDate());
			collector.setProperty(Collector.TO_DATE, datePanel.getEndDate());
			collector.setProperty(Collector.TARGET_DEVICE_ON_ARRAY, getTargetDevices());
			
			//TODO 트리에 반영
			this.actionProcessor.updateTree(collector);
			
		}
		
		
	}
	
	public void load(Collector c) {
		if(c != null) {
			collector = c;
			jTextFieldURL.setText(collector.getProperty(Collector.URL).toString());
			initConnection();
			jTextFieldName.setText(collector.getProperty(Collector.NAME).toString());
			datePanel.setStartDate((Date) collector.getProperty(Collector.FROM_DATE) );
			datePanel.setEndDate((Date) collector.getProperty(Collector.TO_DATE) );
			jComboBoxInterval.setSelectedIndex((Integer)collector.getProperty(Collector.INTERVAL) );
			setSelectedTargetDevices((Object[])collector.getProperty(Collector.TARGET_DEVICE_ON_ARRAY));

		}
		
	}
	
	
	private void setSelectedTargetDevices(Object[] deviceONArray) {
		boolean found = false;
		for(int i = 0, end = this.jPanelTargets.getComponentCount(); i < end; i++) {
			CheckBox check = (CheckBox)jPanelTargets.getComponent(i);
			for (int j = 0, jend = deviceONArray.length; j < jend; j++) {
				if(deviceONArray[j].toString().indexOf(check.getText()) > -1) {
					check.setSelected(true);
					found = true;
					break;
				}
			}
			

		}
		if(!found) {
			try {
				for (int j = 0, jend = deviceONArray.length; j < jend; j++) {
					CheckBox check = new CheckBox(new Location( deviceONArray[j].toString()));
					this.jPanelTargets.add(check);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	}
	
	private Object[] getTargetDevices() {
		ArrayList<String> targets = new ArrayList<String>();
		for(int i = 0, end = this.jPanelTargets.getComponentCount(); i < end; i++) {
			CheckBox check = (CheckBox)jPanelTargets.getComponent(i);
			if(check.isSelected())
				targets.add(((Location)check.getUserObject()).getOName().toString());
		}
		return targets.toArray();
	}

	private synchronized void initConnection() {
		
		jPanelTargets.removeAll();
		jmx = (JMXChannel)ChannelManager.getChannel(jTextFieldURL.getText());
		Object[] servers = jmx.getMbeanByType("server.ComputerSystem");
		System.out.println("server count="+servers.length);
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		
		for(int i = 0, end = servers.length; i < end; i++) {
			CheckBox check = new CheckBox(new Location((ObjectName)servers[i]));
			this.jPanelTargets.add(check);
		}
		
		Object[] networks = jmx.getMbeanByType("network.NMSNode");
		System.out.println("networks count="+networks.length);
		
		for(int i = 0, end = networks.length; i < end; i++) {
			CheckBox check = new CheckBox(new Location((ObjectName)networks[i]));
			this.jPanelTargets.add(check);
		}
		jPanelTargets.updateUI();
	}
	
	private void checkAll(boolean b) {
		for (int i = 0, end = jPanelTargets.getComponentCount(); i < end; i++) {
			CheckBox check = (CheckBox)jPanelTargets.getComponent(i);
			if(check != null)
				check.setSelected(b);
		}
	}
	
	private void initGUI() {
		try {
			this.setTitle("Edit JMX Collector");
			
			jPanelTop = new JPanel();
			FlowLayout jPanelTopLayout = new FlowLayout();
			jPanelTopLayout.setAlignment(FlowLayout.LEFT);
			jPanelTop.setLayout(jPanelTopLayout);
			getContentPane().add(jPanelTop, BorderLayout.NORTH);
			jPanelTop.setPreferredSize(new java.awt.Dimension(494, 65));
			jLabelName = new JLabel();
			jPanelTop.add(jLabelName);
			jLabelName.setText("Name: ");
			jTextFieldName = new JTextField();
			jPanelTop.add(jTextFieldName);
			jTextFieldName.setText("192.168.206.12");
			jTextFieldName.setPreferredSize(new java.awt.Dimension(161, 24));
			jLabelURL = new JLabel();
			jPanelTop.add(jLabelURL);
			jLabelURL.setText("URL: ");
			jTextFieldURL = new JTextField();
			jPanelTop.add(jTextFieldURL);
			jTextFieldURL.setText("service:jmx:rmi:///jndi/rmi://192.168.206.12:21001/server");
			jTextFieldURL.setPreferredSize(new java.awt.Dimension(241, 24));
			jButtonConnect = new JButton();
			jPanelTop.add(jButtonConnect);
			jButtonConnect.setText("Connect");
			jButtonConnect.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					System.out.println("jButtonConnect.mousePressed, event="+evt);
//					collector = new Collector(jLabelURL.getText());
					initConnection();
				}
			});
			datePanel = new DatePanel();
			jPanelTop.add(datePanel);
			datePanel.setPreferredSize(new java.awt.Dimension(364, 29));
			{
				jLabelInterval = new JLabel();
				jPanelTop.add(jLabelInterval);
				jLabelInterval.setText("수집간격: ");
				ComboBoxModel jComboBoxIntervalModel = new DefaultComboBoxModel(
						new Object[] { 
								"1분통계",
								"5분통계",
								"10분통계",
								"시간통계",
								"일통계",
								"주통계",
								"월통계" });
				jComboBoxInterval = new JComboBox();
				jPanelTop.add(jComboBoxInterval);
				jComboBoxInterval.setModel(jComboBoxIntervalModel);
				jComboBoxInterval.setSelectedIndex(2);
				jComboBoxInterval
						.setPreferredSize(new java.awt.Dimension(
								96, 24));
			}
			jPanelBottom = new JPanel();
			FlowLayout jPanelBottomLayout = new FlowLayout();
			jPanelBottomLayout.setAlignment(FlowLayout.RIGHT);
			jPanelBottom.setLayout(jPanelBottomLayout);
			getContentPane().add(jPanelBottom, BorderLayout.SOUTH);
			jPanelBottom.setPreferredSize(new java.awt.Dimension(494, 35));
			jButtonOk = new JButton();
			jPanelBottom.add(jButtonOk);
			jButtonOk.setText("OK");
			jButtonOk.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					System.out.println("jButtonOk.mousePressed, event="+evt);
					save();
					dispose();
				}
			});
			jButtonCancel = new JButton();
			jPanelBottom.add(jButtonCancel);
			jButtonCancel.setText("CANCEL");
			jButtonCancel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					System.out.println("jButtonCancel.mousePressed, event="+evt);
					dispose();
				}
			});
			jPanelCenter = new JPanel();
			BorderLayout jPanelCenterLayout = new BorderLayout();
			jPanelCenter.setLayout(jPanelCenterLayout);
			getContentPane().add(jPanelCenter, BorderLayout.CENTER);
			jPanelCenter.setBorder(BorderFactory.createTitledBorder("Target Devices"));
			jPanelTargetControl = new JPanel();
			FlowLayout jPanelTargetControlLayout = new FlowLayout();
			jPanelTargetControlLayout.setAlignment(FlowLayout.RIGHT);
			jPanelTargetControlLayout.setVgap(2);
			jPanelTargetControl.setLayout(jPanelTargetControlLayout);
			jPanelCenter.add(jPanelTargetControl, BorderLayout.NORTH);
			jPanelTargetControl.setPreferredSize(new java.awt.Dimension(566, 29));
			jButtonSelectAll = new JButton();
			jPanelTargetControl.add(jButtonSelectAll);
			jButtonSelectAll.setText("Check All");
			jButtonSelectAll.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					System.out.println("jButtonSelectAll.mousePressed, event="+evt);
					checkAll(true);
				}
			});
			jButton1 = new JButton();
			jPanelTargetControl.add(jButton1);
			jButton1.setText("Uncheck All");
			jButton1.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					System.out.println("jButton1.mousePressed, event="+evt);
					checkAll(false);
				}
			});
			jScrollPaneTargets = new JScrollPane();
			jPanelCenter.add(jScrollPaneTargets, BorderLayout.CENTER);
			jScrollPaneTargets.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jPanelTargets = new JPanel();
			FlowLayout jPanelTargetsLayout = new FlowLayout();
			jPanelTargetsLayout.setAlignment(FlowLayout.LEFT);
			jPanelTargets.setLayout(jPanelTargetsLayout);
			jPanelTargets.setBackground(Color.white);
			jScrollPaneTargets.setViewportView(jPanelTargets);
			jPanelTargets.setPreferredSize(new Dimension(583, 800));
			this.setSize(625, 291);
			Util.setCenterLocation(this);
			this.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}

class CheckBox extends JCheckBox{
	/**
	 * @uml.property  name="userObject"
	 */
	private Object userObject;
	
	public CheckBox(Object o) {
		super();
		userObject = o;
		this.setText(userObject.toString());
		this.setBackground(Color.white);
	}
	
	/**
	 * @return
	 * @uml.property  name="userObject"
	 */
	public Object getUserObject() {
		return userObject;
	}
	
}
