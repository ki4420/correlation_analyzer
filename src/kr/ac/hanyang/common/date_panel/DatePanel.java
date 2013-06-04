package kr.ac.hanyang.common.date_panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import kr.ac.hanyang.common.ImageManager;



/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class DatePanel extends JPanel implements ActionListener {

	public static final int MINUTELY = 1;

	public static final int HOURLY = 2;

	public static final int DAILY = 3;

	static final long HOUR = 3600 * 1000;

	/**
	 * @uml.property  name="jLabel1"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JLabel jLabel1 = new JLabel();

	/**
	 * @uml.property  name="jLabel2"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JLabel jLabel2 = new JLabel();

	/**
	 * @uml.property  name="jTFStartDate"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JTextField jTFStartDate = new JTextField();

	/**
	 * @uml.property  name="jButtonStartDate"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JButton jButtonStartDate = new JButton();

	/**
	 * @uml.property  name="jButtonEndDate"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JButton jButtonEndDate = new JButton();

	/**
	 * @uml.property  name="jTFEndDate"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JTextField jTFEndDate = new JTextField();

	// for calendar
	// ***************************************************************
	/**
	 * @uml.property  name="dialog"
	 * @uml.associationEnd  
	 */
	JDialog dialog;

	/**
	 * @uml.property  name="calendar"
	 * @uml.associationEnd  
	 */
	PSCalendar calendar;

	/**
	 * @uml.property  name="btn1"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JButton btn1 = new JButton();

	/**
	 * @uml.property  name="btn2"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JButton btn2 = new JButton();

	/**
	 * @uml.property  name="flowLayout1"
	 */
	FlowLayout flowLayout1 = new FlowLayout();

	/**
	 * @uml.property  name="source"
	 */
	Object source;

	/**
	 * @uml.property  name="_isStartDate"
	 */
	private boolean _isStartDate;

	/**
	 * @uml.property  name="parent"
	 * @uml.associationEnd  
	 */
	private JFrame parent;

	// ****************************************************************************

	/**
	 * @uml.property  name="bFirst"
	 */
	boolean bFirst = true;

	/**
	 * @uml.property  name="now"
	 */
	Date now = null;

	public DatePanel() {
		initAll();
		setFixedStartDate(24);
	}

	public DatePanel(JFrame frame) {
		this.parent = frame;
		initAll();

		setFixedStartDate(24);
	}

	private void initAll() {
		try {

			jbInit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final String CALENDAR = "calendar.gif";

	private void jbInit() throws Exception {
		Font font = new Font("Dialog", 0, 12);
		jLabel1.setText("검색기간 설정");
		jLabel1.setFont(font);

		jTFStartDate.setPreferredSize(new Dimension(100, 22));
		jTFStartDate.setFont(font);
		jLabel2.setText("~");// 장애 등급
		jLabel2.setFont(font);
		jButtonStartDate.setBorder(null);
		jButtonStartDate.setActionCommand("startdate");
		// jButtonStartDate.setToolTipText(ResourceManager.getFaultText(StationResource.FAULT_216));
		jButtonStartDate.addActionListener(this);
		jButtonStartDate.setIcon(ImageManager.getImageIcon(CALENDAR));
		jButtonEndDate.setBorder(null);
		jButtonEndDate.setActionCommand("enddate");
		// jButtonEndDate.setToolTipText(ResourceManager.getFaultText(StationResource.FAULT_216));
		jButtonEndDate.addActionListener(this);
		jButtonEndDate.setIcon(ImageManager.getImageIcon(CALENDAR));

		jTFEndDate.setPreferredSize(new Dimension(100, 22));
		jTFEndDate.setFont(font);

		this.setOpaque(true);
		this.setPreferredSize(new Dimension(230, 260));
		this.setToolTipText("");

		jLabel1.setText("검색기간 설정");
		
		flowLayout1.setAlignment(FlowLayout.LEFT);
		this.add(jLabel1, null);
//		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		this.add(jTFStartDate, null);
//		jTFStartDate.setBackground(new java.awt.Color(0, 15, 98));
//		jTFStartDate.setForeground(new java.awt.Color(255, 255, 255));
		this.add(jButtonStartDate, null);
		this.add(jLabel2, null);
//		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		this.add(jTFEndDate, null);
//		jTFEndDate.setBackground(new java.awt.Color(0, 15, 98));
//		jTFEndDate.setForeground(new java.awt.Color(255, 255, 255));
		this.add(jButtonEndDate, null);

		this.setLayout(flowLayout1);

		initDateTextFiled();
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("startdate")) {
			// source = e.getSource();
			makeDialog(true);
		} else if (command.equals("enddate")) {
			// source = e.getSource();
			makeDialog(false);
		} else if (command.equals("open")) {
			source = e.getSource();
			if (_isStartDate) {
				jTFStartDate.setText(calendar.getValue());
			} else {
				jTFEndDate.setText(calendar.getValue());
			}
			dialog.dispose();
		} else if (command.equals("close")) {
			dialog.dispose();
		}

	}

	private void makeDialog(boolean isStartDate) {
		_isStartDate = isStartDate;
		if (dialog != null) {
			dialog.dispose();
			dialog = null;
		}

		calendar = new PSCalendar(PSCalendar.HOUR_MODE);
		if (parent != null) {
			dialog = new JDialog(parent, "날짜 선택", true);
		} else {
			dialog = new JDialog();
		}

		dialog.setTitle("NIMS CALENDAR");

		dialog.getContentPane().setLayout(new BorderLayout());
		JPanel panel1 = new JPanel();
		
		panel1.setLayout(null);
		panel1.add(btn1);
		panel1.add(btn2);
		btn1.setLocation(90, 3);
		btn2.setLocation(153, 3);
		

		dialog.getContentPane().add(panel1, BorderLayout.SOUTH);

		btn1.setText("확인");
		btn2.setText("최소");

		dialog.getContentPane().add(calendar, BorderLayout.CENTER);
		// calendar.revalidate();
		// calendar.repaint();
		final boolean isStart = isStartDate;
		// 04.03.25_혁이 Calendar의 Double Click 이벤트에 반응한다.
		calendar
				.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
					public void propertyChange(java.beans.PropertyChangeEvent e) {
						if (e.getPropertyName().equals("Double Click")) {
							if (isStart) {
								jTFStartDate.setText(calendar.getValue());
								dialog.setVisible(false);
							} else {
								jTFEndDate.setText(calendar.getValue());
								dialog.setVisible(false);
							}
						}
					}
				});

		btn1.addActionListener(this);
		btn1.setActionCommand("open");
		btn2.addActionListener(this);
		btn2.setActionCommand("close");
		

		dialog.setSize(220, 200);
		dialog.setResizable(false);
		Point locationPoint;
		if (isStartDate) {
			locationPoint = jButtonStartDate.getLocationOnScreen();

		} else {
			locationPoint = jButtonEndDate.getLocationOnScreen();
		}
		dialog.setLocation(locationPoint.x - 100, locationPoint.y + 20);
		dialog.setVisible(true);
	}

	public Date getDate(Date originDate, int minusDay) {
		Date result = null;
		long currentTime = originDate.getTime();
		currentTime += minusDay * 24 * HOUR;
		result = new Date(currentTime);
		System.out.println("originDate:" + df.format(originDate)
				+ ", minusDay:" + minusDay + ", result:" + df.format(result));
		return result;
	}

	public boolean checkDate() {
		if (jTFStartDate.getText() == null) {
			JOptionPane.showMessageDialog(this, "검색 시작 날짜를 입력하십시요"
			,"POLESTAR(TM)", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (jTFStartDate.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "검색 시작 날짜를 입력하십시요"
			, "POLESTAR(TM)", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {

		}
		if (jTFEndDate.getText() == null) {
			JOptionPane.showMessageDialog(this, "검색 종료 날짜를 입력하십시요"
			, "POLESTAR(TM)", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (jTFEndDate.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "검색 종료 날짜를 입력하십시요"
			, "POLESTAR(TM)", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {

		}
		return true;
	}

	public String getStartDateString() {
		return jTFStartDate.getText();
	}

	public Date getStartDate() {
		Date result = null;
		try {
			result = df.parse(getStartDateString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getEndDateString() {
		return jTFEndDate.getText();
	}

	public Date getEndDate() {
		Date result = null;
		try {
			result = df.parse(getEndDateString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setFixedStartDate(int minusHour) {

		if (bFirst) {

			now = new Date();

			bFirst = false;

		}

		Date startDate;
		Date endDate;

		long startTime = now.getTime();
		long endTime = now.getTime();
		startTime -= (minusHour - 1) * HOUR; // iDay 전
		endTime += 1 * HOUR; // iDay 전
		startDate = new Date(startTime);
		endDate = new Date(endTime);

		jTFStartDate.setText(df.format(startDate));
		jTFEndDate.setText(df.format(endDate));
		now = startDate;

	}
	
	public void setStartDate(Date date) {

		jTFStartDate.setText(df.format(date));
		now = date;
	}
	
	public void setEndDate(Date date) {

		jTFEndDate.setText(df.format(date));
	}

	/**
	 * @uml.property  name="df"
	 */
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");

	private void initDateTextFiled() {

		Date currentDate = new Date();

		jTFStartDate.setText(df.format(currentDate));
		jTFEndDate.setText(df.format(currentDate));

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		DatePanel d = new DatePanel();
		frame.getContentPane().add(d);
		d.setBounds(0, 0, 200, 30);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}

}
