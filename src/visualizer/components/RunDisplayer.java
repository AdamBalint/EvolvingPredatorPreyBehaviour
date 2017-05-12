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

// Sets up the display that shows all of the runs of the experiment
public class RunDisplayer extends JPanel implements ListSelectionListener{

	private ArrayList<File> runs;
	private JList runList;
	private VisualizerMain vm;
	public RunDisplayer(VisualizerMain vm){
		this(225, 250, vm);
	}
	
	// Sets up the panel
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
	
	// If a different file was selected, update the available runs
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
	
	// Gets a list of folders that are available (different runs)
	private void getFolderList(){
		runs = new ArrayList<File>();
		File experiment = new File(VisualizerMain.experimentBaseLocation);
		File[] fileList = experiment.listFiles();
		for(int i = 0; i < fileList.length; i++){
			if (fileList[i].isDirectory())
				runs.add(fileList[i]);
		}
		
	}

	// If the value is selected, then update the particles available from that run
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
		int selected = runList.getSelectedIndex();
		VisualizerMain.selectedRun = runs.get(selected).getName();
		vm.updateParticleDisplayer();
	}
	
	
	
}
