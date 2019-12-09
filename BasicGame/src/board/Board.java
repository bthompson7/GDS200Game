package board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import sprites.Alien;
import sprites.BigShip;
import sprites.ExtraLife;
import sprites.Missle;
import sprites.PowerUp;
import sprites.Ship;
import sprites.Ship2;
import util.Bucket;

public class Board extends JPanel implements ActionListener {
	private static final long serialVersionUID = 7225208019412994637L;
	private Timer timer;
	private Ship spaceship;
	private Ship2 spaceship2;
	private PowerUp speedBoost;
	private ExtraLife el;
	private boolean ingame;
	private boolean hasExtraLifeSpawned = false;
	private boolean powerUpVisible = false;
	private final int ICRAFT_X = 400;
	private final int ICRAFT_Y = 600;
	private final int B_WIDTH = 400;
	private final int B_HEIGHT = 300;
	private final int DELAY = 15;
	private boolean hitAlien = false;
	private boolean outOfLives = false;
	private int player1Points = 0;
	private int player2Points = 0;
	private int startingDifficulty = 70;
	private boolean changeDiff = false;
	private Clip clip;
	Logger logger = Logger.getLogger("MyLog");
	FileHandler fh;
	URL url = null;
	Bucket<Alien> allAliens = new Bucket<Alien>();
	BigShip big = new BigShip(50, 50);
	JButton b = new JButton("Play Again");

	public Board() {
		initBoard();
	}

