package fr.tangv.takyo.game;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.tangv.takyo.gamemode.Gamemode;
import fr.tangv.takyo.objet.Entity;
import fr.tangv.takyo.objet.Map;
import fr.tangv.takyo.objet.MoveVoid;
import fr.tangv.takyo.util.Res;

public class Jeux extends BasicGameState {
	
	public static final int grandeurblock = 32;
	private int id;
	private boolean ModeDev = false;
	private Animation fond = Res.resource[2].getAnimation(4, 0);
	private App app;
	private Input input;
	private Map map;
	private MoveVoid movevoid;
	public static ArrayList<int[]> blockref = new ArrayList<int[]>();
	
	//---------------------------
	
	private Entity hero;
	private Gamemode gamemode;
	
	//---------------------------
	
	public Jeux(int id,App app) {
		this.app = app;
		this.id = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		input = container.getInput();
		
		map = new Map(grandeurblock, 30, "test",1);
		gamemode = Gamemode.survie();
		
		Image imgh = new Image("res/img/entity/hero.png");
		hero = new Entity(imgh, new float[] {App.ecran[0]/2 - 32, App.ecran[1]/2 - 32}, 64, 64);
		
		movevoid = new MoveVoid(this);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		movevoid.update(15);
		gamemode.update(this);
		if(input.isKeyDown(Input.KEY_LCONTROL) && input.isKeyDown(Input.KEY_LALT) && input.isKeyPressed(Input.KEY_SLASH) && input.isKeyDown(Input.KEY_SLASH)) {
			ModeDev = !ModeDev;
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		fond.draw(0, 0);
		map.render(g,ModeDev);
		hero.render(g,ModeDev);
		gamemode.render(g, container,this);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if(key == Res.parametre.getesc()) {
			if(gamemode.getinv().getopen()) {
				gamemode.getinv().setopen(false);
			}else {
				if(gamemode.equal(Gamemode.creatif())) {
					JFileChooser jfc = new JFileChooser(new File(System.getProperty("user.home")));
					if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						Map.exportmap(map, jfc.getSelectedFile().getPath());
					}
				}
				app.changestate(app.Menu);
			}
		}
		
		if(key == Res.parametre.getexporter() && gamemode.equal(Gamemode.creatif())) {
			JFileChooser jfc = new JFileChooser(new File(System.getProperty("user.home")));
			if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				Map.exportmap(map, jfc.getSelectedFile().getPath());
			}else {
				return;
			}
		}
		App.keys(key,c);
	}
	
	public Input getinput() {
		return input;
	}
	
	public Entity gethero() {
		return hero;
	}
	
	public Map getmap() {
		return map;
	}
	
	public void setmap(Map map) {
		this.map = map;
	}
	
	public Gamemode getgamemode() {
		return gamemode;
	}
	
	public void setgamemode(Gamemode gamemode) {
		this.gamemode = gamemode;
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	public void initext() {
		
	}

}
