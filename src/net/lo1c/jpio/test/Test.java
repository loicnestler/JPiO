package net.lo1c.jpio.test;

import net.lo1c.jpio.JPiO;
import net.lo1c.jpio.gpio.GPIO;

public class Test extends JPiO {

	public static void main(final String[] args) {
		new Test().start();
	}

	public void start() {
		setup(17, GPIO.OUTPUT);
		setup(12, GPIO.OUTPUT);
		setup(19, GPIO.INPUT);
		
		output(12);
		
		while(true) {
			
			if(isInput(19)) {
				output(17);
			} else
				output(17, GPIO.LOW);
			
			sleep(0.2);
		}
		
	}
	
}
