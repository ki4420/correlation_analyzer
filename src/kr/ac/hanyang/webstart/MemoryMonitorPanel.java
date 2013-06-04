package kr.ac.hanyang.webstart;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;

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
public class MemoryMonitorPanel extends JPanel implements ActionListener {
	/**
	 * @uml.property  name="jProgressBarUtil"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JProgressBar jProgressBarUtil;
	/**
	 * @uml.property  name="jLabelHeap"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JLabel jLabelHeap;
	/**
	 * @uml.property  name="jProgressBar4"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JProgressBar jProgressBar4;
	/**
	 * @uml.property  name="jLabelUsed"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JLabel jLabelUsed;
	/**
	 * @uml.property  name="jProgressBar5"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JProgressBar jProgressBar5;
	/**
	 * @uml.property  name="jProgressBar3"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JProgressBar jProgressBar3;
	/**
	 * @uml.property  name="jProgressBar2"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JProgressBar jProgressBar2;
	/**
	 * @uml.property  name="jProgressBar1"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JProgressBar jProgressBar1;
	
	/**
	 * @uml.property  name="pbs"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	private JProgressBar[] pbs = null;//{jProgressBar1, jProgressBar2, jProgressBar3, jProgressBar4, jProgressBar5};
	
	/**
	 * @uml.property  name="runtime"
	 */
	private Runtime runtime;
	
	/**
	 * @uml.property  name="timer"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Timer timer = null;

	/**
	 * @uml.property  name="timerSleep"
	 */
	private int timerSleep = 3000;

	/**
	 * 타이머 스타트.
	 * 
	 */
	public void timerStart() {
		if (timer == null) {
			timer = new Timer(timerSleep, this);
		}
		if (timer.isRunning()) {
			timer.stop();
		}
		timer.start();

	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			try {
				calcHealth();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public MemoryMonitorPanel() {
		try {
			initGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.updateUI();
		timerStart();
	
	}
	
	private void calcHealth() {
//		System.out.println("calcHealth");
		if(runtime == null) {
			runtime = Runtime.getRuntime();
		}
		long free = runtime.freeMemory();
		long total = runtime.totalMemory();
		double util = ( ((total - free)*100)/total );
//		System.out.println("used="+(total - free));
//		System.out.println("total="+total+", free="+free+", util="+util);
		jProgressBarUtil.setValue(Util.getIntValue(String.valueOf( util) ));
		jProgressBarUtil.setString(String.valueOf( util)+"%");
		String usedStr = Util.getOptimizedByteValue(total - free);
		String totalStr = Util.getOptimizedByteValue(total);
		jLabelHeap.setText("Total: "+totalStr);
		jLabelUsed.setText("Used: "+usedStr);
		
		for(int i = 1, end = pbs.length; i < end; i++) {
			pbs[i - 1].setValue( pbs[i].getValue());
			pbs[i - 1].setString("");
		}
		pbs[4].setValue(jProgressBarUtil.getValue() + 10);
		pbs[4].setString("");
	}
	
	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exc) {
			System.err.println("Error loading L&F: " + exc);
			exc.printStackTrace();
		}
		JFrame f = new JFrame();
		f.getContentPane().add(new MemoryMonitorPanel());
		f.setSize(new Dimension(400, 300));
		f.setVisible(true);
	}
	
	
	private void initGUI() {
		try {
			{
				FlowLayout thisLayout = new FlowLayout();
				thisLayout.setAlignment(FlowLayout.LEFT);
				thisLayout.setHgap(1);
				thisLayout.setVgap(1);
				this.setLayout(thisLayout);
				this.setPreferredSize(new java.awt.Dimension(187, 31));

				{
					jProgressBarUtil = new JProgressBar();
					jProgressBarUtil.setStringPainted(true);
					this.add(jProgressBarUtil);
					jProgressBarUtil.setPreferredSize(new java.awt.Dimension(79, 15));
					jProgressBarUtil.setForeground(Color.orange);
				}
				{
					jLabelHeap = new JLabel();
					this.add(jLabelHeap);
					jLabelHeap.setText("Total 12GB");
					jLabelHeap.setPreferredSize(new java.awt.Dimension(103, 13));
					jLabelHeap.setHorizontalAlignment(SwingConstants.RIGHT);
					jLabelHeap.setFont(new Font("Dialog", 1, 10));
				}
				{
					jProgressBar1 = new JProgressBar();
					this.add(jProgressBar1);
					jProgressBar1.setStringPainted(true);
					jProgressBar1.setForeground(Color.green);
					jProgressBar1.setValue(0);
					jProgressBar1.setString("");
					jProgressBar1.setPreferredSize(new java.awt.Dimension(15, 15));
					jProgressBar1.setOrientation(SwingConstants.VERTICAL);
					jProgressBar1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				}
				{
					jProgressBar2 = new JProgressBar();
					jProgressBar2.setStringPainted(true);
					jProgressBar2.setForeground(Color.green);
					this.add(jProgressBar2);
					jProgressBar2.setValue(0);
					jProgressBar2.setString("");
					jProgressBar2.setOrientation(SwingConstants.VERTICAL);
					jProgressBar2.setPreferredSize(new java.awt.Dimension(15, 15));
					jProgressBar2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				}
				{
					jProgressBar3 = new JProgressBar();
					this.add(jProgressBar3);
					jProgressBar3.setStringPainted(true);
					jProgressBar3.setForeground(Color.green);
					jProgressBar3.setValue(0);
					jProgressBar3.setString("");
					jProgressBar3.setOrientation(SwingConstants.VERTICAL);
					jProgressBar3.setPreferredSize(new java.awt.Dimension(15,15));
					jProgressBar3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				}
				{
					jProgressBar4 = new JProgressBar();
					this.add(jProgressBar4);
					jProgressBar4.setValue(0);
					jProgressBar4.setString("");
					jProgressBar4.setStringPainted(true);
					jProgressBar4.setForeground(Color.green);
					jProgressBar4.setOrientation(SwingConstants.VERTICAL);
					jProgressBar4.setPreferredSize(new java.awt.Dimension(15, 15));
					jProgressBar4.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				}
				{
					jProgressBar5 = new JProgressBar();
					this.add(jProgressBar5);
					jProgressBar5.setValue(0);
					jProgressBar5.setStringPainted(true);
					jProgressBar5.setString("");
					jProgressBar5.setForeground(Color.green);
					jProgressBar5.setOrientation(SwingConstants.VERTICAL);
					jProgressBar5.setPreferredSize(new java.awt.Dimension(15,15));
					jProgressBar5.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					
				}
				{
					jLabelUsed = new JLabel();
					this.add(jLabelUsed);
					jLabelUsed.setText("Used 8GB");
					jLabelUsed.setPreferredSize(new java.awt.Dimension(103, 10));
					jLabelUsed.setHorizontalAlignment(SwingConstants.RIGHT);
					jLabelUsed.setFont(new Font("Dialog", 1, 10));
				}
				 pbs = new JProgressBar[5];
				 pbs[0] = jProgressBar1;
				 pbs[1] = jProgressBar2;
				 pbs[2] = jProgressBar3; 
				 pbs[3] = jProgressBar4; 
				 pbs[4] = jProgressBar5;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
