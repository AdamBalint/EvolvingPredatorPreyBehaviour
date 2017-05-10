package visualizer.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import visualizer.VisualizerMain;

public class ParticleDisplayer extends JPanel implements ChangeListener, ListSelectionListener{

	JList displayPred, displayPrey;
	private ArrayList<File> predParticles, preyParticles;
	private int epochSelected = 0;
	private int minEpoch = 0, maxEpoch = 10;
	private JSpinner epochSelector;
	private VisualizerMain vm;
	
	public ParticleDisplayer(VisualizerMain vm){
		this(225, 450, vm);
	}
	
	public ParticleDisplayer(int width, int height, VisualizerMain vm){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.magenta);
		this.vm = vm;
		
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		this.setLayout(flowLayout);
		
		
		displayPred = new JList();
		displayPred.setLayout(flowLayout);
		displayPred.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		displayPred.setLayoutOrientation(JList.VERTICAL);
		displayPred.addListSelectionListener(this);
		displayPred.setName("pred");
		JScrollPane listScroller = new JScrollPane(displayPred);
		listScroller.setPreferredSize(new Dimension(width, height-100));
		
		displayPrey = new JList();
		displayPrey.setLayout(flowLayout);
		displayPrey.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		displayPrey.setLayoutOrientation(JList.VERTICAL);
		displayPrey.addListSelectionListener(this);
		displayPrey.setName("prey");
		JScrollPane listScroller2 = new JScrollPane(displayPrey);
		listScroller2.setPreferredSize(new Dimension(width, height-100));
		//this.add(listScroller);
		
		
		JTabbedPane tp = new JTabbedPane();
		//tp.setLayout(flowLayout2);
		//tp.add("Predator", component)
		tp.add("Predator", listScroller);
		tp.add("Prey", listScroller2);
		tp.setTabPlacement(JTabbedPane.BOTTOM);
		//tp.setPreferredSize(new Dimension(width, height-50));
		this.add(tp);
		
		
		this.add(new JLabel("Epoch:"));
		SpinnerNumberModel model1 = new SpinnerNumberModel(minEpoch, minEpoch, maxEpoch, 1); 
		epochSelector = new JSpinner(model1);
		epochSelector.setValue(0);
		epochSelector.setPreferredSize(new Dimension(width-50, 50));
		epochSelector.addChangeListener(this);
		this.add(epochSelector);
	}
	
	public void update(){
		if (!VisualizerMain.selectedRun.equals("")){
			updateEpochNum();
			updateParticles();
			this.repaint();
		}
		
	}

	private void updateParticles() {
		// TODO Auto-generated method stub
		predParticles = new ArrayList<File>();
		preyParticles = new ArrayList<File>();
		File f = new File(VisualizerMain.experimentBaseLocation+"/"+VisualizerMain.selectedRun+"/Epoch-"+epochSelected+"/");
		File[] listFolders = f.listFiles();
		for(File file : listFolders){
			//if(file.isDirectory())
			predParticles.add(file);
		}
		/*
		f = new File(VisualizerMain.experimentBaseLocation+"/"+VisualizerMain.selectedRun+"/Epoch-"+epochSelected+"/Games/Prey");
		listFolders = f.listFiles();
		for(File file : listFolders){
			if(file.isDirectory())
				preyParticles.add(file);
		}
		*/
		DefaultListModel listModelPredator = new DefaultListModel();
		for (File file: predParticles){
			listModelPredator.addElement(file.getName());
		}
		displayPred.setModel(listModelPredator);
		
		DefaultListModel listModelPrey = new DefaultListModel();
		for (File file: preyParticles){
			listModelPrey.addElement(file.getName());
		}
		displayPrey.setModel(listModelPrey);
	}

	private void updateEpochNum() {
		// TODO Auto-generated method stub
		File epCount = new File(VisualizerMain.experimentBaseLocation+"/"+VisualizerMain.selectedRun);
		File[] list = epCount.listFiles();
		int count = 0;
		for (File file : list){
			if (file.isDirectory())
				count++;
		}
		System.out.println("Folder Count: " + count);
		maxEpoch = count-1;
		SpinnerNumberModel model1 = new SpinnerNumberModel(((Number)epochSelector.getValue()).intValue(), minEpoch, maxEpoch, 1); 
		epochSelector.setModel(model1);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		epochSelected = ((Number)epochSelector.getValue()).intValue();
		updateParticles();
		this.repaint();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		String name = ((JList) e.getSource()).getName();
		if (name.equals("pred")){
			int selected = displayPred.getSelectedIndex();
			if (selected == -1)
				selected = 0;
			String loc = predParticles.get(selected).getPath();
			vm.updateParticleGame(loc);
			
		}
		else{
			int selected = displayPrey.getSelectedIndex();
			String loc = preyParticles.get(selected).getPath();
			vm.updateParticleGame(loc);
		}
		
		
		
	}
	
	
}
