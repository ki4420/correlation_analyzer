/**
 * 
 */
package kr.ac.hanyang.webstart;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import kr.ac.hanyang.common.ImageManager;
import kr.ac.hanyang.common.Util;
import kr.ac.hanyang.webstart.internal_frame.AbstractInternalFrame;

/**
 * @author  KimChul
 */
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5156266864779914615L;
	
	private final static String TITLE = "Correlation Analyzer";//"Annie Analyzer";
	
	/**
	 * @uml.property  name="menuBar"
	 * @uml.associationEnd  
	 */
	private MenuBar menuBar;
	/**
	 * @uml.property  name="layoutPanel"
	 * @uml.associationEnd  
	 */
	private static LayoutPanel layoutPanel;
	/**
	 * @uml.property  name="actionProcessor"
	 * @uml.associationEnd  
	 */
	private ActionProcessor actionProcessor;
	/**
	 * @uml.property  name="property"
	 * @uml.associationEnd  
	 */
	private PropertyManager property = null;
	
	public static void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exc) {
			System.err.println("Error loading L&F: " + exc);
			exc.printStackTrace();
		}

	}
	
	public MainFrame() {
		try {
			setLookAndFeel();
			setTitle(TITLE);
			
			setIconImage(ImageManager.getImageIcon("ico_polestar.gif").getImage());
			actionProcessor = new ActionProcessor(this);
			menuBar = new MenuBar(actionProcessor);
			setJMenuBar(menuBar);
			layoutPanel = new LayoutPanel(actionProcessor);
			
			getContentPane().add(layoutPanel, BorderLayout.CENTER);
//			mainPanel.loadMIB(getClass().getClassLoader().getResource("com/nkia/station/applet/mibbrowser/mib_parser/mibs/RFC1213-MIB").toString());
			setSize(1550, 980);
			property = new PropertyManager();
			property.loadXMLFile();
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			Util.setCenterLocation(this);
			addWindowListener(new WindowAdapter() {
				 public void windowClosing(WindowEvent e) {
					 property.saveXMLFile();
					 super.windowClosing(e);
				 }
			 });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void setStatus(int progress) {
		if(layoutPanel != null) {
			layoutPanel.setStatus(progress);
		}
	}
	
	public static void setStatus(String msg) {
		if(layoutPanel != null) {
			layoutPanel.setStatus(msg);
		}
	}
	
	public static void addFrame(AbstractInternalFrame frm) {
		if(layoutPanel != null) {
			layoutPanel.addFrame(frm);
		}
	}

	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
	
		mf.setVisible(true);
	}
	
	public static Object[] getTargetDataSets() {
		Object[] result = null;
		if(layoutPanel != null) {
			result = layoutPanel.getTreePanel().getTargetDataSets();
		}
		return result;
	}
}
