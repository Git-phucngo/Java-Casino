package deck;

public class Card {
	private String suit;
	private String face;
	
	public Card(String s, String f) {
		this.suit = s;
		this.face = f;
	}
	
	public String getSuit() {
		return suit;
	}
	public void setSuit(String suit) {
		this.suit = suit;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String toString() {
		return suit+":"+face;
	}
}