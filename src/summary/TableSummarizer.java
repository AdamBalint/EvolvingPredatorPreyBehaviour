package summary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import simulation.GameLogger;
import storage.Variables;

public class TableSummarizer {

	public TableSummarizer(){
		
		generateTable("V1", true);
		generateTable("V2", true);
		generateTable("V3", true);
		generateTable("V3", false);
		
		
		
		
	}
	
	
	//  V1-true-rc5-pl30-pdh1-pdc15-pyh1-pyc15
	public void generateTable(String root, boolean canFall){
		//ArrayList<String> files = new ArrayList<>();
		String[][] limits = {{"2", "40"},{"5", "30"}};
		String[] hidden = {"15","30","60"};
		String[] charged = {"0", "0.33","0.67","1"};
		
		File logF = new File(root + (canFall ? "-true.txt" : "-false.txt"));
		try {
			logF.createNewFile();
		
			BufferedWriter bw = new BufferedWriter(new FileWriter(logF));
			
			int count = 1;
			
			for (int i = 0; i < hidden.length; i++){
				for(int j = 0; j < limits.length; j++){
					for(int k = 0; k < charged.length; k++){
						String f = (root+"/"+root+"-"+(canFall ? "true" : "false")+"-rc"+limits[j][0]+"-pl"+limits[j][1]+"-pdh"+charged[k]+"-pdc"+hidden[i]+"-pyh"+charged[k]+"-pyc"+hidden[i]);
						double[] results = getSummary(f);
						bw.write(count + " & " +String.format( "%.3f", results[0]) + " & " + String.format( "%.3f", results[1]) + " & " + String.format( "%.3f", results[2]) + " & " + String.format( "%.3f", results[3]) + " & " + String.format( "%.3f", results[4]) + " & " + String.format( "%.3f", results[5]) + " \\\\");
						bw.newLine();
						System.out.println("Experiment " + count +":\t" + Arrays.toString(results));
						count++;
					}
				}
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private double[] getSummary(String f) {
		// TODO Auto-generated method stub
		// Pred Max, avg, min, Prey max, avg, min
		double[] results = {-Double.MAX_VALUE, 0, Double.MAX_VALUE, -Double.MAX_VALUE, 0, Double.MAX_VALUE};
		for (int i = 0; i < 5; i++){
			double[] best = getBestForRun(f,i);
			if (best[0] > results[0])
				results[0] = best[0];
			if (best[0] < results[2])
				results[2] = best[0];
			
			if (best[1] > results[3])
				results[3] = best[1];
			if (best[1] < results[5])
				results[5] = best[1];
			results[1] += best[0];
			results[4] += best[1];
		}
		results[1] /= 5;
		results[4] /= 5;
		
		return results;
	}

	private double[] getBestForRun(String s, int run){
		double ret[] = {Integer.MIN_VALUE,Integer.MIN_VALUE};
		
		
		try {
			Scanner in = new Scanner(new File(s+"/Run-" + run + "/RunSummary.txt"));
			while(in.hasNextLine()){
				String line = in.nextLine();
				String[] parts = line.split("\t");
				//System.out.println(Arrays.toString(parts));
				if (ret[0] < Double.parseDouble(parts[0]) && !parts[0].contains("I"))
					ret[0] = Double.parseDouble(parts[0]);
				if (ret[1] < Double.parseDouble(parts[3]) && !parts[3].contains("I"))
					ret[1] = Double.parseDouble(parts[3]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("\n--------\n");
		}
		
		
		//System.out.println(s);
		
		/*int count = 0;
		try{
				//System.err.println(new File(s+"/Run-"+run).listFiles(File::isDirectory));
			for (File f: new File(s+"/Run-"+run).listFiles(File::isDirectory)){ // epochs
				
				File[] files = f.listFiles();
				FileInputStream fis = new FileInputStream(files[0]);
				ObjectInputStream ois = new ObjectInputStream(fis);
				GameLogger gl = (GameLogger) ois.readObject();
				double fit = gl.getParticleFitness();
				if (fit > ret[0])
					ret[0] = fit;
				
				//drawGame(gl, loc, bestWorst, predator, true);
				
				fis = new FileInputStream(files[1]);
				ois = new ObjectInputStream(fis);
				gl = (GameLogger) ois.readObject();
				
				fit = gl.getParticleFitness();
				if (fit > ret[1])
					ret[1] = fit;*/
				//System.err.println(f.getAbsolutePath());
				/*for (File files : f.listFiles()){
				
					FileInputStream fis = new FileInputStream(files[0]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					GameLogger gl = (GameLogger) ois.readObject();
					//drawGame(gl, loc, bestWorst, predator, true);
					
					fis = new FileInputStream(files[1]);
					ois = new ObjectInputStream(fis);
					gl = (GameLogger) ois.readObject();
				}*/
				/*System.err.println("Done epoch " + count);
				count ++;
			}
		}catch (NullPointerException e){
			System.out.println("\n--------\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		return ret;
	}
	

	public static void main(String[] args) {
		new TableSummarizer();

	}

}
