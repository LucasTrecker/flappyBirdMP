package standard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javafx.application.Application;

public class PlayerInput extends JFrame {
	PlayerInput playerInput;
	JTextField inputName = new JTextField(null, 15);
	JTextField inputServer = new JTextField(null, 15);
	JButton submit = new JButton("Submit");
	
	//Zuerst Eingabefenster, nach sumbit die App
	public static void main(String[] args) {
		new PlayerInput().init();
	}
	
	public void init() {
		setSize(300, 150);
		setTitle("PlayerInput");
		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("Spielername: "));
		panel1.add(inputName);
		//panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		
		JPanel panel2 = new JPanel();
		panel2.add(new JLabel("Servername: "));
		panel2.add(inputServer);
		//panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		playerInput=this;
		submit.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  playerInput.dispatchEvent(new WindowEvent(playerInput, WindowEvent.WINDOW_CLOSING));
				  Application.launch(FlappyBird.class, (new String[] {inputName.getText(),inputServer.getText()}));
				  } 
				} );
		
		JPanel containerPanel = new JPanel();
		containerPanel.add(panel1);
		containerPanel.add(panel2);
		containerPanel.add(submit);
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		add(containerPanel);
		setVisible(true);
		pack();
	}
	

}