import java.io.File;

import pso.PSO;
import storage.BrainStorage;
import storage.Variables;

public class Main {
	
	PSO pso;
	
	public Main(){
		// Brain storage
		//String nanoTime = ""+System.nanoTime();
		//Variables.runBase = nanoTime;
		File f = new File ("Logs/"+Variables.runBase);
		f.mkdirs();
		
		for (int run = 0; run < 5; run++){
			Variables.currentRun = run;
			//f = new File ("Logs/"+nanoTime+"/Run-"+run);
			//f.mkdir();
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
		Variables.predPercentCharged = Double.parseDouble(args[0]);
		Variables.hiddenLayerSizesPred = new int[]{Integer.parseInt(args[1])};
		
		Variables.preyPercentCharged = Double.parseDouble(args[2]);
		Variables.hiddenLayerSizesPrey= new int[]{Integer.parseInt(args[3])};
		
		Variables.coreRad = Double.parseDouble(args[4]);
		Variables.perceptionLimit = Double.parseDouble(args[5]);
		
		Variables.canFall = Boolean.parseBoolean(args[6]);
		Variables.runBase = "V2/V2-" + args[6] + "-rc" + args[4] + "-pl" + args[5] + "-pdh" + args[0] + "-pdc" + args[1] + "-pyh" + args[2] + "-pyc" + args[3];
		
		
		
		new Main();
	}

}
