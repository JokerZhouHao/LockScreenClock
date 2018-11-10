package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

public class MButton extends JButton{
	protected PropertyChangeListener pcl = null;
	
	public MButton(String txt, PropertyChangeListener pcl) {
		super(txt);
		this.pcl = pcl;
	}
	
	public void fire(String propertyName, Object oldValue, Object newValue) {
		this.pcl.propertyChange(new PropertyChangeEvent(this, propertyName, oldValue, newValue));
	}
}
