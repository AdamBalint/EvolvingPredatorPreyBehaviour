package visualizer.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

// Displays the parameters used for the set of runs
// Not vital to view display, therefore it is not complete
public class ParameterDisplayer extends JPanel{

	public ParameterDisplayer(){
		this(225, 450);
	}
	
	public ParameterDisplayer(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.cyan);
	}
	
	
}
