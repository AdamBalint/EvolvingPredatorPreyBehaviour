package visualizer.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

// Holds information about the agents that are currently being shown
// Not vital, and therefore this panel is incomplete
public class CreatureDisplayer extends JPanel{

	public CreatureDisplayer(){
		this(225, 250);
	}
	
	public CreatureDisplayer(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.yellow);
	}
	
}
