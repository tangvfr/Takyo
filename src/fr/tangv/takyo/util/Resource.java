package fr.tangv.takyo.util;

import org.newdawn.slick.Animation;

public class Resource {

	private String[] textres;
	private Animation[][] imgres;
	
	public Resource(String chemin, String[] name) {
		this.textres = name;
		this.imgres = new Animation[textres.length][4];
		for(int i=0; i < textres.length; i++) {
			//img 1
			Animation img = Anim.createAnimation(chemin+textres[i],0,0f);
			imgres[i][0] = img;
			//img 2
			Animation imgs1 = Anim.createAnimation(chemin+textres[i],0,90f);
			imgres[i][1] = imgs1;
			//img 3
			Animation imgs2 = Anim.createAnimation(chemin+textres[i],0,180f);
			imgres[i][2] = imgs2;
			//img 4
			Animation imgs3 = Anim.createAnimation(chemin+textres[i],0,270f);
			imgres[i][3] = imgs3;
		}
	}
	
	public Animation getAnimation(int id,int sens) {
		return imgres[id][sens];
	}
	
	public Animation getAnimation(String name,int sens) {
		return imgres[getid(name)][sens];
	}
	
	public int getid(String string) {
		for(int i = 0;i < textres.length;i++) {
			if(textres[i] == string) {
				return i;
			}
		}
		return -1;
	}
	
	public String getname(int id) {
		return textres[id];
	}
	
	public int lenght() {
		return imgres.length;
	}
}
