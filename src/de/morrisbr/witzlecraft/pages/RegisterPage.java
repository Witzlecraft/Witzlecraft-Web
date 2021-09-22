package de.morrisbr.witzlecraft.pages;

import de.morrisbr.witzlecraft.network.objects.EventPlayer;

public class RegisterPage {

	private String code;
	private EventPlayer player;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public EventPlayer getPlayer() {
		return player;
	}

	public void setPlayer(EventPlayer player) {
		this.player = player;
	}
}
