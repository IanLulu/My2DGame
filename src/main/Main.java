package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // lets the window properly close when user clicks the "close" button
		window.setResizable(false); // prevents game window resize
		window.setTitle("2D Adventure");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); // causes this window to be sized to fit the preferred size+layouts of its subcomponents (GamePanel). So we can see the panel basically
		
		window.setLocationRelativeTo(null); // won't specify where the window opens, i.e. the window will display in the center of the screen
		window.setVisible(true);
		
		gamePanel.startGameThread();

	}

}
