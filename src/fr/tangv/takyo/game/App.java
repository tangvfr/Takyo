package fr.tangv.takyo.game;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.tangv.takyo.util.Res;
import fr.tangv.takyo.util.Tfont;

public class App extends StateBasedGame {
	public final int Menu = 0;
	public final int Jeux = 1;
	public final int Option = 2;
	public final int LangueChoose = 3;
	public static final int[] ecran = {1024,768};
	public static final String chemin = System.getProperty("user.home") + "/Takyo/";
	public static final String cheminfont = "res/font/AldotheApache.ttf";
	public static final String cheminparametre = chemin+"Parametre.json";
	public static AppGameContainer agc;
	
	public void iniall() {
		Tfont.iniFont();
		Res.iniRes();
		File filetakyo = new File(chemin);
		if(!filetakyo.isDirectory()) {
			filetakyo.mkdir();
		}
		File filelangue = new File(chemin+"/langue");
		if(!filelangue.isDirectory()) {
			filelangue.mkdir();
		}
		Res.importparametre();
		if(Res.importlangue(chemin+"/langue/"+Res.parametre.getlangue()+".json") == false) Res.exportlangue(chemin+"/langue/fr.json");
	}
	
	public App(String name) {
		super(name);
		try {
			agc = new AppGameContainer(this);
			agc.setDisplayMode(App.ecran[0],App.ecran[1], false);
			agc.setShowFPS(false);
			agc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void keys(int key, char c) {
		if(key == Res.parametre.getpoint()) {
			System.out.println(agc.getInput().getMouseX() + " | " + agc.getInput().getMouseY());
		}
		if(agc.getInput().isKeyDown(Input.KEY_LCONTROL) && agc.getInput().isKeyDown(Input.KEY_LALT) && agc.getInput().isKeyPressed(Input.KEY_GRAVE) && agc.getInput().isKeyDown(Input.KEY_GRAVE)) {
			agc.setShowFPS(!agc.isShowingFPS());
		}
	}
	
	public void exit() {
		agc.destroy();
		System.exit(0);
	}
	
	public void changestate(int nb) {
		this.enterState(nb);
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		try {
			container.setMouseCursor(new Image("res/img/gui/cursor.png"), 0, 0);
		}catch (Exception e) {}
		iniall();
		
		this.addState(new Menu(Menu,this));
		this.addState(new Jeux(Jeux,this));
		this.addState(new Option(Option,this));
		this.addState(new LangueChoose(LangueChoose,this));
		
		Music music = new Music("res/son/One Sly Move.ogg");
		music.loop(1, 0.40f);
	}

}
