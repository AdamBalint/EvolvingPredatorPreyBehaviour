package pso.particles;

import org.apache.commons.math3.linear.RealMatrix;
import storage.SpeciesType;
import pso.Swarm;
import storage.Variables;

public class Particle implements ParticleInterface{
	
	
	private double personalBestFitness;
	private RealMatrix[] personalBestLoc;
	private SpeciesType sType;
	private RealMatrix[] velocity;
	private RealMatrix[] location;
	private Swarm parentSwarm;
	
	
	public Particle(SpeciesType sType, Swarm parent){
		this.sType = sType;
		parentSwarm = parent;
	}
	
	
	@Override
	public double getFitness() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPersonalBestFit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RealMatrix[] getPersonalBestLoc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RealMatrix[] getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	// updates the velocity of the particle
	private void updateVelocity(){
		RealMatrix[] globalBestLoc = parentSwarm.getGlobalBest();
		for (int i = 0; i < velocity.length; i++){
			velocity[i].scalarMultiply(sType == SpeciesType.PREDATOR ? Variables.inertiaPred : Variables.inertiaPrey);
			
			RealMatrix per = (personalBestLoc[i].subtract(location[i])).scalarMultiply(Math.random()* (sType == SpeciesType.PREDATOR ? Variables.cognitivePred : Variables.cognitivePrey));
			RealMatrix glob = (globalBestLoc[i].subtract(location[i])).scalarMultiply(Math.random()* (sType == SpeciesType.PREDATOR ? Variables.socialPred : Variables.socialPrey));
			
			velocity[i].add(per.add(glob));
		}
	}
	
	// updates the location of the particle
	private void updateLocation(){
		for (int i = 0; i < location.length; i++){
			location[i].add(velocity[i]);
		}
	}
	

	// updates the particle
	@Override
	public void updateParticle() {
		// TODO Auto-generated method stub
		
	}

}
