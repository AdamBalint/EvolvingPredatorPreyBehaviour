package test;

import static org.junit.Assert.*;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Test;

import messages.MovementMessage;

public class MovementTest {

	@Test
	public void testDiagonal_Up_Right() {
		RealMatrix res = MatrixUtils.createRealMatrix(1, 5);
		res.setRow(0, new double[]{1,1,0,1,0});
		MovementMessage mov = new MovementMessage(res);
		
		assert(mov.getDiagonalFlag() == true);
		assert(mov.getXMovement() > 0);
		assert(mov.getYMovement() > 0);
	}
	
	@Test
	public void testDiagonal_Up_Left(){
		RealMatrix res = MatrixUtils.createRealMatrix(1, 5);
		res.setRow(0, new double[]{1,0,1,1,0});
		MovementMessage mov = new MovementMessage(res);
		
		assert(mov.getDiagonalFlag() == true);
		assert(mov.getXMovement() < 0);
		assert(mov.getYMovement() > 0);
	}
	
	@Test
	public void testDiagonal_Down_Left(){
		RealMatrix res = MatrixUtils.createRealMatrix(1, 5);
		res.setRow(0, new double[]{1,0,1,0,1});
		MovementMessage mov = new MovementMessage(res);
		
		assert(mov.getDiagonalFlag() == true);
		assert(mov.getXMovement() < 0);
		assert(mov.getYMovement() < 0);
	}
	
	@Test
	public void testDiagonal_Down_Right(){
		RealMatrix res = MatrixUtils.createRealMatrix(1, 5);
		res.setRow(0, new double[]{1,1,0,0,1});
		MovementMessage mov = new MovementMessage(res);
		
		assert(mov.getDiagonalFlag() == true);
		assert(mov.getXMovement() > 0);
		assert(mov.getYMovement() < 0);
	}

	@Test
	public void testRight(){
		RealMatrix res = MatrixUtils.createRealMatrix(1, 5);
		res.setRow(0, new double[]{0, 1,0,0.5,0.2});
		MovementMessage mov = new MovementMessage(res);
		assert(mov.getDiagonalFlag() == false);
		assert(mov.getXMovement() > 0);
		assert(mov.getYMovement() == 0);
	}
	
	@Test
	public void testLeft(){
		RealMatrix res = MatrixUtils.createRealMatrix(1, 5);
		res.setRow(0, new double[]{0, 0.1,0.6,0.5,0.2});
		MovementMessage mov = new MovementMessage(res);
		assert(mov.getDiagonalFlag() == false);
		assert(mov.getXMovement() < 0);
		assert(mov.getYMovement() == 0);
	}
	
	@Test
	public void testUp(){
		RealMatrix res = MatrixUtils.createRealMatrix(1, 5);
		res.setRow(0, new double[]{0, 0.11,0.5,0.55,0.2});
		MovementMessage mov = new MovementMessage(res);
		assert(mov.getDiagonalFlag() == false);
		assert(mov.getXMovement() == 0);
		assert(mov.getYMovement() > 0);
	}
	
	@Test
	public void testDown(){
		RealMatrix res = MatrixUtils.createRealMatrix(1, 5);
		res.setRow(0, new double[]{0, 0.21,0,0.22,0.23});
		MovementMessage mov = new MovementMessage(res);
		assert(mov.getDiagonalFlag() == false);
		assert(mov.getXMovement() == 0);
		assert(mov.getYMovement() < 0);
	}
	
}
