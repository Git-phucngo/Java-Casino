package table.poker;

import table.Slot;
import user.User;
import deck.Card;

/*
 * 
 * PokerSlot.java
 * Slot for Poker game
 * Holding two cards and four function Fold, Check, Call, Raise
 * 
 * */


public class PokerSlot extends Slot {
	private Card [] card;
	private String command;
	
	public PokerSlot(User player) {
		super(player);
		card = new Card[2];
	}
	
	public Card getCard(int i) {
		return this.card[i];
	}
	
	public Card[] getCard() {
		return card;
	}

	public void setCard(int i, Card card) {
		this.card[i] = card;
	}
	
	public void setCard(Card[] card) {
		this.card = card;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}	
}
