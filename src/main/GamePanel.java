package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

public class GamePanel extends JPanel implements Runnable { // GamePanel is subclass of JPanel
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // default size of in-game sprites. Common in NES/SNES era
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48x48 pixel tile
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	// Frames per second
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this, keyH);
	
	// Set player's default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //from JPanel library
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // basically, better rendering performance (tbh idk how it works)
		this.addKeyListener(keyH); // so that this GamePanel can recognize the key inputs
		this.setFocusable(true); // with this, this GamePanel will receive key input
	}

	public void startGameThread() {
		gameThread = new Thread(this); // instantiate gameThread, passing GamePanel class to the Thread's constructor
		gameThread.start();
	}
	
//	@Override
//	public void run() {
//		
//		while (gameThread != null) {
//			// System.out.println("The game is loop is running");
//			
////			long currentTime = System.nanoTime(); // 1 billion nanoseconds = 1 second (1,000,000,000 ns)
////			System.out.println("Current time: " + currentTime);
//			
//			double drawInterval = 1000000000 / FPS; // 1 bil ns  -> so 1 frame per 0.01666... seconds
//			double nextDrawTime = System.nanoTime() + drawInterval;
//			
//			// 1) UPDATE: update information such as character positions
//			update();
//			// 2) DRAW: draw the screen with the updated information
//			repaint(); // this calls the paintComponent method
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime = remainingTime / 1000000; // converting to milliseconds from ns because sleep method only accepts ms
//				
//				if (remainingTime < 0) // time was all used up and went into the negatives so check against that
//					remainingTime = 0;
//				
//				Thread.sleep((long) remainingTime); // pauses game loop
//				
//				nextDrawTime += drawInterval;
//						
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//	} // last edited sep 5 20:26 (last added this run code block)
	
/* another way to do the run method / game loop is the "delta" or "accumulator" method: */
	public void run() {
		
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			// for displaying FPS (debug, kinda)
			if (timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
		}
	} // less code.. - 21:59 ended here. on sep 18 '22 22:04
	
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) { // paintComponent is one of the standard methods from the JPanel library
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		player.draw(g2);
		
		g2.dispose();
	}

}
