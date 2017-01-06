package simulation;

public interface BoardInterface {

	public void runSimulation();
	public void placeCreatures();
	// same kind = -1, opposite kind = -1, empty = 0, out of bounds = -2
	public int[] getSurroundings(int x, int y, int xOff, int yOff, int xLOS, int yLOS);
	
	
}