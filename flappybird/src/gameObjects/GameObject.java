package gameObjects;

import java.util.ArrayList;
import java.util.List;

public interface GameObject {
	// koštruktor - prerender
	public void update(long now);

	public void render();

	public enum ObjectSizes {
		BACKGROUND(288, 512), BIRD(56, 40), PIPE(62, 2000), FLOOR(336, 112), GAMEOVER(205, 55), RESTART(134, 47),
		SCORE(84, 112), TITLE(178, 48), HIGHSCORE(52,30), HIGHSCOREBOARD(226,114);
		//143 108; 120 90; 
		private final double length;
		private final double height;

		ObjectSizes(double length, double height) {
			this.length = length;
			this.height = height;
		}

		public double length() {
			return length;
		}

		public double height() {
			return height;
		}
	}

	public enum ImageLink {
		IMGLINK("/images/");

		private final String imglink;

		ImageLink(String imglink) {
			this.imglink = imglink;
		}

		public String imglink() {
			return this.imglink;
		}
	}

	public enum BirdAnimation {
		
		
		BIRD1(new String[] { "bird1an1.png", "bird1an2.png", "bird1an3.png" }),
 		BIRD2(new String[] {"bird2an1.png","bird2an2.png","bird2an3.png"}),
 		BIRD3(new String[] {"bird3an1.png","bird3an2.png","bird3an3.png"}),
		BA(new String[] {"ba1.png","ba1.png","ba1.png"});

		private final String[] birdanimation;
		
		private final static List<BirdAnimation> allAnimations;
		
		static {
			allAnimations = new ArrayList<>();
			allAnimations.add(BIRD1);
			allAnimations.add(BIRD2);
			allAnimations.add(BIRD3);
		}
		
		BirdAnimation(String[] birdImages) {
			this.birdanimation = birdImages;
		}

		public String[] birdanimation() {
			return birdanimation;
		}
		
		public static List allAnimations() {
			return allAnimations;
		}
		
		public static void addBA() {
			if(!allAnimations.contains(BA))
			allAnimations.add(BA);
		}
	}
}


