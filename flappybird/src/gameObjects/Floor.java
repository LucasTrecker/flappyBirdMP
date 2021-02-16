package gameObjects;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import standard.Asset;
import standard.FlappyBird;
import standard.Sprite;

public class Floor implements GameObject {
	private FlappyBird flappyBird;
    private Asset asset = new Asset(ImageLink.IMGLINK.imglink()+"floor.png", ObjectSizes.FLOOR.length(), ObjectSizes.FLOOR.height());
    private ArrayList<Sprite> sprites = new ArrayList<>();

	public Floor(FlappyBird flappyBird, double screenWidth, double screenHeight, GraphicsContext ctx) {
    	this.flappyBird=flappyBird;
        int floorWidth = 0;
        do {
            Sprite floor = new Sprite(asset);
            floor.setPos(floorWidth, screenHeight - ObjectSizes.FLOOR.height());
            floor.setVel(-2.5, 0);
            floor.setCtx(ctx);

            sprites.add(floor);
            floorWidth += ObjectSizes.FLOOR.length();
        } while (floorWidth < (screenWidth + ObjectSizes.FLOOR.length()));
    }

    public void update(long now) {
        if (flappyBird.isGameStarted()) {        
            for (Sprite floor : sprites)
                floor.update();

            if (sprites.get(0).getPosX() < -ObjectSizes.FLOOR.length()) {
                Sprite firstFloor = sprites.get(0);

                sprites.remove(0);
                firstFloor.setPosX( sprites.get( sprites.size() - 1 ).getPosX() + ObjectSizes.FLOOR.length() );
                sprites.add(firstFloor);
            }
        }
    }

    public void render() {
        for (Sprite floor : sprites)
            floor.render();
    }
}