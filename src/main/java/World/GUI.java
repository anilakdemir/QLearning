 package World;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private double gamma;
	private String INPUTDIR = "data/input/";
	private String OUTPUTDIR = "data/output/";
	private File file = null;
	private Maze maze;
	private Agent agent;
	private boolean isTrained = false;

	private JPanel contentPane;
	private JTextField txtIteration;
	private JButton btnTrain;
	private JLabel colon4;
	private JLabel colon3;
	private JLabel colon2;
	private JLabel lblIteration;
	private JLabel lblGoal;
	private JLabel lblStart;
	private JLabel lblInput;
	private JLabel colon1;
	private JLabel lblInputFileName;
	private JButton btnSelectInput;
	private JCheckBox checkInput;
	private JComboBox<Integer> comboStart;
	private JComboBox<Integer> comboGoal;
	private JButton btnShowPath;
	private JButton btnOutputFolder;
	private JButton btnInputFolder;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 411, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 98, 32, 136, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		lblInput = new JLabel("Input");
		GridBagConstraints gbc_lblInput = new GridBagConstraints();
		gbc_lblInput.insets = new Insets(0, 0, 5, 5);
		gbc_lblInput.gridx = 1;
		gbc_lblInput.gridy = 1;
		panel.add(lblInput, gbc_lblInput);

		colon1 = new JLabel(":");
		GridBagConstraints gbc_colon1 = new GridBagConstraints();
		gbc_colon1.insets = new Insets(0, 0, 5, 5);
		gbc_colon1.gridx = 2;
		gbc_colon1.gridy = 1;
		panel.add(colon1, gbc_colon1);

		lblInputFileName = new JLabel("No File Selected");
		GridBagConstraints gbc_lblInputFileName = new GridBagConstraints();
		gbc_lblInputFileName.insets = new Insets(0, 0, 5, 5);
		gbc_lblInputFileName.gridx = 3;
		gbc_lblInputFileName.gridy = 1;
		panel.add(lblInputFileName, gbc_lblInputFileName);

		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setCurrentDirectory(new File(INPUTDIR));
		fc.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "text"));
		btnSelectInput = new JButton("...");
		btnSelectInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == btnSelectInput) {
					int returnVal = fc.showOpenDialog(btnSelectInput);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						isTrained = false;
						file = fc.getSelectedFile();
						lblInputFileName.setText(file.getName());
						if (file.getName().contains(".txt")) {
							checkInput.setSelected(true);
							comboStart.setEnabled(true);
							comboGoal.setEnabled(true);
							txtIteration.setEnabled(true);

							try {
								comboStart.removeAllItems();
								comboGoal.removeAllItems();
								for (int i = 0; i < Files.readAllLines(Paths.get(file.getPath())).size(); i++) {
									comboStart.addItem(i);
									comboGoal.addItem(i);
								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						} else {
							checkInput.setSelected(false);
							comboStart.setEnabled(false);
							comboGoal.setEnabled(false);
							txtIteration.setEnabled(false);
						}
					}
				}

			}
		});

		GridBagConstraints gbc_btnSelectInput = new GridBagConstraints();
		gbc_btnSelectInput.anchor = GridBagConstraints.WEST;
		gbc_btnSelectInput.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectInput.gridx = 4;
		gbc_btnSelectInput.gridy = 1;
		btnSelectInput.setPreferredSize(new Dimension(25, 20));
		panel.add(btnSelectInput, gbc_btnSelectInput);

		lblStart = new JLabel("Start");
		GridBagConstraints gbc_lblStart = new GridBagConstraints();
		gbc_lblStart.insets = new Insets(0, 0, 5, 5);
		gbc_lblStart.gridx = 1;
		gbc_lblStart.gridy = 2;
		panel.add(lblStart, gbc_lblStart);

		colon2 = new JLabel(":");
		GridBagConstraints gbc_colon2 = new GridBagConstraints();
		gbc_colon2.insets = new Insets(0, 0, 5, 5);
		gbc_colon2.gridx = 2;
		gbc_colon2.gridy = 2;
		panel.add(colon2, gbc_colon2);

		comboStart = new JComboBox<Integer>();
		comboStart.setEnabled(false);
		GridBagConstraints gbc_comboStart = new GridBagConstraints();
		gbc_comboStart.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboStart.insets = new Insets(0, 0, 5, 5);
		gbc_comboStart.gridx = 3;
		gbc_comboStart.gridy = 2;
		panel.add(comboStart, gbc_comboStart);

		checkInput = new JCheckBox("");
		checkInput.setEnabled(false);
		checkInput.setVisible(false);
		GridBagConstraints gbc_checkInput = new GridBagConstraints();
		gbc_checkInput.insets = new Insets(0, 0, 5, 5);
		gbc_checkInput.gridx = 4;
		gbc_checkInput.gridy = 2;
		panel.add(checkInput, gbc_checkInput);

		lblGoal = new JLabel("Goal");
		GridBagConstraints gbc_lblGoal = new GridBagConstraints();
		gbc_lblGoal.insets = new Insets(0, 0, 5, 5);
		gbc_lblGoal.gridx = 1;
		gbc_lblGoal.gridy = 3;
		panel.add(lblGoal, gbc_lblGoal);

		colon3 = new JLabel(":");
		GridBagConstraints gbc_colon3 = new GridBagConstraints();
		gbc_colon3.insets = new Insets(0, 0, 5, 5);
		gbc_colon3.gridx = 2;
		gbc_colon3.gridy = 3;
		panel.add(colon3, gbc_colon3);

		comboGoal = new JComboBox<Integer>();
		comboGoal.setEnabled(false);
		GridBagConstraints gbc_comboGoal = new GridBagConstraints();
		gbc_comboGoal.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboGoal.insets = new Insets(0, 0, 5, 5);
		gbc_comboGoal.gridx = 3;
		gbc_comboGoal.gridy = 3;
		panel.add(comboGoal, gbc_comboGoal);

		lblIteration = new JLabel("Iteration");
		GridBagConstraints gbc_lblIteration = new GridBagConstraints();
		gbc_lblIteration.insets = new Insets(0, 0, 5, 5);
		gbc_lblIteration.gridx = 1;
		gbc_lblIteration.gridy = 4;
		panel.add(lblIteration, gbc_lblIteration);

		colon4 = new JLabel(":");
		GridBagConstraints gbc_colon4 = new GridBagConstraints();
		gbc_colon4.insets = new Insets(0, 0, 5, 5);
		gbc_colon4.gridx = 2;
		gbc_colon4.gridy = 4;
		panel.add(colon4, gbc_colon4);

		txtIteration = new JTextField();
		txtIteration.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent keyEvent) {
				try {
					Integer.parseInt(txtIteration.getText().trim());

					if (txtIteration.getText().equals("")) {
						btnTrain.setEnabled(false);
					} else if (Integer.parseInt(txtIteration.getText().trim()) <= 10) {
						btnTrain.setEnabled(false);
					} else {
						btnTrain.setEnabled(true);
					}

				} catch (NumberFormatException e1) {
					btnTrain.setEnabled(false);
					txtIteration.setText(" ");
				}
			}
		});
		txtIteration.setEnabled(false);
		txtIteration.setColumns(10);
		GridBagConstraints gbc_txtIteration = new GridBagConstraints();
		gbc_txtIteration.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIteration.insets = new Insets(0, 0, 5, 5);
		gbc_txtIteration.gridx = 3;
		gbc_txtIteration.gridy = 4;
		panel.add(txtIteration, gbc_txtIteration);

		btnTrain = new JButton("Train");
		btnTrain.setEnabled(false);
		btnTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					if (!checkInput.isSelected()) {
						JOptionPane.showMessageDialog(null, "Please Select File.");

					} else {

						int start = comboStart.getSelectedIndex();
						int goal = comboGoal.getSelectedIndex();
						int iteration = Integer.valueOf(txtIteration.getText().trim());

						gamma = 0.8d;

						IOUtility input = new IOUtility();

						maze = input.ReadInput(file.getPath(), goal);

						agent = new Agent();
						agent.train(maze, start, goal, iteration, gamma);

						maze.printR(OUTPUTDIR + "outR.txt");

						System.out.println("\n---------\n");

						agent.printQ(OUTPUTDIR + "outQ.txt");

						System.out.println("\n---------\n");

						agent.printPath(maze, OUTPUTDIR + "outPath.txt");

						isTrained = true;
					}

				} catch (IOException exception) {
					System.out.println(exception.getMessage());
				}

			}
		});

		GridBagConstraints gbc_btnTrain = new GridBagConstraints();
		gbc_btnTrain.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnTrain.insets = new Insets(0, 0, 5, 5);
		gbc_btnTrain.gridx = 1;
		gbc_btnTrain.gridy = 5;
		panel.add(btnTrain, gbc_btnTrain);

		btnShowPath = new JButton("Show Path");
		btnShowPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (isTrained) {
					MazeGUI mazeGraphic = new MazeGUI(maze, agent);
					mazeGraphic.display();
				} else {
					JOptionPane.showMessageDialog(null, "Please Train First.");
				}

			}
		});
		GridBagConstraints gbc_btnShowPath = new GridBagConstraints();
		gbc_btnShowPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnShowPath.insets = new Insets(0, 0, 5, 5);
		gbc_btnShowPath.gridx = 3;
		gbc_btnShowPath.gridy = 5;
		panel.add(btnShowPath, gbc_btnShowPath);

		btnInputFolder = new JButton("Input Folder");
		btnInputFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(System.getProperty("os.name"));
					if(System.getProperty("os.name").startsWith("Windows"))
						Desktop.getDesktop().open(new File("data\\input"));
					else if(System.getProperty("os.name").startsWith("Mac"))
						Runtime.getRuntime().exec("open data/input");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnInputFolder = new GridBagConstraints();
		gbc_btnInputFolder.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnInputFolder.insets = new Insets(0, 0, 5, 5);
		gbc_btnInputFolder.gridx = 1;
		gbc_btnInputFolder.gridy = 6;
		panel.add(btnInputFolder, gbc_btnInputFolder);

		btnOutputFolder = new JButton("Output Folder");
		btnOutputFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(System.getProperty("os.name"));
					if(System.getProperty("os.name").startsWith("Windows"))
						Desktop.getDesktop().open(new File("data\\output"));
					else if(System.getProperty("os.name").startsWith("Mac"))
						Runtime.getRuntime().exec("open data/output");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnOutputFolder = new GridBagConstraints();
		gbc_btnOutputFolder.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOutputFolder.insets = new Insets(0, 0, 5, 5);
		gbc_btnOutputFolder.gridx = 3;
		gbc_btnOutputFolder.gridy = 6;
		panel.add(btnOutputFolder, gbc_btnOutputFolder);
	}

}
