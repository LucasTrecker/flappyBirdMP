package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

import standard.FlappyClientInterface;

public interface FlappyServerInterface extends Remote {
	public int login(FlappyClientInterface clientInterface) throws RemoteException;

	public double[] getPosExt(int remoteIndex) throws RemoteException;

	public void birdReady() throws RemoteException;

	public void birdDied() throws RemoteException;

	public int getServerRandomForPipes() throws RemoteException;

	public HashMap<String, Integer> getTopThree() throws RemoteException;
}
