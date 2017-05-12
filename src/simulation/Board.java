package simulation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import messages.SimulationLog;
import simulation.creature.Creature;
import simulation.creature.NeuralNet;
import storage.SpeciesType;
import storage.Variables;

// This class stores the board used for the simulation
public class Board implements BoardInterface, Callable<SimulationLog>{

	int width, height;
	
	Creature[][] board;
	
	ArrayList<Creature> creatures = new ArrayList<Creature>();
	
	SimulationLog log = new SimulationLog();
	
	// Sets up the board
	public Board(){
		this(Variables.boardWidth,Variables.boardHeight);
	}
	
	public Board(int width, int height){
		this.width = width;
		this.height = height;
		board = new Creature[width][height];
	}
	
	// Places the creatures on the board
	public void placeCreatures(NeuralNet predatorBrain, NeuralNet preyBrain){
		// Generates the predator location
		Point p1 = new Point(Variables.rand.nextInt(width), Variables.rand.nextInt(height/2));
		
		// Generates the prey location
		Point p2 = new Point(Variables.rand.nextInt(width), (height/2)+Variables.rand.nextInt(height/2));
			
		// Places the agents into the board array
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
		log.movementLog = new ArrayList<Point>();
		Creature creat = creatures.get(0);
		log.movementLog.add(new Point(creat.getX(), creat.getY()));
		creat = creatures.get(1);
		log.movementLog.add(new Point(creat.getX(), creat.getY()));
		boolean endSimulation = false;
		boolean predFallen = false, preyFallen = false;
		// Runs the simulation for n turns
		for (int i = 0; i < Variables.simulationTurnNum; i++){
			// Moves the creatures
			for (int cr = 0; cr < creatures.size(); cr++){
				Creature currCreature = creatures.get(cr);
				Point prev = new Point (currCreature.getX(), currCreature.getY());
				currCreature.makeMove(getSurroundings(currCreature));
				Point n = new Point(currCreature.getX(), currCreature.getY());
				log.movementLog.add((Point)n.clone());
			}
			// Checks if either agent fell off the board
			Creature pred = creatures.get(0);
			Creature prey = creatures.get(1);
			if (Variables.canFall){
				if (offBoard(pred))
					predFallen = true;
				if (offBoard(prey))
					preyFallen = true;
				if (predFallen || preyFallen)
					break;
			}
			if (pred.getX() == prey.getX() && pred.getY() == prey.getY()){
				endSimulation = true;
			}
			if (endSimulation)
				break;
			
			// If neither agent fell and the game has not ended update
			// the board matrix representation
			updateBoard();
		}
		int predCount = 0, preyCount = 0;
		
		// Score the agents based on the result
		if (predFallen && preyFallen){
			predScore -= 2;
			preyScore -= 2;
		}
		else if (endSimulation){
			predScore += 1;
			preyScore -= 1;
		}
		else if (predFallen){
			predScore -= 2;
			//preyScore += 1;
		} else if (preyFallen){
			preyScore -= 2;
			//predScore -= 1;
		} else if(!endSimulation){
			predScore -= 1;
			preyScore += 1;
		}
		
		
		log.predatorScore = predScore;
		log.preyScore = preyScore;
	}
	
	// Check if agent fell off the board or not
	private boolean offBoard(Creature creat) {
		// TODO Auto-generated method stub
		if (creat.getX() < 0 || creat.getX() >= Variables.boardWidth || creat.getY() < 0 || creat.getY() >= Variables.boardHeight)
			return true;		
		return false;
	}

	// Removes the prey if caught (from an older implementation)
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

	// Encodes the board, 
	private double[] getSurroundings(Creature creature) {
		// TODO Auto-generated method stub
		// See in 3x3 box around themselves
		
		double[] surr = new double[Variables.inputLayerSizePred];
		for (int i = 0; i < Variables.boardWidth; i++){
			for (int j = 0; j < Variables.boardHeight; j++){	
				if(board[i][j] == null)
					surr[i*Variables.boardWidth+j] = 0;
				else if (board[i][j].getSpeciesType() == creature.getSpeciesType())
					surr[i*Variables.boardWidth+j] = 1;
				else
					surr[i*Variables.boardWidth+j] = -1;
			}
		}
		return surr;
		
	}

	// Updates the location of the agents
	private void updateBoard(){
		board = new Creature[board.length][board[0].length];
		for (Creature curr : creatures){
			board[curr.getX()][curr.getY()] = curr;
		}
	}
	
	
	// Implementation for the FOV version
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

	// Runs the sumulation and returns the log of what happened
	@Override
	public SimulationLog call() throws Exception {
		// TODO Auto-generated method stub
		runSimulation();
		return new SimulationLog(log);
	}
	
}
