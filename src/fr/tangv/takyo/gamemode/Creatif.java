package fr.tangv.takyo.gamemode;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import fr.tangv.takyo.colide.Box;
import fr.tangv.takyo.colide.Colide;
import fr.tangv.takyo.game.App;
import fr.tangv.takyo.game.Jeux;
import fr.tangv.takyo.objet.Block;
import fr.tangv.takyo.objet.Item;
import fr.tangv.takyo.util.Res;
import fr.tangv.takyo.util.Tfont;

public class Creatif extends Gamemode{
	
	public static boolean changeitemselec = false;
	private boolean optionsolide = false;
	private int optionsens = 0;
	private Box boxbutonsolide = new Box(App.ecran[0]-145,31,65,33);
	private Box boxbutonsens = new Box(App.ecran[0]-64,32,32,32);
	private int[] pointblockzone = new int[] {0,0,0};
	
	public Creatif(int type) {
		super(type);
		for(int i = 0;i < inv.getmax() && i < Res.resource[0].lenght();i++) {
			inv.setitem(i,new Item(i,0, Res.resource[0].getname(i),inv.getloc(i)));
		}
		inv.setitemselec(inv.getitem(0));
	}
	
	public void update(Jeux jeux) {
		Input input = jeux.getinput();
		super.update(jeux);
		Box MouseBox = new Box(new float[] {input.getMouseX(),input.getMouseY()});
		Colide colideso = boxbutonsolide.colide(MouseBox);
		Colide colidese = boxbutonsens.colide(MouseBox);
		
		if(input.isKeyPressed(Input.KEY_O)) {
			int x = App.ecran[0]/2-input.getMouseX();
			int y = App.ecran[1]/2-input.getMouseY();
			jeux.getmap().addpointcentre(x, y);
			jeux.getmap().setlocationallmap();
		}
		
		if(input.isKeyPressed(Input.KEY_I)) {
			jeux.getmap().setpointcentre(0, 0);
		}
		
		if(super.inv.getopen() || mouseclick.isMouseClick(0) || mouseclick.isMouseClick(1) || mouseclick.isMouseClick(2) || input.isKeyPressed(Input.KEY_H)) {
			pointblockzone = new int[] {0,0,0};
		}
		
		int[] b = jeux.getmap().getblockloc(input.getMouseX(), input.getMouseY());
		if(input.isKeyPressed(Input.KEY_J)) {
			if(pointblockzone[0] != 1) {
				pointblockzone = new int[] {1,b[0],b[1]};
			}else {
				int minx;
				int maxx;
				if(pointblockzone[1] > b[0]) {
					minx = b[0];
					maxx = pointblockzone[1];
				}else {
					minx = pointblockzone[1];
					maxx = b[0];
				}
				int miny;
				int maxy;
				if(pointblockzone[2] > b[1]) {
					miny = b[1];
					maxy = pointblockzone[2];
				}else {
					miny = pointblockzone[2];
					maxy = b[1];
				}
				for(int x = minx;x <= maxx;x++) {
					for(int y = miny;y <= maxy;y++) {
						jeux.getmap().getblock(x, y).setidblock(inv.getitemselec().getid());
						jeux.getmap().getblock(x, y).setsens(optionsens);
						jeux.getmap().getblock(x, y).setsolide(optionsolide);
					}
				}
				pointblockzone = new int[] {0,0,0};
			}
		}
		
		if(input.isKeyPressed(Input.KEY_N)) {
			if(pointblockzone[0] != 2) {
				pointblockzone = new int[] {2,b[0],b[1]};
			}else {
				int minx;
				int maxx;
				if(pointblockzone[1] > b[0]) {
					minx = b[0];
					maxx = pointblockzone[1];
				}else {
					minx = pointblockzone[1];
					maxx = b[0];
				}
				int miny;
				int maxy;
				if(pointblockzone[2] > b[1]) {
					miny = b[1];
					maxy = pointblockzone[2];
				}else {
					miny = pointblockzone[2];
					maxy = b[1];
				}
				for(int x = minx;x <= maxx;x++) {
					jeux.getmap().getblock(x, miny).setidblock(inv.getitemselec().getid());
					jeux.getmap().getblock(x, miny).setsens(optionsens);
					jeux.getmap().getblock(x, miny).setsolide(optionsolide);
					jeux.getmap().getblock(x, maxy).setidblock(inv.getitemselec().getid());
					jeux.getmap().getblock(x, maxy).setsens(optionsens);
					jeux.getmap().getblock(x, maxy).setsolide(optionsolide);
				}
				for(int y = miny;y <= maxy;y++) {
					jeux.getmap().getblock(minx, y).setidblock(inv.getitemselec().getid());
					jeux.getmap().getblock(minx, y).setsens(optionsens);
					jeux.getmap().getblock(minx, y).setsolide(optionsolide);
					jeux.getmap().getblock(maxx, y).setidblock(inv.getitemselec().getid());
					jeux.getmap().getblock(maxx, y).setsens(optionsens);
					jeux.getmap().getblock(maxx, y).setsolide(optionsolide);
				}
				pointblockzone = new int[] {0,0,0};
			}
		}
		
		if(pointblockzone[0] > 0) {
			Jeux.blockref.add(new int[] {pointblockzone[1],pointblockzone[2]});
		}
		
		if(changeitemselec) {
			optionsens = 0;
			changeitemselec = false;
		}
		
		if(inv.getopen()) {
			if(colideso.iscolide() && mouseclick.isMouseClick(0)) {
				optionsolide = !optionsolide;
			}
			if(colidese.iscolide() && mouseclick.isMouseClick(0)) {
				optionsens++;
				if(optionsens >= 4) optionsens = 0;
			}
		}
		
		if(mouseclick.isMouseClick(2) && !inv.getopen()) {
			Block block = jeux.getmap().getblock(b[0], b[1]);
			inv.setitemselec(inv.getitem(block.getidblock()));
			optionsolide = block.getsolide();
			optionsens = block.getsens();
		}
		
		if(jeux.getmap().blockvalid(b[0],b[1]) && !inv.getopen()) {
			if(mouseclick.isMouseClick(1)) {
				int sens = jeux.getmap().getblock(b[0], b[1]).getsens();
				if(sens < 3) {
					sens++;
				}else {
					sens = 0;
				}
				jeux.getmap().getblock(b[0], b[1]).setsens(sens);
			}else if((mouseclick.isMouseClick(0) || input.isKeyDown(Input.KEY_L)) && inv.getitemselec() != null){
				jeux.getmap().getblock(b[0], b[1]).setidblock(inv.getitemselec().getid());
				jeux.getmap().getblock(b[0], b[1]).setsens(optionsens);
				jeux.getmap().getblock(b[0], b[1]).setsolide(optionsolide);
			}
		}
		inv.update(input);
	}
	
