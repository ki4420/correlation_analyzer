/**
 * 
 */
package kr.ac.hanyang.webstart.internal_frame;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.management.Descriptor;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.swing.JButton;

import kr.ac.hanyang.collector.ChannelManager;
import kr.ac.hanyang.collector.jmx.Data;
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
public class CollectorFrm extends AbstractInternalFrame {

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
	 * @param title
	 */
	public CollectorFrm(Collector collector) {
		super(collector.getName());
		this.collector = collector;
		jmx = (JMXChannel)ChannelManager.getChannel(collector.getProperty(Collector.URL).toString());
		setStatus("초기화 합니다.");
		setStatus(0);

		Runnable doRun = new Runnable() {
			public void run() {
				long start = System.currentTimeMillis();
				ArrayList<Target> targets = getCompareTargets();

				for (int i = 0; i < targets.size(); i++) {
					collectCompareItem(targets.get(i));
					setStatus((i * 100) / targets.size());

				}

				setStatus(100);
				long end = System.currentTimeMillis();
				setStatus("수집을 완료 하였습니다.(수집시간:" + (end - start) / 1000 + "초)");

			}
		};
		Thread thread = new Thread(doRun);
		thread.start();
	}
	
	public CollectorFrm(TableModel model) {
		super(model.getPath());
		table.setModel(model);
	}

	private void collectCompareItem(Target item) {
		Data data = jmx.getPastValues(item, collector);

		if (data.getLossRate() < 20) {
			model.addRow(new Object[] { data, data.getMOName(),
			data.getAttribute(), data.getFromDateString(), data.getToDateString(), data.getIntervalName(), data.getValueCount(), data.getLossRate(), data.getDataStr() });
//			setChartData(targetChart1, data);
		} else {
			setStatus("데이터의 손실률이 높아서 제거 합니다." + data.getHost() + "의 "
					+ data.getMOName() + " " + data.getAttribute()
					+ " lossRate=" + data.getLossRate() + "%");
		}

//		model.addRow(new Object[] { data, data.getMOName(),
//				data.getAttribute(), data.getFromDateString(), data.getToDateString(), data.getIntervalName(), data.getValueCount(), data.getLossRate(), data.getDataStr() });
		
	}

	public ArrayList<Target> getCompareTargets() {
		long start = System.currentTimeMillis();
		ArrayList<Target> result = null;
		if (collector != null) {
			result = new ArrayList<Target>();

			Object[] oTargetDevices = (Object[]) collector
					.getProperty(Collector.TARGET_DEVICE_ON_ARRAY);

			for (int i = 0; i < oTargetDevices.length; i++) {
				Location device = new Location(oTargetDevices[i].toString());
				findMo(device, result);
			}
			setStatus(oTargetDevices.length + "개 장치에서 " + result.size()
					+ "의 지표를 추출 하였습니다.(소요시간:"
					+ (System.currentTimeMillis() - start) / 1000 + "초)");
			setStatus(100);
		}
		return result;
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
	public CollectorFrm() {
		super();

	}

	/**
	 * @param title
	 */
	public CollectorFrm(String title) {
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
