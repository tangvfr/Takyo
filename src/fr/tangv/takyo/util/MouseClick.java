package fr.tangv.takyo.util;

import org.newdawn.slick.Input;

public class MouseClick {

	boolean[] boutonvalue = {false,false,false};
	boolean[] boutonresult = {false,false,false};
	
	public void update(Input input) {
		boolean[] boutonvalue = {input.isMouseButtonDown(0),input.isMouseButtonDown(1),input.isMouseButtonDown(2)};
		boolean[] boutonresult = new boolean[3];
		for(int i = 0; i < 3;i++) {
			if(boutonvalue[i] == true && boutonvalue[i] != this.boutonvalue[i]) {
				boutonresult[i] = true;
			}else {
				boutonresult[i] = false;
			}
		}
		this.boutonvalue = boutonvalue;
		this.boutonresult = boutonresult;
	}
	
	public boolean isMouseClick(int index) {
		return boutonresult[index];
	}
	
}
