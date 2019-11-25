package dbconnection;
import java.util.ArrayList;

public class ConnectionDemo {
	private String pwd;
	private String user;
	public ConnectionDemo(){
		pwd = DBDetails.password;
		user = DBDetails.username;
	}
	
	public ArrayList<String> getMonsters(){
		ArrayList<String> monsters;
		DBConnection c = new DBConnection(user, pwd);
	    monsters = c.getAllMonsters();
		return monsters;
	}
	
	public void emptyDatabase(){
		DBConnection c = new DBConnection(user, pwd);
		c.deleteAllMonsters();
	}
	
	public void addMonster(String name, String lo, String hi, String desc){
		DBConnection c = new DBConnection(user, pwd);
		c.addMonster(name, lo, hi, desc);
	}
	
	public void testConnection(){
		//Do not run this program before running the setupDB program
		
		DBMonster m;
		DBMonster found;

		DBConnection c = new DBConnection(user, pwd);
		ArrayList<String> monsters;
		//test addMonster
		c.addMonster("kobold", "5", "50", "Tiny little humanoids with sharp spears");
		c.addMonster("orc", "3", "6", "Smelly creatures with blunt teeth");
		c.addMonster("snake", "6", "36", "Why did it have to be snakes");
		c.addMonster("golem", "1", "1", "Stone Golem");

		

		/*//test deleteMonster
		c.deleteMonster("golem");

		//test getAllMonsters
		 monsters = c.getAllMonsters();
		for (String result : monsters){
			System.out.println(result);
		}
		
		//test add monster
		m = new DBMonster("giant frog","2","5","ribbit");
		c.addMonster(m);
		c.addMonster("shrieker","1","2","");

		//test  find Monster
		found = c.findMonster("kobold");
		System.out.println(found);

		found.setUpperBound("1000");
		c.updateMonster(found);
		m = c.findMonster("kobold");
		System.out.println(m);

	    monsters = c.getAllMonsters();
		for (String result : monsters){
			System.out.println(result);
		}

		//test delete all Monsters
		c.deleteAllMonsters();

	    monsters = c.getAllMonsters();
		for (String result : monsters){
			System.out.println(result);
		}*/


	}
	
	/*public static void main (String[] args){
		ConnectionDemo p = new ConnectionDemo();
	}*/

}
