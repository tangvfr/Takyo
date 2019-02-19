package fr.tangv.takyo.objet;

import java.io.File;
import java.io.InputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.ResourceLoader;

import fr.tangv.takyo.game.App;
import fr.tangv.takyo.game.Jeux;
import fr.tangv.takyo.util.Res;

public class Map {
	
	private MapSave mapsave = new MapSave();
	
	public Map(int grandeurblock,int grandeurmap, String name,int idblock) {
		mapsave.setname(name);
		mapsave.setpointcentre(new float[]{0,0});
		mapsave.setgrandeurblock(grandeurblock);
		mapsave.setgrandeurmap(grandeurmap);
		mapsave.setoriginemap((mapsave.getgrandeurmap()*mapsave.getgrandeurblock())/-2);
		mapsave.setmap(new Block[grandeurmap][grandeurmap]);
		
		for(int X = 0; X < grandeurmap; X++) {
			for(int Y = 0; Y < grandeurmap; Y++) {
				mapsave.getmap()[X][Y] = BlockVoid.createBlock(idblock, new float[] {0,0}, grandeurblock);
			}
		}
	}
	
	public static boolean inscreen(float x,float y,int taile) {
		if(x >= -taile && y >= -taile && x <= App.ecran[0] && y <= App.ecran[1]){
			return true;
		}
		return false;
	}
	
	public static void exportmap(Map map, String chemin) {
		ObjectMapper om = new ObjectMapper();
		try {
			File file = new File(chemin);
			if(!file.exists()) {
				file.createNewFile();
			}
			om.writeValue(file, map.getmapsave());
			System.out.println("Export Ok");
		} catch (Exception e) {
			System.out.println("Error");
		}
	}
	
	public static void importmap(Map map, String chemin) {
		ObjectMapper om = new ObjectMapper();
		try {
			File file = new File(chemin);
			if(!file.exists()) {
				file.createNewFile();
			}
			MapSave mapsave = om.readValue(file, MapSave.class);
			map.setMapSave(mapsave);
		} catch (Exception e) {
			System.out.println("Error Import Map");
		}
	}
	
	public static void importmap(Map map, InputStream in) {
		ObjectMapper om = new ObjectMapper();
		try {
			MapSave mapsave = om.readValue(in, MapSave.class);
			map.setMapSave(mapsave);
		} catch (Exception e) {
			System.out.println("Error Import Map");
		}
	}
	
	public static void importmap(String don,Map map) {
		ObjectMapper om = new ObjectMapper();
		try {
			MapSave mapsave = om.readValue(don, MapSave.class);
			map.setMapSave(mapsave);
		} catch (Exception e) {
			System.out.println("Error Import Map");
		}
	}
	
	private final int[] divec = new int[]{App.ecran[0]/2,App.ecran[1]/2};
	public void render(Graphics g, boolean ModeDev) {
		int loc[] = getblockloc(divec[0], divec[1]);
		for(int X = loc[0]-16; X < loc[0]+17; X++) {
			for(int Y = loc[1]-12; Y < loc[1]+13; Y++) {
				if(0 <= X && X < this.getgrandeurmap() && 0 <= Y && Y < this.getgrandeurmap()) {
					if(Map.inscreen(this.getblock(X,Y).getlocation()[0], this.getblock(X,Y).getlocation()[1], mapsave.getgrandeurblock())){
						Res.resource[0].getAnimation(this.getblock(X,Y).getidblock(),this.getblock(X,Y).getsens()).draw(this.getblock(X,Y).getlocation()[0], this.getblock(X,Y).getlocation()[1]);
						if(ModeDev) {
							if(getblock(X, Y).getsolide()) {
								g.setColor(Color.magenta);
							}else {
								g.setColor(Color.lightGray);
							}
							g.drawRect(getblock(X, Y).getlocation()[0], getblock(X, Y).getlocation()[1], getblock(X,Y).getgrandeurblock()-1, getblock(X,Y).getgrandeurblock()-1);
						}
					}
				}
			}
		}
	}
	
