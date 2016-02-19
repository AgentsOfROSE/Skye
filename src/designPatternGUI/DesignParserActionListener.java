package designPatternGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import checkBoxTree.CheckBoxNodeData;
import checkBoxTree.CheckBoxNodeEditor;
import checkBoxTree.CheckBoxNodeRenderer;
import umlParser.GUIConfigInfo;

public class DesignParserActionListener implements ActionListener {

	JFrame frame = null;
	GUIConfigInfo configInfo = null;
	ArrayList<String> analyzedClasses = null;
	JPanel graphPanel;
	JPanel treePanel;
	Timer timer = new Timer(200, this);
	ArrayList<String> analyzedPhases = null;
	IAnalyzer resultAnalyzer = new ResultAnalyzerProxy(null, null);
	HashMap<String, String> phaseToDetector = new HashMap<>();

	public DesignParserActionListener(JFrame frame) {
		this.frame = frame;
		phaseToDetector.put("Singleton-Detector", "Singleton");
		phaseToDetector.put("Decorator-Detector", "Decorator");
		phaseToDetector.put("Adapter-Detector", "Adapter");
		phaseToDetector.put("Composite-Detector", "Composite");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			this.frame.revalidate();
		} else if (e.getSource().getClass() == JButton.class) {
			if (((JButton) e.getSource()).getName().equals("Analyze")) {
				if (configInfo != null) {
					JProgressBar progBar = new LoadAnalyzerProxy(new Analyzer(this.analyzedClasses));
					progBar.setBounds(100, 350, 300, 50);
					progBar.setMaximum(100);
					progBar.setMinimum(0);
					progBar.setBorderPainted(true);
					progBar.setVisible(true);
					this.frame.getContentPane().add(progBar);
					this.frame.repaint();
					try {
						File outputFile = new File(".//output.dot");
						PrintStream printStream = new PrintStream(new FileOutputStream(outputFile));
						PrintStream old = System.out;
						System.setOut(printStream);
						((LoadAnalyzerProxy) progBar).executeAll(this.analyzedPhases);
						printStream.close();
						System.setOut(old);
						ProcessBuilder pb = new ProcessBuilder(configInfo.getDotPath(), "-Tpng", "output.dot", "-o",
								"output.png");
						Process child = pb.start();
						child.waitFor();
						setupResultFrame();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			} else if (((JButton) e.getSource()).getName().equals("Load Config")) {
				loadConfig();
			}
		} else if (e.getSource().getClass() == JMenuItem.class) {
			if (((JMenuItem) e.getSource()).getName().equals("Help Menu")) {
				setupHelpScreen();
			} else if (((JMenuItem) e.getSource()).getName().equals("About Menu")) {
				setupAboutScreen();
			} else if (((JMenuItem) e.getSource()).getName().equals("New Config")) {
				loadConfig();
				resultAnalyze();
			} else if (((JMenuItem) e.getSource()).getName().equals("Export Graph")) {
				try {
					export();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private void loadConfig(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("./docs/configs"));
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			this.configInfo = new GUIConfigInfo();
			File selectedFile = fileChooser.getSelectedFile();
			loadFile(selectedFile);
		}
	}
	
	private void resultAnalyze(){
		((ResultAnalyzerProxy) resultAnalyzer).setImageIcon(null);
		frame.revalidate();
		File outputFile = new File(".//output.dot");
		PrintStream printStream;
		try {
			printStream = new PrintStream(new FileOutputStream(outputFile));
			PrintStream old = System.out;
			System.setOut(printStream);
			resultAnalyzer.executeAll(analyzedPhases);
			System.setOut(old);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		this.treePanel.removeAll();
		this.treePanel.add(setupTree());
		graphPanel.repaint();
	}
	
	private void export() throws IOException{
		JFileChooser save = new JFileChooser();
		save.setApproveButtonText("Save");
		save.showOpenDialog(frame);
		String toSave = save.getSelectedFile().getAbsolutePath();
		File graphFile = new File("output.png");
		File saveFile = null;
		if (toSave.endsWith(".png")) {
			saveFile = new File(toSave);
		} else {
			saveFile = new File(toSave + ".png");
		}
		InputStream in;
		OutputStream out;
		in = new FileInputStream(graphFile);
		out = new FileOutputStream(saveFile);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);

		}
		in.close();
		out.close();
	}
	
	private boolean loadFile(File selectedFile) {
		try {
			Scanner scanner = new Scanner(selectedFile);
			if (!scanner.hasNextLine()) {
				scanner.close();
				throw new Exception();
			}
			String inputPathLine = scanner.nextLine();
			configInfo.setInputFolder(inputPathLine.substring(inputPathLine.indexOf(":") + 2));
			String inputClassesLine = scanner.nextLine();
			inputClassesLine = inputClassesLine.substring(inputClassesLine.indexOf(":") + 2);
			for (String className : inputClassesLine.split(",")) {
				configInfo.getInputClasses().add(className.trim());
			}
			String outputPathLine = scanner.nextLine();
			configInfo.setOutputFolder(outputPathLine.substring(outputPathLine.indexOf(":") + 2));
			String dotPathLine = scanner.nextLine();
			configInfo.setDotPath(dotPathLine.substring(dotPathLine.indexOf(":") + 2));
			String phasesLine = scanner.nextLine();
			phasesLine = phasesLine.substring(phasesLine.indexOf(":") + 2);
			for (String phaseName : phasesLine.split(",")) {
				configInfo.getPhases().add(phaseName.trim());
			}
			File directoryFile = new File(configInfo.getInputFolder());
			search(directoryFile, directoryFile.getName());
			// Other attributes possibly added here later
			// System.out.println(configInfo.getInputFolder());
			//System.out.println(configInfo.getInputClasses());
			// System.out.println(configInfo.getOutputFolder());
			// System.out.println(configInfo.getDotPath());
			// System.out.println(configInfo.getPhases());
			scanner.close();
			ArrayList<String> phasesToAnalyze = new ArrayList<String>();
			for(String phase : configInfo.getPhases()){
				phasesToAnalyze.add(phase);
			}
			this.analyzedPhases = phasesToAnalyze;
			this.analyzedClasses = configInfo.getInputClasses();
			((ResultAnalyzerProxy) this.resultAnalyzer)
					.setAnalyzer(new ResultAnalyzerProxy(new Analyzer(this.analyzedClasses), configInfo.getDotPath()));
			((ResultAnalyzerProxy) this.resultAnalyzer).setDotPath(configInfo.getDotPath());
			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}

	}

	public void search(File file, String path) {
		if (file.isDirectory()) {
			if (file.canRead()) {
				for (File temp : file.listFiles()) {
					if (temp.isDirectory()) {
						search(temp, path + "." + temp.getName());
					} else {
						if (temp.getName().substring(temp.getName().lastIndexOf(".")).equals(".java")) {
							configInfo.getInputClasses().add(path + "." + temp.getName().substring(0, temp.getName().lastIndexOf(".")));
						}
					}
				}
			}
		}
	}

	public void setupResultFrame() throws IOException {
		this.frame.getContentPane().removeAll();
		this.frame.setSize(1250, 800);
		this.frame.getContentPane().setSize(1250, 743);

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem newConfig = new JMenuItem("New Config File");
		newConfig.setName("New Config");
		newConfig.addActionListener(this);
		fileMenu.add(newConfig);
		JMenuItem exportGraph = new JMenuItem("Export Graph");
		exportGraph.setName("Export Graph");
		exportGraph.addActionListener(this);
		fileMenu.add(exportGraph);
		menuBar.add(fileMenu);

		JMenu helpMenu = new JMenu("Help");
		JMenuItem helpItem = new JMenuItem("Help");
		helpItem.setName("Help Menu");
		helpItem.addActionListener(this);
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.setName("About Menu");
		aboutItem.addActionListener(this);
		helpMenu.add(helpItem);
		helpMenu.add(aboutItem);

		menuBar.add(helpMenu);
		this.frame.setJMenuBar(menuBar);

		JPanel leftPane = new JPanel();
		leftPane.setBounds(0, 0, 2000, 2000);
		JScrollPane leftScrollFrame = new JScrollPane(leftPane);
		// test.setAutoscrolls(true);
		leftPane.setVisible(true);
		leftScrollFrame.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		leftScrollFrame.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		leftScrollFrame.setBounds(0, 0, 300, 743);

		JTree tree = setupTree();
		leftPane.add(tree);
		leftPane.setBackground(Color.WHITE);
		this.treePanel = leftPane;
		// STUFF ENDS HERE
		leftScrollFrame.setVisible(true);
		this.frame.getContentPane().add(leftScrollFrame);

		JPanel rightPane = new JPanel();
		rightPane.setBackground(Color.WHITE);
		rightPane.setBounds(0, 0, 2000, 2000);
		JScrollPane rightScrollFrame = new JScrollPane(rightPane);
		// test.setAutoscrolls(true);
		rightPane.setVisible(true);
		rightScrollFrame.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		rightScrollFrame.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		rightScrollFrame.setBounds(300, 0, 944, 743);
		rightScrollFrame.setVisible(true);
		this.frame.getContentPane().add(rightScrollFrame);
		((ResultAnalyzerProxy) this.resultAnalyzer).setImageIcon(new ImageIcon(ImageIO.read(new File("output.png"))));
		JLabel graphLabel = new JLabel((ResultAnalyzerProxy) this.resultAnalyzer);
		rightPane.add(graphLabel);
		this.graphPanel = rightPane;

		this.frame.repaint();
		timer.start();
	}

	public void setupHelpScreen() {
		JFrame helpFrame = new JFrame("Help");
		helpFrame.setSize(400, 400);
		helpFrame.setLayout(null);
		helpFrame.setResizable(false);
		helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel helpPanel = new JPanel();
		helpPanel.setLayout(null);
		helpPanel.setBounds(0, 0, 400, 400);
		JTextArea helpContents = new JTextArea(
				"HELP\n\nANALYZE CLASSES\nTo analyze new classes you must select a config file to analyze by going to 'File'->'New Config File'\n"
						+ "\nEXPORTING GRAPH\nYou may export your graph by selecting 'File'->'Export Graph' and then using the file selector\n"
						+ "\nSELECTING PATTERNS AND CLASSES\nYou may select pattern detectors to run by using the tree located in the left window pane. The analyzer will automatically recreate the UML graph.",
				50, 1);
		helpContents.setEditable(false);
		helpContents.setWrapStyleWord(true);
		helpContents.setLineWrap(true);
		helpContents.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		helpContents.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		helpContents.setBounds(25, 25, 350, 350);
		helpContents.setOpaque(false);
		helpContents.setVisible(true);
		helpPanel.add(helpContents);
		helpPanel.setVisible(true);
		helpFrame.add(helpPanel);
		helpFrame.setVisible(true);
	}

	public void setupAboutScreen() {
		JFrame aboutFrame = new JFrame("Help");
		aboutFrame.setSize(400, 400);
		aboutFrame.setLayout(null);
		aboutFrame.setResizable(false);
		aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel aboutPanel = new JPanel();
		aboutPanel.setLayout(null);
		aboutPanel.setBounds(0, 0, 400, 400);
		JTextArea aboutContents = new JTextArea(
				"ABOUT\n\nTEAM\nTrevor Burch\nEmily Richardson\n" + 
						"\nPURPOSE\nThe purpose of this application is to allow users to dynamically detect different design patterns in given source files\n" + 
						"\nCOPYRIGHT\nAgents Of R.O.S.E. 2016 All rights reserved\n\nHAIL HYDRA",
				50, 1);
		aboutContents.setEditable(false);
		aboutContents.setWrapStyleWord(true);
		aboutContents.setLineWrap(true);
		aboutContents.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		aboutContents.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		aboutContents.setBounds(25, 25, 350, 350);
		aboutContents.setOpaque(false);
		aboutContents.setVisible(true);
		aboutPanel.add(aboutContents);
		aboutPanel.setVisible(true);
		aboutFrame.add(aboutPanel);
		aboutFrame.setVisible(true);
	}

	private static DefaultMutableTreeNode add(final DefaultMutableTreeNode parent, final String text,
			final boolean checked) {
		final CheckBoxNodeData data = new CheckBoxNodeData(text, checked);
		final DefaultMutableTreeNode node = new DefaultMutableTreeNode(data);
		parent.add(node);
		return node;
	}

	public JTree setupTree() {
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Patterns");

		for (String phase : configInfo.getPhases()) {
			if (!(phase.equals("Loader") || phase.equals("Output"))) {
				final DefaultMutableTreeNode pattern = add(root, phaseToDetector.get(phase), analyzedPhases.contains(phase));
				for (String className : configInfo.getInputClasses()) {
					add(pattern, className, true);
				}
			}
		}

		final DefaultTreeModel treeModel = new DefaultTreeModel(root);
		final JTree tree = new JTree(treeModel);

		final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		tree.setCellRenderer(renderer);

		final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(tree);
		tree.setCellEditor(editor);
		tree.setEditable(true);

		// listen for changes in the model (including check box toggles)
		treeModel.addTreeModelListener(new TreeModelListener() {

			// e.getChildren gets selected node
			// e.getPath gets nodes above it
			@Override
			public void treeNodesChanged(final TreeModelEvent e) {
				for (Object node : e.getChildren()) {
					if (e.getPath().length == 1) {
						String phaseToModify = null;
						for (String key : phaseToDetector.keySet()) {
							if (phaseToDetector.get(key).equals(
									((CheckBoxNodeData) ((DefaultMutableTreeNode) node).getUserObject()).getText())) {
								phaseToModify = key;
							}
						}
						if (phaseToModify != null) {
							if (((CheckBoxNodeData) ((DefaultMutableTreeNode) node).getUserObject()).isChecked()) {
								analyzedPhases.add(1, phaseToModify);
							} else {
								analyzedPhases.remove(phaseToModify);
							}
						}
					} else if (e.getPath().length == 2) {
						String phaseToUse = null;
						for (String key : phaseToDetector.keySet()) {
							if (phaseToDetector.get(key)
									.equals(((CheckBoxNodeData) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) node)
											.getParent()).getUserObject()).getText())) {
								phaseToUse = key;
							}
						}
						if (phaseToUse != null) {
							if (((CheckBoxNodeData) ((DefaultMutableTreeNode) node).getUserObject()).isChecked()) {
								resultAnalyzer.removeException(phaseToUse,
										((CheckBoxNodeData) ((DefaultMutableTreeNode) node).getUserObject()).getText());
							} else {
								resultAnalyzer.addException(phaseToUse,
										((CheckBoxNodeData) ((DefaultMutableTreeNode) node).getUserObject()).getText());
							}
						}
					}
				}
				resultAnalyze();
			}

			@Override
			public void treeNodesInserted(final TreeModelEvent e) {
			}

			@Override
			public void treeNodesRemoved(final TreeModelEvent e) {
			}

			@Override
			public void treeStructureChanged(final TreeModelEvent e) {
			}
		});
		tree.setBounds(0, 0, 2000, 2000);
		return tree;
	}

}
