package gameObjects;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import standard.Asset;
import standard.FlappyBird;
import standard.Sprite;

public class Highscoreboard implements GameObject {

	private FlappyBird flappyBird;
	private Asset asset = new Asset(ImageLink.IMGLINK.imglink() + "highscoreboard.png",
			ObjectSizes.HIGHSCOREBOARD.length(), ObjectSizes.HIGHSCOREBOARD.height());
	private Sprite sprite;
	private GraphicsContext ctx;

	private int screenWidth;
	private int screenHeight;

	private int tablePosX, tablePosY;

	public Highscoreboard(FlappyBird flappyBird, double screenWidth, double screenHeight, GraphicsContext ctx) {
		this.flappyBird = flappyBird;
		this.screenWidth = (int) screenWidth;
		this.screenHeight = (int) screenHeight;
		sprite = new Sprite(asset);
		tablePosX = (int) ((screenWidth - ObjectSizes.HIGHSCOREBOARD.length()) / 2); 
		tablePosY = (int) ((screenHeight - ObjectSizes.HIGHSCOREBOARD.height()) / 2);
		sprite.setPosX(tablePosX);
		sprite.setPosY(tablePosY);
		sprite.setVel(0, 0);
		sprite.setCtx(ctx);

		this.ctx = ctx;
	}

	@Override
	public void update(long now) {

	}

	@Override
	public void render() {
		if (flappyBird.isShowHighScoreBoard()) {
			sprite.render();
			HashMap<String, Integer> topThree = new HashMap<>();
			int offShift = 50;
			int i = 1;
			topThree = flappyBird.getTopThree();
			for (Entry<String, Integer> entry : topThree.entrySet()) {
				ctx.setFill(Color.WHITE);
				ctx.setFont(new Font("04b_19", 20));
				ctx.fillText(i + ". " + entry.getKey() + ": " + entry.getValue() + "",
						(screenWidth - ObjectSizes.HIGHSCOREBOARD.length()) / 2 + 15,
						(screenHeight - ObjectSizes.HIGHSCOREBOARD.height()) / 2 + offShift);
				ctx.strokeText(i + ". " + entry.getKey() + ": " + entry.getValue() + "",
						(screenWidth - ObjectSizes.HIGHSCOREBOARD.length()) / 2 + 15,
						(screenHeight - ObjectSizes.HIGHSCOREBOARD.height()) / 2 + offShift);
				offShift += 25;
				i++;
			}

		}

	}

}
