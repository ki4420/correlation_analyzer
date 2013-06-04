/*
 * Created on 2006. 2. 2
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package kr.ac.hanyang.common.table;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

public class HandCursorViewButton extends JButton{
	/**
	 * @uml.property  name="mouseListener"
	 * @uml.associationEnd  
	 */
	ToolbarMouseListener mouseListener = new ToolbarMouseListener();
	public HandCursorViewButton() {
		super();
		this.addMouseListener(mouseListener);
	}
	public HandCursorViewButton(String title) {
		super(title);
		this.addMouseListener(mouseListener);
	}
	public HandCursorViewButton(AbstractAction action) {
		super(action);
		this.addMouseListener(mouseListener);
	}
    class ToolbarMouseListener extends MouseAdapter {
    	public void mouseEntered(MouseEvent e) {
    		//System.out.println("mouseEntered!");
    		setCursorShape(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	}
    	public void mouseExited(MouseEvent e) {
    		//System.out.println("mouseExited!");
    		setCursorShape(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    	}
    }
    private void setCursorShape(Cursor cursor) {
    	this.setCursor(cursor);
    }
}
