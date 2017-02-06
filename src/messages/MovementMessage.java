package messages;

import java.util.Arrays;

import org.apache.commons.math3.linear.RealMatrix;

import storage.Variables;

public class MovementMessage {

	private boolean diagonalFlag;
	private int xDir = 0;
	private int yDir = 0;
	private boolean chaseX = false;
	private boolean chaseY = false;
	
	public MovementMessage(RealMatrix res){
		
		double[] row = res.getRow(0);
		
		if (row[0] <= 0.5){
			diagonalFlag = false;
			double maxVal = row[1];
			int max = 1;
			for (int i = 2; i < row.length; i++){
				if (maxVal < row[i]){
					max = i;
					maxVal = row[i];
				}
			}
			if (max == 1 || max == 2){
				xDir = 3 - (max*2);
				yDir = 0;
			}else {
				xDir = 0;
				yDir = max == 3 ? 1 : -1;
			}
		}else {
			diagonalFlag = true;
			xDir = (row[1] > row[2]) ? 1 : -1;
			yDir = (row[3] > row[4]) ? 1 : -1;
		}
		if (row.length == Variables.outputLayerSizePred){
			if (row[row.length-2] > 0.5)
				chaseX = true;
			if (row[row.length-1] > 0.5)
				chaseY = true;
		}
	}
	
	public boolean getDiagonalFlag(){
		return diagonalFlag;
	}
	
	public int getXMovement(){
		return xDir;
	}
	
	public int getYMovement(){
		return yDir;
	}
	
	public boolean isChasingX(){
		return chaseX;
	}
	public boolean isChasingY(){
		return chaseY;
	}
}
