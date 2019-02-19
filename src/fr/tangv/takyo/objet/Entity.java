package fr.tangv.takyo.objet;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import fr.tangv.takyo.colide.Box;
import fr.tangv.takyo.colide.Colide;

public class Entity {
	private float[] location;
	private float[] locationt;
	private int largeur;
	private int hauteur;
	private Image img;
	private int sens = 0;
	private String name = "";
	
	public Entity(Image img,float[] location,int largeur,int hauteur) {
		this.img = img;
		this.largeur = largeur;
		this.hauteur = hauteur;
		setlocation(location);
	}
	
	//--------------------------------------------------
	
	public void setlocation(float[] point) {
		locationt = point;
		location = point;
	}
	
	public void setsens(int i) {
		sens = i;
	}
	
	public void setname(String name) {
		this.name = name;
	}
	
	//--------------------------------------------------
	
	public void update(Map map) {
		location = map.getcoordloc(locationt[0], locationt[1]);
	}
	
	public void render(Graphics g, boolean ModeDev) {
		if(Map.inscreen(location[0], location[1], 64)) {
			getimage().draw(location[0], location[1]);
		}
		if(ModeDev) {
			g.setColor(Color.yellow);
			g.drawRect(getlocation()[0], getlocation()[1], getlargeur()-1, gethauteur()-1);
		}
	}
	
	//--------------------------------------------------
	
	public String getname() {
		return name;
	}
	
	public Image getimage() {
		Image simg = img.getScaledCopy(1);
		simg.rotate(90*sens);
		return simg;
	}
	
	public int getlargeur() {
		return largeur;
	}
	
	public int gethauteur() {
		return hauteur;
	}
	
	public float[] getlocation() {
		return location;
	}
	
	public Colide colide(Box box) {
		return getBox().colide(box);
	}
	
	public Box getBox() {
		int jeu = 1;
		float[] loc1 = {location[0]+jeu, location[1]+jeu};
		float[] loc2 = {location[0] + largeur-jeu, location[1] + hauteur-jeu};
		return new Box(loc1,loc2);
	}
}
