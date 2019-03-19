package net.lo1c.jpio.gpio;

public enum GPIOState {

	HIGH(1),
	LOW(0);
	
	final Integer state;
	
	public static final String PATH = "/sys/class/gpio/gpio%d/value";
	
	private GPIOState(final Integer state) {
		this.state = state;
	}
	
	public Integer getState() {
		return state;
	}
	
	public static GPIOState byInteger(final Integer state) {
		for(GPIOState s : GPIOState.values()) {
			if(s.getState() == state)
				return s;
		}
		
		return GPIOState.LOW;
	}
	
}
