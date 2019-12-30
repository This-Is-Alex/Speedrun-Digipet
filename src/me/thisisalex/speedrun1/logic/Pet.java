package me.thisisalex.speedrun1.logic;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import me.thisisalex.speedrun1.menus.MenuManager;

public class Pet implements Serializable {

	final String name;
	int mood = 0; //0 happy, 1 sad, 3 sleeping
	private Map<String, Stat> stats = new LinkedHashMap<>();
	
	public Pet(String name) {
		this.name = name;
		
		stats.put("mood", new DecayingStat("Happiness", 500, 1, 5000, 1000));
		stats.put("energy", new DecayingStat("Physical Energy", 500, 1, 10000, 1000));
		stats.put("thirst", new DecayingStat("Water", 500, 1, 3000, 500));
		stats.put("social", new IncreasingStat("Loneliness", 500, 1, 10000, 1000));
		stats.put("clean", new DecayingStat("Cleanliness", 500, 1, 5000, 1000));
		stats.put("diet", new Stat("Diet Quality (%)", 50));
		
		stats.put("stomachfood", new IncreasingStat("Stomach Capacity", 90, 1, 3000, 100));
		stats.put("sleep", new IncreasingStat("Tiredness", 100, 1, 20000, 1000));
		stats.put("drunk", new DecayingStat("Intoxication", 0, 1, 2000, 100));
		stats.put("piss", new IncreasingStat("Bladder", 0, 1, 3000, 100));
	}
	
	public int getMood() {
		return this.mood;
	}
	
	public Collection<Stat> getStats() {
		return stats.values();
	}
	
	public int getStat(String name) {
		return stats.get(name).getValue();
	}
	
	public void addStat(String name, int amount) {
		stats.get(name).addValue(amount);
	}
	
	public void setStat(String name, int amount) {
		stats.get(name).setValue(amount);
	}
	
	public void tick() {
		if (getStat("stomachfood") < 100) { //gets energy from food
			stats.get("energy").addValue(5);
		}
		
		if (getStat("piss") >= 100) {
			stats.get("clean").setValue(0);
			stats.get("piss").setValue(0);
			MenuManager.dialog(this.name+" peed themselves :(");
		}
		
		if (getStat("clean") < 100) {
			stats.get("mood").addValue(-3);
		}
		
		if (getStat("social") < 200) { //lonely = sad
			stats.get("mood").addValue(-1);
		} else if (getStat("social") > 900 && getStat("mood") < 1000) { //doing stuff = happy
			stats.get("mood").addValue(1);
		}
		
		if (getStat("stomachfood") < 50) { //gets less drunk if they eat
			stats.get("drunk").addValue(-2);
		}
		
		if (getStat("drunk") > 20) { //well at least it makes your character happier
			if (mood != 3) {
				stats.get("thirst").addValue(-2);
				stats.get("piss").addValue(4);
				stats.get("mood").addValue(2);
			}
			stats.get("sleep").addValue(getStat("drunk") / 5);
		}
		
		
		
		if (mood == 3) { //sleep and sadness
			stats.get("sleep").addValue(-15);
			stats.get("mood").addValue(1);
			if (getStat("sleep") < 300) { //sleep until 300
				mood = 0;
			}
		} else if (getStat("sleep") >= 1000) {
			mood = 3;
		} else {
			if (getStat("mood") < 200) {
				mood = 1;
			} else {
				mood = 0;
			}
		}
		
		
		for (Stat stat : getStats()) {
			stat.update();
		}
		
		
		boolean isDead = false;
		//check for kill
		if (getStat("mood") <= 0) {
			MenuManager.dialog("Your pet ran away");
			isDead = true;
		} else if (getStat("energy") <= 0) {
			MenuManager.dialog("Your pet starved to death");
			isDead = true;
		} else if (getStat("thirst") <= 0) {
			MenuManager.dialog("Your pet dehydrated and died");
			isDead = true;
		}
		
		if (isDead) {
			System.exit(0);
		}
	}
}
