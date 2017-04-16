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
		
		
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		JPanel topPane = new JPanel(flowLayout);
		
		
		topPane.setBackground(Color.blue);
		topPane.setPreferredSize(new Dimension(900, 450));
		
		topPane.add(new ParameterDisplayer());
		topPane.add(new GameDisplayer());
		topPane.add(new ParticleDisplayer());
		f.add(topPane, BorderLayout.CENTER);
	}
	
	
	private void setUpBotPane(JFrame f){
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		JPanel botPane = new JPanel(flowLayout);
		botPane.setBackground(Color.red);
		botPane.setPreferredSize(new Dimension(900, 150));
		
		botPane.add(new GraphDisplayer());
		botPane.add(new ParticleDisplayer());
		botPane.add(new RunDisplayer());
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
		}
	}

}
