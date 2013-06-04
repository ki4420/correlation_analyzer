package kr.ac.hanyang.collector.jmx;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import kr.ac.hanyang.collector.IChannel;
import kr.ac.hanyang.common.Util;
import kr.ac.hanyang.entity.Collector;


/**
 * 
 * 폴스타 접속, JMX 커넥션 채널 내부적으로 커넥션을 하나를 갖기 위한 커넥션 채널임. ItemAdapter는 단일 또는 Set 개념의
 * 지표 단위로 묶여 있고, ItemAdapterFactory를 통해 리스트를 제공하여, 커스텀 데이터 등록 시, 선택 할 목록을 제공한다.
 * 
 * @author 김철
 * 
 */
public class JMXChannel implements IChannel {
	
	
	public static SimpleDateFormat DATE_FORM = new SimpleDateFormat("HH:mm");
	public static SimpleDateFormat DATE_FORM_SS = new SimpleDateFormat("HH:mm:ss");
	
	private static boolean isSimulation = false;

	/**
	 * @uml.property  name="jmxc"
	 * @uml.associationEnd  
	 */
	private JMXConnector jmxc = null;

	/**
	 * @uml.property  name="mbsc"
	 * @uml.associationEnd  
	 */
	private MBeanServerConnection mbsc = null;

	/**
	 * @uml.property  name="managerURL"
	 */
	private String managerURL = null;

	/**
	 * @uml.property  name="isConnected"
	 */
	private boolean isConnected = false;

	/**
	 * @uml.property  name="isClosed"
	 */
	private boolean isClosed = false;

	/**
	 * @uml.property  name="timeStampFieldKey"
	 */
	private final String timeStampFieldKey = "lastUpdatedTimeStamp";

	/**
	 * @uml.property  name="staticsServiceON"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" elementType="java.util.HashMap" qualifier="constant:java.lang.String java.lang.Object"
	 */
	private ObjectName staticsServiceON = null;

	/**
	 * @uml.property  name="bulkRetrievalServiceON"
	 * @uml.associationEnd  
	 */
	private ObjectName bulkRetrievalServiceON = null;
	
	/**
	 * @uml.property  name="faultHandlerON"
	 * @uml.associationEnd  
	 */
	private ObjectName faultHandlerON = null;

	/**
	 * @uml.property  name="domainName"
	 */
	private String domainName;

	/**
	 * @uml.property  name="businessOName"
	 * @uml.associationEnd  
	 */
	private ObjectName businessOName = null;

	/**
	 * @uml.property  name="sdeOName"
	 * @uml.associationEnd  
	 */
	private ObjectName sdeOName = null;

	/**
	 * @uml.property  name="controllerOName"
	 * @uml.associationEnd  
	 */
	private ObjectName controllerOName = null;

	/**
	 * @uml.property  name="faultHandlerOName"
	 * @uml.associationEnd  
	 */
	private ObjectName faultHandlerOName = null;
	
	/**
	 * @uml.property  name="thread"
	 */
	private Thread thread;
	
	public JMXChannel(String url) {
		managerURL = url;
	}

	public void initConnection() {
		if (!isConnected)
			connect();
	}

	/**
	 * @uml.property  name="pastValueSignature" multiplicity="(0 -1)" dimension="1"
	 */
	private final String[] pastValueSignature = new String[] {
			"[Ljavax.management.ObjectName;", "[Ljava.lang.String;", "int", "java.lang.String",
			"java.lang.String" };

	// private final String[] pastValueSignature = new String[] {
	// "[Ljavax.management.ObjectName;", "[Ljava.lang.String;", "int",
	// "java.lang.String", "java.lang.String"};

	public final static SimpleDateFormat STATICS_FORM = new SimpleDateFormat(
			"yyyyMMddHHmm");
	
	public final static SimpleDateFormat DATE_VISIBLE_FORM_MINUTE_1 = new SimpleDateFormat("HH시mm분");
	public final static SimpleDateFormat DATE_VISIBLE_FORM_MINUTE_5 = new SimpleDateFormat("HH시mm분");
	public final static SimpleDateFormat DATE_VISIBLE_FORM_MINUTE_10 = new SimpleDateFormat("HH시mm분");
	public final static SimpleDateFormat DATE_VISIBLE_FORM_HOUR_1 = new SimpleDateFormat("MM월dd일 HH시");
	public final static SimpleDateFormat DATE_VISIBLE_FORM_DAY_1 = new SimpleDateFormat("MM월dd일 HH시");
	public final static SimpleDateFormat DATE_VISIBLE_FORM_WEEK_1 = new SimpleDateFormat("MM월dd일 HH시");
	public final static SimpleDateFormat DATE_VISIBLE_FORM_MONTH_1 = new SimpleDateFormat("yyyy년MM월");
 
	
	public Data getPastValues(Target target, Collector collector) {
		
		Date fromDate = (Date)collector.getProperty(Collector.FROM_DATE);
		Date toDate = (Date)collector.getProperty(Collector.TO_DATE);
		int interval = (Integer)collector.getProperty(Collector.INTERVAL);
		
		Data result = getPastValues(target, fromDate, toDate, interval);

		
		return result;
	}
	
