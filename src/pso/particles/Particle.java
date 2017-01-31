package pso.particles;

import java.util.Arrays;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import storage.SpeciesType;
import pso.Swarm;
import storage.Variables;

public class Particle implements ParticleInterface{
	
	
	private double personalBestFitness = Double.NEGATIVE_INFINITY;
	private RealMatrix[] personalBestLoc;
	private SpeciesType sType;
	private RealMatrix[] velocity;
	private RealMatrix[] location;
	private Swarm parentSwarm;
	private double currFit;
	
	public Particle(SpeciesType sType, Swarm parent, int brainNum){
		this.sType = sType;
		parentSwarm = parent;
		initParticle(sType, parent, brainNum);
		updateFitness();
	}
	
	
	private void initParticle(SpeciesType sType2, Swarm parent, int brainNum) {
		// TODO Auto-generated method stub
		location = Variables.brainStorage.getParticleBrain(sType, parent.getSpeciesNumber(), brainNum).getBrain();
		velocity = new RealMatrix[location.length];
		for (int i = 0; i < velocity.length; i++){
			velocity[i] = MatrixUtils.createRealMatrix(location[i].getRowDimension(), location[i].getColumnDimension());
			for(int r = 0; r < velocity[i].getRowDimension(); r++){
				for(int c = 0; c < velocity[i].getColumnDimension(); c++){
					velocity[i].addToEntry(r, c, Variables.rand.nextDouble());
				}
			}
		}
	}


	@Override
	public double getFitness() {
		// TODO Auto-generated method stub
		return currFit;
	}

	@Override
	public double getPersonalBestFit() {
		// TODO Auto-generated method stub
		return personalBestFitness;
	}

	@Override
	public RealMatrix[] getPersonalBestLoc() {
		// TODO Auto-generated method stub
		return personalBestLoc;
	}

	@Override
	public RealMatrix[] getLocation() {
		// TODO Auto-generated method stub
		return location;
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
	
	private double calculateFitness(){
		
		// Run simulation n times
		// thread the simulation runs
		
		return 0;
	}
	
	
	private void updateFitness(){
		currFit = calculateFitness();
		if (currFit >= personalBestFitness || personalBestFitness == Double.NEGATIVE_INFINITY){
			personalBestFitness = currFit;
			personalBestLoc = location.clone();
		}
	}
	
	
	// updates the particle
	@Override
	public void updateParticle() {
		// TODO Auto-generated method stub
		updateVelocity();
		updateLocation();
		updateFitness();
	}

}
