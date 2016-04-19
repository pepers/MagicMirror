package main.java;

/*
 * display clothing to wear
 */

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ClothingView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// panels for images:
	private ImagePanel head;  
	private ImagePanel torso; 
	private ImagePanel legs;
	
	public ClothingView() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.BLACK); // without this, there may be a white line at bottom
		
		// Head:
		this.head = new ImagePanel();
		head.setBackground(Color.BLACK);
		this.add(head);
		head.setVisible(true);
		
		// Torso:
		this.torso = new ImagePanel();
		torso.setBackground(Color.BLACK);
		this.add(torso);
		torso.setVisible(true);
		
		// Legs:
		this.legs = new ImagePanel();
		legs.setBackground(Color.BLACK);
		this.add(legs);
		legs.setVisible(true);
	}
	
	// ***** PUBLIC METHODS: ***********************************************************
	
	public void setHead(String strHead)   { this.head.loadImage(strHead); }
	public void setTorso(String strTorso) { this.torso.loadImage(strTorso); }
	public void setLegs(String strLegs)   { this.legs.loadImage(strLegs); }
}
