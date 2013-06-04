package kr.ac.hanyang.webstart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
 * 입력값을 받기위한 심플 다이얼로그
 * 
 * @author 김철
 * 
 */
public class InputDialog extends JDialog implements ActionListener {
	/**
	 * @uml.property  name="jPanelCenter"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JPanel jPanelCenter = new JPanel();

	/**
	 * @uml.property  name="jPanelSouth"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JPanel jPanelSouth = new JPanel();

	/**
	 * @uml.property  name="jTextAreaMSG"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JTextArea jTextAreaMSG = new JTextArea();

	/**
	 * @uml.property  name="jTextFieldValue"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JTextField jTextFieldValue = new JTextField();

	/**
	 * @uml.property  name="jButtonOK"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JButton jButtonOK = new JButton();

	/**
	 * @uml.property  name="jButtonCancel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JButton jButtonCancel = new JButton();

	public InputDialog(Frame owner, String message, String oldValue) {
//		super(owner, UIManager.getString("OptionPane.inputDialogTitle"), true);
		super(owner, "", true);
		jTextAreaMSG.setText(message);
		jButtonOK.setText("OK");
		jButtonCancel.setText("Cancel");
		init();
		jTextFieldValue.setText(oldValue);
		jTextFieldValue.requestFocus();
		setResizable(false);
		Util.setCenterLocation(this);
		this.addWindowListener(new DialogClose());
		pack();
	}
	
	public InputDialog(Frame owner, String message) {
//		super(owner, UIManager.getString("OptionPane.inputDialogTitle"), true);
		super(owner, "", true);
		jTextAreaMSG.setText(message);
		jButtonOK.setText("OK");
		jButtonCancel.setText("Cancel");
		init();
		
		jTextFieldValue.requestFocus();
		setResizable(false);
		Util.setCenterLocation(this);
		this.addWindowListener(new DialogClose());
		pack();
	}
	
	public InputDialog(Frame owner, String message, String okText, String cancelText) {
//		super(owner, UIManager.getString("OptionPane.inputDialogTitle"), true);
		super(owner, "", true);
		jTextAreaMSG.setText(message);
		jButtonOK.setText(okText);
		jButtonCancel.setText(cancelText);
		init();
		
		jTextFieldValue.requestFocus();
		setResizable(false);
		Util.setCenterLocation(this);
		this.addWindowListener(new DialogClose());
		pack();
	}


	public void init() {

		jTextAreaMSG.setEditable(false);
		jTextFieldValue.setPreferredSize(new Dimension(250, 25));

		BorderLayout jPanelCenterLayout = new BorderLayout();
		jPanelCenter.setLayout(jPanelCenterLayout);
		jPanelCenterLayout.setVgap(5);
		jPanelCenter.setBorder(BorderFactory.createEmptyBorder(7, 15, 7, 15));

		jPanelCenter.add(jTextAreaMSG, BorderLayout.CENTER);
		jPanelCenter.add(jTextFieldValue, BorderLayout.SOUTH);

		

		jPanelSouth.add(jButtonOK);
		jButtonOK.setMnemonic(java.awt.event.KeyEvent.VK_ENTER);
		jPanelSouth.add(jButtonCancel);
		jButtonCancel.setMnemonic(java.awt.event.KeyEvent.VK_ESCAPE);

		getContentPane().add(jPanelCenter, BorderLayout.CENTER);
		getContentPane().add(jPanelSouth, BorderLayout.SOUTH);

		jTextFieldValue.addActionListener(this);
		jButtonOK.addActionListener(this);
		jButtonCancel.addActionListener(this);
	}
	
	public void setInuptValue(String value) {
		jTextFieldValue.setText(value);
	}

	public String getInputValue() {
		return jTextFieldValue.getText();
	}
	
	/**
	 * @uml.property  name="ok"
	 */
	private boolean ok = false;
	
	public boolean isOK() {
		return ok;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jButtonOK || e.getSource() == jTextFieldValue) {
			ok = true;
			dispose();
		} else if (e.getSource() == jButtonCancel) {
			ok = false;
			jTextFieldValue.setText("");
			dispose();
		}
	}

	class DialogClose extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			dispose();
		}
	}
}
