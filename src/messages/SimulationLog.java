package messages;

import java.awt.Point;
import java.util.ArrayList;

public class SimulationLog {

	// Stores the simulations.
	public SimulationLog(SimulationLog log) {
		// TODO Auto-generated constructor stub
		predatorScore = log.predatorScore;
		preyScore = log.preyScore;
		movementLog = log.movementLog;
	}
	public SimulationLog() {
		// TODO Auto-generated constructor stub
	}
	public double predatorScore;
	public double preyScore;
	public ArrayList<Point> movementLog;
	
	
}
