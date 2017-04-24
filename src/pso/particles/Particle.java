package pso.particles;

import java.util.Arrays;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import storage.SpeciesType;
import pso.Swarm;
import simulation.SimulationMaster;
import storage.Variables;

public class Particle implements ParticleInterface{
	
	
	private double personalBestFitness = Double.NEGATIVE_INFINITY;
	private RealMatrix[] personalBestLoc;
	private SpeciesType sType;
	private RealMatrix[] velocity;
	private RealMatrix[] location;
	private Swarm parentSwarm;
	private double currFit;
	private int brainNum;
	private boolean charged = false;
	private RealMatrix[] newLocation;
	private SimulationMaster sm;
	
	public Particle(SpeciesType sType, Swarm parent, int brainNum, boolean charged){
		this.sType = sType;
		parentSwarm = parent;
		this.brainNum = brainNum;
		this.charged = charged;
		initParticle(sType, parent, brainNum);
		updateFitness();
	}
	
	
	private void initParticle(SpeciesType sType2, Swarm parent, int brainNum) {
		// TODO Auto-generated method stub
		location = Variables.brainStorage.getParticleBrain(sType, parent.getSpeciesNumber(), brainNum).getBrain();
		newLocation = new RealMatrix[location.length];
		/*System.out.println("Brain Storage NN: \n" + Arrays.toString(Variables.brainStorage.getParticleBrain(sType, parent.getSpeciesNumber(), brainNum).getBrain()));
		System.out.println("Particle NN: \n" + Arrays.toString(location));
		location[0] = location[0].scalarMultiply(5.0);
		System.err.println("Brain Storage NN: \n" + Arrays.toString(Variables.brainStorage.getParticleBrain(sType, parent.getSpeciesNumber(), brainNum).getBrain()));
		System.err.println("Particle NN: \n" + Arrays.toString(location));
		
		System.exit(0);*/
		velocity = new RealMatrix[location.length];
		for (int i = 0; i < velocity.length; i++){
			velocity[i] = MatrixUtils.createRealMatrix(location[i].getRowDimension(), location[i].getColumnDimension());
			for(int r = 0; r < velocity[i].getRowDimension(); r++){
				for(int c = 0; c < velocity[i].getColumnDimension(); c++){
					velocity[i].addToEntry(r, c, Variables.rand.nextDouble()*2-1);
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
		RealMatrix[] copy = new RealMatrix[personalBestLoc.length];
		
		for (int i = 0; i < copy.length; i++){
			copy[i] = personalBestLoc[i].copy();
		}
		
		return copy;
	}

	@Override
	public RealMatrix[] getLocation() {
		// TODO Auto-generated method stub
		RealMatrix[] copy = new RealMatrix[location.length];
		for (int i = 0; i < copy.length; i++){
			copy[i] = location[i].copy();
		}
		
		return copy;
	}

	// updates the velocity of the particle
	private void updateVelocity(){
		RealMatrix[] globalBestLoc = parentSwarm.getGlobalBest();
		for (int i = 0; i < velocity.length; i++){
			velocity[i] = velocity[i].scalarMultiply(sType == SpeciesType.PREDATOR ? Variables.inertiaPred : Variables.inertiaPrey);
			
			// personal component
			RealMatrix per = (personalBestLoc[i].subtract(location[i])).scalarMultiply(Math.random()* (sType == SpeciesType.PREDATOR ? Variables.cognitivePred : Variables.cognitivePrey));
			// global component
			RealMatrix glob = (globalBestLoc[i].subtract(location[i])).scalarMultiply(Math.random()* (sType == SpeciesType.PREDATOR ? Variables.socialPred : Variables.socialPrey));
			if (charged){
				// charge force
				RealMatrix force = parentSwarm.calculateForce(location[i].copy(), brainNum, i).scalarMultiply(Math.random()*(sType == SpeciesType.PREDATOR? Variables.chargeCoeffPred : Variables.chargeCoeffPrey));
				velocity[i] = velocity[i].add(per.add(glob.add(force)));
			}
			else{
				velocity[i] = velocity[i].add(per.add(glob));
			}
		}
	}
	
	// updates the location of the particle
	private void updateLocation(){
		/*if (charged && Math.random() < 0.01)
		randomizeParticle();
		else*/
		for (int i = 0; i < location.length; i++){
			
			newLocation[i] = location[i].add(velocity[i]);
		}
	}
	
	private void randomizeParticle() {
		// TODO Auto-generated method stub
		System.out.println("Randomized Location");
		for (int p = 0; p < location.length; p++){
			newLocation[p] = MatrixUtils.createRealMatrix(location[p].getRowDimension(), location[p].getColumnDimension());
			for (int r = 0; r < location[p].getRowDimension(); r++){
				for (int c = 0; c < location[p].getColumnDimension(); c++){
					newLocation[p].setEntry(r, c, Math.random()*-0.5);
				}
			}
		}
	}


	private double calculateFitness(){
		
		// Run simulation n times
		// thread the simulation runs
		sm = new SimulationMaster(sType, Variables.brainStorage.getParticleBrain(sType, parentSwarm.getSpeciesNumber(), brainNum), brainNum);
		double score = sm.runSimulations();
		
		return score;
	}
	
	
	private void updateFitness(){
		currFit = calculateFitness();
		if (currFit >= personalBestFitness || personalBestFitness == Double.NEGATIVE_INFINITY){
			personalBestFitness = currFit;
			personalBestLoc = getLocation();
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
	
	public void stepParticle(){
		/*RealMatrix[] copy = new RealMatrix[newLocation.length];
		for (int i = 0; i < copy.length; i++){
			copy[i] = newLocation[i].copy();
		}*/
		for (int p = 0; p < location.length; p++){
			for(int r = 0; r < location[p].getRowDimension(); r++){
				for (int c = 0; c < location[p].getColumnDimension(); c++){
					location[p].setEntry(r, c, newLocation[p].getEntry(r, c));
				}
			}
		}
		//location = copy;
	}
	
	public void writeGames(){
		sm.saveSimulationGames();
	}
	
	public int getBrainNum(){
		return brainNum;
	}
	
	public boolean isCharged(){
		return charged;
	}
	
}
