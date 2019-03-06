package fr.univangers.pacman.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import fr.univangers.pacman.model.beans.LoginInformation;
import fr.univangers.pacman.model.beans.PacmanGameState;
import fr.univangers.pacman.model.beans.PacmanGameState.Winner;

public class PostRequest {
	private static final Logger LOGGER = LogManager.getLogger("Post"); 
	private static final String USER_AGENT = "Mozilla/5.0";
	
	private PostRequest() {}
	
	public static String sendPost(String url, String urlParameters) {
		String stringResponse = "";
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			if(responseCode == 200) {
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();
				
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				stringResponse = new Gson().fromJson(response.toString(), String.class);
			} else {
				LOGGER.warn("Refusé");
			}
		} catch (IOException e) {
			LOGGER.warn("Erreur lecture");
		}
		return stringResponse;
	}
	
	public static String sendPartie(PacmanGameState state, String token) {
		String url = "http://localhost:8080/Pacman_Score/Partie";
		String urlParameters = 
				  "victoire=" + (state.getWinner() == Winner.PACMANWINNER) + "&"
				+ "score=" + state.getScore() + "&"
				+ "token=" + token;
		return sendPost(url, urlParameters);
	}
	
	public static String getToken(LoginInformation login) {
        String url = "http://localhost:8080/Pacman_Score/Authentification";
		String urlParameters = 
				  "username=" + login.getUsername() + "&"
				+ "password=" + login.getPassword();
		return sendPost(url, urlParameters);
	}
	
}
