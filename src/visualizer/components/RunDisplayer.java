package visualizer.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import visualizer.VisualizerMain;

public class RunDisplayer extends JPanel implements ListSelectionListener{

	private ArrayList<File> runs;
	private JList runList;
	private VisualizerMain vm;
	public RunDisplayer(VisualizerMain vm){
		this(225, 250, vm);
	}
	
	public RunDisplayer(int width, int height, VisualizerMain vm){
		this.vm = vm;
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.GREEN);
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		this.setLayout(flowLayout);
		
		runs = new ArrayList<File>();
		
		
		runList = new JList(); 
		runList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		runList.setLayoutOrientation(JList.VERTICAL);
		runList.addListSelectionListener(this);
		JScrollPane listScroller = new JScrollPane(runList);
		listScroller.setPreferredSize(new Dimension(width, height));
		
		this.add(listScroller);
	}
	
	public void update(){
		if (!VisualizerMain.experimentBaseLocation.equals("")){
			getFolderList();
			DefaultListModel listModel = new DefaultListModel();
			for (File f: runs){
				listModel.addElement(f.getName());
			}
			runList.setModel(listModel);
			System.out.println("Elements: " + runs.size());
			this.repaint();
		}
	}
	
	private void getFolderList(){
		runs = new ArrayList<File>();
		File experiment = new File(VisualizerMain.experimentBaseLocation);
		File[] fileList = experiment.listFiles();
		for(int i = 0; i < fileList.length; i++){
			if (fileList[i].isDirectory())
				runs.add(fileList[i]);
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
		int selected = runList.getSelectedIndex();
		VisualizerMain.selectedRun = runs.get(selected).getName();
		vm.updateParticleDisplayer();
	}
	
	
	
}
