package standard;

import java.rmi.RemoteException;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gameObjects.Background;
import gameObjects.Bird;
import gameObjects.BirdExt;
import gameObjects.Floor;
import gameObjects.GameObject;
import gameObjects.GameObject.BirdAnimation;
import gameObjects.GameOver;
import gameObjects.Highscore;
import gameObjects.Highscoreboard;
import gameObjects.Pipes;
import gameObjects.Restart;
import gameObjects.Score;
import gameObjects.Title;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FlappyBird extends Application {

	private Map<String, GameObject> gameObjects = new LinkedHashMap<String, GameObject>();
	private Map<Integer, BirdExt> allBirdsExt = new LinkedHashMap<>();

	private Bird bird;
	private Restart restart;
	private Highscore highscoreObject;
	public static Sprite activePipes[];

	private static List<BirdAnimation> allBirdAnimations = BirdAnimation.allAnimations();
	private static int animationIndex = 0;

	public static Font appFont = Font.loadFont(FlappyBird.class.getResource("/fonts/04b_19.ttf").toExternalForm(), 42);
	public static Color appColor = Color.web("#543847");

	private String playerName;
	private String serverName;
	private int remoteIndex;

	private Scene scene;
	private GraphicsContext ctx;

	private boolean birdDead = false;
	private boolean birdReady = false;
	private boolean mainMenu = true;
	private boolean gameStarted = false;
	private boolean showHighScoreBoard = false;

	private double width = 450;
	private double height = 600;
	private double minWidth = 365;
	private double minHeight = 412;

	public static int score = 0;
	public static int highscore = 0;

	private StringBuilder ba = new StringBuilder();
	private AnimationTimer timer;
	private ServerInteraction serverInteraction;

	public void start(Stage stage) throws RemoteException {
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				Platform.exit();
			}
		});
		Parameters params = getParameters();
		List<String> list = params.getRaw();
		this.playerName = list.get(0);
		this.serverName = list.get(1);
		stage.setTitle("Flappy bird");
		stage.getIcons().add(new Image("/images/icon.png"));
		stage.setMinWidth(minWidth);
		stage.setMinHeight(minHeight);

		this.serverInteraction = new ServerInteraction(this);

		this.remoteIndex = serverInteraction.login();

		setScene(stage);
		initRender();
		startGameLoop();

		stage.show();

		if (remoteIndex != 0) {
			for (int i = 0; i < remoteIndex; i++) {
				registerNewBird(i);
			}
		}
	}

	private void setScene(Stage stage) {
		Pane pane = new Pane();
		Canvas canvas = new Canvas();
		ctx = canvas.getGraphicsContext2D();

		canvas.heightProperty().bind(pane.heightProperty());
		canvas.widthProperty().bind(pane.widthProperty());

		canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
			width = newVal.doubleValue();
			resizeHandler();
		});
		canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
			height = newVal.doubleValue();
			resizeHandler();
		});

		pane.getChildren().addAll(canvas);
		scene = new Scene(pane, width, height);

		setInputHandlers(scene);
		stage.setScene(scene);
	}

	private void setInputHandlers(Scene scene) {

		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.RIGHT && mainMenu) {
				if (animationIndex == allBirdAnimations.size() - 1) {
					animationIndex = 0;
				} else {
					animationIndex++;
				}
				initRender();
			} else if (e.getCode() == KeyCode.LEFT && mainMenu) {
				if (animationIndex == 0) {
					animationIndex = allBirdAnimations.size() - 1;
				} else {
					animationIndex--;
				}
				initRender();
			} else if (e.getCode() == KeyCode.SPACE) {
				inputHandler(-1, -1);
			} else if (e.getCode() == KeyCode.B) {
				ba.append("B");

			} else if (e.getCode() == KeyCode.A) {
				ba.append("A");
				if (ba.substring(0).equals("BA")) {
					BirdAnimation.addBA();
				} else {
					ba = new StringBuilder();
				}
			}
		});

		scene.setOnMousePressed(e -> {
			inputHandler(e.getX(), e.getY());
		});
	}

	private void inputHandler(double posX, double posY) {
		if (gameStarted && !isBirdDead()) {
			bird.jumpHandler();
		} else if (posX == -1 && posY == -1 || restart.checkClick(posX, posY) && !isBirdReady()) {
			initRender();
			setBirdReady(true);
			showHighScoreBoard = false;

			FlappyBird.score = 0;

		} else if (highscoreObject.checkClick(posX, posY) && !isBirdReady()) {
			changeBoolHighScoreBoard();
		}
	}

	private void changeBoolHighScoreBoard() {
		if (showHighScoreBoard) {
			showHighScoreBoard = false;
		} else {
			showHighScoreBoard = true;
		}

	}

	void setBirdReady(boolean b) {
		if (b)
			serverInteraction.birdReady();
		this.birdReady = b;
	}

	private void resizeHandler() {
		initRender();
	}

	public void initRender() {
		ctx.clearRect(0, 0, width, height);
		gameObjects.clear();

		gameObjects.put("background", new Background(this, width, height, ctx));

		gameObjects.put("pipes", new Pipes(this, width, height, ctx));

		gameObjects.put("floor", new Floor(this, width, height, ctx));

		gameObjects.put("score", new Score(this, width, height, ctx));

		gameObjects.put("title", new Title(this, width, height, ctx));

		gameObjects.put("gameover", new GameOver(this, width, height, ctx));

		gameObjects.put("highscoreboard", new Highscoreboard(this, width, height, ctx));

		restart = new Restart(this, width, height, ctx);
		gameObjects.put("restart", restart);

		bird = new Bird(this, width, height, ctx);
		gameObjects.put("bird", bird);

		highscoreObject = new Highscore(this, width, height, ctx);
		gameObjects.put("highscore", highscoreObject);

		allBirdsExt.forEach((k, v) -> gameObjects.put("extern" + k, v));

	}

	public HashMap<String, Integer> getTopThree() {
		try {
			return serverInteraction.getTopThree();
		} catch (RemoteException e) {
			e.printStackTrace();

		}
		return null;
	}

	public void renderBirdAfterGameEnded() {
		gameObjects.remove("bird");
		bird = new Bird(this, width, height, ctx);
		gameObjects.put("bird", bird);
	}

	private void updateGame(long now) {
		for (GameObject gameObject : gameObjects.values())
			try {
				gameObject.update(now);
			} catch (ConcurrentModificationException e) {

			}
	}

	private void renderGame() {
		ctx.clearRect(0, 0, width, height);

		for (GameObject gameObject : gameObjects.values()) {
			gameObject.render();

		}
	}

	private void startGameLoop() {
		timer = new AnimationTimer() {
			public void handle(long now) {
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				updateGame(now);
				renderGame();
			}
		};
		timer.start();
	}

	public void registerNewBird(int remoteIndex) {
		allBirdsExt.put(remoteIndex, new BirdExt(this, width, height, ctx, remoteIndex));
		initRender();

	}

	public double[] getPosExt(int remoteIndex2) {
		try {
			return serverInteraction.getPosExt(remoteIndex2);
		} catch (NullPointerException e) {
			System.out.println("Noch keine Position");
			return null;
		}
	}

	public double[] getPos() {

		return this.bird.getSprite().getPos();
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	public boolean isBirdDead() {
		return birdDead;
	}

	public void setBirdDead(boolean birdDead) {
		if (birdDead) {

			serverInteraction.birdDied();

		}
		this.birdDead = birdDead;
	}

	public boolean isBirdReady() {
		return birdReady;
	}

	public boolean isMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(boolean mainMenu) {
		this.mainMenu = mainMenu;
	}

	public int getRemoteIndex() {
		return remoteIndex;
	}

	// Soll nur synchronisiert werden wenn Spiel gestartet ist
	// Vorher schon zu synchronisieren war zu kompliziert da bereits beim
	// Start-InitRender() Werte berechnet werden
	// wenn aber beispielweise schon ein Client existiert
	public int getServerRandomForPipes() {
		if (gameStarted) {
			return serverInteraction.getServerRandomForPipes();
		} else {
			return 0;
		}
	}

	public int getScore() {
		return score;
	}

	public void setHighscore(int highscoreAll) {
		highscore = highscoreAll;

	}

	public static int getAnimationIndex() {
		return animationIndex;
	}

	public static List<BirdAnimation> getAllBirdAnimations() {
		return allBirdAnimations;
	}

	public Scene getScene() {
		return scene;
	}

	public String getName() {
		if (!this.playerName.equals(null)) {
			return this.playerName;
		}

		return "unknown";
	}

	public boolean isShowHighScoreBoard() {
		return showHighScoreBoard;
	}

	public String getServerName() {
		return serverName;
	}

}

//evtl noch Servername hinzufügen bei Server und Client