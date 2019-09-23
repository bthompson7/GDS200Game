package sprites;

import java.net.URL;

public class Alien extends Sprite {
	 private double dy;
	public Alien(int x, int y) {
		super(x, y);
		initAlien();
	}

	private void initAlien() {
		
    	URL url = 	PowerUp.class.getResource("/resources/alien.gif");

		loadImage(url);
		getImageDimensions();
		
	}
	   public void move() {
		    dy=1.2;
	        y += dy;
	        if (y < 1) {
	            y = 1;
	        }
	     
	    }

}
