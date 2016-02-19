package designPatternGUI;

import java.util.ArrayList;

import javax.swing.JProgressBar;

public class LoadAnalyzerProxy extends JProgressBar implements IAnalyzer {

	private static final long serialVersionUID = 132126952481863742L;
	private IAnalyzer analyzer;

	public LoadAnalyzerProxy(IAnalyzer analyzer) {
		this.analyzer = analyzer;
	}

	@Override
	public boolean execute(String phase) {
		return this.analyzer.execute(phase);
	}

	@Override
	public boolean executeAll(ArrayList<String> phases) {
		JProgressBar bar = this;
		for(String phase : phases){
			if(execute(phase)){
				bar.setValue((int) 100*(phases.indexOf(phase) + 1)/phases.size());	
				bar.repaint();
			}
		}
		return true;
	}
	
//	private void updateProgBar(String phase, ArrayList<String> phases, JProgressBar bar){
//		SwingUtilities.invokeLater(new Runnable(){						
//			public void run(){							
//				if(execute(phase)){
//					bar.setValue((int) 100*(phases.indexOf(phase) + 1)/phases.size());	
//					bar.repaint();
//				}
//			}
//		});
//	}

}
