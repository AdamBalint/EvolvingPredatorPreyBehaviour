package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Test;

import simulation.creature.NeuralNet;
import storage.BrainStorage;
import storage.Variables;

public class NeuralNetTest {

	@Test
	public void test() {
		RealMatrix r = MatrixUtils.createRealMatrix(5, 5);
		r.setColumn(0, new double[]{1,1,0,0,0});
		r.setColumn(1, new double[]{1,0,1,0,1});
		r.setColumn(2, new double[]{1,1,0,0,1});
		r.setColumn(3, new double[]{0,0,1,1,1});
		r.setColumn(4, new double[]{0,0,0,0,0});
		
		RealMatrix inp = MatrixUtils.createRealMatrix(1, 5);
		inp.setRow(0, new double[]{1,0,0,1,1});
		
		NeuralNet n = null;
		try {
			n = new NeuralNet(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Object Not Created Exception");
		}
		n.createNN(5, 5, new int[]{}, this);
		n.setWeights(0, r, this);
		double[] check = {0.7310585786300049, 0.8807970779778823, 0.8807970779778823, 0.8807970779778823,0.5};
		
		assert Arrays.equals(check, n.getRawOutput(inp));
		
		//fail("Not yet implemented");
		
		
		
	}

}
