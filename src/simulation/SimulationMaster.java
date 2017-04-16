package simulation;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	private int particleNum;
	
	// Species type to get type of brain, 
	public SimulationMaster(SpeciesType specType, NeuralNet nn, int particleNum){
		this.specType = specType;
		this.nn = nn;
		this.particleNum = particleNum;
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
							if (particleNum != -1){
								ArrayList<Point> moves = sl.movementLog;
								String loc = "Logs/"+Variables.runBase + "/Run-"+Variables.currentRun+"/Epoch-"+Variables.currentEpoch+"/Games/Predator/Pred-"+particleNum+"/";
								writeMovementLog(moves, loc, count-1);
							}
						} catch (InterruptedException | ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						try {
							SimulationLog sl = f.get();
							//System.err.println("Added to Prey score");
							score += (sl.preyScore);
							if (particleNum != -1){
								ArrayList<Point> moves = sl.movementLog;
								String loc = "Logs/"+Variables.runBase + "/Run-"+Variables.currentRun+"/Epoch-"+Variables.currentEpoch+"/Games/Prey/Prey-"+particleNum+"/";
								writeMovementLog(moves, loc, count-1);
							}
						} catch (InterruptedException | ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		if (particleNum != -1){
			String loc;
			if (specType == SpeciesType.PREDATOR)
				loc = "Logs/"+Variables.runBase + "/Run-"+Variables.currentRun+"/Epoch-"+Variables.currentEpoch+"/Games/Predator/Pred-"+particleNum+"/";
			else
				loc = "Logs/"+Variables.runBase + "/Run-"+Variables.currentRun+"/Epoch-"+Variables.currentEpoch+"/Games/Prey/Prey-"+particleNum+"/";
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(loc+"Fitness.txt")));
				bw.write(""+score);
				bw.flush();
				bw.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		executor.shutdown();
		
		
		/*if (specType == SpeciesType.PREDATOR)
			return predScore;
		else
			return preyScore;
		*/
		//System.out.println("Score: " + score);
		return score/Variables.simulationNum;
	}
	
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
