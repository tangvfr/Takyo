package fr.tangv.takyo.game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.tangv.takyo.colide.Box;
import fr.tangv.takyo.objet.Bouton;
import fr.tangv.takyo.util.Parametre;
import fr.tangv.takyo.util.Res;

public class Option extends BasicGameState {

	private int id;
	private App app;
	private Input input;
	private Animation fond = Res.resource[2].getAnimation("background-option", 0);
	
	private boolean fullscreen = false;
	
	private String[] namebouton = {"avancer","reculer","droit","gauche","entrer"};
	private String[] stringmethode;
	private Bouton[] bouton;
	private Bouton boutondefault;
	private Bouton boutonretour;
	private Bouton boutonlangue;
	private Bouton boutonscreen;
	private int selec = -1;
	
	public Option(int id, App app) {
		this.id = id;
		this.app = app;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		input = container.getInput();
		
		SpriteSheet bsprite = new SpriteSheet("res/img/bouton/bouton-menu.png", 224, 47);
		float[] bcord = {30f,30f,70f};
		Color bcolor = new Color(10,213,210);
		int decalg = 0;
		float decalh = 0;

		stringmethode = new String[namebouton.length];
		bouton = new Bouton[namebouton.length];
		
		for(int i = 0; i < stringmethode.length; i++) {
			String name = "set"+namebouton[i];
			stringmethode[i] = name;
			if(i == 36) {
				System.out.print("Tros de creations de bouton");
				break;
			}
			if(i == 9 || i == 18 || i == 27) {
				decalg += 254;
				decalh = bcord[2]*i;
			}
			bouton[i] = new Bouton(bsprite.getSprite(0, 1), bsprite.getSprite(0, 0),new float[] {bcord[0]+decalg, (bcord[1]+bcord[2]*i)-decalh},"",bcolor);
		}
		
		boutonretour = new Bouton(bsprite.getSprite(0, 1), bsprite.getSprite(0, 0), new float[]{224*0+26*1,App.ecran[1]-(47f+30f)}, Res.langue.getOptionBoutonText()[0], bcolor);
		boutonlangue = new Bouton(bsprite.getSprite(0, 1), bsprite.getSprite(0, 0), new float[]{224*1+26*2,App.ecran[1]-(47f+30f)}, Res.langue.getOptionBoutonText()[9], bcolor);
		boutonscreen = new Bouton(bsprite.getSprite(0, 1), bsprite.getSprite(0, 0), new float[]{224*2+26*3,App.ecran[1]-(47f+30f)}, Res.langue.getOptionBoutonText()[7], bcolor);
		boutondefault = new Bouton(bsprite.getSprite(0, 1), bsprite.getSprite(0, 0), new float[]{224*3+26*4,App.ecran[1]-(47f+30f)}, Res.langue.getOptionBoutonText()[1], bcolor);	
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Box MouseBox = new Box(new float[] {input.getMouseX(),input.getMouseY()});
		initextbouton();
		boutondefault.colide(MouseBox);
		boutonretour.colide(MouseBox);
		
		boutonlangue.update(MouseBox);
		boutonscreen.update(MouseBox);
		
		boolean clicsouris = input.isMousePressed(0);
		//----------------------------------------------------------------------------------
		if(clicsouris && selec == -1) {
			if(boutondefault.iscolide()) {
				Res.parametre = new Parametre();
				Res.exportparametre();
			}
			if(boutonretour.iscolide()) {
				app.changestate(app.Menu);
			}
			if(boutonscreen.iscolide()) {
				fullscreen = !fullscreen;
				if(fullscreen) boutonscreen.settext(Res.langue.getOptionBoutonText()[8]);
				else boutonscreen.settext(Res.langue.getOptionBoutonText()[7]);
				container.setFullscreen(fullscreen);
			}
			if(boutonlangue.iscolide()) {
				app.changestate(app.LangueChoose);
			}
		}
		//----------------------------------------------------------------------------------
		for(int i = 0;i < bouton.length;i++) {
			bouton[i].update(MouseBox);
			if(bouton[i].iscolide() && clicsouris && selec == -1) {
				System.out.println(i);
				selec = i;
				break;
			}
		}
		if(selec >= 0 && selec < bouton.length) {
			bouton[selec].settext(Res.langue.getOptionBoutonText()[selec+2]+":");
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		fond.draw(0, 0);
		boutondefault.render();
		boutonretour.render();
		boutonlangue.render();
		boutonscreen.render();
		for(int i = 0;i < bouton.length;i++) {
			bouton[i].render();
		}
	}
	
	public void initextbouton() {
		for(int i = 0; i < stringmethode.length;i++) {
			try {
				settextkeybouton(i,(int) Parametre.class.getMethod(stringmethode[i].replaceFirst("s", "g"), new Class[] {}).invoke(Res.parametre));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setpara(int index, int value) {
		try {
			Parametre.class.getMethod(stringmethode[index], new Class[] {int.class}).invoke(Res.parametre, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void settextkeybouton(int i,int key) {
		bouton[i].settext(Res.langue.getOptionBoutonText()[i+2]+": "+Input.getKeyName(key));
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if(selec != -1) {
			if(key != Res.parametre.getesc()) {
				setpara(selec,key);
				selec = -1;
				Res.exportparametre();
			}
		}else {
			if(key == Res.parametre.getesc()) {
				app.changestate(app.Menu);
			}
		}
		App.keys(key,c);
	}

	@Override
	public int getID() {
		return id;
	}

	public void initext() {
		boutonretour.settext(Res.langue.getOptionBoutonText()[0]);
		boutonlangue.settext(Res.langue.getOptionBoutonText()[9]);
		if(fullscreen) boutonscreen.settext(Res.langue.getOptionBoutonText()[8]); else boutonscreen.settext(Res.langue.getOptionBoutonText()[7]);
		boutondefault.settext(Res.langue.getOptionBoutonText()[1]);	
	}
	
}
