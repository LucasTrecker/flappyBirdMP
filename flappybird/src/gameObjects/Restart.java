package gameObjects;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import standard.Asset;
import standard.FlappyBird;
import standard.Sprite;

public class Restart implements GameObject {
	private FlappyBird flappyBird;
	private Asset asset = new Asset(ImageLink.IMGLINK.imglink() + "restart.png", ObjectSizes.RESTART.length(),
			ObjectSizes.RESTART.height());
	private Sprite sprite;

	public Restart(FlappyBird flappyBird, double screenWidth, double screenHeight, GraphicsContext ctx) {
		this.flappyBird = flappyBird;
		sprite = new Sprite(asset);
		sprite.setPosX((screenWidth - ObjectSizes.RESTART.length()) / 2);
		sprite.setPosY(screenHeight - 80);
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