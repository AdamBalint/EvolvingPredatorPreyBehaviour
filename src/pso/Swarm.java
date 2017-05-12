package pso;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import org.apache.commons.math3.linear.RealMatrix;

import pso.particles.Particle;
import simulation.SimulationMaster;
import simulation.creature.NeuralNet;
import storage.SpeciesType;
import storage.Variables;

public class Swarm {

	private Particle [] swarm;
	private int speciesNum;
	private RealMatrix[] globalBest;
	private Vector<RealMatrix[]> priorBests = new Vector<>();
	private double globalBestFitness = Double.NEGATIVE_INFINITY;
	private SpeciesType sType;
	
	private double coreRad = 2;
	private double perceptionLimit = 40;
	private int bestParticle;
	
	// Sets up the swarm
	public Swarm(int num, SpeciesType sType, int speciesNumber){
		coreRad = Variables.coreRad;
		perceptionLimit = Variables.perceptionLimit;
		System.err.println("Swarm - Constructor");
		swarm = new Particle[num];
		this.sType = sType;
		setUpParticles();
	}
	
	// Creates the particles
	private void setUpParticles() {
		// TODO Auto-generated method stub
		System.err.println("Swarm - Setting up particles");
		double percentCharged = sType == SpeciesType.PREDATOR ? Variables.predPercentCharged : Variables.preyPercentCharged;
		for (int i = 0; i < swarm.length; i++){
			swarm[i] = new Particle(sType, this, i, i > swarm.length*(1-percentCharged) ? true : false);
		}
	}
	
	// Returns if the particle is charged or not
	public boolean particleIsCharged(int num){
		return swarm[num].isCharged();
	}
	
	// Returns the size of the swarm
	public int getSwarmSize(){
		return swarm.length;
	}
	
	// Returns the location of the global best particle
	public RealMatrix[] getGlobalBest(){
		RealMatrix[] copy = new RealMatrix[globalBest.length];
		for (int i = 0; i < copy.length; i++){
			copy[i] = globalBest[i].copy();
		}
		
		return copy;
	}
	
	// Returns the best fitness
	public double getGlobalBestFitness(){
		return globalBestFitness;
	}
	
	// Updates the population
	public void updatePopulation(){
		//System.err.println("Swarm - Updating Population");
		evaluatePopulation();
		updateLocations();
	}

	// Updates the location of the swarm
	private void updateLocations() {
		// TODO Auto-generated method stub
		
		// Parallelizes the particle update
		List<Particle> swarmList = Arrays.asList(swarm);
		swarmList.parallelStream().forEach(p -> p.updateParticle());
		
		// Iterative version (Left in just in case)
		/*for (int i = 0; i < swarm.length; i++){
			swarm[i].updateParticle();
		}*/
	}

	double epochBestFit = 0;
	int epochBestParticle = 0;
	
	// Updates the global best
	private void evaluatePopulation() {
		epochBestFit = -Double.MAX_VALUE;
		epochBestParticle = 0;
		
		// Could have just 1 call to method
		double maxFit = globalBestFitness;
		RealMatrix[] bestLoc = null;
		
		// Loops through the particles
		for (int i = 0; i < swarm.length; i++){
			Particle p = swarm[i];
			if (p.getFitness() > globalBestFitness){
				maxFit = p.getPersonalBestFit();
				bestLoc = p.getPersonalBestLoc();
				bestParticle = i;
			}
			if (p.getFitness() > epochBestFit){
				epochBestFit = p.getFitness();
				epochBestParticle = i;
			}
		}
		// Writes the games of the best particle
		swarm[epochBestParticle].writeGames();
		
		// If if a new best location was found then add it to the HOF
		if (bestLoc != null){
			globalBestFitness = maxFit;
			globalBest = bestLoc;
			if (priorBests.size() == 25)
				priorBests.remove(0);
			priorBests.add(bestLoc);
		}		
	}
	
	// Returns the particle
	public Particle getParticle(int particleNum){
		return swarm[particleNum];
	}
	
	// Returns which species the particle is
	public int getSpeciesNumber(){
		return speciesNum;
	}

	// Calculates and returns the average fitness of the swarm
	public double getAverageFitness() {
		// TODO Auto-generated method stub
		double fit = 0;
		for (int i = 0; i < swarm.length; i++){
			fit += swarm[i].getFitness();
		}
		return fit/swarm.length;
	}
	
	// Updates the location of each particle
	public void stepPopulation(){
		for (int i = 0; i < swarm.length; i++){
			swarm[i].stepParticle();
			
			
		}
	}
	
	// Returns the best particle number
	public int getBestParticleNum(){
		return bestParticle;
	}
	
	// Recalculates the fitness of the currently stored best location
	public void recalculateBest() {
		// TODO Auto-generated method stub
		SimulationMaster sm = new SimulationMaster(sType, new NeuralNet(sType, priorBests.lastElement()), -1);
		double nFitness = sm.runSimulations();
		globalBestFitness = nFitness;
		
		for (RealMatrix[] oldBest : priorBests){
			sm = new SimulationMaster(sType, new NeuralNet(sType, oldBest), -1);
			nFitness = sm.runSimulations();
			if (nFitness > globalBestFitness){
				globalBest = oldBest;
				globalBestFitness = nFitness;
				System.err.println("Prior Fitness has better fitness");
			}
		}
	}
	
	// Calculates the force on the charged particles
	public RealMatrix[] calculateForce(RealMatrix[] loc, int brainNum){
		RealMatrix[] force = null;
		for (Particle p : swarm){
			if (p.isCharged() && p.getBrainNum() != brainNum){
				RealMatrix[] part = p.getLocation();
				// Calculates the Euclidean distance
				double calcDistance = dist(loc, part);
				RealMatrix[] distances = new RealMatrix[part.length];
				for (int i = 0; i < distances.length; i++){
					if (calcDistance <= perceptionLimit && calcDistance >= coreRad){
						distances[i] = loc[i].subtract(part[i]).scalarMultiply(16*16/(calcDistance*calcDistance*calcDistance));
					}
					else if (calcDistance < coreRad){
						distances[i] = loc[i].subtract(part[i]).scalarMultiply(16*16/(coreRad*coreRad*calcDistance));
					}
					else {
						distances[i] = loc[i].subtract(part[i]).scalarMultiply(0);
					}
				}
				if (force == null){
					force = distances;
				}
				else {
					for (int i = 0; i < force.length; i++){
						force[i] = force[i].add(distances[i]);
					}
				}
			}
		}
		
		return force;
	}

	// Squares the matrix
	private RealMatrix squareMatrix(RealMatrix in){
		RealMatrix ret = in.copy();
		for (int i = 0; i < ret.getRowDimension(); i++){
			for (int j = 0; j < ret.getColumnDimension(); j++){
				ret.setEntry(i, j, ret.getEntry(i, j)* ret.getEntry(i, j));
			}
		}
		return ret;
	}
	
	// Euclidean distance calculation
	private double dist(RealMatrix[] loc, RealMatrix[] part) {
		// TODO Auto-generated method stub
		double sum = 0;
		for (int i = 0; i < loc.length; i++){
			RealMatrix diff = squareMatrix(loc[i].subtract(part[i]));
			for (int x = 0; x < diff.getRowDimension(); x++){
				for (int y = 0; y < diff.getColumnDimension(); y++){
					sum += diff.getEntry(x, y);
				}
			}
		}		
		return Math.sqrt(sum);
	}
	
}
