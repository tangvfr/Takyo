package fr.tangv.takyo.colide;

public class Colide {

	private boolean colide;
	private char sens;
	//sens = A,N,S,E,O
	
	public Colide(boolean colide, char sens) {
		this.colide = colide;
		this.sens = sens;
	}
	
	public boolean iscolide(){
		return colide;
	}
	
	public char getsens() {
		return sens;
	}
	
}
