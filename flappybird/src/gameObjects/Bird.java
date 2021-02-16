package gameObjects;

import javafx.scene.canvas.GraphicsContext;
import standard.Asset;
import standard.FlappyBird;
import standard.Sprite;

public class Bird implements GameObject {
	protected FlappyBird flappyBird;

	private String[] actualAnimation = FlappyBird.getAllBirdAnimations().get(FlappyBird.getAnimationIndex())
			.birdanimation();
	private Asset assets[] = {
			new Asset(ImageLink.IMGLINK.imglink() + actualAnimation[0], ObjectSizes.BIRD.length(),
					ObjectSizes.BIRD.height()),
			new Asset(ImageLink.IMGLINK.imglink() + actualAnimation[1], ObjectSizes.BIRD.length(),
					ObjectSizes.BIRD.height()),
			new Asset(ImageLink.IMGLINK.imglink() + actualAnimation[2], ObjectSizes.BIRD.length(),
					ObjectSizes.BIRD.height()) };
	protected Sprite sprite;
	protected int currentAssetIndex = 0;
	protected long prevTime = 0;
	private float terminalVel = 8;
	private float shiftMax = 10;
	private float shiftDelta = 0;
	private double screenHeight;

	public Bird(FlappyBird flappyBird, double screenWidth, double screenHeight, GraphicsContext ctx) {
		this.flappyBird = flappyBird;
		this.screenHeight = screenHeight;

		sprite = new Sprite(assets[currentAssetIndex]);
		sprite.setPosX((screenWidth - ObjectSizes.BIRD.length()) / 2);
		sprite.setPosY(!flappyBird.isMainMenu() ? screenHeight - ObjectSizes.FLOOR.height() - ObjectSizes.BIRD.height()
				: (screenHeight - ObjectSizes.FLOOR.height()) / 2);
		sprite.setVel(0, 0);
		sprite.setCtx(ctx);
	}

	public void jumpHandler() {
		sprite.setVelY(-8);
	}

	public void update(long now) {
		if (flappyBird.isMainMenu()) { 
			updateBirdHovering();
		} else if (flappyBird.isBirdDead()) {
			updateBirdFalldown();
		} else if (flappyBird.isGameStarted()) {
			if (now - prevTime > 90000000) {
				updateSpriteAnimation();
				prevTime = now;
			}

			if ((sprite.getPosY() + ObjectSizes.BIRD.height()) > (screenHeight - ObjectSizes.FLOOR.height())
					|| sprite.intersects(FlappyBird.activePipes[0]) || sprite.intersects(FlappyBird.activePipes[1])) {

				flappyBird.setBirdDead(true);

			}

			updateBirdPlaying();
		}

		sprite.update();
	}
	//Methode f�r sch�nen Schwebe�bergang des Vogels, aus dem Internet kopiert
	public void updateBirdHovering() {
		double vel = sprite.getVelY();
		if (shiftDelta == 0) {
			sprite.setVelY(0.5);
			shiftDelta += 0.5;
		} else if (shiftDelta > 0) {
			if (vel > 0.1) {
				if (shiftDelta < shiftMax / 2) {
					float shift = (float) (vel * 1.06);
					sprite.setVelY(shift);
					shiftDelta += shift;
				} else {
					float shift = (float) (vel * 0.8);
					sprite.setVelY(shift);
					shiftDelta += shift;
				}
			} else if (vel < 0.1) {
				if (vel > 0) {
					sprite.setVelY(-0.5);
				} else {
					float shift = (float) (vel * 1.06);
					sprite.setVelY(shift);
					shiftDelta += shift;
				}
			}
		} else if (shiftDelta < 0) {
			if (vel < -0.1) {
				if (shiftDelta > -shiftMax / 2) {
					float shift = (float) (vel * 1.06);
					sprite.setVelY(shift);
					shiftDelta += shift;
				} else {
					float shift = (float) (vel * 0.8);
					sprite.setVelY(shift);
					shiftDelta += shift;
				}
			} else if (vel > -0.1) {
				if (vel < 0) {
					sprite.setVelY(0.5);
				} else {
					float shift = (float) (vel * 1.06);
					sprite.setVelY(shift);
					shiftDelta += shift;
				}
			}
		}
	}

	public void updateBirdPlaying() {
		double vel = sprite.getVelY();

		if (vel >= terminalVel)
			sprite.setVelY(vel + 0.2);
		else
			sprite.setVelY(vel + 0.44);
	}

	public void updateBirdFalldown() {
		if (sprite.getPosY() + ObjectSizes.BIRD.height() >= screenHeight - ObjectSizes.FLOOR.height()) {
			sprite.setVel(-2.5, 0); // Vogel soll nach hinten "gezogen werden", beim letzten nicht aber da wird ja
									// eh Spiel beendet
			sprite.setPosY(screenHeight - ObjectSizes.FLOOR.height() - ObjectSizes.BIRD.height());
		} else {
			updateBirdPlaying();
		}

	}

	// Andere V�gel immer als Standard Skin
	public void updateSpriteAnimation() {
		if (!flappyBird.isGameStarted()) {
			currentAssetIndex = 0;
		} else if (currentAssetIndex == 3)
			currentAssetIndex = 0;

		sprite.changeImage(assets[currentAssetIndex]);

		currentAssetIndex++;
	}

	public void render() {
		sprite.render();
	}

	public Sprite getSprite() {
		return sprite;
	}

}