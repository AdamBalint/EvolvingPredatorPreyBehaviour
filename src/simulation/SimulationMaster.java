package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import messages.SimulationLog;
import simulation.creature.NeuralNet;
import storage.SpeciesType;
import storage.Variables;

// Set up the board to call the simulation stuff
public class SimulationMaster {

	private SpeciesType specType;
	private NeuralNet nn;
	
	// Species type to get type of brain, 
	public SimulationMaster(SpeciesType specType, NeuralNet nn){
		this.specType = specType;
		this.nn = nn;
	}
	
	public double runSimulations(){
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
		List<Future<SimulationLog>> resultList = new ArrayList<>();
		
		for (int i = 0; i < Variables.simulationNum; i++){
			Board b = new Board();
			if (specType == SpeciesType.PREDATOR)
				b.placeCreatures(nn, Variables.brainStorage.getRandomBrain(1));
			else
				b.placeCreatures(Variables.brainStorage.getRandomBrain(0), nn);
			Future<SimulationLog> res = executor.submit(b);
			resultList.add(res);
		}
		
		double score = 0;
		int count = 0;
		while(resultList.size() > 0 && count != Variables.simulationNum){
			for (int i = resultList.size()-1; i >= 0 ; i--){
				if (resultList.get(i).isDone()){
					count++;
					Future<SimulationLog> f = resultList.remove(i);
					if (specType == SpeciesType.PREDATOR){
						try {
							SimulationLog sl = f.get();
							//System.err.println("Added to Predator score");
							score += (sl.predatorScore);
						} catch (InterruptedException | ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						try {
							SimulationLog sl = f.get();
							//System.err.println("Added to Prey score");
							score += (sl.preyScore);
						} catch (InterruptedException | ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		executor.shutdown();
		
		
		/*if (specType == SpeciesType.PREDATOR)
			return predScore;
		else
			return preyScore;
		*/
		//System.out.println("Score: " + score);
		return score;
	}
	
	
}
