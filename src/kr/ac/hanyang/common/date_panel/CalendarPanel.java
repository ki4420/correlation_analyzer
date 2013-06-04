/**===========================================================

==========================================================**/
package kr.ac.hanyang.common.date_panel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CalendarPanel extends JPanel {
	public static final int DAY_MODE = 0;
	public static final int WEEK_MODE = 1;
	public static final int CUSTOM_MODE = 2;
	public static final int MONTH_MODE = 3;
	public static final int YEAR_MODE = 4;

	/**
	 * @uml.property  name="days"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	private CalLabel[] days = null;
	/**
	 * @uml.property  name="jjaturi" multiplicity="(0 -1)" dimension="1"
	 */
	private Label[] jjaturi = null;
	/**
	 * @uml.property  name="currentJjaturiIndex"
	 */
	private int currentJjaturiIndex = 0;

	static Color raisedColor;
	static Color platColor;
	static Color lowerColor;

	static {
		platColor = Color.white;
		//raisedColor = new Color(187, 208, 232);
                //lowerColor = new Color(133, 170, 211);
                raisedColor = new Color(185, 234, 148);
		lowerColor = new Color(116, 169, 91);
	}

	/**
	 * @uml.property  name="calendar"
	 */
	private GregorianCalendar calendar = null;
	/**
	 * @uml.property  name="maxday"
	 */
	private int maxday;
	/**
	 * @uml.property  name="firstYoil"
	 */
	private int firstYoil;
	/**
	 * @uml.property  name="year"
	 */
	private int year;
	/**
	 * @uml.property  name="month"
	 */
	private int month;
	/**
	 * @uml.property  name="layout"
	 */
	private GridLayout layout = null;

	/**
	 * @uml.property  name="mode"
	 */
	private int mode = -1;

	/**
	 * @uml.property  name="selectedGroup"
	 * @uml.associationEnd  
	 */
	CalLabelGroup selectedGroup = null;
	/**
	 * @uml.property  name="selectedLabel"
	 * @uml.associationEnd  
	 */
	CalLabel selectedLabel = null;

	/**
	 * @uml.property  name="beforeLabel"
	 * @uml.associationEnd  
	 */
	CalLabel beforeLabel = null;
	/**
	 * @uml.property  name="afterLabel"
	 * @uml.associationEnd  
	 */
	CalLabel afterLabel = null;

	//====================	consturctor
	public CalendarPanel(int year, int month, int mode) {
		this.mode = mode;
		layout = new GridLayout();
		setLayout(layout);
		setBackground(Color.white);
		setBorder(BorderFactory.createLoweredBevelBorder());

		if(year == 0 || month == 0) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(new Date(System.currentTimeMillis()));
			this.year = cal.get(Calendar.YEAR);
			this.month = cal.get(Calendar.MONTH) + 1;
		}
		else {
			this.year = year;
			this.month = month;
		}
		calendar = new GregorianCalendar(this.year, this.month-1, 1);
		initValues();

		//	��¥�� �� Label���� �ʱ�ȭ�Ѵ�.
		days = new CalLabel[31];
		jjaturi = new Label[13];

		for(int i=0 ; i<31 ; i++) {
			days[i] = new CalLabel(String.valueOf(i+1), this, i);
			days[i].setBackground(Color.white);
		}
		for(int i=0 ; i<13 ; i++) {
			jjaturi[i] = new Label();
			jjaturi[i].setBackground(Color.white);
		}
		//===============	end of initialization
		arrangeComponents();
	}
	//====================	end of consturctor

	//	calendar�� �ش� ���� setting�� �Ŀ� �ʿ��� ������ �ʱ�ȭ.
	private void initValues() {
		maxday = calendar.getActualMaximum(Calendar.DATE);
		firstYoil = calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * @return
	 * @uml.property  name="mode"
	 */
	public int getMode() {
		return mode;
	}

	public void setDate(int year, int month) {
		calendar.set(year, month, 1);
		this.year = year;
		this.month = month;
		initValues();
		arrangeComponents();
	}
        //04.03.24_����
        /**
		 * @uml.property  name="group"
		 * @uml.associationEnd  
		 */
        CalLabelGroup group;
	//�޷� ����� ���� ������Ʈ ���� �迭�Ѵ�.
	private void arrangeComponents() {
		currentJjaturiIndex = 0;
		int addCount = 0;
		layout.setRows(calendar.getActualMaximum(Calendar.WEEK_OF_MONTH));
		layout.setColumns(7);
                //04.03.24_����
		group = new CalLabelGroup(this);
		group.setBackground(platColor);
		group.setMode(mode);
		for(int i=0 ; i<firstYoil-1 ; i++) {
			group.add(jjaturi[currentJjaturiIndex++]);
			addCount++;
		}

		for(int i=0 ; i<maxday ; i++) {
			if(addCount % 7 == 0) {
				if(addCount != 0)
				{
					add(group);
					group = new CalLabelGroup(this);
					group.setBackground(platColor);
					group.setMode(mode);
				}

				days[i].setForeground(Color.red);
			}
			else
				days[i].setForeground(Color.black);
			group.addCalLabels(days[i]);
			days[i].setGroup(group);
			addCount++;
		}
		while(addCount % 7 != 0) {
			group.add(jjaturi[currentJjaturiIndex++]);
			addCount++;
		}
		add(group);
	}

	public void groupSelected(CalLabelGroup group) {
		if(selectedGroup != null) {
			selectedGroup.setBackground(platColor);
			selectedGroup.isSelected = false;
		}
		selectedGroup = group;
		group.isSelected = true;
		group.setBackground(lowerColor);
	}

	public void labelSelected(CalLabel label) {
		if(mode == DAY_MODE || mode == PSCalendar.HOUR_MODE) {
			if(selectedLabel != null) {
				selectedLabel.setBackground(platColor);
				selectedLabel.setSelected(false);
			}
			selectedLabel = label;
			label.setSelected(true);
		}
		else {
			if(afterLabel != null) {
				int b = beforeLabel.getIndex();
				int a = afterLabel.getIndex();
				if(a > b)
					setLabelSelected(b, a, false);
				else
					setLabelSelected(a, b, false);
				beforeLabel = null;
				afterLabel = null;
			}
			if(beforeLabel == null) {
				beforeLabel = label;
				beforeLabel.setSelected(true);				
			}
			else {
				afterLabel = label;
				int b = beforeLabel.getIndex();
				int a = afterLabel.getIndex();
				if(a > b)
					setLabelSelected(b, a, true);
				else
					setLabelSelected(a, b, true);
			}
		}
	}

	private void setLabelSelected(int start, int end, boolean selected) {
		for(int i=start ; i<=end ; i++)
			days[i].setSelected(selected);
	}

	public int[] getSelectedTerm() {
		if(mode == WEEK_MODE) {
			if(selectedGroup != null) {
				int [] it = selectedGroup.getTerm();
				System.out.println(it[0] + " " + it[1]);
				return it;
			}
			else
				return null;
		}
		else if(mode == CUSTOM_MODE) {
			if(afterLabel == null) afterLabel = beforeLabel;
			if(beforeLabel == null) return null;
			else {
				int[] result = new int[2];
				result[0] = Integer.valueOf(beforeLabel.getText()).intValue();
				result[1] = Integer.valueOf(afterLabel.getText()).intValue();
				return result;
			}
		}
		return null;
	}

	public int getSelectedDay() {
		if(selectedLabel == null) return 0;
		return Integer.valueOf(selectedLabel.getText()).intValue();
	}
        //04.03.25_���� CalLabelGroup������ Mouse Double Click �̺�Ʈ�� �޾� ProperyChangeListener ��ü(OmniCalendar)���� �����Ѵ�.
        public void dispatchMouseDoubleClicked() {
            System.out.println("dispatchMouseDoubleClicked() called!!");
            this.firePropertyChange("Double Click",null,null);
        }
}//====================	end of class CalendarPanel
