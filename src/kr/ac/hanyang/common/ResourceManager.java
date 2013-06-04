/*
 * @(#)ResourceManager.java	$ 1.0   $ date: 2005. 11. 18
 *
 * Copyright 2005 Nkia.com, Inc. All rights reserved.
 * NKIA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kr.ac.hanyang.common;

import java.awt.Font;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.text.StyleConstants.FontConstants;

/**
 * 클래스 정의 및 설명. <pre> Usage : 사용방법 예시. 이미지의 대표이름이란 --> 각 이미지들의 모임에 대한 대표 이름을 나타낸다. 예를 들어 이미지를 추가할 때 SYS_SERVER.gif라는 이미지파일을 추가하고, image.properties파일에서 NODE_IMAGES = ..|..|.. 에 추가하고,  SYS_SERVER.01=Description 이라고 추가한다. 여기서,  'SYS_SERVER'가 이미지의 대표 이름이 된다. </pre>
 * @version  <tt>$ Version: 1.0 $</tt>    date:2005. 11. 18
 * @author  <a href="mailto:hschoi@nkia.co.kr"> 최형석 </a>  <pre></pre>  클래스 수정 내용 및 설명.
 * @version  <tt>$ Reversion: 1.x $</tt>    date:2005. 11. 18
 * @author  <a href="mailto:hschoi@nkia.co.kr"> 최형석 </a>
 */
public class ResourceManager {
	/**
	 * @uml.property  name="manager"
	 * @uml.associationEnd  
	 */
	private static ResourceManager manager = new ResourceManager();
    private static ResourceBundle bundle;
    private static Hashtable<String, String> resourceHash = new Hashtable<String, String>();
    
    private static ResourceBundle popupBundle;
    private static Hashtable popupHash = new Hashtable();

    private static ResourceBundle imageBundle;    
    private static Hashtable imageHash = new Hashtable();
    
    private static Hashtable<String, String> jobschedulerImageHash = new Hashtable<String, String>();
    
    private static Hashtable backgroundImageHash = new Hashtable();
    
//    private static String[] resource_text;
//    private static String[] message_text;
    
    private static ResourceBundle optionBundle;
    
//    private static ArrayList imageArray = new ArrayList();
    private static Vector vImageNames = new Vector();
    
    private static Locale locale = new Locale("");
    
    public ResourceManager() {

    }
    
//	public static String FILE_RESOURCE = 
//		"com/nkia/station/applet/map/resource/files/" + NetConstants.LOCALE + "/International_Resource";
	public static String FILE_RESOURCE = 
		"com/nkia/station/applet/map/resource/files/resource";
//    public static final String FILE_MAIN_POPUP = 
//		"com/nkia/station/applet/map/resource/files/" + NetConstants.LOCALE + "/popup";
    public static final String FILE_MAIN_POPUP = 
		"com/nkia/station/applet/map/resource/files/popup";
//	public static final String FILE_IMAGE_LINK = 
//		"com/nkia/station/applet/map/resource/files/image";
	public static final String FILE_IMAGE_LINK = 
		"station/images/applet/bizmap/files/image";
    public static final String FILE_MAIN_OPTION = 
    	"com/nkia/station/applet/map/resource/files/option";
	
    // image.properties 파일에서 쓰일 Prefix 들. 노드 이미지 이름 + 배경 이미지 이름.
    public static final String IMAGE_GROUP_NAME = "NODE_IMAGES";    
    public static final String BACKGROUND_IMAGE_PREFIX = "BACKGROUND";
    public static final String JOBSCHEDULER_IMAGE_PREFIX = "JOBSCHEDULER";
    
	public static final int TOOLBAR_UP_002 		= 2;//"위로"
	public static final int TOOLBAR_LINK_005 	= 5;//"링크"
	public static final int TOOLBAR_LOCATION_SAVE_032 = 32; //위치저장
	
	public static final int TOOLBAR_THUMBNAIL 		= 157;//미니맵보기
    
