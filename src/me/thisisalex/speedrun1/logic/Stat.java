package me.thisisalex.speedrun1.logic;

import java.io.Serializable;

public class Stat implements Serializable {

	final String name;
	int value;
	
	public Stat(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int val) {
		this.value = val;
	}
	
	public void addValue(int val) {
		this.value += val;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void update() {
		
	}
}
