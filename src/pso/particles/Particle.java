package pso.particles;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import storage.SpeciesType;
import pso.Swarm;
import simulation.SimulationMaster;
import storage.Variables;

// Stores all the information required by a particle
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
	
	// sets up the particle
	public Particle(SpeciesType sType, Swarm parent, int brainNum, boolean charged){
		this.sType = sType;
		parentSwarm = parent;
		this.brainNum = brainNum;
		this.charged = charged;
		initParticle(sType, parent, brainNum);
		updateFitness();
	}
	
	// Initializes a random location (from weights of NN) and velocity to the particle
	private void initParticle(SpeciesType sType2, Swarm parent, int brainNum) {
		// TODO Auto-generated method stub
		location = Variables.brainStorage.getParticleBrain(sType, parent.getSpeciesNumber(), brainNum).getBrain();
		newLocation = new RealMatrix[location.length];
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

	// Returns the particle's current fitness
	@Override
	public double getFitness() {
		// TODO Auto-generated method stub
		return currFit;
	}

	// Returns the particle's best fitness
	@Override
	public double getPersonalBestFit() {
		// TODO Auto-generated method stub
		return personalBestFitness;
	}

	// Returns the particle's best location
	@Override
	public RealMatrix[] getPersonalBestLoc() {
		// TODO Auto-generated method stub
		RealMatrix[] copy = new RealMatrix[personalBestLoc.length];
		
		for (int i = 0; i < copy.length; i++){
			copy[i] = personalBestLoc[i].copy();
		}
		
		return copy;
	}
	
	// Returns the particle's current location
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
		RealMatrix[] force = parentSwarm.calculateForce(getLocation(), brainNum);
		for (int i = 0; i < velocity.length; i++){
			velocity[i] = velocity[i].scalarMultiply(sType == SpeciesType.PREDATOR ? Variables.inertiaPred : Variables.inertiaPrey);
			
			// personal component
			RealMatrix per = (personalBestLoc[i].subtract(location[i])).scalarMultiply(Math.random()* (sType == SpeciesType.PREDATOR ? Variables.cognitivePred : Variables.cognitivePrey));
			// global component
			RealMatrix glob = (globalBestLoc[i].subtract(location[i])).scalarMultiply(Math.random()* (sType == SpeciesType.PREDATOR ? Variables.socialPred : Variables.socialPrey));
			if (charged){
				// charge force 
				velocity[i] = velocity[i].add(per.add(glob.add(force[i])));
			}
			else{
				velocity[i] = velocity[i].add(per.add(glob));
			}
		}
	}
	
	// updates the location of the particle
	private void updateLocation(){
		for (int i = 0; i < location.length; i++){
			newLocation[i] = location[i].add(velocity[i]);
		}
	}
	
	// Randomizes the particle (another method to increase diversity in the swarm)
	// Not currently used
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

	// Calculates the fitness of the particle
	private double calculateFitness(){
		
		// Runs simuations in order to calculate the fitness of the particle
		sm = new SimulationMaster(sType, Variables.brainStorage.getParticleBrain(sType, parentSwarm.getSpeciesNumber(), brainNum), brainNum);
		double score = sm.runSimulations();
		
		return score;
	}
	
	// Updates the fitness of the particle
	private void updateFitness(){
		currFit = calculateFitness();
		if (currFit >= personalBestFitness || personalBestFitness == Double.NEGATIVE_INFINITY){
			personalBestFitness = currFit;
			personalBestLoc = getLocation();
		}
	}
	
	
	// Updates the particle and stores the new position until the step
	@Override
	public void updateParticle() {
		// TODO Auto-generated method stub
		updateVelocity();
		updateLocation();
		updateFitness();
	}
	
	// Updates the location of the particle
	public void stepParticle(){
		for (int p = 0; p < location.length; p++){
			for(int r = 0; r < location[p].getRowDimension(); r++){
				for (int c = 0; c < location[p].getColumnDimension(); c++){
					location[p].setEntry(r, c, newLocation[p].getEntry(r, c));
				}
			}
		}
	}
	
	// Writes the games of the particle
	public void writeGames(){
		sm.saveSimulationGames();
	}
	
	// Returns the NN number that this particle is representing
	public int getBrainNum(){
		return brainNum;
	}
	
	// Returns if the particle is charged or not
	public boolean isCharged(){
		return charged;
	}
	
}
