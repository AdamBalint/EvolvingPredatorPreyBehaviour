package storage;

import simulation.creature.NeuralNet;


public class BrainStorage {
	// [Predator/Prey][Specialization][Matrices of NN]
	NeuralNet [][][] brains;
	
	public BrainStorage(){
		brains = new NeuralNet[2][1][Math.max(Variables.popSizePred, Variables.popSizePrey)];
		setUpBrains();
	}
	
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
	
	public NeuralNet getParticleBrain(SpeciesType specType, int speciesNumber, int particleNumber){
		int type = SpeciesType.PREDATOR == specType ? 0 : 1;
		return brains[type][speciesNumber][particleNumber];
	}

	public NeuralNet getRandomBrain(int i) {
		// TODO Auto-generated method stub
		return brains[i][0][Variables.rand.nextInt(brains[i][0].length)];
	}
	
	
}