	private String parseGetMethod(String attr) {
		String result = null;
		if(attr != null && attr.length() > 0) {
			result = "get" + attr;//attr.substring(0, 1).toUpperCase() + attr.substring(1);
		}
		return result;
	}
	
	public Object getValue(ObjectName on, String attr) {
		
		Object result = -999;
		String errorMsg = null;
		if (!isConnected || on == null || attr == null) {
			Util.debug(this, "getValue exception:isConnected=" + isConnected
					+ ", on=" + on + ", attr=" + attr);
			
		} else {
			
		}

		try {
			result = mbsc.invoke(on, parseGetMethod(attr), null, null);
//			result = (double)mbsc.getAttribute(on, attr);
		} catch (java.net.ConnectException e) {
//			Util.debug(this, "java.net.ConnectionException, reconnection on="+on+", attribute="+attr+", e="+e.getMessage());
			this.isConnected = false;

		} catch (java.rmi.ConnectException e) {
//			Util.debug(this, "java.rmi.ConnectException, reconnection  on="+on+", attribute="+attr+", e="+e.getMessage());
			this.isConnected = false;
		}
		/**
		 * 재커넥션을 하지 않아도 되는 예외처리들
		 */
		catch (MBeanException e) {
//			Util.debug(this, "MBeanException on="+on+", attribute="+attr+", e="+e.getMessage());
			errorMsg = e.getMessage();
		} catch (InstanceNotFoundException e) {
//			Util.debug(this, "InstanceNotFoundException on="+on+", attribute="+attr+", e="+e.getMessage());
			errorMsg = e.getMessage();
		}
		/**
		 * 예상할 수 없는 예외는 재연결을 시도한다.
		 */
		catch (Exception e) {
			
//			Util.debug(this, (new StringBuilder()).append(
//					"getValue error, ObjectName:").append(on).append(
//					", attribute:").append(attr).toString()
//					+ ", managerURL:"
//					+ managerURL
//					+ ", error:"
//					+ e.getMessage());
//			e.printStackTrace();
			if(e.getMessage().indexOf("The client has been closed") > -1) {
				isConnected = false;
				connection();
			}
			errorMsg = e.getMessage();

		}
		if(errorMsg != null && errorMsg.indexOf("AttributeNotSupportedException") > -1) {
			result = Integer.MIN_VALUE;
		}
		return result;
	}
	
	
	
