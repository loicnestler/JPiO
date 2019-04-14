# JPiO
## A GPIO API for the Raspberry Pi 3, written in java.
###### :warning: This package is originally from January 2017 and not under active development. But please still feel free to contribute or use this library as you'd like. :warning:

### Example
```java
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
```
