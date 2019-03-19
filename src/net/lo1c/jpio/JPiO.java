package net.lo1c.jpio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import net.lo1c.jpio.error.ErrorHandler;
import net.lo1c.jpio.gpio.GPIODirection;
import net.lo1c.jpio.gpio.GPIOState;

/**
 * 
 * You have to extend this class, in order to use the JPiO features.
 * 
 * @version 0.1
 * @author lo1c
 *
 */
public class JPiO {

	private final String EXPORT_PATH   = "/sys/class/gpio/export";
	private final String UNEXPORT_PATH = "/sys/class/gpio/unexport";
	
	/**
	 * IMPORTANT CONSTRUCTOR!
	 * This constructor prevents letting your GPIO pins registered, which might cause bugs
	 */
	public JPiO() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		    	cleanup();
		    }
		}));
	}
	
	private HashMap<Integer, GPIODirection> directions   = new HashMap<>();
	private ErrorHandler 			        errorhandler = new ErrorHandler();
	
	/**
	 * Initialize the pin to a specific direction (input / output)
	 * @param pin The pin you wish to control
	 * @param direction The {@link GPIODirection#byInteger(Integer)} (input = 1 / output = 0) to which the pin should be initialized
	 */	
	public void setup(final Integer pin, final Integer direction) {
		this.setup(pin, GPIODirection.byInteger(direction));
	}

	/**
	 * Initialize the pin to a specific direction (input / output)
	 * @param pin The pin you wish to control
	 * @param direction The {@link GPIODirection} (input / output) to which the pin should be initialized
	 * @param state The default value the pin should give
	 */
	public void setup(final Integer pin, final GPIODirection direction) {
		if(this.directions.containsKey(pin)) {
			this.directions.remove(pin);
			this.remove(pin);
		}
		this.write(new File(this.EXPORT_PATH), pin.toString());
		this.directions.put(pin, direction);
		this.write(new File(String.format(GPIODirection.PATH, pin)), direction.toString());
		//this.output(pin, state);
	}
	
	/**
	 * Unregisters a pin, registered with the setup method
	 */	
	public void unregister(final Integer pin) {
		this.remove(pin);
	}
	
	/**
	 * Unregisters a pin, registered with the setup method
	 */
	public void remove(final Integer pin) {
		this.write(new File(this.UNEXPORT_PATH), pin.toString());
	}

	/**
	 * Set the output to on (or high) of the pin
	 * @param pin The pin you wish to control
	 */
	public void output(final Integer pin) {
		this.output(pin, GPIOState.HIGH);
	}
	
	/**
	 * Set the output (on / off, or rather high / low) on the pin
	 * @param pin The pin you wish to control
	 * @param state The {@link GPIOState} (high = 1 / low = 0) the pin should give as an output
	 */
	public void output(final Integer pin, final Integer state) {
		this.output(pin, GPIOState.byInteger(state));
	}
	

	/**
	 * Set the output (on / off, or rather high / low) on the pin
	 * @param pin The pin you wish to control
	 * @param state The {@link GPIOState} (high / low) the pin should give as an output
	 */
	public void output(final Integer pin, final GPIOState state) {
		if(this.directions.get(pin) == GPIODirection.OUTPUT)
			this.write(new File(String.format(GPIOState.PATH, pin)), state.getState().toString());
		else
			this.getErrorhandler().error(ErrorHandler.UNREGISTERED_OUTPUT);
	}

	
	/**
	 * Get the input (1 = on, 0 = off) of a pin
	 * @param pin The pin you wish to get the input
	 */
	public Integer input(final Integer pin) {
		try {
			return Integer.parseInt(this.read(new File(String.format(GPIOState.PATH, pin))));
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Get the input as an boolean
	 * @param pin The pin you wish to get the input
	 * @return Either if the pin is powered or not
	 */
	public Boolean isInput(final Integer pin) {
		return input(pin) != 0;
	}
	
	
	/**
	 * Clears all pins you've initialized using the setup method
	 */
	public void cleanup() {
		for(Integer pin : this.directions.keySet()) {
			this.write(new File(this.UNEXPORT_PATH), pin.toString());
		}
		this.directions.clear();
	}
	
	
	/**
	 * Waits x milliseconds before continuing the mainthread
	 * @param milliseconds The milliseconds you wish to sleep
	 */
	public void sleep(final Long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Waits x seconds before continuing the mainthread
	 * @param seconds The seconds you wish to sleep
	 */
	public void sleep(final Double seconds) {
		try {
			Thread.sleep((long) (seconds * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Waits x seconds before continuing the mainthread
	 * @param seconds The seconds you wish to sleep
	 */
	public void sleep(final Integer seconds) {
		try {
			Thread.sleep((seconds * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Overwrites a file with your content
	 * @param file The file you wish to write
	 * @param content The content you wish to write in the file
	 */
	private void write(final File file, final String content) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
			writer.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Reads the first line of a file
	 * @param file The file from which you will read
	 * @return The first line of the file
	 */
	private String read(final File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			reader.close();
			return line;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	public ErrorHandler getErrorhandler() { return this.errorhandler; }
	
}
