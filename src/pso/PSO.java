package pso;

import java.io.*;

import storage.SpeciesType;
import storage.Variables;

public class PSO {
	// [0=predator, 1=prey][specializationSub-swarm]
	Swarm[][] predPreySwarms;
	
	// Logs the fitness of the population best and average and best particle number
	double iterationLog[][] = new double[Variables.psoEpochs][6];
	
	// Sets up the swarms
	public PSO(){
		predPreySwarms = new Swarm[2][1];
		predPreySwarms[0][0] = new Swarm(Variables.popSizePred, SpeciesType.PREDATOR, 0);
		predPreySwarms[1][0] = new Swarm(Variables.popSizePrey, SpeciesType.PREY, 0);
		iterationLog[2][1] = 1;
	}
	
	// Runs the PSO and logs files
	public void runPSO(){
		try {
			// Creates the directory structure required
			File f = new File("Logs/"+Variables.runBase + "/Run-"+Variables.currentRun);
			f.mkdirs();
			// Creates a summary file to log to
			f = new File("Logs/"+Variables.runBase + "/Run-"+Variables.currentRun+"/ParticleSummary.txt");
			f.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			
			// Writes the particle summary - particle number and whether it is charged or not
			for (int i = 0; i < predPreySwarms.length; i++){
				bw.write((i == 0 ? "Predator\t" : "Prey\t")+predPreySwarms[i][0].getSwarmSize());
				bw.newLine();
				for(int p = 0; p < predPreySwarms[i][0].getSwarmSize(); p++){
					bw.write(p+"\t"+predPreySwarms[i][0].particleIsCharged(p));
					bw.newLine();
				}
				bw.newLine();
			}
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Runs the pso for n number of generations
		for (int i = 0; i < Variables.psoEpochs; i++){
			// Updates the counter and prints out the values
			Variables.currentEpoch = i;
			System.err.println("Epoch: " + i);
			// Slight delay to print correctly
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Loop through species
			for (int s = 0; s < predPreySwarms.length; s++){
				// loop through specializations
				
				if (i % 10 == 0 && i != 0){
					// Recalculate old best
					predPreySwarms[s][0].recalculateBest();
				}
				
				// Store the values
				iterationLog[i][s*3] = predPreySwarms[s][0].getGlobalBestFitness();
				iterationLog[i][s*3+1] = predPreySwarms[s][0].getAverageFitness();
				iterationLog[i][s*3+2] = predPreySwarms[s][0].getBestParticleNum();
				
				// Updates the population and prints best
				for (int spec = 0; spec <predPreySwarms[s].length; spec++){
					predPreySwarms[s][spec].updatePopulation();
					System.out.println((s == 0 ? "Pred ":"Prey ") + "Global Best Fitness: " + predPreySwarms[s][spec].getGlobalBestFitness() + "\t Average: " + iterationLog[i][s*2+1]);
				}
			}
			// Goes through each swarm and updates the particles at the same time
			// Synchronous PSO
			for (int s = 0; s < predPreySwarms.length; s++){
				for (int spec = 0; spec <predPreySwarms[s].length; spec++){
					predPreySwarms[s][spec].stepPopulation();
				}
			}
		}
		
		
		// Print to log file of run
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("Logs/"+Variables.runBase + "/Run-"+Variables.currentRun+"/RunSummary.txt")));
			
			for (int i = 0; i < iterationLog.length; i++){
				bw.write(iterationLog[i][0] + "\t" + iterationLog[i][1] + "\t" + iterationLog[i][3] + "\t" + iterationLog[i][4] + "\t" + iterationLog[i][2] + "\t" + iterationLog[i][5]);
				bw.newLine();
				//System.out.println(iterationLog[i][0] + "\t" + iterationLog[i][1] + "\t" + iterationLog[i][2] + "\t" + iterationLog[i][3]);
			}
			bw.flush();
			bw.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < iterationLog.length; i++){
			System.out.println(iterationLog[i][0] + "\t" + iterationLog[i][1] + "\t" + iterationLog[i][2] + "\t" + iterationLog[i][3]);
		}
		
	}
	
}
