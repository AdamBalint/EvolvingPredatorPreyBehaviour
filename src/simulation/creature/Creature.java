package simulation.creature;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import messages.MovementMessage;
import storage.SpeciesType;
import storage.Variables;

public class Creature {

	private int x, y;
	private SpeciesType speciesType;
	private NeuralNet brain;
	
	public Creature(int x, int y, SpeciesType type, NeuralNet brain){
		this.x = x;
		this.y = y;
		speciesType = type;
		this.brain = brain;
	}
	
	
	public SpeciesType getSpeciesType(){
		return speciesType;
	}


	public void makeMove(double[] surroundings) {
		// TODO Auto-generated method stub
		
		//if (speciesType == SpeciesType.PREDATOR){
			RealMatrix inp = MatrixUtils.createRowRealMatrix(surroundings);
			MovementMessage mov = brain.getOutput(inp);
			
			int multiplierX = 1;
			int multiplierY = 1;
			if (mov.isChasingX())
				multiplierX = 2;
			if (mov.isChasingY())
				multiplierY = 2;
			
			int nx = x+mov.getXMovement() * multiplierX;
			int ny = y+mov.getYMovement() * multiplierY;
			if (nx >= Variables.boardWidth || nx < 0 || ny >= Variables.boardHeight || ny < 0){
				return;
			}
			
			x += mov.getXMovement();
			y += mov.getYMovement();
		//}
		
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	
	
}
