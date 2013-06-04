package kr.ac.hanyang.webstart.internal_frame;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;

import kr.ac.hanyang.algorithm.rca.Kendall;
import kr.ac.hanyang.common.TableModel;
import kr.ac.hanyang.common.Util;
import kr.ac.hanyang.entity.AbstractEntity;
import kr.ac.hanyang.entity.DataSet;
import kr.ac.hanyang.entity.DataSets;
import kr.ac.hanyang.webstart.ActionProcessor;
import kr.ac.hanyang.webstart.MainFrame;

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
public class CorrelationFrm extends AbstractInternalFrame {

	/**
	 * @uml.property  name="targets" multiplicity="(0 -1)" dimension="1"
	 */
	private Object[] targets;
	
	/**
	 * @uml.property  name="sources"
	 */
	private ArrayList<DataSets> sources;
	
	/**
	 * @param title
	 */
	public CorrelationFrm(ArrayList<DataSets> sources) {
		super("Correlaton Analysis..");
		this.sources = sources;
		
		setStatus("초기화 합니다.");
		setStatus(0);
		cor();
	}
	
	public CorrelationFrm(TableModel model) {
		super(model.getPath());
		table.setModel(model);
	}
	
	private void calc(Vector<AbstractEntity> dataSetList) {
		for(int i = 0, end = dataSetList.size(); i < end; i++) {
			DataSet ds = (DataSet)dataSetList.get(i);
			ArrayList<Object> row = new ArrayList<Object>();
			row.add(ds);
			row.add(ds.getProperty(DataSet.MO));
			row.add(ds.getProperty(DataSet.ATTR));
			row.add(ds.getProperty(DataSet.FROM_DATE));
			row.add(ds.getProperty(DataSet.TO_DATE));
			row.add(ds.getProperty(DataSet.INTERVAL));
			row.add(ds.getProperty(DataSet.TOTAL));
			row.add(ds.getProperty(DataSet.LOSS));
			
			for(int j = 0, jend = targets.length; j < jend; j++) {
				Util.debug(this, "ds.isAvailable()="+ds.isAvailable()+", ds="+ds);
				if(ds.isAvailable()) {
					double cor = Kendall.cor(((DataSet)targets[j]).getData(), ds.getData());
					row.add(cor);
				} else {
					row.add("N/A("+ds.getCause()+")");
				}
			}
			
			model.addRow(row.toArray());
		}
	}
	
	public void cor() {
		Runnable doRun = new Runnable() {
			public void run() {
				long start = System.currentTimeMillis();
				for(int i = 0, end = sources.size(); i < end; i++) {
					DataSets dataSets = sources.get(i);
					calc(dataSets.getChildren());
					setStatus((i * 100)/end);
				}
				
				setStatus(100);
				long end = System.currentTimeMillis();
				setStatus("수집을 완료 하였습니다.(수집시간:" + (end - start) / 1000 + "초)");

			}
		};
		Thread thread = new Thread(doRun);
		thread.start();
	}

	public CorrelationFrm() {
		super();
	}

	/**
	 * @param title
	 */
	public CorrelationFrm(String title) {
		super(title);

	}

	protected void init() {

//		Object[] columns = { "Host", "Managed Object", "Attribute", "From",
//				"To", "Interval", "Total", "Loss", "data" };
		ArrayList<Object> columns = new ArrayList<Object>();
		columns.add("Host"); 
		columns.add("Managed Object");  
		columns.add("Attribute");  
		columns.add("From"); 
		columns.add("To");  
		columns.add("Interval");  
		columns.add("Total");
		columns.add("Loss");
		
		targets = MainFrame.getTargetDataSets();
		for(int i = 0, end = targets.length; i < end; i++) {
			columns.add(targets[i]);
		}

		this.setTableColumns(columns.toArray());

		// this.addButton(ActionProcessor.COLLECT, "get_next.gif");
		// this.addButton(ActionProcessor.STOP, "stop.gif");
		this.addButton(ActionProcessor.SAVE_CSV, "save.gif");
		this.addButton(ActionProcessor.ADD_DATASETS, "compliance.gif");
	}

	@Override
	public void buttonAction(MouseEvent e) {
		JButton button = (JButton) e.getSource();

		if (button.getText().equals("SAVE CSV")) {
			Util.saveCSVFileByTableModel(this, model);
		}

	}
}
