package umlParser;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI {
	
	public static void main(String[] args){
				
		
		JFrame frame = new JFrame();
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
	}

}
