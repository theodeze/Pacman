package fr.univangers.pacman.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.beans.PacmanGameSettings;
import fr.univangers.pacman.model.beans.PacmanGameState.Winner;
import fr.univangers.pacman.model.game.PacmanGame;

public class PacmanServer extends PacmanGame {

	private static final long serialVersionUID = -3170763958222800378L;
	private static final Logger LOGGER = LogManager.getLogger("Server"); 
	private static final String USER_AGENT = "Mozilla/5.0";
	
	private Socket so;
	private String token;
	
	public PacmanServer(PacmanGameSettings settings, Maze maze, Socket so, String token) {
		super(settings, maze);
		this.so = so;
		this.token = token;
	}
	
	@Override
	public void gameOver() {
		super.gameOver();
		sendPartie();
	}
	
	private void sendPartie() {
		try {
			String url = "http://localhost:8080/Pacman_Score/Partie";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			String urlParameters = 
					  "victoire=" + (state.getWinner() == Winner.PACMANWINNER) + "&"
					+ "score=" + state.getScore() + "&"
					+ "token=" + token;
			
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			if(responseCode != 200)
				LOGGER.warn("Le résultat de la partie n'a pas peut être envoyer.");

		} catch (IOException e) {
			LOGGER.warn("Le résultat de la partie n'a pas peut être envoyer.");
		}
	}
	
	@Override
	public void notifyViews() {
		if(so != null && !so.isClosed()) {
			try {
				PrintWriter output = new PrintWriter(so.getOutputStream(), true);
				output.println(state.toJson());
				state.setCurrentSong("");
			} catch (IOException e) {
				LOGGER.warn("Erreur envoie");
			}
		}
	}
}
