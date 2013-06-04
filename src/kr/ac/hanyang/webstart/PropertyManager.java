package kr.ac.hanyang.webstart;

import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class PropertyManager {

	public PropertyManager() {
		
	}

	/**
	 * @uml.property  name="cONFIG_FILE_NAME"
	 */
	private final String CONFIG_FILE_NAME = "mibbrowser_config.xml";

	/**
	 * @uml.property  name="fILE_PATH"
	 */
	private final String FILE_PATH = System.getProperty("user.home") + File.separator + CONFIG_FILE_NAME;

	public void loadXMLFile() {
		Runnable doRun = new Runnable(){
			public void run() {
				File f = new File(FILE_PATH);
			      if(f.exists()){
			    	  try{
				          java.beans.XMLDecoder d = new java.beans.XMLDecoder(
				                          new BufferedInputStream(
				                              new FileInputStream(f.getAbsolutePath())));
				          Object result = d.readObject();
				          if(result instanceof Object[]) {
				        	  Object[] properties = (Object[])result;
//				        	  ToolBar.setIPList((ArrayList<String>)properties[0]);
//				        	  ToolBar.setOIDList((ArrayList<String>)properties[1]);
				        	  
				          } else System.out.println("loadXML is not Object[], result="+result);
//				          if(result instanceof Hashtable) loadConfig((Hashtable)result);
				          d.close();
				        } catch (FileNotFoundException fnfe) {
				          fnfe.printStackTrace();
				        }
			      } else {
			    	  System.out.println(FILE_PATH + " is not found.");
			      }
			}
		};
		
		Thread thread = new Thread(doRun);
		thread.start();
	      
	      
	}
	
	private void saveXML(File file, Object comp) {
		
		try {
			XMLEncoder e = new XMLEncoder(new BufferedOutputStream( new FileOutputStream(file.getAbsoluteFile()) ));
			e.writeObject( /* (sClass) */comp );
			e.close();

		} catch (FileNotFoundException fnfe) {
		      fnfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void saveXMLFile() {
		
		File file = new File(FILE_PATH);
	    if (!file.exists()) {
	    	// 파일이 존재하지 않습니다.
		    createFile(FILE_PATH,"");
	    }
        saveXML(file, creatConfigHash());
	}
	
	private Object creatConfigHash() {
//		Object result = null;
		Object[] result = new Object[2];
//		result[0] = ToolBar.getIPList();
//		result[1] = ToolBar.getOIDList();
		return (Object)result;
		
	}
	

	 /**
		 * 파일을 생성한다.
		 * 
		 * @param fileName
		 *            absolute file path
		 * @param fileData
		 *            data
		 */
    private void createFile(String fileName, String fileData) {
        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileData);
            fileWriter.close();
            fileWriter = null;
            file = null;
            System.gc();
        }
        catch (IOException io) {
            io.printStackTrace();
        }
    }

	
}
