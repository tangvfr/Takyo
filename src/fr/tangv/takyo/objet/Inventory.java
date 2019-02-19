package fr.tangv.takyo.objet;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

import fr.tangv.takyo.colide.Box;
import fr.tangv.takyo.colide.Colide;
import fr.tangv.takyo.game.App;
import fr.tangv.takyo.gamemode.Creatif;
import fr.tangv.takyo.util.MouseClick;
import fr.tangv.takyo.util.Res;
import fr.tangv.takyo.util.Tfont;

public class Inventory {

	private final int max = 84;
	private Item[] item = new Item[84];
	private int[] dim = {336,768};
	private boolean open = false;
	private Item itemselec;
	private final float[] locitemselec = {coord()[0]+32,32};
	private String name;
	private MouseClick mouseclick = new MouseClick();
	private TrueTypeFont fonttext = Tfont.fonttext1;
	
	public Inventory(String name) {
		this.name = name;
	}

	public void update(Input input) {
		mouseclick.update(input);
		if(mouseclick.isMouseClick(Input.MOUSE_LEFT_BUTTON)) {
			Box MouseBox = new Box(new float[]{input.getMouseX(),input.getMouseY()});
			if(getopen()) {
				if(!MouseBox.colide(new Box(coord(), new float[]{App.ecran[0],App.ecran[1]})).iscolide()) {
					setopen(false);
					return;
				}
				int[] colid = colide(MouseBox);
				if(colid[0] == 1 && item[colid[1]] != null) {
					itemselec = item[colid[1]];
					Creatif.changeitemselec = true;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(this.getopen()) {
			this.getimg().draw(this.coord()[0],this.coord()[1]);
			for(int i = 0;i < max;i++) {
				if(item[i] != null) {
					item[i].render();
				}
			}
			if(itemselec != null){
				itemselec.render(locitemselec);
				fonttext.drawString(locitemselec[0]+44,locitemselec[1]+16-fonttext.getHeight(itemselec.getname())/2,itemselec.getname(),new Color(255f, 255f, 255f));
			}
		}
	}
	
	public float[] getloc(int index) {
		int lines = 0;
		while(index > 5) {
			index -= 6;
			lines++;
		}
		if(lines >= 14) {
			return new float[] {App.ecran[0]/2-16,App.ecran[1]/2-16};
		}
		return new float[] {(App.ecran[0] - dim[0] +16 + ((32+16)*index)) ,96+((32+16)*lines)};
	}
	
	public int[] colide(Box box) {
		for(int i = 0;i < max && item[i] != null;i++) {
			Colide colide = item[i].box().colide(box);
			if(colide.iscolide()) {
				return new int[] {1,i};
			}
		}
		return new int[] {0,0};
	}
	
	public Animation getimg() {
		return Res.resource[2].getAnimation(2, 0);
	}
	
	public float[] coord() {
		return new float[]{App.ecran[0]-dim[0],0};
	}
	
	public boolean getopen() {
		return open;
	}
	
	public void setopen(boolean open) {
		this.open = open;
	}
	
	public void setiopen() {
		open = !open;
	}
	
	public Item getitemselec() {
		return itemselec;
	}
	
	public void setitemselec(Item item) {
		itemselec = item;
	}
	
	public int getmax() {
		return max;
	}
	
	public String getname() {
		return name;
	}
	
	public void setname(String name) {
		this.name = name;
	}
	
	public Item getitem(int index) {
		if(index >= max) {
			return this.item[0];
		}
		return this.item[index];
	}
	
	public void setitem(int index, Item item) {
		if(index >= max) {
			this.item[0] = item;
		}
		this.item[index] = item;
	}
	
	public void clear() {
		for(int i = 0;i < max;i++) {
			this.item[i] = null;
		}
	}
	
	public void clearitem(int id,int idtype) {
		for(int i = 0;i < max;i++) {
			if(this.item[i] != null && this.item[i].getid() == id && this.item[i].getidtype() == idtype) {
				this.item[i] = null;
			}
		}
	}
	
	public void clearitem(String name) {
		for(int i = 0;i < max;i++) {
			if(this.item[i] != null && this.item[i].getname() == name) {
				this.item[i] = null;
			}
		}
	}
	
	public void additem(Item item) {
		for(int i = 0;i < max;i++) {
			if(this.item[i] == null) {
				this.item[i] = item;
				return;
			}
		}
		System.out.println("Inventory full");
	}
	
	public void removeitem(int id,int idtype,int nb) {
		for(int i = 0;i < max;i++) {
			if(this.item[i] != null && this.item[i].getid() == id && this.item[i].getidtype() == idtype && nb > 0) {
				this.item[i] = null;
				nb--;
			}
			if(nb <= 0) {
				break;
			}
		}
	}
	
	public void removeitem(String name,int nb) {
		for(int i = 0;i < max;i++) {
			if(this.item[i] != null && this.item[i].getname() == name && nb > 0) {
				this.item[i] = null;
				nb--;
			}
			if(nb <= 0) {
				break;
			}
		}
	}
	
	public boolean gethaveitem(int id,int idtype,int nb) {
		int nombre = 0;
		for(int i = 0;i < max;i++) {
			if(this.item[i] != null && this.item[i].getid() == id && this.item[i].getidtype() == idtype) {
				nombre++;
			}
		}
		return nombre <= nb;
	}
	
	public boolean gethaveitem(String name,int nb) {
		int nombre = 0;
		for(int i = 0;i < max;i++) {
			if(this.item[i] != null && this.item[i].getname() == name) {
				nombre++;
			}
		}
		return nombre <= nb;
	}
	
}
