package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import database.FlappyDatabase;
import standard.FlappyClientInterface;

public class FlappyServer extends UnicastRemoteObject implements FlappyServerInterface {
	private ArrayList<FlappyClientInterface> allClients = new ArrayList<>();
	private FlappyDatabase database;
	private int deadCounter = 0;
	private int readyCounter = 0;
	private int pipeCounter = 0;
	private int randomNum = 0;

	

	int roundHighScore;
	String roundHighScoreName;

	protected FlappyServer() throws RemoteException {
		super();
		this.database = new FlappyDatabase();
		// TODO Auto-generated constructor stub
	}

	public static void start(String[] args) {
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			Naming.rebind(args[0], new FlappyServer());
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	// Gibt den Indexwert zur�ck
	public int login(FlappyClientInterface clientInterface) throws RemoteException {

		newBirdLogin(allClients.size());
		allClients.add(clientInterface);
		return (allClients.indexOf(clientInterface));

	}

	public void newBirdLogin(int index) {
		for (FlappyClientInterface client : allClients) {
			try {

				client.registerNewBird(index);

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public double[] getPosExt(int remoteIndex) throws RemoteException {
		return allClients.get(remoteIndex).getPos();
	}

	public void birdReady() {
		readyCounter++;
		if (readyCounter % allClients.size() == 0) {
			allBirdsReady();
			readyCounter = 0;
		}
	}

	private void allBirdsReady() {
		for (FlappyClientInterface client : allClients) {
			try {
				client.setGameStarted(true);
				client.setBirdDead(false);
				client.setMainMenu(false);
				roundHighScore = 0;
				roundHighScoreName = null;

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void birdDied() {
		deadCounter++;
		if (deadCounter % allClients.size() == 0) {
			allBirdsDead();
			deadCounter = 0;
		}
	}

	private void allBirdsDead() {
		for (FlappyClientInterface client : allClients) {
			try {
				calculateHighScore();
				client.setHighscore(roundHighScore);
				database.insertHighscore(roundHighScore, roundHighScoreName);
				client.setGameStarted(false);
				client.setMainMenu(true);
				client.setBirdReady(false);
				client.renderBirdAfterGameEnded();

			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

	}

	private void calculateHighScore() {

		int score;
		for (FlappyClientInterface client : allClients) {
			try {
				score = client.getScore();
				if (score > roundHighScore)
					roundHighScore = score;
				roundHighScoreName = client.getName();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// tmp Variable, weil wenn pipeCounter==Client-Gr��e soll zwar ein neuer Wert
	// berechnet werden,
	// aber trotzdem der alte zur�ck gegeben werden, weil die Werte sonst versetzt
	// sind
	public int getServerRandomForPipes() {
		int tmp;
		pipeCounter++; // Hier weil sonst immer in else if schleife
		if (randomNum == 0) {
			randomNum = ThreadLocalRandom.current().nextInt(0, (int) 600 - 364 + 1); // Wenn erstes Mal die Methode neue
																						// randomNum berechnen
		} else if (pipeCounter % allClients.size() == 0) {
			tmp = randomNum; // tmp als Platzhalter f�r alten Wert
			randomNum = ThreadLocalRandom.current().nextInt(0, (int) 600 - 364 + 1); // Berechnung vom neuen Wert
			pipeCounter = 0; // Setze Counter zur�ck
			return tmp; // Gebe alten Wert zur�ck
		}
		// nicht mehr hier

		return this.randomNum;

	}

	@Override
	public HashMap<String, Integer> getTopThree() throws RemoteException {
		return database.getTopThree();
	}

}
