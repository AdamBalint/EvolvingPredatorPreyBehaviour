package pso;

import org.apache.commons.math3.linear.RealMatrix;

import pso.particles.Particle;
import storage.SpeciesType;

public class Swarm {

	private Particle [] swarm;
	private int speciesNum;
	private RealMatrix[] globalBest;
	private double globalBestFitness = Double.NEGATIVE_INFINITY;
	private SpeciesType sType;
	
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
			swarm[i] = new Particle(sType, this, i);
		}
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
		for (Particle p : swarm){
			if (p.getFitness() > globalBestFitness){
				globalBestFitness = p.getPersonalBestFit();
				globalBest = p.getPersonalBestLoc();
			}
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
	
}
