package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class FlappyDatabase {
	private Connection conn;
	private static final String DBCLASS = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String DBCONNECTIONPATH = "jdbc:derby:C:\\db;";
	// private static final String DBCONNECTIONPATH =
	// "jdbc:derby:N:\\BA-Ablagen\\D022\\D02200-ISPA-FI\\Gruppen\19A\\SeminarJava2\\Klausuren\\Strecker\\FlappyBirdMP\\db;";
	private static final String CREATE = "create=true";

	public FlappyDatabase() {
		try {

			Class.forName(DBCLASS);
			this.conn = DriverManager.getConnection(DBCONNECTIONPATH + CREATE);
			if (!checkIfTableExists()) {
				initFlappyHighscoresTable();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean checkIfTableExists() {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = conn.getMetaData().getTables(null, null, "HIGHSCORES", null);

			while (rs.next()) {
				String tbName = rs.getString("TABLE_NAME");
				if (tbName.equals("HIGHSCORES")) {
					return true; // vielleicht noch den Namen der tb pr�fen bei Parameter
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public void closeFlappyDb() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean checkIfDataBaseExists() {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = conn.getMetaData().getCatalogs();

			while (rs.next()) {
				String dbName = rs.getString(1);
				if (!dbName.equals(null)) {
					return true; // vielleicht noch den Namen der db pr�fen
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public void initFlappyHighscoresTable() {
		try {
			Statement st = conn.createStatement();
			st.execute("CREATE TABLE HIGHSCORES (SCORE INT, PLAYERNAME VARCHAR(15))");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearHighscoreTable() {
		Statement st;
		try {
			st = conn.createStatement();
			st.execute("DELETE * FROM HIGHSCORES");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertHighscore(int score, String playername) {
		try {
			PreparedStatement st = conn.prepareStatement("INSERT INTO HIGHSCORES (SCORE, PLAYERNAME) values (?, ?)");
			st.setInt(1, score);
			st.setString(2, playername);

			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, Integer> getTopThree() {
		HashMap<String, Integer> tmp = new HashMap<>();
		try {
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT DISTINCT * FROM HIGHSCORES ORDER BY SCORE DESC ");
			for(int i =0; i<3;i++) {
			if(rs.next()) {
				tmp.put(rs.getString("PLAYERNAME"), rs.getInt("SCORE"));
			}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmp;
	}

}
