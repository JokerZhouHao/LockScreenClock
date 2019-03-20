package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import entity.Plan;
import utility.Global;
import utility.io.IOUtility;

public class AllPlanModel implements PropertyChangeListener{
	
	private List<Plan> plans = new ArrayList<>();
	private PropertyChangeListener pcAddPlanPanel = null;
	private String fp = null;
	
	public AllPlanModel(String fp) throws Exception{
		this.fp = fp;
		this.loadAllPlansFromFile();
	}
	
	private void loadAllPlansFromFile() throws Exception{
		BufferedReader br = IOUtility.getBR(fp);
		String line = br.readLine();
		int numPlan = Integer.parseInt(line);
		while(numPlan-- > 0) {
			line = br.readLine();
			String[] arr = line.split(Global.delimiterSemicolon);
			List<String> weeks = new ArrayList<>();
			String[] arr1 = arr[2].split(Global.delimiterSemicolon);
			for(String st : arr1)
				weeks.add(st);
			plans.add(new Plan(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), weeks));
		}
		br.close();
	}
	
	public void writeAllPlansToFile(String fp) throws Exception{
		BufferedWriter bw = IOUtility.getBW(fp);
		bw.write(String.valueOf(plans.size()) + "\n");
		for(Plan pl : plans) {
			bw.write(pl.toString() + "\n");
		}
		bw.close();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
//		System.out.println("AllPlanModel get fire " + evt.getPropertyName());
		pcAddPlanPanel.propertyChange(evt);
	}
	
	public Plan getPlan(int index) {
		return plans.get(index);
	}
	
	public void addPlan(Plan pl) {
		this.plans.add(pl);
	}
	
	public void removePlan(int index) {
		this.plans.remove(index);
	}
	
	public int getSize() {
		return plans.size();
	}
	
	public boolean isEmpty() {
		return plans.isEmpty();
	}
	
	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}

	public PropertyChangeListener getPcAddPlanPanel() {
		return pcAddPlanPanel;
	}

	public void setPcAddPlanPanel(PropertyChangeListener pcAddPlanPanel) {
		this.pcAddPlanPanel = pcAddPlanPanel;
	}
	
	public Boolean inLockTime(String curWeek, int curMin) {
		for(Plan pl : this.plans) {
			if(pl.inLockTime(curWeek, curMin))	return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(Plan pl : plans) {
			sb.append(pl.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	public static void main(String[] args) throws Exception{
		String pathPlan = Global.baseDatasetPath + "all_plans.txt";
		AllPlanModel plans = new AllPlanModel(pathPlan);
		plans.writeAllPlansToFile(pathPlan);
		plans = new AllPlanModel(pathPlan);
		System.out.println(plans);
	}
}
