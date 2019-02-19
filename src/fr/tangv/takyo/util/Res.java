package fr.tangv.takyo.util;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import fr.tangv.takyo.game.App;

public class Res {
	public static Resource[] resource;
	public static Langue langue = new Langue();
	public static Parametre parametre = new Parametre();
	
	public static void importparametre() {
		ObjectMapper om = new ObjectMapper();
		try {
			File file = new File(App.cheminparametre);
			if(file.exists()) {
				parametre = om.readValue(file, Parametre.class);
				System.out.println("Import Parametre Réussie");
			}else {
				exportparametre();
				System.out.println("Parametre Crée");
			}
		}catch (Exception e) {
			System.out.println("Import Parametre Error");
		}
	}
	
	public static void exportparametre() {
		ObjectMapper om = new ObjectMapper();
		try {
			File file = new File(App.cheminparametre);
			if(!file.exists()) {
				file.createNewFile();
			}
			om.writeValue(file, parametre);
			System.out.println("Export Parametre Réussie");
		}catch (Exception e) {
			System.out.println("Export Parametre Error");
		}
	}
	
	public static boolean importlangue(String chemin) {
		ObjectMapper om = new ObjectMapper();
		try {
			langue = om.readValue(new File(chemin),Langue.class);
			System.out.println("Import Langue true");
			return true;
		} catch (Exception e) {}
		System.out.println("Import Langue false");
		langue = new Langue();
		parametre.setlangue(new Parametre().getlangue());
		Res.exportlangue(App.chemin+"/langue/fr.json");
		System.out.println("Langue redéfinie: "+Res.parametre.getlangue());
		Res.importlangue(App.chemin+"/langue/"+Res.parametre.getlangue()+".json");
		Res.exportparametre();
		return false;
	}
	
	public static void exportlangue(String chemin) {
		File file = new File(chemin);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(file, langue);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Resource getRes(String string) {
		switch(string) {
			case "block":
				return resource[0];
			case "item":
				return resource[1];
			case "gui":
				return resource[2];
			default:
				return null;
		}
	}
	
	public static final int block = 0;
	public static final int item = 1;
	public static final int gui = 2;
	
	public static void iniRes() {
		resource = new Resource[]{new Resource("res/img/block/", new String[] {"Air","Herbe","Brick","Pierre","1","2","3","4","5","6"}),
				new Resource("res/img/item/", new String[]{"1"}),
				new Resource("res/img/gui/", new String[]{"background-menu","background-option","gui-inv","toolbar","neant"})};
	}
}
