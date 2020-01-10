package com.casino.table;

import com.casino.deck.Card;
import com.casino.player.Player;
import com.casino.util.CPN;

/*
 * Slot.java
 * When each players join a table, they will be assigned into a slot.
 * Slot is a place where players store the commands of the game.
 * 	Command example: Fold, Check, Call, and Raise in Poker
 * 
 * Modified Date: 4/30/2015 by DTT
 * */

public class Slot {
	private Player player = null;
	private Card [] playerCard = null;
	private String command = null;
	private boolean playerTurn = false;
	private boolean fold = false;
	// I may add the bet amount and the bet method to this file
	// the bet method will take the number of chip from player 
	// and set it to bet variable.
	private int bet;

	public Slot(Player player) {
		this.player = player;
		this.playerCard = new Card[CPN.MAXIMUM_CARDS_PER_PLAYER];
		this.bet = 0;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setCard(int i, Card c) {
		this.playerCard[i] = c;
	}

	public Card [] getCard() {
		return this.playerCard;
	}

	public Card getCard(int i) {
		return this.playerCard[i];
	}

	public void setCommand(String c) {
		this.command = c;
	}

	public String getCommand() {
		return this.command;
	}

	public void setBetAmount(int b) {
		this.bet += b;
		if((this.player.getChips()-b) >= 0 ) {
			this.player.setChips(this.player.getChips()-b); // decrease player's chips
		}
	}
	
	public boolean isBetable(int b) {
		return b <= this.player.getChips();
	}
	
	public int getBetAmount() {
		return this.bet;
	}
	
	public boolean isPlayerFolded() {
		return this.fold;
	}

	public boolean isPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(boolean playerTurn) {
		this.playerTurn = playerTurn;
	}
}
