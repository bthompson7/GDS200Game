package sprites;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;

public class Ship2 extends Sprite {

    private int dx;
    private int dy;
    private int numOfLives = 2;

    public Ship2(int x, int y) {
    	super(x, y);
    	initShip();
    }

    private void initShip() {
    	URL url = 	Ship.class.getResource("/resources/player-2.png");
    	System.out.println(url);
        loadImage(url);
        getImageDimensions();
    }


    public void move() {

        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }
        if (y < 1) {
            y = 1;
        }
        
        //to stop the player from leaving on the right and bottom parts of the board
        if(x > 727) {
        	x = 727;
        }
        if(y > 635) {
        	y = 635;
        }
     
    }
    
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

	public int getNumOfLives() {
		return numOfLives;
	}

	public void setNumOfLives(int numOfLives) {
		this.numOfLives = numOfLives;
	}
    
}
