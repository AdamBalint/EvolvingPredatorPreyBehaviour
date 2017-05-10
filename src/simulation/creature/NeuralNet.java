package simulation.creature;

import storage.SpeciesType;
import storage.Variables;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import messages.MovementMessage;

public class NeuralNet {
	
	private RealMatrix[] brain;
	
	private SpeciesType specType;
	private int inSize, outSize;
	private int[] hidden;
	
	private int numConnections = 0;
	
	
	public NeuralNet(SpeciesType specType){
		if (specType == SpeciesType.PREDATOR){
			brain = new RealMatrix[Variables.hiddenLayerSizesPred.length+1];
			inSize = Variables.inputLayerSizePred;
			outSize = Variables.outputLayerSizePred;
			hidden = Variables.hiddenLayerSizesPred.clone();
		}else{
			brain = new RealMatrix[Variables.hiddenLayerSizesPrey.length+1];
			inSize = Variables.inputLayerSizePrey;
			outSize = Variables.outputLayerSizePrey;
			hidden = Variables.hiddenLayerSizesPrey.clone();
		}
		this.specType = specType;
		setUpMatrices();
	}
	
	public NeuralNet(SpeciesType specType, RealMatrix[] net){
		this.specType = specType;
		if (specType == SpeciesType.PREDATOR){
			brain = new RealMatrix[Variables.hiddenLayerSizesPred.length+1];
			inSize = Variables.inputLayerSizePred;
			outSize = Variables.outputLayerSizePred;
			hidden = Variables.hiddenLayerSizesPred.clone();
		}else{
			brain = new RealMatrix[Variables.hiddenLayerSizesPrey.length+1];
			inSize = Variables.inputLayerSizePrey;
			outSize = Variables.outputLayerSizePrey;
			hidden = Variables.hiddenLayerSizesPrey.clone();
		}
		brain = net;
	}
	
	private void setUpMatrices() {
		// Generate the number of matrices needed for the weights
		if (hidden.length > 0){
			for (int i = 0; i < brain.length; i++){
				if (i == 0){
					brain[i] = MatrixUtils.createRealMatrix(inSize, hidden[0]);
					numConnections += inSize*hidden[0];
				}
				else if (i == brain.length-1){
					brain[i] = MatrixUtils.createRealMatrix(hidden[hidden.length-1], outSize);
					numConnections += outSize*hidden[hidden.length-1];
				}
				else{
					brain[i] = MatrixUtils.createRealMatrix(hidden[i-1], hidden[i]);
					numConnections += hidden[i]*hidden[i-1];
				}
			}
		}
		else{
			brain[0] = MatrixUtils.createRealMatrix(outSize, inSize);
			numConnections = inSize*outSize;
		}
	}

	
	
	public void generateRandomBrains(){
		for (int i = 0; i < brain.length; i++){
			for (int j = 0; j < brain[i].getRowDimension(); j++){
				for (int k = 0; k < brain[i].getColumnDimension(); k++){
					brain[i].setEntry(j, k, (Math.random()*4)-2);
				}
			}
		}
	}
	
	
	public MovementMessage getOutput(RealMatrix inp){
		for (int i = 0; i < brain.length; i++){
			//System.out.println(brain[i].getRowDimension() + "\t" + brain[i].getColumnDimension());
			inp = inp.multiply(brain[i]);
			for (int j = 0; j < inp.getColumnDimension(); j++){
				// go through row matrix and pass through sigmoid function
				inp.setEntry(0,j, sigmoid(inp.getEntry(0, j)));
			}
		}
		return new MovementMessage(inp);
	}
	
	public double[] getRawOutput(RealMatrix inp){

		for (int i = 0; i < brain.length; i++){
			System.out.printf("Input: (%d,%d)\n", inp.getRowDimension(), inp.getColumnDimension());
			System.out.printf("Brain: (%d,%d)\n", brain[i].getRowDimension(), brain[i].getColumnDimension());
			inp = inp.multiply(brain[i]);
			for (int j = 0; j < inp.getColumnDimension(); j++){
				// go through row matrix and pass through sigmoid function
				inp.setEntry(0,j, sigmoid(inp.getEntry(0,j)));
			}
		}
		return inp.getRow(0);
	}
	
	private double sigmoid(double in){
		return 1/(1+Math.exp(-in));
	}
	
	////////////////////////////////////////////////////////
	// Methods for testing the neural net with unit tests //
	////////////////////////////////////////////////////////
	
	public NeuralNet(Object calledBy) throws Exception{
		System.out.println(calledBy.getClass());
		System.out.println(test.NeuralNetTest.class);
		if (calledBy.getClass() != test.NeuralNetTest.class){
			System.out.println("Object not created!");
			throw new Exception();
		}
	}
	
	
	public void createNN(int input, int output, int[] hidden, Object calledBy){
		if (calledBy.getClass() == test.NeuralNetTest.class){
			brain = new RealMatrix[hidden.length+1];
			inSize = input;
			outSize = output;
			this.hidden = hidden;
			setUpMatrices();
		}
	}
	
	public void setWeights(int matrix, int row, int col, int value, Object calledBy){
		if (calledBy.getClass() == test.NeuralNetTest.class || calledBy.getClass() == test.ParticleTest.class){
			brain[matrix].setEntry(row, col, value);
		}
	}
	
	public void setWeights(int matrix, RealMatrix newMat, Object calledBy){
		if (calledBy.getClass() == test.NeuralNetTest.class || calledBy.getClass() == test.ParticleTest.class){
			brain[matrix] = newMat;
		}
	}
	
	public RealMatrix[] getBrain(){
		return brain;
	}
	
}
