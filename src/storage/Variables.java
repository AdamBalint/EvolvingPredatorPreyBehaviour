package storage;

import java.util.Random;

import simulation.*;

public class Variables {

	
	//PSO Parameters
	public static int psoEpochs = 50;
	
	public static int popSizePred = 50;
	public static double socialPred = 1.496180;
	public static double cognitivePred = 1.496180;
	public static double inertiaPred = 0.729844;
//	public static double communityCoeffPred;
//	public static double personalCoeffPred;
	
	public static int popSizePrey = 5;
	public static double socialPrey = 1.496180;
	public static double cognitivePrey = 1.496180;
	public static double inertiaPrey = 0.729844;
//	public static double communityCoeffPrey;
//	public static double personalCoeffPrey;
	
	
	public static BrainStorage brainStorage;
	
	public static int inputLayerSizePred = 25;
	public static int[] hiddenLayerSizesPred = {20, 10};
	public static int outputLayerSizePred = 7;
	
	public static int inputLayerSizePrey = 9;
	public static int[] hiddenLayerSizesPrey = {5};
	public static int outputLayerSizePrey = 5;
	
	
	public static Board brd;
	public static int boardWidth = 5, boardHeight = 5;
	
	
	public static int simulationTurnNum = 50;
	public static int simulationNum = 250;
	
	public static Random rand = new Random();
	
}
