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
 * Ŭ���� ���� �� ����. <pre> Usage : ����� ����. �̹����� ��ǥ�̸��̶� --> �� �̹������� ���ӿ� ���� ��ǥ �̸��� ��Ÿ����. ���� ��� �̹����� �߰��� �� SYS_SERVER.gif��� �̹��������� �߰��ϰ�, image.properties���Ͽ��� NODE_IMAGES = ..|..|.. �� �߰��ϰ�,  SYS_SERVER.01=Description �̶�� �߰��Ѵ�. ���⼭,  'SYS_SERVER'�� �̹����� ��ǥ �̸��� �ȴ�. </pre>
 * @version  <tt>$ Version: 1.0 $</tt>    date:2005. 11. 18
 * @author  <a href="mailto:hschoi@nkia.co.kr"> ������ </a>  <pre></pre>  Ŭ���� ���� ���� �� ����.
 * @version  <tt>$ Reversion: 1.x $</tt>    date:2005. 11. 18
 * @author  <a href="mailto:hschoi@nkia.co.kr"> ������ </a>
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
	
    // image.properties ���Ͽ��� ���� Prefix ��. ��� �̹��� �̸� + ��� �̹��� �̸�.
    public static final String IMAGE_GROUP_NAME = "NODE_IMAGES";    
    public static final String BACKGROUND_IMAGE_PREFIX = "BACKGROUND";
    public static final String JOBSCHEDULER_IMAGE_PREFIX = "JOBSCHEDULER";
    
	public static final int TOOLBAR_UP_002 		= 2;//"����"
	public static final int TOOLBAR_LINK_005 	= 5;//"��ũ"
	public static final int TOOLBAR_LOCATION_SAVE_032 = 32; //��ġ����
	
	public static final int TOOLBAR_THUMBNAIL 		= 157;//�̴ϸʺ���
    
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
     * Ű�� �ε����� ���� Bundle�� �ε��Ǿ� �ִ� ���ڰ����� �����Ѵ�.
     * @param sKey Ű.
     * @param iCount �ε���.
     * @return ���ڿ�.
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
//    		Logger.println("���ڰ��� Ȯ���ϼ���.   args => "+args);
    	}
    	
    	String value = resourceHash.get(key);

    	if(value == null || value.equals("")) return "";
    	value = makeMultiplexText(value, args);
    	
    	return value;
    }

    /**
     * ���ҽ����鿡 ����Ǿ� �ִ� ���ڿ���, ���ڿ� ���̿� �߰��� ���ڵ��� �Է¹޾Ƽ� 
     * ������ �Ͽ� ���ڿ��� ����� ����.
     * @param sText ���ڿ�.
     * @param sArgs ���ڿ� ���̿� �߰� �� ���ڵ�.
     * @return String �����Ͽ� �ϼ��� ���ڿ�.
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
//            	Logger.println("sArgs �� �۽��ϴ�. \n" + ex);
            }
            
            sTempBuffer.insert(iIndex, sTemp);
            sText = sTempBuffer.toString();
            iIndex = iIndex + sTemp.length();
        }
        sMultiText = sTempBuffer.toString();

        return sMultiText;
    }
    
    /**
     * �ε����� ���� ���ڿ��� �����Ѵ�.
     * @param iIndex �ε���.
     * @return ã�� ���ڿ�.
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
     * �ε����� ���ڿ��� �����Ͽ� ���ڿ��� �����Ͽ� �����Ѵ�.
     * @param iIndex �ε���.
     * @param sArgs �Է��� ���ڿ���.
     * @return ������ ���ڿ�.
     */
/*    public static String getResourceText(int iIndex, String[] sArgs) {
    	String sText ="";
    	if (sArgs ==null || sArgs.length == 0) {
    		Logger.println("���ڰ��� Ȯ���ϼ���.   sArgs => "+sArgs);
    	}
    	sText = makeMultiplexText(resource_text[iIndex], sArgs);
    	return sText;
    }*/
   
    public static String getResourceText(int index, String[] args) {
    	return getResourceValue("MAP_"+index, args);
    }
    
    /**
     * �ε����� ���� �޼����� �����Ѵ�.
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
     * �ε����� ���ڿ��� �����Ͽ� �޼����� �����Ѵٿ� �����Ѵ�.
     * @param iIndex �ε���.
     * @param sArgs �Է��� ���ڿ���.
     * @return ������ �޼���.
     */
