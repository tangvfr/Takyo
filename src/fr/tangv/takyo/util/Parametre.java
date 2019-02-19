package fr.tangv.takyo.util;

import org.newdawn.slick.Input;

public class Parametre {

	private String langue = "fr";
	
	public void setlangue(String string) {
		langue = string;
	}
	
	public String getlangue() {
		return langue;
	}
	
	private int esc = Input.KEY_ESCAPE;
	private int avancer = Input.KEY_Z;
	private int reculer = Input.KEY_S;
	private int gauche = Input.KEY_Q;
	private int droit = Input.KEY_D;
	private int entrer = Input.KEY_ENTER;
	
	private int exporter = Input.KEY_P;
	private int point = Input.KEY_M;

	//-------------------------------------

	public void setavancer(int i) {
		avancer = i;
	}
	
	public void setreculer(int i) {
		reculer = i;
	}
	
	public void setgauche(int i) {
		gauche = i;
	}
	
	public void setdroit(int i) {
		droit = i;
	}
	
	public void setentrer(int i) {
		entrer = i;
	}
	
	public void setexporter(int i) {
		exporter = i;
	}
	
	public void setpoint(int i) {
		point = i;
	}
	
	public void setesc(int i) {
		esc = i;
	}
	
	//-------------------------------------
	
	public int getavancer() {
		return avancer;
	}
	
	public int getreculer() {
		return reculer;
	}
	
	public int getgauche() {
		return gauche;
	}
	
	public int getdroit() {
		return droit;
	}
	
	public int getentrer() {
		return entrer;
	}
	
	public int getexporter() {
		return exporter;
	}
	
	public int getpoint() {
		return point;
	}
	
	public int getesc() {
		return esc;
	}
	
}
