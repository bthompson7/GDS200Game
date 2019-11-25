package sprites;

import java.net.URL;

public class Missle extends Sprite {
	   private final int BOARD_WIDTH = 800;
	    private final int MISSILE_SPEED = 2;

	public Missle(int x, int y) {
		super(x, y);
		initMissile();
	}
	
	 private void initMissile() {
	        
	    	URL url = 	Missle.class.getResource("/resources/smallSquare.png");
	        loadImage(url);  
	        getImageDimensions();
	    }

	    public void move() {
	        
	        x += MISSILE_SPEED;
	        
	        if (x > BOARD_WIDTH) {
	            visible = false;
	        }
	    }
}
