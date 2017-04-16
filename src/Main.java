import java.io.File;

import pso.PSO;
import storage.BrainStorage;
import storage.Variables;

public class Main {
	
	PSO pso;
	
	public Main(){
		// Brain storage
		String nanoTime = ""+System.nanoTime();
		Variables.runBase = nanoTime;
		File f = new File ("Logs/"+nanoTime);
		f.mkdirs();
		
		for (int run = 0; run < 1; run++){
			Variables.currentRun = run;
			f = new File ("Logs/"+nanoTime+"/Run-"+run);
			f.mkdir();
			// Loop for experiments
			Variables.brainStorage = new BrainStorage();
			setUpPSO();
			setUpSimulation();
			pso.runPSO();
		}
	
	
	}
	
	
	
	private void setUpSimulation() {
		
	}



	private void setUpPSO() {
		//predator/prey, number of different styles, population size of each
		//BrainStorage.brains = new RealMatrix[2][1][100];
		pso = new PSO();
	}



	public static void main(String[] args) {
		new Main();
	}

}
