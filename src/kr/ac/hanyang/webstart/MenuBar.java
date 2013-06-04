package kr.ac.hanyang.webstart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import kr.ac.hanyang.common.ImageManager;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class MenuBar extends JMenuBar {
	
	/**
	 * @uml.property  name="actionProcessor"
	 * @uml.associationEnd  
	 */
	private ActionProcessor actionProcessor = null;
	
	
	
	private static HashMap<String, JMenuItem> menuItemMap = new HashMap<String, JMenuItem>();
	

	/**
	 * @uml.property  name="helpMenu"
	 * @uml.associationEnd  readOnly="true"
	 */
	private JMenu helpMenu;

	/**
	 * @uml.property  name="toolsMenu"
	 * @uml.associationEnd  readOnly="true"
	 */
	private JMenu toolsMenu;
	/**
	 * @uml.property  name="operationsMenu"
	 * @uml.associationEnd  readOnly="true"
	 */
	private JMenu operationsMenu;
	/**
	 * @uml.property  name="editMenu"
	 * @uml.associationEnd  readOnly="true"
	 */
	private JMenu editMenu;
	/**
	 * @uml.property  name="fileMenu"
	 * @uml.associationEnd  readOnly="true"
	 */
	private JMenu fileMenu;

	public MenuBar(ActionProcessor ap) {
		this.actionProcessor = ap;
		initGUI();
	}
	
	/**
	 * @return
	 * @uml.property  name="menuItem"
	 */
	public static HashMap<String, JMenuItem> getMenuItemMap() {
		return menuItemMap;
	}
	
	public JMenuItem getMenuItem(String command, String iconPath) {
		JMenuItem result = menuItemMap.get(command);
		
		if(result == null) {
			result = new JMenuItem();
			result.setText(command);
			result.setActionCommand(command);
			if(iconPath != null) result.setIcon(ImageManager.getImageIcon(iconPath));
			result.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                	actionProcessor.actionProcess(e.getActionCommand());
                }
            });
			menuItemMap.put(command, result);
		}
		
		return result;
	}
	
	public JSeparator getSeparator() {
		return new JSeparator();
		
	}

	private void initGUI() {
		try {
//			fileMenu = new JMenu();
//			add(fileMenu);
//			fileMenu.setText("File");
//			fileMenu.add(getMenuItem(ActionProcessor.LOAD_CSV, "load_mibs.gif"));
//			fileMenu.add(getSeparator());
//			fileMenu.add(getMenuItem(ActionProcessor.EXIT, "close.gif"));
//			
//			editMenu = new JMenu();
//			add(editMenu);
//			editMenu.add(getMenuItem(ActionProcessor.FIND_IN_TREE, "find.png"));
//			editMenu.add(getMenuItem(ActionProcessor.FIND_IN_RESULT, "find.png"));
//
//			operationsMenu = new JMenu();
//			operationsMenu.setText("Operations");
//			add(operationsMenu);
//			operationsMenu.add(getMenuItem(ActionProcessor.GET, "get.gif"));
//			operationsMenu.add(getMenuItem(ActionProcessor.GET_NEXT, "get_next.gif"));
////			operationsMenu.add(getMenuItem(ActionProcessor.GET_BULK, "get_bulk.gif"));
//			operationsMenu.add(getMenuItem(ActionProcessor.GET_SUBTREE, "get_subtree.gif"));
//			operationsMenu.add(getMenuItem(ActionProcessor.SET, "set.gif"));
//			operationsMenu.add(getMenuItem(ActionProcessor.WALK, "walk.gif"));
//			operationsMenu.add(getSeparator());
//			operationsMenu.add(getMenuItem(ActionProcessor.STOP, "stop.gif"));
//
//			toolsMenu = new JMenu();
//			add(toolsMenu);
//			toolsMenu.setText("Tools");
//			toolsMenu.add(getMenuItem(ActionProcessor.OPTIONS, null));
//
//			helpMenu = new JMenu();
//			add(helpMenu);
//			helpMenu.setText("Help");
//			helpMenu.add(getMenuItem(ActionProcessor.USER_GUIDE, null));
//			helpMenu.add(getSeparator());
//			helpMenu.add(getMenuItem(ActionProcessor.ABOUT, null));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}