    static {
        try {
        	try {
//        		locale = new Locale(NetConstants.LOCALE);
//        		bundle = ResourceBundle.getBundle(FILE_RESOURCE,
//        				locale, manager.getClass().getClassLoader());
        		
        		
        		bundle = ResourceBundle.getBundle(FILE_RESOURCE, Util.getLocaleInfo("ko"),manager.getClass().getClassLoader());

        		
        		
        		
        		
        		
        		
        	} catch (Exception ex) {
        		ex.printStackTrace();
        		bundle = ResourceBundle.getBundle(FILE_RESOURCE,
        				locale, manager.getClass().getClassLoader());
        	}
        	makeHash(bundle, resourceHash);
        	
//            resource_text = loadOmniResource("MAP_", 121);
            
//            message_text = loadOmniResource("MESSAGE_", 57);
        	
//        	 imageBundle = ResourceBundle.getBundle(FILE_IMAGE_LINK, 
//             		locale, manager.getClass().getClassLoader());
//             makeImage(imageBundle);
             
            popupBundle = ResourceBundle.getBundle(FILE_MAIN_POPUP,
            		locale, manager.getClass().getClassLoader());
            makeHash(popupBundle, popupHash);
            
           
           
            optionBundle = ResourceBundle.getBundle(FILE_MAIN_OPTION,
                    Locale.getDefault(), manager.getClass().getClassLoader());
            
//            URL[] urlArray = {new URL(NetConstants.STATION_BASE_URL+"/")}; 
//            URLClassLoader urlcLoader = new URLClassLoader(urlArray); 
//            
//            imageBundle = ResourceBundle.getBundle(FILE_IMAGE_LINK, 
//            		locale, urlcLoader);
//            makeImage(imageBundle);
//
//            if (Integer.parseInt(getOptionText("log")) == 1) {
//            	Logger.LOGGER = true;
//            } else {
//            	Logger.LOGGER = false;
//            }
//
//            FontConstants.DEFATUL_NODE_NAME_FONT_NAME = getOptionText("nodeFontName");
//            FontConstants.DEFATUL_NODE_NAME_FONT_BOLD = Integer.parseInt(getOptionText("nodeFontBold"));
//            FontConstants.DEFATUL_NODE_NAME_FONT_SIZE = Integer.parseInt(getOptionText("nodeFontSize"));
//            
//            FontConstants.DEFATUL_NODE_NAME_FONT = new Font(
//            		FontConstants.DEFATUL_NODE_NAME_FONT_NAME, FontConstants.DEFATUL_NODE_NAME_FONT_BOLD, FontConstants.DEFATUL_NODE_NAME_FONT_SIZE);
//
//            MapConstants.AUTOSORTING_GAP = Integer.parseInt(getOptionText("lineupGap"));

        } catch(MissingResourceException ex) {
        	System.err.println("No Resource ... \n" + ex);
        } catch(Exception ex) {
        	System.err.println("No Resource ... \n" + ex);
        }
    }
    
    /**
     * 키와 인덱스에 따라서 Bundle에 로딩되어 있는 문자값들을 리턴한다.
     * @param sKey 키.
     * @param iCount 인덱스.
     * @return 문자열.
     */
/*    public static String[] loadOmniResource(String sKey, int iCount) {
        String[] sOmniResource = new String[iCount];
        String sTemp = "";
        for (int i=0 ; i<iCount; i++) {
            sTemp = getResourceText(bundle,sKey+i);
            sOmniResource[i] = sTemp;
        }
        return sOmniResource;
    }*/
    
    public static String getResourceValue(String key){
    	if(key == null) return "";
    	
    	return resourceHash.get(key);
    }
    
    public static String getResourceValue(String key, String[] args){
    	if (args == null || args.length == 0) {
//    		Logger.println("인자값을 확인하세요.   args => "+args);
    	}
    	
    	String value = resourceHash.get(key);

    	if(value == null || value.equals("")) return "";
    	value = makeMultiplexText(value, args);
    	
    	return value;
    }

    /**
     * 리소스번들에 저장되어 있는 문자열과, 문자열 사이에 추가될 문자들을 입력받아서 
     * 조합을 하여 문자열을 만들어 낸다.
     * @param sText 문자열.
     * @param sArgs 문자열 사이에 추가 될 문자들.
     * @return String 조합하여 완성된 문자열.
     */
    public static String makeMultiplexText(String sText, String[] sArgs) {
        String sMultiText = "";
        StringBuffer sTempBuffer = new StringBuffer(sText);
        String sTemp = "";
        String sIndexString;
        int iIndex = 0;

        while (true) {
            iIndex = sText.indexOf('%', iIndex);
            if (iIndex == -1) {
                break;
            }
            char nextToken = sText.charAt(iIndex+1);
            if (nextToken != '%') {
                break;
            }
            sIndexString = sTempBuffer.substring(iIndex + 2, iIndex + 4);
            sTempBuffer.delete(iIndex, iIndex + 4);
            try {
                sTemp = sArgs[Integer.parseInt(sIndexString)];
            } catch(ArrayIndexOutOfBoundsException ex) {
//            	Logger.println("sArgs 가 작습니다. \n" + ex);
            }
            
            sTempBuffer.insert(iIndex, sTemp);
            sText = sTempBuffer.toString();
            iIndex = iIndex + sTemp.length();
        }
        sMultiText = sTempBuffer.toString();

        return sMultiText;
    }
    
    /**
     * 인덱스에 따른 문자열을 리턴한다.
     * @param iIndex 인덱스.
     * @return 찾은 문자열.
     */
/*    public static String getResourceText(int iIndex) {
    	String sText ="";	
    	sText = resource_text[iIndex];
    	return sText;    	
    }*/
    
