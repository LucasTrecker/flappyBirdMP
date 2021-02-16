package gameObjects;

import javafx.scene.canvas.GraphicsContext;
import standard.Asset;
import standard.FlappyBird;
import standard.Sprite;

public class Title implements GameObject {
	private FlappyBird flappyBird;
	private Asset asset = new Asset(ImageLink.IMGLINK.imglink() + "title.png", ObjectSizes.TITLE.length(),
			ObjectSizes.TITLE.height());
	private Sprite sprite;

	public Title(FlappyBird flappyBird, double screenWidth, double screenHeight, GraphicsContext ctx) {
		this.flappyBird = flappyBird;
		sprite = new Sprite(asset);
		sprite.setPosX((screenWidth - ObjectSizes.TITLE.length()) / 2);
		sprite.setPosY(40);
		sprite.setVel(0, 0);
		sprite.setCtx(ctx);
	}

	public void update(long now) {
	}

	public void render() {
		if (flappyBird.isMainMenu())
			sprite.render();
	}
}