package standard;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import server.FlappyServerInterface;

public class ServerInteraction extends UnicastRemoteObject implements FlappyClientInterface {
	FlappyServerInterface serverInterface;
	FlappyBird flappybird;

	public ServerInteraction(FlappyBird flappybird) throws RemoteException {
		super();
		try {

			this.flappybird = flappybird;
			this.serverInterface = (FlappyServerInterface) Naming.lookup("//localhost/" + flappybird.getServerName());
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int login() {
		try {
			return serverInterface.login(this);
		} catch (RemoteException e) {
			return 0;
		}
	}

	public void birdReady() {
		try {
			serverInterface.birdReady();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void birdDied() {
		try {
			serverInterface.birdDied();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public double[] getPosExt(int remoteIndex) {
		try {
			return serverInterface.getPosExt(remoteIndex);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void registerNewBird(int index) throws RemoteException {
		flappybird.registerNewBird(index);

	}

	@Override
	public double[] getPos() throws RemoteException {
		return flappybird.getPos();
	}

	@Override
	public void setBirdDead(boolean dead) throws RemoteException {
		flappybird.setBirdDead(dead);

	}

	@Override
	public void setGameStarted(boolean b) throws RemoteException {
		flappybird.setGameStarted(b);

	}

	@Override
	public void setMainMenu(boolean b) throws RemoteException {
		flappybird.setMainMenu(b);

	}

	@Override
	public void initRender() throws RemoteException {
		flappybird.initRender();

	}

	@Override
	public void renderBirdAfterGameEnded() throws RemoteException {
		flappybird.initRender();

	}

	@Override
	public void setBirdReady(boolean b) throws RemoteException {
		flappybird.setBirdReady(b);

	}

	public int getServerRandomForPipes() {
		try {
			return serverInterface.getServerRandomForPipes();
		} catch (RemoteException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int getScore() throws RemoteException {
		return flappybird.getScore();
	}

	@Override
	public void setHighscore(int highscore) throws RemoteException {
		flappybird.setHighscore(highscore);

	}

	@Override
	public String getName() throws RemoteException {
		return flappybird.getName();
	}

	public HashMap<String, Integer> getTopThree() throws RemoteException {
		return serverInterface.getTopThree();
	}
}
