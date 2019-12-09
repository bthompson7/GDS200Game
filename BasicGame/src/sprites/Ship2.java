package sprites;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Ship2 extends Sprite {

    private int dx;
    private int dy;
    private int numOfLives = 4;
	private List<Missle> missiles;
	private boolean isSlow = false;


    public Ship2(int x, int y) {
    	super(x, y);
    	initShip();
    }

    private void initShip() {
    	URL url = 	Ship2.class.getResource("/resources/player-2.png");
    	//System.out.println(url);
		missiles = new ArrayList<>();
        loadImage(url);
        getImageDimensions();
    }

    public List<Missle> getMissiles() {
		return missiles;
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
    
    public void fire() {
    	int xPos = x+width;
    	int yPos = y + height / 2;
    	System.out.println(x+width);
    	System.out.println(y + height / 2);
		missiles.add(new Missle(xPos-80, yPos));
	}
    
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if(!isSlow) {
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
        if (key == KeyEvent.VK_SPACE) {
           fire();
        }
    }

	public int getNumOfLives() {
		return numOfLives;
	}

	public void setNumOfLives(int numOfLives) {
		this.numOfLives = numOfLives;
	}
	
	public boolean getIsSlow() {
		return isSlow;
	}

	public void setIsSlow(boolean slow) {
		this.isSlow = slow;
	}
    
}
