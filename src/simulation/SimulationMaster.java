package simulation;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

// In charge of running all of the simulations
public class SimulationMaster {

	private SpeciesType specType;
	private NeuralNet nn;
	private int particleNum;
	private GameLogger gameLogger = null;
	
	
	// Species type to get type of brain, 
	public SimulationMaster(SpeciesType specType, NeuralNet nn, int particleNum){
		this.specType = specType;
		this.nn = nn;
		this.particleNum = particleNum;
		this.gameLogger = new GameLogger();
	}
	
	// Runs the simulations for a particle
	public double runSimulations(){
		// Sets up the thread executor to allow for multi-threading
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
		List<Future<SimulationLog>> resultList = new ArrayList<>();
		
		// Runs all of the simulations and stores stores the object that will hold the result
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
		
		// Keeps checking for correct solutions until all the simulations are complete
		while(count < Variables.simulationNum){
			for (int i = 0; i < resultList.size(); i++){
				if (resultList.get(i).isDone()){
					count++;
					Future<SimulationLog> f = resultList.remove(i);
					// Adds the score to the particle
					try {
						SimulationLog sl = f.get();
						ArrayList<Point> moves = sl.movementLog;
						score += specType == SpeciesType.PREDATOR ? sl.predatorScore : sl.preyScore;
						gameLogger.addGame(moves);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		// Sets the particles fitness
		gameLogger.setParticleFitness(score/Variables.simulationNum);
		// Closes the thread pool
		executor.shutdown();
		
		return score/Variables.simulationNum;
	}
	
	// Saves the simulations to a specific location
	public void saveSimulationGames(){
		String loc = "Logs/"+Variables.runBase + "/Run-"+Variables.currentRun+"/Epoch-"+Variables.currentEpoch;;
		String save;
		if (specType == SpeciesType.PREDATOR){
			save = "/PredBest-"+particleNum+".save";
		}else{
			save = "/PreyBest-"+particleNum+".save";
		}
		try {
			File f = new File(loc);
			f.mkdirs();
			FileOutputStream fout = new FileOutputStream(loc+save);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(gameLogger);
			oos.flush();
			oos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Writes the movement log (a text version of what was done above)
	private void writeMovementLog(ArrayList<Point> moves, String location, int gameNum){
		File f = new File(location);
		if (!f.exists()){
			f.mkdirs();
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(location+"Game-"+gameNum+".txt")));
			for (int i = 0; i < moves.size(); i++){
				Point cur = moves.get(i);
				bw.write(cur.x+","+cur.y + "\t");
				
				if (i % 2 != 0){
					bw.newLine();
				}
			}
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
