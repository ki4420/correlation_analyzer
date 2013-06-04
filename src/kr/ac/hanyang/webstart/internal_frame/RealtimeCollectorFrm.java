/**
 * 
 */
package kr.ac.hanyang.webstart.internal_frame;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.management.Descriptor;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.swing.JButton;

import kr.ac.hanyang.collector.ChannelManager;
import kr.ac.hanyang.collector.jmx.JMXChannel;
import kr.ac.hanyang.collector.jmx.Location;
import kr.ac.hanyang.collector.jmx.MO;
import kr.ac.hanyang.collector.jmx.Target;
import kr.ac.hanyang.common.TableModel;
import kr.ac.hanyang.common.Util;
import kr.ac.hanyang.entity.Collector;
import kr.ac.hanyang.webstart.ActionProcessor;

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
 * @author KimChul
 * 
 */
public class RealtimeCollectorFrm extends AbstractInternalFrame {

	/**
	 * @uml.property  name="collector"
	 * @uml.associationEnd  
	 */
	private Collector collector;
	/**
	 * @uml.property  name="jmx"
	 * @uml.associationEnd  
	 */
	private JMXChannel jmx;
	
	/**
	 * @uml.property  name="tableMap"
	 * @uml.associationEnd  qualifier="key:java.lang.String [Ljava.lang.Object;"
	 */
	private HashMap<String, Object[]> tableMap = new HashMap<String, Object[]>();

	
	/**
	 * @uml.property  name="count"
	 */
	int count = 0;
	/**
	 * @param title
	 */
	public RealtimeCollectorFrm(Collector collector) {
		super(collector.getName());
		this.collector = collector;
		jmx = (JMXChannel)ChannelManager.getChannel(collector.getProperty(Collector.URL).toString());
		setStatus("초기화 합니다.");
		setStatus(0);

		Runnable doRun = new Runnable() {
			public void run() {
				while(true) {
					try {
						long start = System.currentTimeMillis();
						ArrayList<Target> targets = getTargets();

						for (int i = targets.size() - 1; i >= 0; i--) {
							appendItem(i, targets.get(i));
							setStatus((i * 100) / targets.size());

						}
						long end = System.currentTimeMillis();
						setStatus("최근 수집 시간:"+Util.FULL_FORMAT_24H.format(new Date(end))+"(수집시간:" + (end - start) / 1000 + "초)");
						
						if(count%10 == 0) {
							Object content = Util.makeCSV(model);
							Util.createFile("./tempSave.csv", content.toString());
						} else {
							Thread.sleep(5000);
						}
						count++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread thread = new Thread(doRun);
		thread.start();
	}
	
	public RealtimeCollectorFrm(TableModel model) {
		super(model.getPath());
		table.setModel(model);
	}

	private void appendItem(int index, Target item) {
		
		Object value = jmx.getValue(item.getMO().getOName(), item.getAttribute());
		if(value != null && value instanceof Integer && Integer.parseInt(value.toString()) == Integer.MIN_VALUE) {
			targets.remove(index);
			Util.debug(this, "Remove item="+item);
			return;
		}
		
		String key = item.getMO().getOName() + item.getAttribute();
		Object[] row = (Object[])tableMap.get(key);
		if(row != null) {
			int rowIndex = (Integer)row[9];
			String v = (String)row[8];
			String newV = v + ";"+value;
			row[8] = newV;
			model.setValueAt(newV, rowIndex, 8);
			// 4
			model.setValueAt(Util.FULL_FORMAT_24H.format(new Date()), rowIndex, 4);
			// 6 count
			int count = (Integer)row[6];
			int newCount = count + 1;
			row[6] = newCount;
			model.setValueAt(newCount, rowIndex, 6);
			tableMap.put(key, row);
		} else {
			int newRowIndex = model.getRowCount();
			String dateStr = Util.FULL_FORMAT_24H.format(new Date());
			Object[] newRow = new Object[] { item.getMO().getOName().getKeyProperty("location"), item.getMO(),
					item.getAttribute(), dateStr, dateStr, "실시간", 1, "-", String.valueOf(value), newRowIndex };
			model.addRow(newRow);
			
			tableMap.put(key, newRow);
		}
		
	}
	
	/**
	 * @uml.property  name="targets"
	 */
	private ArrayList<Target> targets = null;

	public ArrayList<Target> getTargets() {
		long start = System.currentTimeMillis();
		if (collector != null && targets == null) {
			targets = new ArrayList<Target>();

			Object[] oTargetDevices = (Object[]) collector
					.getProperty(Collector.TARGET_DEVICE_ON_ARRAY);

			for (int i = 0; i < oTargetDevices.length; i++) {
				Location device = new Location(oTargetDevices[i].toString());
				findMo(device, targets);
			}
			setStatus(oTargetDevices.length + "개 장치에서 " + targets.size()
					+ "의 지표를 추출 하였습니다.(소요시간:"
					+ (System.currentTimeMillis() - start) / 1000 + "초)");
			setStatus(100);
		}
		return targets;
	}

	private void findMo(Location location, ArrayList<Target> result) {
		Object[] moList = jmx.getMbeanByLocation(location.getOName()
				.getKeyProperty("location"));
		for (int i = 0; i < moList.length; i++) {
			findTarget(new MO((ObjectName) moList[i]), result);
		}
	}

	private void findTarget(MO mo, ArrayList<Target> result) {
		if (mo != null) {
			MBeanInfo mInfo = jmx.getMBeanInfo(mo.getOName());

			if (mInfo instanceof ModelMBeanInfoSupport) {
				ModelMBeanInfoSupport modelMBean = (ModelMBeanInfoSupport) mInfo;
				
				// MBeanAttributeInfo ex) Name==Used, Description==물리적 메모리 사용량,
				// Type==long
				MBeanAttributeInfo[] infos = modelMBean.getAttributes();
				try { 
					Descriptor mBeanDescriptor = modelMBean.getMBeanDescriptor();
					String name = (String) mBeanDescriptor.getFieldValue("mbeanName");
					if(name != null) {
//						Util.debug(this, "mBeanName="+name);
						mo.setMbeanName(name);
					}
				} catch (Exception e) {
					
				}
				
				for (int i = 0, end = infos.length; i < end; i++) {
					MBeanAttributeInfo info = (MBeanAttributeInfo) infos[i];
					String key = info.getName();
					Descriptor descriptor = null;
					try {
						descriptor = modelMBean.getDescriptor(key);
						
						String classification = (String) descriptor
								.getFieldValue("classification");
						
						
						
						int visibility = Integer.parseInt((String) descriptor
								.getFieldValue("visibility"));
						if (!"configuration".equals(classification)
								&& visibility < 5) {
							result.add(new Target(mo, key));
						}
					} catch (Exception e) {
						System.out.println("error=" + mo + ":::" + key);
						// e.printStackTrace();
					}
				}

			}

		}
	}

	/**
	 * 
	 */
	public RealtimeCollectorFrm() {
		super();

	}

	/**
	 * @param title
	 */
	public RealtimeCollectorFrm(String title) {
		super(title);

	}

	protected void init() {

		Object[] columns = { "Host", "Managed Object", "Attribute", "From",
				"To", "Interval", "Total", "Loss", "data" };
		this.setTableColumns(columns);

//		this.addButton(ActionProcessor.COLLECT, "get_next.gif");
//		this.addButton(ActionProcessor.STOP, "stop.gif");
		this.addButton(ActionProcessor.SAVE_CSV, "save.gif");
		this.addButton(ActionProcessor.ADD_DATASETS, "compliance.gif");
	}

	@Override
	public void buttonAction(MouseEvent e) {
		JButton  button = (JButton)e.getSource();
		
		if(button.getText().equals("SAVE CSV")) {
			Util.saveCSVFileByTableModel(this, model);
		}

	}

}
