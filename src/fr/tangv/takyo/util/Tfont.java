package fr.tangv.takyo.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import fr.tangv.takyo.game.App;

public class Tfont {
	
	public static TrueTypeFont fontbouton;
	public static TrueTypeFont fonttext1;
	
	private static Font Fontbase;
	public static void iniFont() {
		try {
			Fontbase = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream(App.cheminfont));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		//-----------------------------------------------------------------------------------------------------------
		fontbouton = Tfont.create(35f);
		fonttext1 = Tfont.create(20f);
	}
	
	private static TrueTypeFont create(float taile) {
		Font font = Fontbase.deriveFont(taile);
		return new TrueTypeFont(font,false);
	}
}
