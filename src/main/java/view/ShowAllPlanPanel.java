package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JPanel;

import entity.Plan;
import model.AllPlanModel;
import utility.Global;
import window.MainWindow;

public class ShowAllPlanPanel extends JPanel implements PropertyChangeListener {
	
	private AllPlanModel planModel = null;
	public ShowAllPlanPanel(AllPlanModel model) {
		super();
		this.planModel = model;
		this.planModel.setPcAddPlanPanel(this);
		this.init();
	}
	
	private void init() {
		this.setLayout(new GridBagLayout());
		this.setBackground(Global.colorPanelBackground);
		
		if(this.planModel.getSize() == 0)	return;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = -1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(1, 1, 2, 1);
		
		for(int i=0; i<planModel.getSize()-1; i++) {
			gbc.gridy++;
			this.add(new OnePlanPanel(planModel.getPlan(i), i, this.planModel), gbc);
		}
		gbc.gridy++;
		gbc.weighty = 1;
		this.add(new OnePlanPanel(planModel.getPlan(planModel.getSize() - 1), planModel.getSize() - 1, this.planModel), gbc);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt){
		String command = evt.getPropertyName();
		if(command.equals("del")) {
			if(this.planModel.isEmpty())	return;
			int index = (Integer)evt.getNewValue();
			if(index > this.planModel.getSize()-1)	return;
			
			int numPlans = this.planModel.getSize();
			this.planModel.removePlan(index);
			this.remove(index);
			
			if(numPlans != 1) {
				Component[] comps = null;
				if(index == numPlans-1) {
					comps = this.getComponents();
					GridBagConstraints gb = ((GridBagLayout)this.getLayout()).getConstraints(comps[comps.length - 1]);
					gb.weighty = 1;
					((GridBagLayout)this.getLayout()).setConstraints(comps[comps.length - 1], gb);
				}
				
				comps = this.getComponents();
				int i = 0;
				for(Component comp : comps) {
					OnePlanPanel opp = (OnePlanPanel)comp;
					opp.getDelBtn().setId(i++);
				}
			}
			if(index == numPlans-1)	this.repaint();
		} else if(command.equals("add")) {
			Plan addedPlan = (Plan)evt.getNewValue();
			
//			System.out.println(addedPlan);
			
			int numPlan = this.planModel.getSize();
			this.planModel.addPlan(addedPlan);
			
			GridBagConstraints gb = null;
			GridBagLayout gbl = (GridBagLayout)this.getLayout();
			if(numPlan != 0) {
				Component[] comps = this.getComponents();
				gb = gbl.getConstraints(comps[comps.length - 1]);
				gb.weighty = 0;
				gbl.setConstraints(comps[comps.length - 1], gb);
			}
			
			if(null == gb) {
				gb = new GridBagConstraints();
				gb.gridx = 0;
				gb.gridy = -1;
				gb.gridwidth = 1;
				gb.gridheight = 1;
				gb.weightx = 1;
				gb.weighty = 0;
				gb.anchor = GridBagConstraints.NORTH;
				gb.fill = GridBagConstraints.HORIZONTAL;
				gb.insets = new Insets(1, 1, 2, 1);
			}
			gb.gridy++;
			gb.weighty = 1;
			gb.fill = GridBagConstraints.HORIZONTAL;
			OnePlanPanel opp = new OnePlanPanel(addedPlan, this.planModel.getSize() - 1, this.planModel);
			this.add(opp, gb);
			
		}
		
		try {
			this.planModel.writeAllPlansToFile(Global.pathAllPlan);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		Global.mainWindow.getCenterSPane().validate();
		Global.mainWindow.getCenterSPane().doLayout();
		Global.mainWindow.getCenterSPane().repaint();
	}
}
