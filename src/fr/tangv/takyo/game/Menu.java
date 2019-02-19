package fr.tangv.takyo.game;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
import fr.tangv.takyo.game.Jeux;
import fr.tangv.takyo.gamemode.Gamemode;
import fr.tangv.takyo.objet.Bouton;
import fr.tangv.takyo.objet.Map;
import fr.tangv.takyo.util.Res;

public class Menu extends BasicGameState {

	private int id;
	private App app;
	private Input input;
	private Animation fond = Res.resource[2].getAnimation(0, 0);
	
	private Bouton[] bouton = new Bouton[3];
	
	public Menu(int id, App app) {
		this.id = id;
		this.app = app;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		input = container.getInput();
		
		//------------- Bouton
		SpriteSheet bsprite = new SpriteSheet("res/img/bouton/bouton-menu.png", 224, 47);
		float[] bcord = {30f,200f,70f};
		Color bcolor = new Color(10,213,210);
		
		for(int i = 0;i < bouton.length;i++) {
			bouton[i] = new Bouton(bsprite.getSprite(0, 1), bsprite.getSprite(0, 0),new float[] {bcord[0], bcord[1]+bcord[2]*i},"",bcolor);
		}
		
		bouton[0].settext(Res.langue.getMenuBouton()[0]);
		bouton[1].settext(Res.langue.getMenuBouton()[1]);
		bouton[2].settext(Res.langue.getMenuBouton()[2]);
		//------------- FinBouton
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Box MouseBox = new Box(new float[] {input.getMouseX(),input.getMouseY()});
		
		for(int i = 0;i < bouton.length;i++) {
			bouton[i].update(MouseBox);
		}
		
		if(input.isKeyDown(Input.KEY_LCONTROL) && input.isKeyDown(Input.KEY_LALT) && input.isKeyPressed(Input.KEY_SLASH) && input.isKeyDown(Input.KEY_SLASH)) {
			try {
				if(!createmap()) {
					cmmsg();
				}
			}catch(Exception e) {
				cmmsg();
			}
		}
	}
	
	private void cmmsg() {
		JOptionPane.showMessageDialog(null, "Erreur la map n'a pu être crée !", "Creation Map", JOptionPane.ERROR_MESSAGE);
	}
	
	private boolean createmap() {
		int rep = JOptionPane.showConfirmDialog(null, "Crée une nouvelle map", "Creation Map", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(rep == JOptionPane.YES_OPTION) {
			int grandeurmap = Integer.parseInt(JOptionPane.showInputDialog(null, "Grandeur de la map", "Creation Map", JOptionPane.QUESTION_MESSAGE));
			String name = JOptionPane.showInputDialog(null, "Non de la map", "Creation Map", JOptionPane.QUESTION_MESSAGE);
			JFileChooser jfc = new JFileChooser(new File(System.getProperty("user.home")));
			if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && JOptionPane.showConfirmDialog(null, "Crée une nouvelle map:\nName: "+name+"\nGrandeurMap: "+grandeurmap+"\nChemin du fichier: "+jfc.getSelectedFile().getPath(), "Creation Map", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				try {
					int blockid = Integer.parseInt(JOptionPane.showInputDialog(null, "L'id du block de fond","Creation Map", JOptionPane.QUESTION_MESSAGE));
					Jeux jeux = (Jeux) app.getState(app.Jeux); 
					input.isKeyPressed(Input.KEY_J);
					jeux.setgamemode(Gamemode.creatif());
					jeux.setmap(new Map(Jeux.grandeurblock, grandeurmap, name,blockid));
					Map.exportmap(jeux.getmap(), jfc.getSelectedFile().getPath());
					app.changestate(app.Jeux);
				}catch (Exception e) {
					return false;
				}
			}else {
				return true;
			}
		}else if(rep == JOptionPane.NO_OPTION) {
			JFileChooser jfc = new JFileChooser(new File(System.getProperty("user.home")));
			if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				Jeux jeux = (Jeux) app.getState(app.Jeux); 
				input.isKeyPressed(Input.KEY_J);
				jeux.setgamemode(Gamemode.creatif());
				Map.importmap(jeux.getmap(), jfc.getSelectedFile().getPath());
				app.changestate(app.Jeux);
			}else {
				return true;
			}
		}else {
			return true;
		}
		return true;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		fond.draw(0,0);
		
		for(int i = 0;i < bouton.length;i++) {
			bouton[i].render();
		}
	}
	
	@Override
	public void mouseClicked(int intbouton, int x, int y, int clickCount) {
		if(bouton[0].iscolide() && intbouton == 0) {
			Jeux jeux = (Jeux) app.getState(app.Jeux);
			Map.load(jeux.getmap(),"mapdemarage");
			input.isKeyPressed(Input.KEY_J);
			jeux.setgamemode(Gamemode.survie());
			app.changestate(app.Jeux);
		}
		
		if(bouton[1].iscolide() && intbouton == 0) {
			app.changestate(app.Option);
		}
		
		if(bouton[2].iscolide() && intbouton == 0) {
			app.exit();
		}
	}

	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public void keyPressed(int key, char c) {
		App.keys(key,c);
	}
	
	public void initext() {
		bouton[0].settext(Res.langue.getMenuBouton()[0]);
		bouton[1].settext(Res.langue.getMenuBouton()[1]);
		bouton[2].settext(Res.langue.getMenuBouton()[2]);
	}
	
}
