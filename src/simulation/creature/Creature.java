package simulation.creature;

import storage.SpeciesType;

public class Creature {

	private int x, y;
	private SpeciesType speciesType;
	
	public Creature(int x, int y, SpeciesType type){
		this.x = x;
		this.y = y;
		speciesType = type;
	}
	
	
	public SpeciesType getSpeciesType(){
		return speciesType;
	}
	
	
	
}
