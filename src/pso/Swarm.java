package pso;

import java.util.Vector;

import org.apache.commons.math3.analysis.function.Abs;
import org.apache.commons.math3.analysis.function.Power;
import org.apache.commons.math3.analysis.function.Sqrt;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import pso.particles.Particle;
import simulation.SimulationMaster;
import simulation.creature.NeuralNet;
import storage.SpeciesType;

public class Swarm {

	private Particle [] swarm;
	private int speciesNum;
	private RealMatrix[] globalBest;
	private Vector<RealMatrix[]> priorBests = new Vector<>();
	private double globalBestFitness = Double.NEGATIVE_INFINITY;
	private SpeciesType sType;
	
	private double coreRad = 2;
	private double perceptionLimit = 40;
	
	
	public Swarm(int num, SpeciesType sType, int speciesNumber){
		System.err.println("Swarm - Constructor");
		swarm = new Particle[num];
		this.sType = sType;
		setUpParticles();
	}
	
	/*public void createSwarm(int num, SpeciesType sType, int speciesNumber){
		swarm = new Particle[num];
		this.sType = sType;
		setUpParticles();
	}*/
	
	private void setUpParticles() {
		// TODO Auto-generated method stub
		System.err.println("Swarm - Setting up particles");
		for (int i = 0; i < swarm.length; i++){
			if (sType == SpeciesType.PREDATOR)
				swarm[i] = new Particle(sType, this, i, i > swarm.length*(1-0.3) ? true : false);
			else
				swarm[i] = new Particle(sType, this, i, false);
		}
	}
	
	public boolean particleIsCharged(int num){
		return swarm[num].isCharged();
	}
	
	public int getSwarmSize(){
		return swarm.length;
	}
	
	public RealMatrix[] getGlobalBest(){
		RealMatrix[] copy = new RealMatrix[globalBest.length];
		for (int i = 0; i < copy.length; i++){
			copy[i] = globalBest[i].copy();
		}
		
		return copy;
	}
	
	public double getGlobalBestFitness(){
		return globalBestFitness;
	}
	
	public void updatePopulation(){
		//System.err.println("Swarm - Updating Population");
		evaluatePopulation();
		updateLocations();
	}

	//
	private void updateLocations() {
		// TODO Auto-generated method stub
		for (int i = 0; i < swarm.length; i++){
			swarm[i].updateParticle();
		}
	}

	// Updates the global best
	private void evaluatePopulation() {

		
		// Could have just 1 call to method
		double maxFit = globalBestFitness;
		RealMatrix[] bestLoc = null;
		
		for (Particle p : swarm){
			if (p.getFitness() > globalBestFitness){
				maxFit = p.getPersonalBestFit();
				bestLoc = p.getPersonalBestLoc();
			}
		}
		if (bestLoc != null){
			globalBestFitness = maxFit;
			globalBest = bestLoc;
			priorBests.add(bestLoc);
		}		
	}
	
	public Particle getParticle(int particleNum){
		return swarm[particleNum];
	}
	
	public int getSpeciesNumber(){
		return speciesNum;
	}

	public double getAverageFitness() {
		// TODO Auto-generated method stub
		double fit = 0;
		for (int i = 0; i < swarm.length; i++){
			fit += swarm[i].getFitness();
		}
		return fit/swarm.length;
	}
	
	public void stepPopulation(){
		for (int i = 0; i < swarm.length; i++){
			swarm[i].stepParticle();
			
			
		}
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
			
			/*if (nFitness < globalBestFitness)
				System.err.println(sType.toString() + " Best got worse!" );
			globalBestFitness = nFitness;*/
		}
	}
	
	public RealMatrix calculateForce(RealMatrix loc, int brainNum, int loopCounter){
		RealMatrix force = null;
		for (Particle p : swarm){
			if (p.isCharged() && p.getBrainNum() != brainNum){
				RealMatrix jLoc = p.getLocation()[loopCounter];
				RealMatrix dist = loc.subtract(jLoc);
				RealMatrix partialForce = dist.copy().scalarMultiply(0);
				for (int r = 0; r < dist.getRowDimension(); r++){
					for (int c = 0; c < dist.getColumnDimension(); c++){
						double absDist = Math.abs(dist.getEntry(r, c));
						if (absDist >= coreRad && absDist <= perceptionLimit)
							partialForce.setEntry(r, c, partialForce.getEntry(r, c)+(1/(Math.pow(absDist, 3))));
						else if (absDist < coreRad)
							partialForce.setEntry(r, c, partialForce.getEntry(r, c)+(coreRad*coreRad));
					}
				}
				if (force == null)
					force = partialForce;
				else
					force = force.add(partialForce);
				/*for (int i = 0; i < partialForce.getRowDimension(); i++){
					RealVector rv = dist.getRowVector(i);
					//rv = rv.ebeDivide(rv.mapToSelf(new Power(2))).mapMultiply(16);
					rv = rv.mapMultiply(16*16).ebeDivide(rv.mapToSelf(new Abs()).map(new Power(3)));
					partialForce.setRowVector(i, rv);
				}
				//.scalarMultiply(64).multiply(dist.power(-3));//.power(2).power(1/2).power(-3));
				if (force == null){
					force = partialForce;
				}
				else{
					force = force.add(partialForce);
				}
				*/
			}
		}
		
		return force;
	}
	
}
