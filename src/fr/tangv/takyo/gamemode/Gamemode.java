package fr.tangv.takyo.gamemode;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

import fr.tangv.takyo.game.App;
import fr.tangv.takyo.game.Jeux;
import fr.tangv.takyo.objet.Inventory;
import fr.tangv.takyo.util.MouseClick;
import fr.tangv.takyo.util.Res;
import fr.tangv.takyo.util.Tfont;

public class Gamemode {
	
	public static final int survie = 0;
	public static final int creatif = 1;
	
	protected Gamemode(int type) {
		this.type = type;
	}

	public static Gamemode survie() {
		return new Survie(survie);
	}
	
	public static Gamemode creatif() {
		return new Creatif(creatif);
	}
	
	protected Inventory inv = new Inventory("invhero");
	protected MouseClick mouseclick = new MouseClick();
	protected Animation toolbarimg = Res.resource[2].getAnimation(3, 0);
	protected TrueTypeFont fonttext = Tfont.fonttext1;
	protected int type;
	
	public int gettype() {
		return type;
	}
	
	public Inventory getinv() {
		return inv;
	}
	
	public void update(Jeux jeux) {
		Jeux.blockref.clear();
		Input input = jeux.getinput();
		mouseclick.update(input);
		if(input.isKeyPressed(Res.parametre.getentrer()) && input.isKeyDown(Res.parametre.getentrer())) {
			inv.setiopen();
		}
		if(input.isKeyPressed(Input.KEY_K)) {
			System.out.println((App.ecran[0]/2-input.getMouseX()) +" | "+ (App.ecran[1]/2-input.getMouseY()));
		}
		if(input.isKeyPressed(Input.KEY_U)) {
			System.out.println(jeux.getmap().getmapsave().getpointcentre()[0] +" | "+ jeux.getmap().getmapsave().getpointcentre()[1]);
		}
	}
	
	public void render(Graphics g, GameContainer container,Jeux jeux) {
		if(!inv.getopen()) {
			toolbarimg.draw(App.ecran[0]-toolbarimg.getWidth(),0);
			if(inv.getitemselec() != null) {
				float[] coord = {App.ecran[0]-toolbarimg.getWidth()+8,8};
				inv.getitemselec().render(coord);
				fonttext.drawString(coord[0]+44,(toolbarimg.getHeight()/2)-(fonttext.getHeight()/2),inv.getitemselec().getname(),new Color(255f, 255f, 255f));
			}
		}
		inv.render(g);
	}
	
	public boolean equal(Gamemode gamemode) {
		if(gamemode.gettype() == type) {
			return true;
		}
		return false;
	}
}
