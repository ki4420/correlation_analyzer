package kr.ac.hanyang.collector;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import kr.ac.hanyang.collector.jmx.JMXChannel;
import kr.ac.hanyang.common.Util;

/**
 * 매니저와 커넥션을 관리 한다. 멀티매니저 환경에서의 중복 커넥션을 방지하기 위해 커넥션 풀을 사용 한다. 1. 커넥션 풀 관리 2. 커넥션
 * 해제 3. 신규 커넥션 생성
 * 
 * @author 김철
 * 
 */

public class ChannelManager {

	
		
	private static Hashtable<String, IChannel> implementations = null;

	public ChannelManager() {

	}

	/**
	 * init
	 */
	private static synchronized void initConnectionManagerIfNecessary() {
		if (implementations == null) {
			implementations = new Hashtable<String, IChannel>();
		}
	}
	
	public static IChannel getChannel(String url) {
		IChannel result = null;
		if(url != null && url.indexOf("jndi") > -1) {
			result = getJMXChannel(url);
		}
		return result;
		
	}
	

	/**
	 * 폴스터 JMX 커넥션
	 * @param key
	 * @return
	 */
	public static IChannel getJMXChannel(String managerURL) {
		long start = System.currentTimeMillis();
		initConnectionManagerIfNecessary();
		IChannel result = null;

		result = implementations.get(managerURL);
		if (result == null) {
			Util.debug(
					"com.nkia.station.applet.performance_monitoring.connect.ChannelManager",
					"getJMXChannel is null, newChannel create.. , implementations:"
							+ implementations);
			JMXChannel newChannel = new JMXChannel(managerURL);
			
			result = newChannel;
			newChannel.initConnection();
			implementations.put(managerURL, newChannel);
		}
//		Util.debug(
//				"com.nkia.station.applet.performance_monitoring.connect.ChannelManager",
//				"[getJMXChannel]elapsed time:"+((System.currentTimeMillis() - start)/1000.0)+"sec, result"+result);
		return result;
	}
		
	
	public static void removeAllChannel() {
		if(implementations != null) {
			Set keys = implementations.keySet();
			Iterator iterator = keys.iterator();
			while(iterator.hasNext()) {
				Object key = iterator.next();
				IChannel channel = implementations.get(key);
				channel.close();
			}
			implementations.clear();

		}
		
//		Util.debug("ChannelManager", "removeAllChannel:"+implementations);
	}

	public static void removeChannel(String key) {
		IChannel channel = implementations.remove(key);
		
		if (channel != null) {
			Util.debug(
					"ChannelManager",
					"channel remove success:" + key);
			channel.close();
		} else {
			Util.debug(
					"ChannelManager",
					"channel remove fail:" + key);
		}
	}
	
	public static String getContents() {
		return implementations.toString();
	}

}
