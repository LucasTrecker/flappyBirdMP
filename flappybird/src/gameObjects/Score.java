package gameObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import standard.Asset;
import standard.FlappyBird;
import standard.Sprite;

public class Score implements GameObject {
	private FlappyBird flappyBird;
	private Asset asset = new Asset(ImageLink.IMGLINK.imglink() + "score.png", ObjectSizes.SCORE.length(),
			ObjectSizes.SCORE.height());
	private Sprite sprite;
	private GraphicsContext ctx;

	private int screenWidth;
	private int screenHeight;

	private int posX = 10;
	private int posY = 50;
	private int tablePosX, tablePosY;

	private double prevActivePipePosY = FlappyBird.activePipes[0].getPosY();

	public Score(FlappyBird flappyBird, double screenWidth, double screenHeight, GraphicsContext ctx) {
		this.flappyBird = flappyBird;
		this.screenWidth = (int) screenWidth;
		this.screenHeight = (int) screenHeight;
		sprite = new Sprite(asset);
		tablePosX = (int) ((screenWidth - ObjectSizes.SCORE.length()) / 8); // /2
		tablePosY = (int) (screenHeight - ObjectSizes.SCORE.height());// ObjectSizes.FLOOR.height() -
																		// ObjectSizes.SCORE.height() ) / 2
		sprite.setPosX(tablePosX);
		sprite.setPosY(tablePosY);
		sprite.setVel(0, 0);
		ctx.setFont(FlappyBird.appFont);
		ctx.setStroke(FlappyBird.appColor);
		sprite.setCtx(ctx);

		posX = (int) screenWidth / 8 - 10;
		posY = 60;

		this.ctx = ctx;
	}

	public void update(long now) {
		// Punktzahl würde weiter gehen da weiter Pipes geladen werden bis der letzte
		// tot ist --> Score-Erhöhung nur wenn Vogel noch lebt
		// Prüfung aufgrund der Y-Achse, da sich diese beim Ändern der Active-Pipe
		// ändert
		if (FlappyBird.activePipes[0].getPosY() != prevActivePipePosY && !flappyBird.isBirdDead()) {
			FlappyBird.score++;
			prevActivePipePosY = FlappyBird.activePipes[0].getPosY();
		}

	}

	public void render() {
		if (!flappyBird.isGameStarted()) {
			sprite.render();
			ctx.setFill(FlappyBird.appColor);
			ctx.setFont(new Font("04b_19", 32));
			ctx.strokeText(FlappyBird.score + "", posX + ObjectSizes.SCORE.length() / 2 - 10, tablePosY + 56);
			ctx.strokeText(FlappyBird.highscore + "", posX + ObjectSizes.SCORE.length() / 2 - 10, tablePosY + 104);
		}
		// Punktzahlanzeige noch ändern
		if (flappyBird.isGameStarted()) {
			ctx.setFill(Color.WHITE);
			ctx.fillText(FlappyBird.score + "", (int) screenWidth / 2 - 10, posY);
			ctx.strokeText(FlappyBird.score + "", (int) screenWidth / 2 - 10, posY);
		}
	}
}