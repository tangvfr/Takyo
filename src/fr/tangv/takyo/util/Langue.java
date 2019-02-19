package fr.tangv.takyo.util;

public class Langue {
	private String[] MenuBouton = {"Solo","Option","Quitter"};
	private String[] OptionBoutonText = {"Retour","Reset","Avancer","Reculer","Droit","Gauche","Entrer","FullScreen","SmallScreen","Langue"}; 
	private String textinfokey = "ExporterMap = P\r\n" + 
			"GetPointScreen = M\r\n" + 
			"GetPointParRapportMilieu = K\r\n" + 
			"GetPointHero = U\r\n" + 
			"TeleportToCursor = O\r\n" + 
			"TeleportMilieu = I\r\n" + 
			"PoserBlock = ClickGauche\r\n" + 
			"RotationBlock = ClickDroit\r\n" + 
			"RecupererBlock = ClickMolette\r\n" + 
			"AfficherBoxColide = Ctrl+Alt+/\r\n" + 
			"PoserBlockEnContinue = L\r\n" +
			"SetBlockZone = J\r\n" +
			"SetContourBlockZone = N\r\n" + 
			"AnullerSBZ = H";
	
	public String gettextinfokey() {
		return textinfokey;
	}
	
	public void settextinfokey(String textinfokey) {
		this.textinfokey = textinfokey;
	}
	
	public String[] getMenuBouton() {
		return MenuBouton;
	}
	
	public void setMenuBouton(String[] string) {
		MenuBouton = string;
	}
	
	public String[] getOptionBoutonText() {
		return OptionBoutonText;
	}
	
	public void setOptionBoutonText(String[] string) {
		OptionBoutonText = string;
	}
	
	
}