	private void initBoard() {
		url = getClass().getResource("/resources/damage_taken.wav");
		try {

			// This block configure the logger with handler and formatter
			fh = new FileHandler("/home/ben/Desktop/3DGame/MyLogFile.txt");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			// the following statement is used to log any messages

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		ingame = true;
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		spaceship = new Ship(ICRAFT_X, ICRAFT_Y);
		spaceship2 = new Ship2(ICRAFT_X + 50, ICRAFT_Y + 50);
		timer = new Timer(DELAY, this);
		timer.start();
		add(b);

		b.setVisible(false);

	}

	private void reinitBoard(Graphics g) {
		url = Board.class.getResource("/resources/damage_taken.wav");
		remove(b);
		add(b);
		b.setVisible(false);
		System.out.println("Pressed play again button");
		ingame = true;
		spaceship = new Ship(ICRAFT_X, ICRAFT_Y);
		spaceship2 = new Ship2(ICRAFT_X + 50, ICRAFT_Y + 50);
		player1Points = 0;
		player2Points = 0;
		addKeyListener(new TAdapter());
		setFocusable(true);
		// numOfPowerUpsCollected = 0;
		outOfLives = false;
		hasExtraLifeSpawned = false;
		g.dispose();
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (ingame) {
			drawObjects(g);
		}
		if (outOfLives) {
			drawGameOver(g);

		}
		Toolkit.getDefaultToolkit().sync();
	}

	private void drawObjects(Graphics g) {
		if (hitAlien) {
			// reinitBoardAfterCollision(g);

			big.setVisible(true);
			allAliens.clear();

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 5000, 5000);
			timer.stop();

			hitAlien = false;

		}
		timer.restart();
		if (spaceship.isVisible()) {
			g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
		}
		if (spaceship2.isVisible()) {
			g.drawImage(spaceship2.getImage(), spaceship2.getX(), spaceship2.getY(), this);
		}

		if (powerUpVisible) {
			g.drawImage(speedBoost.getImage(), speedBoost.getX(), speedBoost.getY(), this);
		}
		if (el != null && el.isVisible()) {
			g.drawImage(el.getImage(), el.getX(), el.getY(), this);

		}
		int index = 0;
		while (index < allAliens.size()) {
			Alien a = allAliens.get(index);
			g.drawImage(a.getImage(), a.getX(), a.getY(), this);
			index++;
		}

		List<Missle> missiles = spaceship.getMissiles();

		for (Missle missile : missiles) {

			g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
		}
		List<Missle> missiles2 = spaceship2.getMissiles();

		for (Missle missile2 : missiles2) {

			g.drawImage(missile2.getImage(), missile2.getX(), missile2.getY(), this);
		}

		String numOfLives = "Player 1 Lives " + spaceship.getNumOfLives();
		String numOfPoints = "Player 1 Points " + player1Points;
		String numOfLives2 = "Player 2 Lives " + spaceship2.getNumOfLives();
		String numOfPoints2 = "Player 1 Points " + player2Points;
		Font small = new Font("Helvetica", Font.PLAIN, 30);

		if (spaceship.getNumOfLives() > 1) {
			g.setColor(Color.GREEN);
		} else if (spaceship.getNumOfLives() == 1) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.RED);
		}
	
		g.setFont(small);
		g.drawString(numOfLives, 5, 30);
		g.setColor(Color.WHITE);
		g.drawString(numOfPoints, 5, 60);
		
		if (spaceship2.getNumOfLives() > 1) {
			g.setColor(Color.GREEN);
		} else if (spaceship2.getNumOfLives() == 1) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.RED);
		}
		g.drawString(numOfLives2, 750, 30);
		g.setColor(Color.WHITE);
		g.drawString(numOfPoints2, 750, 60);
	

	}

	private void updateShip1Missiles() {

		List<Missle> missiles = spaceship.getMissiles();
		for (int i = 0; i < missiles.size(); i++) {

			Missle missile = missiles.get(i);

			if (missile.isVisible()) {

				missile.move(false);
			} else {

				missiles.remove(i);
			}
		}
	}

	private void updateShip2Missles() {
		List<Missle> missiles2 = spaceship2.getMissiles();
		for (int i = 0; i < missiles2.size(); i++) {

			Missle missile2 = missiles2.get(i);

			if (missile2.isVisible()) {

				missile2.move(true);
			} else {

				missiles2.remove(i);
			}
		}

	}

	private void drawGameOver(Graphics g) {

		// String msg = "Game Over";
		String msg2 = "";
		if (player1Points > player2Points) {
			msg2 = "Player 1 Wins with " + player1Points + " points";

		} else if(player2Points > player1Points) {
			msg2 = "Player 2 Wins with " + player2Points + " points";

		}else {
			msg2 = "The game ended in a tie!";
		}
		Font small = new Font("Helvetica", Font.BOLD, 30);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg2, (B_WIDTH + 50 - fm.stringWidth(msg2)) + 100 / 2, 100 / 2);
		b.setVisible(true);
		b.addActionListener((e) -> {
			reinitBoard(g);
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		inGame();
		updateShip();
		generateAliens();
		moveAliens();
		updateShip1Missiles();
		updateShip2Missles();
		shouldPowerUpSpawn();
		shouldExtraLifeSpawn();
		repaint();
		checkCollisions();

	}

	private void inGame() {
		if (!ingame) {
			timer.stop();
		}
	}

	private void generateAliens() {
		Random rand = new Random();
		if (player1Points >= 10 && !changeDiff || player2Points >= 10 && !changeDiff) {
			startingDifficulty -= 10;
			changeDiff = true;
			System.out.println(startingDifficulty);
		}
		int num = rand.nextInt(startingDifficulty);
		if (num == 0) {
			int xLoc = rand.nextInt(1000);
			Alien alien = new Alien(xLoc, 0);
			allAliens.add(alien);
			System.out.println("num of aliens:" + allAliens.size());
		}
	}

	private void updateShip() {
		if (spaceship.isVisible()) {
			spaceship.move();
		}
		if (spaceship2.isVisible()) {
			spaceship2.move();
		}
	}

	private void moveAliens() {
		int sizeOfAliens = 0;
		while (sizeOfAliens < allAliens.size()) {
			Alien a = allAliens.get(sizeOfAliens);
			if (a.isVisible()) {
				a.move();
			}
			sizeOfAliens++;
		}
	}

	/*
	 * Checks the collisions between the blue square and the aliens and the red
	 * square by checking if any of the points intersect
	 */
	public void checkCollisions() {

		int numOfLives;
		Rectangle r3 = spaceship.getBounds(); // PLAYER 1
		Rectangle r = spaceship2.getBounds(); // PLAYER 2
		int sizeOfAliens = 0;

		if (el != null) {
			Rectangle extraLife = el.getBounds();
			if (r3.intersects(extraLife)) {
				int lives = spaceship.getNumOfLives();
				lives++;
				spaceship.setNumOfLives(lives);
				el = null;
			} else if (r.intersects(extraLife)) {
				int lives2 = spaceship2.getNumOfLives();
				lives2++;
				spaceship2.setNumOfLives(lives2);
				el = null;
			}
		}

		if (powerUpVisible) {
			Rectangle r4 = speedBoost.getBounds();
			if (r3.intersects(r4)) {
				speedBoost.setVisible(false);
				powerUpVisible = false;
				player1Points++;
			}
			if (r.intersects(r4)) {
				speedBoost.setVisible(false);
				powerUpVisible = false;
				player2Points++;
			}
		}
		// player 1 r3
		// player 2 r
		while (sizeOfAliens < allAliens.size()) {
			Alien a = allAliens.get(sizeOfAliens);
			Rectangle r2 = a.getBounds();
			if (r3.intersects(r2)) {
				System.out.println("touched an alien");
				numOfLives = spaceship.getNumOfLives();
				numOfLives--;
				if (numOfLives < 0) {
					outOfLives = true;
					ingame = false;
					System.out.println("out of lives");
				} else {
					spaceship.setNumOfLives(numOfLives);
				}
				hitAlien = true;
				allAliens.clear();
				playTheSound();
			} else if (r.intersects(r2)) {
				System.out.println("touched an alien");
				numOfLives = spaceship2.getNumOfLives();
				numOfLives--;
				if (numOfLives < 0) {
					outOfLives = true;
					ingame = false;
					System.out.println("out of lives");
				} else {
					spaceship2.setNumOfLives(numOfLives);

				}
				playTheSound();
				hitAlien = true;
				allAliens.clear();
			}
			sizeOfAliens++;

		}

	
		// playAudio();


	}

	private void shouldPowerUpSpawn() {
		Random rand = new Random();
		if (!powerUpVisible) {
			int num = rand.nextInt(300);
			if (num == 0) {
				System.out.println("Power up spawned");
				powerUpVisible = true;
				int xLoc = rand.nextInt(600);
				int yLoc = rand.nextInt(600);
				speedBoost = new PowerUp(xLoc, yLoc);
				speedBoost.setVisible(true);
			}
		}
	}

	public void shouldExtraLifeSpawn() {
		Random rng = new Random();
		int chance = rng.nextInt(300);
		if (spaceship.getNumOfLives() < 4 && chance == 0 && !hasExtraLifeSpawned) {
			hasExtraLifeSpawned = true;
			el = new ExtraLife(400, 400);

		}

	}

	public void playTheSound() {

		URL url = getClass().getResource("/resources/damage_taken.wav");// You can change this to whatever other sound
																		// you have
		SoundEffect(url);// this method will load the sound

		if (clip.isRunning()) {
			clip.stop(); // Stop the player if it is still running
		}
		clip.setFramePosition(0); // rewind to the beginning
		clip.start(); // Start playing

	}

	private void SoundEffect(URL url) {
		try {
			// Set up an audio input stream piped from the sound file.
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			// Get a clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			spaceship.keyReleased(e);
			spaceship2.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			spaceship.keyPressed(e);
			spaceship2.keyPressed(e);

		}
	}
}
