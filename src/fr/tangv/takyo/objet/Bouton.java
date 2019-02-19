package fr.tangv.takyo.objet;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

import fr.tangv.takyo.colide.Box;
import fr.tangv.takyo.colide.Colide;
import fr.tangv.takyo.util.Tfont;

public class Bouton {

	private float[] location;
	private int[] taile = new int[2];
	private Image imagedac;
	private Image imageac;
	private Image img;
	private boolean iscolide = false;
	private TrueTypeFont font = Tfont.fontbouton;
	private String text = "";
	private Color color;
	
	public Bouton(Image imgdac,Image imgac,float[] location) {
		this.imagedac = imgdac;
		this.imageac = imgac;
		this.img = imagedac;
		this.location = location;
		taile[0] = imagedac.getWidth();
		taile[1] = imagedac.getHeight();
	}
	
	public Bouton(Image imgdac,Image imgac,float[] location,String text) {
		this.imagedac = imgdac;
		this.imageac = imgac;
		this.img = imagedac;
		this.location = location;
		this.text = text;
		taile[0] = imagedac.getWidth();
		taile[1] = imagedac.getHeight();
	}
	
	public Bouton(Image imgdac,Image imgac,float[] location,String text,Color color) {
		this.imagedac = imgdac;
		this.imageac = imgac;
		this.img = imagedac;
		this.location = location;
		this.text = text;
		this.color = color;
		taile[0] = imagedac.getWidth();
		taile[1] = imagedac.getHeight();
	}
	
	public void render() {
		this.getimage().draw(this.getlocation()[0],this.getlocation()[1]);
		this.getfont().drawString(this.getcentretext()[0], this.getcentretext()[1], this.gettext(),this.getcolor());
	}
	
	public void update(Box box) {
		colide(box);
	}
	
	public void setloaction(float[] location) {
		this.location = location;
	}
	
	public Colide colide(Box box) {
		Box mybox = new Box(location, new float[] {location[0]+taile[0],location[1]+taile[1]});
		Colide colide = mybox.colide(box);
		iscolide = colide.iscolide();
		
		if(iscolide) {
			if(img != imageac) {
				img = imageac;
			}
		}else {
			if(img != imagedac) {
				img = imagedac;
			}
		}
		
		return colide;
	}
	
	public boolean iscolide() {
		return iscolide;
	}
	
	public void settext(String text) {
		this.text = text;
	}
	
	public void setcolor(Color color) {
		this.color = color;
	}
	
	public Color getcolor() {
		return color;
	}
	
	public String gettext() {
		return text;
	}
	
	public TrueTypeFont getfont() {
		return font;
	}
	
	public Image getimage() {
		return img;
	}
	
	public float[] getcentre() {
		float x = location[0] + (taile[0]/2);
		float y = location[1] + (taile[1]/2);
		return new float[]{x, y};
	}
	
	public float[] getcentretext() {
		return new float[]{getcentre()[0] - (font.getWidth(text)/2),getcentre()[1] - (font.getLineHeight()/2)};
	}
	
	public float[] getlocation() {
		return location;
	}
	
}
