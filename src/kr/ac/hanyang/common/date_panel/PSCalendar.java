/**===========================================================

	작성자 :

==========================================================**/

package kr.ac.hanyang.common.date_panel;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;



public class PSCalendar extends JPanel implements PropertyChangeListener{

	/**
	 * @uml.property  name="calPanel"
	 * @uml.associationEnd  
	 */
	CalendarPanel calPanel = null;

	/**
	 * @uml.property  name="year"
	 */
	int year;

	/**
	 * @uml.property  name="month"
	 */
	int month;

	/**
	 * @uml.property  name="centerPanel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JPanel centerPanel = null;

	/**
	 * @uml.property  name="mode"
	 */
	int mode;
	
	public final static int HOUR_MODE = 100;

	/**
	 * @uml.property  name="monthSel"
	 * @uml.associationEnd  
	 */
	MonthSelection monthSel = null;



        /**
		 * @uml.property  name="result"
		 */
        String result = "";



	public PSCalendar(int mode) {

		this(0, 0, mode);

	}



	public PSCalendar(int year, int month, int mode) {

		setLayout(new BorderLayout());

		this.mode = mode;

		//setBorder(BorderFactory.createLoweredBevelBorder());



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



		monthSel = new MonthSelection(this.year, this.month, this);

		calPanel = new CalendarPanel(0, 0, mode);

                //04.03.25_혁이

                calPanel.addPropertyChangeListener(this);

		centerPanel = new JPanel(new BorderLayout());

		centerPanel.add(calPanel, "Center");

		JPanel yoilPanel = new JPanel(new GridLayout(1, 7));



                yoilPanel.add(new Label("일", Label.CENTER));

                yoilPanel.add(new Label("월", Label.CENTER));

                yoilPanel.add(new Label("화", Label.CENTER));

                yoilPanel.add(new Label("수", Label.CENTER));

                yoilPanel.add(new Label("목", Label.CENTER));

                yoilPanel.add(new Label("금", Label.CENTER));

                yoilPanel.add(new Label("토", Label.CENTER));

		centerPanel.add(yoilPanel, "North");

		centerPanel.setBackground(new Color(30, 30, 125));

		centerPanel.setBorder(BorderFactory.createLoweredBevelBorder());



		add(monthSel, "North");

		add(centerPanel, "Center");

	}
    public void setAllFont(Font font) {
        this.setFont(font);
        for(int i = 0, iEnd = this.getComponentCount(); i < iEnd; i++) {
            Component child = this.getComponent(i);
            child.setFont(font);
            //Common.println("child[1]>>"+child);
            if(child instanceof JComponent )recusiveSetFont((JComponent)child, font);

        }
    }
    void recusiveSetFont(JComponent comp, Font font) {
        for(int i = 0, iEnd = comp.getComponentCount(); i < iEnd; i ++) {
            Component child = comp.getComponent(i);
            child.setFont(font);
            //Common.println("child[2]>>"+child);
            if(child instanceof JComponent )recusiveSetFont((JComponent)child, font);
        }

    }



	public void setDate(int year, int month) {

		//calPanel.setDate(year, month);

		centerPanel.remove(calPanel);

		calPanel = new CalendarPanel(year, month, mode);

                //04.03.25_혁이

                calPanel.addPropertyChangeListener(this);

		centerPanel.add(calPanel, "Center");

	}



    private String makeFormat(int i) {

        String res;

        if(i<10) {

            res = "0" + i;

        }

        else res = String.valueOf(i);



        return res;

    }



	// =========================  public methods
    public GregorianCalendar  getDateValue() {
        int i = calPanel.getSelectedDay();
        if (i == 0)return null;
        String sDay = makeFormat(i);
        int iDay = Integer.parseInt(sDay);
        GregorianCalendar c = new GregorianCalendar();
        //Common.println("monthSel.getYear()>>"+monthSel.getYear());
        String sMonth = makeFormat(monthSel.getMonth());
        //Common.println("sMonth>>"+(sMonth));
        c.set(Calendar.YEAR, monthSel.getYear());
        c.set(Calendar.MONTH, Integer.parseInt(sMonth)-1);
        c.set(Calendar.DATE, iDay);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c;

    }
    public Date getBeginDateOfBase(boolean isWeekly) {

       Calendar c = Calendar.getInstance();

       if (isWeekly) {
           c.set(Calendar.DATE, c.get(Calendar.DATE) - 7);
       }
       else {
           c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
       }
       c.set(Calendar.HOUR_OF_DAY, 0);
       c.set(Calendar.MINUTE, 0);
       c.set(Calendar.SECOND, 0);
       //Common.println("[getBeginDateOfBase]c>>>"+c.getTime());
       return c.getTime();

   }


	public String getValue() {

		switch(mode) {
			
			case HOUR_MODE :
				int iDay = calPanel.getSelectedDay();

				if(iDay == 0) return null;

				result = monthSel.getYear() + "-" + makeFormat(monthSel.getMonth())

                     + "-" + makeFormat(iDay)+" 00";

				return result;
				
		
			case CalendarPanel.DAY_MODE :

				int i = calPanel.getSelectedDay();

				if(i == 0) return null;

				result = monthSel.getYear() + "-" + makeFormat(monthSel.getMonth())

                     + "-" + makeFormat(i);

				return result;

			case CalendarPanel.WEEK_MODE :

				int []res = calPanel.getSelectedTerm();

				if(res == null) return null;

    //우종혁 수정 - 주간 return값을 0000-00-00~0000-00-00 형식으로

				result = monthSel.getYear() +  "-" + makeFormat(monthSel.getMonth()) + "-" +

					makeFormat(res[0])+"~"+

                    monthSel.getYear() +  "-" + makeFormat(monthSel.getMonth()) + "-" +

					makeFormat(res[1]);

				return result;

			case CalendarPanel.CUSTOM_MODE :

				int []r = calPanel.getSelectedTerm();

				if(r == null) return null;

				result = monthSel.getYear() +  "-" +makeFormat( monthSel.getMonth()) + "-" +

					makeFormat(r[0]) + ":" + monthSel.getYear() +  "-" +

                    makeFormat(monthSel.getMonth()) + "-" +

					makeFormat(r[1]);

				return result;

			case CalendarPanel.MONTH_MODE :

				result = monthSel.getYear() + "-" + makeFormat(monthSel.getMonth());

				return result;

			case CalendarPanel.YEAR_MODE :

				result = "" + monthSel.getYear();

				return result;

		}

		return null;

	}

        //04.03.25_혁이

        public void propertyChange(PropertyChangeEvent e) {

            System.out.println("OmniCalendar e.getPropertyName() : "+e.getPropertyName());

            if(e.getPropertyName().equals("Double Click")) {

                this.firePropertyChange(e.getPropertyName(), e.getOldValue(),e.getNewValue());

            }

        }

}//	==================== end of class OmniCalendar

