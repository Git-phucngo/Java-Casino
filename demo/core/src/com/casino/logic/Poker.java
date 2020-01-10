package com.casino.logic;

import com.casino.deck.Card;
import com.casino.deck.Deck;
public class Poker {

    private final Deck deck = new Deck();
    private final Card[]flop = new Card[3];
    private Card turnCard;
    private Card riverCard;
    private final Card [] playerCard = new Card[7];
    private int highestCard;

    public Poker (){
    }
    public void sethighestCard(int card){
    	highestCard = card;
    }
    public float gethighestCard(){
    	return highestCard;
    }
    public void setTurnCard(){
        turnCard = deck.getCard();
    }

    public Card getTurnCard(){
        return turnCard;
    }

    public void setRiverCard(){
       riverCard= deck.getCard();
    }

    public Card getRiverCard(){
        return riverCard;
    }

    public void setFlop(){
        for(int i=0;i<3;i++){
            flop[i]=deck.getCard();
        }
    }

    public Card[] getFlop(){
        return flop ;
    }

    public Card getFlop(int i) {
    	return flop[i];
    }

    public void remove(){
        deck.getCard();
    }

    public int getNumberOfCard() {
        return deck.getNumberOfCard();
    }

    public Card getCard() {
    	return deck.getCard();
    }


    public boolean flopEmty(){
        return flop!=null;
    }
    private void high(){
    	highestCard = stoN(playerCard[playerCard.length-1].getFace());
    }

    public int combineHand(Card[] player){

        int counter = 0;
        for(int i=0; i  < player.length;i++) {
            playerCard[counter] = player[i];
            counter++;
        }
        if(flopEmty()!= false){
        	for(int i=0;i<flop.length;i++){
        		playerCard[counter]=flop[i];
        		counter++;
        	}
        }
        if(turnCard !=null){
            playerCard[counter]=turnCard;
            counter++;
        }
        if(riverCard !=null){
            playerCard [counter]=riverCard;
            counter++;
        }
        sortCard();
       return converRankingtoNumber (RankinginString());
    }

    private int stoN(String Face){
        int tempNum = 0;
        switch(Face){
            case "ACE" :tempNum = 14;  break;
            case "JACK": tempNum = 11; break;
            case "QUEEN": tempNum = 12;break;
            case "KING": tempNum = 13; break;
            case "2": tempNum = 2;break;
            case "3": tempNum = 3;break;
            case "4": tempNum = 4;break;
            case "5": tempNum = 5;break;
            case "6": tempNum = 6;break;
            case "7": tempNum = 7;break;
            case "8": tempNum = 8;break;
            case "9": tempNum = 9;break;
            case "10": tempNum = 10;break;
            default:;
        }
        return tempNum;
    }
    public int numberOfPair(){
        int numberOfPair = 0;
        for(int i=0;i<playerCard.length;i++){
            for(int j=i+1;j<playerCard.length;j++){
                if(playerCard[i].getFace().equals(playerCard[j].getFace())){
                    numberOfPair++;
                    highestCard = stoN(playerCard[j].getFace());
                }
            }
        }
        return numberOfPair;
    }

    private int sameCard(){
        int same=1;
        for(int i=0;i<playerCard.length;i++){
            for(int j=i+1;j<playerCard.length;j++){
                if(playerCard[i].getFace().equals(playerCard[j].getFace())){
                    same++;
                    highestCard = stoN(playerCard[j].getFace());
                }
                else if( same >= 3){
                    return same;
                }
                else{
                    same=0;
                }
        }
    }
        return same;
    }
    private boolean checkStraight(int a,int b){
        return a-1==b;
    }
    private boolean Straight(){
        int str=0;
        for(int i=playerCard.length-1;i>=0;i--){
                if(i==0){return false;}
                else if(playerCard[i].getFace().equals(playerCard[i-1].getFace())){}
                else if(checkStraight(stoN(playerCard[i].getFace()),stoN(playerCard[i-1].getFace()))){
                        if(str==0){
                            highestCard = stoN(playerCard[i].getFace());
                        }
                        str++;
                        if(str==4){return true;}
                    }
                else{
                    str=0;
                }
                }
        return false;
    }

    public boolean flush(){
        for(int i=0;i<playerCard.length;i++){
            int samesuit=0;
            for(int j=i+1;j<playerCard.length;j++){
            if(playerCard[i].getSuit().equals(playerCard[j].getSuit())){
                samesuit++;
                if(samesuit==4){
                    return true;
                }
            }

        }
    }
        return false;
    }

    public String RankinginString(){

        if(flush() && Straight()){
        	return  "STRAIGHT FLUSH";
        }
        else if(sameCard()>=4){
            return "FOUR OF A KIND";
            }
        else if(sameCard()==3){
            if(numberOfPair()>=4){
            	return "FULL HOUSE" ;
            }
            return "THREE OF A KIND";
        }
        else if (flush()){
        	return "FLUSH";
        }
        else if (Straight()){
            return "STRAIGHT";
        }
        else if(numberOfPair()>=2){
        	return "TWO PAIR";
            }
        else if(numberOfPair()==1){
        	return "ONE PAIR";
        }
        high();
        return "HIGH";
    }


    public int converRankingtoNumber (String rank){
    	switch(rank){
    		case "HIGH": return highestCard;
    		case "ONE PAIR": return 20 + highestCard;
    		case "TWO PAIR": return 40 + highestCard;
    		case "THREE OF A KIND": return 60 + highestCard;
    		case "STRAIGHT": return 80 + highestCard;
    		case "FLUSH": return 100 + highestCard;
    		case "FULL HOUSE": return 120 + highestCard;
    		case "FOUR OF A KIND": return 140 + highestCard;
    		case "STRAIGHT FLUSH": return 160 + highestCard;
    		case "ROYAL FLUSH": return 180 + highestCard;
    	}
    	return 0;
    }

    public void sortHandbyNum(){
        String temp;
        int i;
        boolean check = true;

        while(check)
        {
            check=false;
            for(i=0;i<playerCard.length-1;i++)
            {
                if(stoN(playerCard[i].getFace())>stoN(playerCard[i+1].getFace()))
                {
                    temp = playerCard[i].getFace();
                    playerCard[i].setFace(playerCard[i+1].getFace());
                    playerCard[i+1].setFace(temp);
                    check=true;
                }
            }
        }
    }
     public void sortCard() {
        int n = playerCard.length;
        int k;
        for (int m = n; m >= 0; m--) {
            for (int i = 0; i < n - 1; i++) {
                k = i + 1;
                if (stoN(playerCard[i].getFace()) > stoN(playerCard[k].getFace())) {
                    swapCard(i, k, playerCard);
                }
            }
        }
     }
    private void swapCard(int i,int j,Card[] array){
        Card temp;
        temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public void printHand(){
        sortCard();
        for(int i=0;i<playerCard.length;i++){
            System.out.printf("[%s] ",playerCard[i]);
        }
    }


}