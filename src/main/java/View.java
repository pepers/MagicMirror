package main.java;

/*
 * the GUI - display all relevant info
 */

import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class View extends JFrame {
	
	private static final long serialVersionUID = 1L; 
	
	private MagicMirror mm;         // main program
	private Font font;              // the weather icons as font
	
	private CurrentWeather currentWeather;

	public View(MagicMirror mm, String strFont) {
		this.mm = mm;
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
		this.currentWeather = new CurrentWeather(this.font, cx);
		this.add(this.currentWeather);				
		
		//this.setUndecorated(true); // Remove title bar TODO: uncomment when done
		this.setVisible(true);
		this.currentWeather.update();    // update the currentWeather
	}
	
	public CurrentWeather getCurrent() { return this.currentWeather; }
	
	public Font loadFont(String fontName) {
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
	
	/*
	 * display current weather
	 */
	public class CurrentWeather extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private final String DEGREE_CELCIUS = "\uf03c";
		private String icon = "\uf07b";  // weather icon
		private int currentTemp = 0;     // current temperature
		private int feelsTemp = 0;       // current temperature feels like
		
		private JLabel current;    // icon + current temperature
		private JLabel feelslike;  // current temperature feels like

		public CurrentWeather(Font iconFont, int height) {
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			this.setBackground(Color.GREEN);
			
			current = new JLabel();
			current.setBackground(Color.RED);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.66;
			c.ipady = 4*height/3;      // make this component tall
			c.gridheight = 2;
			c.gridx = 0;
			c.gridy = 0;
			current.setFont(iconFont);
			current.setForeground(Color.WHITE);
			current.setVisible(true);
			this.add(current, c);
			
			feelslike = new JLabel();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.33;
			c.ipady = 2*height/3;
			c.gridheight = 1;
			c.gridx = 0;
			c.gridy = 2;
			c.anchor = GridBagConstraints.PAGE_END;
			feelslike.setFont(iconFont);
			feelslike.setForeground(Color.WHITE);
			feelslike.setVisible(true);
			this.add(feelslike, c);
			
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
}


