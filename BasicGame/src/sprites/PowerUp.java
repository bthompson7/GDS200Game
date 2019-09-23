package sprites;

import java.net.URL;

public class PowerUp extends Sprite {

    public PowerUp(int x, int y) {
    	super(x, y);
    	initPowerUp();
    }

	private void initPowerUp() {
    	URL url = 	PowerUp.class.getResource("/resources/power-up.png");
		loadImage(url);
		getImageDimensions();

		
	}

	

}