	public void render(Graphics g, GameContainer container,Jeux jeux) {
		super.render(g, container,jeux);
		if(inv.getopen()) {
			String solide = "Solide";
			Color color = Color.magenta;
			if(!optionsolide) {
				solide = "Gaz";
				color = Color.lightGray;
			}
			g.setColor(color);
			g.drawRect(App.ecran[0]-145,31,65,33);
			Tfont.fonttext1.drawString(App.ecran[0]-112-Tfont.fonttext1.getWidth(solide)/2, 48-Tfont.fonttext1.getHeight(solide)/2, solide,color);
			Res.resource[0].getAnimation(inv.getitemselec().getid(), optionsens).draw(App.ecran[0]-64,32);
			g.drawRect(App.ecran[0]-65,31,33,33);
		}
		if(!inv.getopen()) {
			if(inv.getitemselec() != null) {
				float[] coord = {App.ecran[0]-toolbarimg.getWidth()+8,8};
				inv.getitemselec().render(coord,optionsens);
			}
		}
		if(container.isShowingFPS()) {
			g.resetFont();
			g.setColor(Color.white);
			g.drawString(Res.langue.gettextinfokey(),10, 30);
		}
		if(pointblockzone[0] > 0) {
			g.setColor(Color.yellow);
			if(pointblockzone[0] == 2) {
				g.setColor(Color.cyan);
			};
			Block block = jeux.getmap().getblock(pointblockzone[1], pointblockzone[2]);
			float[] coord = block.getlocation();
			g.fillRect(coord[0]+11, coord[1]+11, 10, 10);
			g.drawRect(coord[0], coord[1], 31, 31);
			int[] loc = jeux.getmap().getblockloc(jeux.getinput().getMouseX(), jeux.getinput().getMouseY());
			Block block2 = jeux.getmap().getblock(loc[0],loc[1]);
			float[] coord2 = block2.getlocation();
			g.fillRect(coord2[0]+11, coord2[1]+11, 10, 10);
			g.drawRect(coord2[0], coord2[1], 31, 31);
			g.drawRect(coord[0]+16, coord[1]+16, -(coord[0]-coord2[0]), -(coord[1]-coord2[1]));
			g.drawLine(coord[0]+16, coord[1]+16,coord2[0]+16, coord2[1]+16);
		}
	}
}
