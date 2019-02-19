package fr.tangv.takyo.gamemode;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import fr.tangv.takyo.game.Jeux;

public class Survie extends Gamemode{
	
	public Survie(int type) {
		super(type);
	}
	
	public void update(Jeux jeux) {
		Input input = jeux.getinput();
		super.update(jeux);
		
		inv.update(input);
	}
	
	public void render(Graphics g, GameContainer container, Jeux jeux) {
		super.render(g, container,jeux);
	}
}
