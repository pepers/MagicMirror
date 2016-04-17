package main.java;

/*
 * the GUI - display all relevant info
 */

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;

public class View extends JFrame {
	
	private static final long serialVersionUID = 1L; 
	
	private Font font;                         // the weather icons as font
	private CurrentWeatherView currentWeather; // current weather view
	private ClothingView clothing;             // clothing to wear view

	public View(String strFont) {
		this.font = this.loadFont(strFont);
		assert this.font != null;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen
		this.pack(); // make frame just big enough for panels
		
		// set up layout:
		this.setLayout(new GridLayout(0, 1));
		int y = this.getHeight(); 
		int x = this.getWidth();
		
		// add panels:
		int cx = x;
		this.currentWeather = new CurrentWeatherView(this.font, cx);
		this.add(this.currentWeather);		
		this.clothing = new ClothingView();
		//TODO: add panels to clothing
		
		//this.setUndecorated(true); // Remove title bar TODO: uncomment when done
		this.setVisible(true);
		this.currentWeather.update();    // update the currentWeather
	}
	
	private Font loadFont(String fontName) {
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, 
					this.getClass().getResourceAsStream("/main/resources/" + fontName));
			//font = new Font("Weather Icons Regular");
		} catch (Exception e) {
			System.out.println("Error: creating font: " + e);
		}
		return font;
	}
	
	// ***** PUBLIC METHODS: ***********************************************************
	
	public CurrentWeatherView getCurrent()  { return this.currentWeather; }
	public ClothingView       getClothing() { return this.clothing; }

}


