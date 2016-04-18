package main.java;

/*
 * display current weather:
 * - weather condition icon, current temperature
 * - relative temperature
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CurrentWeatherView extends JPanel {

	private static final long serialVersionUID = 1L;
		
	private static final String DEGREE_CELCIUS = "\uf03c";
	private String icon = "\uf07b";  // weather icon
	private int currentTemp = 0;     // current temperature
	private int feelsTemp = 0;       // current temperature feels like
	
	private JLabel current;    // icon + current temperature
	private JLabel feelslike;  // current temperature feels like

	public CurrentWeatherView(Font iconFont, int width) {
		GridBagLayout gbl_currentWeather = new GridBagLayout();
		gbl_currentWeather.columnWidths = new int[]{width, 0};
		gbl_currentWeather.rowHeights = new int[] {1, 0, 0, 0};
		gbl_currentWeather.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_currentWeather.rowWeights = new double[]{1.0, 1.0, 1.0};
		this.setLayout(gbl_currentWeather);
		
		// Weather Condition Icon + Current Temperature	
		current = new JLabel();
		current.setFont(iconFont);
		current.setForeground(Color.WHITE);
		current.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_current = new GridBagConstraints();
		gbc_current.gridheight = 2;
		gbc_current.fill = GridBagConstraints.BOTH;
		gbc_current.gridx = 0;
		gbc_current.gridy = 0;
		this.add(current, gbc_current);
		
		// "Feels like: " + Current Relative Temperature
		feelslike = new JLabel();
		feelslike.setFont(iconFont);
		feelslike.setForeground(Color.WHITE);
		feelslike.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_feelslike = new GridBagConstraints();
		gbc_feelslike.fill = GridBagConstraints.BOTH;
		gbc_feelslike.gridx = 0;
		gbc_feelslike.gridy = 2;
		this.add(feelslike, gbc_feelslike);
		
		// JPanel
		this.setBackground(Color.BLACK);
		this.setOpaque(true);
	}
		
	public void update() {
		this.current.setText("<html>" + this.icon + "<font face=\"verdana\"> " + this.currentTemp + "</font>" + DEGREE_CELCIUS + "</html>");
		this.feelslike.setText("<html><font face=\"verdana\">Feels like: " + this.feelsTemp + "</font>" + DEGREE_CELCIUS + "</html>");
		resize(this.current);
		resize(this.feelslike);
	}
		
	/*
	 * resize label font to fill label
	 */
	private void resize(JLabel label) {
		Font labelFont = label.getFont();
		String labelText = label.getText();
		int fontStyle = labelFont.getStyle();
			
		int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = label.getWidth();

		// find out how much the font can grow in width
		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = label.getHeight();

		// choose a new font size, not higher than the label
		int fontSizeToUse = Math.min(newFontSize, componentHeight);
			
		// set the font size
		label.setFont(new Font(labelFont.getName(), fontStyle, fontSizeToUse));
	}
	
	// ***** PUBLIC METHODS: ***********************************************************
		
	// set the new temperature
	public void setTemp(int newTemp) { 
		this.currentTemp = newTemp; 
		update();
	}
		
	// set the new current temperature feels like
	public void setFeels(int newFeels) { 
		this.feelsTemp = newFeels; 
		update();
	}
		
	// set the new weather condition
	public void setIcon(String newIcon) { 
		this.icon = newIcon; 
		update();
	}

}
