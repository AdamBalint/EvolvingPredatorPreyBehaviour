package storage;

import java.util.Random;

import simulation.*;

public class Variables {

	public static String runBase = "";
	public static int currentRun;
	public static int currentEpoch;
	
	//PSO Parameters
	public static int psoEpochs = 500;
	
	public static int popSizePred = 50;
	public static double socialPred = 1.42;//1.496180;
	public static double cognitivePred = 1.42; //1.496180;
	public static double inertiaPred = 0.72;//0.729844;
	public static double chargeCoeffPred;
	public static double predPercentCharged = 0;

	
	public static int popSizePrey = 50;
	public static double socialPrey = 1.42;//1.496180;
	public static double cognitivePrey = 1.42;//1.496180;
	public static double inertiaPrey = 0.72;//0.729844;
	public static double chargeCoeffPrey;
	public static double preyPercentCharged = 0;

	
	
	
	public static BrainStorage brainStorage;
	
	//public static int inputLayerSizePred = 25;
	public static int inputLayerSizePred = 9*9;
	public static int[] hiddenLayerSizesPred;
	public static int outputLayerSizePred = 7;
	
	//public static int inputLayerSizePrey = 9;
	public static int inputLayerSizePrey = 9*9;
	public static int[] hiddenLayerSizesPrey;
	public static int outputLayerSizePrey = 5;
	
	
	public static Board brd;
	public static int boardWidth = 9, boardHeight = 9;
	
	
	public static int simulationTurnNum = 20;
	public static int simulationNum = 40;
	
	public static Random rand = new Random();
	
	public static boolean canFall;
	public static double coreRad;
	public static double perceptionLimit;
	
	
}
