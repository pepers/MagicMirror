package main.java;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/*
 * by Matthew Pepers
 * 
 * This program will wait to detect motion from the user, then it will 
 * display the current weather, read the daily forecast, and suggest what
 * clothing the user should wear today.
 */

public class MagicMirror implements Runnable {

	private View gui;                // the program GUI
	private WeatherIcon weatherIcon; // the weather icons
	private Clothing clothing;       // the clothing to wear
	
	public final static int minBetweenCalls = 60; // number of minutes between api calls
	
	// open weather map api:
	private final String key = "f886e3a99da41b3c10ec6c92ac46583d";    // my owm api key
	private final int city = 6094817;                                 // Ottawa, CA
	private final WeatherData.Units unit = WeatherData.Units.CELCIUS; // units in Celcius
	
	// pi4j PIR motion detection:
	/* TODO: set correct pins and uncomment once system (raspberry pi + pir) is set up
	final GpioController gpioSensor = GpioFactory.getInstance(); 
	final GpioPinDigitalInput sensor = gpioSensor.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);          
	final GpioController gpioLED = GpioFactory.getInstance();           
	final GpioPinDigitalOutput led = gpioLED.provisionDigitalOutputPin(RaspiPin.GPIO_05, "LED", PinState.HIGH);         
	*/
	public static void main(String[] args) {
		(new Thread(new MagicMirror())).start();
	}
	
	public MagicMirror() {
		this.gui = new View("weathericons-regular-webfont.ttf");
		this.weatherIcon = new WeatherIcon();
		this.clothing = new Clothing();
		
		/* TODO: uncomment when raspberry pi and pir is set up
		led.high(); TODO: uncomment when raspberry pi and pir is set up
		// create and register gpio pin listener
		sensor.addListener(new GpioPinListenerDigital() {           
			@Override       
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {        
				if(event.getState().isHigh()){  
					System.out.println("Motion Detected!"); 
					led.toggle();
					// make JNI wrapper for C functions to turn monitor on:
					// SendMessage(HWND_BROADCAST, WM_SYSCOMMAND, SC_MONITORPOWER, (LPARAM) -1);		 
				}  
				if(event.getState().isLow()){   
					System.out.println("All is quiet...");
					led.toggle();
					// make JNI wrapper for C functions to turn monitor off:
					// SendMessage(HWND_BROADCAST, WM_SYSCOMMAND, SC_MONITORPOWER, (LPARAM) 2); 
				} 
			}  
		});   
		*/ 
	}
	
	public void run() {
		while(true) {
			// get weather info for Ottawa, Ontario, Canada
			WeatherData data = new WeatherData(this.key, this.city, this.unit);
			data.getJSON();
			
			// time/date data:
			long time     = data.getTime();
			long sunrise  = data.getSunrise();
			long sunset   = data.getSunset();
			boolean isDay = ((time >= sunrise) && (time < sunset)); // is it Day or Night?
			
			// weather data:
			float temp       = data.getTemperature();
			int humPerc      = data.getHumidity();
			float humIndex   = fToC(humidityIndex(cToF(temp), humPerc)); 
			int wID          = data.getWeatherID();
			String icon      = this.weatherIcon.getIcon(isDay, wID);
			String condition = this.weatherIcon.getCondition(wID);
			
			// clothing data:			
			this.clothing.setLevel(clothingLevel(humIndex)); 
			String headIcon, torsoIcon, legsIcon, headName, torsoName, legsName;
			headIcon = torsoIcon = legsIcon = headName = torsoName = legsName = "";
			try {
				headIcon  = this.clothing.getHeadIcon();
				torsoIcon = this.clothing.getTorsoIcon();
				legsIcon  = this.clothing.getLegsIcon();
				headName  = this.clothing.getHeadName();
				torsoName = this.clothing.getTorsoName();
				legsName  = this.clothing.getLegsName();
			} catch (Exception e) {
				System.out.println("ERROR: clothing level was not set 1-7 inclusive. Exiting.");
				System.exit(0);
			}
			
			// print data to console:
			printTimeDate(time, sunrise, sunset);
			printWeather(isDay, temp, humPerc, humIndex, wID, condition);
			printClothing(headName, torsoName, legsName);
			System.out.println("");
			
			// inform gui:
			this.gui.getCurrent().setTemp(Math.round(temp));
			this.gui.getCurrent().setFeels(Math.round(humIndex));
			this.gui.getCurrent().setIcon(icon);
			this.gui.getClothing().setHead(headIcon);
			this.gui.getClothing().setTorso(torsoIcon);
			this.gui.getClothing().setLegs(legsIcon);
			
			try {
				// sleep for 30min (weather update every 30min)
				Thread.sleep(MagicMirror.minBetweenCalls*60000);
			} catch (InterruptedException e1) {
				System.out.println("Error: could not sleep thread for " 
						+ MagicMirror.minBetweenCalls + "min: " + e1);
			}
		}
	}
	
