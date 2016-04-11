package main.java;

/*
 * get appropriate weather icon
 */

public class WeatherIcon {
	
	private static final String NA = "\uf07b";
	private static boolean isDay = true; // is it day (true) or night (false)
	
	// icons for day and night
	private enum Icon {
		CLEAR("\uf00d", "\uf02e"),
		CLOUDY("\uf002", "\uf086"),
		FOGGY("\uf003", "\uf04a"),
		HAZY("\f0b6", NA),
		RAINY("\uf008", "\uf028"),
		STORMY("\uf010", "\uf02d"),
		SNOWY("\uf00a", "\uf02a"),
		SLEETY("\uf0b2", "\uf0b4");
	
		private String day;    // icon for daytime weather
		private String night;  // icon for nighttime weather
		
		private Icon (String day, String night) {	
			this.day = day;
			this.night = night;
		}	
		
		private String getIcon()  { return (isDay) ? this.day : this.night;	}
	}
	
	/*
	 * get the weather icon corresponding to current weather conditions
	 * isDay = is it Day (true) or night (false)
	 * weatherID = id of current weather conditions (openweathermap)
	 */
	public String getIcon (boolean isDay, int weatherID) {
		WeatherIcon.isDay = isDay; 
		String icon = WeatherIcon.NA; // N/A
		
		switch (weatherID) {
			case 200: case 201: case 202: case 210: case 211: case 212:
			case 221: case 230: case 231: case 232:
				icon = Icon.STORMY.getIcon();	
				break;
			case 300: case 301: case 302: case 310: case 311: case 312: case 313: case 314:
			case 321: case 500: case 501: case 502: case 503: case 504: case 511: 
			case 520: case 521: case 522: case 531:
				icon = Icon.RAINY.getIcon();
				break;
			case 600: case 601: case 602: 
				icon = Icon.SNOWY.getIcon();
				break;
			case 611: case 612:
				icon = Icon.SLEETY.getIcon();
				break;
			case 615: case 616: case 620: case 621: case 622:
				icon = Icon.SNOWY.getIcon();
				break;
			case 701:
				icon = Icon.FOGGY.getIcon();
				break;
			case 721:
				icon = Icon.HAZY.getIcon();
				break;
			case 741:
				icon = Icon.FOGGY.getIcon();
				break;
			case 800: 
				icon = Icon.CLEAR.getIcon();	
				break;
			case 801: case 802: case 803: case 804:
				icon = Icon.CLOUDY.getIcon();
				break;
			default: 
				break;	
		}
		
		return icon;
	}
	
	/*
	 * get weather condition name
	 */
	public String getCondition (int weatherID) {
		String condition = "?";
		
		switch (weatherID) {
			case 200: case 201: case 202: case 210: case 211: case 212:
			case 221: case 230: case 231: case 232:
				condition = Icon.STORMY.toString();	
				break;
			case 300: case 301: case 302: case 310: case 311: case 312: case 313: case 314:
			case 321: case 500: case 501: case 502: case 503: case 504: case 511: 
			case 520: case 521: case 522: case 531:
				condition = Icon.RAINY.toString();
				break;
			case 600: case 601: case 602: 
				condition = Icon.SNOWY.toString();
				break;
			case 611: case 612:
				condition = Icon.SLEETY.toString();
				break;
			case 615: case 616: case 620: case 621: case 622:
				condition = Icon.SNOWY.toString();
				break;
			case 701:
				condition = Icon.FOGGY.toString();
				break;
			case 721:
				condition = Icon.HAZY.toString();
				break;
			case 741:
				condition = Icon.FOGGY.toString();
				break;
			case 800: 
				condition = Icon.CLEAR.toString();	
				break;
			case 801: case 802: case 803: case 804:
				condition = Icon.CLOUDY.toString();
				break;
			default: 
				break;	
		}
		
		return condition;
	}
}
