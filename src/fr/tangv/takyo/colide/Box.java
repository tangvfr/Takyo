package fr.tangv.takyo.colide;

public class Box {

	private float[] p = new float[4];
	
	public Box(float x, float y) {
		p[0] = x;
		p[1] = y;
		p[2] = x;
		p[3] = y;
	}
	
	public Box(float x, float y,float width, float height) {
		p[0] = x;
		p[1] = y;
		p[2] = x+width;
		p[3] = y+height;
	}
	
	public Box(float[] location) {
		p[0] = location[0];
		p[1] = location[1];
		p[2] = location[0];
		p[3] = location[1];
	}
	
	public Box(float[] location,float[] location2) {
		p[0] = location[0];
		p[1] = location[1];
		p[2] = location2[0];
		p[3] = location2[1];
	}

	public float[] getPoints() {
		return p;
	}
	
	public float[] getlocation1() {
		return new float[] {p[0],p[1]};
	}
	
	public float[] getlocation2() {
		return new float[] {p[2],p[3]};
	}
	
	public Colide colide(Box box) {
		float[] p2 = box.getPoints();
		boolean colide = true;
		char sens = 'A';
		float min = -1;
		
		if(p[2] < p2[0]) {colide = false;}
		if(p[0] > p2[2]) {colide = false;}
		if(p[3] < p2[1]) {colide = false;}
		if(p[1] > p2[3]) {colide = false;}
		
		if(colide) {
			//------------------ sens1
			min = 100;
			
			if((p2[3] - p[1]) < min) {
				min = (p2[3] - p[1]);
				sens = 'S';
			}
			if((p[3] - p2[1]) < min){
				min = (p[3] - p2[1]);
				sens = 'N';
			}		
			if((p2[2] - p[0]) < min){
				min = (p2[2] - p[0]);
				sens = 'E';
			}
			if((p[2] - p2[0]) < min){
				min = (p[2] - p2[0]);
				sens = 'O';
			}
			
		}
		
		return new Colide(colide, sens);
	}
	
}
