package fr.tangv.takyo.objet;

import fr.tangv.takyo.colide.Box;
import fr.tangv.takyo.util.Res;

public class Item {

	private int id;
	private int idtype;
	private int sens;
	private String name;
	private float[] location;
	private float[] location2;
	
	public Item(int id,int idtype,String name) {
		this.id = id;
		this.name = name;
		this.sens = 0;
	}
	
	public Item(int id,int idtype,String name,float[] location) {
		this.id = id;
		this.name = name;
		this.sens = 0;
		setlocation(location);
	}
	
	public Box box() {
		return new Box(location,location2);
	}
	
	//--------------------------------------------------
	
	public void setlocation(float[] point) {
		location = point;
		location2 = new float[]{point[0] + 32, point[1] + 32};
	}
	
	public void setid(int id) {
		this.id = id;
	}
	
	public void setidtype(int idtype) {
		this.idtype = idtype;
	}
	
	public void setname(String name) {
		this.name = name;
	}
	
	public void setsens(int sens) {
		this.sens = sens;
	}
	
	//--------------------------------------------------
	
	public void render() {
		Res.resource[idtype].getAnimation(id,sens).draw(location[0],location[1]);
	}
	
	public void render(int sens) {
		Res.resource[idtype].getAnimation(id,sens).draw(location[0],location[1]);
	}
	
	public void render(float[] location) {
		Res.resource[idtype].getAnimation(id,sens).draw(location[0],location[1]);
	}
	
	public void render(float[] location, int sens) {
		Res.resource[idtype].getAnimation(id,sens).draw(location[0],location[1]);
	}
	
	//--------------------------------------------------
	
	public int getsens() {
		return sens;
	}
	
	public float[] getlocation() {
		return location;
	}
	
	public int getid() {
		return id;
	}
	
	public int getidtype() {
		return idtype;
	}
	
	public String getname() {
		return name;
	}
	
}
