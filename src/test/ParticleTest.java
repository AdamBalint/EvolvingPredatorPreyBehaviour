package test;

import static org.junit.Assert.*;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Test;

import pso.Swarm;
import pso.particles.Particle;
import storage.BrainStorage;
import storage.SpeciesType;
import storage.Variables;

public class ParticleTest {

	@Test
	public void test() {
		Variables.popSizePred = 1;
		Variables.popSizePrey = 0;
		Variables.hiddenLayerSizesPred = new int[0];
		Variables.brainStorage = new BrainStorage();
		RealMatrix r = MatrixUtils.createRealMatrix(5, 5);
		r.setColumn(0, new double[]{1,1,0,0,0});
		r.setColumn(1, new double[]{1,0,1,0,1});
		r.setColumn(2, new double[]{1,1,0,0,1});
		r.setColumn(3, new double[]{0,0,1,1,1});
		r.setColumn(4, new double[]{0,0,0,0,0});
		
		Swarm s = new Swarm(1, SpeciesType.PREDATOR, 0);
		//s.getGlobalBest();
		Particle p = s.getParticle(0);
		p.getPersonalBestLoc();
		p.getPersonalBestLoc()[0] = r;
		
		
		for (int i = 0; i < p.getPersonalBestLoc().length; i++){
			System.out.println(p.getPersonalBestLoc()[0]);
			System.out.println("\n\n" + r);
			assert(p.getPersonalBestLoc()[i] == r);
		}
		
		r.addToEntry(0, 0, 2.0);
		for (int i = 0; i < p.getPersonalBestLoc().length; i++){
			System.out.println(p.getPersonalBestLoc()[0]);
			System.out.println("\n\n" + r);
			assert(p.getPersonalBestLoc()[i] == r);
		}
		//fail("Not yet implemented");
		
	}

}
