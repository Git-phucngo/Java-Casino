package com.casino.main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.casino.deck.Card;
import com.casino.logic.Poker;
import com.casino.player.Player;
import com.casino.table.Slot;
import com.casino.table.Table;
import com.casino.util.CPN;

public class Casino {
	private Poker poker = null;
	private Slot [] slot;
	private Player [] players;
	private Table table = null;
	private int [] rank; 
	private int gameState = CPN.GameState.PLAYER_BET_1;
	private int currentRaise = 50;
	public boolean update1GameStage = false;
	public boolean update2GameStage = false;
	public boolean update3GameStage = false;
	public boolean update4GameStage = false;
	private boolean flopShowed = false;
	private boolean turnShowed = false;
	private boolean riverShower = false;
	//private int gameRound = 0;
	private int firstWinner = 0;
	private int secondWinner = 0;
	
	public Casino() {
		this.poker = new Poker();
		this.slot = new Slot[CPN.MAXIMUM_PLAYER_PER_TABLE];
		this.players = new Player[CPN.MAXIMUM_PLAYER_PER_TABLE];
		this.table = new Table();
		this.rank = new int[CPN.MAXIMUM_PLAYER_PER_TABLE];
		/*
		this.poker.setFlop();
		this.poker.setTurnCard();
		this.poker.setRiverCard();
		this.compareCard();
		*/
		
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if(gameState == CPN.GameState.AI_BET_1) {
					botCall();
					setGameState();
					update1GameStage = true;
				} 
				if(gameState == CPN.GameState.SHOW_FLOP) {
					poker.setFlop();
					flopShowed = true;
					setGameState();
				} 
				if(gameState == CPN.GameState.AI_BET_2) {
					botCall();
					setGameState();
					update2GameStage = true;
				}
				if(gameState == CPN.GameState.SHOW_TURN) {
					poker.setTurnCard();
					turnShowed = true;
					setGameState();
				}
				if(gameState == CPN.GameState.AI_BET_3) {
					botCall();
					setGameState();
					update3GameStage = true;
				}
				if(gameState == CPN.GameState.SHOW_RIVER) {
					poker.setRiverCard();
					riverShower = true;
					setGameState();
				}
				if(gameState == CPN.GameState.AI_BET_4) {
					botCall();
					setGameState();
					update3GameStage = true;
					System.out.println("Game state: "+ gameState);
				}
				if(gameState == CPN.GameState.SHOW_HAND) {
					compareCard();
					findWinner();
					update4GameStage = true;
					setGameState();
				}
			}
		}, 0, 1, TimeUnit.MILLISECONDS);
		
	}
	
	public Player getPlayer(int i) {
		return players[i];
	}
	
	public Slot getSlot(int i) {
		return slot[i];
	}
	
	public void activeTurn(int i) {
		slot[i].setPlayerTurn(true);
	}
	
	public void deactiveTurn(int i) {
		slot[i].setPlayerTurn(false);
	}
	
	public int getCurrentRaise() {
		return currentRaise;
	}
	
	public void createNewPlayer(String name, int age, int chips, String avatar) {
		players[0] = new Player(name, age, chips, avatar);
	}
	
	public void initialize() {
		this.playersInitialize();
		this.slotInitialize();
		this.tableInitialize();
		this.defaultBet();
		this.dealCardToPlayer();
	}
	
	private void defaultBet() {
		int defaultBet = 50;
		for(int i = 0; i < CPN.MAXIMUM_PLAYER_PER_TABLE; i++) {
			Slot temp = table.getPlayerFromTable();
			temp.setBetAmount(defaultBet);
			table.addPlayerToTable(temp);
		}
	}
	
	public void playersInitialize() {
		// temporary
		players[1] = new Player("Shaco", 34, 1000, "default");
		players[2] = new Player("Jarvan", 25, 1000, "default");
		players[3] = new Player("Blitcranka", 18, 1000, "default");
		players[4] = new Player("Jinx", 18, 1000, "default");
		players[5] = new Player("Graves", 50, 1000, "default");

	}

	public void slotInitialize() {
		for(int i = 0; i < slot.length; i++) {
			slot[i] = new Slot(players[i]);
		}
	}

	public void tableInitialize() {
		for(int i = 0; i < CPN.MAXIMUM_PLAYER_PER_TABLE; i++) {
			table.addPlayerToTable(slot[i]);
		}
	}

	public void dealCardToPlayer() {
		for(int i = 0; i < CPN.MAXIMUM_CARDS_PER_PLAYER; i++) {
			for(int j = 0; j < slot.length; j++) {
				slot[j].setCard(i, poker.getCard());
			}
		}
	}
	public void compareCard() {
		int temp = 0;
		System.out.println("Compare hand");
		for(int i = 0; i < CPN.MAXIMUM_PLAYER_PER_TABLE; i++) {
			temp = poker.combineHand(slot[i].getCard());
			rank[i] = temp;
		}
		printRank();
	}
	
	public void findFirstWinner() {
		firstWinner = 0;
		int max = rank[0];
		for(int i = 1; i < CPN.MAXIMUM_PLAYER_PER_TABLE; i++) {
			if(max < rank[i]) {
				max = rank[i];
				firstWinner = i;
			}
		}
	}
	
	public void findSecondWinner() {
		secondWinner = 0;
		int max = rank[0];
		for(int i = 1; i < CPN.MAXIMUM_PLAYER_PER_TABLE; i++) {
			if(max < rank[i]) {
				if(i == firstWinner) {
					// skip
				} else {
					max = rank[i];
					secondWinner = i;
				}
			}
		}
	}
	
	public void findWinner() {
		findFirstWinner();
		findSecondWinner();
		if(rank[firstWinner] == rank[secondWinner]) {
			int halfPrize = getTotalBet()/2;
			slot[firstWinner].getPlayer().setChips(slot[firstWinner].getPlayer().getChips()+halfPrize);
			slot[secondWinner].getPlayer().setChips(slot[firstWinner].getPlayer().getChips()+halfPrize);
		} else {
			int prize = getTotalBet();
			slot[firstWinner].getPlayer().setChips(slot[firstWinner].getPlayer().getChips()+prize);
		}
	}
	
	public int getTotalBet() {
		int sum = 0;
		for(int i = 0; i < CPN.MAXIMUM_PLAYER_PER_TABLE; i++) {
			sum += slot[i].getBetAmount();
		}
		return sum;
	}
	
	public void printRank() {
		System.out.print("Rank: ");
		for(int i = 0; i < CPN.MAXIMUM_PLAYER_PER_TABLE; i++) {
			System.out.print(rank[i]+" ");
		}
		System.out.println("");
	}	

	/*
	public void compareCard() {
		System.out.println("Compare hand");
		for(int i = 0; i < CPN.MAXIMUM_PLAYER_PER_TABLE; i++) {
			System.out.println("\n"+slot[i].getPlayer().getName());
			poker.combineHand(slot[i].getCard());
			poker.printHand();
			System.out.println(poker.RankinginString());
		}
	}
	*/
	
	public void log() {
		LinkedList<Slot> temp = table.getSlotList();
		Iterator<Slot> itr = temp.iterator();
		
		while(itr.hasNext()) {
			Slot slotPlayer = (Slot) itr.next(); 
			Player player = slotPlayer.getPlayer();
			String playerName = player.getName();
			int playersAge = player.getAge();
			int playersChips = player.getChips();
			Card card_1 = slotPlayer.getCard(CPN.PLAYER_FIRST_CARD);
			Card card_2 = slotPlayer.getCard(CPN.PLAYER_SECOND_CARD);
			
			int bet = slotPlayer.getBetAmount();
			
			String log = "Name: "+playerName+" Age:"+playersAge+" Chips:"+playersChips;
			log = log+" ["+card_1.toString()+"]";
			log = log+" ["+card_2.toString()+"] Bet: "+bet;

			System.out.println(log);
		}
	}

	public void gameState() {
		// 1. wait for player to call/raise/fold
		// 2. show flop
		this.poker.setFlop();
		
		// 3 repeat 1
		
		// 4 show turn
		
		// 5 repeat 1
		
		// 6 show river
		
		// repeat 1
		
		// compare card
		
		// end game
		
	}
	
	// main player command
	// call function
	public void playerCall(int i) {
		if(slot[i].getPlayer().getChips()+slot[i].getBetAmount() >= currentRaise) {
			int b = currentRaise - slot[i].getBetAmount();
			if(b < 0) {
				b = 0;
			}
			slot[i].setBetAmount(b);
		} else if (slot[i].getPlayer().getChips()+slot[i].getBetAmount() < currentRaise) {
			slot[i].setBetAmount(slot[i].getPlayer().getChips());
		}
		slot[i].setCommand(CPN.CommandString.CALL);
		setGameState();
		System.out.println("Game state: "+gameState);
	}
	
	// raise function
	public void playerRaise(int i, int raiseAmount) {
		slot[i].setBetAmount(raiseAmount);
		currentRaise += raiseAmount;
		slot[i].setCommand(CPN.CommandString.RAISE);
		System.out.println("CURRENT RAISE: "+currentRaise);
		setGameState();
		this.log();
	}
	
	// all in function
	public void playerAllin(int i) {
		slot[i].setBetAmount(slot[i].getPlayer().getChips());
		slot[i].setCommand(CPN.CommandString.ALL_IN);
		setGameState();
	}
	
	// bot command
	public void botCall() {
		System.out.println("\nBots call...");
		for(int i = 1; i < CPN.MAXIMUM_PLAYER_PER_TABLE; i++) {
			if(slot[i].getPlayer().getChips()+slot[i].getBetAmount() >= currentRaise) {
				int b = currentRaise - slot[i].getBetAmount();
				if(b < 0) {
					b = 0;
				}
				slot[i].setBetAmount(b);
			} else if (slot[i].getPlayer().getChips()+slot[i].getBetAmount() < currentRaise) {
				slot[i].setBetAmount(slot[i].getPlayer().getChips());
			}
			slot[i].setCommand(CPN.CommandString.CALL);
		}
		this.log();
	}

	/*
	public boolean isUpdateGameStage() {
		return updateGameStage;
	}
	*/
	
	public void setUpdateGameStage() {
		this.update1GameStage = false;
		this.update2GameStage = false;
		this.update3GameStage = false;
		this.update4GameStage = false;
	} 

	public Poker getPoker() {
		return poker;
	}

	public boolean isFlopShowed() {
		return flopShowed;
	} 
	
	public boolean isTurnShowed() {
		return turnShowed;
	}

	public boolean isRiverShowed() {
		return riverShower;
	}

	public void setGameState() {
		if(gameState == CPN.GameState.PLAYER_BET_1) {
			gameState = CPN.GameState.AI_BET_1;
		} else if(gameState == CPN.GameState.AI_BET_1) {
			gameState = CPN.GameState.SHOW_FLOP;
		} else if(gameState == CPN.GameState.SHOW_FLOP) {
			gameState = CPN.GameState.PLAYER_BET_2;
		} else if(gameState == CPN.GameState.PLAYER_BET_2) {
			gameState = CPN.GameState.AI_BET_2;
		} else if(gameState == CPN.GameState.AI_BET_2) {
			gameState = CPN.GameState.SHOW_TURN;
		} else if(gameState == CPN.GameState.SHOW_TURN) {
			gameState = CPN.GameState.PLAYER_BET_3;
		} else if(gameState == CPN.GameState.PLAYER_BET_3) {
			gameState = CPN.GameState.AI_BET_3;
		} else if(gameState == CPN.GameState.AI_BET_3) {
			gameState = CPN.GameState.SHOW_RIVER;
		} else if(gameState == CPN.GameState.SHOW_RIVER) {
			gameState = CPN.GameState.PLAYER_BET_4;
		} else if(gameState == CPN.GameState.PLAYER_BET_4) {
			gameState = CPN.GameState.AI_BET_4;
		} else if(gameState == CPN.GameState.AI_BET_4) {
			gameState = CPN.GameState.SHOW_HAND;
		} else if(gameState == CPN.GameState.SHOW_HAND) {
			gameState = CPN.GameState.IDLE;
		}
	}
}
