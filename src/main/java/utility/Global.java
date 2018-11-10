package utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import utility.io.IOUtility;
import window.MainWindow;

/**
 * 
 * @author ZhouHao
 * provide some global variables
 * 2018/10/24
 */
public class Global {
	
	private static Properties configProps = null;
	
	public static String projectName = "SleepClock";
	public static String basePath = null;
	public static String baseDatasetPath = null;
	public static String pathAllPlan = null;
	public static String pathImgs = null;
	
	/* size */
	public static Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
	public static Point screenCenter = new Point(dimScreen.width/2, dimScreen.height/2);
	public final static Dimension dimMainWindow = new Dimension(600, 400);
	public final static Dimension dimAddPlanPanel = new Dimension(330, 150);
	
	
	/* file content delimiter sign */
	public static String delimiterSemicolon = ";";
	public static String delimiterCommon = ",";
	public static String delimiterSpace = " ";
	public static String delimiterPound = "#";
	public static String delimiterCsv = ",";
	public static String delimiterPoint = "\\.";
	
	public static String[] i2week = {"", "周日", "周一",  "周二" , "周三",  "周四",  "周五", "周六"};
	
	public static Color colorPanelBackground = Color.cyan;
	
	public static MainWindow mainWindow = null;
	
	// initBasePath
	public static void initBasePath() throws Exception{
		basePath = getBasePath();
		IOUtility.existsOrThrowsException(basePath);
		
		baseDatasetPath = getBaseDatasetPath();
		IOUtility.existsOrThrowsException(baseDatasetPath);
	}
	
	// getBasePath
	public static String getBasePath() {
		if(null==basePath) {
			basePath = Global.class.getResource("").getPath();
//			System.out.println(basePath);
			char ch = '\\';
			if(basePath.contains("/")) ch = '/';
			
			int which = 0;
			if(basePath.contains("target")) which = -4;
			else which = -3;
			
			basePath = basePath.substring(0, StringTools.indexOf(basePath, ch, which) + 1).replace("/", File.separator);
			if(File.separator.equals("/")) basePath = File.separator + basePath;
			try {
				basePath = java.net.URLDecoder.decode(basePath,"utf-8"); 
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("解析basePath : " + basePath + " 报错，而退出！");
				System.exit(0);
			}
		}
		return basePath;
	}
	
	// getBaseDatasetPath
	public static String getBaseDatasetPath() {
		if(null == baseDatasetPath) {
			baseDatasetPath = Global.getBasePath() + "data" + File.separator;
		}
		return baseDatasetPath;
	}
	
	// setAllPathsForDataset
	public static void setAllPaths() throws Exception{
		pathAllPlan = Global.baseDatasetPath + "all_plans.txt";
		IOUtility.existsOrThrowsException(pathAllPlan);
		pathImgs = Global.baseDatasetPath + "imgs" + File.separator;
		IOUtility.existsOrThrowsException(pathImgs);
	}
	
	public static void display() {
		System.out.println("--------------------------- base path --------------------------");
		System.out.println("projectName : " + Global.projectName);
		System.out.println("basePath : " + Global.basePath);
		System.out.println("baseDatasetPath : " + Global.baseDatasetPath);
		System.out.println("pathAllPlan : " + Global.pathAllPlan);
		System.out.println("pathImgs : " + Global.pathImgs);
	}
	
	static {
		try {
			// set paths
			initBasePath();
			setAllPaths();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void main(String[] args) throws Exception{
		Global.display();
	}
	
}
