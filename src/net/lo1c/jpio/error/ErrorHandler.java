package net.lo1c.jpio.error;

public class ErrorHandler {

	public static final String UNREGISTERED_OUTPUT = "Please register this pin as an output first, using the setup() method.";
	
	public void error(final String msg) {
		throw new RuntimeException(msg);
	}
	
}
