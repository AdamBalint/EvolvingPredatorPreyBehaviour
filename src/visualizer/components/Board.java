package visualizer.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Board extends JPanel{

	public Board(){
		this(410, 410);
	}
	
	public Board(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.white);
	}
	
}
