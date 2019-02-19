package fr.tangv.takyo.game;
import java.io.File;
import java.util.ArrayList;

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
import fr.tangv.takyo.util.Tfont;

public class LangueChoose extends BasicGameState{

		private int id;
		private App app;
		private Input input;
		private Animation fond = Res.resource[2].getAnimation("background-option", 0);
		private Color bcolor = new Color(10,213,210);
		private ArrayList<String> namebouton = new ArrayList<>();
		private Bouton[] bouton;
		private Bouton boutondefault;
		private Bouton boutonretour;
		private int selec = -1;
		
		public LangueChoose(int id, App app) {
			this.id = id;
			this.app = app;
		}
		
		@Override
		public void init(GameContainer container, StateBasedGame game) throws SlickException {
			input = container.getInput();
			
			SpriteSheet bsprite = new SpriteSheet("res/img/bouton/bouton-menu.png", 224, 47);
			float[] bcord = {30f,30f,70f};
			int decalg = 0;
			float decalh = 0;
			
			File file = new File(App.chemin+"/langue");
			if(file.isDirectory()) {
				File[] files = file.listFiles();
				
				for(File f : files) {
					String path = f.getName();
					if(path.contains(".json")) {
						namebouton.add(path.replaceAll(".json",""));
					}
				}
				
				if(namebouton.size() > 36) {
					bouton = new Bouton[36];
				}else {
					bouton = new Bouton[namebouton.size()];
				}
				
				for(int i = 0; i < namebouton.size(); i++) {
					if(i == 36) {
						System.out.print("Tros de creations de bouton");
						break;
					}
					if(i == 9 || i == 18 || i == 27) {
						decalg += 254;
						decalh = bcord[2]*i;
					}
					bouton[i] = new Bouton(bsprite.getSprite(0, 1), bsprite.getSprite(0, 0),new float[] {bcord[0]+decalg, (bcord[1]+bcord[2]*i)-decalh},namebouton.get(i),bcolor);
				}
				
				boutonretour = new Bouton(bsprite.getSprite(0, 1), bsprite.getSprite(0, 0), new float[]{224*0+26*1,App.ecran[1]-(47f+30f)}, Res.langue.getOptionBoutonText()[0], bcolor);
				boutondefault = new Bouton(bsprite.getSprite(0, 1), bsprite.getSprite(0, 0), new float[]{224*3+26*4,App.ecran[1]-(47f+30f)}, Res.langue.getOptionBoutonText()[1], bcolor);	
			}
		}

		@Override
		public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
			Box MouseBox = new Box(new float[] {input.getMouseX(),input.getMouseY()});
			boutondefault.colide(MouseBox);
			boutonretour.colide(MouseBox);
			
			boolean clicsouris = input.isMousePressed(0);
			//----------------------------------------------------------------------------------
			if(clicsouris && selec == -1) {
				if(boutondefault.iscolide()) {
					setlangue(new Parametre().getlangue());
				}
				if(boutonretour.iscolide()) {
					app.changestate(app.Option);
				}
			}
			//----------------------------------------------------------------------------------
			for(int i = 0;i < bouton.length;i++) {
				bouton[i].update(MouseBox);
				if(bouton[i].iscolide() && clicsouris && selec == -1) {
					setlangue(namebouton.get(i));
					break;
				}
			}
		}
		
		private void setlangue(String langue) {
			Res.parametre.setlangue(langue);
			System.out.println("Langue définie: "+Res.parametre.getlangue());
			Res.importlangue(App.chemin+"/langue/"+Res.parametre.getlangue()+".json");
			((Jeux)app.getState(app.Jeux)).initext();
			((Menu)app.getState(app.Menu)).initext();
			((Option)app.getState(app.Option)).initext();
			((LangueChoose)app.getState(app.LangueChoose)).initext();
			Res.exportparametre();
		}
		
		@Override
		public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
			fond.draw(0, 0);
			boutondefault.render();
			boutonretour.render();
			for(int i = 0;i < bouton.length;i++) {
				bouton[i].render();
			}
			
			String text = Res.langue.getOptionBoutonText()[9]+" : "+Res.parametre.getlangue();
			int h = Tfont.fontbouton.getHeight(text);
			int w = Tfont.fontbouton.getWidth(text);
			Tfont.fontbouton.drawString((App.ecran[0]/2)-(w/2),(App.ecran[1]-(24f+30f))-(h/2),text,bcolor);
		}
		
		@Override
		public void keyPressed(int key, char c) {
			if(key == Res.parametre.getesc()) {
				app.changestate(app.Option);
			}
			App.keys(key,c);
		}

		@Override
		public int getID() {
			return id;
		}

		public void initext() {
			boutonretour.settext(Res.langue.getOptionBoutonText()[0]);
			boutondefault.settext(Res.langue.getOptionBoutonText()[1]);	
		}
		
	}

