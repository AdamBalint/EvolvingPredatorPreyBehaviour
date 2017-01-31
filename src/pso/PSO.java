package pso;

import storage.SpeciesType;
import storage.Variables;

public class PSO {
	// [0=predator, 1=prey][specializationSub-swarm]
	Swarm[][] predPreySwarms;
	
	public PSO(){
		predPreySwarms = new Swarm[2][1];
		predPreySwarms[0][0] = new Swarm(Variables.popSizePred, SpeciesType.PREDATOR, 0);
		predPreySwarms[1][0] = new Swarm(Variables.popSizePrey, SpeciesType.PREY, 0);
	}
	
	public void runPSO(){
		for (int i = 0; i < Variables.psoEpochs; i++){
			// Loop through species
			for (int s = 0; s < predPreySwarms.length; s++){
				// loop through specializations
				for (int spec = 0; spec <predPreySwarms[s].length; spec++){
					System.out.println("Global Best Fitness: " + predPreySwarms[s][spec].getGlobalBestFitness());
					predPreySwarms[s][spec].updatePopulation();
				}
			}
		}
		
		/*System.err.println(predPreySwarms[0][0].getGlobalBestFitness());
		for (int i = 0; i < Variables.popSizePred; i++){
			System.out.println(predPreySwarms[0][0].getParticle(i).getPersonalBestFit());
		}*/
		
	}
	
	
	
}
