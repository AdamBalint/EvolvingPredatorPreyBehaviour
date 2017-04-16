package visualizer.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameDisplayer extends JPanel implements ActionListener{

	JButton left, right;
	int counter = 1;
	int max = 20;
	JLabel currentRun;
	public GameDisplayer(){
		this(450, 450);
		
	}
	
	public GameDisplayer(int width, int height){
		
		this.setPreferredSize(new Dimension(width, height));
		
		Board b = new Board();
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setVgap(20);
		this.setLayout(borderLayout);
		
		this.add(b, BorderLayout.CENTER);
		
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		JPanel buttonDisplayer = new JPanel(new BorderLayout());
		
		left = new JButton("<");
		left.setActionCommand("previous");
		left.addActionListener(this);
		buttonDisplayer.add(left, BorderLayout.WEST);
		
		currentRun = new JLabel(counter + "/"+max, SwingConstants.CENTER);
		currentRun.setPreferredSize(new Dimension(300, 20));
		buttonDisplayer.add(currentRun);
		
		right = new JButton(">");
		right.setActionCommand("next");
		right.addActionListener(this);
		buttonDisplayer.add(right, BorderLayout.EAST);
		
		
		this.add(buttonDisplayer, BorderLayout.SOUTH);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		switch(e.getActionCommand()){
		case "previous":
			loadPrevious();
			break;
		case "next":
			loadNext();
			break;
		}
		currentRun.setText(counter + "/"+max);
	}
	
	
	private void loadPrevious(){
		if (counter == 1)
			return;
		counter--;
	}
	
	private void loadNext(){
		if (counter == max)
			return;
		counter++;
	}
}
