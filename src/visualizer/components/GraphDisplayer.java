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

// Displays the fitness graph
public class GraphDisplayer extends JFXPanel{

	private int width, height;
	public GraphDisplayer(){
		this(450, 250);
	}
	LineChart<Number,Number> lineChart;
	
	// Sets up the panel
	public GraphDisplayer(int width, int height){
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		
		XYChart.Series series = new XYChart.Series();
		 
		for (double x = -5.0; x <= 5.0; x+=0.25) {
		    double y = 5.0*Math.sin(x);
		    series.getData().add(new XYChart.Data<>(x, y));
		}
				
		
		
		
		
	}

	
	// Updates the graph with the correct information
	public void update(){
		
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
			while (in.hasNext()){
				String line = in.nextLine();
				String[] parts = line.split("\t");
				if (parts[0].contains("Inf"))
					parts[0] = "0";
				if (parts[2].contains("Inf"))
					parts[2] = "0";
				
				
				
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
			
			System.out.println("Should have updated graph");
			this.repaint();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
