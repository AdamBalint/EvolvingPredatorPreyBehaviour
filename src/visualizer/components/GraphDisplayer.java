package visualizer.components;

import java.awt.Color;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

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

import visualizer.VisualizerMain;
/*
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
*/

public class GraphDisplayer extends JFXPanel{

	private int width, height;
	public GraphDisplayer(){
		this(450, 250);
	}
	LineChart<Number,Number> lineChart;
	
	public GraphDisplayer(int width, int height){
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		//this.setBackground(Color.DARK_GRAY);
		
		//DataTable temp = new DataTable(Double.class, Double.class);
		
		
		//final XYChart<Number, Number>.Series series = new XYChart.Series<Number, Number>(xAxis, yAxis);
		XYChart.Series series = new XYChart.Series();
		 
		for (double x = -5.0; x <= 5.0; x+=0.25) {
		    double y = 5.0*Math.sin(x);
		    series.getData().add(new XYChart.Data<>(x, y));
		}
				
		
		
		/*plot = new XYPlot(temp);
		plot.setBounds(new Rectangle(width-20, height-20));
		
		ipane = new InteractivePanel(plot);
		ipane.setPreferredSize(new Dimension(width-20, height-20));
		ipane.setBounds(new Rectangle(width-20, height-20));
		*/
		
		
		
		/*JFrame f = new JFrame("Test");
		f.getContentPane().setPreferredSize(new Dimension(width, height));
		f.add(new JPanel().add(ipane));
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		*/
		
		//this.add(ipane);
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
		
		/*DataTable predBest = new DataTable(Double.class, Double.class);
		DataTable predAvg = new DataTable(Double.class, Double.class);
		DataTable preyBest = new DataTable(Double.class, Double.class);
		DataTable preyAvg = new DataTable(Double.class, Double.class);
		
		
		*/
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		
		lineChart = new LineChart<Number,Number>(xAxis,yAxis);
		lineChart.setTitle("Fitness Over Time");
		xAxis.setLabel("Generation/Epoch");
		yAxis.setLabel("Fitness");
		lineChart.setCreateSymbols(false);
		
		int count = 0;
		XYChart.Series predBest = new XYChart.Series();
		predBest.setName("Predator Best");
		XYChart.Series predAvg = new XYChart.Series();
		predAvg.setName("Predator Avg");
		XYChart.Series preyBest = new XYChart.Series();
		preyBest.setName("Prey Best");
		XYChart.Series preyAvg = new XYChart.Series();
		preyAvg.setName("Prey Avg");
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
				
				
				
				predBest.getData().add(new XYChart.Data<>(count, Double.parseDouble(parts[0])));
				predAvg.getData().add(new XYChart.Data<>(count, Double.parseDouble(parts[1])));
				preyBest.getData().add(new XYChart.Data<>(count, Double.parseDouble(parts[2])));
				preyAvg.getData().add(new XYChart.Data<>(count, Double.parseDouble(parts[3])));
				count++;
			}
			
			Scene scene  = new Scene(lineChart,width,height);
			lineChart.getData().removeAll(lineChart.getData());
			lineChart.getData().addAll(predBest, predAvg, preyBest, preyAvg);
			
			this.setScene(scene);
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
