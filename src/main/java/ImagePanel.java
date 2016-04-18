package main.java;

/*
 * display an image
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage image = null;
	
	/*
	 * load a new image in place of current
	 */
	public void loadImage(String path) {
		try {             
			image = ImageIO.read(this.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			System.out.println("ERROR: could not read image: " + e);
		}
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// center and scale image
		if (this.image != null) {
			double r = (double) this.getHeight() / image.getHeight(null);
			double x = (this.getWidth() - image.getWidth(null) * r) / 2;
			g.drawImage(image, 
					(int) x, 
					0, 
					(int) (image.getWidth(null) * r), 
					(int) (image.getHeight(null) * r), 
					null);
		}
	}
}
