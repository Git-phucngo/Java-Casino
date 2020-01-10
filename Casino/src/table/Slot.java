package table;

import user.User;

/*
 * Slot.java
 * When each players join a table, they will be assigned into a slot.
 * Slot is a place where players can perform the commands of the game.
 * 	Command example: Fold, Check, Call, and Raise in Poker.
 * 
 * */

public class Slot {
	private User player;
	private String gameType;
	
	public Slot(User player) {
		setPlayer(player);
	}
	
	/*
	 * Register a new slot for player when they join the game/table.
	 * */
	public void setPlayer(User p) {
		this.player = p;
	}

	public User getPlayer() {
		return player;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public int getChips() {
		return player.getChips();
	}

	public void setChips(int chips) {
		player.setChips(chips);
	}
}
