package simulation;

import simulation.creature.Creature;
import storage.SpeciesType;
import storage.Variables;

public class Board implements BoardInterface{

	int width, height;
	
	Creature[][] board;
	
	public Board(){
		this(15,15);
	}
	
	public Board(int width, int height){
		this.width = width;
		this.height = height;
		board = new Creature[width][height];
	}
	
	// predator, prey
	public void placeCreatures(){
		
	}
	
	// runs the simulation
	public void runSimulation(){
		for (int i = 0; i < Variables.simulationTurnNum; i++){
			
		}
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
		
		
		
		
		
		return null;
	}
	
}