	/*
	 * print time/date info obtained to console
	 */
	private void printTimeDate(long time, long sunrise, long sunset) {
		System.out.printf("%-19s - Sun Rises at %s, and Sets at %s.\n", dateFormat(time), timeFormat(sunrise), timeFormat(sunset));	
	}
	
	/*
	 * print weather info obtained to console
	 */
	private void printWeather(boolean isDay, float temp, int humPerc, float humIndex, 
			int wID, String condition) {
		String dayNight = (isDay) ? "Day" : "Night";
		System.out.print("                    - ");	
		System.out.printf("It is a %s %s.  The temperature is %.1fC, but feels like %.1fC, because of the %d%% humidity.\n", condition, dayNight, temp, humIndex, humPerc);
	}
	
	/*
	 * print clothing info obtained to console
	 */
	private void printClothing(String head, String torso, String legs) {
		System.out.print("                    - ");
		System.out.printf("You should wear: %s on your head, a %s, and a pair of %s\n", head, torso, legs);
	}
	
	/*
	 * convert unix time to nice time of day format
	 */
	private String timeFormat(long unixTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
		// GMT-4 for Ottawa,ON
		return Instant.ofEpochSecond(unixTime).atZone(ZoneId.of("GMT-4")).format(formatter);
	}
	
	/*
	 * convert unix time to nice date format
	 */
	private String dateFormat(long unixTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
		// GMT-4 for Ottawa,ON
		return Instant.ofEpochSecond(unixTime).atZone(ZoneId.of("GMT-4")).format(formatter);
	}
	
	/*
	 * convert celcius to farenheit
	 */
	private float cToF(float c) {
		return (c * 9/5) + 32;
	}
	
	/*
	 * convert farenheit to celcius
	 */
	private float fToC(float f) {
		return (f - 32) * 5/9;
	}
	
	/*
	 * calculate humidity index (feels like) (Farenheit)
	 * t = dry-bulb temperature
	 * r = relative humidity (percent, eg: 73.0)
	 */
	private float humidityIndex(float t, float r) {
		// constants:
		final double c1 = -42.379;
		final double c2 = 2.04901523;
		final double c3 = 10.14333127;
		final double c4 = -0.22475541;
		final double c5 = -0.00683783;
		final double c6 = -0.05481717;
		final double c7 = 0.00122874;
		final double c8 = 0.00085282;
		final double c9 = -0.000000199;
		
		r = r/100; // decimal percentage
		
		// humidity index calculation
		double hi = c1 + (c2*t) + (c3*r) + (c4*t*r)	+ (c5*t*t) + (c6*r*r) + (c7*t*t*r) 
				+ (c8*t*r*r) + (c9*t*t*r*r);
		
		return (float)hi;
	}

	/*
	 * simple calculation of clothing level based on humidity index (relative temperature)
	 * clothing level 1-7 (least clothing to most clothing) (see Clothing class)
	 */
	private int clothingLevel(float hi) {
		int temp = Math.round(hi); // get an approximation of relative temperature 
		int level = 1;             // level to return;
		
		// take care of levels 1-7
		if (temp < -5) {                          level = 7;
		} else if ((temp >= -5) && (temp < 0)) {  level = 6;
		} else if ((temp >= 0) && (temp < 5)) {   level = 5;		
		} else if ((temp >= 5) && (temp < 10)) {  level = 4;		
		} else if ((temp >= 10) && (temp < 15)) { level = 3;		
		} else if ((temp >= 15) && (temp < 25)) { level = 2;	
		} else if (temp >= 25) {                  level = 1; }
		
		return level;
	}
}
