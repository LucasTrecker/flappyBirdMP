package gameObjects;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import standard.Asset;
import standard.FlappyBird;
import standard.Sprite;

public class Highscore implements GameObject {
	private FlappyBird flappyBird;
	private Asset asset = new Asset(ImageLink.IMGLINK.imglink() + "highscore.png", ObjectSizes.HIGHSCORE.length(),
			ObjectSizes.HIGHSCORE.height());
	private Sprite sprite;

	public Highscore(FlappyBird flappyBird, double screenWidth, double screenHeight, GraphicsContext ctx) {
		this.flappyBird = flappyBird;
		sprite = new Sprite(asset);
		sprite.setPosX(screenWidth - 100);
		sprite.setPosY(screenHeight - 72);
		sprite.setVel(0, 0);
		sprite.setCtx(ctx);
	}

	public boolean checkClick(double posX, double posY) {
		return sprite.intersects(new Rectangle2D(posX, posY, 1, 1));
	}

	public void update(long now) {
	}

	public void render() {
		if (flappyBird.isMainMenu() && !flappyBird.isBirdReady())
			sprite.render();
	}

}
