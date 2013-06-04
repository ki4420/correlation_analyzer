package kr.ac.hanyang.webstart;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import kr.ac.hanyang.common.ImageManager;


/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ToolBar extends JToolBar {

	/**
	 * @uml.property  name="methodComboBox"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JComboBox methodComboBox;

	/**
	 * @uml.property  name="actionProcessor"
	 * @uml.associationEnd  
	 */
	private ActionProcessor actionProcessor;

	private static HashMap<String, JButton> buttonMap = new HashMap<String, JButton>();
	
	public ToolBar(ActionProcessor ap) {
		this.actionProcessor = ap;
		initGUI();
	}
	
	/**
	 * @return
	 * @uml.property  name="button"
	 */
	public static HashMap<String, JButton> getButtonMap() {
		return buttonMap;
	}
	
	private void initGUI() {
		try {
			FlowLayout mainLayout = new FlowLayout();
			mainLayout.setAlignment(FlowLayout.LEFT);
			mainLayout.setHgap(1);
			mainLayout.setVgap(1);
			setLayout(mainLayout);

			this.add(getButton(ActionProcessor.OPEN_WS,  "root.gif"));
			this.add(getButton(ActionProcessor.SAVE_WS,  "set.gif"));
			
			this.addSeparator(new Dimension(10, 20));
			
			this.add(getButton(ActionProcessor.LOAD_CSV,  "load_mibs.gif"));
			this.add(getButton(ActionProcessor.ADD_COLLECTOR,  "index.gif"));
			this.add(getButton(ActionProcessor.ADD_GROUP,  "group.gif"));
			
			this.addSeparator(new Dimension(10, 20));

			ComboBoxModel methodComboBoxModel = 
				new DefaultComboBoxModel(
						new String[] {"Pearson", "Kendall", "Spearman"});
			methodComboBox = new JComboBox(methodComboBoxModel);
			methodComboBox.setPreferredSize(new Dimension(150, 20));
			methodComboBox.setSelectedIndex(0);
			methodComboBox.setEditable(false);
			this.add(methodComboBox);
			this.add(getButton(ActionProcessor.COR, "walk.gif"));
			this.add(getButton(ActionProcessor.SHIFT_COR, "walk.gif"));
			this.add(getButton(ActionProcessor.COLLECT, "get_next.gif"));
			this.add(getButton(ActionProcessor.REALTIME_COLLECT, "get_next.gif"));
//			this.add(getButton(ActionProcessor.STOP, "stop.gif"));
			this.addSeparator(new Dimension(10, 20));
			MemoryMonitorPanel mmp = new MemoryMonitorPanel();
			mmp.setSize(new java.awt.Dimension(206, 37));
			this.add(mmp);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public JButton getButton(String command, String iconPath) {
		JButton result = buttonMap.get(command);
		
		if(result == null) {
			result = new JButton();
			result.setText(command);
			result.setActionCommand(command);
			if(iconPath != null) result.setIcon(ImageManager.getImageIcon(iconPath));
			result.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	actionProcessor.actionProcess(e.getActionCommand());
                }
            });
			buttonMap.put(command, result);
		}
		return result;
	}
}