	public void setlocationallmap() {
		BlockVoid.setlocation(mapsave.getmap()[0][0],new float[] {repere(0) + 0*mapsave.getgrandeurblock(), repere(1) + 0*mapsave.getgrandeurblock()});
		BlockVoid.setlocation(mapsave.getmap()[(this.getgrandeurmap()-1)][(this.getgrandeurmap()-1)],new float[] {repere(0) + (this.getgrandeurmap()-1)*mapsave.getgrandeurblock(), repere(1) + (this.getgrandeurmap()-1)*mapsave.getgrandeurblock()});
		if(Jeux.blockref.size() > 0) {
			for(int[] i : Jeux.blockref) {
				BlockVoid.setlocation(mapsave.getmap()[i[0]][i[1]],new float[] {repere(0) + i[0]*mapsave.getgrandeurblock(), repere(1) + i[1]*mapsave.getgrandeurblock()});
			}
		}
		int loc[] = getblockloc(divec[0], divec[1]);
		for(int X = loc[0]-16; X < loc[0]+17; X++) {
			for(int Y = loc[1]-12; Y < loc[1]+13; Y++) {
				if(0 <= X && X < this.getgrandeurmap() && 0 <= Y && Y < this.getgrandeurmap()) {
					BlockVoid.setlocation(mapsave.getmap()[X][Y],new float[] {repere(0) + X*mapsave.getgrandeurblock(), repere(1) + Y*mapsave.getgrandeurblock()});
				}
			}
		}
	}
	
	public void addpointcentre(float x, float y) {
		mapsave.setpointcentre(new float[] {mapsave.getpointcentre()[0] + x, mapsave.getpointcentre()[1] + y});
	}
	
	public void setpointcentre(float x, float y) {
		mapsave.setpointcentre(new float[]{x, y});
	}
	
	public int[] getblockloc(float x, float y) {
		float sx = repere(0) + mapsave.getgrandeurblock()/2;
		float sy = repere(1) + mapsave.getgrandeurblock()/2;
		int[] r = {Math.round((x-sx)/mapsave.getgrandeurblock()),Math.round((y-sy)/mapsave.getgrandeurblock())};
		return r;
	}
	
	public float repere(int i) {
		return mapsave.getpointcentre()[i] + mapsave.getoriginemap() + App.ecran[i]/2;
	}
	
	public float[] getcoordloc(float coordx,float coordy) {
		return new float[]{-coordx + mapsave.getpointcentre()[0] + App.ecran[0]/2, -coordy + mapsave.getpointcentre()[1] + App.ecran[1]/2};
	}
	
	public float[] getcoordmap(float coordx,float coordy) {
		return new float[]{-(coordx - (mapsave.getpointcentre()[0] + App.ecran[0]/2)), -(coordy - (mapsave.getpointcentre()[1] + App.ecran[1]/2))};
	}
	
	public float[] coordhero() {
		return new float[] {mapsave.getpointcentre()[0]+32,mapsave.getpointcentre()[1]+32};
	}
	
	public boolean blockvalid(int x,int y) {
		if(!(x < 0 || x > mapsave.getgrandeurmap()-1 || y < 0 || y > mapsave.getgrandeurmap()-1)) {
			return true;
		}
		return false;
	}
	
	public Block getblock(int x,int y) {
		if(blockvalid(x, y)) {
			return mapsave.getmap()[x][y];
		}
		return mapsave.getmap()[0][0];
	}
	
	public int getgrandeurmap() {
		return mapsave.getgrandeurmap();
	}
	
	public int getgrandeurblock() {
		return mapsave.getgrandeurblock();
	}
	
	//-------------------------------------
	
	public void setMapSave(MapSave mapsave) {
		this.mapsave = mapsave;
	}
	
	public MapSave getmapsave() {
		return mapsave;
	}

	public static void load(Map map, String string) {
		Map.importmap(map, ResourceLoader.getResourceAsStream("res/map/"+string+".json"));
	}
	
}
