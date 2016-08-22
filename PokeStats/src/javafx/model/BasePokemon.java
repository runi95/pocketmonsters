package javafx.model;

import javafx.util.Pair;

public class BasePokemon {
	private String name, image;
	private String[] evolutions;
	private Type[] type;
	private Pair<Integer, Integer> hp, attack, defense, spa, spd, speed;
	
	public BasePokemon(String name, String[] evolutions, Type[] type, String image , Pair<Integer, Integer> hp, Pair<Integer, Integer> attack, Pair<Integer, Integer> defense, Pair<Integer, Integer> spa, Pair<Integer, Integer> spd, Pair<Integer, Integer> speed) {
		this.name = name;
		this.evolutions = evolutions;
		this.image = image;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.spa = spa;
		this.spd = spd;
		this.speed = speed;
		this.type = type;
	}
	
	public String getTypesString() {
		return getTypesString("", 0);
	}
 	
	public String getTypesString(String build, int i) {
		if(type.length == i+1)
			return type[i].toString();
		else
			return type[i].toString() + "/" + getTypesString(build, ++i);
	}
	
	public String getName() { return name; }
	public String[] getEvolutions() { return evolutions; }
	public Type[] getType() { return type; }
	public String getImage() { return image; }
	public Pair<Integer, Integer> getHp() { return hp; }
	public Pair<Integer, Integer> getAttack() { return attack; }
	public Pair<Integer, Integer> getDefense() { return defense; }
	public Pair<Integer, Integer> getSpa() { return spa; }
	public Pair<Integer, Integer> getSpd() { return spd; }
	public Pair<Integer, Integer> getSpeed() { return speed; }
}
