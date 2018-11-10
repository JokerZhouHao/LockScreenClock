package window;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import entity.Plan;
import utility.Global;
import view.MButton;

public class AddPlanWindow extends JDialog {
	
	private MButton addBtn = null;
	private JTextField tfHour = null;
	private JTextField tfMinute = null;
	private JTextField tfLockSpan = null;
	
	private List<JCheckBox> weekCBs = null;
	
	public AddPlanWindow(JFrame parent, String title, PropertyChangeListener pcl) {
		super(parent, title);
		this.init(pcl);
	}
	
	private void init(PropertyChangeListener pcl) {
		this.setIconImage(MainWindow.getIcon());
		this.setLocation(Global.dimScreen.width/2 - Global.dimAddPlanPanel.width/2, Global.dimScreen.height/2 - Global.dimAddPlanPanel.height/2);
		this.setSize(Global.dimAddPlanPanel);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setModal(true);
		
		this.setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = gbc.WEST;
		gbc.insets = new Insets(1, 1, 1, 1);
		
		gbc.weightx = 0;
		topPanel.add(new JLabel("起始时间"), gbc);
		
		gbc.gridx++;
		gbc.weightx = 0;
		this.tfHour = new JTextField(3);
		topPanel.add(this.tfHour, gbc);
		
		gbc.gridx++;
		topPanel.add(new JLabel(":"), gbc);
		
		gbc.gridx++;
		this.tfMinute = new JTextField(3);
		topPanel.add(this.tfMinute, gbc);
		
		gbc.gridx++;
		gbc.weightx = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
		topPanel.add(new JLabel(" 锁屏时长"), gbc);
		
		gbc.gridx ++;
		gbc.weightx = 0.6;
		this.tfLockSpan = new JTextField(3);
		this.tfLockSpan.setText("10");
		topPanel.add(this.tfLockSpan, gbc);
		
		gbc.gridx = GridBagConstraints.LAST_LINE_END;
		gbc.weightx = 0;
		topPanel.add(new JLabel("分 "), gbc);
		
		
		JPanel centerPane = new JPanel();
		centerPane.setLayout(new GridBagLayout());
		centerPane.setBackground(Global.colorPanelBackground);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = gbc.CENTER;
		gbc.insets = new Insets(1, 1, 1, 1);
		weekCBs = new ArrayList<>();
		String[][] weeks = {
				{"周一", "周二", "周三", "周四"},
				{"周五", "周六", "周日", "每天"}
		};
		int i, j=0;
		for(i=0; i<2; i++) {
			for(j=0; j<4; j++) {
				gbc.gridy = i;
				gbc.gridx = j;
				JCheckBox cb = new JCheckBox(weeks[i][j]);
				weekCBs.add(cb);
				centerPane.add(cb, gbc);
			}
		}
		
		addBtn = new MButton("添加计划", pcl);
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println(tfHour.getText() + " " + tfMinute.getText() + " " + tfLockSpan.getText());
				if(tfHour.getText().equals("") || tfMinute.getText().equals("") || tfLockSpan.getText().equals("")) {
					JOptionPane.showMessageDialog(MainWindow.getMainWindow(), "请先输入数据");
					return;
				}
				int totalMin = Integer.parseInt(tfHour.getText())*60 + Integer.parseInt(tfMinute.getText());
				int spanMin = Integer.parseInt(tfLockSpan.getText());
				List<String> wks = new ArrayList<>();
				if(weekCBs.get(weekCBs.size()-1).isSelected()) wks.add(weekCBs.get(weekCBs.size()-1).getText());
				else {
					for(JCheckBox cb : weekCBs) {
						if(cb.isSelected()) {
							wks.add(cb.getText());
						}
					}
				}
				if(wks.isEmpty())	return;
				addBtn.fire("add", null, new Plan(totalMin, spanMin, wks));
				JOptionPane.showMessageDialog(MainWindow.getMainWindow(), "添加计划成功");
			}
		});
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPane, BorderLayout.CENTER);
		this.add(addBtn, BorderLayout.SOUTH);
		
	}
	
}
