package standard;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FlappyClientInterface extends Remote{

	void registerNewBird(int remoteIndex) throws RemoteException;

	double[] getPos() throws RemoteException;
	
	void setBirdDead(boolean dead) throws RemoteException;

	void setGameStarted(boolean b) throws RemoteException;
	
	void setMainMenu(boolean b) throws RemoteException;

	void initRender() throws RemoteException;

	void renderBirdAfterGameEnded() throws RemoteException;

	void setBirdReady(boolean b) throws RemoteException;

	int getScore() throws RemoteException;

	void setHighscore(int highscore) throws RemoteException;

	String getName() throws RemoteException;
}
