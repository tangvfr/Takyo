package fr.tangv.takyo.objet;

import org.newdawn.slick.Input;

import fr.tangv.takyo.colide.Colide;
import fr.tangv.takyo.game.App;
import fr.tangv.takyo.game.Jeux;
import fr.tangv.takyo.util.Res;

public class MoveVoid {

	private long timeprog;
	private Jeux jeux;
	
	private char[] bl = {'A','A','A','A'};
	
	public MoveVoid(Jeux jeux) {
		this.jeux = jeux;
		this.timeprog = System.currentTimeMillis();
	}
	
	public void move(Input input,Map map,Entity hero) {
	    float movex = 0f;
		float movey = 0f;
		
		if(input.isKeyDown(Res.parametre.getavancer())) {
			hero.setsens(0);
			if(map.getblock(0,0).getlocation()[1] <= (App.ecran[1]/2)-33
			&& bl[0] != 'N' && bl[1] != 'N' && bl[2] != 'N' && bl[3] != 'N') {
				movey += 1;
			}
		}else if(input.isKeyDown(Res.parametre.getreculer())) {
			hero.setsens(2);
			if(map.getblock(map.getgrandeurmap()-1,map.getgrandeurmap()-1).getlocation()[1] >= (App.ecran[0]/2)-127
			&& bl[0] != 'S' && bl[1] != 'S' && bl[2] != 'S' && bl[3] != 'S') {
				movey += -1;
			}
		}else if(input.isKeyDown(Res.parametre.getdroit())) {
			hero.setsens(1);
			if(map.getblock(map.getgrandeurmap()-1,map.getgrandeurmap()-1).getlocation()[0] >= (App.ecran[0]/2) +1
			&& bl[0] != 'E' && bl[1] != 'E' && bl[2] != 'E' && bl[3] != 'E') {
				movex += -1;
			}
		}else if(input.isKeyDown(Res.parametre.getgauche())) {
			hero.setsens(3);
			if(map.getblock(0,0).getlocation()[0] <= (App.ecran[0]/2)-33
			&& bl[0] != 'O' && bl[1] != 'O' && bl[2] != 'O' && bl[3] != 'O') {
				movex += 1;
			}
		}
		map.addpointcentre(movex, movey);
		map.setlocationallmap();
		
		bl = new char[] {'A','A','A','A'};
		int[] loc = map.getblockloc(hero.getlocation()[0], hero.getlocation()[1]);
		movex = 0f;
		movey = 0f;
		
		for(int dx = -1; dx <= 3; dx++) {
			for(int dy = -1; dy <= 3; dy++) {
				if(map.blockvalid(loc[0]+dx,loc[1]+dy)) {
					Colide h = BlockVoid.colide(map.getblock(loc[0]+dx,loc[1]+dy),hero.getBox());
					//--------------------
					if(h.iscolide()) {
						for(int I = 0;I < 4;I++) {
							if(bl[I] == h.getsens()) {
								break;
							}
							if(bl[I] == 'A') {
								bl[I] = h.getsens();
								switch (bl[I]) {
									case 'N':
										if(map.getblock(map.getgrandeurmap()-1,map.getgrandeurmap()-1).getlocation()[1] >= (App.ecran[0]/2)-128){
											movey -= 1;
										}
										break;
									case 'S':
										if(map.getblock(0,0).getlocation()[1] <= (App.ecran[1]/2)-32) {
											movey += 1;
										}
										break;
									case 'E':
										if(map.getblock(0,0).getlocation()[0] <= (App.ecran[0]/2)-32) {
											movex += 1;
										}
										break;
									case 'O':
										if(map.getblock(map.getgrandeurmap()-1,map.getgrandeurmap()-1).getlocation()[0] >= (App.ecran[0]/2)) {
											movex -= 1;
										}
										break;
									
									default:
										break;
								}
								break;
							}
						}
					}
					//-------------------
				}
			}
		}
		map.addpointcentre(movex, movey);
		map.setlocationallmap();
	}
	 public void update(int time) {
		 if(System.currentTimeMillis() >= timeprog) {
			this.timeprog = System.currentTimeMillis()+time;
			if(!jeux.getgamemode().getinv().getopen()) {
				move(jeux.getinput(), jeux.getmap(), jeux.gethero());
			}
		}
	 }
}
