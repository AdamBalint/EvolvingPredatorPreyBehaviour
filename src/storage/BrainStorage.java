package storage;

import simulation.creature.NeuralNet;

// Stores all of the neural networks
public class BrainStorage {
	// [Predator/Prey][Specialization][Matrices of NN]
	NeuralNet [][][] brains;
	
	// Creates a container to store the neural networks of both agent types
	public BrainStorage(){
		brains = new NeuralNet[2][1][Math.max(Variables.popSizePred, Variables.popSizePrey)];
		setUpBrains();
	}
	
	// Sets up new random neural networks
	public void setUpBrains(){
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < brains[i].length; j++){
				for(int pop = 0; pop < brains[i][j].length; pop++){
					brains[i][j][pop] = new NeuralNet(i == 0 ? SpeciesType.PREDATOR : SpeciesType.PREY);
					brains[i][j][pop].generateRandomBrains();
				}
			}
		}
	}
	
	// Returns a specific neural network
	public NeuralNet getParticleBrain(SpeciesType specType, int speciesNumber, int particleNumber){
		int type = SpeciesType.PREDATOR == specType ? 0 : 1;
		return brains[type][speciesNumber][particleNumber];
	}

	// Returns a random neural network for a specific agent type
	public NeuralNet getRandomBrain(int i) {
		// TODO Auto-generated method stub
		return brains[i][0][Variables.rand.nextInt(brains[i][0].length)];
	}
	
	
}
