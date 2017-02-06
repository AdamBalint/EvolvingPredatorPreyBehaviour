package simulation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import messages.SimulationLog;
import simulation.creature.Creature;
import simulation.creature.NeuralNet;
import storage.SpeciesType;
import storage.Variables;

public class Board implements BoardInterface, Callable<SimulationLog>{

	int width, height;
	
	Creature[][] board;
	
	ArrayList<Creature> creatures = new ArrayList<Creature>();
	
	SimulationLog log = new SimulationLog();
	
	public Board(){
		this(Variables.boardWidth,Variables.boardHeight);
	}
	
	public Board(int width, int height){
		this.width = width;
		this.height = height;
		board = new Creature[width][height];
	}
	
	// predator, prey
	public void placeCreatures(NeuralNet predatorBrain, NeuralNet preyBrain){
		//Point p1 = new Point(Variables.rand.nextInt(width), Variables.rand.nextInt(height));
		
		// Replace with random point later
		Point p1 = new Point (0,0);
		Point p2;
		do {
			p2 = new Point(Variables.rand.nextInt(width), Variables.rand.nextInt(height));
			
		}while (p1.equals(p2) || p1.distance(p2) < 2);
		
		// Remove later
		p2 = new Point(0,4);
		
		for (int r = 0; r < height; r++){
			for (int c = 0; c < width; c++){
				board[c][r] = null;
				// Predator Creature
				if (new Point (c,r).equals(p1)){
					Creature cr = new Creature(c,r, SpeciesType.PREDATOR, predatorBrain);
					board[c][r] = cr;
					creatures.add(0, cr);
				}
				// Prey Creature
				if (new Point (c,r).equals(p2)){
					Creature cr = new Creature(c,r, SpeciesType.PREY, preyBrain);
					board[c][r] = cr;
					creatures.add(cr);
				}
			}
		}
		
	}
	
	// runs the simulation
	public void runSimulation(){
		double predScore = 0;
		double preyScore = 0;
		
		for (int i = 0; i < Variables.simulationTurnNum; i++){
			for (int cr = 0; cr < creatures.size(); cr++){
				Creature currCreature = creatures.get(cr);
				Point prev = new Point (currCreature.getX(), currCreature.getY());
				currCreature.makeMove(getSurroundings(currCreature));
				board[prev.x][prev.y] = null;
				
				Point n = new Point(currCreature.getX(), currCreature.getY());
				if(n.x >= width || n.x < 0 || n.y >= height || n.y < 0){
					board[prev.x][prev.y] = currCreature;
					break;
				}
				if (board[n.x][n.y] == null){
					board[n.x][n.y] = currCreature;
				}
				else if (board[n.x][n.y].getSpeciesType() == SpeciesType.PREY && currCreature.getSpeciesType() == SpeciesType.PREDATOR){
					// caught the enemy get a score
					board[n.x][n.y] = currCreature;
					//predScore += 0.1;
					//preyScore -= 0.1;
					
					
					// Remove prey from arraylist
					
					
					removePrey(n.x, n.y);
					// increase score of predators
				} else{
					board[prev.x][prev.y] = currCreature;
				}
			}
		}
		int predCount = 0, preyCount = 0;
		
		for (int c = 0; c < width; c++){
			for (int r = 0; r < height; r++){
				if (board[c][r] != null){
					if (board[c][r].getSpeciesType() == SpeciesType.PREDATOR)
						predCount++;
					else
						preyCount++;
				}
			}
		}
		if (predCount > preyCount){
			predScore += 1;
		}
		else if (preyCount >= predCount){
			preyScore += 1;
		}
		log.predatorScore = predScore;
		log.preyScore = preyScore;
	}

	private void removePrey(int x, int y) {
		// TODO Auto-generated method stub
		for (int i = 0; i < creatures.size(); i++){
			Creature c = creatures.get(i);
			if (c.getX() == x && c.getY() == y){
				if(c.getSpeciesType() == SpeciesType.PREY){
					creatures.remove(i);
					break;
				}
			}
		}
	}

	private double[] getSurroundings(Creature creature) {
		// TODO Auto-generated method stub
		// See in 3x3 box around themselves
		
		int start = creature.getSpeciesType() == SpeciesType.PREDATOR? -2 : -1;
		int end = creature.getSpeciesType() == SpeciesType.PREDATOR? 2 : 1;;
		double[] surr = new double[(end-start+1)*(end-start+1)];
		
		for (int i = start; i <= end; i++){
			for (int j = start; j <= end; j++){
				int xLoc = creature.getX()-i;
				int yLoc = creature.getY()-j;
				int ind = (i+end)*(end-start+1)+(j+end);
				if (xLoc < width || xLoc >= width || yLoc < height || yLoc >= height)
					surr[ind] = -2;
				else if(board[xLoc][yLoc] == null)
					surr[ind] = 0;
				else if (board[xLoc][yLoc].getSpeciesType() == creature.getSpeciesType())
					surr[ind] = 1;
				else
					surr[ind] = -1;
			}
		}
		
		return surr;
	}

	@Override
	public int[] getSurroundings(int x, int y, int xOff, int yOff, int xLOS, int yLOS) {
		// TODO Auto-generated method stub
		int xStart = (int) (x-Math.floor(xLOS/2));
		int xEnd = (int) (x+Math.floor(xLOS/2));
		int yStart = (int) (y-Math.floor(yLOS/2));
		int yEnd = (int) (y+Math.floor(yLOS/2));
		
		int [] surr = new int[xLOS*yLOS-1];
		
		SpeciesType curSpecies = board[x][y].getSpeciesType();
		
		for (int i = xStart; i < xEnd; i++){
			for (int j = yStart; j < yEnd; j++){
				if (i < 0 || i > width-1 || j < 0 || j > height-1){
					surr[i*width+j] = -2;
				}
				else if (board[i][j] == null){
					surr[i*width+j] = 0;
				}else{
					if (board[i][j].getSpeciesType() == curSpecies){
						surr[i*width+j] = -1;
					}else{
						surr[i*width+j] = 1;
					}
				}
				
			}
		}
		
		return surr;
	}

	@Override
	public SimulationLog call() throws Exception {
		// TODO Auto-generated method stub
		runSimulation();
		return new SimulationLog(log);
	}
	
}