    public static String getResourceText(int index) {
    	return getResourceValue("MAP_"+index);  	
    }
    
    /**
     * 인덱스와 문자열을 조합하여 문자열을 생성하여 리턴한다.
     * @param iIndex 인덱스.
     * @param sArgs 입력할 문자열들.
     * @return 생성된 문자열.
     */
/*    public static String getResourceText(int iIndex, String[] sArgs) {
    	String sText ="";
    	if (sArgs ==null || sArgs.length == 0) {
    		Logger.println("인자값을 확인하세요.   sArgs => "+sArgs);
    	}
    	sText = makeMultiplexText(resource_text[iIndex], sArgs);
    	return sText;
    }*/
   
    public static String getResourceText(int index, String[] args) {
    	return getResourceValue("MAP_"+index, args);
    }
    
    /**
     * 인덱스에 따라서 메세지를 리턴한다.
     * @param iIndex
     * @return
     */
/*    public static String getMessageText(int iIndex) {
    	String sText ="";	
    	sText = message_text[iIndex];
    	return sText;    	
    }*/
    public static String getMessageText(int index) {
    	return getResourceValue("MESSAGE_"+index);    	
    }
    
    /**
     * 인덱스와 문자열을 조합하여 메세지를 생성한다여 리턴한다.
     * @param iIndex 인덱스.
     * @param sArgs 입력할 문자열들.
     * @return 생성된 메세지.
     */
/*    public static String getMessageText(int iIndex, String[] sArgs) {
    	String sText ="";
    	if (sArgs ==null || sArgs.length == 0) {
    		Logger.println("인자값을 확인하세요.   sArgs => "+sArgs);
    	}
    	sText = makeMultiplexText(message_text[iIndex], sArgs);
    	return sText;
    }*/
    public static String getMessageText(int index, String[] args) {
    	return getResourceValue("MESSAGE_"+index, args);
    }
    
    /**
     * 번들에 저장된 값들을 해쉬에 다시 저장한다.
     * @param rBundle ResourceBundle.
     * @param hash 값들을 저장할 Hashtable.
     */
    public static void makeHash(ResourceBundle rBundle, Hashtable hash) {
        Enumeration em = rBundle.getKeys();
        String sKey = "";
        String sValue = "";
        while (em.hasMoreElements()) {
            sKey = em.nextElement().toString();
           	sValue = getResourceText(rBundle,sKey);
           	hash.put(sKey, sValue);
        }
    }
    
    /**
     * 이미지에서 대표 값을 뽑아낸다. 대표 값들은 파일 이름들이다. 
     * 대표 값들은 각 index를 가진 값들을 Hash에 저장하기 위해 이용된다.
     * @param rBundle ResourceBundle.
     */
    private static void makeImage(ResourceBundle rBundle){
        Enumeration em = rBundle.getKeys();
        String sKey = "";
        String sValue = "";
        while (em.hasMoreElements()) {
            sKey = em.nextElement().toString();
            sValue = getResourceText(rBundle,sKey);
            if (sKey.indexOf("#", 0) != -1) {
            	continue;
            }
            if (sKey.equalsIgnoreCase(IMAGE_GROUP_NAME)) {            	
            	makeImageHash(rBundle, sValue);
            	//return;
            } else {
            	//sValue = encodingToUTF8(sValue);
//            	if (CommonUtil.getStrIncludedBackground(sKey)) {            		
//            		backgroundImageHash.put(CommonUtil.getStrExcludeBackground(sKey), sValue);
//            	}else if(sKey.indexOf(JOBSCHEDULER_IMAGE_PREFIX) != -1){
//            		jobschedulerImageHash.put(sKey.substring(JOBSCHEDULER_IMAGE_PREFIX.length()+1, sKey.length()), sValue);
//            	} else {            		
//            		imageHash.put(sKey, sValue);
//            	}
            }
        }
    }
        
    /**
     * '문자.문자'의 형식으로 되어 있는 문자를 '문자.숫자'형식으로 변경한다. 
     * @param str 입력할 문자. '문자.문자'
     * @return String 변환된 문자. '문자.숫자'
     */
    public static String integerValue(String str) {
    	int index = str.indexOf(".");   	
    	if (index == -1) {
//    		Logger.println("파일의 키에 형식에 맞지 않는 라인이 존재합니다. 형식은 '파일이름.인덱스'.");
//    		Logger.println("Current Image Name => " + str);
    		return "";
    	}    	
    	return str.substring(0, index) + "." + String.valueOf(Integer.parseInt(str.substring(index+1, str.length())));
    }
    
    /**
	 * 노드 이미지들이 저장되어 있는 Hashtable을 리턴한다.
	 * @return  노드 이미지 Hashtablel.
	 * @uml.property  name="imageHash"
	 */
    public static Hashtable getImageHash() {
    	return imageHash;
    }

