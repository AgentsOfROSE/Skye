package umlParser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import checkBoxTree.CheckBoxNodeData;
import checkBoxTree.CheckBoxNodeEditor;
import checkBoxTree.CheckBoxNodeRenderer;

public class DesignParserActionListener implements ActionListener {

	JFrame frame = null;
	GUIConfigInfo configInfo = null;
	ArrayList<String> analyzedClasses = null;
	JPanel graphPanel;
	Timer timer = new Timer(200, this);
	ArrayList<String> analyzedPhases = null;
	IAnalyzer resultAnalyzer = null;
	HashMap<String, String> phaseToDetector = new HashMap<>();

	public DesignParserActionListener(JFrame frame) {
		this.frame = frame;
		phaseToDetector.put("Singleton-Detector", "Singleton");
		phaseToDetector.put("Decorator-Detector", "Decorator");
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
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("./docs"));
				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					this.configInfo = new GUIConfigInfo();
					File selectedFile = fileChooser.getSelectedFile();
					loadFile(selectedFile);
				}
			}
		} else if (e.getSource().getClass() == JMenuItem.class) {
			System.out.println(((JMenuItem) e.getSource()).getName());
			if (((JMenuItem) e.getSource()).getName().equals("Help Menu")) {
				setupHelpScreen();
			} else if (((JMenuItem) e.getSource()).getName().equals("About Menu")) {
				setupAboutScreen();
			} else if (((JMenuItem) e.getSource()).getName().equals("New Config")) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("./docs"));
				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					this.configInfo = new GUIConfigInfo();
					File selectedFile = fileChooser.getSelectedFile();
					loadFile(selectedFile);
				}
				((ResultAnalyzerProxy) this.resultAnalyzer).setImageIcon(null);
				File outputFile = new File(".//output.dot");
				PrintStream printStream;
				try {
					printStream = new PrintStream(new FileOutputStream(outputFile));
					PrintStream old = System.out;
					System.setOut(printStream);
					resultAnalyzer.executeAll(this.analyzedPhases);
					System.setOut(old);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				this.graphPanel.repaint();
			} else if (((JMenuItem) e.getSource()).getName().equals("Export Graph")) {
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
				try {
					in = new FileInputStream(graphFile);
					out = new FileOutputStream(saveFile);
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);

					}
					in.close();
					out.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
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
			// search(directoryFile);
			// Other attributes possibly added here later
			// System.out.println(configInfo.getInputFolder());
			// System.out.println(configInfo.getInputClasses());
			// System.out.println(configInfo.getOutputFolder());
			// System.out.println(configInfo.getDotPath());
			//System.out.println(configInfo.getPhases());
			scanner.close();
			this.analyzedPhases = configInfo.getPhases();
			this.analyzedClasses = configInfo.getInputClasses();
			this.resultAnalyzer = new ResultAnalyzerProxy(new Analyzer(this.analyzedClasses),
					configInfo.getDotPath());
			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}

	}

	public void search(File file) {
		System.out.println(file.canRead());
		if (file.isDirectory()) {
			if (file.canRead()) {
				for (File temp : file.listFiles()) {
					if (temp.isDirectory()) {
						search(temp);
					} else {
						System.out.println(temp.getName().getClass().toGenericString());
						if (temp.getName().substring(temp.getName().lastIndexOf(".")).equals(".java")) {
							// configInfo.getInputClasses().add(temp.getName().getClass().toString());
						}
					}
				}
			}
		}
	}

	public void setupResultFrame() throws IOException {
		this.frame.getContentPane().removeAll();
		this.frame.setSize(1000, 800);
		this.frame.getContentPane().setSize(1000, 743);

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

		// STUFF STARTS HERE
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Patterns");

		for(String phase : configInfo.getPhases()){
			if(!(phase.equals("Loader") || phase.equals("Output"))){
				final DefaultMutableTreeNode pattern = add(root, phaseToDetector.get(phase), true);
				for(String className : configInfo.getInputClasses()){				
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

		// listen for changes in the selection
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(final TreeSelectionEvent e) {
				System.out.println(System.currentTimeMillis() + ": selection changed");
			}
		});

		// listen for changes in the model (including check box toggles)
		treeModel.addTreeModelListener(new TreeModelListener() {

			//e.getChildren gets selected node
			//e.getPath gets nodes above it
			@Override
			public void treeNodesChanged(final TreeModelEvent e) {
				for(Object node : e.getChildren()){
					if(e.getPath().length == 1){
						String phaseToModify = null;
						for(String key : phaseToDetector.keySet()){
							if(phaseToDetector.get(key).equals(((CheckBoxNodeData) ((DefaultMutableTreeNode) node).getUserObject()).getText())){
								phaseToModify = key;
							}
						}
						if(phaseToModify != null){
							if(((CheckBoxNodeData) ((DefaultMutableTreeNode) node).getUserObject()).isChecked()){
								analyzedPhases.add(1, phaseToModify);
							} else {
								analyzedPhases.remove(phaseToModify);
							}
						}
					}
					System.out.println(e.getPath().length);
					System.out.println(((CheckBoxNodeData) ((DefaultMutableTreeNode) node).getUserObject()).getText());
				}
				((ResultAnalyzerProxy) resultAnalyzer).setImageIcon(null);
				frame.revalidate();
				System.out.println(analyzedPhases);
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
				graphPanel.repaint();
				System.out.println(System.currentTimeMillis() + ": nodes changed");
			}
			@Override
			public void treeNodesInserted(final TreeModelEvent e) {
				System.out.println(System.currentTimeMillis() + ": nodes inserted");
			}
			@Override
			public void treeNodesRemoved(final TreeModelEvent e) {
				System.out.println(System.currentTimeMillis() + ": nodes removed");
			}
			@Override
			public void treeStructureChanged(final TreeModelEvent e) {
				System.out.println(System.currentTimeMillis() + ": structure changed");
			}
		});
		// STUFF ENDS HERE
		tree.setBounds(0,0,2000,2000);
		leftPane.add(tree);
		leftScrollFrame.setVisible(true);
		this.frame.getContentPane().add(leftScrollFrame);

		JPanel rightPane = new JPanel();
		rightPane.setBounds(0, 0, 2000, 2000);
		JScrollPane rightScrollFrame = new JScrollPane(rightPane);
		// test.setAutoscrolls(true);
		rightPane.setVisible(true);
		rightScrollFrame.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		rightScrollFrame.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		rightScrollFrame.setBounds(300, 0, 694, 743);
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
		JFrame helpFrame = new JFrame();
		helpFrame.setSize(400, 400);
		helpFrame.setLayout(null);
		helpFrame.setResizable(false);
		helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel helpPanel = new JPanel();
		helpPanel.setLayout(null);
		helpPanel.setBounds(0, 0, 400, 400);
		JLabel helpContents = new JLabel();
		helpContents.setText("HELP CONTENTS");
		helpContents.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		helpContents.setBounds(50, 50, 300, 300);
		helpContents.setVisible(true);
		helpPanel.add(helpContents);
		helpPanel.setVisible(true);
		helpFrame.setVisible(true);
	}

	public void setupAboutScreen() {
		JFrame aboutFrame = new JFrame();
		aboutFrame.setSize(400, 400);
		aboutFrame.setLayout(null);
		aboutFrame.setResizable(false);
		aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel aboutPanel = new JPanel();
		aboutPanel.setLayout(null);
		aboutPanel.setBounds(0, 0, 400, 400);
		JLabel aboutContents = new JLabel();
		aboutContents.setText("ABOUT CONTENTS");
		aboutContents.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		aboutContents.setBounds(50, 50, 300, 300);
		aboutContents.setVisible(true);
		aboutPanel.add(aboutContents);
		aboutPanel.setVisible(true);
		aboutFrame.setVisible(true);
	}

	private static DefaultMutableTreeNode add(final DefaultMutableTreeNode parent, final String text,
			final boolean checked) {
		final CheckBoxNodeData data = new CheckBoxNodeData(text, checked);
		final DefaultMutableTreeNode node = new DefaultMutableTreeNode(data);
		parent.add(node);
		return node;
	}

}
