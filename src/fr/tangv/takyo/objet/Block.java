package fr.tangv.takyo.objet;

public class Block {

	private float[] location;
	private float[] location2;
	private int grandeurblock;
	private int idblock;
	private int sens;
	private boolean solide;
	
	//--------------------------------------------------
	
	public void setidblock(int idblock) {
		this.idblock = idblock;
	}
	
	public void setsolide(boolean solide) {
		this.solide = solide;
	}
	
	public void setsens(int sens) {
		this.sens = sens; 
	}
	
	public void setgrandeurblock(int grandeurblock) {
		this.grandeurblock = grandeurblock;
	}
	
	public void setlocation(float[] location) {
		this.location = location;
	}
	
	public void setlocation2(float[] location2) {
		this.location2 = location2;
	}
	
	//--------------------------------------------------
	
	public int getidblock() {
		return idblock;
	}
	
	public boolean getsolide() {
		return solide;
	}
	
	public int getsens() {
		return sens;
	}
	
	public int getgrandeurblock() {
		return grandeurblock;
	}
	
	public float[] getlocation() {
		return location;
	}
	
	public float[] getlocation2() {
		return location2;
	}
	
}
