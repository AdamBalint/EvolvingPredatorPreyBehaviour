package simulation;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class GameLogger implements Serializable{

	private static final long serialVersionUID = -5481024168409828837L;
	
	private ArrayList<ArrayList<Point>> games;
	
	public GameLogger(){
		games = new ArrayList<>();
	}
	
	public ArrayList<Point> getGame(int i){
		return games.get(i);
	}
	
	public void addGame(ArrayList<Point> game){
		games.add(game);
	}
}
