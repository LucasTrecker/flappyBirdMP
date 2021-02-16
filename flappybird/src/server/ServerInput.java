package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerInput extends JFrame {
	JTextField inputServer = new JTextField(null, 15);
	JButton submit = new JButton("Submit");

	// Zuerst Serverfenster, nach Sumbit Serverstart
	public static void main(String[] args) {
		new ServerInput().init();
	}

	public void init() {
		setSize(300, 150);
		setTitle("ServerInput");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("Servername: "));
		panel1.add(inputServer);
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				FlappyServer.start(new String[] { inputServer.getText(), inputServer.getText() });
			}
		});

		JPanel containerPanel = new JPanel();
		containerPanel.add(panel1);
		containerPanel.add(submit);
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		add(containerPanel);
		setVisible(true);
		pack();
	}

	public JTextField getInputName() {
		return inputServer;
	}

}
