package visualizer.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class ParameterDisplayer extends JPanel{

	public ParameterDisplayer(){
		this(225, 450);
	}
	
	public ParameterDisplayer(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.cyan);
	}
	
	
}
