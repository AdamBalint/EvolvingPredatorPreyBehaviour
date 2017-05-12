package simulation;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

// Logs all the games that an agent plays. This is what is written to a file for later use
public class GameLogger implements Serializable{

	
	private static final long serialVersionUID = 8921384492548109895L;
	// Stores all the games at a specific epoch/generation
	private ArrayList<ArrayList<Point>> games;
	
	private double particleFitness;
	
	public GameLogger(){
		games = new ArrayList<>();
	}
	
	public ArrayList<Point> getGame(int i){
		return games.get(i);
	}
	
	// Adds a game to be stored
	public void addGame(ArrayList<Point> game){
		ArrayList<Point> copy = new ArrayList<>();
		for (Point g : game){
			copy.add((Point) g.clone());
		}
		games.add(copy);
	}
	
	// Returns the number of games
	public int getGamesNumber(){
		return games.size();
	}
	
	// Returns the particle's fitness
	public double getParticleFitness(){
		return particleFitness;
	}
	
	// Sets the particle's fitness
	public void setParticleFitness(double fitness){
		particleFitness = fitness;
	}
}