    /**
	 * @return
	 * @uml.property  name="jobschedulerImageHash"
	 */
    public static Hashtable<String, String> getJobschedulerImageHash(){
    	return jobschedulerImageHash;
    }
    
    /**
	 * 배경 이미지들이 저장되어 있는 Hashtable을 리턴한다.
	 * @return  배경 이미지 Hashtable.
	 * @uml.property  name="backgroundImageHash"
	 */
    public static Hashtable getBackgroundImageHash() {
    	return backgroundImageHash;    	
    }
    
    /**
     * 리소스번들에서 키로 값을 찾아서 리턴한다.
     * @param rBundle ResourceBundle.
     * @param sKey 키.
     * @return String 찾은 문자.
     */
    public static String getResourceText (ResourceBundle rBundle,String sKey) {
        String sText = "";
        try {
            sText = rBundle.getString(sKey);
        } catch(MissingResourceException ex){
//        	Logger.println("No Key => " + sKey);			
        }
        
        return encodingToUTF8(sText);
    }
    
    /**
     * UTF-8로 인코딩한다.
     * @param sText 인코딩할 문자.
     * @return String 인코딩된 문자.
     */
    public static String encodingToUTF8(String sText) {
    	String sUniText = "";
    	try {
    		sUniText = new String(sText.getBytes("8859_1"), "UTF8");
    	} catch (Exception ex) {
//    		Logger.println("unitext 변경중 exception 발생 \n" + ex);    		
    	}
    	return sUniText;
    }
    
    /**
     * 이미지의 대표 이름을 저장하고 Hash에 저장할 수 있도록 호출한다.
     * @param rBundle ResourceBundle
     * @param value NODE_IMAGES에 대한 값.
     */
    private static void makeImageHash (ResourceBundle rBundle, String value) { 
    	vImageNames.clear();
    	
        StringTokenizer stk = new StringTokenizer(value, "|");
        while (stk.hasMoreTokens()) {
            String imageName = stk.nextToken();
            vImageNames.addElement(imageName);
//            imageArray.add(makeImageHashtable(rBundle, imageName));
        }
    }
    
    /**
     * 이미지의 대표 이름에 포함되는 값들을 해쉬에 저장한다. 예) SYS_SERVER.01, SYS_SERVER.02 ==> SYS_SERVER 
     * @param rBundle ResourceBundle.
     * @param name 이미지의 대표 이름. 
     * @return HashMap 같은 대표 이름을 가진 값들이 저장된 HashMap. 
     */
    private static HashMap makeImageHashtable(ResourceBundle rBundle, String name) {
    	HashMap hash = new HashMap();
    	Enumeration em = rBundle.getKeys();
    	String sKey = "";
    	String sValue = "";
    	
    	while (em.hasMoreElements()) {
    		sKey = em.nextElement().toString();
    		if (name.equals(splitKeys(sKey))) {
    			sValue = getResourceText(imageBundle,sKey);
    			hash.put(sKey, sValue);
    		}    		
    	}
    	return hash;
    }    
   
    /**
     * 대표이름으로 HahsMap의 크기를 구한다.
     * @param name 이미지의 대표 이름.
     * @return
     */
/*    public static int getImageHashSize(String name) {
    	int size = 0;
    	for (int i = 0 ; i < imageArray.size(); i++) {
    		HashMap hashMap = (HashMap)imageArray.get(i);
    		Set keys = (Set)hashMap.keySet();
    		Iterator iter = keys.iterator();
    		while (iter.hasNext()) {
		    	String name1 = (String)iter.next();
		    	if (splitKeys(name1).equals(name)) {
		    		size = hashMap.size();
		    	}
		    	break;
    		}
    	}
    	return size;
    }*/
    
    /**
     * 이미지의 배열을 리턴한다.
     * @return
     */
/*    public static ArrayList getImageArray() {
    	return imageArray;
    }*/
    
    /**
     * 이미지 파일 이름
     * @return
     */
    public static Vector getImageNames() {    	
    	return vImageNames;
    }
    
    /**
     * key의 형식이 xxx.01 의 형식이므로 .앞을 잘라내어 리턴한다.
     * key에 '.'이 포함 되지 않은 경우에는 key를 바로 리턴한다.
     */
    private static String splitKeys(String key) {
    	int dot = key.indexOf(".");
    	if (dot == -1) {
    		return key;
    	}
    	
    	return key.substring(0, dot);
    }    
    
    /**
	 * 팝업 Hashtable을 리턴한다.
	 * @return  팝업 Hashtable.
	 * @uml.property  name="popupHash"
	 */
    public static Hashtable getPopupHash() {
        return popupHash;
    }
    
    public static String getOptionText(String key) {
    	return getResourceText(optionBundle, key);    	
    }

}
