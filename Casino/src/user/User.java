
package user;


public class User {
    private String name;
    private int age;
    private int chips;
    
    public User(){    
        this.name = "Default";
        this.age = 18;
    }
    public User(String name,int age, int chips){
        this.name= name;
        this.age = age;
        this.chips = chips;
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
}
