package visualizer.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class CreatureDisplayer extends JPanel{

	public CreatureDisplayer(){
		this(225, 150);
	}
	
	public CreatureDisplayer(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.yellow);
	}
	
}
