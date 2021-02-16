package gameObjects;

import javafx.scene.canvas.GraphicsContext;
import standard.Asset;
import standard.FlappyBird;

public class BirdExt extends Bird {
	int remoteIndex;
	private String[] birdExtAnimation = FlappyBird.getAllBirdAnimations().get(0).birdanimation();
	private Asset assets[] = {
			new Asset(ImageLink.IMGLINK.imglink() + birdExtAnimation[0], ObjectSizes.BIRD.length(),
					ObjectSizes.BIRD.height()),
			new Asset(ImageLink.IMGLINK.imglink() + birdExtAnimation[1], ObjectSizes.BIRD.length(),
					ObjectSizes.BIRD.height()),
			new Asset(ImageLink.IMGLINK.imglink() + birdExtAnimation[2], ObjectSizes.BIRD.length(),
					ObjectSizes.BIRD.height()) };

	public BirdExt(FlappyBird flappyBird, double screenWidth, double screenHeight, GraphicsContext ctx,
			int remoteIndex) {
		super(flappyBird, screenWidth, screenHeight, ctx);
		this.remoteIndex = remoteIndex;
	}

	@Override
	public void update(long now) {

		double[] tmp = flappyBird.getPosExt(remoteIndex);
		this.sprite.setPos(tmp[0], tmp[1]);

		if (flappyBird.isGameStarted() && tmp[1] != 448) { // Wenn der Vogel am Boden ist soll er nicht mehr animiert
															// werden; Test ob Vogel tot w�re zu aufwendig
			if (now - prevTime > 90000000) {
				updateSpriteAnimation();
				prevTime = now;
			}
		}
	}

	public void render() {
		sprite.render();
	}

	public void updateSpriteAnimation() {
		if (!flappyBird.isGameStarted()) {
			currentAssetIndex = 0;
		} else if (currentAssetIndex == 3)
			currentAssetIndex = 0;

		sprite.changeImage(assets[currentAssetIndex]);

		currentAssetIndex++;
	}

}
