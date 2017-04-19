package visualizer.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.*;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.ui.InteractivePanel;
import visualizer.VisualizerMain;
/*
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
*/

public class GraphDisplayer extends JPanel{

	private XYPlot plot;
	private InteractivePanel ipane;
	private int width, height;
	public GraphDisplayer(){
		this(450, 150);
	}
	
	public GraphDisplayer(int width, int height){
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		//this.setBackground(Color.DARK_GRAY);
		
		DataTable temp = new DataTable(Double.class, Double.class);
		
		for (double x = -5.0; x <= 5.0; x+=0.25) {
		    double y = 5.0*Math.sin(x);
		    temp.add(x, y);
		}
				
		plot = new XYPlot(temp);
		plot.setBounds(new Rectangle(width-20, height-20));
		
		ipane = new InteractivePanel(plot);
		ipane.setPreferredSize(new Dimension(width-20, height-20));
		ipane.setBounds(new Rectangle(width-20, height-20));
		
		
		
		
		/*JFrame f = new JFrame("Test");
		f.getContentPane().setPreferredSize(new Dimension(width, height));
		f.add(new JPanel().add(ipane));
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		*/
		
		this.add(ipane);
		//this.add(new JLabel("This is a panel that exists!"));
		
		//this.add(new JLabel("",new ImageIcon(ipane.getGraphics().create())));
		
		
		//JFreeChart objChart = ChartFactory.createLineChart("Fitness Over Time", "Generation", "Fitness", null);
		//ChartPanel cp = new ChartPanel(objChart);
		//cp.setPreferredSize(new Dimension(width, height));
		//this.add(cp);
		//this.revalidate();
		//this.repaint();
		
	}

	
	
	public void update(){
		
		DataTable predBest = new DataTable(Double.class, Double.class);
		DataTable predAvg = new DataTable(Double.class, Double.class);
		DataTable preyBest = new DataTable(Double.class, Double.class);
		DataTable preyAvg = new DataTable(Double.class, Double.class);
		
		int count = 0;
		Scanner in;
		try {
			in = new Scanner(new File(VisualizerMain.experimentBaseLocation+"/"+VisualizerMain.selectedRun+"/RunSummary.txt"));
			//System.out.println(VisualizerMain.experimentBaseLocation+"/"+VisualizerMain.selectedRun+"/RunSummary.txt");
			while (in.hasNext()){
				String line = in.nextLine();
				//System.out.println("Line: " + line);
				String[] parts = line.split("\t");
				if (parts[0].contains("Inf"))
					parts[0] = "0";
				if (parts[2].contains("Inf"))
					parts[2] = "0";
				//System.out.println("Parts: " + Arrays.toString(parts));
				predBest.add((double)count, Double.parseDouble(parts[0]));
				predAvg.add((double)count, Double.parseDouble(parts[1]));
				preyBest.add((double)count, Double.parseDouble(parts[2]));
				preyAvg.add((double)count, Double.parseDouble(parts[3]));
				count++;
			}
			
			/*for (double x = -5.0; x <= 5.0; x+=0.25) {
	            double y = 5.0*Math.sin(x);
	            predBest.add(x, y);
	        }*/
			
			
			//System.out.println("Count: " + count);
			//plot.clear();
			
			//JFrame f = new JFrame();
			//this.removeAll();
			//plot.add(predBest);
			//ipane = new InteractivePanel(plot);
			//this.add(ipane);
			
			//ipane.repaint();
			//ipane.revalidate();
			//this.repaint();
			//this.revalidate();
			/*f.pack();
			f.setVisible(true);
			*/
			//plot.add(predAvg);
			//plot.add(preyBest);
			//plot.add(preyAvg);
			
			//System.out.println(predBest.);
			
			System.out.println("Should have updated graph");
			this.repaint();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
