import org.apache.commons.math3.linear.RealMatrix;
import storage.BrainStorage;


public class Main {
	
	public Main(){
		setUpPSO();
		setUpSimulation();
	}
	
	
	
	private void setUpSimulation() {
		
	}



	private void setUpPSO() {
		// predator/prey, number of different styles, population size of each
		BrainStorage.brains = new RealMatrix[2][1][100];
		
	}



	public static void main(String[] args) {
		new Main();
	}

}
