package net.lo1c.jpio.gpio;

public enum GPIODirection {

	OUTPUT("out"),
	INPUT("in");
	
	final String string;

	public static final String PATH = "/sys/class/gpio/gpio%d/direction";
	
	private GPIODirection(final String string) {
		this.string = string;
	}

	public String getDirection() {
		return this.string;
	}
	
	@Override
	public String toString() {
		return this.getDirection();
	}
	
	public static GPIODirection byName(final String name) {
		for(GPIODirection d : GPIODirection.values()) {
			if(d.getDirection().equalsIgnoreCase(name))
				return d;
		}
		return GPIODirection.OUTPUT;
	}
	
	public static GPIODirection byInteger(final Integer id) {
		if(id == 0)
			return GPIODirection.OUTPUT;
		else if(id == 1)
			return GPIODirection.INPUT;
		
		return GPIODirection.OUTPUT;
	}
	
}
