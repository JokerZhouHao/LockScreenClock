package entity;

import java.util.List;

import utility.Global;

public class Plan {
	private int totalMinute = 0;
	private int spanMinute = 0;
	private List<String> weeks = null;
	
	public Plan(int totalMinute, int spanMinute, List<String> weeks) {
		super();
		this.totalMinute = totalMinute;
		this.spanMinute = spanMinute;
		this.weeks = weeks;
	}

	public int getTotalMinute() {
		return totalMinute;
	}

	public void setTotalMinute(int totalMinute) {
		this.totalMinute = totalMinute;
	}

	public int getSpanMinute() {
		return spanMinute;
	}

	public void setSpanMinute(int spanMinute) {
		this.spanMinute = spanMinute;
	}

	public List<String> getWeeks() {
		return weeks;
	}

	public void setWeeks(List<String> weeks) {
		this.weeks = weeks;
	}
	
	public String getTimeStr() {
		int hour = this.totalMinute/60;
		int minute = this.totalMinute%60;
		String hourStr = null;
		if(hour==0)	hourStr = "00";
		else if(hour<10) hourStr = " " + String.valueOf(hour);
		else hourStr = String.valueOf(hour);
		String minStr = null;
		if(minute==0)	minStr = "00";
		else if(minute < 10) minStr =  String.valueOf(minute) + " ";
		else minStr = String.valueOf(minute);
		return " " + hourStr + ":" + minStr;
	}
	
	public String getWeeksStr() {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<weeks.size(); i++) {
			sb.append(weeks.get(i));
			if(i != weeks.size() -1) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	public String getSpanStr() {
		if(this.spanMinute < 10)	return String.valueOf(spanMinute) + " ";
		else return String.valueOf(spanMinute);
	}
	
	public Boolean inLockTime(String curWeek, int curMin) {
		if((this.weeks.get(0).equals("每天") || this.weeks.get(0).contains(curWeek)) &&
			this.totalMinute <= curMin &&
			this.totalMinute + this.spanMinute >= curMin) {
			return Boolean.TRUE;
		} else return Boolean.FALSE;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(totalMinute);
		sb.append(Global.delimiterSemicolon);
		sb.append(spanMinute);
		sb.append(Global.delimiterSemicolon);
		for(int i=0; i<weeks.size(); i++) {
			sb.append(weeks.get(i));
			if(i != weeks.size()-1)
				sb.append(Global.delimiterCommon);
		}
		return sb.toString();
	}
}
