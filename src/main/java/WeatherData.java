package main.java;

import java.io.IOException;

/*
 * get weather data from openweathermap
 */

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.*; // deal with JSON

public class WeatherData {

	// for api call:
	private String sURL = "http://api.openweathermap.org/data/2.5/weather";
	private String APIkey; // your openweathermap api key
	private int cityID;    // city id
	private Units unit;    // unit format for data
	public enum Units { 
		FARENHEIT("&units=imperial"),
		CELCIUS("&units=metric"),
		KELVIN("");
		private String unit;  // unit name	
		private Units (String unit) { this.unit = unit; }	
		public  String getUnit()    { return unit; }
	}
	
	// retrievable data:
	private long sunrise = 0;          // time of today's sunrise (unix time)
	private long sunset = 0;           // time of today's sunset (unix time)
	private int weatherID = 0;         // id for weather condition
	private float temp = 0;            // current temperature
	private int humidity = 0;          // percentage humidity
	
	// system data:
	private long time = 0;             // current system time (unix time)
	private HttpURLConnection request; // connection to owm
	
	/*
	 * constructor
	 */
	public WeatherData(String APIkey, int cityID, Units unit) {
		this.APIkey = APIkey;	
		this.cityID = cityID;
		this.unit = unit;
	}
	
	// ***** PRIVATE METHODS: *************************************************
	
	/*
	 * connect to url
	 */
	private boolean connect() {
		try {
			URL url = new URL(createURL());
			this.request = (HttpURLConnection) url.openConnection();
			this.request.connect();
		} catch (Exception e) {
			System.out.println("Error: could not connect to url: " + e);
			return false;
		}
		return true;
	}
	
	/*
	 * create the query url
	 */
	private String createURL() {
		return sURL + "?id=" + this.cityID + "&APPID=" + this.APIkey + this.unit.getUnit();
	}
	
	/*
	 * convert input stream to JSON object
	 */
	private JsonObject streamToJSON(InputStream is) {
		JsonParser jp = new JsonParser(); // from gson
		JsonElement jsonElem = jp.parse(new InputStreamReader(is));	
		return jsonElem.getAsJsonObject(); // may be an array, may be an object. 
	}
	
	/*
	 * parse the data after getJSON()
	 */
	private void parse(JsonObject obj) {
		this.time = System.currentTimeMillis()/1000; // current time
		
		// parse weather elements:
		JsonArray weather = obj.getAsJsonArray("weather");
		this.weatherID = weather.get(0).getAsJsonObject().get("id").getAsInt();
		
		// parse main elements:
		JsonObject main = obj.getAsJsonObject("main");
		this.temp = main.get("temp").getAsFloat();
		this.humidity = main.get("humidity").getAsInt();
		
		// parse system elements:
		JsonObject sys = obj.getAsJsonObject("sys");
		this.sunrise = sys.get("sunrise").getAsLong();
		this.sunset = sys.get("sunset").getAsLong();
	}
	
	// ***** PUBLIC METHODS: **************************************************
	
	public long  getTime()        { return this.time; }
	public long  getSunrise()     { return this.sunrise; }
	public long  getSunset()      { return this.sunset; }
	public float getTemperature() { return this.temp; }
	public int   getHumidity()    { return this.humidity; }
	public int   getWeatherID()   { return this.weatherID; }
	
	/*
	 * connect to url, and retrieve JSON object
	 */
	public JsonObject getJSON() {
		JsonObject obj = null;
		if (connect()) {
			try {
				obj = streamToJSON((InputStream) this.request.getContent());
			} catch (IOException e) {
				System.out.println("Error: could not convert stream to json element: " + e);
			}
		}
		parse(obj); // parse the data retrieved
		return obj; 
	}	

}

