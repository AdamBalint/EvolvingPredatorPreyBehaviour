package simulation;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GameLogger implements Serializable{

	
	private static final long serialVersionUID = 8921384492548109895L;
	
	private ArrayList<ArrayList<Point>> games;
	
	private double particleFitness;
	
	public GameLogger(){
		games = new ArrayList<>();
	}
	
	public ArrayList<Point> getGame(int i){
		return games.get(i);
	}
	
	public void addGame(ArrayList<Point> game){
		ArrayList<Point> copy = new ArrayList<>();
		for (Point g : game){
			copy.add((Point) g.clone());
		}
		games.add(copy);
		//System.out.println(games.get(games.size()-1).toString());
	}
	
	public int getGamesNumber(){
		return games.size();
	}
	
	public double getParticleFitness(){
		return particleFitness;
	}
	public void setParticleFitness(double fitness){
		particleFitness = fitness;
	}
}
