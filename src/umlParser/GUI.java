package umlParser;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class GUI {
	
	public static void main(String[] args){
				
		
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setTitle("Design Parser");
		frame.setSize(550, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		LandingActionListener actionListener = new LandingActionListener(frame);
		
		JButton loadConfigButton = new JButton();
		loadConfigButton.setText("Load Config");
		loadConfigButton.setName("Load Config");
		loadConfigButton.setSize(new Dimension(150, 50));
		loadConfigButton.addActionListener(actionListener);
		loadConfigButton.setVisible(true);
		loadConfigButton.setLocation(100, 150);
		frame.getContentPane().add(loadConfigButton);
		
		JButton analyzeButton = new JButton();
		analyzeButton.setText("Analyze");
		analyzeButton.setName("Analyze");
		analyzeButton.setSize(new Dimension(150, 50));
		analyzeButton.addActionListener(actionListener);
		analyzeButton.setVisible(true);
		analyzeButton.setLocation(300, 150);
		frame.getContentPane().add(analyzeButton);
		
		JProgressBar progBar = new LoadAnalyzerProxy(new Analyzer());
		progBar.setVisible(false);
		progBar.setSize(new Dimension(300, 50));
		progBar.setMaximum(100);
		progBar.setMinimum(0);
		progBar.setBorderPainted(true);
		progBar.setLocation(100, 350);
		frame.getContentPane().add(progBar);
	}

}
