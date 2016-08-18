package javafx.model;

import javafx.model.MyLabel;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Pokemon {
	
	private String id, name;
	private MyLabel hp, attack, defense, spa, spd, speed, iv, rating;
	
	public Pokemon(String id, String name, int hp, int attack, int defense, int spa, int spd, int speed) {
		this.name = name;
		this.id = id;
		this.hp = parseIVToMyLabel(hp, 31.0);
		this.attack = parseIVToMyLabel(attack, 31.0);
		this.defense = parseIVToMyLabel(defense, 31.0);
		this.spa = parseIVToMyLabel(spa, 31.0);
		this.spd = parseIVToMyLabel(spd, 31.0);
		this.speed = parseIVToMyLabel(speed, 31.0);
		this.iv = parseIVToMyLabel(hp + attack + defense + spa + spd + speed, 186.0);
		this.rating = getRating(hp, attack, defense, spa, spd, speed);
	}
	
	private MyLabel getRating(int hp, int attack, int defense, int spa, int spd, int speed) {
		MyLabel label = new MyLabel();
		
		double rating = 10*(hp*1.2 + Math.max(attack, spa) + defense + spd + speed*0.8)/155;
		label.setText(Double.toString(rating).substring(0, 3));
		label.setTextFill(getTextColor(rating, 10.0));
		
		return label;
	}
	
	private MyLabel parseIVToMyLabel(int iv, double maxIV) {
		MyLabel label = new MyLabel();
		
		label.setText(Integer.toString(iv));
		label.setTextFill(getTextColor(iv, maxIV));
		
		return label;
	}
	
	private Paint getTextColor(int iv, double maxIV) {
		return Color.color(getRed(iv, maxIV), getGreen(iv, maxIV), getBlue(iv, maxIV));
	}
	
	private Paint getTextColor(double iv, double maxIV) {
		return Color.color(getRed(iv, maxIV), getGreen(iv, maxIV), getBlue(iv, maxIV));
	}
	
	public double getRed(int iv, double maxIV) {
		return (Math.pow((double)(iv/maxIV), 2));
	}
	
	public double getRed(double iv, double maxIV) {
		return (Math.pow((double)(iv/maxIV), 2));
	}
	
	public double getGreen(int iv, double maxIV) {
//		return (Math.sin((Math.PI*(double)(iv/maxIV))));
		return 0.0;
	}
	
	public double getGreen(double iv, double maxIV) {
//		return (Math.sin((Math.PI*(double)(iv/maxIV))));
		return 0.0;
	}
	
	public double getBlue(int iv, double maxIV) {
		return (1.0 - 1.0*((double)(iv/maxIV)));
	}
	
	public double getBlue(double iv, double maxIV) {
		return (1.0 - 1.0*((double)(iv/maxIV)));
	}
	
	public String getName() { return name; }
	public String getId() { return id; }
	
	public MyLabel getHp() { return hp; }
	public MyLabel getAttack() { return attack; }
	public MyLabel getDefense() { return defense; }
	public MyLabel getSpa() { return spa; }
	public MyLabel getSpd() { return spd; }
	public MyLabel getSpeed() { return speed; }
	public MyLabel getIv() { return iv; }
	public MyLabel getRating() { return rating; }
	
	public void setName(String name) { this.name = name; }
	public void setId(String id) { this.id = id; }
}
