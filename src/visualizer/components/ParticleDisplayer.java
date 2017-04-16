package visualizer.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class ParticleDisplayer extends JPanel{

	JList displayParticle;
	
	public ParticleDisplayer(){
		this(225, 450);
		
	}
	
	public ParticleDisplayer(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.magenta);
	}
	
}
