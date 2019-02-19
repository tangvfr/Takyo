package fr.tangv.takyo.util;

import java.io.InputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.ResourceLoader;

public class Anim {

	public static Animation createAnimation(String pathall,int decal,float rotate) {
		try{
			Image img = new Image(pathall+".png");
			try {
				InputStream in =  ResourceLoader.getResourceAsStream(pathall+".json");
				ObjectMapper om = new ObjectMapper();
				AnimPara p = om.readValue(in, AnimPara.class);
				in.close();
				SpriteSheet sprit = new SpriteSheet(img, p.getwidth(), p.getheigth());
				Animation anim = new Animation();
				int nb = Math.floorDiv(img.getHeight(), p.getheigth());
				if(nb > 0) {
					for(int i = 0; i < nb;i++) {
						Image imgs = sprit.getSprite(decal, i);
						imgs.rotate(rotate);
						anim.addFrame(imgs, p.gettimeframe());
					}
					anim.start();
				}else {
					System.out.println("Error Anim Image >= 0");
					System.exit(1);
				}
				return anim;
			}catch (Exception e) {
				Animation anim = new Animation();
				img.rotate(rotate);
				anim.addFrame(img, 1);
				anim.stop();
				return anim;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
