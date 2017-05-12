package visualizer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import simulation.GameLogger;

// Generates the graphs of fitness and the visualization of the games
public class ImageGeneratorMain extends Application{

	String gameRulesBase = "V3/";
	Stage stage;
	
	public ImageGeneratorMain(){
		
	}
	
	int count = 0;
	private void genPictures() {
		// TODO Auto-generated method stub
		File progressionChart = new File("Charts");
		File games = new File("Games");
		progressionChart.mkdirs();
		games.mkdirs();
		File[] fileList = new File(gameRulesBase).listFiles(File::isDirectory);
		
		for (File fil : fileList){
			File[] runFolders = fil.listFiles(File::isDirectory);
			for (File runFolder : runFolders){
				File log = new File(runFolder.getAbsolutePath()+"/RunSummary.txt");
				try {
					Scanner in = new Scanner (log);
					ArrayList<Point2D.Double> predBestAverage = new ArrayList<>();
					ArrayList<Point2D.Double> preyBestAverage = new ArrayList<>();
					
					while(in.hasNextLine()){
						String[] parts = in.nextLine().split("\t");
						double a = Double.parseDouble(parts[0].contains("I") ? "0" : parts[0]), b = Double.parseDouble(parts[1].contains("I") ? "0" : parts[1]);
						predBestAverage.add(new Point2D.Double(a,b));
						a = Double.parseDouble(parts[2].contains("I") ? "0" : parts[2]);
						b = Double.parseDouble(parts[3].contains("I") ? "0" : parts[3]);
						preyBestAverage.add(new Point2D.Double(a,b));
						//System.out.println("Data points: " + predBestAverage.size());
					}
					File f = new File("Charts/"+fil.getName());
					f.mkdirs();
					WritableImage img = makeLineGraph(predBestAverage, preyBestAverage, new File("Charts/"+fil.getName()+"/"+runFolder.getName()+".png"));
					
					int[] predMaxMin = getMaxMin(predBestAverage);
					int[] preyMaxMin = getMaxMin(preyBestAverage);
					
					readDrawGame(fil.getName()+"/"+runFolder.getName(), predMaxMin, true);
					readDrawGame(fil.getName()+"/"+runFolder.getName(), preyMaxMin, false);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.err.println("Did not record: " + runFolder.getAbsolutePath());
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			count ++;
			System.out.println("Processed folder: " + count);
		}
	}


	private int[] getMaxMin(ArrayList<java.awt.geom.Point2D.Double> bestAverage) {
		// TODO Auto-generated method stub
		double[] bestWorstVals = new double[]{-Double.MAX_VALUE, Double.MAX_VALUE};
		int[] bestWorst = new int[2];
		
		for (int i = 0; i < bestAverage.size(); i++){
			Point2D.Double p = bestAverage.get(i);
			if (p.getX() > bestWorstVals[0]){
				bestWorstVals[0] = p.getX();
				bestWorst[0] = i;
			}
			if (p.getX() > bestWorstVals[1]){
				bestWorstVals[1] = p.getY();
				bestWorst[1] = i;
			}
		}
		
		return bestWorst;
	}

	private void readDrawGame(String loc, int[] bestWorst, boolean predator){
		File[] files = new File(gameRulesBase+loc+"/Epoch-"+bestWorst[0]+"/").listFiles();
		//System.out.println(files);
		try {
			FileInputStream fis = new FileInputStream(files[0]);
			ObjectInputStream ois = new ObjectInputStream(fis);
			GameLogger gl = (GameLogger) ois.readObject();
			drawGame(gl, loc, bestWorst, predator, true);
			
			fis = new FileInputStream(files[1]);
			ois = new ObjectInputStream(fis);
			gl = (GameLogger) ois.readObject();
			drawGame(gl, loc, bestWorst, predator, false);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void drawGame(GameLogger gl, String loc, int[] bestWorst, boolean predator, boolean best){
		
		try {
			
			int width = 450, height = 450;
			
			for (int game = 0; game < gl.getGamesNumber(); game++){
			
				ArrayList<Point> moves = gl.getGame(game);
				
				ArrayList<Point> predMoves = new ArrayList<Point>();
				ArrayList<Point> preyMoves = new ArrayList<Point>();
				
				for (int i = 0; i < moves.size(); i+= 2){
					predMoves.add(moves.get(i));
					preyMoves.add(moves.get(i+1));
				}
				
				BufferedImage b = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics g = b.getGraphics();
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, width, height);
				
				g.setColor(Color.BLACK);
				for (int i = 0; i < 10; i++){
					g.drawLine((int)(i*width/9.0), 0, (int)(i*width/9.0), height);
					g.drawLine(0, (int)(i*height/9.0), width, (int)(i*height/9.0));
				}
				
				
				//g.drawRect(0, 0, 50, 50);
				g.setColor(Color.RED);
				if (predMoves != null){
					for (int i = 0; i < predMoves.size()-1; i++){
			
						Point curr = predMoves.get(i), next = predMoves.get(i+1);
						//System.out.println(curr + " to " + next);
						g.drawLine((int)(curr.x*(width/9.0)+width/18.0), (int)(curr.y*(height/9.0)+height/18.0), (int)(next.x*(width/9.0)+width/18.0), (int)(next.y*(height/9.0)+height/18.0));
						g.fillOval((int)(next.x*(width/9.0)+width/18.0-6), (int)(next.y*(height/9.0)+height/18.0-6), 12, 12);
					}
				}
				
				g.setColor(Color.GREEN);
				if (preyMoves != null){
					for (int i = 0; i < preyMoves.size()-1; i++){
			
						Point curr = preyMoves.get(i), next = preyMoves.get(i+1);
						//System.out.println(curr + " to " + next);
						g.drawLine((int)(curr.x*(width/9.0)+width/18.0), (int)(curr.y*(height/9.0)+height/18.0), (int)(next.x*(width/9.0)+width/18.0), (int)(next.y*(height/9.0)+height/18.0));
						g.fillOval((int)(next.x*(width/9.0)+width/18.0-3), (int)(next.y*(height/9.0)+height/18.0-3), 6, 6);
					}
				}
				new File("Games/"+gameRulesBase+loc+(best ? "-Best" : "-worst")+(predator ? "/pred/" : "/prey/")).mkdirs();
				
				ImageIO.write(b, "png", new File("Games/"+gameRulesBase+loc+(best ? "-Best" : "-worst")+(predator ? "/pred" : "/prey")+"/Game-"+game+".png"));
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	private WritableImage makeLineGraph(ArrayList<java.awt.geom.Point2D.Double> predBestAverage,
			ArrayList<java.awt.geom.Point2D.Double> preyBestAverage, File f) {
		// TODO Auto-generated method stub
		
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		
		
		LineChart<Number, Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
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
		
		for (int i = 0; i < predBestAverage.size(); i++){
			predBest.getData().add(new XYChart.Data(i, predBestAverage.get(i).getX()));
			predAvg.getData().add(new XYChart.Data(i, predBestAverage.get(i).getY()));
			preyBest.getData().add(new XYChart.Data(i, preyBestAverage.get(i).getX()));
			preyAvg.getData().add(new XYChart.Data(i, preyBestAverage.get(i).getY()));
			//System.out.println("Added data: " + i);
			//System.out.println(predBestAverage.toString());
		}
		
		//lineChart.getData().addAll(predBest, predAvg, preyBest, preyAvg);
		
		stage = new Stage();
		
		
		//lineChart.getData().removeAll(lineChart.getData());
		lineChart.getData().addAll(predBest, predAvg, preyBest, preyAvg);
		lineChart.getXAxis().setSide(Side.BOTTOM);
		lineChart.getXAxis().setAutoRanging(true);
		lineChart.getYAxis().setSide(Side.LEFT);
		lineChart.getYAxis().setAutoRanging(true);
		lineChart.setAnimated(false);
		
		Scene scene  = new Scene(lineChart, 1000, 600);
		stage.setScene(scene);
		WritableImage img = scene.snapshot(null);
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new ImageGeneratorMain();
		//new ImageGeneratorMain();
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		stage = primaryStage;
		//gameRulesBase = "V1/";
		//genPictures();
		gameRulesBase = "V2/";
		genPictures();
		//gameRulesBase = "V3/";
		//genPictures();
		
		System.exit(0);
	}

}
