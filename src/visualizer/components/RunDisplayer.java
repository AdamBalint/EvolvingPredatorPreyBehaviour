package visualizer.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class RunDisplayer extends JPanel{

	public RunDisplayer(){
		this(225, 150);
	}
	
	public RunDisplayer(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.YELLOW);
	}
	
}
