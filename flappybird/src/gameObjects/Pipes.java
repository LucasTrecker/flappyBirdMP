package gameObjects;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import standard.Asset;
import standard.FlappyBird;
import standard.Sprite;

public class Pipes implements GameObject {
	private FlappyBird flappyBird;
	private int WIDTH = 62;
	private int HEIGHT = 2000;
	private Asset assetUp = new Asset(ImageLink.IMGLINK.imglink() + "up_pipe.png", WIDTH, HEIGHT);
	private Asset assetDown = new Asset(ImageLink.IMGLINK.imglink() + "down_pipe.png", WIDTH, HEIGHT);
	private ArrayList<Sprite> spritesUp = new ArrayList<>();
	private ArrayList<Sprite> spritesDown = new ArrayList<>();

	private double screenHeight, screenWidth;
	private GraphicsContext ctx;

	public Pipes(FlappyBird flappyBird, double screenWidth, double screenHeight, GraphicsContext ctx) {
		this.flappyBird = flappyBird;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.ctx = ctx;

		Sprite pipes[] = createPipes(screenWidth + 200);
		FlappyBird.activePipes = pipes;
		spritesUp.add(pipes[0]);
		spritesDown.add(pipes[1]);
	}

	public void update(long now) {
		if (flappyBird.isGameStarted()) {
			for (int i = 0; i < spritesUp.size(); i++) {
				spritesUp.get(i).update();
				spritesDown.get(i).update();

				if (FlappyBird.activePipes[0].getPosX() + ObjectSizes.PIPE.length() < screenWidth / 2) {
					FlappyBird.activePipes = new Sprite[] { spritesUp.get(i), spritesDown.get(i) };
				}
			}
		}
	}

	public void render() {
		for (Sprite pipe : spritesUp)
			pipe.render();

		for (Sprite pipe : spritesDown)
			pipe.render();
		if (spritesUp.get(spritesUp.size() - 1).getPosX() < screenWidth) {

			Sprite pipes[] = createPipes(spritesUp.get(spritesUp.size() - 1).getPosX() + 260); 

			spritesUp.add(pipes[0]);
			spritesDown.add(pipes[1]);
		}

		if (spritesUp.get(0).getPosX() < -ObjectSizes.PIPE.length()) {
			spritesUp.remove(0);
			spritesDown.remove(0);
		}
	}

	private Sprite[] createPipes(double posX) {
		int randomNum = flappyBird.getServerRandomForPipes();

		Sprite pipeUp = new Sprite(assetUp);
		pipeUp.setPos(posX, 206 + randomNum);
		pipeUp.setVel(-2.5, 0);
		pipeUp.setCtx(ctx);

		Sprite pipeDown = new Sprite(assetDown);
		pipeDown.setPos(posX, -1954 + randomNum);
		pipeDown.setVel(-2.5, 0);
		pipeDown.setCtx(ctx);

		return new Sprite[] { pipeUp, pipeDown };
	}
}