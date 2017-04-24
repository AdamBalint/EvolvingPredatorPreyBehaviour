package visualizer.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JPanel;

import simulation.GameLogger;

public class Board extends JPanel{

	private int width, height;
	private ArrayList<Point> predMoves;
	private ArrayList<Point> preyMoves;
	private GameLogger gameLogger;
	
	
	public Board(){
		this(410, 410);
	}
	
	public Board(int width, int height){
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.white);
	}
	
	public void loadBoard(int game){
		System.out.println("Game Number: " + game);
		ArrayList<Point> moves = gameLogger.getGame(game);
		
		predMoves = new ArrayList<Point>();
		preyMoves = new ArrayList<Point>();
		
		for (int i = 0; i < moves.size(); i+= 2){
			predMoves.add(moves.get(i));
			preyMoves.add(moves.get(i+1));
		}
		this.repaint();
		/*try {
			
			Scanner in = new Scanner(f);
			while (in.hasNextLine()){
				String line = in.nextLine();
				String[] parts = line.split("\t");
				System.out.println(Arrays.toString(parts));
				String[] loc = parts[0].split(",");
				predMoves.add(new Point (Integer.parseInt(loc[0]), Integer.parseInt(loc[1])));
				if (parts.length == 1)
					break;
				loc = parts[1].split(",");
				preyMoves.add(new Point (Integer.parseInt(loc[0]), Integer.parseInt(loc[1])));
			}
			in.close();
			this.repaint();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		for (int i = 0; i < 10; i++){
			g.drawLine(i*(width/9), 0, i*(width/9), height);
			g.drawLine(0, i*(height/9), width, i*(height/9));
		}
		
		
		//g.drawRect(0, 0, 50, 50);
		g.setColor(Color.RED);
		if (predMoves != null){
			for (int i = 0; i < predMoves.size()-1; i++){
	
				Point curr = predMoves.get(i), next = predMoves.get(i+1);
				System.out.println(curr + " to " + next);
				g.drawLine(curr.x*(width/9)+width/18, curr.y*(height/9)+height/18, next.x*(width/9)+width/18, next.y*(height/9)+height/18);
				g.fillOval(next.x*(width/9)+width/18-6, next.y*(height/9)+height/18-6, 12, 12);
			}
		}
		
		g.setColor(Color.GREEN);
		if (preyMoves != null){
			for (int i = 0; i < preyMoves.size()-1; i++){
				Point curr = preyMoves.get(i), next = preyMoves.get(i+1);
				g.drawLine(curr.x*(width/9)+width/18, curr.y*(height/9)+height/18, next.x*(width/9)+width/18, next.y*(height/9)+height/18);
				g.fillRect(next.x*(width/9)+width/18-3, next.y*(height/9)+height/18-3, 6, 6);
			}
		}
	}

	public void loadGames(File file) {
		// TODO Auto-generated method stub
		
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			gameLogger = (GameLogger) ois.readObject();
			System.out.println(gameLogger.getGame(0).toString());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
		
	}

	public int getGamesNumber() {
		// TODO Auto-generated method stub
		if (gameLogger != null)
			return gameLogger.getGamesNumber();
		return -1;
	}
	
	
}
