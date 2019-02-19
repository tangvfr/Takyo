package fr.tangv.takyo.game;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

public class Start {
	
	private static final String Location = Start.class.getResource("").toString().replaceAll("!/"+Start.class.getPackage().getName()+"/", "").replaceAll("jar:file:/", "");
	
	private static void export(JProgressBar jbar) throws IOException {
		String des = "";
		System.out.println("Location: "+Location);
		ZipFile zipf = new ZipFile(Location);
		Enumeration<? extends ZipEntry> zip = zipf.entries();
		ArrayList<String> list = new ArrayList<String>();
		while(zip.hasMoreElements()) {
			ZipEntry en = zip.nextElement();
			String name = en.getName();
			if(!en.isDirectory() && (name.toLowerCase().contains("native/") || name.toLowerCase().contains("lib/"))) {
				list.add(name);
			}
		}
		jbar.setMinimum(0);
		jbar.setMaximum(list.size());
		jbar.setString("0/0");
		for(int i = 0; i < list.size();i++) {
			String name = list.get(i);
			System.out.println("Etat: "+(i+1)+"/"+list.size()+" Fichier: "+name);
			jbar.setValue(i+1);
			jbar.setString((i+1)+"/"+list.size()+" => "+name);
			if(!new File("./"+name).exists() || new File("./"+name).length() != zipf.getEntry(name).getSize()) {
				InputStream in = zipf.getInputStream(zipf.getEntry(name));
				try {
					File file = new File("./"+name);
					file.mkdirs();
					if(!zipf.getEntry(name).isDirectory()) {
						file.delete();
						file.createNewFile();
						FileOutputStream out = new FileOutputStream(file);
						int k;
						byte[] b = new byte[2048];
						while((k = in.read(b)) != -1) {
							out.write(b, 0, k);
						}
						out.close();
					}
				}catch (Exception e) {
					System.out.println("Erreur Fichier: "+name);
				}
				in.close();
			}
		}
		zipf.close();
	}
	
	public static final String name = "Takyo Alpha_1.3.1";
	
	public static void main(String[] args) throws Exception {
		System.out.println(name.replace(' ', '_'));
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		if(args.length == 1 && args[0].toLowerCase().equals("startgame")) {
			System.out.println("StarGame");
			new App(name);
		}else {
			System.out.println("Starting Game");
			String osname = System.getProperty("os.name");
			System.out.println(osname);
			String osis = "";
			if(osname.toLowerCase().contains("windows")) {
				osis = "windows";
			}else if(osname.toLowerCase().contains("mac")) {
				osis = "macosx";
			}else {
				osis = "linux";
			}
			String java = "javaw";
			File file = new File("./java");
			if(file.exists()) {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				try {
					String string = br.readLine();
					if(!string.isEmpty()) {
						java = string;
					}
				}catch (Exception e) {
					System.out.println("Fichier java vide");
				}
				br.close();
				fr.close();
			}
			System.out.println("Démarage avec: " + osis + "\n" + "Java: "+ java);
			String[] nfichier = Location.split("/");
			if(!nfichier[nfichier.length-1].equals("Takyo.jar")) {
				String text = "Echec le jeux n'a pas réussie à etre lancer: \nPour cause que le nom du fichier a été changée et ne corespond plus à Takyo.jar";
				System.out.println(text);
				JOptionPane.showMessageDialog(null, text, name, JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			try {
				try {
					JFrame fen = new JFrame(name);
					fen.setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
					fen.setSize(320, 80);
					fen.setLocationRelativeTo(null);
					fen.setResizable(false);
					fen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					JProgressBar pbar = new JProgressBar();
					pbar.setStringPainted(true);
					pbar.setVisible(true);
					fen.getContentPane().add(pbar,BorderLayout.CENTER);
					fen.setVisible(true);
					try {
						export(pbar);
					}catch (IOException e) {
						String text = "Echec le jeux n'a pas réussie à etre lancer: \n"+e.getCause();
						System.out.println(text);
						JOptionPane.showMessageDialog(null, text, name, JOptionPane.ERROR_MESSAGE);
					}
					fen.setEnabled(false);
					fen.setVisible(false);
				}catch (Exception e) {
					e.printStackTrace();
				}
				Runtime.getRuntime().exec(java+" -Djava.library.path=\"native\\"+osis+"\" -classpath .;Takyo.jar;lib/slick.jar;lib/jorbis-0.0.17-1.jar;lib/lwjgl_util.jar;lib/lwjgl.jar;lib/jogg-0.0.7.jar;lib/jackson-all-1.9.5.jar fr.tangv.takyo.game.Start startgame");
				System.exit(0);
			}catch (Exception e) {
				String text = "Echec le jeux n'a pas réussie à etre lancer: \n"+e.getCause();
				System.out.println(text);
				JOptionPane.showMessageDialog(null, text, name, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
