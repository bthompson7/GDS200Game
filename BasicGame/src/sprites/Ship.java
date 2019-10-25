package sprites;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;

public class Ship extends Sprite {

    private int dx;
    private int dy;
    private int numOfLives = 2;

    public Ship(int x, int y) {
    	super(x, y);
    	initShip();
    }

    private void initShip() {
    	URL url = 	Ship.class.getResource("/resources/smallSquare.png");
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
        if(x > 927) {
        	x = 927;
        }
        if(y > 635) {
        	y = 635;
        }
     
    }
    
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            dx = -2;
        }

        if (key == KeyEvent.VK_D) {
            dx = 2;
        }

        if (key == KeyEvent.VK_W) {
            dy = -2;
        }

        if (key == KeyEvent.VK_S) {
            dy = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            dx = 0;
        }

        if (key == KeyEvent.VK_D) {
            dx = 0;
        }

        if (key == KeyEvent.VK_W) {
            dy = 0;
        }

        if (key == KeyEvent.VK_S) {
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
