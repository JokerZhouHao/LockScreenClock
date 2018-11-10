package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.FontUIResource;

import entity.Plan;
import model.AllPlanModel;
import utility.Global;
import view.MButton;
import view.OnePlanPanel;
import view.ShowAllPlanPanel;

public class MainWindow {
	private static JFrame frame = null;
	private Font globalFont = new Font("楷体", Font.PLAIN, 17);
	private Font globalTFFont = new Font("微软雅黑", Font.PLAIN, 15);
	
	private TrayIcon trayIcon = null;
	private static Image icon = null;
	
	private AllPlanModel allPlanModel = null;
	private JScrollPane centerSPane = null;
	private ShowAllPlanPanel centerPanel = null;
	
	public MainWindow() throws Exception{
		initGobalFont(globalFont);
		
		allPlanModel = new AllPlanModel(Global.pathAllPlan);
		centerPanel = new ShowAllPlanPanel(allPlanModel);
		
		this.frame = new JFrame("锁屏闹钟");
		this.frame.setLocation(Global.dimScreen.width/2 - Global.dimMainWindow.width/2, Global.dimScreen.height/2 - Global.dimMainWindow.height/2);
		this.frame.setSize(Global.dimMainWindow);
		icon = Toolkit.getDefaultToolkit().getImage(Global.pathImgs + "icon5.png");
		this.frame.setIconImage(icon);
		this.frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		trayIcon = new TrayIcon(icon);
		trayIcon.setImageAutoSize(Boolean.TRUE);
		trayIcon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(true);
			}
		});
		try {
			SystemTray.getSystemTray().add(trayIcon);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		BorderLayout bLayout = new BorderLayout();
		this.frame.setLayout(bLayout);
		
		centerSPane = new JScrollPane(centerPanel);
		centerSPane.getVerticalScrollBar().setUnitIncrement(7);
		
		this.frame.add(centerSPane, BorderLayout.CENTER);
		
		
		JButton addPlanBtn = new JButton("+");
		addPlanBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddPlanWindow(frame, "添加计划", allPlanModel).setVisible(true);
//				new JDialog(frame).setVisible(true);
			}
		});
		this.frame.add(addPlanBtn, BorderLayout.SOUTH);
		
	}
	
	
	/**
	 * 设置全局字体
	 * @param font
	 */
	public void initGobalFont(Font font) {  
	    FontUIResource fontResource = new FontUIResource(font);  
	    for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {  
	        Object key = keys.nextElement();  
	        Object value = UIManager.get(key);  
	        if(value instanceof FontUIResource) {  
//	            System.out.println(key);  
	            UIManager.put(key, fontResource);  
	        }  
	    }  
	} 
	
	public static JFrame getMainWindow() {
		return frame;
	}
	
	/**
	 * show
	 */
	public void show() {
		this.frame.setVisible(true);
	}
	
	public static Image getIcon() {
		return icon;
	}

	public void startClock() throws Exception{
		Calendar cld = null;
		int i = 1;
		String curWeek = null;
		int curMin = 0;
		int sleepFreq = 60000;
		
		while(true) {
			cld = Calendar.getInstance();
			
			curWeek = Global.i2week[cld.get(Calendar.DAY_OF_WEEK)];
			curMin = cld.get(Calendar.HOUR_OF_DAY) * 60 + cld.get(Calendar.MINUTE);
			
			if(allPlanModel.inLockTime(curWeek, curMin)) {
				sleepFreq = 1000;
				Runtime.getRuntime().exec("RunDll32.exe user32.dll,LockWorkStation");
			} else sleepFreq = 60000;
			Thread.sleep(sleepFreq);
		}
	}
	
	public JScrollPane getCenterSPane() {
		return centerSPane;
	}

	public static void main(String[] args) throws Exception{
		MainWindow mw = new MainWindow();
		Global.mainWindow = mw;
		mw.show();
		mw.startClock();
		
	}
}
