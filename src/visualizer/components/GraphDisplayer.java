package visualizer.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;


public class GraphDisplayer extends JPanel{

	public GraphDisplayer(){
		this(450, 150);
	}
	
	public GraphDisplayer(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.DARK_GRAY);
	}
	
	
}
