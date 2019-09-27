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
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import sprites.*;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	private static final long serialVersionUID = 7225208019412994637L;
	private Timer timer;
	private Ship spaceship;
	private PowerUp speedBoost;
	private boolean ingame;
	private boolean powerUpVisible = false;
	private final int ICRAFT_X = 400;
	private final int ICRAFT_Y = 600;
	private final int B_WIDTH = 400;
	private final int B_HEIGHT = 300;
	private final int DELAY = 15;
	private boolean hitAlien = false;
	private boolean outOfLives = false;
	private int numOfPowerUpsCollected = 0;
	private ArrayList<Alien> allAliens = new ArrayList<Alien>();

	public Board() {
		initBoard();
	}

	private void initBoard() {

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		ingame = true;
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		spaceship = new Ship(ICRAFT_X, ICRAFT_Y);
		timer = new Timer(DELAY, this);
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
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			allAliens.clear();
			hitAlien = false;
		}
		if (spaceship.isVisible()) {
			g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
		}
		if (powerUpVisible) {
			g.drawImage(speedBoost.getImage(), speedBoost.getX(), speedBoost.getY(), this);
		}
		int index = 0;
		while (index < allAliens.size()) {
			Alien a = allAliens.get(index);
			g.drawImage(a.getImage(), a.getX(), a.getY(), this);
			index++;
		}

		String numOfLives = "Lives " + spaceship.getNumOfLives();
		String numOfPoints = "Points " + numOfPowerUpsCollected;
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

	}

	private void drawGameOver(Graphics g) {

		String msg = "Game Over";
		String msg2 = "Final Score: " + numOfPowerUpsCollected;
		Font small = new Font("Helvetica", Font.BOLD, 30);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH + 350 - fm.stringWidth(msg)) / 2, B_HEIGHT + 50 / 2);
		g.drawString(msg2, (B_WIDTH + 50 - fm.stringWidth(msg2)) + 100 / 2, B_HEIGHT + 100 / 2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		inGame();
		updateShip();
		generateAliens();
		moveAliens();
		shouldPowerUpSpawn();
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
		int num = rand.nextInt(75);
		if (num == 0) {
			int xLoc = rand.nextInt(800);
			Alien alien = new Alien(xLoc, 0);
			allAliens.add(alien);
			System.out.println(allAliens.size());
		}
	}

	private void updateShip() {
		if (spaceship.isVisible()) {
			spaceship.move();
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

		Rectangle r3 = spaceship.getBounds();
		int sizeOfAliens = 0;
		if (powerUpVisible) {
			Rectangle r4 = speedBoost.getBounds();
			if (r3.intersects(r4)) {
				speedBoost.setVisible(false);
				powerUpVisible = false;
				numOfPowerUpsCollected++;
			}
		}
		while (sizeOfAliens < allAliens.size()) {
			Alien a = allAliens.get(sizeOfAliens);
			Rectangle r2 = a.getBounds();
			if (r3.intersects(r2)) {
				System.out.println("touched an alien");
				// here
				int numOfLives = spaceship.getNumOfLives();
				numOfLives--;
				if (numOfLives < 0) {
					outOfLives = true;
					ingame = false;
					System.out.println("out of lives");
				}
				spaceship.setNumOfLives(numOfLives);
				hitAlien = true;
				allAliens.clear();
			}
			sizeOfAliens++;
		}

	}

	private void shouldPowerUpSpawn() {
		Random rand = new Random();
		if (!powerUpVisible) {
			int num = rand.nextInt(400);
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

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			spaceship.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			spaceship.keyPressed(e);
		}
	}
}
