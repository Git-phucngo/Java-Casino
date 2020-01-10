package com.casino.util;

/*
    CPN = Casino Predefined Numbers
    All the constants will be stored in here
    Name Convention: variable names must be written in upper case;
*/

public class CPN {
    public static final int MAXIMUM_CARDS_PER_PLAYER = 2;
    public static final int MAXIMUM_PLAYER_PER_TABLE = 6;
    public static final int PLAYER_FIRST_CARD = 0;
    public static final int PLAYER_SECOND_CARD = 1;
    
    // command constants;
    public static class Command {
    	public static final int CHECK = 0;
    	public static final int CALL = 1;
    	public static final int FOLD = 2;
    	public static final int RAISE = 3;
    	public static final int ALL_IN = 4;
    }
    
    public static class CommandString {
    	public static final String CHECK = "Check";
    	public static final String CALL = "Call";
    	public static final String FOLD = "Fold";
    	public static final String RAISE = "Raise";
    	public static final String ALL_IN = "All-in";
    }
    
    public static class GameState {
    	public static final int PLAYER_BET_1 = 0; 
    	public static final int AI_BET_1 = 1;
    	public static final int SHOW_FLOP = 2; // show flop
    	public static final int PLAYER_BET_2 = 3; 
    	public static final int AI_BET_2 = 4; 
    	public static final int SHOW_TURN = 5; // show turn
    	public static final int PLAYER_BET_3 = 6;
    	public static final int AI_BET_3 = 7;
    	public static final int SHOW_RIVER = 8; // show river
    	public static final int PLAYER_BET_4 = 9; 
    	public static final int AI_BET_4 = 10; 
    	public static final int SHOW_HAND = 11;
    	public static final int IDLE = 12;
    }
    
    public static final int PLAYER00 = 0; // main player
    public static final int PLAYER01 = 1;
    public static final int PLAYER02 = 2;
    public static final int PLAYER03 = 3;
    public static final int PLAYER04 = 4;
    public static final int PLAYER05 = 5;
    
    public static class Flop  {
    	public static final int FIRST_FLOP = 0;
    	public static final int SECOND_FLOP = 1;
    	public static final int THIRD_FLOP = 2;
    }
}
