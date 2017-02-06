package pso;

import storage.SpeciesType;
import storage.Variables;

public class PSO {
	// [0=predator, 1=prey][specializationSub-swarm]
	Swarm[][] predPreySwarms;
	
	double iterationLog[][] = new double[Variables.psoEpochs][4];
	
	public PSO(){
		predPreySwarms = new Swarm[2][1];
		predPreySwarms[0][0] = new Swarm(Variables.popSizePred, SpeciesType.PREDATOR, 0);
		predPreySwarms[1][0] = new Swarm(Variables.popSizePrey, SpeciesType.PREY, 0);
		iterationLog[2][1] = 1;
	}
	
	public void runPSO(){
		for (int i = 0; i < Variables.psoEpochs; i++){
			System.err.println("Epoch: " + i);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Loop through species
			for (int s = 0; s < predPreySwarms.length; s++){
				// loop through specializations
				iterationLog[i][s*2] = predPreySwarms[s][0].getGlobalBestFitness();
				iterationLog[i][s*2+1] = predPreySwarms[s][0].getAverageFitness();
				
				
				for (int spec = 0; spec <predPreySwarms[s].length; spec++){
					predPreySwarms[s][spec].updatePopulation();
					System.out.println((s == 0 ? "Pred ":"Prey ") + "Global Best Fitness: " + predPreySwarms[s][spec].getGlobalBestFitness() + "\t Average: " + iterationLog[i][s*2+1]);
				}
			}
		}
		
		for (int i = 0; i < iterationLog.length; i++){
			System.out.println(iterationLog[i][0] + "\t" + iterationLog[i][1] + "\t" + iterationLog[i][2] + "\t" + iterationLog[i][3]);
		}
		
		
		/*System.err.println(predPreySwarms[0][0].getGlobalBestFitness());
		for (int i = 0; i < Variables.popSizePred; i++){
			System.out.println(predPreySwarms[0][0].getParticle(i).getPersonalBestFit());
		}*/
		
	}
	
	
	
}
