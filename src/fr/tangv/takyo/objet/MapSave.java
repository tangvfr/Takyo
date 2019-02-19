package fr.tangv.takyo.objet;

public class MapSave {
	
	private int grandeurmap;
	private int grandeurblock;
	private int originemap;
	private float[] pointcentre;
	private String name;
	
	private Block[][] map;
	
	//-----------------------------------
	
	public int getgrandeurmap() {
		return grandeurmap;
	}
	
	public int getgrandeurblock() {
		return grandeurblock;
	}
	
	public int getoriginemap() {
		return originemap;
	}
	
	public float[] getpointcentre() {
		return pointcentre;
	}
	
	public String getname() {
		return name;
	}
	
	public Block[][] getmap() {
		return map;
	}
	
	//-----------------------------------
	
	public void setgrandeurmap(int grandeurmap) {
		this.grandeurmap = grandeurmap;
	}
	
	public void setgrandeurblock(int grandeurblock) {
		this.grandeurblock = grandeurblock;
	}
	
	public void setoriginemap(int originemap) {
		this.originemap = originemap;
	}
	
	public void setpointcentre(float[] pointcentre) {
		this.pointcentre = pointcentre;
	}
	
	public void setname(String name) {
		this.name = name;
	}
	
	public void setmap(Block[][] map) {
		this.map = map;
	}
}
