package kr.ac.hanyang.common.date_panel;



import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kr.ac.hanyang.common.ImageManager;



/**
 * @author  KimChul
 */
public class MonthSelection extends JPanel {

	/**
	 * @uml.property  name="yearIncButton"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JButton yearIncButton = null;

	/**
	 * @uml.property  name="yearDecButton"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JButton yearDecButton = null;

	/**
	 * @uml.property  name="monthIncButton"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JButton monthIncButton = null;

	/**
	 * @uml.property  name="monthDecButton"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JButton monthDecButton = null;



	/**
	 * @uml.property  name="yearLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JLabel yearLabel = null;

	/**
	 * @uml.property  name="monthLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JLabel monthLabel = null;



	/**
	 * @uml.property  name="year"
	 */
	int year;

	/**
	 * @uml.property  name="month"
	 */
	int month;



	/**
	 * @uml.property  name="oCal"
	 * @uml.associationEnd  
	 */
	PSCalendar oCal = null;



	public MonthSelection(PSCalendar oCal) {

		this(0, 0, oCal);

	}



	public MonthSelection(int year, int month, PSCalendar oCal) {

		setLayout(new FlowLayout(FlowLayout.CENTER));

		this.oCal = oCal;



		//주어진 설정이 없으면, 현재 년과 월로 초기화

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

//		ImageManager.getImageIcon(CALENDAR));

		ImageIcon incIcon = ImageManager.getImageIcon("inc.jpg");

        ImageIcon decIcon = ImageManager.getImageIcon("dec.jpg");

		yearIncButton = new JButton(incIcon);

		yearDecButton = new JButton(decIcon);

		monthIncButton = new JButton(incIcon);

		monthDecButton = new JButton(decIcon);

        yearIncButton.setBorder(null);

        yearDecButton.setBorder(null);

        monthIncButton.setBorder(null);

        monthDecButton.setBorder(null);

		Dimension dm = new Dimension(10,15);

		yearIncButton.setPreferredSize(dm);

		yearDecButton.setPreferredSize(dm);

		monthIncButton.setPreferredSize(dm);

		monthDecButton.setPreferredSize(dm);



		yearLabel = new JLabel(String.valueOf(this.year));

		monthLabel = new JLabel(String.valueOf(this.month));



		add(yearDecButton);

		add(yearLabel);

		add(yearIncButton);

		Dimension d = new Dimension(30,20);

                JLabel t1 = new JLabel("년");

		t1.setPreferredSize(d);

		add(t1);

		add(monthDecButton);

		add(monthLabel);

		add(monthIncButton);

                t1 = new JLabel("월");

		t1.setPreferredSize(d);

		add(t1);



		ButtonListener listener = new ButtonListener();

		yearIncButton.addActionListener(listener);

		yearDecButton.addActionListener(listener);

		monthIncButton.addActionListener(listener);

		monthDecButton.addActionListener(listener);

	}



	/**
	 * @return
	 * @uml.property  name="year"
	 */
	public int getYear() {

		return Integer.valueOf(yearLabel.getText()).intValue();

	}



	/**
	 * @return
	 * @uml.property  name="month"
	 */
	public int getMonth() {

		return Integer.valueOf(monthLabel.getText()).intValue();

	}



	private void setValue(JLabel label, boolean increase) {

		int val = Integer.valueOf(label.getText()).intValue();

		if(label == monthLabel && (val == 12 || val == 1)) {

			if(val == 12 && increase) {

				int y = Integer.valueOf(yearLabel.getText()).intValue();

				yearLabel.setText(String.valueOf(y+1));

				monthLabel.setText("1");

				return;

			}

			else if(val == 1 && !increase){

				int y = Integer.valueOf(yearLabel.getText()).intValue();

				yearLabel.setText(String.valueOf(y-1));

				monthLabel.setText("12");

				return;

			}

		}

		if(increase) val++;

		else	val--;

		label.setText(String.valueOf(val));

	}



	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e){

			Component c = (Component)e.getSource();

			if(c == yearIncButton) {

				setValue(yearLabel, true);

			}

			else if(c == yearDecButton) {

				setValue(yearLabel, false);

			}

			else if(c == monthIncButton) {

				setValue(monthLabel, true);

			}

			else if(c == monthDecButton) {

				setValue(monthLabel, false);

			}

			oCal.setDate(Integer.valueOf(yearLabel.getText()).intValue(),

				Integer.valueOf(monthLabel.getText()).intValue());


		}

	}

}

