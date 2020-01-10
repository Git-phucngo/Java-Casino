package games.poker;
import deck.Deck;
import deck.Card;
public class Poker {
    
    private final Deck deck = new Deck();
    private final Card[]flop = new Card[3]; // the first three card on the table
    private Card turnCard;
    private Card riverCard;
    
    public Poker (){
        System.out.println("There are "+deck.getNumberOfCard()+" cards remain in deck now");
    }
    
    public void setTurnCard(){
        remove();
        turnCard = deck.getCard();
    }
    
    public Card getTurnCard(){
        return turnCard;
    }
    
    public void setRiverCard(){
        remove();
        riverCard= deck.getCard();
    }
    
    public Card getRiverCard(){
        return riverCard;
    }
    
    public void setFlop(){
    	remove();
        for(int i=0;i<3;i++){
            flop[i]=deck.getCard();
        }
    }
    
    public Card[] getFlop(){
        return flop ;
    }
    
    public void remove(){
        deck.getCard();
    }
    
    // Tan - get the number of card left in deck after set flop, turn, and river
    public int getNumberOfCard() {
        return deck.getNumberOfCard();
    }
    
    public Card getCard() {
    	return deck.getCard();
    }
    
    
    public boolean flopEmty(){
        return flop!=null;
    }
    
    public int converNumber(String suit){
        switch(suit){
            case "2" : return 2;  
            case "3" : return 3; 
            case "4" : return 4; 
            case "5" : return 5; 
            case "6" : return 6; 
            case "7" : return 7; 
            case "8" : return 8; 
            case "9" : return 9; 
            case "10" : return 10; 
            case "JACK" : return 11; 
            case "QUEEN" : return 12; 
            case "KING" : return 13;
            case "ACE" : return 14; 
            default: System.out.println("System Can't Conver Error");
        }
        return 0;
    }
    
    // Phuc This function ask for the Player Cards.
    public Card[] combineHand(Card [] playerCard){
        Card[] combinehand = new Card[7];
        int counter = 0;
        
        
        //Phuc - Combine The Player 2 Cards inside the Handrank
        for(int i=0; i  < playerCard.length;i++) {
            combinehand[counter] = playerCard[i];
            counter++;
        }
        //Phuc - check if flop is avalible then combine them
        
        if(flopEmty()!= false){
        	for(int i=0;i<flop.length;i++){
        		combinehand[counter]=flop[i];
        		counter++;
        	}
        }
        
        //Phuc - check if Turn is avalible then combine them
        if(turnCard !=null){
            combinehand[counter]=turnCard;
            counter++;
        }
        
        //Phuc - check if River is avalible then combine them
        if(riverCard !=null){
            combinehand[counter]=riverCard;
            counter++;
        }
        return combinehand;
    }
    
    public void handRank(Card [] playerCard){
    	Card [] combineHand = this.combineHand(playerCard);
    	
    	
        for(int i = 0; i< combineHand(playerCard).length; i++){
            System.out.printf("Hand : %d %s\n",i ,combineHand[i]);
        }
        int checkS=0;
        for(int i=0;i<combineHand.length;i++){
            int checkT=0;
            for(int j=i+1;j<combineHand.length;j++){
                
            if(combineHand[i].getFace().equals(combineHand[j].getFace())){ // Phuc Check Double 
                System.out.printf("Pair : %d %d\n",i,j);
                checkT++;
                if(checkT>1){ // Phuc Check Triple 
                    System.out.println("Oh A triple Baby!!!");
                }
                if(checkT>2){ // Phuc Check Four Of A Kind
                    System.out.println("Oh A QUATRA Baby!!!");
                }   
            }
            
            if(converNumber(combineHand[i].getFace())==converNumber(combineHand[j].getFace())+1){
               checkS++;
               if(checkS>4){
                   System.out.println("Straight");
            }
        }
    }
    }
}
}
