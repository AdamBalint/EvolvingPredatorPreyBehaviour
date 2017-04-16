package storage;

import java.util.Random;

import simulation.*;

public class Variables {

	public static String runBase = "";
	public static int currentRun;
	public static int currentEpoch;
	
	//PSO Parameters
	public static int psoEpochs = 100;
	
	public static int popSizePred = 100;
	public static double socialPred = 1.42;//1.496180;
	public static double cognitivePred = 1.42; //1.496180;
	public static double inertiaPred = 0.72;//0.729844;
	public static double chargeCoeffPred = 1;
//	public static double communityCoeffPred;
//	public static double personalCoeffPred;
	
	public static int popSizePrey = 100;
	public static double socialPrey = 1.42;//1.496180;
	public static double cognitivePrey = 1.42;//1.496180;
	public static double inertiaPrey = 0.72;//0.729844;
	public static double chargeCoeffPrey = 1;
//	public static double communityCoeffPrey;
//	public static double personalCoeffPrey;
	
	
	public static BrainStorage brainStorage;
	
	//public static int inputLayerSizePred = 25;
	public static int inputLayerSizePred = 9*9;
	public static int[] hiddenLayerSizesPred = {12};
	public static int outputLayerSizePred = 7;
	
	//public static int inputLayerSizePrey = 9;
	public static int inputLayerSizePrey = 9*9;
	public static int[] hiddenLayerSizesPrey = {12};
	public static int outputLayerSizePrey = 5;
	
	
	public static Board brd;
	public static int boardWidth = 9, boardHeight = 9;
	
	
	public static int simulationTurnNum = 20;
	public static int simulationNum = 100;
	
	public static Random rand = new Random();
	
}
