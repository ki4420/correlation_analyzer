package kr.ac.hanyang.common;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 이미지 매니저 애플릿과 웹스타트는 아래의 클래스를 상속 받아서 이미지 매니저를 구현해야 한다.
 * 
 * @author 김철
 * 
 */
public class ImageManager {

	private final static String path = "kr/ac/hanyang/webstart/resource/image/";

	private static Map<String, ImageIcon> implementations = null;
	
	private static Object o = new Object();

	/**
	 * init
	 */
	private static synchronized void initImageMapIfNecessary() {
		if (implementations == null) {
			implementations = new HashMap<String, ImageIcon>();

		}
	}

	public static ImageIcon getInstance(String remoteURL) {

		initImageMapIfNecessary();

		ImageIcon result = implementations.get(remoteURL);

		if (result == null) {
			try {
				URL imageURL = new URL(remoteURL);
				result = new ImageIcon(ImageIO.read(imageURL));
				implementations.put(remoteURL, result);
//				Util.debug("ImageManager", "[getInstance] imageIcon=" + remoteURL);
			} catch (Exception e) {
				Util.debug("ImageManager", "[getInstance] error, remoteURL=" + remoteURL);
			}
			
		}
		return result;
	}
	
	public static ImageIcon getImageIcon(String imagePath) {

		initImageMapIfNecessary();
		ImageIcon result = implementations.get(imagePath);
		
		if(result == null) {
			
			try {
				result = new ImageIcon(ClassLoader
				        .getSystemResource(path + imagePath));
				implementations.put(imagePath, result);
//				Util.debug("ImageManager", "[getImageIcon] imageIcon=" + path + imagePath);
			} catch (Exception e) {
				Util.debug("ImageManager", "[getImageIcon] error, imagePath=" + path + imagePath);
				e.printStackTrace();
			}
		}

		return result;
	}
}
