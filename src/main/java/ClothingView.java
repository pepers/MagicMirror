package main.java;

/*
 * display clothing to wear
 */

import java.awt.Color;
import java.io.IOException;
import java.net.URI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.kitfox.svg.app.beans.SVGPanel;
import com.kitfox.svg.SVGCache;
import com.kitfox.svg.SVGElement;

public class ClothingView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// panels for images:
	private SVGPanel head;  
	private SVGPanel torso; 
	private SVGPanel legs;
	
	public ClothingView() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Head:
		this.head = new SVGPanel();
		setHead("/clothing/tuque.svg");
		this.add(head);
		head.setVisible(true);	
		
		// Torso:
		this.torso = new SVGPanel();
		setTorso("/clothing/tshirt.svg");
		this.add(torso);
		torso.setVisible(true);
		
		// Legs:
		this.legs = new SVGPanel();
		setLegs("/clothing/pants.svg");
		setColour(this.legs, "#ffffff");
		this.add(legs);
		legs.setVisible(true);
	}
	
	/*
	 * change colour of svg 
	 */
	private void setColour(SVGPanel p, String c) {
		SVGElement e = p.getSvgUniverse().getElement(p.getSvgURI());
		// TODO: change colour
	}
	
	// ***** PUBLIC METHODS: ***********************************************************
	
	public void setHead(String strHead)   { 
		try {
			URI uri = SVGCache.getSVGUniverse().loadSVG(this.getClass().getResourceAsStream(strHead), strHead);
			this.head.setSvgURI(uri);
		} catch (IOException e) {
			System.out.println("ERROR: could not load head svg: " + e);
		}
	}
	
	public void setTorso(String strTorso) { 
		try {
			URI uri = SVGCache.getSVGUniverse().loadSVG(this.getClass().getResourceAsStream(strTorso), strTorso);
			this.torso.setSvgURI(uri);
		} catch (IOException e) {
			System.out.println("ERROR: could not load torso svg: " + e);
		}
	}
	
	public void setLegs(String strLegs)   { 
		try {
			URI uri = SVGCache.getSVGUniverse().loadSVG(this.getClass().getResourceAsStream(strLegs), strLegs);
			this.legs.setSvgURI(uri);
		} catch (IOException e) {
			System.out.println("ERROR: could not load legs svg: " + e);
		}
	}

}
