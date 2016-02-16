package umlParser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class LandingActionListener implements ActionListener{

	JFrame landingFrame = null;
	GUIConfigInfo configInfo = null;
	
	public LandingActionListener(JFrame frame) {
		this.landingFrame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(((JButton) e.getSource()).getName().equals("Analyze")){
			System.out.println("Analyze");
		} else if(((JButton) e.getSource()).getName().equals("Load Config")){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("./docs"));
			int result = fileChooser.showOpenDialog(landingFrame);
			if (result == JFileChooser.APPROVE_OPTION){
				this.configInfo = new GUIConfigInfo();
				File selectedFile = fileChooser.getSelectedFile();
				loadFile(selectedFile);
			}
		}
	}
	
	private boolean loadFile(File selectedFile){
		try {
			Scanner scanner = new Scanner(selectedFile);
			if(!scanner.hasNextLine()) {
				scanner.close(); 
				throw new Exception();
			}
			String inputPathLine = scanner.nextLine();
			configInfo.setInputFolder(inputPathLine.substring(inputPathLine.indexOf(":") + 2));
			String inputClassesLine = scanner.nextLine();
			inputClassesLine = inputClassesLine.substring(inputClassesLine.indexOf(":") + 2);
			for(String className : inputClassesLine.split(",")){
				configInfo.getInputClasses().add(className.trim());
			}
			String outputPathLine = scanner.nextLine();
			configInfo.setOutputFolder(outputPathLine.substring(outputPathLine.indexOf(":") + 2));
			String dotPathLine = scanner.nextLine();
			configInfo.setDotPath(dotPathLine.substring(dotPathLine.indexOf(":") + 2));
			String phasesLine = scanner.nextLine();
			phasesLine = phasesLine.substring(phasesLine.indexOf(":") + 2);
			for(String phaseName : phasesLine.split(",")){
				configInfo.getPhases().add(phaseName.trim());
			}
			// Other attributes possibly added here later
			System.out.println(configInfo.getInputFolder());
			System.out.println(configInfo.getInputClasses());
			System.out.println(configInfo.getOutputFolder());
			System.out.println(configInfo.getDotPath());
			System.out.println(configInfo.getPhases());
			scanner.close();
			return true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

}
