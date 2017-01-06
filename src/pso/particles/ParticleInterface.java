package pso.particles;

import org.apache.commons.math3.linear.RealMatrix;

public interface ParticleInterface {
	
	public double getFitness();
	public double getPersonalBestFit();
	public RealMatrix[] getPersonalBestLoc();
	public RealMatrix[] getLocation();
	public void updateParticle();
		
}
