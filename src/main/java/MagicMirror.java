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

	private View gui;        // the program GUI
	WeatherIcon weatherIcon; // the weather icons
	public final static int minBetweenCalls = 60; // number of minutes between api calls
	
	// open weather map api:
	private final String key = "f886e3a99da41b3c10ec6c92ac46583d";    // my owm api key
	private final int city = 6094817;                                 // Ottawa, CA
	private final WeatherData.Units unit = WeatherData.Units.CELCIUS; // units in Celcius
	
	public static void main(String[] args) {
		(new Thread(new MagicMirror())).start();
	}
	
	public MagicMirror() {
		this.gui = new View(this, "weathericons-regular-webfont.ttf");
		this.weatherIcon = new WeatherIcon();
	}
	
	public void run() {
		while(true) {
			// get weather info for Ottawa, Ontario, Canada
			WeatherData data = new WeatherData(this.key, this.city, this.unit);
			data.getJSON();
			
			// data:
			long time = data.getTime();
			long sunrise = data.getSunrise();
			long sunset = data.getSunset();
			boolean isDay = ((time >= sunrise) && (time < sunset)); // is it Day or Night?
			float temp = data.getTemperature();
			int humPerc = data.getHumidity();
			float humIndex = fToC(humidityIndex(cToF(temp), humPerc)); 
			int wID = data.getWeatherID();
			String icon = this.weatherIcon.getIcon(isDay, wID);
			String condition = this.weatherIcon.getCondition(wID);
			
			// print data to console:
			print(time, sunrise, sunset, isDay, temp, humPerc, humIndex, wID, condition);
			
			// inform gui:
			this.gui.getCurrent().setTemp(Math.round(temp));
			this.gui.getCurrent().setFeels(Math.round(humIndex));
			this.gui.getCurrent().setIcon(icon);
			
			try {
				// sleep for 30min (weather update every 30min)
				Thread.sleep(this.minBetweenCalls*60000);
			} catch (InterruptedException e1) {
				System.out.println("Error: could not sleep thread for " 
						+ this.minBetweenCalls + "min: " + e1);
			}
		}
	}
	
	/*
	 * print info obtained to console
	 */
	private void print(long time, long sunrise, long sunset, boolean isDay, 
			float temp, int humPerc, float humIndex, int wID, String condition) {
		String dayNight = (isDay) ? "Day" : "Night";
		System.out.printf("%-19s - Sun Rises at %s, and Sets at %s.\n", dateFormat(time), timeFormat(sunrise), timeFormat(sunset));
		System.out.print("                    - ");
		System.out.printf("It is a %s %s.  The temperature is %.1fC, but feels like %.1fC, because of the %d%% humidity.", condition, dayNight, temp, humIndex, humPerc);
		System.out.println("");
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

}
