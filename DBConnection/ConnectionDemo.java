import java.util.ArrayList;

public class ConnectionDemo {
	private String pwd;
	private String user;
	
	public ConnectionDemo(){
		pwd = DBDetails.password;
		user = DBDetails.username;
		testConnection();
	}
	
	public void testConnection(){
		//Do not run this program before running the setupDB program
		ArrayList<String> monsters;
		DBMonster m;
		DBMonster found;

		DBConnection c = new DBConnection(user, pwd);
		
		//test addMonster
		c.addMonster("kobold", "5", "50", "Tiny little humanoids with sharp spears");
		c.addMonster("orc", "3", "6", "Smelly creatures with blunt teeth");
		c.addMonster("snake", "6", "36", "Why did it have to be snakes");
		c.addMonster("golem", "1", "1", "Stone Golem");

		//test getAllMonsters
	    monsters = c.getAllMonsters();
		for (String result : monsters){
			System.out.println(result);
		}

		//test deleteMonster
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
		}


	}
	
	public static void main (String[] args){
		ConnectionDemo p = new ConnectionDemo();
	}

}
