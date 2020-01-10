
package com.casino.player;


public class Player {
    private String name;
    private int age;
    private int chips;
    private String avatar;
    
    public Player(){    
        this.name = "Default";
        this.age = 18;
    }
    public Player(String name,int age, int chips, String avatar){
        this.name= name;
        this.age = age;
        this.chips = chips;
        this.avatar = avatar;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
	public int getChips() {
		return chips;
	}
	public void setChips(int chips) {
		this.chips = chips;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
