package kr.ac.hanyang.common.date_panel;

import javax.swing.*;
import java.awt.event.*;

public class CalLabel extends JLabel {
	/**
	 * @uml.property  name="selected"
	 */
	private boolean selected = false;
	/**
	 * @uml.property  name="group"
	 * @uml.associationEnd  
	 */
	private CalLabelGroup group = null;
	/**
	 * @uml.property  name="listener"
	 * @uml.associationEnd  
	 */
	private LabelListener listener = null;
	/**
	 * @uml.property  name="cal"
	 * @uml.associationEnd  
	 */
	private CalendarPanel cal = null;
	/**
	 * @uml.property  name="index"
	 */
	private int index;

	public CalLabel() {
		super();
	}

	public CalLabel(String text, CalendarPanel cal, int index) {
		this(text, null, cal, index);
	}

	public CalLabel(String text, CalLabelGroup group, CalendarPanel cal, int index) {
		super(text, JLabel.CENTER);
		this.group = group;
		this.cal = cal;
		this.index = index;
		listener = new LabelListener();
		addMouseListener(listener);
		if(cal.getMode() != CalendarPanel.WEEK_MODE)
			setOpaque(true);
	}

	/**
	 * @param group
	 * @uml.property  name="group"
	 */
	public void setGroup(CalLabelGroup group) {
		this.group = group;
	}

	/**
	 * @param selected
	 * @uml.property  name="selected"
	 */
	public void setSelected(boolean selected) {
		if(selected) {
			setBackground(CalendarPanel.lowerColor);
		}
		else setBackground(CalendarPanel.platColor);
		this.selected = selected;
	}

	/**
	 * @return
	 * @uml.property  name="selected"
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @return
	 * @uml.property  name="index"
	 */
	public int getIndex() {
		return index;
	}

	class LabelListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			group.mouseClicked(CalLabel.this,e);
		}

		public void mouseEntered(MouseEvent e) {
			group.mouseEntered(CalLabel.this);
		}

		public void mouseExited(MouseEvent e) {
			group.mouseExited(CalLabel.this);
		}
                public void mouseReleased(MouseEvent e) {
                         group.mouseReleased(CalLabel.this);
                }
		public void mousePressed(MouseEvent e) {
                    group.mousePressed(CalLabel.this,e);
		}
            }
}
