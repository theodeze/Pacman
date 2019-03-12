package fr.univangers.pacman.model.beans;

import com.google.gson.Gson;

public class ServerInformation {
	private int port;
	private boolean needAuthentication;

	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public static ServerInformation fromJson(String json) {
		return new Gson().fromJson(json, ServerInformation.class);
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public boolean isNeedAuthentication() {
		return needAuthentication;
	}
	
	public void setNeedAuthentication(boolean needAuthentication) {
		this.needAuthentication = needAuthentication;
	}
	
}
