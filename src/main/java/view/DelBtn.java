package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

public class DelBtn extends MButton {
	
	private int id = 0;
	
	public DelBtn(int id, String txt, PropertyChangeListener pcl) {
		super(txt, pcl);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
