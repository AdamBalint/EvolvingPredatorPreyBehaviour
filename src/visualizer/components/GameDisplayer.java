package visualizer.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

// Handles the set up and changing of the games
public class GameDisplayer extends JPanel implements ActionListener{

	private JButton left, right;
	private int counter = 1;
	private int max = 20;
	private JLabel currentRun;
	private Board b;
	private String dir;
	
	public GameDisplayer(){
		this(450, 450);
		
	}
	// Sets up panel (Contains buttons to swap between games)
	public GameDisplayer(int width, int height){
		
		this.setPreferredSize(new Dimension(width, height));
		
		b = new Board();
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
	
	// Updates the available games
	public void update(String dir){
		this.dir = dir;
		File f = new File(dir);
		File[] files = f.listFiles();
		int count = 0;
		
		b.loadGames(new File(dir));
		b.loadBoard(counter-1);
		
		/*for (File file : files){
			if (file.getName().contains("Game"))
				count++;
		}*/
		max = b.getGamesNumber();
		if (counter > max-1)
			counter = max-1;
		currentRun.setText(counter + "/" + max);
		
		
	}
	
	// When button is pressed, loads the correct game
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
		currentRun.setText(counter + "/" + max);
		//b.loadGames(new File(dir+"/games.save"));
		b.loadBoard(counter-1);
	}
	
	// Decrements the counter
	private void loadPrevious(){
		if (counter == 1)
			return;
		counter--;
	}
	
	// Increments the counter
	private void loadNext(){
		if (counter == max)
			return;
		counter++;
	}
}
