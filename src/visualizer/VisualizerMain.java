package visualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import visualizer.components.*;

public class VisualizerMain implements ActionListener{

	public static String experimentBaseLocation = "";
	public static String selectedRun = "";
	private ParameterDisplayer parameterDisplayer = null;
	private GameDisplayer gameDisplayer = null;
	private ParticleDisplayer particleDisplayer = null;
	private CreatureDisplayer creatureDisplayer = null;
	private GraphDisplayer graphDisplayer = null;
	private RunDisplayer runDisplayer = null;
	
	public VisualizerMain(){
		
		JFrame f = new JFrame("Game Visualizer");
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setPreferredSize(new Dimension(900,600));
		BorderLayout layout = new BorderLayout();
		layout.setVgap(0);
		f.setLayout(layout);
		
		setUpMenubar(f);
		setUpTopPane(f);
		setUpBotPane(f);
		
		f.setResizable(false);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
	}
	
	private void setUpMenubar(JFrame f){
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem open = new JMenuItem("Open Experiment");
		open.addActionListener(this);
		menu.add(open);
		menuBar.add(menu);
		f.setJMenuBar(menuBar);
	}
	
	private void setUpTopPane(JFrame f){
		parameterDisplayer = new ParameterDisplayer();
		gameDisplayer = new GameDisplayer();
		particleDisplayer = new ParticleDisplayer();
		
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		JPanel topPane = new JPanel(flowLayout);
		
		
		topPane.setBackground(Color.blue);
		topPane.setPreferredSize(new Dimension(900, 450));
		
		topPane.add(parameterDisplayer);
		topPane.add(gameDisplayer);
		topPane.add(particleDisplayer);
		f.add(topPane, BorderLayout.CENTER);
	}
	
	
	private void setUpBotPane(JFrame f){
		graphDisplayer = new GraphDisplayer();
		creatureDisplayer = new CreatureDisplayer();
		runDisplayer = new RunDisplayer(this);
		
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		JPanel botPane = new JPanel(flowLayout);
		botPane.setBackground(Color.red);
		botPane.setPreferredSize(new Dimension(900, 150));
		
		botPane.add(graphDisplayer);
		botPane.add(creatureDisplayer);
		botPane.add(runDisplayer);
		f.add(botPane,  BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new VisualizerMain();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")+"/Logs"));
		int choice = fc.showOpenDialog(null);
		
		if (choice == 0){
			File selected = fc.getSelectedFile();
			experimentBaseLocation = selected.getPath();
			runDisplayer.update();
		}
	}

	public void updateParticleDisplayer() {
		// TODO Auto-generated method stub
		particleDisplayer.update();
	}
	
}
