package gameObjects;

import javafx.scene.canvas.GraphicsContext;
import standard.Asset;
import standard.FlappyBird;
import standard.Sprite;

public class GameOver implements GameObject {
	private FlappyBird flappyBird;
	private Asset asset = new Asset(ImageLink.IMGLINK.imglink() + "game_over.png", ObjectSizes.GAMEOVER.length(),
			ObjectSizes.GAMEOVER.height());
	private Sprite sprite;

	public GameOver(FlappyBird flappyBird, double screenWidth, double screenHeight, GraphicsContext ctx) {
		this.flappyBird = flappyBird;
		sprite = new Sprite(asset);
		sprite.setPosX((screenWidth - ObjectSizes.GAMEOVER.length()) / 2);
		sprite.setPosY(40);
		sprite.setVel(0, 0);
		sprite.setCtx(ctx);
	}

	public void update(long now) {
	}

	public void render() {
		if (flappyBird.isBirdDead() && !flappyBird.isMainMenu())
			sprite.render();
	}
}