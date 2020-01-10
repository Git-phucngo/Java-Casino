package table.poker;

import table.Slot;
import table.Table;

public class PokerTable extends Table{
	private final int MAXIMUM_NUMBER_OF_PLAYERS = 6;
	private int counter = 1;
	
	public void addPlayerToTable(Slot s) {
		if(counter <= MAXIMUM_NUMBER_OF_PLAYERS) {
			super.addSlotToTable(s);
			counter++;
		} else {
			System.out.println("Table is full.");
		}
	}
	
	public Slot getPlayerFromTalbe() {
		return super.getSlotFromTable();
	}
	
	public int getMaximumPlayer() {
		return this.MAXIMUM_NUMBER_OF_PLAYERS;
	}
}
