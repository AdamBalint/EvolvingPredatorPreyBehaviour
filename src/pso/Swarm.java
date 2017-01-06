package pso;

import org.apache.commons.math3.linear.RealMatrix;

import pso.particles.Particle;

public class Swarm {

	private Particle [] swarm;
	
	public void createSwarm(int num){
		swarm = new Particle[num];
		
	}
	
	public RealMatrix[] getGlobalBest(){
		return null;
	}
	
	public void updatePopulation(){
		evaluatePopulation();
		updateLocations();
	}

	private void updateLocations() {
		// TODO Auto-generated method stub
		
	}

	private void evaluatePopulation() {
		// TODO Auto-generated method stub
		
	}
	
}
