package javafx.model;

import javafx.scene.control.Label;

public class MyLabel extends Label implements Comparable<MyLabel> {
	
	@Override
	public int compareTo(MyLabel o) {
		Double i_1 = getInt(), i_2 = o.getInt();
		
		return Double.compare(i_1, i_2);
	}		
	
	private double getInt() {
		Double i = null;
		
		try{
			i = Double.parseDouble(this.getText());
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		return i;
	}
}
