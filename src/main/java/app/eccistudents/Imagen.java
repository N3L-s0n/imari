package app.eccistudents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public final class Imagen extends JFrame {
		/**
		 *
		 */
	private static final long serialVersionUID = 1L;
	private BufferedImage buffer;
	
	public Imagen(int ancho, int alto) {
		this.alto = alto;
		this.ancho = ancho;
		int blanco = (new Color(255, 255, 255)).getRGB();
		this.buffer = new BufferedImage(ancho, alto, 1);
		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++)
				this.buffer.setRGB(i, j, blanco); 
		} 
	}
	private int ancho; private int alto;
	
	public Imagen(int[][] pixeles) {
		this.alto = pixeles.length;
		this.ancho = pixeles[0].length;
		this.buffer = new BufferedImage(this.ancho, this.alto, 1);
		for (int i = 0; i < this.ancho; i++) {
			for (int j = 0; j < this.alto; j++) {
				this.buffer.setRGB(i, j, pixeles[j][i]);
			}
		} 
	}

	public BufferedImage getBufferedImage(){
		return buffer;
	}
	
	public Imagen(String nombreImagen) {
		Image imagen = (new ImageIcon(nombreImagen)).getImage();
		this.ancho = imagen.getWidth(this);
		this.alto = imagen.getHeight(this);
		this.buffer = new BufferedImage(this.ancho, this.alto, 1);
		this.buffer.createGraphics().drawImage(imagen, 0, 0, this.ancho, this.alto, this);
	}

	
	public int getAncho() { return this.ancho; }


	
	public int getAltura() { return this.alto; }

	
	public void verColores() {
		for (int i = 0; i < this.ancho; i++) {
			for (int j = 0; j < this.alto; j++) {
				System.out.print("\t" + new Color(this.buffer.getRGB(i, j)));
			}
			System.out.println();
		} 
	}


	
	public Color getPixelColor(int x, int y) { return new Color(this.buffer.getRGB(x, y)); }


	
	public void setPixelColor(int x, int y, Color c) { this.buffer.setRGB(x, y, c.getRGB()); }
	
	public void dibujar() {
		setSize(this.ancho + 100, this.alto + 100);
		setVisible(true);
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(this.buffer, 50, 50, this.ancho, this.alto, this);
	}
	
	public int[][] getMatriz() {
		int[][] matriz = new int[this.alto][this.ancho];
		for (int i = 0; i < this.ancho; i++) {
			for (int j = 0; j < this.alto; j++) {
				matriz[j][i] = this.buffer.getRGB(i, j);
			}
		} 
		return matriz;
	}
}
