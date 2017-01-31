package messages;

import java.util.Arrays;

import org.apache.commons.math3.linear.RealMatrix;

public class MovementMessage {

	private boolean diagonalFlag;
	private int xDir;
	private int yDir;
	
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
	
}
