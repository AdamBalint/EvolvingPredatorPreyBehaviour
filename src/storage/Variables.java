package storage;

import java.util.Random;

import simulation.*;

public class Variables {

	
	//PSO Parameters
	public static int psoEpochs = 100;
	
	public static int popSizePred = 1000;
	public static double socialPred = 1.496180;
	public static double cognitivePred = 1.496180;
	public static double inertiaPred = 0.729844;
//	public static double communityCoeffPred;
//	public static double personalCoeffPred;
	
	public static int popSizePrey = 1000;
	public static double socialPrey = 1.496180;
	public static double cognitivePrey = 1.496180;
	public static double inertiaPrey = 0.729844;
//	public static double communityCoeffPrey;
//	public static double personalCoeffPrey;
	
	
	public static BrainStorage brainStorage;
	
	public static int inputLayerSizePred = 9;
	public static int[] hiddenLayerSizesPred = {5,5};
	public static int outputLayerSizePred = 5;
	
	public static int inputLayerSizePrey = 9;
	public static int[] hiddenLayerSizesPrey = {5,5};
	public static int outputLayerSizePrey = 5;
	
	
	public static Board brd;
	public static int boardWidth = 5, boardHeight = 5;
	
	
	public static int simulationTurnNum = 40;
	public static int simulationNum = 20;
	
	public static Random rand = new Random();
	
}
