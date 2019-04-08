package fr.univangers.pacman.model.beans;

import com.google.gson.Gson;

public class LoginInformation {
	private String host;
	private int port;
	private String username;
	private String password;

	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public static LoginInformation fromJson(String json) {
		return new Gson().fromJson(json, LoginInformation.class);
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