	/**
	 * 통계에서 이전 데이터를 가져온다.
	 * 
	 * @param on
	 * @param attr
	 * @return
	 * 
	 * 
	 * MBean Operation Info
	 * 
	 * DB쿼리를 이용하여 애플릿 등에 통계 값을 줄 수 있는 함수.
	 * 
	 * @param location_id
	 *            통계 대상의 되는 노드 id
	 * @param attribute_name
	 *            통계 대상의 attribute name
	 * @param stat_type
	 *            통계 저장 타입(0=1분통계, 1=5분통계, 2=분통계만, 3=시간통계, 4=일통계, 5=주통계, 6=월통계)
	 * @param start_time
	 *            통계 검색 시작 시간.
	 * @param end_time
	 *            통계 검색 종료 시간.
	 * @param values
	 *            통계 값(MIN=최소값, AVG=평균값, MAX=최대값, TOP=상위, BOTTOM=하위)
	 * @return HashMap 조건에 맞는 값을 리턴한다.(location_id=로케이션 id,
	 *         attribute_name=attribute name, event_date=이벤트 일시, min_value=최소값,
	 *         avg_value=평균값, max_value=최대값, top_value=상위 값, bottom_value=하위 값)
	 *         public List getStatisticsInfo(String[] location_id, String[]
	 *         attribute_name, int stat_type, String start_time, String
	 *         end_time, String[] values)
	 * 
	 */
	public Data getPastValues(Target target, Date startDate, Date endDate, int type) {

		Data result = new Data(target);
		if (!isConnected) {
			return result;
		}

		/**
		 * public List getStatisticsInfo(String[] location_id, String[]
		 * attribute_name, int stat_type, String start_time, String end_time,
		 * String[] values)
		 * stat_type(0=1분통계, 1=5분통계, 2=10분통계, 3=시간통계, 4=일통계, 5=주통계, 6=월통계)
		 */
		// ObjectName[] onArray = {on};
		// String[] attrs = {attr};
		// 2시간 전부터 지금까지의 10분 데이터를 표시한다.
		result.setInterval(type);

		String startTime = STATICS_FORM.format(startDate);
		String endTime = STATICS_FORM.format(endDate);
//		Util.debug(this, "type="+type+", startTime="+startTime+", endTime="+endTime);

		Object[] params = new Object[] {new ObjectName[] {target.getMO().getOName()}, new String[]{ target.getAttribute()}, type,
				startTime, endTime};
		Object returnVal = null;
		try {
			float start = System.currentTimeMillis();
			returnVal = mbsc.invoke(staticsServiceON, "getStatisticsInfo",
					params, pastValueSignature);
			
			if (returnVal != null && returnVal instanceof List) {
				Util.debug(this, "getPastValues:("+((List)returnVal).size()+ ")=" + returnVal+", elapsedTime="+(System.currentTimeMillis() - start)+"msec");
				
				List returnList = (List) returnVal;
				int size = returnList.size();

				double[] values = new double[size];
				String[] xLabels = new String[size];
				
				for (int i = 0; i < size; i++) {
					HashMap element = (HashMap) returnList.get(i);
					
					try {
//						Util.debug(this, "data type("+element.get("avgValue").getClass()+")="+element.get("avgValue")
//								+", date type("+element.get("eventDate").getClass()+")="+element.get("eventDate")
//						);
//						Util.debug(this, "data="+element);
						if(i == 0) { //from
							result.setFromDate(STATICS_FORM.parse(element.get("eventDate").toString()));
						} else if (i == (size - 1 )) { //to
							result.setToDate(STATICS_FORM.parse(element.get("eventDate").toString()));
						}
						values[i] = Double.parseDouble( element.get("avgValue").toString() );
						xLabels[i] = DATE_FORM.format(STATICS_FORM.parse(element.get("eventDate").toString()));
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				result.setValues(values);
				result.setxLabels(xLabels);
			}

		} catch (ReflectionException e) {
			Util.debug(this, "ReflectionException, do not reconnection");

		} catch (java.net.ConnectException e) {
			Util.debug(this, "java.net.ConnectionException, reconnection");
			this.isConnected = false;
		} catch (java.rmi.ConnectException e) {
			Util.debug(this, "java.rmi.ConnectException, reconnection");
			this.isConnected = false;
		} catch (Exception e) {
			
			e.printStackTrace();
			if(e.getMessage().indexOf("The client has been closed") > -1) {
				isConnected = false;
				connection();
			}
		}
		return result;
	}

	public void close() {
		isClosed = true;
	}

	public boolean isConnect() {
		return isConnected;
	}
	
	public boolean isManagerAlive() {

		boolean isAlive = false;
		ObjectName objName = null;
		try {
			objName = new ObjectName(mbsc.getDefaultDomain()
					+ ":name=com.nkia.service.controller,type=service");
		} catch (MalformedObjectNameException e1) {
			Util.debug(this, "MalformedObjectNameException" + e1);
		} catch (NullPointerException e1) {
			Util.debug(this, "NullPointerException" + e1);
		} catch(Exception e){
			Util.debug(this, "%%ERROR%% UnknownException:"+objName+"-"+ e);
			e.printStackTrace();
		}
		try {
			isAlive = (Boolean) mbsc.getAttribute(objName, "Bootup");
			Util.debug(this, "MANAGER BOOTUP?>"+isAlive);
			// this.isConnected = isAlive;
		} catch (IOException ie) {
			ie.printStackTrace();
			this.isConnected = false;
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			this.isConnected = false;
			return false;
		}
		if(isAlive){
			
			isAlive = true;
		}
		return isAlive;
	}

	private synchronized void connection() {
		Util.debug(this, "connection()managerURL="+managerURL+", isConnected="+isConnected);
		if(!isSimulation) {
			if (managerURL != null && !isConnected) {
				Util.debug(this, "managerURL != null && !isConnected");
				try {
					JMXServiceURL url = new JMXServiceURL(managerURL);

					jmxc = JMXConnectorFactory.connect(url, null);
					mbsc = jmxc.getMBeanServerConnection();
					if (mbsc != null && isManagerAlive()) {
						
						domainName = mbsc.getDefaultDomain();
						createBulkRetrievalServiceObjectName();
						createStaticsServiceObjectName();
						createControllerServiceObjectName();
						createFaultHandlerONObjectName();
						Util.debug(this, (new StringBuilder()).append(
								"JMXConnect connect success:").append(
								"[" + managerURL + "]").toString());
						isConnected = true;
					}
						
					
				} catch (Exception e) {
					Util.debug(this, (new StringBuilder()).append("jmxc:")
							.append(jmxc).toString());
					Util.debug(this, (new StringBuilder()).append("mbsc:")
							.append(mbsc).toString());
					Util.debug(this, (new StringBuilder()).append(
							"JMXConnect connect fail:").append(managerURL)
							.toString());
					isConnected = false;
					// e.printStackTrace();
				}

			}
		}
		
		// else
		// Util.debug(this, "managerURL is null");

		Util.debug(this, "end of connection()isConnected="+isConnected+", mbsc:"+mbsc);
	}

	private void createStaticsServiceObjectName() {
		if (staticsServiceON == null) {

			try {
				if (mbsc != null)
					domainName = mbsc.getDefaultDomain();
				if (domainName != null) {
					staticsServiceON = new ObjectName(mbsc.getDefaultDomain()
							+ ":name=com.nkia.service.statistics,type=service");
				}
			} catch (Exception ex) {
				Util.debug(this,
						"[createStaticsServiceObjectName] Exception:"
								+ ex.getMessage());
				ex.printStackTrace();
			}
		}
	}


	private void createControllerServiceObjectName() {

		if (controllerOName == null) {

			try {
				if (mbsc != null)
					domainName = mbsc.getDefaultDomain();
				if (domainName != null) {
					controllerOName = new ObjectName(domainName
							+ ":name=com.nkia.service.controller,type=service");
				}
			} catch (Exception ex) {
				Util.debug(this,
						"[createControllerServiceObjectName()] Exception:"
								+ ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

	
	private void createFaultHandlerONObjectName() {
		if (faultHandlerON == null) {
			try {
				if (mbsc != null)
					domainName = mbsc.getDefaultDomain();

				if (domainName != null) {
					faultHandlerON = new ObjectName(
							domainName
									+ ":type=FaultHandler,name=FaultStatusHandler");

				}

			} catch (Exception ex) {
				Util.debug(this,
						"[createFaultHandlerONObjectName] Exception:"
								+ ex.getMessage());
				ex.printStackTrace();
			}
		}

	}
	
	private void createBulkRetrievalServiceObjectName() {
		if (bulkRetrievalServiceON == null) {
			try {
				if (mbsc != null)
					domainName = mbsc.getDefaultDomain();

				if (domainName != null) {
					bulkRetrievalServiceON = new ObjectName(
							domainName
									+ ":name=com.nkia.service.bulkretrieval,type=service");

				}

			} catch (Exception ex) {
				Util.debug(this,
						"[createBulkRetrievalServiceObjectName] Exception:"
								+ ex.getMessage());
				ex.printStackTrace();
			}
		}

	}

	private void connect() {
		isClosed = false;

		connection();
		
		if(thread == null) {
			Runnable runnable = new Runnable() {
				public void run() {

					while (!isClosed) {

						try {

							if (!isConnected) {
								connection();
							}
								
							Thread.sleep(60000); // 30초에 한번씩 커넥션 체크를 한다.

						} catch (Exception e) {
							Util.debug(this, "connect() fail... flag:"
									+ isConnected);
							e.printStackTrace();
						}
					}
				}
			};
			thread = new Thread(runnable);
			thread.start();
		}
		

	}
	
	public MBeanInfo getMBeanInfo(ObjectName on) {
		MBeanInfo result = null;
		try {
			result = mbsc.getMBeanInfo(on);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Object[] getMbeanByType(String mbeanType) {
		Object[] result;
		ObjectName objNamePattern = null;
		Set targetObjectNameSet = null;
		try {
			objNamePattern = new ObjectName(getDomain()	+ ":type=" + mbeanType + ",*");
			targetObjectNameSet = mbsc.queryNames(objNamePattern, null);
		} catch (MalformedObjectNameException e1) {
			e1.printStackTrace();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
		if (targetObjectNameSet != null) {
			result = targetObjectNameSet.toArray();
		} else {
			result = new Object[0];
		}
		return result;
	}
	
	public Object[] getMbeanByLocation(String location) {
		Object[] result;
		ObjectName objNamePattern = null;
		Set targetObjectNameSet = null;
		try {
			objNamePattern = new ObjectName("*:location=" + location + ",*");
			targetObjectNameSet = mbsc.queryNames(objNamePattern, null);
		} catch (MalformedObjectNameException e1) {
			e1.printStackTrace();
		}catch (IOException e1) {
			e1.printStackTrace();
		}
		if (targetObjectNameSet != null) {
			result = targetObjectNameSet.toArray();
		} else {
			result = new Object[0];
		}
		
		Util.debug(this, "targetObjectNameSet length="+targetObjectNameSet.size());
		return targetObjectNameSet.toArray();
	}

	public String getDomain() {
		return domainName;
	}

	public String getKey() {
		return managerURL;
	}

	public String toString() {
		return getKey();
	}

	public MBeanServerConnection getMBSC() {
		return this.mbsc;
	}

	public JMXConnector getJMXConnector() {
		return this.jmxc;
	}

}
