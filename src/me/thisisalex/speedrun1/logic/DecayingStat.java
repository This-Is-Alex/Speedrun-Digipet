package me.thisisalex.speedrun1.logic;

public class DecayingStat extends Stat {

	final int decayAmount;
	final long decayFrequency;
	long lastUpdate;
	final int max;
	
	public DecayingStat(String name, int value, int decayAmount, long decayFrequency, long lastUpdate, int max) {
		super(name, value);
		
		this.decayAmount = decayAmount;
		this.decayFrequency = decayFrequency;
		
		this.lastUpdate = lastUpdate;
		this.max = max;
	}
	
	public DecayingStat(String name, int value, int decayAmount, long decayFrequency, int max) {
		this(name, value, decayAmount, decayFrequency, System.currentTimeMillis(), max);
	}
	
	@Override
	public void update() {
		int intervals = (int) Math.floor((System.currentTimeMillis() - lastUpdate) / decayFrequency);
		this.addValue(0 - (intervals * decayAmount));
		
		if (this.getValue() < 0) {
			this.setValue(0);
		}
		
		lastUpdate += intervals * decayFrequency;
	}
	
	public int maxValue() {
		return this.max;
	}
}
