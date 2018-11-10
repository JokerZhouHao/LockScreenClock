package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import entity.Plan;
import window.MainWindow;

public class OnePlanPanel extends JPanel {
	private Plan plan = null;
	private DelBtn delBtn = null;
	
	public OnePlanPanel(Plan plan, int delBtnId, PropertyChangeListener pcl) {
		super();
		this.plan = plan;
		delBtn = new DelBtn(delBtnId, String.valueOf("删除"), pcl);
//		delBtn.setFont(new Font("楷体", Font.PLAIN, 12));
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println(String.valueOf(delBtn.getId()) + " click ");
				if(JOptionPane.OK_OPTION ==  JOptionPane.showConfirmDialog(MainWindow.getMainWindow(), "确定要删除该计划吗？", "提示消息", JOptionPane.WARNING_MESSAGE)) {
					delBtn.fire("del", null, new Integer(delBtn.getId()));
				}
				
			}
		});
//		delBtn.setBackground(Color.yellow);
//		this.setBackground(Color.gray);
		this.init();
	}
	
	public void init() {
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = gbc.WEST;
		JLabel timeLbl = new JLabel(plan.getTimeStr());
		timeLbl.setHorizontalAlignment(SwingConstants.LEFT);
		timeLbl.setPreferredSize(new Dimension(70, 30));
		this.add(timeLbl, gbc);
		
		gbc.weightx = 0;
		gbc.gridx++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = gbc.WEST;
		JLabel spanLbl = new JLabel("  " + String.valueOf(plan.getSpanStr()));
		spanLbl.setHorizontalAlignment(SwingConstants.LEFT);
		spanLbl.setPreferredSize(new Dimension(70, 30));
		this.add(spanLbl, gbc);
		
		gbc.weightx = 0.7;
		gbc.gridx++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = gbc.EAST;
		JLabel weekLbl = new JLabel(plan.getWeeksStr());
		weekLbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(weekLbl, gbc);
		
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.gridx++;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(delBtn, gbc);
	}

	public DelBtn getDelBtn() {
		return delBtn;
	}
	
}
