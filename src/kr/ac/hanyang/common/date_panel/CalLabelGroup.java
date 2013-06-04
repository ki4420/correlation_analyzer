package kr.ac.hanyang.common.date_panel;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CalLabelGroup extends JPanel {
	/**
	 * @uml.property  name="labels"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="group:kr.ac.hanyang.common.date_panel.CalLabel"
	 */
	private Vector labels = null;
	/**
	 * @uml.property  name="mode"
	 */
	public int mode = -1;
	/**
	 * @uml.property  name="isSelected"
	 */
	boolean isSelected = false;

	static GridLayout layout = new GridLayout(1, 7);

	/**
	 * @uml.property  name="calPanel"
	 * @uml.associationEnd  
	 */
	CalendarPanel calPanel = null;

	public CalLabelGroup(CalendarPanel calPanel) {
		labels = new Vector();
		setLayout(layout);
		this.calPanel = calPanel;
		if(calPanel.getMode() != CalendarPanel.WEEK_MODE)
			setBackground(CalendarPanel.platColor);
	}

	public void addCalLabels(CalLabel label) {
		labels.add(label);
		add(label);
	}

	/**
	 * @param mode
	 * @uml.property  name="mode"
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	public void mouseClicked(CalLabel label,java.awt.event.MouseEvent e) {
		if(mode != CalendarPanel.CUSTOM_MODE && label.isSelected() || isSelected) return;
		if(mode == CalendarPanel.DAY_MODE || mode == CalendarPanel.CUSTOM_MODE || mode == PSCalendar.HOUR_MODE)
			calPanel.labelSelected(label);
		else if(mode == CalendarPanel.WEEK_MODE) {
			isSelected = true;
			calPanel.groupSelected(this);
			setBackground(CalendarPanel.lowerColor);
		}
	}

	public void mouseEntered(CalLabel label) {
		if(label.isSelected() || isSelected) return;
		if(mode == CalendarPanel.DAY_MODE || mode == CalendarPanel.CUSTOM_MODE || mode == PSCalendar.HOUR_MODE)
			label.setBackground(CalendarPanel.raisedColor);
		else if(mode == CalendarPanel.WEEK_MODE)
			setBackground(CalendarPanel.raisedColor);
	}

	public void mouseExited(CalLabel label) {
		if(label.isSelected() || isSelected) return;
		if(mode == CalendarPanel.DAY_MODE || mode == CalendarPanel.CUSTOM_MODE || mode == PSCalendar.HOUR_MODE)
			label.setBackground(CalendarPanel.platColor);
		else if(mode == CalendarPanel.WEEK_MODE)
			setBackground(CalendarPanel.platColor);
	}

	public void mousePressed(CalLabel label,java.awt.event.MouseEvent e) {
		/*
                if(label.isSelected() || isSelected) return;
		if(mode == CalendarPanel.DAY_MODE || mode == CalendarPanel.CUSTOM_MODE)
			label.setBackground(CalendarPanel.lowerColor);
		else if(mode == CalendarPanel.WEEK_MODE)
			setBackground(CalendarPanel.lowerColor);
                */
                //if(mode != CalendarPanel.CUSTOM_MODE && label.isSelected() || isSelected) return;
                if(mode == CalendarPanel.DAY_MODE || mode == CalendarPanel.CUSTOM_MODE || mode == PSCalendar.HOUR_MODE)
                    calPanel.labelSelected(label);
                else if(mode == CalendarPanel.WEEK_MODE) {
                    isSelected = true;
                    calPanel.groupSelected(this);
                    setBackground(CalendarPanel.lowerColor);
                }else{}

                //04.03.25_ÇõÀÌ
                if(e.getClickCount() >= 2) {
                    calPanel.dispatchMouseDoubleClicked();
                }
	}

	public void mouseReleased(CalLabel label) {
		if(label.isSelected() || isSelected) return;
		if(mode == CalendarPanel.DAY_MODE || mode == CalendarPanel.CUSTOM_MODE || mode == PSCalendar.HOUR_MODE)
			label.setBackground(CalendarPanel.raisedColor);
		else if(mode == CalendarPanel.WEEK_MODE)
			setBackground(CalendarPanel.raisedColor);
	}

	public int[] getTerm() {
		int []result = new int[2];
		result[0] = Integer.valueOf((String)((CalLabel)labels.get(0)).getText()).intValue();
		result[1] = Integer.valueOf((String)((CalLabel)labels.get(labels.size()-1)).getText()).intValue();
		System.out.println(result[0]+"in group" +result[1]);
		return result;
	}
}
