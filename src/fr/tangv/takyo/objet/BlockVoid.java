package fr.tangv.takyo.objet;

import fr.tangv.takyo.colide.Box;
import fr.tangv.takyo.colide.Colide;

public class BlockVoid {

	public static Block createBlock(int idblock,float[] location,int grandeurblock) {
		Block block = new Block();
		block.setidblock(idblock);
		block.setgrandeurblock(grandeurblock);
		block.setsens(0);
		block.setsolide(false);
		BlockVoid.setlocation(block,location);
		return block;
	}
	
	//--------------------------------------------------
	
	public static void setlocation(Block block,float[] point) {
		block.setlocation(point);
		block.setlocation2(new float[]{point[0] + block.getgrandeurblock(), point[1] + block.getgrandeurblock()});
	}
	
	//--------------------------------------------------
	
	public static Colide colide(Block block,Box box) {
		if(block.getsolide()) {
			return new Box(block.getlocation(),block.getlocation2()).colide(box);
		}else {
			char sens = 'A';
			return new Colide(false, sens);
		}
	}
	
}