/*    public static String getMessageText(int iIndex, String[] sArgs) {
    	String sText ="";
    	if (sArgs ==null || sArgs.length == 0) {
    		Logger.println("���ڰ��� Ȯ���ϼ���.   sArgs => "+sArgs);
    	}
    	sText = makeMultiplexText(message_text[iIndex], sArgs);
    	return sText;
    }*/
    public static String getMessageText(int index, String[] args) {
    	return getResourceValue("MESSAGE_"+index, args);
    }
    
    /**
     * ���鿡 ����� ������ �ؽ��� �ٽ� �����Ѵ�.
     * @param rBundle ResourceBundle.
     * @param hash ������ ������ Hashtable.
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
     * �̹������� ��ǥ ���� �̾Ƴ���. ��ǥ ������ ���� �̸����̴�. 
     * ��ǥ ������ �� index�� ���� ������ Hash�� �����ϱ� ���� �̿�ȴ�.
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
     * '����.����'�� �������� �Ǿ� �ִ� ���ڸ� '����.����'�������� �����Ѵ�. 
     * @param str �Է��� ����. '����.����'
     * @return String ��ȯ�� ����. '����.����'
     */
    public static String integerValue(String str) {
    	int index = str.indexOf(".");   	
    	if (index == -1) {
//    		Logger.println("������ Ű�� ���Ŀ� ���� �ʴ� ������ �����մϴ�. ������ '�����̸�.�ε���'.");
//    		Logger.println("Current Image Name => " + str);
    		return "";
    	}    	
    	return str.substring(0, index) + "." + String.valueOf(Integer.parseInt(str.substring(index+1, str.length())));
    }
    
    /**
	 * ��� �̹������� ����Ǿ� �ִ� Hashtable�� �����Ѵ�.
	 * @return  ��� �̹��� Hashtablel.
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
	 * ��� �̹������� ����Ǿ� �ִ� Hashtable�� �����Ѵ�.
	 * @return  ��� �̹��� Hashtable.
	 * @uml.property  name="backgroundImageHash"
	 */
    public static Hashtable getBackgroundImageHash() {
    	return backgroundImageHash;    	
    }
    
    /**
     * ���ҽ����鿡�� Ű�� ���� ã�Ƽ� �����Ѵ�.
     * @param rBundle ResourceBundle.
     * @param sKey Ű.
     * @return String ã�� ����.
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
     * UTF-8�� ���ڵ��Ѵ�.
     * @param sText ���ڵ��� ����.
     * @return String ���ڵ��� ����.
     */
    public static String encodingToUTF8(String sText) {
    	String sUniText = "";
    	try {
    		sUniText = new String(sText.getBytes("8859_1"), "UTF8");
    	} catch (Exception ex) {
//    		Logger.println("unitext ������ exception �߻� \n" + ex);    		
    	}
    	return sUniText;
    }
    
    /**
     * �̹����� ��ǥ �̸��� �����ϰ� Hash�� ������ �� �ֵ��� ȣ���Ѵ�.
     * @param rBundle ResourceBundle
     * @param value NODE_IMAGES�� ���� ��.
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
     * �̹����� ��ǥ �̸��� ���ԵǴ� ������ �ؽ��� �����Ѵ�. ��) SYS_SERVER.01, SYS_SERVER.02 ==> SYS_SERVER 
     * @param rBundle ResourceBundle.
     * @param name �̹����� ��ǥ �̸�. 
     * @return HashMap ���� ��ǥ �̸��� ���� ������ ����� HashMap. 
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
     * ��ǥ�̸����� HahsMap�� ũ�⸦ ���Ѵ�.
     * @param name �̹����� ��ǥ �̸�.
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
     * �̹����� �迭�� �����Ѵ�.
     * @return
     */
/*    public static ArrayList getImageArray() {
    	return imageArray;
    }*/
    
    /**
     * �̹��� ���� �̸�
     * @return
     */
    public static Vector getImageNames() {    	
    	return vImageNames;
    }
    
    /**
     * key�� ������ xxx.01 �� �����̹Ƿ� .���� �߶󳻾� �����Ѵ�.
     * key�� '.'�� ���� ���� ���� ��쿡�� key�� �ٷ� �����Ѵ�.
     */
    private static String splitKeys(String key) {
    	int dot = key.indexOf(".");
    	if (dot == -1) {
    		return key;
    	}
    	
    	return key.substring(0, dot);
    }    
    
    /**
	 * �˾� Hashtable�� �����Ѵ�.
	 * @return  �˾� Hashtable.
	 * @uml.property  name="popupHash"
	 */
    public static Hashtable getPopupHash() {
        return popupHash;
    }
    
    public static String getOptionText(String key) {
    	return getResourceText(optionBundle, key);    	
    }

}
