package main.java;

/*
 * display clothing to wear
 */

import java.awt.Color;
import java.net.URI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.kitfox.svg.app.beans.SVGPanel;
import com.kitfox.svg.app.beans.SVGIcon;

public class ClothingView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	URI uri; // identify resources (icons)
	
	// panels:
	private SVGPanel head;  
	private SVGPanel torso; 
	private SVGPanel legs;
	
	// images for the panels:
	private SVGIcon hIcon;
	private SVGIcon tIcon;
	private SVGIcon lIcon;

	public ClothingView() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Head:
		this.head = new SVGPanel();
		head.setBackground(Color.BLACK);
		this.hIcon = new SVGIcon();
		setHead("/clothing/tuque.svg");
		this.add(head);
		head.setVisible(true);	
		
		// Torso:
		this.torso = new SVGPanel();
		torso.setBackground(Color.BLACK);
		this.tIcon = new SVGIcon();
		setTorso("/clothing/tshirt.svg");
		this.add(torso);
		torso.setVisible(true);
		
		// Legs:
		this.legs = new SVGPanel();
		this.lIcon = new SVGIcon();
		this.add(legs);
		legs.setVisible(true);
	}
	
	// ***** PUBLIC METHODS: ***********************************************************
	
	public void setHead(String head)   { 
		hIcon.setSvgResourcePath(this.getClass().getResourceAsStream(head).toString());
		hIcon.setScaleToFit(true);
	}
	
	public void setTorso(String torso) { 
		tIcon.setSvgResourcePath(this.getClass().getResourceAsStream(torso).toString()); 
		tIcon.setScaleToFit(true);
	}
	
	public void setLegs(String legs)   { 
		lIcon.setSvgResourcePath(this.getClass().getResourceAsStream(legs).toString()); 
		lIcon.setScaleToFit(true);
	}

}
