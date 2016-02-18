package umlParser;

import java.awt.Component;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ResultAnalyzerProxy extends ImageIcon implements IAnalyzer {

	private static final long serialVersionUID = -9110015523991013123L;
	volatile ImageIcon imageIcon;
	Component c;
	String dotPath;
	Thread retrievalThread;
	IAnalyzer analyzer;

	public ResultAnalyzerProxy(IAnalyzer analyzer, String dotPath) {
		this.analyzer = analyzer;
		this.dotPath = dotPath;
	}

	@Override
	public boolean execute(String phase) {
		return this.analyzer.execute(phase);
	}

	@Override
	public boolean executeAll(ArrayList<String> phases) {
		for(String phase : phases){
			if(!execute(phase)){
				return false;
			}
		}
		retrievalThread = new Thread(new Runnable(){
			public void run(){
				try {
					ProcessBuilder pb = new ProcessBuilder(dotPath, "-Tpng", "output.dot", "-o", "output.png");
					Process child = pb.start();
					child.waitFor();
					setImageIcon(new ImageIcon(ImageIO.read(new File("output.png"))));
					if(c != null){
						c.repaint();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		retrievalThread.start();
		return true;
	}

	@Override
	public int getIconHeight() {
		if(imageIcon != null){
			return imageIcon.getIconHeight();
		} else {
			return 200;
		}
	}

	@Override
	public int getIconWidth() {
		if(imageIcon != null){
			return imageIcon.getIconWidth();
		} else {
			return 400;
		}
	}
	
	synchronized void setImageIcon(ImageIcon imageIcon){
		this.imageIcon = imageIcon;
	}

	@Override
	public void paintIcon(final Component c, Graphics g, int x, int y) {
		this.c = c;
		if(imageIcon != null) {
			imageIcon.paintIcon(c, g, x, y);
		} else {
			g.drawString("Loading Graph, Please Wait....", x, y);
		}
	}

}
