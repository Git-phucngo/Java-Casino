package main;

import games.poker.Poker;
import table.poker.PokerSlot;
import table.poker.PokerTable;
import user.User;
import deck.Card;

public class Casino {
	private Poker poker = null;
	private PokerSlot [] slot;
	private User [] player;
	private PokerTable table = null;
	
	public Casino() {
		this.poker = new Poker();
		this.slot = new PokerSlot[6];
		this.player = new User[6];
		this.table = new PokerTable();
		
		this.playerInitialize();
		this.slotInitialize();
		this.tableInitialize();
		this.dealCardToPlayer();
		
		this.log();
		
		this.poker.setFlop();
		this.poker.setTurnCard();
		this.poker.setRiverCard();
		this.compareCard();
		
	}
	
	public void playerInitialize() {
		
		// temporary
		player[0] = new User("Lux", 21, 1000);
		player[1] = new User("Shaco", 34, 2000);
		player[2] = new User("Jarvan", 25, 500);
		player[3] = new User("Blitcrank", 18, 5000);
		player[4] = new User("Jinx", 18, 5000);
		player[5] = new User("Graves", 50, 5000);
		
	}
	
	public void slotInitialize() {
		for(int i = 0; i < slot.length; i++) {
			slot[i] = new PokerSlot(player[i]);
		}
	}
	
	public void tableInitialize() {
		for(int i = 0; i < table.getMaximumPlayer(); i++) {
			table.addPlayerToTable(slot[i]);
		}
	}
	
	public void dealCardToPlayer() {
		final int MAXIMUM_CARD_PER_PLAYER = 2;
		for(int i = 0; i < MAXIMUM_CARD_PER_PLAYER; i++) {
			for(int j = 0; j < slot.length; j++) {
				slot[j].setCard(i, poker.getCard());
			}
		}
	}
	
	public void compareCard() {
		for(int i = 0; i < table.getMaximumPlayer(); i++) {
			System.out.println("\n"+slot[i].getPlayer().getName());
			poker.handRank(slot[i].getCard());
		}
	}
	
	public void log() {
		while(!table.getTable().isEmpty()) {
			PokerSlot player = (PokerSlot) table.getPlayerFromTalbe();
			String playerName = player.getPlayer().getName();
			int playerAge = player.getPlayer().getAge();
			int playerChips = player.getChips();
			Card card_1 = player.getCard(0);
			Card card_2 = player.getCard(1);
			
			String log = "Name: "+playerName+" Age:"+playerAge+" Chips:"+playerChips;
			log = log+" ["+card_1.toString()+"]";
			log = log+" ["+card_2.toString()+"]";
			
			System.out.println(log);
		}
	}
}
