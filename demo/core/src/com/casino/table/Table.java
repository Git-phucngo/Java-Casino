package com.casino.table;

import java.util.LinkedList;

import com.casino.util.CPN;

public class Table {
    private LinkedList<Slot> slotList;
    private int numberOfPlayer = 0;
    
    public Table() {
        this.slotList = null; // clear the queue before initalize it;
        this.slotList = new LinkedList<Slot>();
    }
    
    public void addPlayerToTable(Slot s) {
        if(numberOfPlayer < CPN.MAXIMUM_PLAYER_PER_TABLE) {
            this.slotList.add(s);
            numberOfPlayer++;
        } else {
            System.out.println("Table Message: The table is full now");
        }
    }
    
    public Slot getPlayerFromTable() {
        Slot s = null;
        // Do not run the following code if the queue is empty;
        if(!slotList.isEmpty()) {
            numberOfPlayer--;
            s = this.slotList.remove();
        } else {
            System.out.println("Table Message: The table is empty now");
        }
        return s;
    }
    
    public LinkedList<Slot> getSlotList() {
		return slotList;
	}

	public boolean isEmpty() {
        return slotList.isEmpty();
    }
    
    /*
    Note: I may add a stack to store all the fold players in there
    and take them back to the slotList in the new game;
    */
}
