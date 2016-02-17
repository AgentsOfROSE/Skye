package umlParser;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {
	
	public static void main(String[] args){
				
		
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("Design Parser");
		frame.setPreferredSize(new Dimension(550, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		DesignParserActionListener actionListener = new DesignParserActionListener(frame);
		
		JButton loadConfigButton = new JButton();
		loadConfigButton.setText("Load Config");
		loadConfigButton.setName("Load Config");
		loadConfigButton.addActionListener(actionListener);
		loadConfigButton.setBounds(100, 150, 150, 50);
		loadConfigButton.setVisible(true);
		panel.add(loadConfigButton);
		
		JButton analyzeButton = new JButton();
		analyzeButton.setText("Analyze");
		analyzeButton.setName("Analyze");
		analyzeButton.setBounds(300, 150, 150, 50);
		analyzeButton.addActionListener(actionListener);
		analyzeButton.setVisible(true);
		panel.add(analyzeButton);
		
		panel.setLocation(0,0);
		panel.setSize(new Dimension(550, 550));
		frame.pack();
		frame.setContentPane(panel);
		frame.setResizable(false);
		frame.setVisible(true);
	}

}
