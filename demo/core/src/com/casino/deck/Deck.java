package com.casino.deck;

import java.util.Random;
import java.util.Stack;

public class Deck {
	private final int DEFAULT_SUIT = 4;
	private final int DEFAULT_FACE = 13;
	private Card [][] deck = new Card[DEFAULT_SUIT][DEFAULT_FACE];
	private Stack<Card> shuffledDeck = new Stack<Card>();
        
        // cardTotal holds the number of cards when the card is added or removed
        // from shuffleDeck.
	private int numberOfCard = 0; 

	public Deck() {
		generate();
		shuffle(); 
		setShuffledDeck();
	}

	public Card[][] getDeck() {
		return deck;
	}

	public void setDeck(Card[][] deck) {
		this.deck = deck;
	}
	
	public Stack<Card> getShuffledDeck() {
		for(int i = 0; i < 52; i++) {
			System.out.println(i+" "+shuffledDeck.pop().toString());
		}
		
		return shuffledDeck;
	}
        // Phuc - add to test PokerRank (Maybe can put somewhere more effective)
        public Card getCard(){
            numberOfCard--;
            return shuffledDeck.pop();
        }
        
        // Tan - get card total 
        public int getNumberOfCard() {
            return numberOfCard;
        }
        
	// Tan - Copy the deck from 2d array to the stack
	public void setShuffledDeck() {
		for(int i = 0; i < DEFAULT_SUIT; i++) {
			for(int j = 0; j < DEFAULT_FACE; j++) {
				shuffledDeck.push(deck[i][j]);
                                numberOfCard++;
			}
		}
	}

	private void generate() {
		String tempSuit, tempFace;
		tempSuit = tempFace = null;
		
		for(int i = 0; i < DEFAULT_SUIT; i++) {
			for(int j = 0; j < DEFAULT_FACE; j++) {
				switch(i) {
				case 0: tempSuit = "SPADE"; break;
				case 1: tempSuit = "CLUB"; break;
				case 2: tempSuit = "DIAMOND"; break;
				case 3: tempSuit = "HEART"; break;
				default:;
				}
				
				switch(j) {
				case 9: tempFace = "JACK"; break;
				case 10: tempFace = "QUEEN"; break;
				case 11: tempFace = "KING"; break;
				case 12: tempFace = "ACE"; break;
				default: tempFace = String.valueOf(j+2); 
				}
				
				Card tempCard = new Card(tempSuit,tempFace);
				deck[i][j] = tempCard; // add tempCard to the deck
			}
		}
	}
	
	private void shuffle() {
		Random rand = new Random();
		int randomTime, suitNumber, faceNumber;
		randomTime = rand.nextInt(6)+1; // deck will be shuffled randomly from 1 to 5 times
		
		for(int k = 0; k < randomTime; k++) { 
			for(int i = 0; i < DEFAULT_SUIT; i++) {
				for(int j = 0; j < DEFAULT_FACE; j++) {
					suitNumber = rand.nextInt(3)+0;
					faceNumber = rand.nextInt(12)+0;
				
					if(suitNumber != i || faceNumber != j) {
						swap(i, j, suitNumber, faceNumber);
					}
				}
			}
		}
	}
	
	private void swap(int i, int j, int k, int l) {
		Card temp;
		temp = deck[i][j];
		deck[i][j] = deck[k][l];
		deck[k][l] = temp;
	}
	
	public String toString() {
		int counter = 1;
		String temp = "";
		for(int i = 0; i < DEFAULT_SUIT; i++) {
			for(int j = 0; j < DEFAULT_FACE; j++) {
				temp += counter+" "+deck[i][j].toString()+"\n";
				counter++;
			}
		}
		return temp;
	}
}


