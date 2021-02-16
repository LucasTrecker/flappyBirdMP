package gameObjects;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import standard.Asset;
import standard.FlappyBird;
import standard.Sprite;


public class Background implements GameObject {
	private Asset asset = new Asset(ImageLink.IMGLINK.imglink()
			+ "background.png", ObjectSizes.BACKGROUND.length(), ObjectSizes.BACKGROUND.height());
    private ArrayList<Sprite> sprites = new ArrayList<>();

    public Background(FlappyBird flappyBird, double screenWidth, double screenHeight, GraphicsContext ctx) {
        int backgroundWidth = 0;
        do {
            Sprite background = new Sprite(asset);

            if ((screenHeight - ObjectSizes.FLOOR.height()) < ObjectSizes.BACKGROUND.height())
                background.resizeImage(ObjectSizes.BACKGROUND.length(), ObjectSizes.BACKGROUND.height());
            else
                background.resizeImage(ObjectSizes.BACKGROUND.length(), screenHeight - ObjectSizes.FLOOR.length() );
            
            if (screenHeight > ObjectSizes.BACKGROUND.height())
                background.setPos(backgroundWidth, 0);
            else
                background.setPos(backgroundWidth, screenHeight - ObjectSizes.BACKGROUND.height());

            background.setVel(0, 0);
            background.setCtx(ctx);

            sprites.add(background);
            backgroundWidth += ObjectSizes.BACKGROUND.length();
        } while (backgroundWidth < (screenWidth + ObjectSizes.BACKGROUND.length()));
    }

    public void update(long now) {
    }

    public void render() {
        for (Sprite background : sprites)
            background.render();
    }
}