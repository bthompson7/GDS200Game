package sprites;

import java.net.URL;

public class ExtraLife extends Sprite {

	public ExtraLife(int x, int y) {
		super(x, y);
		initExtraLife();
	}

	
	public void initExtraLife() {
	 	    URL url = ExtraLife.class.getResource("/resources/extra_life.png");
			loadImage(url);
			getImageDimensions();

	}
	public static void main(String[] args) {

	}

}
